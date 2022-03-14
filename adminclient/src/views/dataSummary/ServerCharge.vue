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
            >
              <a-date-picker @change="onTimeChange" v-model="time"/>
            </a-form-item>
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
import { getSvrChargSummary, getServerInfo } from '@/api/user'


export default {
  name: 'ServerCharge',
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
      tableData: {},
      serverList: [],
      startTime: 0,
      // 查询参数
      queryParam: {
        startTime: 0
      },
      pagination: {
        position: 'bottom',
        pageSizeOptions: ['10', '20', '50','99999'],
        defaultPageSize: 10
      },
      // 表头
      columns: [
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
          title: '服务器充值数据',
          scopedSlots: {
            customRender: 'description'
          },
          children: [
            {
              title: '时间',
              dataIndex: 'dateTime'
            },
            {
              title: '充值金额',
              dataIndex: 'price',
              customRender: price => {
                return price && Number(price > 0) ? price / 100 + '元' : ''
              }
            }
          ]
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        let s = this
        s.currentPage = parameter.pageNo;
        //console.log('param------->',s.queryParam)
        if (s.queryParam.startTime == 0) {
          return new Promise(function(resolve, reject) {
            setTimeout(() => {
              resolve('ok')
            }, 500)
          }).then(res => {
            if (res == 'ok') {
              return {
                pageSize: 10,
                pageNo: 0,
                totalPage: 0,
                totalCount: 0,
                startTime: s.startTime,
                contents: []
              }
            }
          })
        }
        return getSvrChargSummary(Object.assign(parameter, s.queryParam)).then(res => {
          //console.log('tablereturn=======>',res)
          if (!res || !res.contents || res.contents.length == 0) {
            return {
              pageSize: 10,
              pageNo: 0,
              totalPage: 0,
              totalCount: 0,
              
              contents: []
            }
          }
          let obj = {}
          obj.pageSize = res.pageSize
          obj.pageNo = s.currentPage;
          obj.totalCount = res.contents.length;
          obj.totalPage = Math.ceil(res.contents.length / res.pageSize)
          let arr = []
          res.contents.forEach(el => {
            // if(el.srvChargeDtos && el.srvChargeDtos.length > 0) {
            let newobj = {}
            el.srvChargeDtos.sort((a, b) => {
              return a.dateTime - b.dateTime
            })
            Object.assign(newobj, { serverId: el.serverId, children: el.srvChargeDtos })
            arr.push(newobj)
            // }
          })
          obj.contents = arr
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
    let s = this;
    // getServerInfo({ serverId: res[0].id }).then(res => {
    //   if (!res) {
    //     let startTime = 20190101
        
    //     s.time = s.$moment(startTime.toString(), 'YYYYMMDD')
    //     s.startTime = startTime;
    //     return
    //   }
    //   let startTime = s.$moment(res.openTime).format('YYYYMMDD')
      
    //   s.time = s.$moment(startTime, 'YYYYMMDD')
    //   s.startTime = startTime;
    // })
    // getServerInfo().then(res => {
    //   //console.log('测试端口', res);
    // })
  },
  methods: {
    seach() {
      this.queryParam = {
        pageNo: 0,
        pageSize: 10,
        startTime: this.startTime
      }
      this.$refs.table.refresh(true)
    },
    qudaoChange(value) {
      //console.log('渠道', value)
    },
    onTimeChange(date, dateString) {
      //console.log('日期------------>:',date,dateString);
      this.startTime = Number(dateString.replace(/-/g, ''))
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
    this.$http.get('/roleGameServer/getMyServers').then(res => {
      s.serverList = res
      if(!res || res.length == 0) {
        return;
      };
    })
  }
}
</script>
