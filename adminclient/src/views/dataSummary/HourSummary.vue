<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="0">
          <a-col :md="5" :sm="24" style="margin-right: 18px;
          ">
            <a-form-item
              label="开始日期"
              :labelCol="{lg: {span: 7}, sm: {span: 7}}"
              :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
            >
              <a-date-picker @change="onTimeChange" v-model="time" />
            </a-form-item>
          </a-col>
          <!-- <a-col :md="3" :sm="12">
            <a-time-picker format="HH" @change="onHourTimeChange" />
          </a-col> -->
          <a-col :md="4" :sm="12">
            <a-row>
              <a-col :md="6" style="line-height: 32px;">渠道:</a-col>
              <a-col :md="14">
                <a-select size="default"  @change="qudaoChange" placeholder="默认全渠道">
                  <a-select-option v-for="i in channelIdList" :key="i.id" :value="i.id">{{i.name}}</a-select-option>
                </a-select>
              </a-col>
            </a-row>
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
            <span
              class="table-page-search-submitButtons"
            
            >
           
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
import { getHourSummary, getServerInfo } from '@/api/user'
const statusMap = {
  0: {
    status: 'default',
    text: '关闭'
  },
  1: {
    status: 'processing',
    text: '运行中'
  },
  2: {
    status: 'success',
    text: '已上线'
  },
  3: {
    status: 'error',
    text: '异常'
  }
}
const defaultCheckList = {
  zhanfu: ['测试服1', '测试服2', '测试服3'],
  qufu: ['区服1', '区服2', '区服3'],
  qudao: ['渠道1', '渠道2', '渠道3']
}
export default {
  name: 'HourSummary',
  components: {
    STable,
    Ellipsis,
    CreateForm,
    StepByStepModal
  },
  data() {
    return {
      time: null,
      serverList: [],
      channelIdList: [],
      // hourTime: '',
      tableData: [],
      dateTime: 0,
      defaultFilterData: defaultCheckList,
      filterBol: false,
      filterData: {
        zhanfu: ['测试服1', '测试服2', '测试服3'],
        qufu: ['区服1', '区服2', '区服3'],
        qudao: ['渠道1', '渠道2', '渠道3']
      },
      mdl: {},
      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParam: {
        dateTime: 0,
        channelIds: null,
        serverIds: null
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
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'dateTime'
        },
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
          title: '登录人次',
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'loginCount'
        },
        {
          title: '充值人次',
          dataIndex: 'chargeCount',
          scopedSlots: {
            customRender: 'description'
          }
        },
        {
          title: '充值人数',
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'chargePlayer'
        },
        {
          title: '充值金额',
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'amount',
          customRender: amount => amount / 100 + '元'
        },
        {
          title: '充值仙晶',
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'chargeGold'
        },
        {
          title: '消耗仙晶',
          dataIndex: 'consumeGold',
          scopedSlots: {
            customRender: 'description'
          },
          customRender: consumeGold => (consumeGold > 0 ? consumeGold : consumeGold * -1)
        },
        {
          title: '首充人数',
          dataIndex: 'firstCharge',
          scopedSlots: {
            customRender: 'description'
          }
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        let s = this
        s.currentPage = parameter.pageNo
        let pageSize = parameter.pageSize
        if (!s.queryParam.dateTime) {
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
          let arr = []
          s.channelIdList.forEach(el => {
            arr.push(el.id)
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
        return getHourSummary(this.queryParam).then(res => {
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
          console.log('单页数据条数：', pageSize)
          let obj = {}
          obj.pageNo = s.currentPage
          obj.pageSize = pageSize
          obj.totalPage = Math.ceil(res.length / pageSize)
          obj.totalCount = res.length
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
    let s = this
    // getServerInfo().then(res => {
    //   //console.log('测试端口', res);
    // })
  },
  methods: {
    seach() {
      let s = this
      s.queryParam.dateTime = s.dateTime
      s.$refs.table.refresh()
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
    onTimeChange(date, dateString) {
      //console.log('日期------------>:', date, dateString);
      this.dateTime = Number(dateString.replace(/-/g, ''))
    },
    // onHourTimeChange(date, dateString) {
    //   //console.log('小时------------>:', date, dateString);
    //   this.hourTime = dateString
    // },
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
      if (!res || res.length == 0) {
        return
      }
      getServerInfo({ serverId: res[0].id }).then(res => {
        if (!res) {
          let startTime = 20190101

          s.time = s.$moment(startTime.toString(), 'YYYYMMDD')
          s.dateTime = startTime
          return
        }
        let startTime = s.$moment(res.openTime).format('YYYYMMDD')

        s.time = s.$moment(startTime, 'YYYYMMDD')
        s.dateTime = startTime
      })
    })
  }
}
</script>
