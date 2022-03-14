<template>
  <a-modal
    title="添加世界服"
    :width="640"
    :visible="visible"
    :confirmLoading="confirmLoading"
    :destroyOnClose="true"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="世界服id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['worldId', {rules: [{required: true, min: 0, max: 32, message: '长度为0-32个字符！'}]}]"
          />
        </a-form-item>
        <a-form-item label="名字" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['name', {rules: [{required: true, min: 0, max: 32, message: '长度为0-32个字符！'}]}]"
          />
        </a-form-item>
        <a-form-item label="开始节点" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['beginNode', {rules: [{required: true, min: 1, max: 128, message: '长度为0-128个字符！'}]}]"
          />
        </a-form-item>
        <a-form-item label="结束节点" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            :row="4"
            v-decorator="['endNode', {rules: [{required: true, min: 1, max: 128, message: '长度为0-1024个字符！'}]}]"
          />
        </a-form-item>
        <a-form-item label="socket地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['socketHost', {rules: [{required: true, min: 0, max: 32, message: '长度为0-32个字符！'}]}]"
          />
        </a-form-item>

        <a-form-item label="socket端口" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['socketPort', {rules: [{required: true, min: 0, max: 32, message: '长度为0-32个字符！'}]}]"
          />
        </a-form-item>

        <a-form-item label="web地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['webHost', {rules: [{required: true, min: 0, max: 32, message: '长度为0-32个字符！'}]}]"
          />
        </a-form-item>
        <a-form-item label="web端口" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['webPort', {rules: [{required: true, min: 0, max: 32, message: '长度为0-32个字符！'}]}]"
          />
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import xmlObj from '@/config/xmlConfig'
import { updateWorldServer } from '@/api/user'

export default {
  data() {
    return {
      nameData: [],
      isfetch: false,
      title: '',
      content: '',
      beginNode: '',
      endNode: null,
      name: null,
      socketPort: null,
      playervisible: true,
      socketHost: '',
      webHost: [],
      webPort: '',
      worldId: '',
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
      rewSelect: '',
      rewNum: ''
    }
  },
  methods: {
    show() {
      let s = this
      s.visible = true
    },
    disabledDate(current) {
      // Can not select days before today and today
      return current && current <= this.$moment().endOf('day')
    },
    disabledDateTime() {
      return {
        disabledHours: () => this.range(0, 24).splice(4, 20),
        disabledMinutes: () => this.range(30, 60),
        disabledSeconds: () => [55, 56]
      }
    },
    handleSubmit() {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      let s = this
      validateFields((errors, values) => {
        if (!errors) {
          updateWorldServer(values).then(result => {
            if (result.code == 0) {
              this.visible = false
              this.confirmLoading = false
              this.$emit('ok', values)
              s.$message.success('添加世界服成功')
            } else {
              this.confirmLoading = false
            }
          })
        } else {
          this.confirmLoading = false
        }
      })
    },
    handleCancel() {
      this.visible = false
    },
  }
}
</script>
