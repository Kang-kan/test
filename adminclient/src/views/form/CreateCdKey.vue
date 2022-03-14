<template>
  <a-card :body-style="{padding: '24px 32px'}" :bordered="false">
    <a-form @submit="handleSubmit" :form="form">
      <a-form-item
        label="生成数量"
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
      >
        <a-input
          v-decorator="[
            'num',
            {rules: [{ required: true, message: '请输入生成礼包码数量' }]}
          ]"
          name="num"
          placeholder="请输入生成礼包码数量"
        />
      </a-form-item>
      <a-form-item
        label="礼包码长度"
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
      >
        <a-input
          v-decorator="[
            'codeLengh',
            {rules: [{ required: true, message: '请输入生成礼包码长度' }]}
          ]"
          name="codeLengh"
          placeholder="请输入生成礼包码长度"
        />
      </a-form-item>
      <a-form-item
        label="prefix"
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
      >
        <a-input
          v-decorator="[
            'prefix',
            {rules: [{ required: true, message: '请输入生成prefix' }]}
          ]"
          name="prefix"
          placeholder="请输入生成prefix"
        />
      </a-form-item>
      <a-form-item
        label="suffix"
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
      >
        <a-input
          v-decorator="[
            'suffix',
            {rules: [{ required: true, message: '请输入生成suffix' }]}
          ]"
          name="suffix"
          placeholder="请输入生成suffix"
        />
      </a-form-item>
      <a-form-item
        label="type"
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
      >
        <a-input
          v-decorator="[
            'type',
            {rules: [{ required: true, message: '请输入生成type' }]}
          ]"
          name="type"
          placeholder="请输入生成type"
        />
      </a-form-item>
      <a-form-item
        label="reward"
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
      >
        <a-input
          v-decorator="[
            'reward',
            {rules: [{ required: true, message: '请输入生成reward' }]}
          ]"
          name="reward"
          placeholder="请输入生成reward"
        />
      </a-form-item>
      <a-form-item
        label="restCount"
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
      >
        <a-input
          v-decorator="[
            'restCount',
            {rules: [{ required: true, message: '请输入生成restCount' }]}
          ]"
          name="restCount"
          placeholder="请输入生成restCount"
        />
      </a-form-item>
      <a-form-item
        label="canUsePerDay "
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
      >
        <a-input
          v-decorator="[
            'canUsePerDay ',
            {rules: [{ required: true, message: '请输入生成canUsePerDay ' }]}
          ]"
          name="canUsePerDay "
          placeholder="请输入生成canUsePerDay "
        />
      </a-form-item>
      <a-form-item
        label="canUsetotal"
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
      >
        <a-input
          v-decorator="[
            'canUsetotal',
            {rules: [{ required: true, message: '请输入生成canUsetotal' }]}
          ]"
          name="canUsetotal"
          placeholder="请输入生成canUsetotal"
        />
      </a-form-item>
      <a-form-item
        label="channelStr"
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
      >
        <a-input
          v-decorator="[
            'channelStr',
            {rules: [{ required: true, message: '请输入生成channelStr' }]}
          ]"
          name="channelStr"
          placeholder="请输入生成channelStr"
        />
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
            {rules: [{ required: true, message: '请选择起止日期' }]}
          ]"
        />
      </a-form-item>
      <!-- <a-form-item
        label="目标描述"
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }">
        <a-textarea
          rows="4"
          placeholder="请输入你阶段性工作目标"
          v-decorator="[
            'description',
            {rules: [{ required: true, message: '请输入目标描述' }]}
          ]" />
      </a-form-item>
      <a-form-item
        label="衡量标准"
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }">
        <a-textarea
          rows="4"
          placeholder="请输入衡量标准"
          v-decorator="[
            'type',
            {rules: [{ required: true, message: '请输入衡量标准' }]}
          ]" />
      </a-form-item>
      <a-form-item
        label="客户"
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }">
        <a-input
          placeholder="请描述你服务的客户，内部客户直接 @姓名／工号"
          v-decorator="[
            'customer',
            {rules: [{ required: true, message: '请描述你服务的客户' }]}
          ]" />
      </a-form-item>
      <a-form-item
        label="邀评人"
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
        :required="false"
      >
        <a-input placeholder="请直接 @姓名／工号，最多可邀请 5 人" />
      </a-form-item>
      <a-form-item
        label="权重"
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
        :required="false"
      >
        <a-input-number :min="0" :max="100" />
        <span> %</span>
      </a-form-item>
      <a-form-item
        label="目标公开"
        :labelCol="{lg: {span: 7}, sm: {span: 7}}"
        :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
        :required="false"
        help="客户、邀评人默认被分享"
      >
        <a-radio-group v-model="value">
          <a-radio :value="1">公开</a-radio>
          <a-radio :value="2">部分公开</a-radio>
          <a-radio :value="3">不公开</a-radio>
        </a-radio-group>
        <a-form-item>
          <a-select mode="multiple" v-if="value === 2">
            <a-select-option value="4">同事一</a-select-option>
            <a-select-option value="5">同事二</a-select-option>
            <a-select-option value="6">同事三</a-select-option>
          </a-select>
      </a-form-item>-->
      <!-- </a-form-item> -->
      <a-form-item :wrapperCol="{ span: 24 }" style="text-align: center">
        <a-button htmlType="submit" type="primary">生成</a-button>
        <!-- <a-button style="margin-left: 8px">保存</a-button> -->
      </a-form-item>
    </a-form>
  </a-card>
</template>

<script>
export default {
  name: 'CreateCdKey',
  data() {
    return {
      description: '表单页用于向用户收集或验证信息，基础表单常见于数据项较少的表单场景。',
      value: 1,
      // form
      form: this.$form.createForm(this)
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
        let obj = {}
        if (values.buildTime) {
          obj.startTime = values.buildTime[0].valueOf()
          obj.endTIme = values.buildTime[1].valueOf()
          delete values.buildTime
        }
        Object.assign(obj, values)
        obj.cmd = 3000
        console.log(obj)
        let url = 'http://192.168.2.140:18555'
        for (let key in obj) {
            if(obj[key]) {
                if(url.indexOf('?') >= 0) {
                    url += '&' + key + '=' + obj[key];
                }else {
                    url += '?' + key + '=' + obj[key];
                }
            }
        }
        window.location.href = url;
        // this.$ajax({ 
        //     url: 'http://192.168.2.140:18555', 
        //     params: obj,
        //     responseType: 'arraybuffer',
        //     method: 'get'
        //     })
        //   .then(res => {
        //     console.log('response', res)
        //     let blob = new Blob([res.data], { type: 'text/plain' })
        //     let objectUrl = URL.createObjectURL(blob)
        //     console.log('bloburl: ', objectUrl, blob)
        //     window.location.href = objectUrl
        //   })
        //   .catch(err => {
        //     if (err) {
        //       throw err
        //     }
        //   })
      })
    }
  }
}
</script>
