<template>
  <a-modal
    title="操作"
    :width="600"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-card :body-style="{padding: '24px 32px'}" :bordered="false">
      <a-form @submit="handleSubmit" :form="form">
        <a-form-item
          label="封号持续时间"
          :labelCol="{lg: {span: 7}, sm: {span: 7}}"
          :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
        >
          <a-select
            size="default"
            @change="timeSelectChange"
            v-decorator="[
          'unlockTime',
          { rules: [{ required: true, message: '请选择全服持续时间' }] },
        ]"
          >
            <a-select-option key="1" value="-1">永久</a-select-option>
            <a-select-option key="2" value="0">解封</a-select-option>
            <a-select-option key="3" value="1">自选时间</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          label="自选时间"
          :labelCol="{lg: {span: 7}, sm: {span: 7}}"
          :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
          v-if="selfSelect"
        >
          <a-date-picker
            :disabledDate="disabledDate"
            :disabledTime="disabledDateTime"
            :showTime="{ defaultValue: $moment('00:00:00', 'HH:mm:ss') }"
            name="buildTime"
            style="width: 100%"
            v-decorator="[
            'buildTime',
            {rules: [{ required: true, message: '请选择起止日期' }]}
          ]"
          />
          <a-time-picker
            @change="onChangeHour"
            :defaultOpenValue="$moment('00:00', 'HH:mm')"
            format="HH:mm"
          />
        </a-form-item>
        <a-form-item
          label="封号理由"
          :labelCol="{lg: {span: 7}, sm: {span: 7}}"
          :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
        >
          <a-input
            v-decorator="[
            'reason',
            {rules: [{ required: false, message: '请输入封号理由' }]}
          ]"
            name="reason"
            placeholder="请输入封号理由内容"
          />
        </a-form-item>
      </a-form>
    </a-card>
  </a-modal>
</template>

<script>
  import { getBlockPlayer } from '@/api/user'
  export default {
    name: 'BlockPlayer1',
    data() {
      return {
        record: null,
        hourTime: 0,
        serverList: [],
        description: '表单页用于向用户收集或验证信息，基础表单常见于数据项较少的表单场景。',
        value: 1,
        // form
        form: this.$form.createForm(this),
        selfSelect: false,
        labelCol: {
          xs: { span: 24 },
          sm: { span: 7 }
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 13 }
        },
        visible: false,
        confirmLoading: false,
        rewSelect: '',
        rewNum: ''
      }
    },
    methods: {
      // handler
      add(record) {
        this.record = record
        this.visible = true
      },
      handleCancel() {
        this.visible = false
      },
      onChangeHour(time, timeString) {
        let hour = timeString.split(':')[0]
        let min = timeString.split(':')[1]
        this.hourTime = (Number(hour) * 60 + Number(min)) * 60 * 1000
      },
      range(start, end) {
        const result = []
        for (let i = start; i < end; i++) {
          result.push(i)
        }
        return result
      },
      disabledDate(current) {
        // Can not select days before today and today
        return current && current < this.$moment().endOf('day')
      },
      disabledDateTime() {
        return {
          disabledHours: () => this.range(0, 24).splice(4, 20),
          disabledMinutes: () => this.range(30, 60),
          disabledSeconds: () => [55, 56]
        }
      },
      handleSubmit(e) {
        let s = this
        e.preventDefault()
        this.form.validateFields((err, values) => {
          if (!err) {
            // eslint-disable-next-line no-console
            //console.log('Received values of form: ', values)
          }
          // console.log('values========>', values)
          let obj = {}
          obj['playerId'] = s.record.playerId
          obj['reason'] = values.reason
          obj['serverId'] = s.record.serverId
          if (values.unlockTime == 1) {
            obj['unBlockTime'] = values.buildTime.valueOf() + this.hourTime
          } else {
            obj['unBlockTime'] = values.unlockTime
          }
          if (!obj.playerId || !obj.serverId || obj.unBlockTime == undefined) {
            return
          }
          getBlockPlayer(obj).then(res => {
            if (!res) {
              return
            }
            let s = this
            if (res) {
              s.visible = false
              s.confirmLoading = false
              s.$emit('ok', obj)
              s.$message.success('玩家账号状态变更成功')
            } else {
              s.$message.error('操作失败')
            }
          })
        })
      },
      timeSelectChange(value) {
        console.log(value)
        let s = this
        if (value == 1) {
          s.selfSelect = true
        } else {
          s.selfSelect = false
        }
      }
    },
    mounted: function() {
      let s = this
      this.$http.get('/roleGameServer/getMyServers').then(res => {
        s.serverList = res
        if (!res || res.length == 0) {
          return
        }
      })
    }
  }
</script>
