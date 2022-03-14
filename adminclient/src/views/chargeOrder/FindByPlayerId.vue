<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="0">
          <a-col :md="6" :sm="24">
            <a-input placeholder="请输入玩家Id" v-model="playerId"/>
          </a-col>
          <a-col :md="!advanced && 6 || 24" :sm="24">
            <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
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
    getFindOrderByPlayerId,
    getServerInfo
  } from '@/api/user';
  import {
    getRoleList,
    getServiceList
  } from '@/api/manage';
  const statusMap = {
    0: {
      status: 'default',
      text: '关闭'
    },
    1: {
      status: 'processing',
      text: '运行中'
    },
    2: {
      status: 'success',
      text: '已上线'
    },
    3: {
      status: 'error',
      text: '异常'
    }
  }
  const defaultCheckList = {
    zhanfu: ['测试服1', '测试服2', '测试服3'],
    qufu: ['区服1', '区服2', '区服3'],
    qudao: ['渠道1', '渠道2', '渠道3']
  };
  export default {
    name: 'FindByPlayerId',
    components: {
      STable,
      Ellipsis,
      CreateForm,
      StepByStepModal
    },
    data() {
      return {
        playerId: '',
        tableData: [],
        timeArr: [],
        defaultFilterData: defaultCheckList,
        filterBol: false,
        filterData: {
          zhanfu: ['测试服1', '测试服2', '测试服3'],
          qufu: ['区服1', '区服2', '区服3'],
          qudao: ['渠道1', '渠道2', '渠道3']
        },
        mdl: {},
        // 高级搜索 展开/关闭
        advanced: false,
        // 查询参数
        queryParam: {
          playerId: '0'
        },
        pagination: {
          position: 'bottom',
          pageSizeOptions: ['10', '20', '50','99999'],
          defaultPageSize: 10,
        },
        // 表头
        columns: [{
            title: '渠道',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'channelId'
          },
          {
            title: '账号',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'account'
          },{
            title: '通知发货时间',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'adviceTime'
          },
          {
            title: '到账时间',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'arrivalTime'
          },{
            title: '是否当天创建',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'createDay'
          },
          
          {
              title: '定单id',
              scopedSlots: {
                customRender: 'description'
              },
              dataIndex: 'first'
            },
            {
              title: '定单状态',
              scopedSlots: {
                customRender: 'description'
              },
              dataIndex: 'gameResultCode'
            },{
              title: '仙晶',
              scopedSlots: {
                customRender: 'description'
              },
              dataIndex: 'gold'
            },
            {
              title: '商品id',
              scopedSlots: {
                customRender: 'description'
              },
              dataIndex: 'goodsId'
            },{
              title: '订单号',
              scopedSlots: {
                customRender: 'description'
              },
              dataIndex: 'orderId'
            },
          // {
          //   title: '平台',
          //   scopedSlots: {
          //     customRender: 'description'
          //   },
          //   dataIndex: 'platName'
          // },
          {
            title: '玩家Id',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'playerId'
          },{
            title: '玩家名',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'playerName'
          },
          {
            title: '订单金额',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'rmb'
          },{
            title: '服务器id',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'serverId'
          },{
            title: '下单时间',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'time'
          },{
            title: '第三方支付订单号',
            scopedSlots: {
              customRender: 'description'
            },
            dataIndex: 'transactionId'
          }
        ],
        selectedRowKeys: [],
        selectedRows: [],
        // 加载数据方法 必须为 Promise 对象
        loadData: parameter => {
          let s = this;
          //console.log('param------->',s.queryParam)
          return getFindOrderByPlayerId(Object.assign(parameter, s.queryParam))
            .then(res => {
              //console.log('tablereturn=======>',res)
              if (!res || !res.content) {
                return {
                  pageSize: 10,
                  pageNo: 0,
                  totalPage: 0,
                  totalCount: 0,
                  contents: []
                };
              }
              return res.content;
            })
        },
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
        let s = this;
        s.queryParam.playerId = s.playerId
        this.$refs.table.refresh(true);
      },
      qudaoChange(value) {
        //console.log('渠道', value)
      },
      handleTimeChange(value,timeString) {
        this.timeArr[0] = Number(timeString[0].replace(/-/g,''));
        this.timeArr[1] = Number(timeString[1].replace(/-/g,''));
        //console.log('日期',this.timeArr);
      },
      onCheckAllChange(e) {
        Object.assign(this, {
          filterData: defaultCheckList
        });
      },
      onRevertCheck() {
        let s = this;
        let obj = {};
        for (let key in defaultCheckList) {
          obj[key] = [];
          defaultCheckList[key].forEach(el => {
            if (s.filterData[key].indexOf(el) < 0) {
              obj[key].push(el);
            }
          });
        };
        Object.assign(this, {
          filterData: obj
        })
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
      toggleAdvanced() {
        this.advanced = !this.advanced
      }
    }
  }
</script>
