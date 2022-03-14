<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="0">
          <a-col :md="6" :sm="24">
            <a-form-item label="起止日期" :labelCol="{lg: {span: 7}, sm: {span: 7}}" :wrapperCol="{lg: {span: 10}, sm: {span: 17} }">
              <a-range-picker :placeholder="['Start month', 'End month']" format="YYYY-MM" :value="value" :mode="mode2"
                @panelChange="handlePanelChange2" @change="handleChange" />
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
          <a-col :md="2">
            <span class="table-page-search-submitButtons">
              <!-- <a-button @click="filterBol = !filterBol">筛选</a-button> -->
              <a-button type="primary" style="margin-left: 8px" @click="seach">查询</a-button>
              <!-- <a-button @click="$refs.table.refresh(true)" style="margin-left: 8px">导出</a-button> -->
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>

    <s-table ref="table" size="default" rowKey="monthtableid" :columns="columns" :data="loadData" 
       :pagination="pagination" style="word-break: normal;">
      <span slot="serial" slot-scope="text, record, index">
        {{ index + 1 }}
      </span>
      <span slot="status" slot-scope="text">
        <a-badge :status="text | statusTypeFilter" :text="text | statusFilter" />
      </span>
      <span slot="description" slot-scope="text">
        <ellipsis :length="10" tooltip>{{ text }}</ellipsis>
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
    getMonthActiveSummary,
    getServerInfo
  } from '@/api/user';
  import {
    getRoleList,
    getServiceList
  } from '@/api/manage';
  export default {
    name: 'MonthActive',
    components: {
      STable,
      Ellipsis,
      CreateForm,
      StepByStepModal
    },
    data() {
      return {
        value: [],
        mode2: ['month', 'month'],
        tableData: {},
        timeArr: [],
        // 查询参数
        queryParam: {
          channelId: '',
          serverId: 0,
          startTime: 0,
          endTime: 0
        },
        pagination: {
          position: 'bottom',
          pageSizeOptions: ['10', '20', '50','99999'],
          defaultPageSize: 10,
        },
        // 表头
        columns: [{
            title: '月',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'dateTime',
            width: '25%'
          },
          {
            title: '总用户数',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'totalPlayer'
          },
          {
            title: '活跃用户数',
            dataIndex: 'activePlayer',
            scopedSlots: {
              customRender: 'description'
            }
          }
        ],
        // 加载数据方法 必须为 Promise 对象
        loadData: parameter => {
          let s = this;
          //console.log('param------->',s.queryParam)
          return getMonthActiveSummary(Object.assign(parameter, s.queryParam))
            .then(res => {
              //console.log('tablereturn=======>',res)
              if (!res) {
                return {
                  pageSize: 10,
                  pageNo: 0,
                  totalPage: 0,
                  totalCount: 0,
                  contents: []
                };
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

      // getServerInfo().then(res => {
      //   //console.log('测试端口', res);
      // })
    },
    methods: {
      seach() {
        this.queryParam.startTime = this.timeArr[0]
        this.queryParam.endTime = this.timeArr[1]
        this.$refs.table.refresh(true);
      },
      qudaoChange(value) {
        //console.log('渠道', value)
      },
      handleChange(value) {
        this.value = value;
      },
      handlePanelChange2(value, mode) {
        let s = this;
        s.value = value;
        s.mode2 = [
          mode[0] === 'date' ? 'month' : mode[0],
          mode[1] === 'date' ? 'month' : mode[1],
        ];
        let date1 = new Date(s.value[0]._d);
        let date2 = new Date(s.value[1]._d);
        let year1 = date1.getFullYear();
        let year2 = date2.getFullYear();
        let mon1 = date1.getMonth() + 1;
        let mon2 = date2.getMonth() + 1;
        s.timeArr[0] = mon1 >= 10 ? Number(year1 + '' + mon1) : Number(year1 + '0' + mon1);
        s.timeArr[1] = mon2 >= 10 ? Number(year2 + '' + mon2) : Number(year2 + '0' + mon2);
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
