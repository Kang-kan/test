<template>
  <a-modal
    title="添加服务器"
    :width="640"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item
          label="服务器id"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input v-decorator="['id', {rules: [{required: true, min: 1, max: 16, message: '长度为1-16个字符！'}]}]" />
        </a-form-item>
        <a-form-item
          label="平台id"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input v-decorator="['operatorId', {rules: [{required: true, min: 1, max: 16, message: '长度为1-16个字符！'}]}]" />
        </a-form-item>
        <a-form-item
          label="服务器名"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input v-decorator="['name', {rules: [{required: true, min: 1, max: 16, message: '长度为1-16个字符！'}]}]" />
        </a-form-item>
        <a-form-item
          label="端口"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input v-decorator="['port', {rules: [{required: true, min: 1, max: 16, message: '长度为1-16个字符！'}]}]" />
        </a-form-item>
        <a-form-item
          label="充值端口"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input v-decorator="['chargePort', {rules: [{required: true, min: 1, max: 16, message: '长度为1-16个字符！'}]}]" />
        </a-form-item>
        <a-form-item
          label="GM端口"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input v-decorator="['gmPort', {rules: [{required: true, min: 1, max: 16, message: '长度为1-16个字符！'}]}]" />
        </a-form-item>
        <a-form-item
          label="内网ip"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input v-decorator="['localIp', {rules: [{required: false, min: 1, max: 16, message: '长度为1-16个字符！'}]}]" />
        </a-form-item>
        <a-form-item
          label="合服后服务器id"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input v-decorator="['mergeSrvId', {rules: [{required: true, min: 1, max: 16, message: '长度为8-20个字符！'}]}]" />
        </a-form-item>
        <a-form-item
          label="合服时间"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-date-picker
          :showTime="{ defaultValue: $moment('00:00:00', 'HH:mm:ss') }"
          name="mergeDate"
          style="width: 100%"
          v-decorator="[
            'mergeDate',
            {rules: [{ required: false, message: '请选择起止日期' }]}
          ]"
        />
         <a-time-picker @change="onChangeMergeHour" :defaultOpenValue="$moment('00:00', 'HH:mm')" format="HH:mm"/>
        </a-form-item>
        <a-form-item
          label="定时开服时间"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-date-picker
          :disabledDate="disabledDate"
          :disabledTime="disabledDateTime"
          :showTime="{ defaultValue: $moment('00:00:00', 'HH:mm:ss') }"
          name="schedOpenDate"
          style="width: 100%"
          v-decorator="[
            'schedOpenDate',
            {rules: [{ required: false, message: '请选择起止日期' }]}
          ]"
        />
         <a-time-picker @change="onChangeSchedHour" :defaultOpenValue="$moment('00:00', 'HH:mm')" format="HH:mm"/>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { getAddServer } from '@/api/user'

export default {
  data () {
    return {
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

      form: this.$form.createForm(this),
      mergehourTime: 0,
      schedhourTime: 0
    }
  },
  methods: {
    range(start, end) {
        const result = [];
        for (let i = start; i < end; i++) {
          result.push(i);
        }
        return result;
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
    onChangeMergeHour(time, timeString) {
      let hour = timeString.split(':')[0]
      let min = timeString.split(':')[1]
      this.mergehourTime = (Number(hour) * 60 + Number(min)) * 60 * 1000;
    },
    onChangeSchedHour(time, timeString) {
      let hour = timeString.split(':')[0]
      let min = timeString.split(':')[1]
      this.schedhourTime = (Number(hour) * 60 + Number(min)) * 60 * 1000;
    },
    add () {
      this.visible = true
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if((!values.chargePort && values.chargePort !== 0) || (!values.gmPort && values.gmPort !== 0) || (!values.id && values.gmPort !== 0) || (!values.mergeSrvId && values.mergeSrvId !== 0) || !values.name || (!values.port && values.port !== 0)) {
            this.confirmLoading = false
            return;
          }
          let obj = {};
          Object.assign(obj,values)
          if(this.mergehourTime && values.mergeDate) {
            obj['mergeTime'] = values.mergeDate.valueOf() + this.mergehourTime
          }
          if(this.schedhourTime && values.schedOpenDate) {
            obj['schedOpenTime'] = values.schedOpenDate.valueOf() + this.schedhourTime
          }
          getAddServer(values).then(result => {
            if (result != null) {
              this.visible = false
              this.confirmLoading = false
              this.$emit('ok', values)
            } else {
              this.confirmLoading = false
            }
          })
        } else {
          this.confirmLoading = false
        }
      })
    },
    handleCancel () {
      this.visible = false
    }
  }
}
</script>
