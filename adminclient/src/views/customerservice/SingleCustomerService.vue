<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="8" :sm="12">
            <a-form-item label="玩家id">
              <a-input v-model="queryParam.playerId" placeholder="" />
            </a-form-item>
          </a-col>
          <a-col :md="2" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>

    <s-table ref="table" size="default" rowKey="key" :columns="columns" :data="loadData" 
       showPagination="auto" :scroll="{x: 2000}">
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
    getSingleCustomerService
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
    name: 'SingleCustomerService',
    components: {
      STable,
      Ellipsis,
      ReplyModal
    },
    data() {
      return {
        mdl: {},
        // 高级搜索 展开/关闭
        advanced: false,
        channelIdList: [],
        serverList: [],
        // 查询参数
        queryParam: {
          playerId: ''
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
          parameter = {};
          //console.log('queryparam', this.queryParam);
          return getSingleCustomerService(Object.assign(parameter, this.queryParam))
            .then(res => {
              //console.log('res======>',res)
              if (res.code == 0 && !res.content) {
                return {
                  pageNo: 0,
                  pageSize: 10,
                  totalPage: 0,
                  totalCount: 0,
                  contents: []
                }
              }
              let obj = {};
              obj.pageNo = 0,obj.pageSize = 10,obj.totalPage = Math.ceil(res.length / obj.pageSize),obj.totalCount = res.length;
              res.sort((a,b) => {
                return a.status - b.status;
              })
              obj.contents = res;
              return obj;
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
      handleEdit(record) {
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
        //console.log('开始刷新表格=================》');
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
