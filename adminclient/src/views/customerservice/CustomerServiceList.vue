<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="6" :sm="12">
            <a-row>
              <a-col :md="6" style="line-height: 32px;">渠道:</a-col>
              <a-col :md="14">
                <a-select size="default"  @change="qudaoChange">
                  <a-select-option key=''>全部</a-select-option>
                  <a-select-option v-for="i in channelIdList" :key="i.id" :value="i.id">{{i.name}}</a-select-option>
                </a-select>
              </a-col>
            </a-row>
          </a-col>
          <a-col :md="4" :sm="12">
            <a-row>
              <a-col :md="7" style="line-height: 32px;">区服:</a-col>
              <a-col :md="14">
                <a-select size="default"   @change="serverChange">
                  <a-select-option key=''>全部</a-select-option>
                  <a-select-option v-for="i in serverList" :key="i.id" :value="i.id">
                    {{i.name}}({{i.id}})
                  </a-select-option>
                </a-select>
              </a-col>
            </a-row>
          </a-col>
          <a-col :md="5" :sm="12">
            <a-form-item label="状态">
              <a-select v-model="queryParam.status" placeholder="请选择" default-value="0">
                <a-select-option value="0">未回复</a-select-option>
                <a-select-option value="1">已回复</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="!advanced && 8 || 24" :sm="24">
            <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
              <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>

    <s-table ref="table" size="default" rowKey="key" :columns="columns" :data="loadData" 
       showPagination="auto" :scroll="{x:2000}">
      <span slot="serial" slot-scope="text, record, index">
        {{ index + 1 }}
      </span>
      <span slot="status" slot-scope="text">
        <a-badge :status="text | statusTypeFilter" :text="text | statusFilter" />
      </span>
      <span slot="description" slot-scope="text">
        <ellipsis :length="30" tooltip>{{ text }}</ellipsis>
      </span>
      <span slot="content" slot-scope="text" style="color: #52CEFF;">
        <ellipsis :length="30" tooltip>{{ text }}</ellipsis>
      </span>
      <span slot="action" slot-scope="text, record">
        <template>
          <a @click="handleEdit(record)">回复</a>
        </template>
      </span>
    </s-table>
    <ReplyModal ref="modal" @ok="handleOk" />
  </a-card>
</template>

<script>
  import moment from 'moment'
  import {
    STable,
    Ellipsis
  } from '@/components'
  import ReplyModal from '../other/modules/ReplyModal'
  import {

    getCustomerService
  } from '@/api/user'

  const statusMap = {
    0: {
      status: 'error',
      text: '未回复'
    },
    1: {
      status: 'success',
      text: '已回复'
    }
  }

  export default {
    name: 'CustomerServiceList',
    components: {
      STable,
      Ellipsis,
      ReplyModal
    },
    data() {
      return {
        serverList: [],
        channelIdList: [],
        mdl: {},
        // 高级搜索 展开/关闭
        advanced: false,
        // 查询参数
        queryParam: {
          channelId: '',
          serverId: '',
          status: '0'
        },
        // 表头
        columns: [{
            title: 'id',
            dataIndex: 'id'
          },
          {
            title: '服务器',
            dataIndex: 'serverId',
            customRender: serverId => {
            let name
            this.serverList.forEach(el => {
              if (el.id == serverId) {
                name = el.name + '(' + el.id + ')'
              }
            })
            return name
          }
          },
          {
            title: '渠道',
            dataIndex: 'channelId',
            scopedSlots: {
              customRender: 'description',
            },
            customRender: channelId => {
            let name
            this.channelIdList.forEach(el => {
              if (el.id == channelId) {
                name = el.name + '(' + el.id + ')'
              }
            })
            return name
          }
          },
          {
            title: '状态',
            dataIndex: 'status',
            scopedSlots: {
              customRender: 'status'
            },
            width: '90px'
          },
          {
            title: '玩家ID',
            dataIndex: 'playerId',
            scopedSlots: {
              customRender: 'description'
            },
            width: '150px'
          },
          {
            title: '玩家名',
            dataIndex: 'playerName',
            scopedSlots: {
              customRender: 'description'
            },
            width: '100px'
          },
          {
            title: '等级',
            dataIndex: 'level',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: 'vip等级',
            dataIndex: 'vipLevel',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '内容',
            dataIndex: 'context',
            // width: '300px',
            scopedSlots: {
              customRender: 'content'
            },

          },
          {
            title: 'IP',
            dataIndex: 'ip',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '回复者',
            dataIndex: 'replier',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '回复标题',
            dataIndex: 'replyTitle',
            // width: '300px',
            scopedSlots: {
              customRender: 'content'
            }
          },
          {
            title: '回复内容',
            dataIndex: 'reply',
            // width: '300px',
            scopedSlots: {
              customRender: 'content'
            }
          },
          {
            title: '回复时间',
            dataIndex: 'replyTime',
            scopedSlots: {
              customRender: 'description'
            },
            width: '240px'
          },
          {
            title: '操作',
            dataIndex: 'action',
            scopedSlots: {
              customRender: 'action'
            }
          }
        ],
        // 加载数据方法 必须为 Promise 对象
        loadData: parameter => {
          //console.log('queryparam', this.queryParam);
          return getCustomerService(Object.assign(parameter, this.queryParam))
            .then(res => {
              if (!res) {
                return {
                  pageNo: 0,
                  pageSize: 10,
                  totalPage: 0,
                  totalCount: 0,
                  contents: []
                }
              }
              return res;
            })
        },
        selectedRowKeys: [],
        selectedRows: [],

        // custom table alert & rowSelection
        options: {
          alert: {
            show: true,
            clear: () => {
              this.selectedRowKeys = []
            }
          },
          rowSelection: {
            selectedRowKeys: this.selectedRowKeys,
            onChange: this.onSelectChange
          }
        },
        optionAlertShow: false
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
    created() {
      this.tableOption()
    },
    methods: {
      tableOption() {
        if (!this.optionAlertShow) {
          this.options = {
            alert: {
              show: true,
              clear: () => {
                this.selectedRowKeys = []
              }
            },
            rowSelection: {
              selectedRowKeys: this.selectedRowKeys,
              onChange: this.onSelectChange,
              getCheckboxProps: record => ({
                props: {
                  disabled: record.no === 'No 2', // Column configuration not to be checked
                  name: record.no
                }
              })
            }
          }
          this.optionAlertShow = true
        } else {
          this.options = {
            alert: false,
            rowSelection: null
          }
          this.optionAlertShow = false
        }
      },
      qudaoChange(value) {
        this.queryParam.channelId = value;
      },
      serverChange(value) {
        this.queryParam.serverId = value;
      },
      handleEdit(record) {
        //console.log(record)
        this.$refs.modal.edit(record)
      },
      handleSub(record) {
        if (record.status !== 0) {
          this.$message.info(`${record.no} 订阅成功`)
        } else {
          this.$message.error(`${record.no} 订阅失败，规则已关闭`)
        }
      },
      handleOk() {
        this.$refs.table.refresh()
      },
      onSelectChange(selectedRowKeys, selectedRows) {
        this.selectedRowKeys = selectedRowKeys
        this.selectedRows = selectedRows
      },
      toggleAdvanced() {
        this.advanced = !this.advanced
      }
    },
    mounted: function() {
      let s = this;
      s.$ajax.get('../gameVar.json').then(res => {
        if (!res || !res.data) {
          return;
        }
        s.channelIdList = res.data.channelIdList;
      });
      let serverstr = localStorage.getItem("serverList");
      this.$http.get('/roleGameServer/getMyServers').then(res => {
      s.serverList = res
      if(!res || res.length == 0) {
        return;
      };
    })
    }
  }
</script>
