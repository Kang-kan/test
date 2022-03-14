<template>
  <a-card :bordered="false">
    <div class="table-operator">
      <a-button type="primary" icon="plus" @click="$refs.createModal.add()">添加服务器</a-button>
      <a-button type="primary" icon="plus" @click="$refs.updatadata.add()">刷新数值配置表</a-button>
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
        <a @click="$refs.changeModal.show(record)">修改</a>
      </span>
    </s-table>
    <create-form ref="createModal" @ok="handleOk" />
    <DetailModal ref="detailModal"></DetailModal>
    <ChangeModal ref="changeModal" @ok="handleOk"></ChangeModal>
    <ResetPwdModal ref="resetModal" @ok="handleOk"></ResetPwdModal>
    <UpadataData ref="updatadata" @ok="handleOk"></UpadataData>
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
import CreateForm from './user/modules/AddAllServer'
import DetailModal from './user/modules/UserDetailModal'
import ChangeModal from './user/modules/ChangeServer'
import ResetPwdModal from './user/modules/ResetPwdModal'
import UpadataData from './modules/UpadataData'
import { getAccessRight } from '@/api/manage'
import { getAddServer, getListServers, getUpdateServer } from '@/api/user'
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
  name: 'ServerController',
  components: {
    STable,
    CreateForm,
    DetailModal,
    ChangeModal,
    ResetPwdModal,
    UpadataData
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
      queryParam: {},
      // 表头
      columns: [
        {
          title: '服务器id',
          dataIndex: 'id'
        },
        {
          title: '平台id',
          dataIndex: 'operatorId'
        },
        {
          title: '服务器名',
          dataIndex: 'name'
        },
        {
          title: '端口',
          dataIndex: 'port'
        },
        {
          title: '充值端口',
          dataIndex: 'chargePort'
        },
        {
          title: 'GM端口',
          dataIndex: 'gmPort'
        },
        {
          title: '内网ip',
          dataIndex: 'localIp'
        },
        {
          title: '开服时间',
          dataIndex: 'openTime',
          width: '150px',
          customRender: openTime => openTime > 0 ? formatTimestamp(openTime) : ''
        },
        {
          title: '定时开服时间',
          dataIndex: 'schedOpenTime',
          customRender: schedOpenTime => schedOpenTime > 0 ? formatTimestamp(schedOpenTime) : ''
        },
        {
          title: '合服后服务器id',
          dataIndex: 'mergeSrvId',
          width: '100px',
        },
        {
          title: '合服时间',
          dataIndex: 'mergeTime',
          customRender: mergeTime => mergeTime > 0 ? formatTimestamp(mergeTime) : ''
        },
        {
          title: '创建时间',
          dataIndex: 'createTime',
          customRender: createTime => createTime > 0 ? formatTimestamp(createTime) : ''
        },
        {
          title: '更新时间',
          dataIndex: 'updateTime',
          customRender: updateTime => updateTime > 0 ? formatTimestamp(updateTime) : ''
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
        return getListServers(parameter).then(res => {
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
      deleteUser({ userId: this.deletId }).then(res => {
        // console.log('删除好友=======》',res)
        s.deletvisible = false
        s.$refs.table.refresh()
      })
    },
    openDelet(record) {
      this.deletText = '确认删除用户' + record.name
      this.deletId = record.id
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
