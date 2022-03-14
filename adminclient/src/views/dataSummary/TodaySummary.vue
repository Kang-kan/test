<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="0">
          <a-col :md="6" :sm="12">
            <a-row>
              <a-col :md="6" style="line-height: 32px;">渠道:</a-col>
              <a-col :md="14">
                <a-select size="default" mode="multiple" placeholder="默认全渠道" @change="qudaoChange">
                  <a-select-option v-for="i in channelIdList" :key="i.id" :value="i.id">{{i.name}}</a-select-option>
                </a-select>
              </a-col>
            </a-row>
          </a-col>
          <a-col :md="4" :sm="12">
            <a-row>
              <a-col :md="7" style="line-height: 32px;">区服:</a-col>
              <a-col :md="14">
                <a-select size="default" mode="multiple" placeholder="默认全服" @change="serverChange">
                  <a-select-option
                    v-for="i in serverList"
                    :key="i.id"
                    :value="i.id"
                  >{{i.name}}({{i.id}})</a-select-option>
                </a-select>
              </a-col>
            </a-row>
          </a-col>
          <a-col :md="2" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" style="margin-left: 8px" @click="seach">查询</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>

    <a-table
      showSizeChanger
      ref="table"
      size="default"
      rowKey="monthtableid"
      :columns="columns"
      :dataSource="tableData"
      style="word-break: normal;"
    >
      <span slot="serial" slot-scope="text, record, index">{{ index + 1 }}</span>
      <span slot="status" slot-scope="text">
        <a-badge :status="text | statusTypeFilter" :text="text | statusFilter" />
      </span>
      <span slot="description" slot-scope="text">
        <ellipsis :length="10" tooltip>{{ text }}</ellipsis>
      </span>

      <span slot="action" slot-scope="text, record">
        <template>
          <a @click="handleEdit(record)">配置</a>
          <a-divider type="vertical" />
          <a @click="handleSub(record)">订阅报警</a>
        </template>
      </span>
    </a-table>
    <create-form ref="createModal" @ok="handleOk" />
    <step-by-step-modal ref="modal" @ok="handleOk" />
  </a-card>
</template>

<script>
  import moment from 'moment'
  import { STable, Ellipsis, Modal } from '@/components'
  import StepByStepModal from '../list/modules/StepByStepModal'
  import CreateForm from '../list/modules/CreateForm'
  import { getTodaySummary, getServerInfo } from '@/api/user'
  export default {
    name: 'TodaySummary',
    components: {
      STable,
      Ellipsis,
      CreateForm,
      StepByStepModal
    },
    data() {
      return {
        serverList: [],
        channelIdList: [],
        tableData: [],
        dateTime: 0,
        // 查询参数
        queryParam: {
          channelIds: null,
          serverIds: null
        },
        pagination: {
          position: 'bottom',
          pageSizeOptions: ['10', '20', '50', '99999'],
          defaultPageSize: 10
        },
        // 表头
        columns: [
          {
            title: '渠道id',
            scopedSlots: {
              customRender: 'description'
            },
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
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'serverId',
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
            title: '当前在线',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'onlineCount'
          },
          {
            title: '新增账号数',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'createAccount'
          },
          {
            title: '创建角色数',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'createPlayer'
          },
          {
            title: '历史创建角色数',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'pastCreatePlayer'
          },
          {
            title: '当日充值人次',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'todayChargeCount'
          },
          {
            title: '当日充值人数',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'todayChargePlayer'
          },
          {
            title: '当日首充人数',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'todayFirstCharge'
          },
          {
            title: '当日充值金额',
            dataIndex: 'todayChargeAmount',
            scopedSlots: {
              customRender: 'description'
            },
            customRender: todayChargeAmount => todayChargeAmount / 100 + '元'
          },
          {
            title: '当日ARPU',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'todayArpu',
            customRender: pastArpu => pastArpu / 100 + '元'
          },
          {
            title: '历史充值人数',
            dataIndex: 'pastChargePlayer',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '历史充值人次',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'pastChargeCount'
          },
          {
            title: '历史充值金额',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'pastChargeAmount',
            customRender: pastChargeAmount => pastChargeAmount / 100 + '元'
          },
          {
            title: '历史ARPU',
            dataIndex: 'pastArpu',
            scopedSlots: {
              customRender: 'description'
            },
            customRender: pastArpu => pastArpu / 100 + '元'
          }
        ],
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

      // getServerInfo().then(res => {
      //   //console.log('测试端口', res);
      // })
    },
    methods: {
      seach() {
        let s = this
        console.log('queryparam', s.queryParam)
        if (!s.queryParam.channelIds || s.queryParam.channelIds == encodeURIComponent(JSON.stringify([]))) {
          let arr = []
          s.channelIdList.forEach(el => {
            arr.push(el.id)
          })
          s.queryParam.channelIds = encodeURIComponent(JSON.stringify(arr))
        }
        if (!s.queryParam.serverIds || s.queryParam.serverIds == encodeURIComponent(JSON.stringify([]))) {
          let arr = []
          s.serverList.forEach(el => {
            arr.push(el.id)
          })
          s.queryParam.serverIds = encodeURIComponent(JSON.stringify(arr))
        }
        getTodaySummary(s.queryParam).then(res => {
          //console.log('tablereturn=======>', res)
          if (!res) {
            s.tableData = []
            return
          }
          s.tableData = res
          //console.log(s.tableData);
        })
      },
      qudaoChange(value) {
        let arr
        if (Array.isArray(value)) {
          arr = value
        } else {
          arr = value.split(',')
        }
        console.log(arr)
        this.queryParam.channelIds = encodeURIComponent(JSON.stringify(arr))
      },
      serverChange(value) {
        let arr = []
        if (Array.isArray(value)) {
          arr = value
        } else {
          arr = value.split(',')
        }
        console.log(arr)
        this.queryParam.serverIds = encodeURIComponent(JSON.stringify(arr))
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
      }
    },
    mounted: function() {
      let s = this
      s.$ajax.get('../gameVar.json').then(res => {
        if (!res || !res.data) {
          return
        }
        s.channelIdList = res.data.channelIdList
      })
      let serverstr = localStorage.getItem('serverList')
      this.$http.get('/roleGameServer/getMyServers').then(res => {
        s.serverList = res
        if (!res || res.length == 0) {
          return
        }
      })
    }
  }
</script>
