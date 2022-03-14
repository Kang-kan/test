<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="6" :sm="24">
            <a-form-item label="起止日期" :labelCol="{lg: {span: 7}, sm: {span: 7}}" :wrapperCol="{lg: {span: 10}, sm: {span: 17} }">
              <a-range-picker name="buildTime" style="width: 100%" v-decorator="[
                  'buildTime',
                  {rules: [{ required: true, message: '请选择起止日期' }]}
                ]"
                @change="onChangeTime" />
            </a-form-item>
          </a-col>
          <a-col :md="4" :sm="12">
            <a-row>
              <a-col :md="7" style="line-height: 32px;">渠道:</a-col>
              <a-col :md="10">
                <a-select size="default" defaultValue="a1" @change="qudaoChange">
                  <a-select-option v-for="i in 25" :key="(i + 9).toString(36) + i">
                    {{(i + 9).toString(36) + i}}
                  </a-select-option>
                </a-select>
              </a-col>
            </a-row>
          </a-col>
          <a-col :md="4" :sm="12">
            <a-row>
              <a-col :md="7" style="line-height: 32px;">区服:</a-col>
              <a-col :md="10">
                <a-select size="default" defaultValue="a1" @change="qudaoChange">
                  <a-select-option v-for="i in 25" :key="(i + 9).toString(36) + i">
                    {{(i + 9).toString(36) + i}}
                  </a-select-option>
                </a-select>
              </a-col>
            </a-row>
          </a-col>
          <a-col :md="4" :sm="12">
            <a-row>
              <a-col :md="7" style="line-height: 32px;">平台:</a-col>
              <a-col :md="10">
                <a-select size="default" defaultValue="a1" @change="qudaoChange">
                  <a-select-option v-for="i in 25" :key="(i + 9).toString(36) + i">
                    {{(i + 9).toString(36) + i}}
                  </a-select-option>
                </a-select>
              </a-col>
            </a-row>
          </a-col>
          <a-col :md="2" :sm="24">
            <span class="table-page-search-submitButtons">
             
              <a-button type="primary" style="margin-left: 8px" @click="() => queryParam = {}">查询</a-button>
            
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>

    <s-table ref="table" size="default" rowKey="key" :columns="columns" :data="loadData" 
       :pagination="pagination">
      <span slot="serial" slot-scope="text, record, index">
        {{ index + 1 }}
      </span>
      <span slot="status" slot-scope="text">
        <a-badge :status="text | statusTypeFilter" :text="text | statusFilter" />
      </span>
      <span slot="description" slot-scope="text">
        <ellipsis :length="4" tooltip>{{ text }}</ellipsis>
      </span>

      <span slot="action" slot-scope="text, record">
        <template>
          <a @click="handleEdit(record)">配置</a>
          <a-divider type="vertical" />
          <a @click="handleSub(record)">订阅报警</a>
        </template>
      </span>
    </s-table>
    <create-form ref="createModal" @ok="handleOk" />
    <step-by-step-modal ref="modal" @ok="handleOk" />
  </a-card>
</template>

<script>
  import moment from 'moment';
  import {
    STable,
    Ellipsis,
    Modal
  } from '@/components';
  import StepByStepModal from '../list/modules/StepByStepModal';
  import CreateForm from '../list/modules/CreateForm';
  import {
    getDailySummary,
    getServerInfo
  } from '@/api/user';
  import {
    getRoleList,
    getServiceList
  } from '@/api/manage';
  export default {
    name: 'SingleSrv',
    components: {
      STable,
      Ellipsis,
      CreateForm,
      StepByStepModal
    },
    data() {
      return {
        timeArr: [],
        // 查询参数
        queryParam: {},
        pagination: {
          position: 'bottom',
          pageSizeOptions: ['10', '20', '50','99999'],
          defaultPageSize: 25,
        },
        // 表头
        columns: [{
            title: '日期',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '充值人数',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '充值金额',
            dataIndex: 'description',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '新增充值人数',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: '新增充值金额',
            dataIndex: 'status',
            scopedSlots: {
              customRender: 'status'
            }
          },
          {
            title: '充值仙晶',
            dataIndex: 'updatedAt',
          },
          {
            title: '新增充值仙晶',
            dataIndex: 'action',
            scopedSlots: {
              customRender: 'description'
            }
          },
          {
            title: 'ARRU',
            dataIndex: 'ARRU',

          },
          {
            title: '新增ARRU'
          }
        ],
        // 加载数据方法 必须为 Promise 对象
        loadData: parameter => {
          //console.log('loadData.parameter', parameter)
          return getServiceList(Object.assign(parameter, this.queryParam))
            .then(res => {
              return res.result
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
      getDailySummary({
        channelId: '100',
        pageNo: 1,
        pageSize: 25,
        serverId: 0
      }).then(res => {
        //console.log('每日充值res=======>', res);
      }).catch(err => {
        if (err) {
          throw err;
        }
      });
      getServerInfo().then(res => {
        //console.log('测试端口', res);
      })
    },
    methods: {
      qudaoChange(value) {
        //console.log('渠道', value)
      },
      onChangeTime(data, dataString) {
        if (dataString && dataString[0] && dataString[1]) {
          let arr = [];
          dataString.forEach(el => {
            let str = el.replace(/-/g, '');
            arr.push(Number(str));
          });
          this.timeArr = arr;
        }
      },
      callback(e) {
        //console.log(e);
      },
      onChange(e) {
        //console.log(e);
      },
      openModal() {
        this.$confirm()
      },
      onSearch() {},
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

    }
  }
</script>
