<template>
  <a-modal
    title="刷新数值配置表"
    :width="640"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="选择服务器" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            mode="multiple"
            v-decorator="[
            'serverIds',
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
import { reloadBaseData } from '@/api/user'

export default {
  data() {
    return {
      nameData: [],
      isfetch: false,
      playervisible: true,
      rewardstxt: '已选奖品：无',
      rewardsArr: [],
      rewNameArr: [],
      serverList: [],
      playertxt: '已有玩家：无',
      playerArr: [],
      playerName: '',
      playerId: '',
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
    add() {
      this.visible = true
    },
    handleSubmit() {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      let s = this
      validateFields((errors, values) => {
        if (!errors) {
            let obj = values.serverIds;
          reloadBaseData(obj).then(result => {
            if (result != null) {
              this.visible = false
              this.confirmLoading = false
              this.$emit('ok', values)
              s.$message.success('成功刷新数值表')
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
    handleSelectChange(value) {
      console.log('选中:', value)
      this.rewSelect = value
    },
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
