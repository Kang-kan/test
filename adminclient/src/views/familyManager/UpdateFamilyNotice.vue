<template>
  <a-card :body-style="{padding: '24px 32px'}" :bordered="false">
    <a-form @submit="handleSubmit" :form="form">
      <a-form-item
        label="选择服务器"
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
      >
        <a-select
          size="default"
          v-decorator="[
            'serverId',
            {rules: [{ required: true, message: '请输入生成服务器id' }]}
          ]"
        >
          <a-select-option
            v-for="i in serverList"
            :key="i.id"
            :value="i.id"
          >{{i.name}}({{i.id}})</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item
        label="仙盟id"
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
      >
        <a-input
          v-decorator="[
            'familyId',
            {rules: [{ required: true, message: '请输入仙盟id' }]}
          ]"
          name="familyId"
          placeholder="请输入仙盟id"
        />
      </a-form-item>
      <a-form-item
        label="公告内容"
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
      >
        <a-textarea :autosize="{ minRows: 4}"
          v-decorator="[
            'notice',
            {rules: [{ required: true, message: '请输入公告内容' }]}
          ]"
          name="notice"
          placeholder="请输入公告内容"
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
import { getUpdateFamily } from '@/api/user'
export default {
  name: 'UpdateFamilyNotice',
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
        if(!values.serverId || !values.notice || values.familyId == undefined) {
            return;
        }
        getUpdateFamily(values).then(res => {
          if (!res) {
            return
          }
          let s = this
          if (res) {
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
      };
    })
  }
}
</script>
