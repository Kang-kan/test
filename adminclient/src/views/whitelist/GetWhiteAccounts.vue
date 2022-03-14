<template>
  <a-card :bordered="false">
    <div class="table-operator">
      <a-button type="primary" icon="plus" @click="$refs.createModal.add(1)">增加白名单用户</a-button>
      <a-button type="primary" icon="plus" @click="$refs.createModal.add(2)">增加白名单ip</a-button>
      <a-button type="primary" icon="plus" @click="$refs.createModal.add(3)">增加黑名单ip</a-button>
    </div>
    <div class="table-operator">
      <span :color="logincolor">{{loginstatustxt}}</span>
      <a-switch
        checkedChildren="开"
        unCheckedChildren="关"
        v-model="gatestatus"
        @change="gateChange"
      />
    </div>
    <a-tabs defaultActiveKey="1" @change="tabChange">
      <a-tab-pane tab="白名单用户" key="1">
        <s-table ref="table1" size="default" :columns="columns1" :data="loadData1" rowKey="id">
          <span slot="status" slot-scope="status">
            <a-badge :status="status | statusTypeFilter" :text="status | statusFilter" />
          </span>
          <span slot="roleNames" slot-scope="roleNames">
            <a-tag
              v-for="roleName in roleNames"
              :color="roleName === 'admin' ? 'volcano' : (roleName.length > 5 ? 'geekblue' : 'green')"
              :key="roleName"
            >{{ roleName }}</a-tag>
          </span>
          <span slot="action" slot-scope="text, record">
            <a @click="openDelet(record,1)">删除</a>
          </span>
        </s-table>
      </a-tab-pane>
      <a-tab-pane tab="白名单ip" key="2" forceRender>
        <s-table ref="table2" size="default" :columns="columns2" :data="loadData2" rowKey="id">
          <span slot="status" slot-scope="status">
            <a-badge :status="status | statusTypeFilter" :text="status | statusFilter" />
          </span>
          <span slot="roleNames" slot-scope="roleNames">
            <a-tag
              v-for="roleName in roleNames"
              :color="roleName === 'admin' ? 'volcano' : (roleName.length > 5 ? 'geekblue' : 'green')"
              :key="roleName"
            >{{ roleName }}</a-tag>
          </span>
          <span slot="action" slot-scope="text, record">
            <a @click="openDelet(record,2)">删除</a>
          </span>
        </s-table>
      </a-tab-pane>
      <a-tab-pane tab="黑名单ip" key="3">
        <s-table ref="table3" size="default" :columns="columns2" :data="loadData3" rowKey="id">
          <span slot="status" slot-scope="status">
            <a-badge :status="status | statusTypeFilter" :text="status | statusFilter" />
          </span>
          <span slot="roleNames" slot-scope="roleNames">
            <a-tag
              v-for="roleName in roleNames"
              :color="roleName === 'admin' ? 'volcano' : (roleName.length > 5 ? 'geekblue' : 'green')"
              :key="roleName"
            >{{ roleName }}</a-tag>
          </span>
          <span slot="action" slot-scope="text, record">
            <a @click="openDelet(record,3)">删除</a>
          </span>
        </s-table>
      </a-tab-pane>
    </a-tabs>

    <create-form ref="createModal" @ok="handleOk" />
    <ChangeModal ref="changeModal" @ok="handleOk"></ChangeModal>
    <UpadataData ref="updatadata" @ok="handleOk"></UpadataData>
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
  import CreateForm from './modules/AddWhite'
  import { getAccessRight } from '@/api/manage'
  import {
    addWhiteAccount,
    getLoginGateStatus,
    getWhiteAccounts,
    removeWhiteAccount,
    setLoginGateStatus,
    addBlackIp,
    addWhiteIp,
    getBlackIps,
    getWhiteIps,
    removeBlackIp,
    removeWhiteIp
  } from '@/api/user'

  export default {
    name: 'GetWhiteAccounts',
    components: {
      STable,
      CreateForm
    },
    data() {
      return {
        tab: 1,
        gatestatus: true,
        deletTitle: '',
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
        queryParam: {},
        // 表头
        // 白名单用户
        columns1: [
          {
            title: '账号',
            dataIndex: 'account'
          },
          {
            title: '备注',
            dataIndex: 'remark'
          },
          {
            title: '操作',
            width: '150px',
            dataIndex: 'action',
            scopedSlots: { customRender: 'action' }
          }
        ],
        // 白名单ip 黑名单
        columns2: [
          {
            title: 'ip',
            dataIndex: 'ip'
          },
          {
            title: '备注',
            dataIndex: 'remark'
          },
          {
            title: '操作',
            width: '150px',
            dataIndex: 'action',
            scopedSlots: { customRender: 'action' }
          }
        ],
        // 加载数据方法 必须为 Promise 对象
        // 白名单用户
        loadData1: parameter => {
          let pageNo = parameter.pageNo
          let pageSize = parameter.pageSize
          return getWhiteAccounts(parameter).then(res => {
            let obj = {}
            obj.totalPage = Math.ceil(res.length / pageSize)
            obj.totalCount = res.length
            obj.pageNo = pageNo
            obj.pageSize = pageSize
            obj.contents = res
            return obj
          })
        },
        // 白名单ip
        loadData2: parameter => {
          let pageNo = parameter.pageNo
          let pageSize = parameter.pageSize
          return getWhiteIps(parameter).then(res => {
            let obj = {}
            obj.totalPage = Math.ceil(res.length / pageSize)
            obj.totalCount = res.length
            obj.pageNo = pageNo
            obj.pageSize = pageSize
            obj.contents = res
            return obj
          })
        },
        // 黑名单ip
        loadData3: parameter => {
          let pageNo = parameter.pageNo
          let pageSize = parameter.pageSize
          return getBlackIps(parameter).then(res => {
            let obj = {}
            obj.totalPage = Math.ceil(res.length / pageSize)
            obj.totalCount = res.length
            obj.pageNo = pageNo
            obj.pageSize = pageSize
            obj.contents = res
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
      getLoginGateStatus().then(res => {
        if (res.code == 0 || res === true) {
          let open = res.code === 0 ? res.content : res
          s.$nextTick(() => {
            s.gatestatus = open
            if (open) {
              s.loginstatustxt = '登录服入口状态：' + '开启   '
              s.logincolor = '#041d8d'
            } else {
              s.loginstatustxt = '登录服入口状态：' + '关闭   '
              s.logincolor = '#cf1111'
            }
            console.log('状态: ', s.loginstatustxt, open)
          })
        }
      })
    },
    methods: {
      tabChange(key) {
        this.tab = Number(key)
        this.$refs['table' + this.tab].refresh()
      },
      gateChange(value) {
        let s = this
        setLoginGateStatus({ open: value }).then(res => {
          console.log('shezhi', res)
          if (res.code == 0 || res === true) {
            let open = res.code === 0 ? res.content : res
            s.$nextTick(() => {
              s.gatestatus = open
              if (open) {
                s.loginstatustxt = '登录服入口状态：' + '开启   '
                s.logincolor = '#041d8d'
              } else {
                s.loginstatustxt = '登录服入口状态：' + '关闭   '
                s.logincolor = '#cf1111'
              }
              console.log('状态: ', s.loginstatustxt, open)
            })
          }
        })
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
        switch (s.tab) {
          case 1:
            removeWhiteAccount({ account: this.deletId }).then(res => {
              // console.log('删除好友=======》',res)
              s.deletvisible = false
              s.$refs.table1.refresh()
            })
            break
          case 2:
            removeWhiteIp({ ip: this.deletId }).then(res => {
              // console.log('删除好友=======》',res)
              s.deletvisible = false
              s.$refs.table2.refresh()
            })
            break
          case 3:
            removeBlackIp({ ip: this.deletId }).then(res => {
              // console.log('删除好友=======》',res)
              s.deletvisible = false
              s.$refs.table3.refresh()
            })
            break
        }
      },
      openDelet(record, key) {
        let str = '';
        let title = '';
        let id = '';
        switch (key) {
          case 1:
            str = '确认删除白名单用户: ' + record.account
            title = '删除白名单用户'
            id = record.account
            break
          case 2:
            str = '确认删除白名单ip: ' + record.ip
            title = '删除白名单ip'
            id = record.ip
            break
          case 3:
            str = '确认删除黑名单ip: ' + record.ip
            title = '删除黑名单ip'
            id = record.ip
            break
        }
        this.deletText = str
        this.deletId = id
        this.deletvisible = true
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
    mounted() {}
  }
</script>
