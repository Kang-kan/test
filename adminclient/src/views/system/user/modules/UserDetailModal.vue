<template>
  <a-modal
    title="用户详情"
    :width="640"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @cancel="handleCancel"
    @ok="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="Email" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <span v-text="email"></span>
        </a-form-item>
        <a-form-item label="id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <span v-text="id"></span>
        </a-form-item>
        <a-form-item label="描述名" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <span v-text="name"></span>
        </a-form-item>
        <a-form-item label="手机号码" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <span v-text="phone"></span>
        </a-form-item>
        <a-form-item label="账号名" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <span v-text="userName"></span>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

export default {
  name: 'UserDetailModal',
  data() {
    return {
      phone: '',
      id: '',
      email: '',
      name: '',
      userName: '',
      status: 0,

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
    show(record) {
      let s = this
      console.log('detail===========>',record)
      s.phone = record.phone
      s.id = record.id
      s.email = record.email
      s.userName = record.username
      s.name = record.name
      s.status = record.status
      this.visible = true
    },
    handleCancel() {
      this.visible = false
    }
  }
}
</script>
