<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="0">
          <a-col :md="6" :sm="24">
            <a-form-item
              label="起止日期"
              :labelCol="{lg: {span: 7}, sm: {span: 7}}"
              :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
            >
              <a-range-picker format="YYYY-MM-DD" @change="handleTimeChange" v-model="time" />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="12">
            <a-row>
              <a-col :md="6" style="line-height: 32px;">渠道:</a-col>
              <a-col :md="14">
                <a-select size="default" @change="qudaoChange">
                  <a-select-option v-for="i in channelIdList" :key="i.id" :value="i.id">{{i.name}}</a-select-option>
                </a-select>
              </a-col>
            </a-row>
          </a-col>
          <a-col :md="4" :sm="12">
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
    >
      <span slot="serial" slot-scope="text, record, index">{{ index + 1 }}</span>
      <span slot="status" slot-scope="text">
        <a-badge :status="text | statusTypeFilter" :text="text | statusFilter" />
      </span>
      <span slot="description" slot-scope="text">
        <ellipsis :length="20" tooltip>{{ text }}</ellipsis>
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
import moment from 'moment'
import { STable, Ellipsis, Modal } from '@/components'
import StepByStepModal from '../list/modules/StepByStepModal'
import CreateForm from '../list/modules/CreateForm'
import { getCostGoldRank, getServerInfo } from '@/api/user'
export default {
  name: 'CostGoldRank',
  components: {
    STable,
    Ellipsis,
    CreateForm,
    StepByStepModal
  },
  data() {
    return {
      currentPage: 0,
      time: [],
      serverList: [],
      channelIdList: [],
      tableData: [],
      timeArr: [],
      // 查询参数
      queryParam: {
        channelId: '',
        serverId: '',
        startTime: 0,
        endTime: 0
      },
      pagination: {
        position: 'bottom',
        pageSizeOptions: ['10', '20', '50','99999'],
        defaultPageSize: 10
      },
      // 表头
      columns: [
        {
          title: '玩家id',
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'playerId'
        },
        {
          title: '玩家名字',
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'playerName'
        },
        {
          title: '消费次数',
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'times'
        },
        {
          title: '消费仙晶',
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'amount'
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        let s = this
        s.currentPage = parameter.pageNo
        let pageSize = parameter.pageSize
        if (s.queryParam.startTime == 0 || s.queryParam.endTime == 0) {
          return new Promise(function(resolve, reject) {
            setTimeout(() => {
              resolve('ok')
            }, 100)
          }).then(res => {
            if (res == 'ok') {
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
        return getCostGoldRank(this.queryParam).then(res => {
          //console.log('tablereturn=======>', res)
          if (!res || s.queryParam.startTime == 0 || s.queryParam.endTime == 0) {
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
  },
  methods: {
    seach() {
      let s = this
      s.queryParam.startTime = s.timeArr[0]
      s.queryParam.endTime = s.timeArr[1]
      s.$refs.table.refresh()
    },
    qudaoChange(value) {
      this.queryParam.channelId = value
    },
    serverChange(value) {
      this.queryParam.serverId = value
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
    this.$http.get('/roleGameServer/getMyServers').then(res => {
      s.serverList = res
      if(!res || res.length == 0) {
        return;
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
        s.time = [s.$moment(startTime, 'YYYYMMDD'), s.$moment(endTime, 'YYYYMMDD')]
        s.timeArr[0] = startTime
        s.timeArr[1] = endTime
      })
    })
  }
}
</script>
