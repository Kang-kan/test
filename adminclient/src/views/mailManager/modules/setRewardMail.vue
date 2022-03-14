<template>
  <a-modal
    title="修改奖励邮件"
    :width="640"
    :visible="visible"
    :confirmLoading="confirmLoading"
    :destroyOnClose="true"
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
            {initialValue: serverIds},
            {rules: [{ required: true, message: '请选择服务器' }]}
          ]"
          >
            <a-select-option v-for="i in serverList" :key="i.id" :value="i.id">{{i.name}}({{i.id}})</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="邮件类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            v-decorator="[
            'type',
            {initialValue: type},
            {rules: [{ required: true, message: '选择邮件类型' }]}
          ]"
          @change="mailTypeChange"
          >
            <a-select-option key="1" value="1">全服邮件(1)</a-select-option>
            <a-select-option key="2" value="2">个人邮件(2)</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="邮件标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['title', {initialValue: title},{rules: [{required: true, min: 2, max: 128, message: '长度为0-128个字符！'}]}]"
          />
        </a-form-item>
        <a-form-item label="邮件内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea
            :row="4"
            v-decorator="['content',{initialValue: content}, {rules: [{required: true, min: 2, max: 128, message: '长度为0-1024个字符！'}]}]"
            :value="content"
          />
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['remark',{initialValue: remark}, {rules: [{required: true, min: 0, max: 32, message: '长度为0-32个字符！'}]}]"
            :value="remark"
          />
        </a-form-item>
        <a-form-item label="指定玩家" :labelCol="labelCol" :wrapperCol="wrapperCol" v-if="playervisible">
            <a-row>
                <a-col>
                    <span v-text="playertxt"></span>
                </a-col>
            </a-row>
            <a-row>
                <a-col>
                    <a-button type="primary" @click="deleteOnePlayer" style="margin-right: 18px;">删除单个玩家</a-button>
                    <a-button type="danger" @click="deleteAllPlayer">清空全部玩家</a-button>
                </a-col>
            </a-row>
            <a-row>
                <a-col>
                  <a-input placeholder="请输入玩家id" v-model="playerId" v-decorator="['', {rules: [{required: true}]}]"/>
                </a-col>
                <a-col>
                    <a-input placeholder="请输入玩家名" v-model="playerName" v-decorator="['', {rules: [{required: true}]}]"/>
                </a-col>
                <a-col>
                    <a-button type="primary" @click="addPlayer">确定添加</a-button>
                </a-col>
            </a-row>
        </a-form-item>
        <a-form-item label="奖励" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-row>
                <a-col>
                    <span v-text="rewardstxt"></span>
                </a-col>
            </a-row>
            <a-row>
                <a-col>
                    <a-button type="primary" @click="deleteOne" style="margin-right: 18px;">删除单个奖品</a-button>
                    <a-button type="danger" @click="deleteAll">清空全部奖品</a-button>
                </a-col>
            </a-row>
            <a-row>
                <a-col>
                    <!-- <a-tree-select
              showSearch
              :dropdownStyle="{ maxHeight: '400px', overflow: 'auto' }"
              placeholder="Please select"
              allowClear
              treeDefaultExpandAll
              @change="handleSelectChange"
            >
              <a-tree-select-node value="0" title="全部" key="0"></a-tree-select-node>
              <a-tree-select-node value="parent 1-0" title="道具" key="0-1-1">
                <a-tree-select-node v-for="item in itemObj" :title="item" :value="item" />
              </a-tree-select-node>
              <a-tree-select-node value="parent 2-0" title="武器" key="1-1-1">
                <a-tree-select-node v-for="item in equipObj" :title="item" :value="item" />
              </a-tree-select-node>
            </a-tree-select> -->
                  <a-select 
                    showSearch
                    @change="handleSelectChange"
                    placeholder="搜索物品"   
                    @search="handleSerach"
                    >
                    <!-- {
                    optionsData.length && optionsData.map( (item, index) => (
                        <Select.Option key={index} value={item}>{item}</Select.Option>)
                    ) 
                    } -->
                    <a-select-option v-for="d in nameData" :titile="d" :value="d">{{d}}</a-select-option>
                  </a-select>
                </a-col>
                <a-col>
                    <span style="margin-right: 10px;">数量: </span>
                    <a-input-number id="inputNumber" :min="1" v-model="rewNum"/>
                </a-col>
                <a-col>
                    <a-button type="primary" @click="addReward">确定添加</a-button>
                </a-col>
            </a-row>
            
        </a-form-item>
        <!-- <a-form-item
          label="起止日期"
          :labelCol="{lg: {span: 7}, sm: {span: 7}}"
          :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
        >
          <a-range-picker
            name="buildTime"
            style="width: 100%"
            v-decorator="[
            'buildTime',
            {initialValue: buildTime},
            {rules: [{ required: false, message: '请选择起止日期' }]}
          ]"
          />
        </a-form-item> -->
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import xmlObj from '@/config/xmlConfig'
import { updateRewardMail } from '@/api/user'

export default {
  data() {
    return {
      nameData: [],
      isfetch: false,
      title: '',
      content: '',
      remark: '',
      serverIds: null,
      type: null,
      buildTime: null,
      playervisible: true,
      rewardstxt: '已选奖品：无',
      rewardsArr: [],
      rewNameArr: [],
      serverList: [],
      playertxt: '已有玩家：无',
      playerArr: [],
      playerName: '',
      playerId: '',
      mailId: '',
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
  computed: {
    itemObj: function() {
      let arr = []
      for (let key in xmlObj.itemNameObj) {
        arr.push(`${key}:${xmlObj.itemNameObj[key]}`)
      }
      return arr
    },
    equipObj: function() {
      let arr = []
      for (let key in xmlObj.equipNameObj) {
        arr.push(`${key}:${xmlObj.equipNameObj[key]}`)
      }
      return arr
    }
  },
  methods: {
    handleSerach(e) {
      let s = this
      let clusterValue = s.itemObj.concat(s.equipObj)
      //类似函数节流
      setTimeout(function() {
        console.log('开始搜索')
        s.loadOption(clusterValue, e)
      }, 300)
    },
    loadOption(clusterValue, keyWords) {
      let newOptionsData = []
      let arrData = clusterValue
      if (clusterValue && clusterValue.length > 0) {
        let len
        len = arrData.length

        //初始化
        if (keyWords == '') {
          newOptionsData.length = 0
        } else {
          //用户搜索
          newOptionsData = []
          for (var j = 0; j < len; j++) {
            if (arrData[j].indexOf(keyWords) != -1) {
              newOptionsData.push(arrData[j])
              // if(newOptionsData.length > 100) break;
            }
          }
        }
      }
      if (newOptionsData.length > 50) newOptionsData.length = 50
      console.log('搜索结果', newOptionsData)
      // return newOptionsData
      // this.setState({
      //   nameData: newOptionsData
      // })
      this.nameData = newOptionsData
    },
    show(record) {
      let s = this
      s.visible = true
      s.mailId = record.id
      s.title = record.title
      s.content = record.content
      s.remark = record.remark
      s.serverIds = record.serverIds
      s.type = record.type + ''
      s.buildTime = [s.$moment(record.startTime), s.$moment(record.endTime)]
      console.log('单挑信息: ', record)
      if (
        record.rewardIds &&
        record.rewardIds != 'undefined' &&
        record.rewardIds != 'undefined,' &&
        record.rewards.indexOf('undefined') < 0
      ) {
        s.rewardsArr = JSON.parse(record.rewardIds)
        s.rewNameArr = []
        record.rewards.split(',').forEach(el => {
          if (el && el.indexOf(':') >= 0) {
            s.rewNameArr.push({ name: el.split(':')[0], num: Number(el.split(':')[1]) })
          }
        })
        let str = '已选奖品: '
        // s.rewNameArr.forEach(el => {
        //   str += el.name + ':' + el.num + ' '
        // })
        s.rewardstxt = str + record.rewards
      }

      if (record.players && record.players.length > 0) {
        s.playerArr = record.players
        let string = '已选玩家: '
        s.playerArr.forEach(el => {
          string += el.playerName + ' '
        })
        s.playertxt = string
      }
    },
    range(start, end) {
      const result = []
      for (let i = start; i < end; i++) {
        result.push(i)
      }
      return result
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
      const { form: { validateFields } } = this
      this.confirmLoading = true
      let s = this
      validateFields((errors, values) => {
        if (!errors) {
          // if (!values.buildTime || !values.serverIds || !values.rewards || !values.title || !values.content) {
          //   this.confirmLoading = false
          //   return
          // }
          values['players'] = s.playerArr
          values['rewards'] = JSON.stringify(s.rewardsArr)
          // values['endTime'] = values.buildTime[1].valueOf()
          // values['startTime'] = values.buildTime[0].valueOf()
          values['rewardMailId'] = this.mailId
          values.type = Number(values.type)
          console.log('修改邮件: ', values)
          values.buildTime = null
          updateRewardMail(values).then(result => {
            if (result != null) {
              this.visible = false
              this.confirmLoading = false
              this.$emit('ok', values)
              s.$message.success('修改邮件成功')
            } else {
              this.confirmLoading = false
              
            }
          })
        } else {
          this.confirmLoading = false
          
        }
      })
    },
    mailTypeChange(value) {
      if (Number(value) == 1) {
        this.playervisible = false
      } else {
        this.playervisible = true
      }
    },
    handleCancel() {
      this.visible = false
    },
    handleSelectChange(value) {
      this.rewSelect = value
    },
    deleteOne() {
      let s = this
      let len = s.rewardsArr.length
      if (len <= 0) {
        return
      }
      s.rewardsArr.splice(len - 1, 1)
      s.rewNameArr.splice(len - 1, 1)
      let str = '已选奖品: '
      s.rewNameArr.forEach(el => {
        str += el.name + ':' + el.num + ' '
      })
      s.rewardstxt = str
    },
    deleteAll() {
      let s = this
      s.rewardsArr.length = 0
      s.rewNameArr.length = 0
      s.rewardstxt = '已选奖品：无'
    },
    deleteOnePlayer() {
      let s = this
      let len = s.playerArr.length
      if (len <= 0) {
        return
      }
      s.playerArr.splice(len - 1, 1)
      let str = '已选奖品: '
      s.playerArr.forEach(el => {
        str += el.playerName + ' '
      })
      s.playertxt = str
    },
    deleteAllPlayer() {
      let s = this
      s.playerArr.length = 0
      s.playertxt = '已选奖品：无'
    },
    addReward() {
      let s = this
      if (!s.rewSelect) {
        s.$message.success('请选择奖品')
        return
      }
      if (!s.rewNum || s.rewNum < 1) {
        s.$message.success('请输入奖品数量')
        return
      }
      let hasSame = false
      let id = s.rewSelect.split(':')[0]
      let name = s.rewSelect.split(':')[1]
      s.rewardsArr.forEach((el, index) => {
        if (el.id == id) {
          el.num += Number(s.rewNum)
          hasSame = true
        }
      })
      if (!hasSame) {
        s.rewardsArr.push({ id: Number(id), num: Number(s.rewNum) })
        s.rewNameArr.push({ name: name, num: Number(s.rewNum) })
      } else {
        s.rewNameArr.forEach(el => {
          if (el.name == name) {
            el.num += Number(s.rewNum)
          }
        })
      }
      let str = '已选奖品: '
      s.rewNameArr.forEach(el => {
        str += el.name + ':' + el.num + ' '
      })
      s.rewardstxt = str
    },
    addPlayer() {
      let s = this
      if (!s.playerName) {
        s.$message.success('请输入角色名')
        return
      }
      if (!s.playerId) {
        s.$message.success('请输入角色id')
        return
      }
      let hasSame = false
      s.playerArr.forEach((el, index) => {
        if (el.playerId == s.playerId) {
          hasSame = true
        }
      })
      if (!hasSame) {
        s.playerArr.push({ playerId: Number(s.playerId), playerName: s.playerName })
      } else {
        return
      }
      let str = '已选玩家: '
      s.playerArr.forEach(el => {
        str += el.playerName + ' '
      })
      s.playertxt = str
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
