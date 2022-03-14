<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="0">
          <a-col :md="6" :sm="24">
            <a-form-item
              label="开服时间段"
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
                <a-select size="default" mode="multiple" placeholder="默认全渠道"  @change="qudaoChange">
                  <a-select-option v-for="i in channelIdList" :key="i.id" :value="i.id">
                    {{i.name}}
                  </a-select-option>
                </a-select>
              </a-col>
            </a-row>
          </a-col>
          <a-col :md="4" :sm="12">
            <a-row>
              <a-col :md="7" style="line-height: 32px;">区服:</a-col>
              <a-col :md="14">
                <a-select size="default" mode="multiple" placeholder="默认全服"  @change="serverChange">
                  <a-select-option v-for="i in serverList" :key="i.id" :value="i.id">
                    {{i.name}}({{i.id}})
                  </a-select-option>
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
      :scroll="{x: 2000}"
    >
      <span slot="serial" slot-scope="text, record, index">{{ index + 1 }}</span>
      <span slot="status" slot-scope="text">
        <a-badge :status="text | statusTypeFilter" :text="text | statusFilter" />
      </span>
      <span slot="description" slot-scope="text">
        <ellipsis :length="30" tooltip>{{ text }}</ellipsis>
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
import { getLoginRetain, getServerInfo } from '@/api/user'
export default {
  name: 'LoginRetain',
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
          dataIndex: 'daily',
          scopedSlots: {
            customRender: 'description'
          }
        },
        {
          title: '渠道',
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
          title: '区服',
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
          title: '当天注册',
          dataIndex: 'createCount',
          scopedSlots: {
            customRender: 'description'
          }
        },
        {
          title: '2天',
          dataIndex: 'day2',
          scopedSlots: {
            customRender: 'description'
          }
        },
        {
          title: '3天',
          dataIndex: 'day3',
          scopedSlots: {
            customRender: 'description'
          }
        },
        {
          title: '4天',
          dataIndex: 'day4',
          scopedSlots: {
            customRender: 'description'
          }
        },
        {
          title: '5天',
          dataIndex: 'day5',
          scopedSlots: {
            customRender: 'description'
          }
        },
        {
          title: '6天',
          dataIndex: 'day6',
          scopedSlots: {
            customRender: 'description'
          }
        },
        {
          title: '7天',
          dataIndex: 'day7',
          scopedSlots: {
            customRender: 'description'
          }
        },
        {
          title: '8天',
          dataIndex: 'day8',
          scopedSlots: {
            customRender: 'description'
          }
        },
        {
          title: '9天',
          dataIndex: 'day9',
          scopedSlots: {
            customRender: 'description'
          }
        },
        {
          title: '10天',
          dataIndex: 'day10',
          scopedSlots: {
            customRender: 'description'
          }
        },
        {
          title: '11天',
          dataIndex: 'day11',
          scopedSlots: {
            customRender: 'description'
          }
        },
        {
          title: '12天',
          dataIndex: 'day12',
          scopedSlots: {
            customRender: 'description'
          }
        },
        {
          title: '13天',
          dataIndex: 'day13',
          scopedSlots: {
            customRender: 'description'
          }
        },
        {
          title: '14天',
          dataIndex: 'day14',
          scopedSlots: {
            customRender: 'description'
          }
        },
        {
          title: '15天',
          dataIndex: 'day15',
          scopedSlots: {
            customRender: 'description'
          }
        },
        {
          title: '30天',
          dataIndex: 'day30',
          scopedSlots: {
            customRender: 'description'
          }
        },
        {
          title: '60天',
          dataIndex: 'day60',
          scopedSlots: {
            customRender: 'description'
          }
        },
        {
          title: '90天',
          dataIndex: 'day90',
          scopedSlots: {
            customRender: 'description'
          }
        },
        // {
        //   title: '留存',
        //   scopedSlots: {
        //     customRender: 'description'
        //   },
        //   children: [
        //     {
        //       title: '天数',
        //       dataIndex: 'days',
        //       scopedSlots: {
        //         customRender: 'description'
        //       }
        //     },
        //     {
        //       title: '登录人数',
        //       dataIndex: 'loginCount',
        //       scopedSlots: {
        //         customRender: 'description'
        //       }
        //     }
        //   ]
        // }
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
        if (!s.queryParam.channelIds || (s.queryParam.channelIds === encodeURIComponent(JSON.stringify([])))) {
          let arr = []
          s.channelIdList.forEach(el => {
            arr.push(el.id)
          })
          s.queryParam.channelIds = encodeURIComponent(JSON.stringify(arr))
        }
        if (!s.queryParam.serverIds || (s.queryParam.serverIds == encodeURIComponent(JSON.stringify([])))) {
          let arr = []
          s.serverList.forEach(el => {
            arr.push(el.id)
          })
          s.queryParam.serverIds = encodeURIComponent(JSON.stringify(arr))
        }
        console.log('channel',s.queryParam.channelIds)
        return getLoginRetain(Object.assign(parameter,s.queryParam)).then(res => {
          if (!res) {
            return
          }
          let arr = []
          res.contents.forEach(el => {
            let obj = {}
            Object.assign(obj, {
              channelId: el.channelId,
              createCount: el.createCount,
              daily: el.daily,
              serverId: el.serverId
            })
            el.activeLoss.forEach(value => {
              let tempObj = {}
              tempObj['day' + value.days] = value.loginCount + `(${(value.loginCount * 100 / el.createCount).toFixed(2)}%)`
              Object.assign(obj,tempObj)
            })
            arr.push(obj)
          })
          let obj = {}
          obj.totalPage = res.totalPage
          obj.totalCount = res.totalCount
          obj.pageNo = res.pageNo
          obj.pageSize = res.pageSize
          obj.contents = arr
          console.log('tablereturn================>', obj)
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
    let s = this
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
      let s = this
      s.queryParam.startTime = s.timeArr[0]
      s.queryParam.endTime = s.timeArr[1]
      s.$refs.table.refresh()
    },
    qudaoChange(value) {
      this.queryParam.channelIds = encodeURIComponent(JSON.stringify(value))
    },
    serverChange(value) {
      this.queryParam.serverIds = encodeURIComponent(JSON.stringify(value))
    },
    handleTimeChange(value, timeString) {
      this.timeArr[0] = Number(timeString[0].replace(/-/g, ''))
      this.timeArr[1] = Number(timeString[1].replace(/-/g, ''))
      console.log('日期', this.timeArr)
    },
    callback(e) {
      console.log(e)
    },
    onChange(e) {
      console.log(e)
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
      if (!res || res.length == 0) {
        return
      }
    })
  }
}
</script>
