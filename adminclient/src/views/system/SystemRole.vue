<template>
  <a-card :bordered="false">
    <div class="table-operator">
      <a-button type="primary" icon="plus" @click="$refs.createModal.add()">创建角色</a-button>
    </div>

    <s-table
      ref="table"
      size="default"
      :columns="columns"
      :data="loadData"
      :pagination="pagination"
      rowKey="id34131"
    >
      <span slot="status" slot-scope="status">
        <a-badge :status="status | statusTypeFilter" :text="status | statusFilter" />
      </span>
      <span slot="roleNames" slot-scope="roleNames">
        <a-tag
          v-for="role in roleNames"
          :color="role === 'admin' ? 'volcano' : (role.length > 5 ? 'geekblue' : 'green')"
          :key="role"
        >
          {{ role }}
        </a-tag>
      </span>
      <span slot="action" slot-scope="text, record">
        <!-- <a @click="$refs.changeModal.show()">编辑</a> -->
        <!-- <a-divider type="vertical" /> -->
        <a-dropdown>
          <a class="ant-dropdown-link">
            更多
            <a-icon type="down" />
          </a>
          <a-menu slot="overlay">
            <a-menu-item>
              <a href="javascript:;" @click="$refs.resetModal.add()">设置角色信息</a>
            </a-menu-item>
            <a-menu-item>
              <a href="javascript:;" @click="openDelet(record)">删除角色 </a>
            </a-menu-item>
          </a-menu>
        </a-dropdown>
      </span>
    </s-table>
    <create-form ref="createModal" @ok="handleOk" />
    <DetailModal ref="detailModal"></DetailModal>
    <ChangeModal ref="changeModal" @ok="handleOk"></ChangeModal>
    <ResetRole ref="resetModal" @ok="handleOk"></ResetRole>
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
import CreateForm from './user/modules/CreateRoleInfo'
import DetailModal from './user/modules/UserDetailModal'
import ChangeModal from './user/modules/ChangeUserInfo'
import ResetRole from './user/modules/ResetRoleInfo'
// import { getAccessRight, deleteUser, resetOtherPassword, changeOwnPsd, addUser } from '@/api/manage'
import { getRoleList ,postDeleteRole } from '@/api/user'

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
  name: 'SystemRole',
  components: {
    STable,
    CreateForm,
    DetailModal,
    ChangeModal,
    ResetRole
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
      pagination: {
        position: 'bottom',
        pageSizeOptions: ['10', '20', '50','99999'],
        defaultPageSize: 10
      },
      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParam: {},
      // 表头
      columns: [
        {
          title: '角色id',
          dataIndex: 'id'
        },
        {
          title: '操作员',
          dataIndex: 'operator'
        },
        {
          title: '备注',
          dataIndex: 'remark'
          //   scopedSlots: { customRender: 'status' }
        },
        {
          title: '角色名',
          dataIndex: 'roleName',
          // key: 'roleNames',
          // scopedSlots: { customRender: 'roleNames' }
        },
        {
          title: '系统内置角色',
          dataIndex: 'systemRole',
          customRender: systemUser => (systemUser ? '是' : '否')
        },
        {
          title: '创建时间',
          dataIndex: 'createTime',
          customRender: createTime => formatTimestamp(createTime)
        },
        {
          title: '更新时间',
          dataIndex: 'updateTime',
          customRender: createTime => formatTimestamp(createTime)
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
        return getRoleList(parameter).then(res => {
          let pageNo = parameter.pageNo
          res.pageNo = pageNo
          // res.totalPage = Math.ceil(res.contents.length / res.pageSize)
          // res.totalCount = res.contents.length
          return res
        })
        // let url = 'http://192.168.2.46:9200/admin/systemRole/roleList'
        // url += '?pageNo=' + parameter.pageNo + '&pageSize=' + parameter.pageSize
        // console.log('url=========>',url);
        // return this.$ajax(url).then(res => {
        //   let pageSize = parameter.pageSize
        //   let pageNo = parameter.pageNo
        //   let obj = {}
        //   obj.pageNo = pageNo
        //   obj.pageSize = pageSize
        //   obj.totalPage = Math.ceil(res.data.result.data.length / pageSize)
        //   obj.totalCount = res.data.result.data.length
        //   obj.contents = res.data.result.data
        //   return obj
        // })
      },
      selectedRowKeys: [],
      selectedRows: []
    }
  },
  created() {
    // getAccessRight().then(res => {
    //   // console.log('permission=====================>',res);
    // })
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
      postDeleteRole({ roleId: this.deletId }).then(res => {
        // console.log('删除好友=======》',res)
        s.deletvisible = false
        s.$refs.table.refresh()
      })
    },
    openDelet(record) {
      this.deletText = '确认删除用户' + record.roleName
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
