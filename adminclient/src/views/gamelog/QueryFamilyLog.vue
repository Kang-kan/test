<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="0">
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
          <a-col :md="5" :sm="24" style="padding-right: 18px;">
            <a-input placeholder="请输入仙盟id" v-model="queryParam.familyId" />
          </a-col>
          <a-col :md="5" :sm="24" style="padding-right: 18px;">
            <a-row>
              <a-input placeholder="请输入仙盟名" v-model="queryParam.familyName" />
            </a-row>
          </a-col>
          <a-col :md="2" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" style="margin-left: 8px" @click="seach(1)">仙盟查询</a-button>
            </span>
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
          <a-col :md="2" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" style="margin-left: 8px" @click="seach(2)">玩家查询</a-button>
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
      showPagination="auto"
      :scroll="{x: 2000}"
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
  import { formatTimestamp } from '@/utils/util'
  import moment from 'moment'
  import { STable, Ellipsis, Modal } from '@/components'
  import StepByStepModal from '../list/modules/StepByStepModal'
  import CreateForm from '../list/modules/CreateForm'
  import { getFamilyLogById, getFamilyLog, getServerInfo } from '@/api/user'
  export default {
    name: 'QueryFamilyLog',
    components: {
      STable,
      Ellipsis,
      CreateForm,
      StepByStepModal
    },
    data() {
      return {
        description: '仙盟查询，玩家查询都必须选择区服',
        showpagi: false,
        currentPage: 0,
        type: 1,
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
            title: '时间',
            dataIndex: 'time',
            scopedSlots: {
              customRender: 'description'
            },
            customRender: createTime => (createTime > 0 ? formatTimestamp(createTime) : '')
          },
          {
            title: '仙盟经验',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'exp'
          },
          {
            title: '仙盟id',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'familyId'
          },
          {
            title: '仙盟名称',
            dataIndex: 'name',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '仙盟等级',
            dataIndex: 'level',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '会长名字',
            dataIndex: 'leaderName',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '操作人id',
            dataIndex: 'opPlayerId',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '操作人',
            dataIndex: 'opPlayerName',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '操作人职位',
            dataIndex: 'opPos',
            scopedSlots: {
              customRender: 'description'
            },
            customRender: opPos => this.setPos(opPos)
          },
          {
            title: '操作类型',
            dataIndex: 'opValue',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '被操作人id',
            dataIndex: 'targetPlayerId',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '被操作人',
            dataIndex: 'targetPlayerName',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '被操作人职位',
            dataIndex: 'targetPos',
            scopedSlots: {
              customRender: 'description'
            },
            customRender: targePos => this.setPos(targePos)
          }
        ],
        // 加载数据方法 必须为 Promise 对象
        loadData: parameter => {
          let s = this
          //console.log('param------->', s.queryParam)
          s.currentPage = parameter.pageNo
          let pageSize = parameter.pageSize
          if (
            (!s.queryParam.familyId &&
              !s.queryParam.familyName &&
              !s.queryParam.accountId &&
              !s.queryParam.playerName &&
              !s.queryParam.playerId) ||
            !s.queryParam.serverId
          ) {
            return new Promise(function(resolve, reject) {
              setTimeout(() => {
                resolve('ok')
              }, 100)
            }).then(res => {
              s.showpagi = false
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
          if (s.type == 1) {
            return getFamilyLogById(Object.assign(parameter, s.queryParam)).then(res => {
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
              res.contents.forEach(el => {
                switch (el.opValue) {
                  case 0:
                    el.opValue = '创建'
                    break
                  case 1:
                    el.opValue = '加入'
                    break
                  case 2:
                    el.opValue = '离开'
                    break
                  case 3:
                    el.opValue = '任命'
                    break
                  case 4:
                    el.opValue = '职位变化'
                    break
                  case 5:
                    el.opValue = '转让'
                    break

                  case 6:
                    el.opValue = '弹劾'
                    break
                  case 7:
                    el.opValue = '踢人'
                    break
                  case 8:
                    el.opValue = '解散'
                    break
                  case 9:
                    el.opValue = '捐献'
                    break
                  case 10:
                    el.opValue = '批准加入'
                    break
                }
              })
              return res
            })
          } else if (s.type == 2)
            return getFamilyLog(Object.assign(parameter, s.queryParam)).then(res => {
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
              res.contents.forEach(el => {
                switch (el.opValue) {
                  case 0:
                    el.opValue = '创建'
                    break
                  case 1:
                    el.opValue = '加入'
                    break
                  case 2:
                    el.opValue = '离开'
                    break
                  case 3:
                    el.opValue = '任命'
                    break
                  case 4:
                    el.opValue = '职位变化'
                    break
                  case 5:
                    el.opValue = '转让'
                    break

                  case 6:
                    el.opValue = '弹劾'
                    break
                  case 7:
                    el.opValue = '踢人'
                    break
                  case 8:
                    el.opValue = '解散'
                    break
                  case 9:
                    el.opValue = '捐献'
                    break
                  case 10:
                    el.opValue = '批准加入'
                    break
                }
              })
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
      this.tableOption()
      let s = this
    },
    methods: {
      seach(type) {
        this.type = type
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
      }
    },
    mounted: function() {
      let s = this
      this.$http.get('/roleGameServer/getMyServers').then(res => {
        s.serverList = res
        if (!res || res.length == 0) {
          return
        }
        getServerInfo({ serverId: res[0].id }).then(res => {
          if (!res) {
            let startTime = 20190101
            s.time = s.$moment(startTime.toString(), 'YYYYMMDD')
            s.startTime = startTime
            return
          }
          let startTime = s.$moment(res.openTime).format('YYYYMMDD')
          s.time = s.$moment(startTime, 'YYYYMMDD')
          s.startTime = startTime
        })
      })
    }
  }
</script>
