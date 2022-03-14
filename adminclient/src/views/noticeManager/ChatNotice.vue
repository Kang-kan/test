<template>
  <a-card :bordered="false">
    <div class="table-operator">
      <a-button type="primary" icon="plus" @click="$refs.createModal.add()">添加轮播跑马灯</a-button>
      <a-button type="primary" icon="plus" @click="$refs.sendModal.add()">发送一次性跑马灯</a-button>
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
        <a-dropdown>
          <a class="ant-dropdown-link">
            更多
            <a-icon type="down" />
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
    <SendModal ref="sendModal" @ok="handleOk"></SendModal>
    <ResetPwdModal ref="resetModal" @ok="handleOk"></ResetPwdModal>
    <a-modal
      title="确认删除该跑马灯"
      :visible="deletvisible"
      @ok="handleDelet"
      @cancel="deletvisible = false"
    >
      <p>{{deletText}}</p>
    </a-modal>
    <!-- <a-modal title="确认发送该邮件" :visible="sendvisible" @ok="handlesend" @cancel="sendvisible = false">
      <p>{{sendText}}</p>
    </a-modal>-->
  </a-card>
</template>

<script>
  import xmlObj from '@/config/xmlConfig'
  import { formatTimestamp } from '@/utils/util'
  import { STable } from '@/components'
  import CreateForm from './modules/CreateChatNotice'
  import SendModal from './modules/SendChatNotice'
  import ResetPwdModal from './modules/ResetChatNotice'
  import { getAccessRight } from '@/api/manage'
  import { addChatNotice, deleteChatNotice, getChatNotices, sendChatNotice, updateChatNotice } from '@/api/user'

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
    name: 'ChatNotice',
    components: {
      STable,
      CreateForm,
      ResetPwdModal,
      SendModal
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
        channelIdList: null,
        serverList: null,
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
            title: '渠道',
            dataIndex: 'channels',
            customRender: channels => {
              let s = this
              if (!channels || channels.length == 0) {
                return '全服务器'
              } else {
                let str = ''
                channels.forEach(els => {
                  let has = false;
                  s.channelIdList.forEach(el => {
                    if (el.id == els) {
                      str += el.name + ','
                      has = true;
                    }
                  })
                  if(!has) {
                    str += els;
                  }
                })
                return str
              }
            }
          },
          {
            title: '时间间隔',
            dataIndex: 'intervalTime',
            customRender: intervalTime => intervalTime + '秒'
          },
          ,
          {
            title: '内容',
            dataIndex: 'notice'
          },
          {
            title: '服务器',
            dataIndex: 'serverIds',
            customRender: serverIds => {
              let s = this
              if (!serverIds || serverIds.length == 0) {
                return '全服务器'
              } else {
                let str = ''
                serverIds.forEach(els => {
                  let has = false;
                  s.serverList.forEach(el => {
                    if (el.id == els) {
                      str += el.name + ','
                      has = true;
                    }
                  })
                  if(!has) {
                    str += els;
                  }
                })
                return str
              }
            }
          },
          {
            title: '更新时间',
            dataIndex: 'updateTime',
            customRender: time => (time > 0 ? formatTimestamp(time) : '')
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
          let pageNo = parameter.pageNo
          let pageSize = parameter.pageSize
          return getChatNotices(parameter).then(res => {
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
            obj.totalPage = Math.ceil(res.length / pageSize)
            obj.totalCount = res.length
            obj.pageNo = s.currentPage
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
          if (res != null) {
            s.$message.success('成功发送奖励邮件')
          }
          s.sendvisible = false
          s.$refs.table.refresh()
        })
      },
      openDelet(record) {
        console.log('删除跑马灯', record)
        this.deletText = '确定删除跑马灯' + record.id
        this.deletId = record.id
        this.deletvisible = true
      },
      handleDelet() {
        let s = this
        deleteChatNotice({ chatNoticeId: this.deletId }).then(res => {
          console.log('删除跑马灯=======》', res)
          if (res != null) {
            s.$message.success('成功删除跑马灯')
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
