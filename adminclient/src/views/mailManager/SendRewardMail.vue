<template>
  <a-card :bordered="false">
    <div class="table-operator">
      <a-button type="primary" icon="plus" @click="$refs.createModal.add()">创建奖励邮件</a-button>
    </div>

    <s-table
      ref="table"
      size="default"
      :columns="columns"
      :data="loadData"
      rowKey="id"
      :scroll="{x: 2000}"
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
        <a @click="opensend(record)" v-if="(record.reviewer || record.reviewTime) && !record.sendTime">发送</a>
        <span v-if="(record.reviewer || record.reviewTime) && record.sendTime">已发送</span>
        <span v-if="!record.reviewer && !record.reviewTime">未审核</span>
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
    <create-form ref="createModal" @ok="handleOk" />
    <DetailModal ref="detailModal"></DetailModal>
    <ChangeModal ref="changeModal" @ok="handleOk"></ChangeModal>
    <ResetPwdModal ref="resetModal" @ok="handleOk"></ResetPwdModal>
    <a-modal
      title="操作"
      style="top: 20px;"
      :width="800"
      v-model="visible"
      @ok="handleOk"
    >
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

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="描述"
          hasFeedback
        >
          <a-textarea :rows="5" v-model="mdl.describe" placeholder="..." id="describe"/>
        </a-form-item>
        <a-divider />
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="拥有权限"
          hasFeedback
        >
          <a-row :gutter="16" v-for="(permission, index) in mdl.permissions" :key="index">
            <a-col :span="4">
              {{ permission.permissionName }}：
            </a-col>
            <a-col :span="20">
              <a-checkbox-group :options="permission.actionsOptions"/>
            </a-col>
          </a-row>
        </a-form-item>
      </a-form>
    </a-modal>
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
import CreateForm from './modules/createRewardMail'
import DetailModal from '../system/user/modules/UserDetailModal'
import ChangeModal from '../system/user/modules/ChangeUserInfo'
import ResetPwdModal from './modules/setRewardMail'
import {getAccessRight} from '@/api/manage'
import {
  getListRewardMail,
  createRewardMail,
  deleteRewardMail,
  updateRewardMail,
  sendRewardMail
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
  name: 'SendRewardMail',
  components: {
    STable,
    CreateForm,
    DetailModal,
    ChangeModal,
    ResetPwdModal
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
          title: '邮件id',
          dataIndex: 'id'
        },
        {
          title: '创建时间',
          dataIndex: 'createTime',
          customRender: time => (time > 0 ? formatTimestamp(time) : '')
        },
        ,
        {
          title: '邮件标题',
          dataIndex: 'title'
        },
        {
          title: '邮件内容',
          dataIndex: 'content'
        },
        {
          title: '创建者',
          dataIndex: 'creator'
        },
        {
          title: '结束时间',
          dataIndex: 'endTime',
          customRender: time => (time > 0 ? formatTimestamp(time) : '')
        },
        {
          title: '备注',
          dataIndex: 'remark'
        },
        {
          title: '审核时间',
          dataIndex: 'reviewTime',
          customRender: time => (time > 0 ? formatTimestamp(time) : '')
        },
        {
          title: '审核人',
          dataIndex: 'reviewer'
        },
        {
          title: '奖励',
          dataIndex: 'rewards'
        },
        {
          title: '玩家列表',
          dataIndex: 'players',
          customRender: players => {
            let str = ''
            if(players) {
                players.forEach(el => {
                str += el.playerName + ','
              })
            }
            return str
          }
        },
        {
          title: '发送时间',
          dataIndex: 'sendTime',
          customRender: time => (time > 0 ? formatTimestamp(time) : '')
        },
        {
          title: '服务器id列表',
          dataIndex: 'serverIds'
        },
        {
          title: '开始时间',
          dataIndex: 'startTime',
          customRender: time => (time > 0 ? formatTimestamp(time) : '')
        },
        {
          title: '服务器id列表',
          dataIndex: 'serverIds'
        },
        {
          title: '行动',
          width: '150px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        let s = this
        return getListRewardMail(parameter).then(res => {
          if (!res || !res.contents || res.contents.length == 0) {
            return {
              pageSize: 10,
              pageNo: 0,
              totalPage: 0,
              totalCount: 0,
              contents: []
            }
          }
          res.contents.forEach(async function(el) {
            let bonusStr = ''
            el['rewardIds'] = el.rewards
            let arr = JSON.parse(el.rewards)
            if (Array.isArray(arr)) {
              for (let i = 0; i < arr.length; i++) {
                bonusStr += (await xmlObj.getReward(arr[i])) + ','
              }
            } else {
              bonusStr = await xmlObj.getReward(arr)
            }
            el.rewards = bonusStr
          })
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
