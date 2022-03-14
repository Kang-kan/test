<template>
  <a-modal
    :title="title"
    :width="640"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item
          v-if="isAccount"
          label="账号"
          :labelCol="{lg: {span: 7}, sm: {span: 7}}"
          :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
        >
          <a-input
            v-decorator="[
            'account',
            {rules: [{ required: true, message: '请输入账号' }]}
          ]"
            name="account"
            placeholder="请输入账号"
          />
        </a-form-item>
        <a-form-item
          v-if="!isAccount"
          label="ip"
          :labelCol="{lg: {span: 7}, sm: {span: 7}}"
          :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
        >
          <a-input
            v-decorator="[
            'ip',
            {rules: [{ required: true, message: '请输入ip' }]}
          ]"
            name="ip"
            placeholder="请输入ip"
          />
        </a-form-item>
        <a-form-item
          label="备注"
          :labelCol="{lg: {span: 7}, sm: {span: 7}}"
          :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
        >
          <a-input
            v-decorator="[
            'remark',
            {rules: [{ required: true, message: '请输入备注' }]}
          ]"
            name="remark"
            placeholder="请输入备注"
          />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { addWhiteAccount, addWhiteIp, addBlackIp } from '@/api/user'
  export default {
    name: 'AddWhite',
    data() {
      return {
        title: '',
        description: '表单页用于向用户收集或验证信息，基础表单常见于数据项较少的表单场景。',
        isAccount: true,
        value: 1,
        tab: 1,
        // form
        ModalText: '',
        channelIdList: [],
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
      // handler
      add(key) {
        let s = this
        s.tab = key
        switch (key) {
          case 1:
            s.title = '增加白名单用户'
            s.isAccount = true
            break
          case 2:
            s.title = '增加白名单ip'
            s.isAccount = false
            break
          case 3:
            s.title = '增加黑名单ip'
            s.isAccount = false
            break
        }
        s.visible = true
      },
      handleOk(e) {
        this.visible = false
      },
      handleCancel(e) {
        this.visible = false
      },
      handleSubmit(e) {
        e.preventDefault()
        this.form.validateFields((err, values) => {
          if (!err) {
            // eslint-disable-next-line no-console
            //console.log('Received values of form: ', values)
          }
          let s = this
          switch (s.tab) {
            case 1:
              addWhiteAccount(values).then(res => {
                if (res) {
                  // // s.ModalText = res
                  // // s.visible = true
                  // s.download('GiftData.txt', res)
                  s.visible = false
                  s.confirmLoading = false
                  s.$emit('ok', values)
                  s.$message.success('成功增加白名单用户')
                }
              })
              break
            case 2:
              addWhiteIp(values).then(res => {
                if (res) {
                  // // s.ModalText = res
                  // // s.visible = true
                  // s.download('GiftData.txt', res)
                  s.visible = false
                  s.confirmLoading = false
                  s.$emit('ok', values)
                  s.$message.success('成功增加白名单ip')
                }
              })
              break
            case 3:
              addBlackIp(values).then(res => {
                if (res) {
                  // // s.ModalText = res
                  // // s.visible = true
                  // s.download('GiftData.txt', res)
                  s.visible = false
                  s.confirmLoading = false
                  s.$emit('ok', values)
                  s.$message.success('成功增加黑名单ip')
                }
              })
              break
          }
        })
      }
    }
  }
</script>
