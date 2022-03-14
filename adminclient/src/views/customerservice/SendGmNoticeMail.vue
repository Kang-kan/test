<template>
  <a-card :body-style="{padding: '24px 32px'}" :bordered="false">
    <a-form @submit="handleSubmit" :form="form">
      <a-form-item
        label="邮件标题"
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
      >
        <a-input
          v-decorator="[
            'title',
            {rules: [{ required: true, message: '请输入邮件标题' }]}
          ]"
          name="title"
          placeholder="请输入邮件标题"
        />
      </a-form-item>
      <a-form-item
        label="邮件内容"
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
      >
        <a-textarea
          :autosize="{ minRows: 4}"
          v-decorator="[
            'content',
            {rules: [{ required: true, message: '请输入邮件内容' }]}
          ]"
          name="content"
          placeholder="请输入邮件内容"
        />
      </a-form-item>
      <a-form-item :wrapperCol="{ span: 24 }" style="text-align: center">
        <a-button htmlType="submit" type="primary">发送</a-button>
        <!-- <a-button style="margin-left: 8px">保存</a-button> -->
      </a-form-item>
    </a-form>
  </a-card>
</template>

<script>
import { getSendGmNoticeMail } from '@/api/user'
export default {
  name: 'SendGmNoticeMail',
  data() {
    return {
      serverList: [],
      description: '表单页用于向用户收集或验证信息，基础表单常见于数据项较少的表单场景。',
      value: 1,
      // form
      form: this.$form.createForm(this),
      selfSelect: false
    }
  },
  methods: {
    // handler
    handleSubmit(e) {
      e.preventDefault()
      this.form.validateFields((err, values) => {
        if (!err) {
          // eslint-disable-next-line no-console
          //console.log('Received values of form: ', values)
        }
        // console.log('values========>', values)
        // console.log(obj)
        if (!values.content || !values.title) {
          return
        }
        getSendGmNoticeMail(values).then(res => {
          if (!res) {
            return
          }
          if (res.code == 0) {
            s.$message.success('操作成功')
          } else {
            this.$message.error('操作失败')
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
      if(!res || res.length == 0) {
        return;
      }
    })
  }
}
</script>
