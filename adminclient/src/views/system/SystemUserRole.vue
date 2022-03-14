<template>
  <a-card :bordered="false">
    <div class="table-operator">
      <a-button type="primary" icon="plus" @click="$refs.createModal.add()">添加角色用户关系</a-button>
      <a-button type="danger" icon="minus" @click="$refs.deleteModal.add()" style="margin-left: 18px;">删除角色用户关系</a-button>
    </div>
    <div class="table-operator">
      <a-row>
        <a-col :md="6">
          <a-input placeholder="请输入角色id" v-model="queryParam.roleId" />
        </a-col>
        <a-col :md="1">
          <span style="width:18px;"></span>
        </a-col>
        <a-col :md="6">
          <a-input placeholder="请输入用户id" v-model="queryParam.userId" />
        </a-col>
        <a-col :md="2">
          <span class="table-page-search-submitButtons">
            <a-button type="primary" style="margin-left: 18px" @click="seach">查询</a-button>
          </span>
        </a-col>
      </a-row>
    </div>
    <s-table
      ref="table1"
      size="default"
      :columns="columns1"
      :data="loadData1"
      :pagination="pagination"
      rowKey="id34131"
      v-show="showRole"
    >
      <span slot="status" slot-scope="status">
        <a-badge :status="status | statusTypeFilter" :text="status | statusFilter" />
      </span>
      <span slot="roleName" slot-scope="roleName">
        <a-tag
          v-for="role in roleName"
          :color="roleName === 'admin' ? 'volcano' : (roleName.length > 5 ? 'geekblue' : 'green')"
          :key="role"
        >{{role}}</a-tag>
      </span>
      <span slot="action" slot-scope="text, record">
        <a href="javascript:;" @click="openDelet(record)">删除该权限</a>
      </span>
    </s-table>
    <s-table
      ref="table2"
      size="default"
      :columns="columns2"
      :data="loadData2"
      :pagination="pagination"
      rowKey="id34131"
      v-show="!showRole"
    >
      <span slot="status" slot-scope="status">
        <a-badge :status="status | statusTypeFilter" :text="status | statusFilter" />
      </span>
      <span slot="roleName" slot-scope="roleName">
        <a-tag
          v-for="role in roleName"
          :color="roleName === 'admin' ? 'volcano' : (roleName.length > 5 ? 'geekblue' : 'green')"
          :key="role"
        >{{role}}</a-tag>
      </span>
      <span slot="action" slot-scope="text, record">
        <a href="javascript:;" @click="openDelet(record)">删除该权限</a>
      </span>
    </s-table>
    <create-form ref="createModal" @ok="handleOk" />
    <DeleteUserRoleModal ref="deleteModal"></DeleteUserRoleModal>
    <ChangeModal ref="changeModal" @ok="handleOk"></ChangeModal>
    <ResetRole ref="resetModal" @ok="handleOk"></ResetRole>
    <a-modal
      title="输入要删除的角色用户关系"
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
import CreateForm from './user/modules/AddUserRoleModal'
import DeleteUserRoleModal from './user/modules/DeleteUserRoleModal'
import ChangeModal from './user/modules/ChangeUserInfo'
import ResetRole from './user/modules/ResetRoleInfo'
// import { getAccessRight, deleteUser, resetOtherPassword, changeOwnPsd, addUser } from '@/api/manage'
import { getRolesByUser, getUserByRole, deleteRoleRight } from '@/api/user'

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
  name: 'SystemUserRole',
  components: {
    STable,
    CreateForm,
    DeleteUserRoleModal,
    ChangeModal,
    ResetRole
  },
  data() {
    return {
      showRole: true,
      roleId: 1,
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
      columns1: [
        {
          title: '创建时间',
          dataIndex: 'createTime',
          customRender: createTime => formatTimestamp(createTime)
        },
        {
          title: 'id',
          dataIndex: 'id'
        },
        {
          title: '邮件',
          dataIndex: 'email'
          //   scopedSlots: { customRender: 'status' }
          // customRender: auth => auth.join(',')
        },
        {
          title: '登录ip',
          dataIndex: 'loginIp'
          // scopedSlots: { customRender: 'action' }
        },
        {
          title: '登录时间',
          dataIndex: 'loginTime',
          customRender: createTime => formatTimestamp(createTime)
        },
        {
          title: '角色名',
          dataIndex: 'name'
          // scopedSlots: { customRender: 'action' }
        },
        {
          title: '手机',
          dataIndex: 'phone'
          // scopedSlots: { customRender: 'action' }
        },
        {
          title: '状态',
          dataIndex: 'status'
          // scopedSlots: { customRender: 'action' }
        },
        {
          title: '用户名',
          dataIndex: 'userName'
          // scopedSlots: { customRender: 'action' }
        },
        {
          title: '系统内置用户',
          dataIndex: 'systemUser',
          customRender: systemUser => (systemUser ? '是' : '否')
        }
      ],
      columns2: [
        {
          title: '创建时间',
          dataIndex: 'createTime',
          customRender: createTime => formatTimestamp(createTime)
        },
        {
          title: 'id',
          dataIndex: 'id'
        },
        {
          title: '操作',
          dataIndex: 'operator'
          //   scopedSlots: { customRender: 'status' }
          // customRender: auth => auth.join(',')
        },
        {
          title: '备注',
          dataIndex: 'remark'
          // scopedSlots: { customRender: 'action' }
        },
        {
          title: '角色名',
          dataIndex: 'roleName'
        },
        {
          title: '系统内置用户',
          dataIndex: 'systemRole',
          customRender: systemUser => (systemUser ? '是' : '否')
        },
        {
          title: '修改时间',
          dataIndex: 'updateTime',
          customRender: createTime => formatTimestamp(createTime)
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData1: parameter => {
        let s = this
        let pageNo = parameter.pageNo
        if (!s.queryParam.roleId) {
          return new Promise(function(resolve, reject) {
            setTimeout(() => {
              resolve('ok')
            }, 100)
          }).then(res => {
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
        return getUserByRole(s.queryParam).then(res => {
          let pageNo = parameter.pageNo
          res.pageNo = pageNo
          // res.totalPage = Math.ceil(res.contents.length / res.pageSize)
          // res.totalCount = res.contents.length
          return res
        })
      },
      loadData2: parameter => {
        let s = this
        let pageNo = parameter.pageNo
        if (!s.queryParam.userId) {
          return new Promise(function(resolve, reject) {
            setTimeout(() => {
              resolve('ok')
            }, 100)
          }).then(res => {
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
        return getRolesByUser(s.queryParam).then(res => {
          let obj = {}
          let pageNo = parameter.pageNo
          obj.pageNo = pageNo
          obj.pageSize = parameter.pageSize
          obj.totalPage = Math.ceil(res.length / obj.pageSize)
          obj.totalCount = res.length
          obj.contents = res
          return obj
        })
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
    seach() {
      this.roleId = this.queryParam.roleId
      if(this.queryParam.roleId) {
        this.showRole = true;
        this.$refs.table1.refresh()
      }else if(!this.queryParam.roleId && this.queryParam.userId) {
        this.showRole = false;
        this.$refs.table2.refresh()
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
      deleteRoleRight({ roleId: this.roleId, resourceId: this.deletId }).then(res => {
        // console.log('删除好友=======》',res)
        s.deletvisible = false
        s.$refs.table.refresh()
      })
    },
    openDelet(record) {
      this.deletText = '确认删除权限' + record.id
      this.deletId = record.id
      this.deletvisible = true
    },
    handleOk() {
      if(this.queryParam.roleId) {
        this.showRole = true;
        this.$refs.table1.refresh()
      }else if(!this.queryParam.roleId && this.queryParam.userId) {
        this.showRole = false;
        this.$refs.table2.refresh()
      }
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
