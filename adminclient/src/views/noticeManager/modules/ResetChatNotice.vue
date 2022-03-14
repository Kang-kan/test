<template>
  <a-modal title="修改跑马灯"
    :width="640"
    :visible="visible"
    :confirmLoading="confirmLoading"
    :destroyOnClose="true"
    @ok="handleSubmit"
    @cancel="handleCancel"
    >
    <a-spin :spinning="confirmLoading">
      <a-form @submit="handleSubmit" :form="form">
        <a-form-item label="选择渠道" :labelCol="{lg: {span: 7}, sm: {span: 7}}"
          :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
        >
          <a-select
            mode="multiple"
            v-decorator="[
            'channels',
            {initialValue: channels},
            {rules: [{ required: false }]}
          ]"
          placeholder="不选默认全渠道"
          >
            <a-select-option v-for="i in channelIdList" :key="i.id" :value="i.id">{{i.name}}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="选择服务器" :labelCol="{lg: {span: 7}, sm: {span: 7}}"
          :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
        >
          <a-select
            mode="multiple"
            v-decorator="[
            'serverIds',
            {initialValue: serverIds},
            {rules: [{ required: false }]}
          ]"
          placeholder="不选默认全服务器"
          >
            <a-select-option v-for="i in serverList" :key="i.id" :value="i.id">{{i.name}}({{i.id}})</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          label="跑马灯内容"
          :labelCol="{lg: {span: 7}, sm: {span: 7}}"
          :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
        >
          <a-textarea
          :autosize="{ minRows: 4 }"
            v-decorator="[
              'notice',
              {initialValue: notice},
              {rules: [{ required: true, message: '请输入生成跑马灯内容' }]}
            ]"
            name="notice"
            placeholder="请输入生成跑马灯内容"
          />
        </a-form-item>
        <a-form-item
          label="时间间隔（秒）"
          :labelCol="{lg: {span: 7}, sm: {span: 7}}"
          :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
        >
          <a-input-number
            v-decorator="[
              'intervalTime',
              {initialValue: intervalTime},
              {rules: [{ required: true, message: '请输入生成轮播时间间隔' }]}
            ]"
            name="notice"
            placeholder="请输入时间间隔（单位秒）"
          />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { updateChatNotice } from '@/api/user'
export default {
  name: 'ResetChatNotice',
  data() {
    return {
      serverList: [],
      channelIdList: [],
      description: '创建轮播跑马灯公告',
      value: 1,
      chatNoticeId: 0,
      // form
      form: this.$form.createForm(this),
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
      rewSelect: '',
      rewNum: '',
      serverIds: null,
      channels: null,
      intervalTime: null,
      notice: ''
    }
  },
  methods: {
    // handler
    show(record) {
      let s = this
      s.visible = true
      s.chatNoticeId = record.id
      s.serverIds = record.serverIds
      s.channels = record.channels
      s.intervalTime = record.intervalTime
      s.notice = record.notice
    },
    handleCancel() {
      this.visible = false
    },
    handleSelectChange(value) {
      console.log('选中:', value)
      this.rewSelect = value
    },
    handleSubmit(e) {
      e.preventDefault()
      let s = this
      this.form.validateFields((err, values) => {
        if (!err) {
          // eslint-disable-next-line no-console
          //console.log('Received values of form: ', values)
        }
        values['chatNoticeId'] = s.chatNoticeId
        updateChatNotice(values).then(res => {
          if (res != null) {
            s.visible = false
            s.confirmLoading = false
            s.$emit('ok', values)
            s.$message.success('修改轮播跑马灯成功')
          }else {
              s.confirmLoading = false
            }
        })
      })
    }
  },
  mounted: function() {
    let s = this
    s.$ajax.get('../gameVar.json').then(res => {
      if (!res || !res.data) {
        return
      }
      s.channelIdList = res.data.channelIdList
    })
    this.$http.get('/roleGameServer/getMyServers').then(res => {
      s.serverList = res
      // if(!res || res.length == 0) {
      //   return;
      // }
      // getServerInfo({ serverId: res[0].id }).then(res => {
      //   if (!res) {
      //     let startTime = 20190101
      //     let endTime = s.$moment().format('YYYYMMDD')
      //     s.time = [s.$moment(startTime.toString(), 'YYYYMMDD'), s.$moment(endTime, 'YYYYMMDD')]
      //     s.timeArr[0] = startTime
      //     s.timeArr[1] = endTime
      //     return
      //   }
      //   let startTime = s.$moment(res.openTime).format('YYYYMMDD')
      //   let endTime = s.$moment().format('YYYYMMDD')
      //   s.time = [s.$moment(startTime, 'YYYYMMDD'), s.$moment(endTime, 'YYYYMMDD')]
      //   s.timeArr[0] = startTime
      //   s.timeArr[1] = endTime
      // })
    })
  }
}
</script>
