<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="0">
          <a-col :md="5" :sm="24">
            <a-form-item
              label="日期"
              :labelCol="{lg: {span: 7}, sm: {span: 7}}"
              :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
            >
              <a-date-picker format="YYYY-MM-DD" @change="handleTimeChange" v-model="time" />
            </a-form-item>
          </a-col>
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
import { formatTimestamp } from '@/utils/util'
import moment from 'moment'
import { STable, Ellipsis, Modal } from '@/components'
import StepByStepModal from '../list/modules/StepByStepModal'
import CreateForm from '../list/modules/CreateForm'
import { getLevelLossStatis, getServerInfo } from '@/api/user'
export default {
  name: 'LevelLoss',
  components: {
    STable,
    Ellipsis,
    CreateForm,
    StepByStepModal
  },
  data() {
    return {
      currentPage: 0,
      time: null,
      serverList: [],
      timeArr: [],
      // 查询参数
      queryParam: {
        serverId: '',
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
          title: '服务器',
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
          title: '等级',
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'level'
        },
        {
          title: '玩家总人数',
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'count'
        },
        {
          title: '当前等级玩家人数',
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'levelCount'
        },
        {
          title: '当前等级玩家分布比率',
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'levelCountp'
        },
        {
          title: '24小时流失人数',
          dataIndex: 'loss24',
          scopedSlots: {
            customRender: 'description'
          }
        },
        {
          title: '24小时流失率',
          dataIndex: 'loss24p',
          scopedSlots: {
            customRender: 'description'
          }
        },
        {
          title: '3天流失人数',
          dataIndex: 'loss72',
          scopedSlots: {
            customRender: 'description'
          }
        },
        {
          title: '3天流失率',
          dataIndex: 'loss72p',
          scopedSlots: {
            customRender: 'description'
          }
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        let s = this
        //console.log('param------->', s.queryParam);
        s.currentPage = parameter.pageNo
        let pageSize = parameter.pageSize
        if (s.queryParam.daily == 0 || !s.queryParam.serverId) {
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
        parameter = {}
        return getLevelLossStatis(Object.assign(parameter, s.queryParam)).then(res => {
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
          let obj = {}
          res.sort((a, b) => {
            return (a.level % 1000) - (b.level % 1000)
          })
          res.forEach(el => {
            el.level > 999
              ? (el.level = Math.floor(el.level / 1000) + '转' + (el.level % 1000) + '级')
              : (el.level = parseInt(el.level) + '级');
            el.levelCountp = (el.levelCount * 100 / el.count).toFixed(2) + '%'
            el.loss24p = (el.loss24 * 100 / el.levelCount).toFixed(2) + '%'
            el.loss72p = (el.loss72 * 100 / el.levelCount).toFixed(2) + '%'
          })
          //console.log('pagination==========>', s.pagination);
          Object.assign(obj, {
            contents: res,
            pageSize: pageSize,
            pageNo: s.currentPage,
            totalPage: Math.ceil(res.length / pageSize),
            totalCount: res.length
          })
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
  },
  methods: {
    seach() {
      let s = this
      s.$refs.table.refresh(true)
    },
    serverChange(value) {
      this.queryParam.serverId = Number(value)
    },
    handleTimeChange(value, timeString) {
      this.queryParam.daily = Number(timeString.replace(/-/g, ''))
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
        // if (!res) {
        //   let startTime = 20190101
        //   let endTime = s.$moment().format('YYYYMMDD')
        //   s.time = [s.$moment(startTime.toString(), 'YYYYMMDD'), s.$moment(endTime, 'YYYYMMDD')]
        //   s.timeArr[0] = startTime
        //   s.timeArr[1] = endTime
        //   return
        // }
        // let startTime = s.$moment(res.openTime).format('YYYYMMDD')
        // let endTime = s.$moment().format('YYYYMMDD')
        // s.time = s.$moment(startTime, 'YYYYMMDD'), s.$moment(endTime, 'YYYYMMDD')
      })
    })
  }
}
</script>
