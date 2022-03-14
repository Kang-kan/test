<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row>
          <a-col :md="8" :sm="24">
            <a-form-item
              label="起止日期"
              :labelCol="{lg: {span: 7}, sm: {span: 7}}"
              :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
              style="margin-right: 18px;"
            >
              <a-range-picker format="YYYY-MM-DD" @change="handleTimeChange" v-model="time" />
            </a-form-item>
          </a-col>
          <a-col :md="5">
            <a-button type="primary" style="margin-left: 8px" @click="exportCharge">导出对账单</a-button>
          </a-col>
        </a-row>
        <a-row style="height: 55px;">
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
          <a-col :md="5">
            <a-button type="primary" style="margin-left: 8px" @click="seachAll">全部查询</a-button>
          </a-col>
        </a-row>
        <a-row :gutter="0">
          <a-col :md="5" :sm="24" style="padding-right: 18px;">
            <a-input placeholder="请输入账号id" v-model="queryParam.accountId" />
          </a-col>
          <a-col :md="5" :sm="24" style="padding-right: 18px;">
            <a-row>
              <a-input placeholder="请输入玩家id" v-model="queryParam.playerId" />
            </a-row>
          </a-col>
          <a-col :md="5" :sm="24" style="padding-right: 18px;">
            <a-input placeholder="请输入玩家名称" v-model="queryParam.playerName" />
          </a-col>
          <a-col :md="3" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" style="margin-left: 8px" @click="seach">查询</a-button>
            </span>
          </a-col>
        </a-row>
        <a-row :gutter="0" style="height: 55px;">
          <a-col :md="5" style="padding-right: 18px;">
            <a-row>
              <!-- <a-col :md="7" style="line-height:32px;">订单id:</a-col> -->
              <!-- <a-col :md="14"> -->
              <a-input placeholder="请输入游戏订单号" v-model="queryParam1.orderId" />
              <!-- </a-col> -->
            </a-row>
          </a-col>
          <a-col :md="5">
            <a-button type="primary" style="margin-left: 8px" @click="seachOrder">定单查询</a-button>
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
      :scroll="{ x: 2000 }"
      showPagination="auto"
    >
      <span slot="serial" slot-scope="text, record, index">{{ index + 1 }}</span>
      <span slot="status" slot-scope="text">
        <a-badge :status="text | statusTypeFilter" :text="text | statusFilter" />
      </span>
      <span slot="description" slot-scope="text">
        <ellipsis :length="25" tooltip>{{ text }}</ellipsis>
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
  import xmlObj from '@/config/xmlConfig'
  import { formatTimestamp } from '@/utils/util'
  import moment from 'moment'
  import { STable, Ellipsis, Modal } from '@/components'
  import StepByStepModal from '../list/modules/StepByStepModal'
  import CreateForm from '../list/modules/CreateForm'
  import {
    getQueryAllChargeLogs,
    getQueryChargeLog,
    getQueryChargeLogById,
    exportChargeBill,
    getServerInfo
  } from '@/api/user'
  import xml from '@/config/xmlConfig'
  export default {
    name: 'QueryPlayerChargeLog',
    components: {
      STable,
      Ellipsis,
      CreateForm,
      StepByStepModal
    },
    data() {
      return {
        showpagi: false,
        seachType: 3,
        currentPage: 0,
        time: null,
        timeArr: [],
        channelIdList: [],
        serverList: [],
        tableData: {},
        // 查询参数
        queryParam: {
          serverId: '',
          accountId: '',
          playerId: '',
          playerName: ''
        },
        queryParam1: {
          orderId: ''
        },
        pagination: {
          position: 'bottom',
          pageSizeOptions: ['10', '20', '50', '99999'],
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
            title: '玩家名',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'playerName'
          },
          {
            title: '账号',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'account',
            customRender: account => {
              return account.split('.')[0]
            }
          },
          {
            title: '商品',
            dataIndex: 'goodsId',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '订单金额',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'rmb',
            customRender: rmb => rmb / 100 + '元'
          },
          {
            title: '区服',
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
            title: '渠道id',
            dataIndex: 'channelId',
            scopedSlots: {
              customRender: 'description'
            },
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
            title: '发货状态',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'gameResultCode',
            customRender: gameResultCode => {
              let result
              if (!gameResultCode) return '未支付'
              switch (gameResultCode + '') {
                case '0000':
                  result = '订单发货成功'
                  break
                case '0001':
                  result = '订单不存在'
                  break
                case '0002':
                  result = '商品金额不符'
                  break
                case '0003':
                  result = '签名不匹配'
                  break
                case '0004':
                  result = '订单已发货'
                  break
              }
              return result
            },
            filters: [
              { text: '未支付', value: 'null' },
              { text: '订单发货成功', value: '0000' },
              { text: '订单不存在', value: '0001' },
              { text: '商品金额不符', value: '0002' },
              { text: '签名不匹配', value: '0003' },
              { text: '订单已发货', value: '0004' }
            ],
            filtered: true,
            onFilter: (value, record) => record.gameResultCode + '' == value
          },
          {
            title: '平台支付订单号',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'transactionId'
          },
          {
            title: '游戏订单号',
            dataIndex: 'orderId',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '下单时间',
            dataIndex: 'time',
            scopedSlots: {
              customRender: 'description'
            },
            customRender: time => (time > 0 ? formatTimestamp(time) : '')
          },
          {
            title: '通知发货时间',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'adviceTime',
            customRender: adviceTime => (adviceTime > 0 ? formatTimestamp(adviceTime) : '')
          },
          {
            title: '到账时间',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'arrivalTime',
            customRender: arrivalTime => (arrivalTime > 0 ? formatTimestamp(arrivalTime) : '')
          },
          {
            title: '第一次充值',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'first'
          }
        ],
        // 加载数据方法 必须为 Promise 对象
        loadData: (parameter, filters) => {
          let s = this
          console.log('fitler========>', parameter)
          let filterArr
          if (parameter.gameResultCode && parameter.gameResultCode.length > 0) {
            filterArr = JSON.parse(JSON.stringify(parameter.gameResultCode))
            delete parameter.gameResultCode
          }
          s.currentPage = parameter.pageNo
          let pageSize = parameter.pageSize
          let content
          if (s.seachType == 0) {
            return getQueryChargeLog(Object.assign(parameter, s.queryParam)).then(res => {
              //console.log('tablereturn=======>', res)
              if (!res || !res.contents || res.contents.length == 0) {
                return {
                  pageSize: 10,
                  pageNo: 0,
                  totalPage: 0,
                  totalCount: 0,
                  contents: []
                }
              }
              s.showpagi = true
              // res.totalPage = Math.ceil(res.contents.length / pageSize)
              // res.totalCount = res.contents.length
              res.pageNo = s.currentPage
              res.contents.forEach(async function(el) {
                let result = ''
                el.goodsId = await xmlObj.getRechargeNameById(el.goodsId + '')
              })
              if (filterArr) {
                content = s.fitler(filterArr, 'gameResultCode', res.contents)
                res.contents = content
              }
              return res
            })
          } else if (s.seachType == 1) {
            return getQueryChargeLogById(s.queryParam1).then(async function(res) {
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
              res.goodsId = await xmlObj.getRechargeNameById(res.goodsId + '')
              let obj = {}
              obj.totalPage = 0
              obj.totalCount = 1
              obj.pageNo = s.currentPage
              obj.pageSize = pageSize
              if (filterArr) {
                content = s.fitler(filterArr, 'gameResultCode', res)
                res = content
              }
              obj.contents = [res]
              return obj
            })
          } else if (s.seachType == 3) {
            return getQueryAllChargeLogs(Object.assign(parameter, s.queryParam)).then(res => {
              //console.log('tablereturn=======>', res)
              if (!res || !res.contents || res.contents.length == 0) {
                return {
                  pageSize: 10,
                  pageNo: 0,
                  totalPage: 0,
                  totalCount: 0,
                  contents: []
                }
              }
              s.showpagi = true
              // res.totalPage = Math.ceil(res.contents.length / pageSize)
              // res.totalCount = res.contents.length
              res.pageNo = s.currentPage
              res.contents.forEach(async function(el) {
                el.goodsId = await xmlObj.getRechargeNameById(el.goodsId + '')
              })
              if (filterArr) {
                content = s.fitler(filterArr, 'gameResultCode', res.contents)
                res.contents = content
              }
              return res
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
      let s = this
    },
    methods: {
      handleTimeChange(value, timeString) {
        this.timeArr[0] = Number(timeString[0].replace(/-/g, ''))
        this.timeArr[1] = Number(timeString[1].replace(/-/g, ''))
        //console.log('日期', this.timeArr);
      },
      fitler(filterArr, key, arr) {
        let newArr = []
        arr.forEach(el => {
          let can = false
          filterArr.forEach(fil => {
            if (fil == el[key] + '') {
              can = true
            }
          })
          if (can) {
            newArr.push(el)
          }
        })
        return newArr
      },
      seach() {
        this.seachType = 0
        this.$refs.table.refresh(true)
      },
      seachOrder() {
        this.seachType = 1
        this.$refs.table.refresh(true)
      },
      download(filename, blob) {
        var a = document.createElement('a')
        a.href = window.URL.createObjectURL(blob)
        a.download = filename
        a.click()
      },
      exportCharge() {
        let s = this
        if (!s.timeArr || !s.timeArr[0] || !s.timeArr[1]) {
          s.$message.error('请选择起止时间')
          return
        }
        let param = { startTime: s.timeArr[0], endTime: s.timeArr[1] }
        exportChargeBill(param).then(res => {
          if (res) {
            let sheetArr = []
            let titleArr = [
              '玩家名',
              null,
              '账号',
              null,
              '玩家id',
              null,
              '到账时间',
              null,
              '渠道id',
              null,
              null,
              '服务器',
              null,
              '订单号',
              null,
              '订单金额(元)',
              null,
              '下单时间',
              null,
              '平台支付定单号',
              null
            ]
            let key = [
              'playerName',
              'account',
              'playerId',
              'arrivalTime',
              'channelId',
              'serverId',
              'orderId',
              'rmb',
              'time',
              'transactionId'
            ]
            sheetArr.push(titleArr)
            res.forEach(el => {
              let tempArr = []
              let name
              s.serverList.forEach(server => {
                if (server.id == el.serverId) {
                  name = server.name + '(' + server.id + ')'
                }
              })
              console.log(s.serverList)
              el.serverId = name || el.serverId
              let channelName
              s.channelIdList.forEach(channel => {
                if (channel.id == el.channelId) {
                  channelName = channel.name + '(' + channel.id + ')'
                }
              })
              el.channelId = channelName || el.channelId

              el.account = el.account.split('.')[0]
              el.arrivalTime = el.arrivalTime && formatTimestamp(el.arrivalTime)
              el.time = el.time && formatTimestamp(el.time)
              el.rmb =  (el.rmb / 100);
              key.forEach(k => {
                if (k == 'channelId') {
                  tempArr.push(el[k], null, null)
                } else {
                  tempArr.push(el[k], null)
                }
              })

              sheetArr.push(tempArr)
            })
            let worksheet = s.$xlsx.utils.aoa_to_sheet(sheetArr)
            let merges = []
            for (let i = 0; i < key.length; i++) {
              for (let j = 0; j < sheetArr.length; j++) {
                if (key[i] == 'channelId') {
                  merges.push({ s: { r: j, c: i * 2 }, e: { r: j, c: i * 2 + 2 } })
                } else {
                  if(i <= 4) {
                    merges.push({ s: { r: j, c: i * 2 }, e: { r: j, c: i * 2 + 1 } })
                  }else {
                    merges.push({ s: { r: j, c: i * 2 + 1 }, e: { r: j, c: i * 2 + 2 } })
                  }
                }
              }
            }
            // worksheet['!merges'] = [{s: {r:0,c:0},e: {r:0,c:1}}];
            worksheet['!merges'] = merges
            s.download('对账单.xlsx', s.sheet2blob(worksheet))
          }
        })
      },
      sheet2blob(sheet, sheetName) {
        let s = this
        sheetName = sheetName || 'sheet1'
        var workbook = {
          SheetNames: [sheetName],
          Sheets: {}
        }
        workbook.Sheets[sheetName] = sheet
        // 生成excel的配置项
        var wopts = {
          bookType: 'xlsx', // 要生成的文件类型
          bookSST: false, // 是否生成Shared String Table，官方解释是，如果开启生成速度会下降，但在低版本IOS设备上有更好的兼容性
          type: 'binary'
        }
        var wbout = s.$xlsx.write(workbook, wopts)
        var blob = new Blob([s2ab(wbout)], { type: 'application/octet-stream' })
        // 字符串转ArrayBuffer
        function s2ab(s) {
          var buf = new ArrayBuffer(s.length)
          var view = new Uint8Array(buf)
          for (var i = 0; i != s.length; ++i) view[i] = s.charCodeAt(i) & 0xff
          return buf
        }
        return blob
      },
      seachAll() {
        this.seachType = 3
        this.$refs.table.refresh(true)
      },
      serverChange(value) {
        this.queryParam.serverId = Number(value)
      },
      onTimeChange(date, dateString) {
        // console.log('日期------------>:', date, dateString);
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
      s.$ajax.get('../gameVar.json').then(res => {
        if (!res || !res.data) {
          return
        }
        s.channelIdList = res.data.channelIdList
      })
      this.$http.get('/roleGameServer/getMyServers').then(res => {
        s.serverList = res
      })
    }
  }
</script>
