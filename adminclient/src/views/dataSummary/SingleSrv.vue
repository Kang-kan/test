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
          <a-col :md="4" :sm="12">
            <a-row>
              <a-col :md="7" style="line-height: 32px;">区服:</a-col>
              <a-col :md="14">
                <a-select
                  size="default"
                  mode="multiple"
                  @change="serverChange"
                  placeholder="默认全服"
                >
                  <a-select-option v-for="i in serverList" :key="i.id" :value="i.id">{{i.name}}({{i.id}})</a-select-option>
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
import { getSingleSvrSummary, getServerInfo } from '@/api/user'
export default {
  name: 'SingleSrv',
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
      timeArr: [],
      channelIdList: [],
      serverList: [],
      // 查询参数
      queryParam: {
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
          title: '日期',
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'daily'
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
          title: '开服天数',
          dataIndex: 'openDay',
          scopedSlots: {
            customRender: 'description'
          }
        },
        {
          title: '充值金额',
          dataIndex: 'amount',
          scopedSlots: {
            customRender: 'description'
          },
          customRender: amount => (amount / 100)+ '元'
        },
        {
          title: '最高在线',
          dataIndex: 'maxOnline',
          scopedSlots: {
            customRender: 'description'
          }
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        let s = this
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
        if (!s.queryParam.serverIds || s.queryParam.serverIds == encodeURIComponent(JSON.stringify([]))) {
          let arr = []
          s.serverList.forEach(el => {
            arr.push(el.id)
          })
          s.queryParam.serverIds = encodeURIComponent(JSON.stringify(arr))
        }
        return getSingleSvrSummary(Object.assign(parameter, s.queryParam)).then(res => {
          //console.log('tablereturn=======>',res)
          if (!res || s.queryParam.startTime == 0 || s.queryParam.endTime == 0) {
            return {
              pageSize: 10,
              pageNo: 0,
              totalPage: 0,
              totalCount: 0,
              contents: []
            }
          }
          // res.totalPage = Math.ceil(res.contents.length / res.pageSize);
          // res.totalCount = res.contents.length;
          res.pageNo = s.currentPage
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
    let s = this
    s.tableOption()
    // getServerInfo({ serverId: res[0].id }).then(res => {
    //   if (!res) {
    //     let startTime = 20190101
    //     let endTime = s.$moment().format('YYYYMMDD')
    //     s.time = [s.$moment(startTime.toString(), 'YYYYMMDD'), s.$moment(endTime, 'YYYYMMDD')]
    //     s.timeArr[0] = startTime
    //     s.timeArr[1] = endTime
    //     return
    //   }
    //   let startTime = s.$moment(res.openTime).format('YYYYMMDD')
    //   let endTime = s.$moment().format('YYYYMMDD')
    //   s.time = [s.$moment(startTime, 'YYYYMMDD'), s.$moment(endTime, 'YYYYMMDD')]
    //   s.timeArr[0] = startTime
    //   s.timeArr[1] = endTime
    // })
  },
  methods: {
    seach() {
      this.queryParam.startTime = this.timeArr[0]
      this.queryParam.endTime = this.timeArr[1]
      this.$refs.table.refresh(true)
    },
    qudaoChange(value) {
      //console.log('渠道', value)
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
      //console.log('日期',this.timeArr);
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
      };
    })
  }
}
</script>
