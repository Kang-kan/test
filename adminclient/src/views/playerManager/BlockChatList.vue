<template>
  <a-card :bordered="false">
    <div class="table-operator">
      <a-button type="primary" icon="plus" @click="$refs.createModal.add()">禁言</a-button>
    </div>
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="0">
          <a-col :md="5" :sm="12">
            <a-row>
              <a-col :md="7" style="line-height: 32px;">区服:</a-col>
              <a-col :md="14">
                <a-select size="default" @change="serverChange">
                  <a-select-option
                    v-for="i in serverList"
                    :key="i.id"
                    :value="i.id"
                  >{{i.name}}({{i.id}})</a-select-option>
                </a-select>
              </a-col>
            </a-row>
          </a-col>
          <a-col :md="5" :sm="24" style="padding-right: 18px;">
            <a-input placeholder="请输入账号id" v-model="queryParam.accountId" />
          </a-col>
          <a-col :md="5" :sm="24" style="padding-right: 18px;">
            <a-row>
              <a-input placeholder="请输入玩家id" v-model="queryParam.playerId" />
            </a-row>
          </a-col>
          <a-col :md="5" :sm="24" style="padding-right: 18px;">
            <a-input placeholder="请输入玩家名称" v-model="queryParam.playerName" />
          </a-col>
          <a-col :md="2" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" style="margin-left: 8px" @click="seach">查询</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <s-table ref="table" size="default" :columns="columns" :data="loadData" rowKey="id">
      <span slot="status" slot-scope="status">
        <a-badge :status="status | statusTypeFilter" :text="status | statusFilter" />
      </span>
      <span slot="roleNames" slot-scope="roleNames">
        <a-tag
          v-for="roleName in roleNames"
          :color="roleName === 'admin' ? 'volcano' : (roleName.length > 5 ? 'geekblue' : 'green')"
          :key="roleName"
        >{{ roleName }}</a-tag>
      </span>
      <span slot="action" slot-scope="text, record">
        <a href="javascript:;" @click="openDelet(record)">解除禁言</a>
      </span>
    </s-table>
    <create-form ref="createModal" @ok="handleOk" />
    <a-modal
      title="解除玩家禁言"
      :visible="deletvisible"
      @ok="handleDelet"
      @cancel="deletvisible = false"
    >
      <p>{{deletText}}</p>
    </a-modal>
  </a-card>
</template>

<script>
  import xmlObj from '@/config/xmlConfig'
  import { formatTimestamp } from '@/utils/util'
  import { STable } from '@/components'
  import CreateForm from './modules/BlockChat'
  import { getAccessRight } from '@/api/manage'
  import { getServerInfo, unBlockChat, queryBlockChats, queryPlayerBlockChat, queryBlockChatsBySrv } from '@/api/user'

  const statusMap = {
    0: {
      status: 'error',
      text: '禁用'
    },
    1: {
      status: 'processing',
      text: '启用'
    }
  }

  export default {
    name: 'BlockChatList',
    components: {
      STable,
      CreateForm
    },
    data() {
      return {
        mailType: 0,
        description: 'desc.',
        deletvisible: false,
        deletText: '',
        deletId: '',
        sendvisible: false,
        sendText: '',
        serverData: '',
        sendId: '',
        visible: false,
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 }
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 }
        },
        form: null,
        mdl: {},

        // 高级搜索 展开/关闭
        advanced: false,
        channelIdList: [],
        serverList: [],
        tableData: {},
        // 查询参数
        queryParam: {
          serverId: '',
          accountId: '',
          playerId: '',
          playerName: ''
        },
        pagination: {
          position: 'bottom',
          pageSizeOptions: ['10', '20', '50', '99999'],
          defaultPageSize: 10
        },
        // 表头
        columns: [
          {
            title: '渠道',
            dataIndex: 'channelId',
            customRender: channelId => {
              let name
              this.channelIdList.forEach(el => {
                if (el.id == channelId) {
                  name = el.name + '(' + el.id + ')'
                }
              })
              return name
            }
          },
          {
            title: '服务器id',
            dataIndex: 'serverId',
            scopedSlots: {
              customRender: 'description'
            },
            customRender: serverId => {
              let name
              this.serverList.forEach(el => {
                if (el.id == serverId) {
                  name = el.name + '(' + el.id + ')'
                }
              })
              return name
            }
          },
          {
            title: '账号',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'account',
            customRender: account => {
              return account.split('.')[0]
            }
          },
          {
            title: '玩家id',
            dataIndex: 'playerId',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '玩家名',
            dataIndex: 'playerName',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '原因',
            dataIndex: 'reason'
          },
          {
            title: '解封时间',
            dataIndex: 'unBlockTime',
            customRender: time => (time < 0 ? '永久' : time == 0 ? '' : formatTimestamp(time))
          },
          {
            title: '更新时间',
            dataIndex: 'updateTime',
            customRender: time => (time > 0 ? formatTimestamp(time) : '')
          },
          {
            title: '行动',
            width: '150px',
            dataIndex: 'action',
            scopedSlots: { customRender: 'action' }
          }
        ],
        // 加载数据方法 必须为 Promise 对象
        loadData: parameter => {
          let s = this
          let pageNo = parameter.pageNo
          let pageSize = parameter.pageSize
          if (!s.queryParam.serverId && !s.queryParam.playerId && !s.queryParam.playerName && !s.queryParam.accountId) {
            return queryBlockChats(Object.assign(parameter, s.queryParam)).then(res => {
              if (!res || !res.contents) {
                return {
                  pageSize: 10,
                  pageNo: 0,
                  totalPage: 0,
                  totalCount: 0,
                  contents: []
                }
              }
              return res
            })
          } else if (
            s.queryParam.serverId &&
            !s.queryParam.playerId &&
            !s.queryParam.playerName &&
            !s.queryParam.accountId
          ) {
            return queryBlockChatsBySrv(Object.assign(parameter, s.queryParam)).then(res => {
              if (!res || !res.contents) {
                return {
                  pageSize: 10,
                  pageNo: 0,
                  totalPage: 0,
                  totalCount: 0,
                  contents: []
                }
              }
              return res
            })
          } else if (
            s.queryParam.serverId &&
            !s.queryParam.playerId &&
            !s.queryParam.playerName &&
            !s.queryParam.accountId
          ) {
            return queryPlayerBlockChat(Object.assign(parameter, s.queryParam)).then(res => {
              if (!res) {
                return {
                  pageSize: 10,
                  pageNo: 0,
                  totalPage: 0,
                  totalCount: 0,
                  contents: []
                }
              }
              let obj = {}
              obj.totalPage = Math.ceil(res.length / pageSize)
              obj.totalCount = res.length
              obj.pageNo = s.currentPage
              obj.pageSize = pageSize
              obj.contents = res
              return obj
            })
          }
        },
        selectedRowKeys: [],
        selectedRows: []
      }
    },
    created() {
      getAccessRight().then(res => {
        // console.log('permission=====================>',res);
      })
    },
    methods: {
      serverChange(value) {
        this.queryParam.serverId = Number(value)
      },
      seach() {
        this.$refs.table.refresh(true)
      },
      openDelet(record) {
        this.deletText = `确认解除玩家:${record.playerName}禁言`
        this.serverData = record
        this.deletvisible = true
      },
      handleDelet() {
        console.log(111119, this.serverData)
        let obj = {}
        obj['playerId'] = this.serverData.playerId
        obj['serverId'] = this.serverData.serverId
        let s = this
        unBlockChat(obj).then(res => {
          if (res != null) {
            s.$message.success('成功解除禁言')
          }
          s.deletvisible = false
          s.$refs.table.refresh()
        })
      },
      handleOk() {
        this.$refs.table.refresh()
      },
      onChange(selectedRowKeys, selectedRows) {
        this.selectedRowKeys = selectedRowKeys
        this.selectedRows = selectedRows
      },
      toggleAdvanced() {
        this.advanced = !this.advanced
      }
    },
    filters: {
      statusFilter(type) {
        return statusMap[type].text
      },
      statusTypeFilter(type) {
        return statusMap[type].status
      }
    },
    watch: {},
    mounted: function() {
      let s = this
      s.$ajax.get('../gameVar.json').then(res => {
        if (!res || !res.data) {
          return
        }
        s.channelIdList = res.data.channelIdList
      })
      this.$http.get('/roleGameServer/getMyServers').then(res => {
        s.serverList = res
        if (!res || res.length == 0) {
          return
        }
        getServerInfo({ serverId: res[0].id }).then(res => {
          if (!res) {
            let startTime = 20190101
            s.time = s.$moment(startTime.toString(), 'YYYYMMDD')
            s.startTime = startTime
            return
          }
          let startTime = s.$moment(res.openTime).format('YYYYMMDD')
          s.time = s.$moment(startTime, 'YYYYMMDD')
          s.startTime = startTime
        })
      })
    }
  }
</script>
