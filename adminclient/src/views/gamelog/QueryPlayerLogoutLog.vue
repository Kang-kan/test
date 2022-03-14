<template>
  <a-card :bordered="false">
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

    <s-table
      ref="table"
      size="default"
      rowKey="monthtableid"
      :columns="columns"
      :data="loadData"
      :pagination="pagination"
      style="word-break: normal;"
      showPagination="auto"
    >
      <span slot="serial" slot-scope="text, record, index">{{ index + 1 }}</span>
      <span slot="status" slot-scope="text">
        <a-badge :status="text | statusTypeFilter" :text="text | statusFilter" />
      </span>
      <span slot="description" slot-scope="text">
        <ellipsis :length="25" tooltip>{{ text }}</ellipsis>
      </span>

      <span slot="action" slot-scope="text, record">
        <template>
          <a @click="handleEdit(record)">配置</a>
          <a-divider type="vertical" />
          <a @click="handleSub(record)">订阅报警</a>
        </template>
      </span>
    </s-table>
    <create-form ref="createModal" @ok="handleOk" />
    <step-by-step-modal ref="modal" @ok="handleOk" />
  </a-card>
</template>

<script>
import { formatTimestamp } from '@/utils/util'
import moment from 'moment'
import { STable, Ellipsis, Modal } from '@/components'
import StepByStepModal from '../list/modules/StepByStepModal'
import CreateForm from '../list/modules/CreateForm'
import { getQueryLogoutLog, getServerInfo } from '@/api/user'
export default {
  name: 'QueryPlayerLogoutLog',
  components: {
    STable,
    Ellipsis,
    CreateForm,
    StepByStepModal
  },
  data() {
    return {
      showpagi: false,
      currentPage: 0,
      time: null,
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
        pageSizeOptions: ['10', '20', '50','99999'],
        defaultPageSize: 10
      },
      // 表头
      columns: [
        {
          title: '时间',
          dataIndex: 'time',
          scopedSlots: {
            customRender: 'description'
          },
          customRender: createTime => formatTimestamp(createTime)
        },
        {
          title: '账号',
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'account'
        },
        {
          title: '渠道',
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'channelId'
        },
        {
          title: '上次登录ip',
          dataIndex: 'lastIP',
          scopedSlots: {
            customRender: 'description'
          }
        },
        {
          title: '等级',
          dataIndex: 'level',
          scopedSlots: {
            customRender: 'description'
          }
        },
        {
          title: '战斗力',
          dataIndex: 'power',
          scopedSlots: {
            customRender: 'description'
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
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        let s = this
        //console.log('param------->', s.queryParam)
        s.currentPage = parameter.pageNo
        let pageSize = parameter.pageSize
        if (!s.queryParam.accountId && !s.queryParam.playerName && !s.queryParam.playerId) {
          return new Promise(function(resolve, reject) {
            setTimeout(() => {
              resolve('ok')
            }, 100)
          }).then(res => {
            if (res == 'ok') {
              s.showpagi = false
              return {
                pageSize: 10,
                pageNo: 0,
                totalPage: 0,
                totalCount: 0,
                contents: []
              }
            }
          })
        }
        return getQueryLogoutLog(Object.assign(parameter, s.queryParam)).then(res => {
          //console.log('tablereturn=======>', res)
          if (!res || !res.contents || res.contents.length == 0) {
            return {
              pageSize: 10,
              pageNo: 0,
              totalPage: 0,
              totalCount: 0,
              contents: []
            }
          }
          s.showpagi = true
          // res.totalPage = Math.ceil(res.contents.length / pageSize)
          // res.totalCount = res.contents.length
          res.pageNo = s.currentPage
          res.contents.forEach(el => {
            el.account = el.account.split('.')[0]
            let z = Math.floor(el.level / 1000)
            let j = Math.floor(el.level % 1000)
            el.level = z > 0 ? z + '转' + j + '级' : j + '级'
          })
          return res
        })
      },
      selectedRowKeys: [],
      selectedRows: [],

      // custom table alert & rowSelection
      options: {
        alert: {
          show: true,
          clear: () => {
            this.selectedRowKeys = []
          }
        },
        rowSelection: {
          selectedRowKeys: this.selectedRowKeys,
          onChange: this.onSelectChange
        }
      },
      optionAlertShow: false
    }
  },
  created() {
    this.tableOption()
    let s = this
  },
  methods: {
    seach() {
      this.$refs.table.refresh(true)
    },
    serverChange(value) {
      this.queryParam.serverId = Number(value)
    },
    onTimeChange(date, dateString) {
      //console.log('日期------------>:', date, dateString);
      this.startTime = Number(dateString.replace(/-/g, ''))
    },
    callback(e) {
      //console.log(e);
    },
    onChange(e) {
      //console.log(e);
    },
    openModal() {
      this.$confirm()
    },
    onSearch() {},
    tableOption() {
      if (!this.optionAlertShow) {
        this.options = {
          alert: {
            show: true,
            clear: () => {
              this.selectedRowKeys = []
            }
          },
          rowSelection: {
            selectedRowKeys: this.selectedRowKeys,
            onChange: this.onSelectChange,
            getCheckboxProps: record => ({
              props: {
                disabled: record.no === 'No 2', // Column configuration not to be checked
                name: record.no
              }
            })
          }
        }
        this.optionAlertShow = true
      } else {
        this.options = {
          alert: false,
          rowSelection: null
        }
        this.optionAlertShow = false
      }
    },

    handleEdit(record) {
      this.$refs.modal.edit(record)
    },
    handleSub(record) {
      if (record.status !== 0) {
        this.$message.info(`${record.no} 订阅成功`)
      } else {
        this.$message.error(`${record.no} 订阅失败，规则已关闭`)
      }
    },
    handleOk() {
      this.$refs.table.refresh()
    },
    onSelectChange(selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    toggleAdvanced() {
      this.advanced = !this.advanced
    },

  },
  mounted: function() {
    let s = this
    this.$http.get('/roleGameServer/getMyServers').then(res => {
      s.serverList = res
      if(!res || res.length == 0) {
        return;
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
