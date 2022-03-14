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
            <a-input placeholder="请输入邮件标题" v-model="queryParam.title" />
          </a-col>
          <a-col :md="2" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" style="margin-left: 8px" @click="seach(2)">标题查找邮件</a-button>
            </span>
          </a-col>
        </a-row>
        <a-row :gutter="0">
          <a-col :md="6" :sm="24">
            <a-form-item
              label="起止日期"
              :labelCol="{lg: {span: 7}, sm: {span: 7}}"
              :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
              style="margin-right: 18px;"
            >
              <a-range-picker format="YYYY-MM-DD" @change="handleTimeChange" v-model="time" />
            </a-form-item>
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
              <a-button type="primary" style="margin-left: 8px" @click="seach(1)">查询玩家邮件</a-button>
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
      :scroll="{x: 2000}"
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
  import xmlObj from '@/config/xmlConfig'
  import { formatTimestamp } from '@/utils/util'
  import moment from 'moment'
  import { STable, Ellipsis, Modal } from '@/components'
  import StepByStepModal from '../list/modules/StepByStepModal'
  import CreateForm from '../list/modules/CreateForm'
  import { getQueryMailLogByTitle, getQueryMailLog, getServerInfo } from '@/api/user'
  export default {
    name: 'QueryPlayerMailLog',
    components: {
      STable,
      Ellipsis,
      CreateForm,
      StepByStepModal
    },
    data() {
      return {
        description:
          '标题查找必须服务器、邮件标题信息，不需玩家信息和时间。玩家查找必须服务器、时间、玩家信息，不输邮件标题则默认查询所有标题玩家邮件。',
        searchType: 1,
        showpagi: false,
        currentPage: 0,
        time: null,
        timeArr: [],
        serverList: [],
        tableData: {},
        // 查询参数
        queryParam: {
          serverId: '',
          accountId: '',
          playerId: '',
          playerName: '',
          title: ''
        },
        pagination: {
          position: 'bottom',
          pageSizeOptions: ['10', '20', '50', '99999'],
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
            dataIndex: 'account',
            customRender: account => {
              return account.split('.')[0]
            }
          },
          {
            title: '附件',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'bonus'
          },
          {
            title: '渠道id',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'channelId'
          },
          {
            title: '邮件配置表id',
            dataIndex: 'configId',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '邮件创建时间',
            dataIndex: 'createTime',
            scopedSlots: {
              customRender: 'description'
            },
            customRender: createTime => formatTimestamp(createTime)
          },
          {
            title: '提取状态',
            dataIndex: 'isGet',
            scopedSlots: {
              customRender: 'description'
            },
            customRender: isGet => (isGet == 0 ? '未领取' : '已领取')
          },
          {
            title: '等级',
            dataIndex: 'level',
            scopedSlots: {
              customRender: 'description'
            },
            customRender: level => {
              return Math.floor(level / 1000) > 0
                ? Math.floor(level / 1000) + '转' + (level % 1000) + '级'
                : (level % 1000) + '级'
            }
          },
          {
            title: '邮件id',
            dataIndex: 'mailId',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '内容',
            dataIndex: 'message',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '操作',
            dataIndex: 'opValue',
            scopedSlots: {
              customRender: 'description'
            },
            customRender: opValue => {
              let str = ''
              switch (opValue) {
                case -1:
                  str = '未读取'
                  break
                case 0:
                  str = '领取'
                  break
                case 1:
                  str = '手动删除'
                  break
                case 2:
                  str = '系统删除'
                  break
                case 3:
                  str = '读取'
                  break
              }
              return str
            }
          },
          // {
          //   title: '平台',
          //   dataIndex: 'platName',
          //   scopedSlots: {
          //     customRender: 'description'
          //   }
          // },
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
            title: '是否已读',
            dataIndex: 'readStatus',
            scopedSlots: {
              customRender: 'description'
            },
            customRender: readStatus => (readStatus == 0 ? '未读' : '已读')
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
            title: '标题',
            dataIndex: 'title',
            scopedSlots: {
              customRender: 'description'
            }
          }
        ],
        // 加载数据方法 必须为 Promise 对象
        loadData: parameter => {
          let s = this
          //console.log('param------->', s.queryParam)
          s.currentPage = parameter.pageNo
          let pageSize = parameter.pageSize
          if (s.searchType == 1) {
            if (
              (!s.queryParam.accountId && !s.queryParam.playerName && !s.queryParam.playerId) ||
              !s.queryParam.startTime ||
              !s.queryParam.endTime ||
              !s.queryParam.serverId
            ) {
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
            return getQueryMailLog(Object.assign(parameter, s.queryParam)).then(res => {
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
              res.contents.forEach(async function(el) {
                let bonusStr = ''
                let arr = JSON.parse(el.bonus)
                if (Array.isArray(arr)) {
                  for (let i = 0; i < arr.length; i++) {
                    bonusStr += (await xmlObj.getBonus(arr[i])) + ','
                  }
                } else {
                  bonusStr = await xmlObj.getBonus(arr)
                }
                el.bonus = bonusStr
              })
              return res
            })
          } else if (s.searchType == 2) {
            if (!s.queryParam.title || !s.queryParam.serverId) {
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
            return getQueryMailLogByTitle(Object.assign(parameter, s.queryParam)).then(res => {
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
              res.contents.forEach(async function(el) {
                let bonusStr = ''
                let arr = JSON.parse(el.bonus)
                if (Array.isArray(arr)) {
                  for (let i = 0; i < arr.length; i++) {
                    bonusStr += (await xmlObj.getBonus(arr[i])) + ','
                  }
                } else {
                  bonusStr = await xmlObj.getBonus(arr)
                }
                el.bonus = bonusStr
              })
              return res
            })
          }
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
      seach(type) {
        let s = this
        s.searchType = type
        s.queryParam.startTime = s.timeArr[0]
        s.queryParam.endTime = s.timeArr[1]
        s.$refs.table.refresh(true)
      },
      serverChange(value) {
        this.queryParam.serverId = Number(value)
      },
      handleTimeChange(value, timeString) {
        this.timeArr[0] = Number(timeString[0].replace(/-/g, ''))
        this.timeArr[1] = Number(timeString[1].replace(/-/g, ''))
        //console.log('日期', this.timeArr);
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
        if (!res || res.length == 0) {
          return
        }
        getServerInfo({ serverId: res[0].id }).then(res => {
          if (!res) {
            let startTime = 20190101
            let endTime = s.$moment().format('YYYYMMDD')
            s.time = [s.$moment(startTime.toString(), 'YYYYMMDD'), s.$moment(endTime, 'YYYYMMDD')]
            s.timeArr[0] = startTime
            s.timeArr[1] = endTime
            return
          }
          let startTime = s.$moment(res.openTime).format('YYYYMMDD')
          let endTime = s.$moment().format('YYYYMMDD')
          console.log('endTime: ', endTime)
          s.time = [s.$moment(startTime, 'YYYYMMDD'), s.$moment(endTime, 'YYYYMMDD')]
          s.timeArr[0] = startTime
          s.timeArr[1] = endTime
        })
      })
    }
  }
</script>
