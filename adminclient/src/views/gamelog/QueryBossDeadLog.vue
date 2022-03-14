<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row>
          <a-col :md="5" :sm="24" style="padding-right: 18px;">
            <a-input placeholder="请输入战斗id" v-model="queryParam.battleId" />
          </a-col>
          <a-col :md="2" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" style="margin-left: 8px" @click="seach(0)">id查询</a-button>
            </span>
          </a-col>
        </a-row>
        <a-row :gutter="0">
          <a-col :md="6" :sm="24">
            <a-form-item
              label="起止日期"
              :labelCol="{lg: {span: 7}, sm: {span: 7}}"
              :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
              style="margin-right: 18px;"
            >
              <a-range-picker format="YYYY-MM-DD" @change="handleTimeChange" v-model="time" />
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
          <a-col :md="5" :sm="12">
            <a-row>
              <a-col :md="7" style="line-height: 32px;">boss玩法类型:</a-col>
              <a-col :md="14">
                <a-select size="default" @change="bossChange">
                  <a-select-option
                    v-for="i in bossType1"
                    :key="i.id"
                    :value="i.id"
                  >{{i.name}}({{i.id}})</a-select-option>
                </a-select>
              </a-col>
            </a-row>
          </a-col>
          <a-col :md="2" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" style="margin-left: 8px" @click="seach(1)">玩法服务器查询</a-button>
            </span>
          </a-col>
        </a-row>
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
          <a-col :md="4" :sm="24" style="padding-right: 18px;">
            <a-input placeholder="请输入账号id" v-model="queryParam.accountId" />
          </a-col>
          <a-col :md="4" :sm="24" style="padding-right: 18px;">
            <a-row>
              <a-input placeholder="请输入玩家id" v-model="queryParam.playerId" />
            </a-row>
          </a-col>
          <a-col :md="4" :sm="24" style="padding-right: 18px;">
            <a-input placeholder="请输入玩家名称" v-model="queryParam.playerName" />
          </a-col>
          <a-col :md="5" :sm="12">
            <a-row>
              <a-col :md="7" style="line-height: 32px;">boss玩法类型:</a-col>
              <a-col :md="14">
                <a-select size="default" @change="bossChange">
                  <a-select-option
                    v-for="i in bossType"
                    :key="i.id"
                    :value="i.id"
                  >{{i.name}}({{i.id}})</a-select-option>
                </a-select>
              </a-col>
            </a-row>
          </a-col>
          <a-col :md="2" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" style="margin-left: 8px" @click="seach(2)">查询</a-button>
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
  import xmlObj from '@/config/xmlConfig'
  import { formatTimestamp, playerTypeName } from '@/utils/util'
  import moment from 'moment'
  import { STable, Ellipsis, Modal } from '@/components'
  import StepByStepModal from '../list/modules/StepByStepModal'
  import CreateForm from '../list/modules/CreateForm'
  import {
    getQueryBossDeadLogByBattleId,
    getQueryBossDeadLogBySrvAndType,
    getQueryPlayerBossDeadLog,
    getServerInfo
  } from '@/api/user'
  export default {
    name: 'QueryBossDeadLog',
    components: {
      STable,
      Ellipsis,
      CreateForm,
      StepByStepModal
    },
    data() {
      return {
        bossType: [
          { id: 0, name: '所有类型' },
          { id: 1, name: '活动boss' },
          { id: 2, name: '奇遇boss' },
          { id: 3, name: '召唤boss' },
          { id: 4, name: '印记塔' },
          { id: 5, name: '仙盟试炼' },
          { id: 6, name: '天域争霸' },
          { id: 7, name: '帝君神殿' },
          { id: 8, name: '帝君' },
          { id: 9, name: '帝君塔' },
          { id: 10, name: '秘境boss' },
          { id: 11, name: 'boss之家' },
          { id: 12, name: '仙戒boss' },
          { id: 13, name: '主线boss' },
          { id: 14, name: '关卡协助' },
          { id: 15, name: '结婚场景' },
          { id: 16, name: '密境boss' },
          { id: 17, name: '守卫剑阁' },
          { id: 18, name: '世界boss' },
          { id: 19, name: '个人boss' },
          { id: 20, name: '万妖塔' },
          { id: 21, name: '翎羽boss' },
          { id: 22, name: '仙缘副本' },
          { id: 23, name: '历练之地' },
          { id: 24, name: '仙魔战场' },
          { id: 25, name: '九重天' },
          { id: 26, name: '仙界魔神' },
          { id: 27, name: '荒古禁地' }
        ],
        bossType1: [
          { id: 1, name: '活动boss' },
          { id: 2, name: '奇遇boss' },
          { id: 3, name: '召唤boss' },
          { id: 4, name: '印记塔' },
          { id: 5, name: '仙盟试炼' },
          { id: 6, name: '天域争霸' },
          { id: 7, name: '帝君神殿' },
          { id: 8, name: '帝君' },
          { id: 9, name: '帝君塔' },
          { id: 10, name: '秘境boss' },
          { id: 11, name: 'boss之家' },
          { id: 12, name: '仙戒boss' },
          { id: 13, name: '主线boss' },
          { id: 14, name: '关卡协助' },
          { id: 15, name: '结婚场景' },
          { id: 16, name: '密境boss' },
          { id: 17, name: '守卫剑阁' },
          { id: 18, name: '世界boss' },
          { id: 19, name: '个人boss' },
          { id: 20, name: '万妖塔' },
          { id: 21, name: '翎羽boss' },
          { id: 22, name: '仙缘副本' },
          { id: 23, name: '历练之地' },
          { id: 24, name: '仙魔战场' },
          { id: 25, name: '九重天' },
          { id: 26, name: '仙界魔神' },
          { id: 27, name: '荒古禁地' }
        ],
        seachType: 0,
        showpagi: false,
        currentPage: 0,
        time: null,
        timeArr: [],
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
            customRender: createTime => formatTimestamp(createTime)
          },
          // {
          //   title: '账号',
          //   dataIndex: 'account',
          //   scopedSlots: {
          //     customRender: 'description'
          //   },
          //   customRender: account => account.split('.')[0]
          // },
          {
            title: '战斗id',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'battleId'
          },
          {
            title: '战斗服务器id',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'battleServer'
          },
          {
            title: 'boss配置id',
            dataIndex: 'bossId',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: 'boss名',
            dataIndex: 'bossName',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '归属者id',
            dataIndex: 'ownerId',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '归属者名',
            dataIndex: 'ownerName',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '归属类型',
            dataIndex: 'ownerType',
            scopedSlots: {
              customRender: 'description'
            },
            customRender: ownerType => (ownerType == 0 ? '个人' : '工会')
          },
          {
            title: '玩法',
            dataIndex: 'playType',
            scopedSlots: {
              customRender: 'description'
            },
            customRender: playType => playerTypeName(playType)
          },
          {
            title: '奖励内容',
            dataIndex: 'reward',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '奖励类型',
            dataIndex: 'rewardType',
            scopedSlots: {
              customRender: 'description'
            },
            customRender: rewardType => {
              let str = ''
              switch (rewardType) {
                case 0:
                  str = '参与'
                  break
                case 1:
                  str = '归属'
                  break
                case 2:
                  str = '排名'
                  break
                case 3:
                  str = '失败'
                  break
                case 4:
                  str = '破盾'
                  break
              }
              return str
            }
          }
        ],
        // 加载数据方法 必须为 Promise 对象
        loadData: parameter => {
          let s = this
          console.log('param------->', s.queryParam)
          s.currentPage = parameter.pageNo
          let pageSize = parameter.pageSize
          if (
            !s.queryParam.accountId &&
            !s.queryParam.playerName &&
            !s.queryParam.playerId &&
            !s.queryParam.playerType &&
            !s.queryParam.battleId &&
            !s.queryParam.startTime &&
            !s.queryParam.endTime &&
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

          if (s.seachType == 0) {
            return getQueryBossDeadLogByBattleId({ battleId: s.queryParam.battleId }).then(res => {
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
              s.showpagi = true
              let obj = {}
              obj.totalPage = Math.ceil(res.length / pageSize)
              obj.totalCount = res.length
              obj.pageNo = s.currentPage
              obj.pageSize = pageSize
              obj.contents = res
              obj.contents.forEach(async function(el) {
                let bonusStr = ''
                let arr = JSON.parse(el.reward)
                if (Array.isArray(arr)) {
                  for (let i = 0; i < arr.length; i++) {
                    bonusStr += (await xmlObj.getBonus(arr[i])) + ','
                  }
                } else {
                  bonusStr = await xmlObj.getBonus(arr)
                }
                el.reward = bonusStr
              })
              return obj
            })
          } else if (s.seachType == 1) {
            s.queryParam.startTime = s.timeArr[0]
            s.queryParam.endTime = s.timeArr[1]
            return getQueryBossDeadLogBySrvAndType(Object.assign(parameter, s.queryParam)).then(res => {
              //console.log('tablereturn=======>', res)
              if (!res || res.contents.length == 0) {
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
              res.contents.forEach(async function(el) {
                let bonusStr = ''
                let arr = JSON.parse(el.reward)
                if (Array.isArray(arr)) {
                  for (let i = 0; i < arr.length; i++) {
                    bonusStr += (await xmlObj.getBonus(arr[i])) + ','
                  }
                } else {
                  bonusStr = await xmlObj.getBonus(arr)
                }
                el.reward = bonusStr
              })
              res.pageNo = s.currentPage
              return res
            })
          } else if (s.seachType == 2) {
            return getQueryPlayerBossDeadLog(Object.assign(parameter, s.queryParam)).then(res => {
              //console.log('tablereturn=======>', res)
              if (!res || res.contents.length == 0) {
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
              res.contents.forEach(async function(el) {
                let bonusStr = ''
                let arr = JSON.parse(el.reward)
                if (Array.isArray(arr)) {
                  for (let i = 0; i < arr.length; i++) {
                    bonusStr += (await xmlObj.getBonus(arr[i])) + ','
                  }
                } else {
                  bonusStr = await xmlObj.getBonus(arr)
                }
                el.reward = bonusStr
              })
              res.pageNo = s.currentPage
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
      seach(type) {
        this.seachType = type
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
      bossChange(value) {
        this.queryParam.playType = Number(value)
        console.log('playerType: ', value)
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
      handleTimeChange(value, timeString) {
        this.timeArr[0] = Number(timeString[0].replace(/-/g, ''))
        this.timeArr[1] = Number(timeString[1].replace(/-/g, ''))
        //console.log('日期',this.timeArr);
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
