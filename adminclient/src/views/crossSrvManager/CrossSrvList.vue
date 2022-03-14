<template>
  <a-card :bordered="false">
    <!-- <div class="table-operator">
      <a-button type="primary" icon="plus" @click="$refs.createModal.add(1)">增加白名单用户</a-button>
      <a-button type="primary" icon="plus" @click="$refs.createModal.add(2)">增加白名单ip</a-button>
      <a-button type="primary" icon="plus" @click="$refs.createModal.add(3)">增加黑名单ip</a-button>
    </div>-->
    <!-- <div class="table-operator">
      <span :color="logincolor">{{loginstatustxt}}</span>
      <a-switch
        checkedChildren="开"
        unCheckedChildren="关"
        v-model="gatestatus"
        @change="gateChange"
      />
    </div>-->
    <a-tabs defaultActiveKey="1" @change="tabChange">
      <a-tab-pane tab="跨服服务器列表" key="1">
        <a-button type="primary" icon="plus" @click="$refs.addWorld.show()">添加世界服</a-button>
        <s-table ref="table1" size="default" :columns="columns1" :data="loadData1" rowKey="id1">
          <span slot="status" slot-scope="status">
            <a-badge :status="status | statusTypeFilter" :text="status | statusFilter" />
          </span>
          <span slot="action" slot-scope="text, record">
            <a @click="$refs.updataworld.show(record)">修改</a>
            <a-divider type="vertical" />
            <a @click="openDelet(record,1)">删除</a>
          </span>
        </s-table>
      </a-tab-pane>
      <a-tab-pane tab="游戏服配置列表" key="2" forceRender>
        <s-table ref="table2" size="default" :columns="columns2" :data="loadData2" rowKey="id2">
          <span slot="status" slot-scope="status">
            <a-badge :status="status | statusTypeFilter" :text="status | statusFilter" />
          </span>
          <span slot="action" slot-scope="text, record">
            <a @click="openDelet(record,2)">删除</a>
          </span>
        </s-table>
      </a-tab-pane>
      <a-tab-pane tab="节点分组列表" key="3">
        <div class="table-operator">
          <a-form layout="inline">
            <a-row>
              <a-col :md="5" :sm="24" style="padding-right: 18px;">
                <a-input placeholder="请输入节点id" v-model="queryParam.nodeId" />
              </a-col>
              <a-col :md="2" :sm="24">
                <span class="table-page-search-submitButtons">
                  <a-button type="primary" style="margin-left: 8px" @click="seach">查询节点信息</a-button>
                </span>
              </a-col>
            </a-row>
          </a-form>
        </div>
        <s-table ref="table3" size="default" :columns="columns3" :data="loadData3" rowKey="id3">
          <span slot="status" slot-scope="status">
            <a-badge :status="status | statusTypeFilter" :text="status | statusFilter" />
          </span>
          <span slot="action" slot-scope="text, record">
            <a @click="$refs.updatanode.show(record)">修改</a>
            <a-divider type="vertical" />
            <a @click="openDelet(record,3)">删除</a>
          </span>
        </s-table>
      </a-tab-pane>
    </a-tabs>
    <UpdataNode ref="updatanode" @ok="handleOk"></UpdataNode>
    <UpdataWorld ref="updataworld" @ok="handleOk"></UpdataWorld>
    <AddWorld ref="addWorld" @ok="handleOk"></AddWorld>
    <a-modal
      :title="deletTitle"
      :visible="deletvisible"
      @ok="handleDelet"
      @cancel="deletvisible = false"
    >
      <p>{{deletText}}</p>
    </a-modal>
  </a-card>
</template>

<script>
  import { formatTimestamp } from '@/utils/util'
  import { STable } from '@/components'
  import { getAccessRight } from '@/api/manage'
  import UpdataWorld from './modules/UpdataWorld'
  import AddWorld from './modules/AddWorld'
  import UpdataNode from './modules/UpdataNode'
  import {
    deleteNode,
    deleteWorldSrv,
    getListGameSrvInfos,
    getNodeInfo,
    getWorldSrvInfo,
    getServerInfo
  } from '@/api/user'

  export default {
    name: 'CrossSrvList',
    components: {
      STable,
      UpdataWorld,
      UpdataNode,
      AddWorld
    },
    data() {
      return {
        tab: 1,
        gatestatus: true,
        deletTitle: '',
        serverList: [],
        loginstatustxt: '登录服入口状态：开启   ',
        logincolor: '#041d8d',
        description: 'desc.',
        deletvisible: false,
        deletText: '',
        deletId: '',
        visible: false,
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 }
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 }
        },
        form: null,
        mdl: {},
        // 高级搜索 展开/关闭
        advanced: false,
        // 查询参数
        queryParam: { nodeId: null },
        // 表头
        // 世界服
        columns1: [
          {
            title: '世界服id',
            dataIndex: 'worldId'
          },
          {
            title: '名字',
            dataIndex: 'name'
          },
          {
            title: '开始节点',
            dataIndex: 'beginNode'
          },
          {
            title: '结束节点',
            dataIndex: 'endNode'
          },
          {
            title: 'socket地址',
            dataIndex: 'socketHost'
          },
          {
            title: 'socket端口',
            dataIndex: 'socketPort'
          },
          {
            title: 'web地址',
            dataIndex: 'webHost'
          },
          {
            title: 'web端口',
            dataIndex: 'webPort'
          },
          {
            title: '行动',
            width: '150px',
            dataIndex: 'action',
            scopedSlots: { customRender: 'action' }
          }
        ],
        // 游戏服
        columns2: [
          {
            title: '游戏服id',
            dataIndex: 'serverId'
          },
          {
            title: '世界服id',
            dataIndex: 'worldId'
          },
          {
            title: '世界服名',
            dataIndex: 'worldName'
          },
          {
            title: '节点分组',
            dataIndex: 'node'
          },
          {
            title: '节点基准时间',
            dataIndex: 'baseTime',
            customRender: (baseTime) => formatTimestamp(baseTime)
          }
        ],
        // 节点
        columns3: [
          {
            title: '节点分组',
            dataIndex: 'node'
          },
          {
            title: '节点包含的服务器',
            dataIndex: 'nodeServers'
          },
          {
            title: '节点基准时间',
            dataIndex: 'baseTime',
            customRender: (baseTime) => formatTimestamp(baseTime)
          },
          {
            title: '操作',
            width: '150px',
            dataIndex: 'action',
            scopedSlots: { customRender: 'action' }
          }
        ],
        // 加载数据方法 必须为 Promise 对象
        // 世界服信息
        loadData1: parameter => {
          let pageNo = parameter.pageNo
          let pageSize = parameter.pageSize
          return getWorldSrvInfo(parameter).then(res => {
            if (!res) {
              return {
                pageSize: 10,
                pageNo: 0,
                totalPage: 0,
                totalCount: 0,
                contents: []
              }
            }
            let s = this
            let data = {}
            // let len1 = Object.getOwnPropertyNames(res.nodeServers).length;
            let len = Object.getOwnPropertyNames(res.worldServers).length
            // let len = Math.max(len1,len2);
            let arr = []
            for(let key in res.worldServers) {
              arr.push(res.worldServers[key])
            }
            let obj = {}
            obj.totalPage = 1
            obj.totalCount = 1
            obj.pageNo = 1
            obj.pageSize = 10
            obj.contents = arr
            console.log('obj========>',obj)
            return obj
          })
        },
        // 游戏服信息
        loadData2: parameter => {
          let pageNo = parameter.pageNo
          let pageSize = parameter.pageSize
          let s = this;
          return getListGameSrvInfos(parameter).then(res => {
            let obj = {}
            if (!res || res.length == 0) {
              return {
                pageSize: 10,
                pageNo: 0,
                totalPage: 0,
                totalCount: 0,
                contents: []
              }
            }
            obj.totalPage = Math.ceil(res.length / pageSize)
            obj.totalCount = res.length
            obj.pageNo = pageNo
            obj.pageSize = pageSize
            res.forEach(el => {
              s.serverList.forEach(server => {
                if(server.id == el.serverId) {
                  el.serverId = `${server.name}(${server.id})`
                }
              })
            })
            res.sort((a,b) => (a.worldId - b.worldId))
            obj.contents = res
            return obj
          })
        },
        // 节点信息
        loadData3: parameter => {
          let pageNo = parameter.pageNo
          let pageSize = parameter.pageSize
          let s = this
          if (!s.queryParam.nodeId) {
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
          return getNodeInfo(s.queryParam).then(res => {
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
            obj.totalPage = 1
            obj.totalCount = 1
            obj.pageNo = 1
            obj.pageSize = 10
            for (let k in res.nodeServers) {
              let list = res.nodeServers[k]
              list.forEach((el, index) => {
                s.serverList.forEach(server => {
                  if (server.id == el) {
                    list[index] = `${server.name}(${server.id})`
                  }
                })
              })
              res.nodeServers = list.join(', ')
              break
            }
            obj.contents = [res]
            return obj
          })
        },
        selectedRowKeys: [],
        selectedRows: []
      }
    },
    created() {
      getAccessRight().then(res => {
        // console.log('permission=====================>',res);
      })
      let s = this
    },
    methods: {
      seach() {
        this.$refs.table3.refresh()
      },
      tabChange(key) {
        let s = this
        this.tab = Number(key)
        if (s.$refs['table' + s.tab]) {
          s.$refs['table' + s.tab].refresh()
        } else {
          s.$nextTick(() => {
            s.$refs['table' + s.tab].refresh()
          })
        }
      },
      handleEdit(record) {
        this.mdl = Object.assign({}, record)
        // console.log('record==========>',record)
        this.mdl.permissions.forEach(permission => {
          permission.actionsOptions = permission.actionEntitySet.map(action => {
            return { label: action.describe, value: action.action, defaultCheck: action.defaultCheck }
          })
        })
        this.visible = true
      },
      handleDelet() {
        let s = this
        if (!s.deletId || !s.tab) {
          s.deletvisible = false
          return
        }
        switch (Number(s.tab)) {
          case 1:
            deleteWorldSrv({ worldId: s.deletId }).then(res => {
              s.deletvisible = false
              if (res.code == 0) {
                s.$refs.table1.refresh()
              }
            })
            break
          case 2:
            break
          case 3:
            deleteNode({ node: s.deletId }).then(res => {
              s.deletvisible = false
              if (res.code == 0) {
                s.$refs.table3.refresh()
              }
            })
            break
        }
      },
      openDelet(record, key) {
        let s = this
        s.deletvisible = true
        switch (key) {
          case 1:
            s.deletText = '确定删除世界服：' + record.worldId
            s.deletTitle = '删除世界服'
            s.deletId = record.worldId
            break
          case 2:
            break
          case 3:
            s.deletText = '确定删除节点：' + record.beginNode
            s.deletTitle = '删除节点'
            s.deletId = record.beginNode
            break
        }
      },
      handleOk() {
        this.$refs['table' + this.tab].refresh()
      },
      onChange(selectedRowKeys, selectedRows) {
        this.selectedRowKeys = selectedRowKeys
        this.selectedRows = selectedRows
      },
      toggleAdvanced() {
        this.advanced = !this.advanced
      }
    },
    filters: {
      statusFilter(type) {
        return statusMap[type].text
      },
      statusTypeFilter(type) {
        return statusMap[type].status
      }
    },
    mounted: function() {
      let s = this
      this.$http.get('/roleGameServer/getMyServers').then(res => {
        s.serverList = res
      })
    }
  }
</script>
