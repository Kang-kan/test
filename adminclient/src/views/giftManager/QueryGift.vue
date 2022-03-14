<template>
  <a-card :bordered="false">
    <a-form layout="inline">
      <a-row :gutter="0" style="line-height: 60px;">
        <a-col :md="5" :sm="12">
          <a-input v-model="giftId" placeholder="请输入礼包id"></a-input>
        </a-col>
        <a-col :md="5" :sm="12" style="margin-left: 20px;">
          <a-button htmlType="submit" type="primary" @click="queryGift">查询已有礼包码</a-button>
        </a-col>
      </a-row>
      <a-row :gutter="0" style="line-height: 60px;">
        <a-col :md="5" :sm="12">
          <a-input v-model="code" placeholder="请输入礼包码"></a-input>
        </a-col>
        <a-col :md="5" :sm="12" style="margin-left: 20px;">
          <a-button htmlType="submit" type="primary" @click="queryCode">查询指定礼包码</a-button>
        </a-col>
      </a-row>
    </a-form>
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
  </a-card>
</template>

<script>
  import notification from 'ant-design-vue/es/notification'
  import { formatTimestamp } from '@/utils/util'
  import moment from 'moment'
  import { STable, Ellipsis, Modal } from '@/components'
  import StepByStepModal from '../list/modules/StepByStepModal'
  import CreateForm from '../list/modules/CreateForm'
  import { getQueryGiftCode, getQueryGift, getServerInfo } from '@/api/user'
  export default {
    name: 'QueryGift',
    components: {
      STable,
      Ellipsis,
      CreateForm,
      StepByStepModal
    },
    data() {
      return {
        code: '',
        giftId: null,
        showpagi: false,
        currentPage: 0,
        time: null,
        serverList: [],
        tableData: {},
        // 查询参数
        queryParam: {
          serverId: '',
          accountId: '',
          playerId: '',
          playerName: ''
        },
        pagination: {
          position: 'bottom',
          pageSizeOptions: ['10', '20', '50', '99999'],
          defaultPageSize: 10
        },
        // 表头
      columns: [
        {
          title: '礼包码',
          dataIndex: 'code',
          scopedSlots: {
            customRender: 'description'
          }
        },
        {
          title: '礼包id',
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'giftId'
        },
        {
          title: '使用礼包码玩家',
          scopedSlots: {
            customRender: 'description'
          },
          dataIndex: 'usedUsers',
          customRender: usedUsers => usedUsers == 'null' ? '无用户使用' : usedUsers
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        let s = this
        //console.log('param------->', s.queryParam)
        s.currentPage = parameter.pageNo
        let pageSize = parameter.pageSize
        if (!s.code) {
          return new Promise(function(resolve, reject) {
            setTimeout(() => {
              resolve('ok')
            }, 100)
          }).then(res => {
            if (res == 'ok') {
              s.showpagi = false
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
        return getQueryGiftCode({code: s.code}).then(res => {
          console.log('tablereturn=======>', res)
          if (!res || res == '礼包码不存在') {
            return {
              pageSize: 10,
              pageNo: 0,
              totalPage: 0,
              totalCount: 0,
              contents: []
            }
          }
          let obj = {}

          obj.totalPage = 1;
          obj.totalCount = 1;
          obj.pageNo = 0;
          obj.pageSize = 10;
          obj.contents = [JSON.parse(res)];
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
      download(filename, content, contentType) {
        if (!contentType) contentType = 'application/octet-stream'
        var a = document.createElement('a')
        var blob = new Blob([content], { type: contentType })
        a.href = window.URL.createObjectURL(blob)
        a.download = filename
        a.click()
      },
      queryGift() {
        let s = this
        let obj = {}
        if (s.giftId) {
          obj['giftId'] = s.giftId
        }
        getQueryGift(obj).then(res => {
          if (res) {
            s.download('HavedGiftCode.txt', res)
          }
        })
      },
      queryCode() {
        this.$refs.table.refresh(true)
      },
      setPos(pos) {
        let posName = ''
        switch (pos) {
          case 1:
            posName = '会长'
            break
          case 2:
            posName = '副会长'
            break
          case 3:
            posName = '长老'
            break
          case 4:
            posName = '护法'
            break
          case 5:
            posName = '会员'
            break
          case 6:
            posName = '非会员'
            break
        }
        return posName
      },
      serverChange(value) {
        this.queryParam.serverId = Number(value)
      },
      onTimeChange(date, dateString) {
        //console.log('日期------------>:', date, dateString);
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
      },
      
    }
  }
</script>
