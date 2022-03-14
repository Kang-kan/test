<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <div class="table-operator">
        <a-button type="primary" icon="plus" @click="openAlert()">添加屏蔽关键字</a-button>
      </div>
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
          <a @click="openDel(record)">删除</a>
        </template>
      </span>
    </s-table>

    <div style="margin-top: 50px;height: 120px;">
      <a-form layout="inline">
        <a-row :gutter="0" style="line-height: 32px;">
          <a-col :md="5" :sm="12">
            <a-row>
              <a-col :md="6" style="line-height: 32px;">渠道:</a-col>
              <a-col :md="14">
                <a-select size="default" @change="qudaoChange" placeholder="默认全渠道">
                  <a-select-option v-for="i in channelIdList" :key="i.id" :value="i.id">{{i.name}}</a-select-option>
                </a-select>
              </a-col>
            </a-row>
          </a-col>
          <a-col :md="4" :sm="12">
            <a-row>
              <a-col :md="7" style="line-height: 32px;">区服:</a-col>
              <a-col :md="14">
                <a-select size="default" @change="serverChange" placeholder="默认全服">
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
              <a-col :md="6" style="line-height: 32px;">聊天频道:</a-col>
              <a-col :md="14">
                <a-select size="default" @change="channelChange" placeholder="默认全频道">
                  <a-select-option key="-1" value>全频道</a-select-option>
                  <a-select-option key="0" value="0">仙盟广播</a-select-option>
                  <a-select-option key="1" value="1">世界频道</a-select-option>
                  <a-select-option key="2" value="2">仙盟频道</a-select-option>
                  <a-select-option key="3" value="3">系统频道</a-select-option>
                  <a-select-option key="4" value="4">公告频道</a-select-option>
                  <a-select-option key="5" value="5">私聊频道</a-select-option>
                  <a-select-option key="6" value="6">跨服频道</a-select-option>
                  <a-select-option key="7" value="7">好友聊天</a-select-option>
                  <a-select-option key="8" value="8">反馈频道</a-select-option>
                </a-select>
              </a-col>
            </a-row>
          </a-col>
        </a-row>
        <a-row style="line-height: 32px;margin-top: 25px;">
          <a-col :md="4" :sm="24" style="padding-right: 18px;">
            <a-input placeholder="请输入账号id" v-model="queryParam.account" />
          </a-col>
          <a-col :md="4" :sm="24" style="padding-right: 18px;">
            <a-input placeholder="请输入角色id" v-model="queryParam.playerId" />
          </a-col>
          <a-col :md="4" :sm="24" style="padding-right: 18px;">
            <a-input placeholder="请输入角色名称" v-model="queryParam.playerName" />
          </a-col>
          <a-col :md="4" :sm="24" style="padding-right: 18px;">
            <a-input placeholder="请输入聊天关键字" v-model="queryParam.keyWord" />
          </a-col>
          <a-col :md="2" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" style="margin-left: 8px" @click="seach(0)">搜索</a-button>
            </span>
          </a-col>
          <a-col :md="2" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" style="margin-left: 8px" @click="seach(1)">滚动搜索</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>

    <s-table
      ref="table1"
      size="default"
      rowKey="monthtableid1"
      :columns="columns1"
      :data="loadData1"
      :pagination="pagination"
      style="word-break: normal;"
      :showPagination="showpagi"
    >
      <span slot="serial" slot-scope="text, record, index">{{ index + 1 }}</span>
      <span slot="status" slot-scope="text">
        <a-badge :status="text | statusTypeFilter" :text="text | statusFilter" />
      </span>
      <span slot="countStatus" slot-scope="text">
        <font :color="text == 0 ? '#61affe' : '#f93e3e'">{{text}}</font>
      </span>
      <span slot="description" slot-scope="text">
        <ellipsis :length="25" tooltip>{{ text }}</ellipsis>
      </span>
      <span slot="clickTxt" slot-scope="text">
        <template>
          <a @click="openAlert(text)">{{ text }}</a>
        </template>
        <!-- <ellipsis :length="25" tooltip></ellipsis> -->
      </span>
      <span slot="action" slot-scope="text, record">
        <template>
          <a @click="$refs.blockChat.add(record)">禁言三天</a>
          <a-divider type="vertical" />
          <a @click="$refs.blockPlayer.add(record)">封号</a>
          <a-divider type="vertical" />
          <a @click="openDel(record,0)">解除禁言</a>
          <a-divider type="vertical" />
          <a @click="openDel(record,1)">解除封号</a>
        </template>
      </span>
    </s-table>
    <BlockChat ref="blockChat" @ok="handleOk"></BlockChat>
    <BlockPlayer ref="blockPlayer" @ok="handleOk"></BlockPlayer>
    <a-modal
      :title="title"
      :width="640"
      :visible="visible"
      @ok="handleSubmit"
      @cancel="handleCancel"
      ref="alertModal"
    >
      <a-form @submit="handleSubmit" :form="form">
        <a-form-item
          label="关键字"
          :labelCol="{lg: {span: 7}, sm: {span: 7}}"
          :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
        >
          <a-input :autosize="{ minRows: 4 }" v-model="keyWord" placeholder="请输入屏蔽关键字" />
        </a-form-item>
      </a-form>
    </a-modal>
    <a-modal
      :title="deltitle"
      :width="640"
      :visible="delvisible"
      @ok="handleDel"
      @cancel="handleDelCancel"
      ref="delModal"
    >
      <p>{{deletText}}</p>
    </a-modal>
  </a-card>
</template>

<script>
  import xmlObj from '@/config/xmlConfig'
  import { formatTimestamp } from '@/utils/util'
  import moment from 'moment'
  import { STable, Ellipsis, Modal } from '@/components'
  import {
    removeBlockWord,
    getBlockWords,
    addBlockWord,
    scrollChatMsg,
    searchRecord,
    unBlockPlayer,
    unBlockChat,
    getBlockPlayer,
    getBlockChat,
    getServerInfo
  } from '@/api/user'
  import xml from '@/config/xmlConfig'
  import BlockChat from './modules/BlockChat'
  import BlockPlayer from './modules/BlockPlayer'
  const statusMap = {
    0: {
      status: 'processing',
      text: '正常'
    },
    1: {
      status: 'error',
      text: '禁言'
    },
    2: {
      status: 'error',
      text: '封号'
    }
  }
  export default {
    name: 'BlockWordList',
    components: {
      STable,
      Ellipsis,
      BlockChat,
      BlockPlayer
    },
    data() {
      return {
        record: null,
        handleType: 0,
        scrollTimer: 0,
        seachType: 0,
        form: this.$form.createForm(this),
        deletText: '',
        deltitle: '',
        delvisible: false,
        keyWord: '',
        title: '',
        visible: false,
        showpagi: false,
        // time: null,
        serverList: [],
        channelIdList: [],
        channel: {
          0: '仙盟广播',
          1: '世界频道',
          2: '仙盟频道',
          3: '系统频道',
          4: '公告频道',
          5: '私聊频道',
          6: '跨服频道',
          7: '好友聊天',
          8: '反馈频道'
        },
        queryParam: {},
        // 查询参数
        pagination: {
          position: 'bottom',
          pageSizeOptions: ['10', '20', '50', '9999'],
          defaultPageSize: 10
        },
        // 表头
        columns: [
          {
            title: '屏蔽关键字',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'message'
          },
          {
            title: '操作',
            scopedSlots: {
              customRender: 'action'
            }
          }
        ],
        // 表头
        columns1: [
          {
            title: '时间',
            dataIndex: 'time',
            scopedSlots: {
              customRender: 'description'
            },
            customRender: time => (time ? formatTimestamp(time) : time)
          },
          {
            title: '渠道id',
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
            title: 'vip等级',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'vipLevel'
          },
          {
            title: '频道',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'chatChannel',
            customRender: chatChannel => chatChannel && this.channel[chatChannel]
          },
          {
            title: '发言内容',
            scopedSlots: {
              customRender: 'clickTxt'
            },
            dataIndex: 'content'
          },
          {
            title: '聊天接收方',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'receiver',
            customRender: receiver => {
              return receiver ? `玩家名:${receiver.name}/类型:${receiver.type == 0 ? '仙盟' : '私聊'}` : ''
            }
          },
          {
            title: '操作',
            scopedSlots: {
              customRender: 'action'
            }
          },
          {
            title: '状态',
            scopedSlots: {
              customRender: 'status'
            },
            dataIndex: 'status'
          }
        ],
        // 加载数据方法 必须为 Promise 对象
        loadData: parameter => {
          let s = this
          return getBlockWords(Object.assign(parameter, s.queryParam)).then(async function(res) {
            console.log('tablereturn=======>', res)
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
            obj.totalPage = Math.floor(res.length / parameter.pageSize)
            obj.totalCount = res.length
            obj.pageNo = parameter.pageNo
            obj.pageSize = parameter.pageSize
            let contents = []
            res.forEach(el => {
              contents.push({ message: el })
            })
            obj.contents = contents
            return obj
          })
        },
        loadData1: parameter => {
          let s = this
          if (s.seachType == 0) {
            return searchRecord(Object.assign(parameter, s.queryParam)).then(async function(res) {
              console.log('tablereturn=======>', res)
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
              return res
            })
          } else if (s.seachType == 1) {
            return scrollChatMsg(Object.assign(parameter, s.queryParam)).then(async function(res) {
              console.log('tablereturn=======>', res)
              if (!res) {
                return {
                  pageSize: 10,
                  pageNo: 0,
                  totalPage: 0,
                  totalCount: 0,
                  contents: []
                }
              }
              s.showpagi = false
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
        let s = this
        s.seachType = type
        if (s.scrollTimer > 0) {
          clearInterval(s.scrollTimer)
        }
        if (type == 1) {
          s.queryParam.scrollTime = Date.now()
          s.$refs.table1.refresh(false)
          s.scrollTimer = setInterval(() => {
            console.log('滚动搜索')
            s.$refs.table1.refresh(false)
          }, 5000)
        } else {
          s.$refs.table1.refresh(true)
        }
      },
      channelChange(value) {
        let s = this
        s.queryParam.chatChannel = value
      },
      qudaoChange(value) {
        this.queryParam.channelId = value
      },
      serverChange(value) {
        this.queryParam.serverId = Number(value)
      },
      openDel(record, type) {
        let s = this
        s.handleType = type
        s.record = JSON.parse(JSON.stringify(record))
        let str
        let title
        switch (type) {
          case 0:
            str = `确认对玩家:${record.playerName}解除禁言`
            title = '解除禁言'
            break
          case 1:
            str = `确认对玩家:${record.playerName}解除封号`
            title = '解除封号'
            break
        }
        s.deltitle = title
        s.deletText = str
        s.delvisible = true
      },
      handleDel() {
        let s = this
        if (!s.record || s.handleType == null) {
          this.delvisible = false
          return
        }
        switch (s.handleType) {
          case 0:
            unBlockChat({ playerId: s.record.playerId }).then(res => {
              if (res) {
                s.$refs.table1.refresh(false)
                console.log(res)
                s.$message.success('成功解除禁言')
              }
            })
            break
          case 1:
            unBlockPlayer({ playerId: s.record.playerId }).then(res => {
              if (res) {
                s.$refs.table1.refresh(false)
                s.$message.success('成功解除封号')
              }
            })
            break
        }
        removeBlockWord({ blockWord: this.keyWord }).then(res => {
          if (res) {
            this.delvisible = false
            this.keyWord = ''
            this.$refs.table.refresh(true)
          }
        })
      },
      handleDelCancel() {
        this.delvisible = false
      },
      handleOk() {
        let s = this
        s.$refs.table1.refresh(false)
      },
      openAlert(text = '') {
        let s = this
        s.title = '确认添加关键字'
        if (text) {
          s.keyWord = text
        }
        s.visible = true
      },
      handleSubmit() {
        let s = this
        addBlockWord({ blockWord: s.keyWord }).then(res => {
          if (res) {
            this.visible = false
            this.keyWord = ''
            s.$refs.table.refresh(true)
          }
        })
      },
      handleCancel() {
        this.visible = false
      },
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
    },
    destroyed: function() {
      if (this.scrollTimer) {
        clearInterval(this.scrollTimer)
      }
    },
    filters: {
      statusFilter(type) {
        return statusMap[type].text
      },
      statusTypeFilter(type) {
        return statusMap[type].status
      }
    }
  }
</script>
