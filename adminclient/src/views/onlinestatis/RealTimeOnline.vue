<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="0">
          <a-col :md="6" :sm="24">
            <a-form-item
              label="开始日期"
              :labelCol="{lg: {span: 7}, sm: {span: 7}}"
              :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
              style="margin-right: 18px;"
            >
              <a-date-picker @change="onTimeChange" v-model="time" />
            </a-form-item>
          </a-col>
          <a-col :md="5" :sm="12">
            <a-row>
              <a-col :md="7" style="line-height: 32px;">区服:</a-col>
              <a-col :md="14">
                <a-select size="default" @change="serverChange" mode="multiple" placeholder="默认全服">
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
          <!-- <a-col :md="2" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" style="margin-left: 8px" @click="output">导出本页数据</a-button>
            </span>
          </a-col> -->
        </a-row>
      </a-form>
    </div>
    <div>
    <v-chart :forceFit="true" :height="height" :data="chartdata" :scale="scale">
      <v-tooltip />
      <v-axis />
      <v-legend />
      <v-smooth-line position="hour*count" color="server" shape="smooth" />
      <v-point position="hour*count" color="server" shape="circle" />
    </v-chart>
  </div>
  </a-card>
</template>

<script>
import DataSet from '@antv/data-set'
import { formatTimestamp } from '@/utils/util'
import moment from 'moment'
import { STable, Ellipsis, Modal } from '@/components'
import StepByStepModal from '../list/modules/StepByStepModal'
import CreateForm from '../list/modules/CreateForm'
import { getRealTimeOnline, getServerInfo } from '@/api/user'
export default {
  name: 'RealTimeOnline',
  components: {
    STable,
    Ellipsis,
    CreateForm,
    StepByStepModal
  },
  data() {
    return {
      chartdata: null,
      scale: [
        {
          dataKey: 'hour',
          min: 0,
          max: 1
        }
      ],
      height: 400,
      totalCount: 0,
      currentPage: 0,
      time: null,
      serverList: [],
      tableData: {},
      startTime: 0,
      endTime: 0,
      // 查询参数
      queryParam: {
        serverIds: '',
        startTime: 0,
        endTime: 0
      },
      pagination: {
        position: 'bottom',
        pageSizeOptions: ['10', '20', '50', '99999'],
        defaultPageSize: 10
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
    download(filename, content, contentType) {
      if (!contentType) contentType = 'application/octet-stream'
      var a = document.createElement('a')
      var blob = new Blob([content], { type: contentType })
      a.href = window.URL.createObjectURL(blob)
      a.download = filename
      a.click()
    },
    output() {
      let s = this
      if (s.tableData && s.tableData.length > 0) {
        let str = '时间  在线人数\n'
        s.tableData.forEach(el => {
          str += formatTimestamp(el.dateTime) + '  ' + el.count + '\n'
        })
        s.download(`实时在线统计_page${s.currentPage}.txt`, str)
      } else {
        s.$message.error('没有数据，请查询到数据后再进行导出操作')
      }
    },
    seach() {
      let s = this
      if (!s.queryParam.serverIds || s.queryParam.serverIds == encodeURIComponent(JSON.stringify([]))) {
        let arr = []
        s.serverList.forEach(el => {
          arr.push(el.id)
        })
        s.queryParam.serverIds = encodeURIComponent(JSON.stringify(arr))
      }
      s.queryParam.startTime = s.startTime
      s.queryParam.endTime = s.endTime
      if (s.startTime == 0 || s.endTime == 0) {
        return
      }
      getRealTimeOnline(Object.assign(this.queryParam, { pageNo: 0, pageSize: 20 })).then(res => {
        let sourceData = []
        let serverNameArr = []
        let serverIdArr = []
        if (res.contents && res.contents.length > 0) {
          res.contents.forEach(el => {
            s.serverList.forEach(server => {
              if (server.id == el.serverId) {
                let name = `${server.name}(${server.id})`
                if (serverNameArr.indexOf(name) < 0) {
                  serverNameArr.push(name)
                  serverIdArr.push(server.id + '')
                }
              }
            })
          })
          let id = res.contents[0].serverId
          let time = res.contents[0].daily
          
          res.contents[0].statis.forEach((count, hour) => {
            let obj = {}
            obj['hour'] = hour + '时'
            obj[id] = count
            // console.log(obj)
            for (let i = 1; i < res.contents.length; i++) {
              let nowId = res.contents[i].serverId
              let nowTime = res.contents[i].daily
              if (nowId == id || nowTime !== time) {
                continue
              }
              let statis = res.contents[i].statis
              for (let j = 0; j < statis.length; j++) {
                if (j == hour) {
                  obj[nowId] = statis[j]
                  break
                }
              }
              sourceData.push(obj)
            }
            if(serverIdArr && serverIdArr.length == 1) {
              sourceData.push(obj)
            }
          })
        }
        let dv = new DataSet.View().source(sourceData)
        dv.transform({
          type: 'fold',
          fields: serverIdArr,
          key: 'server',
          value: 'count'
        })
        s.chartdata = dv.rows

        s.scale = [
          {
            dataKey: 'hour',
            min: 0,
            max: 1
          }
        ]
      })
    },
    serverChange(value) {
      this.queryParam.serverIds = encodeURIComponent(JSON.stringify(value))
    },
    onTimeChange(date, dateString) {
      //console.log('日期------------>:', date, dateString);
      this.startTime = Number(dateString.replace(/-/g, ''))
      this.endTime = Number(dateString.replace(/-/g, ''))
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
      let startTime = s.$moment(Date.now()).format('YYYYMMDD')
      s.time = s.$moment(startTime, 'YYYYMMDD')
      s.startTime = startTime
      s.endTime = startTime
      s.seach()
    })
  }
}
</script>
