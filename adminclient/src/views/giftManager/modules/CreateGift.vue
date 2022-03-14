<template>
    <a-modal
    title="创建礼包"
    :width="640"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
      <a-form-item
        label="礼包奖励"
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
      >
        <a-input
          v-decorator="[
            'reward',
            {rules: [{ required: true, message: '请输入生成reward' }]}
          ]"
          name="reward"
          placeholder="礼包奖励(格式：id1:num1,id2:num2...)"
        />
      </a-form-item>
      <a-form-item
        label="礼包类型"
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
      >
        <a-input
          v-decorator="[
            'type',
            {rules: [{ required: false, message: '请输入生成礼包类型' }]}
          ]"
          name="type"
          placeholder="请输入生成礼包类型"
        />
      </a-form-item>
      <a-form-item
        label="礼包描述"
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
      >
        <a-input
          v-decorator="[
            'giftDesc',
            {rules: [{ required: false, message: '请输入礼包描述' }]}
          ]"
          name="giftDesc"
          placeholder="请输入礼包描述"
        />
      </a-form-item>
      <a-form-item
        label="每日可使用数量"
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
      >
        <a-input
          v-decorator="[
            'canUsePerDay ',
            {rules: [{ required: false, message: '请输入生成每日可使用数量' }]}
          ]"
          name="canUsePerDay"
          placeholder="请输入生成每日可使用数量"
        />
      </a-form-item>
      <a-form-item
        label="个人总可用次数"
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
      >
        <a-input
          v-decorator="[
            'canUsetotal',
            {rules: [{ required: false, message: '请输入生成总可用次数' }]}
          ]"
          name="canUsetotal"
          placeholder="请输入生成总可用次数"
        />
      </a-form-item>
      <a-form-item label="可用礼包渠道" :labelCol="{lg: {span: 7}, sm: {span: 7}}"
          :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
        >
          <a-select
            mode="multiple"
            v-decorator="[
            'channelStr',
            {rules: [{ required: false, message: '请选择渠道' }]}
          ]"
          placeholder="可用礼包渠道(不选则默认为全渠道)"
          >
            <a-select-option v-for="i in channelIdList" :key="i.id" :value="i.id">{{i.name}}</a-select-option>
          </a-select>
        </a-form-item>
      <a-form-item
        label="起止日期"
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
      >
        <a-range-picker
          name="buildTime"
          style="width: 100%"
          v-decorator="[
            'buildTime',
            {rules: [{ required: false, message: '请选择起止日期' }]}
          ]"
        />
      </a-form-item>
    </a-form>
      </a-spin>
  </a-modal>
</template>

<script>
import { getCreateGiftRewards } from '@/api/user'
export default {
  name: 'CreateGift',
  data() {
    return {
      description: '表单页用于向用户收集或验证信息，基础表单常见于数据项较少的表单场景。',
      value: 1,
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
      rewNum: '',
    }
  },
  methods: {
    // handler
    add() {
      this.visible = true
    },
    download(filename, content, contentType) {
      if (!contentType) contentType = 'application/octet-stream'
      var a = document.createElement('a')
      var blob = new Blob([content], { type: contentType })
      a.href = window.URL.createObjectURL(blob)
      a.download = filename
      a.click()
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
        let obj = {}
        if (values.buildTime) {
          obj.startTime = values.buildTime[0].valueOf()
          obj.endTime = values.buildTime[1].valueOf()
          delete values.buildTime
        }
        Object.assign(obj, values)
        // let reward = [];
        // let arr = obj.reward.split(',')
        // arr.forEach(el => {
        //   reward.push({id: Number(el.split(':')[0]),count: Number(el.split(':')[1]),type:"CURRENCY"})
        // })
        // obj.reward = JSON.stringify(reward);
        if (obj.channelStr && obj.channelStr.length > 0) {
          obj.channelStr = obj.channelStr.join('#')
        }
        // console.log(obj)
        obj['cmd'] = 3002
        getCreateGiftRewards(obj).then(res => {
          if (res) {
            // // s.ModalText = res
            // // s.visible = true
            // s.download('GiftData.txt', res)
            s.visible = false
            s.confirmLoading = false
             s.$emit('ok', values)
            s.$message.success('成功创建礼包')
          }
        })
        // http://139.224.66.150:18556/?cmd=3004
        // http://139.224.66.150:18556/?cmd=3002&type=2001&reward=1:100&canuseperday=2&canusetotal=5&channel=P0007418
        // this.$ajax({
        //   url: 'http://139.224.66.150:18556',
        //   method: 'get',
        //   params: obj
        // }).then(rew => {
        //   if(res) {
        //     console.log(res)
        //   }
        // })
        // return;
        // this.$ajax({
        //   url: 'http://47.102.117.154/admin/giftManager/createGift',
        //   method: 'put',
        //   data: obj
        // })
        // let url = 'http://47.102.117.154/admin/giftManager/createGift'
        // for (let key in obj) {
        //     if(obj[key]) {
        //         if(url.indexOf('?') >= 0) {
        //             url += '&' + key + '=' + obj[key];
        //         }else {
        //             url += '?' + key + '=' + obj[key];
        //         }
        //     }
        // }
        // window.location.href = url;
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
    // this.$http.get('/roleGameServer/getMyServers').then(res => {
    //   s.serverList = res
    //   // if(!res || res.length == 0) {
    //   //   return;
    //   // }
    //   // getServerInfo({ serverId: res[0].id }).then(res => {
    //   //   if (!res) {
    //   //     let startTime = 20190101
    //   //     let endTime = s.$moment().format('YYYYMMDD')
    //   //     s.time = [s.$moment(startTime.toString(), 'YYYYMMDD'), s.$moment(endTime, 'YYYYMMDD')]
    //   //     s.timeArr[0] = startTime
    //   //     s.timeArr[1] = endTime
    //   //     return
    //   //   }
    //   //   let startTime = s.$moment(res.openTime).format('YYYYMMDD')
    //   //   let endTime = s.$moment().format('YYYYMMDD')
    //   //   s.time = [s.$moment(startTime, 'YYYYMMDD'), s.$moment(endTime, 'YYYYMMDD')]
    //   //   s.timeArr[0] = startTime
    //   //   s.timeArr[1] = endTime
    //   // })
    // })
  }
}
</script>
