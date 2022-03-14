<template>
  <a-modal
    title="创建用户"
    :width="640"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item
          label="Email"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input v-decorator="['email', {rules: [{required: true, min: 6, max: 17, message: '长度为6-16个字符！'}]}]" />
        </a-form-item>
        <a-form-item
          label="描述名"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input v-decorator="['name', {rules: [{required: true, min: 3, max: 16, message: '长度为3-16个字符！'}]}]" />
        </a-form-item>
        <a-form-item
          label="手机号码"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input v-decorator="['phone', {rules: [{required: true, min: 6, max: 16, message: '长度为6-16个字符！'}]}]" />
        </a-form-item>
        <a-form-item
          label="账号名"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input v-decorator="['userName', {rules: [{required: true, min: 6, max: 16, message: '长度为3-16个字符！'}]}]" />
        </a-form-item>
        <a-form-item
          label="密码"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-input v-decorator="['password', {rules: [{required: true, min: 8, max: 20, message: '请输入8-16位包含数字与英文且不能与用户名相同的密码！'}]}]" />
        </a-form-item>

        <a-form-item
          label="状态"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-select
            v-decorator="[
              'status',
              { status: [{ required: true, message: '请选择用户是否开启！' }] },
            ]"
            placeholder="请选择用户否是否开启使用。"
          >
            <a-select-option value="1">
              开启
            </a-select-option>
            <a-select-option value="0">
              禁用
            </a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { addUser } from '@/api/manage'

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

      form: this.$form.createForm(this)
    }
  },
  methods: {
    add () {
      this.visible = true
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          addUser(values).then(result => {
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
