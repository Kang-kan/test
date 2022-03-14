<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="0">
          <a-col :md="6" :sm="24" style="margin-right: 18px;">
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
                <a-select size="default" @change="qudaoChange" playerholder="默认全渠道">
                  <a-select-option key="-1" value="全渠道">全渠道</a-select-option>
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
  import { getChargeSingleDistV1, getChargeSingleDistsV1, getServerInfo } from '@/api/user'
  export default {
    name: 'ChargeSingleDist',
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
          pageSizeOptions: ['10', '20', '50', '99999'],
          defaultPageSize: 10
        },
        // 表头
        columns: [
          {
            title: '渠道',
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
              return name || channelId
            }
          },
          {
            title: '充值次数',
            dataIndex: 'count',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '单笔充值金额',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'amount',
            customRender: amount => amount / 100 + '元'
          },
          {
            title: '次数占比',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'countp'
          },
          {
            title: '金额占比',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'amountp'
          },
          {
            title: '总充值金额',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'totalAmount',
            customRender: totalAmount => totalAmount / 100 + '元'
          },
          {
            title: '总充值次数',
            dataIndex: 'totalCount',
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
          //console.log('param------->', s.queryParam)
          if (s.queryParam.channelId) {
            return getChargeSingleDistV1(Object.assign(parameter, s.queryParam)).then(res => {
              //console.log('tablereturn=======>', res)
              if (!res) {
                return {
                  pageSize: 10,
                  pageNo: 0,
                  totalPage: 0,
                  totalCount: 0,
                  contents: []
                }
              }
              res.forEach(el => {
                el['amountp'] = (((el.amount * el.count) / el.totalAmount) * 100).toFixed(3) + '%'
                el['countp'] = ((el.count / el.totalCount) * 100).toFixed(3) + '%'
              })
              // res.totalPage = Math.ceil(res.contents.length / res.pageSize)
              // res.totalCount = res.contents.length
              let obj = {}
              obj.pageNo = s.currentPage
              obj.pagesize = pageSize
              obj.totalCount = res.length
              obj.totalPage = Math.ceil(res.length / pageSize)
              obj.contents = res
              return obj
            })
          } else {
            return getChargeSingleDistsV1(Object.assign(parameter, s.queryParam)).then(res => {
              //console.log('tablereturn=======>', res)
              if (!res) {
                return {
                  pageSize: 10,
                  pageNo: 0,
                  totalPage: 0,
                  totalCount: 0,
                  contents: []
                }
              }
              res.forEach(el => {
                el['amountp'] = (((el.amount * el.count) / el.totalAmount) * 100).toFixed(3) + '%'
                el['countp'] = ((el.count / el.totalCount) * 100).toFixed(3) + '%'
              })
              // res.totalPage = Math.ceil(res.contents.length / res.pageSize)
              // res.totalCount = res.contents.length
              let obj = {}
              obj.pageNo = s.currentPage
              obj.pagesize = pageSize
              obj.totalCount = res.length
              obj.totalPage = Math.ceil(res.length / pageSize)
              obj.contents = res
              return obj
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
    },
    methods: {
      seach() {
        let s = this
        s.queryParam.startTime = s.timeArr[0]
        s.queryParam.endTime = s.timeArr[1]
        this.$refs.table.refresh(true)
      },
      qudaoChange(value) {
        if (value.indexOf('全渠道') > -1) {
          this.queryParam.channelId = null
        } else {
          this.queryParam.channelId = value
        }
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
