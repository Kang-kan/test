<template>
  <a-card :bordered="false">
    <div class="table-operator">
      <h2 style="text-align: center;">指定礼包码信息</h2>
      <a-row :gutter="0" style="line-height: 60px;">
        <a-col :md="5" :sm="12">
          <a-input v-model="giftId" placeholder="请输入礼包id"></a-input>
        </a-col>
        <a-col :md="4" :sm="12" style="margin-left: 20px;">
          <a-button htmlType="submit" type="primary" @click="queryGift">查询已有礼包码</a-button>
        </a-col>
        <a-col :md="5" :sm="12">
          <a-input v-model="code" placeholder="请输入礼包码"></a-input>
        </a-col>
        <a-col :md="4" :sm="12" style="margin-left: 20px;">
          <a-button htmlType="submit" type="primary" @click="queryCode">查询指定礼包码</a-button>
        </a-col>
        <a-col :md="3" :sm="6">
          <a-button type="primary" icon="plus" @click="$refs.createModal.add()">添加礼包</a-button>
        </a-col>
      </a-row>
    </div>
    <s-table
      ref="table1"
      size="default"
      rowKey="monthtableid"
      :columns="columns1"
      :data="loadData1"
      :pagination="pagination"
      style="word-break: normal;"
    >
      <span slot="serial" slot-scope="text, record, index">{{ index + 1 }}</span>
      <span slot="status" slot-scope="text">
        <a-badge :status="text | statusTypeFilter" :text="text | statusFilter" />
      </span>
      <span slot="description" slot-scope="text">
        <ellipsis :length="25" tooltip>{{ text }}</ellipsis>
      </span>

      <span slot="action" slot-scope="text, record">
        <template>
          <a @click="handleEdit(record)">配置</a>
          <a-divider type="vertical" />
          <a @click="handleSub(record)">订阅报警</a>
        </template>
      </span>
    </s-table>
    <div class="table-operator" style="margin-top: 50px;">
      <h2 style="text-align: center;line-height: 40px;">礼包信息</h2>
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
        <a-divider type="vertical" />
        <a @click="$refs.updatadata.add(record)">生成礼包码</a>
      </span>
    </s-table>
    <create-form ref="createModal" @ok="handleOk" />
    <ChangeModal ref="changeModal" @ok="handleOk"></ChangeModal>
    <UpadataData ref="updatadata" @ok="handleOk"></UpadataData>
    <!-- <a-modal title="操作" style="top: 20px;" :width="800" v-model="visible" @ok="handleOk">
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
    </a-modal>-->
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
  import CreateForm from './modules/CreateGift'
  import ChangeModal from './modules/UpdataGift'
  import UpadataData from './modules/CreateCode'
  import { getAccessRight } from '@/api/manage'
  import {
    getCreateGiftRewards,
    getUpdataGiftRewards,
    getQueryGiftRewards,
    getQueryGiftCode,
    getQueryGift,
    getAddServer,
    getListServers,
    getUpdateServer
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
    name: 'GiftList',
    components: {
      STable,
      CreateForm,
      ChangeModal,
      UpadataData
    },
    data() {
      return {
        description: 'desc.',
        code: '',
        giftId: null,
        deletvisible: false,
        codeTxt: '',
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
        columns1: [
          {
            title: '礼包码',
            dataIndex: 'code',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '礼包id',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'giftId'
          },
          {
            title: '使用礼包码玩家',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'usedUsers',
            customRender: usedUsers => (usedUsers == 'null' ? '无用户使用' : usedUsers)
          }
        ],
        // 表头
        columns: [
          {
            title: '礼包id',
            dataIndex: 'id'
          },
          {
            title: '礼包类型',
            dataIndex: 'type'
          },
          {
            title: '礼包奖励',
            dataIndex: 'reward'
          },
          {
            title: '渠道',
            dataIndex: 'channels',
            customRender: channels => channels.join(',')
          },
          {
            title: '开始时间',
            dataIndex: 'startTime',
            customRender: startTime => (startTime <= 0 ? '' : formatTimestamp(startTime))
          },
          {
            title: '结束时间',
            dataIndex: 'endTime',
            customRender: endTime => (endTime <= 0 ? '' : formatTimestamp(endTime))
          },
          {
            title: '礼包描述',
            dataIndex: 'giftDesc'
          },

          {
            title: '每日可使用次数',
            dataIndex: 'canUsePerDay'
          },
          {
            title: '总可用次数',
            dataIndex: 'canUsetotal'
          },
          {
            title: '操作',
            width: '150px',
            dataIndex: 'action',
            scopedSlots: { customRender: 'action' }
          }
        ],
        loadData1: parameter => {
          let s = this
          //console.log('param------->', s.queryParam)
          s.currentPage = parameter.pageNo
          let pageSize = parameter.pageSize
          if (!s.code) {
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
          return getQueryGiftCode({ code: s.code }).then(res => {
            console.log('tablereturn=======>', res)
            if (!res || res == '礼包码不存在') {
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
            obj.pageNo = 0
            obj.pageSize = 10
            obj.contents = [JSON.parse(res)]
            return obj
          })
        },
        // 加载数据方法 必须为 Promise 对象
        loadData: parameter => {
          let obj = {}
          let params = {}
          params['cmd'] = 3004
          let pageNo = parameter.pageNo
          let pageSize = parameter.pageSize
          Object.assign(obj, parameter)
          return getQueryGiftRewards(params).then(res => {
            if (!res) {
              obj.totalPage = 0
              obj.totalCount = 0
              obj.pageNo = 0
              obj.pageSize = 10
              obj.contents = []
              return obj
            }
            let data = JSON.parse(res)
            obj.pageNo = pageNo
            obj.pageSize = pageSize
            let arr = []
            for (let key in data) {
              arr.push(data[key])
            }
            obj.contents = arr
            obj.totalPage = Math.ceil(arr.length / pageSize)
            obj.totalCount = arr.length
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
      download(filename, content, contentType) {
        if (!contentType) contentType = 'application/octet-stream'
        var a = document.createElement('a')
        var blob = new Blob([content], { type: contentType })
        a.href = window.URL.createObjectURL(blob)
        a.download = filename
        a.click()
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
      queryGift() {
        let s = this
        let obj = {}
        if (s.giftId) {
          obj['giftId'] = s.giftId
        }
        getQueryGift(obj).then(res => {
          if (res && res != '礼包码不存在') {
            s.download('HavedGiftCode.txt', res)
          }
        })
      },
      queryCode() {
        let s = this
        this.$refs.table1.refresh(true)
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
