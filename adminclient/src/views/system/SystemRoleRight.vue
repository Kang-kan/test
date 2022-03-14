<template>
  <a-card :bordered="false">
    <div class="table-operator">
      <a-button type="primary" icon="plus" @click="$refs.createModal.add()">添加权限</a-button>
    </div>
    <div class="table-operator">
      <a-row>
        <a-col :md="8">
          <a-input placeholder="请输入角色id" v-model="queryParam.roleId" />
        </a-col>
        <a-col :md="2">
          <span class="table-page-search-submitButtons">
            <a-button type="primary" style="margin-left: 8px" @click="seach">查询</a-button>
          </span>
        </a-col>
      </a-row>
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
      <span slot="roleName" slot-scope="roleName">
        <a-tag
          v-for="role in roleName"
          :color="roleName === 'admin' ? 'volcano' : (roleName.length > 5 ? 'geekblue' : 'green')"
          :key="role"
        >{{role}}</a-tag>
      </span>
      <span slot="action" slot-scope="text, record">
        <!-- <a @click="$refs.changeModal.show()">编辑</a> -->
        <!-- <a-divider type="vertical" /> -->
        <a href="javascript:;" @click="openDelet(record)">删除该权限</a>
        <!-- <a-dropdown>
          <a class="ant-dropdown-link">
            更多
            <a-icon type="down" />
          </a>
          <a-menu slot="overlay">
            <a-menu-item>
              <a href="javascript:;" @click="$refs.resetModal.add()">设置角色信息</a>
            </a-menu-item>
            <a-menu-item>
              <a href="javascript:;" @click="openDelet(record)">删除角色</a>
            </a-menu-item>
          </a-menu>
        </a-dropdown>-->
      </span>
    </s-table>
    <create-form ref="createModal" @ok="handleOk" />
    <DetailModal ref="detailModal"></DetailModal>
    <ChangeModal ref="changeModal" @ok="handleOk"></ChangeModal>
    <ResetRole ref="resetModal" @ok="handleOk"></ResetRole>
    <a-modal
      title="确认删除该权限"
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
import CreateForm from './user/modules/AddPemissionModal'
import DetailModal from './user/modules/UserDetailModal'
import ChangeModal from './user/modules/ChangeUserInfo'
import ResetRole from './user/modules/ResetRoleInfo'
// import { getAccessRight, deleteUser, resetOtherPassword, changeOwnPsd, addUser } from '@/api/manage'
import { getRoleRightById, deleteRoleRight } from '@/api/user'

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
  name: 'SystemRoleRight',
  components: {
    STable,
    CreateForm,
    DetailModal,
    ChangeModal,
    ResetRole
  },
  data() {
    return {
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
      columns: [
        {
          title: '权限id',
          dataIndex: 'id'
        },
        {
          title: '权限名',
          dataIndex: 'name'
        },
        {
          title: '权限',
          dataIndex: 'auth',
          //   scopedSlots: { customRender: 'status' }
          customRender: auth => auth.join(',')
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

        return getRoleRightById(Object.assign(parameter, this.queryParam)).then(res => {
          let pageNo = parameter.pageNo
          res.pageNo = pageNo
          // res.totalPage = Math.ceil(res.contents.length / res.pageSize)
          // res.totalCount = res.contents.length
          res.contents.sort((a, b) => {
            return a.id - b.id
          })
          return res
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
      this.$refs.table.refresh()
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
