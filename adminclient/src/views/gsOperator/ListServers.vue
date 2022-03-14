<template>
  <a-card :bordered="false">
    <div class="table-operator">
      <a-button type="primary" icon="plus" @click="$refs.setNotice.add()">设置登录服关闭提示</a-button>
    </div>

    <s-table
      ref="table"
      size="default"
      :columns="columns"
      :data="loadData"
      rowKey="id"
    >
      <span slot="status" slot-scope="status">
        <a-badge :status="status | statusTypeFilter" :text="status | statusFilter" />
      </span>
      <span slot="roleNames" slot-scope="roleNames">
        <a-tag
          v-for="roleName in roleNames"
          :color="roleName === 'admin' ? 'volcano' : (roleName.length > 5 ? 'geekblue' : 'green')"
          :key="roleName"
        >
          {{ roleName }}
        </a-tag>
      </span>
      <span slot="action" slot-scope="text, record">
        <a @click="opensend(record)">发送</a>
        <a-divider type="vertical" />
        <a-dropdown>
          <a class="ant-dropdown-link">
            更多 <a-icon type="down" />
          </a>
          <a-menu slot="overlay">
            <a-menu-item>
              <a href="javascript:;" @click="$refs.resetModal.show(record)">修改</a>
            </a-menu-item>
            <a-menu-item>
              <a href="javascript:;" @click="openDelet(record)">删除</a>
            </a-menu-item>
          </a-menu>
        </a-dropdown>
      </span>
    </s-table>
    <SetServerNotice ref="setNotice" @ok="handleOk"></SetServerNotice>
   
    <a-modal title="确认删除该邮件" :visible="deletvisible" @ok="handleDelet" @cancel="deletvisible = false">
      <p>{{deletText}}</p>
    </a-modal>
    <a-modal title="确认发送该邮件" :visible="sendvisible" @ok="handlesend" @cancel="sendvisible = false">
      <p>{{sendText}}</p>
    </a-modal>
  </a-card>
</template>

<script>
import xmlObj from '@/config/xmlConfig'
import { formatTimestamp } from '@/utils/util'
import { STable } from '@/components'
import SetServerNotice from './modules/SetServerNotice'
import {getAccessRight} from '@/api/manage'
import {
  getGslistServers,
  setLoginServerStopDesc,
} from '@/api/user'

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
  name: 'ListServers',
  components: {
    STable,
    SetServerNotice
  },
  data() {
    return {
      mailType: 0,
      description: 'desc.',
      deletvisible: false,
      deletText: '',
      deletId: '',
      sendvisible: false,
      sendText: '',
      sendId: '',
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
          title: '开服时间',
          dataIndex: 'openTime',
          customRender: openTime => (openTime > 0 ? formatTimestamp(openTime) : '')
        },
        {
          title: '服务器名',
          dataIndex: 'name'
        },
        {
          title: '合服后服务器id',
          dataIndex: 'mergeSrvId'
        },
        {
          title: '合服时间',
          dataIndex: 'mergeTime',
          customRender: mergeTime => (mergeTime > 0 ? formatTimestamp(mergeTime) : '')
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        let s = this
        return getGslistServers(parameter).then(res => {
          if (!res || !res.contents || res.contents.length == 0) {
            return {
              pageSize: 10,
              pageNo: 0,
              totalPage: 0,
              totalCount: 0,
              contents: []
            }
          }
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
    handlesend() {
      let s = this
      sendRewardMail({ rewardMailId: this.sendId }).then(res => {
        // console.log('删除好友=======》',res)
        if(res != null) {
          s.$message.success('成功发送奖励邮件')
        }
        s.sendvisible = false
        s.$refs.table.refresh()
      })
    },
    openDelet(record) {
      console.log('删除邮件',record)
      this.deletText = '确定删除邮件' + record.id
      this.deletId = record.id
      this.deletvisible = true
    },
    handleDelet() {
      let s = this
      deleteRewardMail({ rewardMailId: this.deletId }).then(res => {
        console.log('删除邮件=======》',res)
        if(res != null) {
          s.$message.success('成功删除奖励邮件')
        }
        s.deletvisible = false
        s.$refs.table.refresh()
      })
    },
    opensend(record) {
      this.sendText = '确定发送邮件' + record.id
      this.sendId = record.id
      this.sendvisible = true
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
