<template>
  <a-card :body-style="{padding: '24px 32px'}" :bordered="false">
    <a-row>
      <a-col>
        <h3 style="text-align: center;">现有公告内容</h3>
        <div v-html="noticeHtml" style="width: 100%;minHeight: 100px;font-size:18px;">

        </div>
      </a-col>
    </a-row>
    <a-form @submit="handleSubmit" :form="form">
      <h3 style="text-align: center;">设置游戏内公告内容</h3>
      <a-form-item
        label="公告内容"
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
      >
        <a-textarea
        :autosize="{ minRows: 4 }"
          v-decorator="[
            'notice',
            {rules: [{ required: true, message: '请输入生成公告内容' }]}
          ]"
          name="notice"
          placeholder="请输入生成公告内容"
        />
      </a-form-item>
      
      <a-form-item :wrapperCol="{ span: 24 }" style="text-align: center">
        <a-button htmlType="submit" type="primary">设置</a-button>
        <!-- <a-button style="margin-left: 8px">保存</a-button> -->
      </a-form-item>
    </a-form>
  </a-card>
</template>

<script>
import {getGameNotice , setGameNotice} from '@/api/user'
export default {
  name: 'SetGameNotice',
  data() {
    return {
      description: '表单页用于向用户收集或验证信息，基础表单常见于数据项较少的表单场景。',
      value: 1,
      // form
      form: this.$form.createForm(this),
      noticeHtml: ''
    }
  },
  methods: {                    
    // handler
    handleSubmit(e) {
      e.preventDefault()
      let s = this;
      s.form.validateFields((err, values) => {
        if (!err) {
          // eslint-disable-next-line no-console
          //console.log('Received values of form: ', values)
        }
        setGameNotice(values).then(res => {
          if(res.code == 0) {
            s.$message.success('登录公告设置成功')
          }
        })
      })
    }
  },
  mounted: function() {
    let s = this;
    getGameNotice().then(res => {
        let str = res == "" || res.content == "" ? '<span>公告内容为空</span>' : res.replace('size','font-size');
        s.noticeHtml = str
        console.log(str);
    })
  }
}
</script>
