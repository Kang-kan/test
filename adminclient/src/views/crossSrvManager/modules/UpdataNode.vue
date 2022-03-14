<template>
  <a-modal
    title="修改世界服配置信息"
    :width="640"
    :visible="visible"
    :confirmLoading="confirmLoading"
    :destroyOnClose="true"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="节点id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['node', {initialValue: node},{rules: [{required: true, min: 1, max: 128, message: '长度为0-128个字符！'}]}]"
          />
        </a-form-item>
        <a-form-item label="选择服务器" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            mode="multiple"
            v-decorator="[
            'servers',
            {rules: [{ required: true, message: '请选择服务器' }]}
          ]"
          >
            <a-select-option v-for="i in serverList" :key="i.id" :value="i.id">{{i.name}}({{i.id}})</a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import xmlObj from '@/config/xmlConfig'
  import { updateNode } from '@/api/user'

  export default {
    data() {
      return {
        serverList: null,
        isfetch: false,
        node: '',
        servers:null,
        playervisible: true,
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
      show(record) {
        let s = this
        s.visible = true
        s.node = record.beginNode
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
        const {
          form: { validateFields }
        } = this
        this.confirmLoading = true
        let s = this
        validateFields((errors, values) => {
          if (!errors) {
            values.servers = values.servers.join(',')
            updateNode(values).then(result => {
              if (result.code == 0) {
                this.visible = false
                this.confirmLoading = false
                this.$emit('ok', values)
                s.$message.success('修改节点配置成功')
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
