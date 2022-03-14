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
              style="margin-right: 18px;"
            >
              <a-range-picker format="YYYY-MM-DD" @change="handleTimeChange" v-model="time" />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="12">
            <a-row>
              <a-col :md="6" style="line-height: 32px;">渠道:</a-col>
              <a-col :md="14">
                <a-select size="default" @change="qudaoChange" placeholder="默认全渠道">
                  <a-select-option v-for="i in channelIdList" :key="i.id" :value="i.id">{{i.name}}</a-select-option>
                </a-select>
              </a-col>
            </a-row>
          </a-col>
          <a-col :md="4" :sm="12">
            <a-row>
              <a-col :md="7" style="line-height: 32px;">区服:</a-col>
              <a-col :md="14">
                <a-select size="default" mode="multiple" @change="serverChange" placeholder="默认全服">
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
      style="word-break: normal;white-space: nowrap;"
      :scroll="{x:2500}"
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
import { getDailySummary, getServerInfo } from '@/api/user'

export default {
  name: 'DailySummary',
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
      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParam: {
        channelIds: null,
        serverIds: null,
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
          title: '渠道id',
          width: 150,
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
          title: '日期',
          width: 150,
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'daily'
        },
        {
          title: '创建角色数',
          width: 150,
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'createPlayer'
        },
        {
          title: '登录人数',
          width: 150,
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'loginPlayer'
        },
        {
          title: '最高在线',
          width: 150,
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'maxOnline'
        },
        {
          title: '最低在线',
          width: 150,
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'minOnline'
        },
        {
          title: '平均在线',
          width: 150,
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'avgOnline'
        },
        {
          title: '充值人次',
          width: 150,
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'chargeCount'
        },
        {
          title: '充值人数',
          width: 150,
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'chargePlayer'
        },
        {
          title: '充值金额',
          width: 150,
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'chargeAmount',
          customRender: chargeAmount => (chargeAmount / 100)+ '元'
        },
        {
          title: 'ARPU',
          width: 150,
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'arpu',
          customRender: arpu => (arpu / 100)+ '元'
        },
        {
          title: '当日付费渗透率',
          width: 150,
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'chargeRate'
        },
        {
          title: '新增用户充值点击次数',
          width: 150,
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'newChargeClickCount'
        },
        {
          title: '新增用户充值点击人数',
          width: 150,
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'newChargeClickPlayer'
        },
        {
          title: '新增用户点击付费率',
          width: 150,
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'newChargeClickRate'
        },
        {
          title: '新增用户付费人数',
          width: 150,
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'newChargePlayer'
        },
        {
          title: '新增用户充值金额',
          width: 150,
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'newChargeAmount',
          customRender: newChargeAmount => (newChargeAmount / 100)+ '元'
        },
        {
          title: '新增付费率',
          width: 150,
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'newChargeRate'
        },
        {
          title: '新增用户ARPU',
          width: 150,
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'newArpu',
          customRender: newArpu => (newArpu / 100)+ '元'
        },
        {
          title: '老用户付费人数',
          width: 150,
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'oldChargePlayer'
        },
        {
          title: '老用户充值金额',
          width: 150,
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'oldChargeAmount',
          customRender: oldChargeAmount => (oldChargeAmount / 100)+ '元'
        },
        {
          title: '老用户付费率',
          width: 150,
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'oldChargeRate'
        },
        {
          title: '老用户ARPU',
          width: 150,
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'oldArpu',
          customRender: oldArpu => (oldArpu / 100)+ '元'
        }
      ],
      selectedRowKeys: [],
      selectedRows: [],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        let s = this
        //console.log('param------->', s.queryParam)
        s.currentPage = parameter.pageNo
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
        if(!s.queryParam.channelIds || s.queryParam.channelIds == encodeURIComponent(JSON.stringify([]))) {
          let arr = [];
          s.channelIdList.forEach(el => {
            arr.push(el.id);
          })
          s.queryParam.channelIds = encodeURIComponent(JSON.stringify(arr))
        }
        if(!s.queryParam.serverIds || s.queryParam.serverIds == encodeURIComponent(JSON.stringify([]))) {
          let arr = [];
          s.serverList.forEach(el => {
            arr.push(el.id);
          })
          s.queryParam.serverIds = encodeURIComponent(JSON.stringify(arr))
        }
        return getDailySummary(Object.assign(parameter, s.queryParam)).then(res => {
          //console.log('tablereturn=======>', res)
          if (!res || !res.contents) {
            return {
              pageSize: 10,
              pageNo: 0,
              totalPage: 0,
              totalCount: 0,
              contents: []
            }
          }
          // res.totalPage = Math.ceil(res.contents.length / res.pageSize)
          // res.totalCount = res.contents.length
          res.pageNo = s.currentPage
          return res
        })
      },
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
      let s = this
      s.queryParam.startTime = s.timeArr[0]
      s.queryParam.endTime = s.timeArr[1]
      s.$refs.table.refresh(true)
    },
    qudaoChange(value) {
      let arr
      if (Array.isArray(value)) {
        arr = value
      } else {
        arr = value.split(',')
      }
      this.queryParam.channelIds = encodeURIComponent(JSON.stringify(arr))
    },
    serverChange(value) {
      let arr = []
      if (Array.isArray(value)) {
        arr = value
      } else {
        arr = value.split(',')
      }
      this.queryParam.serverIds = encodeURIComponent(JSON.stringify(arr))
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
        console.log('endTime: ', endTime)
        s.time = [s.$moment(startTime, 'YYYYMMDD'), s.$moment(endTime, 'YYYYMMDD')]
        s.timeArr[0] = startTime
        s.timeArr[1] = endTime
      })
    })
  }
}
</script>
