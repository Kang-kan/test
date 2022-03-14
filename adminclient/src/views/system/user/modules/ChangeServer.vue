<template>
  <a-modal
    title="修改服务器"
    :width="640"
    :visible="visible"
    :confirmLoading="confirmLoading"
    :destroyOnClose="true"
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
          <a-input disabled="disabled" v-decorator="['id',{initialValue: id}, {rules: [{required: true, min: 1, max: 16, message: '长度为1-16个字符！'}]}]" />
        </a-form-item>
        <a-form-item
          label="平台id"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input v-decorator="['operatorId',{initialValue: operatorId}, {rules: [{required: true, min: 1, max: 16, message: '长度为1-16个字符！'}]}]" />
        </a-form-item>
        <a-form-item
          label="服务器名"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input v-decorator="['name',{initialValue: name}, {rules: [{required: true, min: 1, max: 16, message: '长度为1-16个字符！'}]}]" />
        </a-form-item>
        <a-form-item
          label="端口"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input v-decorator="['port',{initialValue: port}, {rules: [{required: true, min: 1, max: 16, message: '长度为1-16个字符！'}]}]" />
        </a-form-item>
        <a-form-item
          label="充值端口"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input v-decorator="['chargePort',{initialValue: chargePort}, {rules: [{required: true, min: 1, max: 16, message: '长度为1-16个字符！'}]}]" />
        </a-form-item>
        <a-form-item
          label="GM端口"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input v-decorator="['gmPort',{initialValue: gmPort}, {rules: [{required: true, min: 1, max: 16, message: '长度为1-16个字符！'}]}]" />
        </a-form-item>
        <a-form-item
          label="内网ip"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input v-decorator="['localIp',{initialValue: localIp}, {rules: [{required: false, min: 1, max: 16, message: '长度为1-16个字符！'}]}]" />
        </a-form-item>
        <a-form-item
          label="开服时间"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input v-decorator="['openTime',{initialValue: openTime}, {rules: [{required: true, min: 1, max: 20, message: '长度为8-20个字符！'}]}]" />
        </a-form-item>
        <a-form-item
          label="合服后服务器id"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input v-decorator="['mergeSrvId',{initialValue: mergeSrvId}, {rules: [{required: true, min: 8, max: 20, message: '长度为8-20个字符！'}]}]" />
        </a-form-item>
        <a-form-item
          label="合服时间"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input v-decorator="['mergeTime',{initialValue: mergeTime}]" />
        </a-form-item>
        <a-form-item
          label="定时开服时间"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input v-decorator="['schedOpenTime',{initialValue: schedOpenTime}]" />
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { updateServer } from '@/api/user'

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
      openTime: 0,
      chargePort: 0,
      gmPort: 0,
      id: 0,
      operatorId: 0,
      localIp: 0,
      mergeSrvId: 0,
      schedOpenTime: 0,
      mergeTime: 0,
      name: '',
      port: 0,
      url: ''
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
    show (record) {
      let s = this
      s.visible = true
      console.log(record)
      s.chargePort = record.chargePort
      s.gmPort = record.gmPort
      s.id = record.id
      s.operatorId = record.operatorId
      s.localIp = record.localIp
      s.openTime = record.openTime
      s.mergeSrvId = record.mergeSrvId
      s.name = record.name
      s.port = record.port
      s.url = record.url
      s.mergeTime = record.mergeTime
      s.schedOpenTime = record.schedOpenTime
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if((!values.chargePort && values.chargePort !== 0) || (!values.gmPort && values.gmPort !== 0) || (!values.id && values.gmPort !== 0) || (!values.mergeSrvId && values.mergeSrvId !== 0) || !values.name || (!values.port && values.port !== 0)) {
            this.confirmLoading = false
            this.$message.error('请检查数据重新提交')
            return;
          }
          let obj = {};
          Object.assign(obj, values)
          updateServer(obj).then(result => {
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
