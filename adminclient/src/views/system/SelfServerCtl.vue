<template>
  <a-card :bordered="false">
    <div class="table-operator">
      <a-row>
        <a-col :md="6">
          <a-input placeholder="请输入角色id" v-model="queryParam.roleId" />
        </a-col>
        <a-col :md="3" :sm="24">
          <span class="table-page-search-submitButtons">
            <a-button type="primary" style="margin-left: 8px" @click="seach">查询</a-button>
          </span>
        </a-col>
      </a-row>
    </div>
    <div class="table-operator">
      <a-button type="primary" icon="plus" @click="$refs.createModal.add()">添加服务器</a-button>
    </div>
    <s-table ref="table" size="default" :columns="columns" :data="loadData" rowKey="id">
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
        <a href="javascript:;" @click="openDelet(record)">删除</a>
        <!-- <a-divider type="vertical" />
        <a-dropdown>
          <a class="ant-dropdown-link">
            更多
            <a-icon type="down" />
          </a>
          <a-menu slot="overlay">
            <a-menu-item>
              <a href="javascript:;" @click="$refs.detailModal.show(record)">详情</a>
            </a-menu-item>
            <a-menu-item>
              <a href="javascript:;" @click="$refs.resetModal.show(record.id)">重置密码</a>
            </a-menu-item>
            <a-menu-item>
              <a href="javascript:;" @click="openDelet(record)">删除</a>
            </a-menu-item>
          </a-menu>
        </a-dropdown>-->
      </span>
    </s-table>
    <create-form ref="createModal" @ok="handleOk" />
    <ResetPwdModal ref="resetModal" @ok="handleOk"></ResetPwdModal>
    <a-modal title="操作" style="top: 20px;" :width="800" v-model="visible" @ok="handleOk">
      <a-form :autoFormCreate="(form)=>{this.form = form}">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="唯一识别码"
          hasFeedback
          validateStatus="success"
        >
          <a-input placeholder="唯一识别码" v-model="mdl.id" id="id" disabled="disabled" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="角色名称"
          hasFeedback
          validateStatus="success"
        >
          <a-input placeholder="起一个名字" v-model="mdl.name" id="roleName" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="状态"
          hasFeedback
          validateStatus="warning"
        >
          <a-select v-model="mdl.status">
            <a-select-option value="1">正常</a-select-option>
            <a-select-option value="2">禁用</a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="描述" hasFeedback>
          <a-textarea :rows="5" v-model="mdl.describe" placeholder="..." id="describe" />
        </a-form-item>

        <a-divider />

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="拥有权限" hasFeedback>
          <a-row :gutter="16" v-for="(permission, index) in mdl.permissions" :key="index">
            <a-col :span="4">{{ permission.permissionName }}：</a-col>
            <a-col :span="20">
              <a-checkbox-group :options="permission.actionsOptions" />
            </a-col>
          </a-row>
        </a-form-item>
      </a-form>
    </a-modal>
    <a-modal
      title="确认删除该用户"
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
import CreateForm from './user/modules/AddServer'
import ResetPwdModal from './user/modules/ResetPwdModal'
import { getAccessRight } from '@/api/manage'
import { listRoleServers, removeRoleServer } from '@/api/user'
const statusMap = {
  0: {
    status: 'error',
    text: '禁用'
  },
  1: {
    status: 'processing',
    text: '启用'
  }
}

export default {
  name: 'SelfServerCtl',
  components: {
    STable,
    CreateForm,
    ResetPwdModal
  },
  data() {
    return {
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
      queryParam: {

      },
      // 表头
      columns: [
        {
          title: '服务器名',
          dataIndex: 'name'
        },
        {
          title: '服务器id',
          dataIndex: 'serverId'
        },
        {
          title: '操作',
          width: '150px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        let s = this
        if (!s.queryParam.roleId) {
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
        return listRoleServers(Object.assign(parameter,s.queryParam)).then(res => {
          return res
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
  },
  methods: {
    seach() {
      this.deletId = this.queryParam.roleId
      this.$refs.table.refresh(true)
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
      removeRoleServer({ userId: this.deletId ,serverId: this.deletServer}).then(res => {
        // console.log('删除好友=======》',res)
        s.deletvisible = false
        s.$refs.table.refresh()
      })
    },
    openDelet(record) {
      this.deletText = '确认删除用户' + record.name
      this.deletServer = record.serverId
      this.deletvisible = true
    },
    handleOk() {
      this.$refs.table.refresh()
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
  watch: {}
}
</script>
