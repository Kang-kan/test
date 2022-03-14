<template>
  <div class="user-wrapper">
    <div class="content-box">
      <a href="https://pro.loacg.com/docs/getting-started" target="_blank"></a>
      <!-- <notice-icon class="action" /> -->
      <a-dropdown>
        <span class="action ant-dropdown-link user-dropdown-menu">
          <a-avatar class="avatar" size="small" :src="avatar" />
          <span>{{ nickname }}</span>
        </span>
        <a-menu slot="overlay" class="user-dropdown-menu-wrapper">
          <a-menu-item key="1">
            <a href="javascript:;" @click="changePassword">
              <a-icon type="user" />
              <span>修改密码</span>
            </a>
          </a-menu-item>
          <a-menu-divider />
          <a-menu-item key="3">
            <a href="javascript:;" @click="handleLogout">
              <a-icon type="logout" />
              <span>退出登录</span>
            </a>
          </a-menu-item>
        </a-menu>
      </a-dropdown>
      <lang-select />
    </div>

    <a-modal title="修改密码" style="top: 180px;" :width="600" v-model="visible" @ok="handleSubmit">
      <a-form :form="form">
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="旧密码">
          <a-input
            type="password"
            placeholder="请输入旧密码"
            v-decorator="['oldPassword', {rules: [{required: true, min: 8, max: 16, message: '请输入旧密码！'}]}]"
          >
          </a-input>
        </a-form-item>

        <a-popover
          placement="rightTop"
          :trigger="['focus']"
          :getPopupContainer="(trigger) => trigger.parentElement"
          v-model="state.passwordLevelChecked"
        >
          <template slot="content">
            <div :style="{ width: '240px' }">
              <div :class="['user-register', passwordLevelClass]">
                强度：
                <span>{{ passwordLevelName }}</span>
              </div>
              <a-progress
                :percent="state.percent"
                :showInfo="false"
                :strokeColor="passwordLevelColor"
              />
              <div style="margin-top: 10px;">
                <span>请至少输入8个字符。请不要使用容易被猜到的密码。</span>
              </div>
            </div>
          </template>
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="新密码">
            <a-input
              type="password"
              autocomplete="false"
              @click="handlePasswordInputClick"
              placeholder="至少8位密码，区分大小写"
              v-decorator="['newPassword', {rules: [{ required: true, message: '至少8位密码，区分大小写'}, { validator: this.handlePasswordLevel }], validateTrigger: ['change', 'blur']}]"
            ></a-input>
          </a-form-item>
        </a-popover>

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="确认密码">
          <a-input
            type="password"
            autocomplete="false"
            placeholder="请再次输入新密码"
            v-decorator="['newPassword1', {rules: [{ required: true, message: '至少8位密码，区分大小写' }, { validator: this.handlePasswordCheck }], validateTrigger: ['change', 'blur']}]"
          ></a-input>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script>
import NoticeIcon from '@/components/NoticeIcon'
import LangSelect from '@/components/tools/LangSelect'
import { mapActions } from 'vuex'
import { salt } from '@/api/login'
import { changePassword } from '@/api/user'
import { aesEncrypt } from '@/utils/CryptoUtil'

const levelNames = {
  0: '低',
  1: '低',
  2: '中',
  3: '强'
}
const levelClass = {
  0: 'error',
  1: 'error',
  2: 'warning',
  3: 'success'
}
const levelColor = {
  0: '#ff0000',
  1: '#ff0000',
  2: '#ff7e05',
  3: '#52c41a'
}

export default {
  name: 'UserMenu',
  components: {
    LangSelect,
    NoticeIcon
  },
  data () {
    return {
      visible: false,
      confirmLoading: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      state: {
        time: 60,
        smsSendBtn: false,
        passwordLevel: 0,
        passwordLevelChecked: false,
        percent: 10,
        progressColor: '#FF0000'
      },
      form: this.$form.createForm(this)
    }
  },
  computed: {
    passwordLevelClass () {
      return levelClass[this.state.passwordLevel]
    },
    passwordLevelName () {
      return levelNames[this.state.passwordLevel]
    },
    passwordLevelColor () {
      return levelColor[this.state.passwordLevel]
    },
    nickname () {
      const userInfo = JSON.parse(localStorage.getItem('USER_INFO'))
      return userInfo.name
    },
    avatar () {
      const userInfo = JSON.parse(localStorage.getItem('USER_INFO'))
      return userInfo.avatar
    }
  },
  methods: {
    ...mapActions(['Logout']),
    handleLogout () {
      this.$confirm({
        title: '提示',
        content: '真的要注销登录吗 ?',
        onOk: () => {
          return this.Logout({})
            .then(() => {
              setTimeout(() => {
                window.location.reload()
              }, 16)
            })
            .catch(err => {
              this.$message.error({
                title: '错误',
                description: err.message
              })
            })
        },
        onCancel () {}
      })
    },
    changePassword () {
      this.visible = true
    },
    handlePasswordLevel (rule, value, callback) {
      let level = 0

      // 判断这个字符串中有没有数字
      if (/[0-9]/.test(value)) {
        level++
      }
      // 判断字符串中有没有字母
      if (/[a-zA-Z]/.test(value)) {
        level++
      }
      // 判断字符串中有没有特殊符号
      if (/[^0-9a-zA-Z_]/.test(value)) {
        level++
      }
      if (value.length < 8 || value.length > 16) {
        callback(new Error('密码长度为8-16位'))
      }
      this.state.passwordLevel = level
      this.state.percent = level * 30
      if (level >= 2) {
        if (level >= 3) {
          this.state.percent = 100
        }
        callback()
      } else {
        if (level === 0) {
          this.state.percent = 10
        }
        callback(new Error('密码强度不够'))
      }
    },
    handlePasswordInputClick () {
      this.state.passwordLevelChecked = true
    },
    handlePasswordCheck (rule, value, callback) {
      const password = this.form.getFieldValue('newPassword')
      if (value === undefined) {
        callback(new Error('请输入密码'))
      }
      if (value && password && value.trim() !== password.trim()) {
        callback(new Error('两次密码不一致'))
      }
      callback()
    },
    handleCancel () {
      this.visible = false
      this.confirmLoading = false
    },
    handleSubmit () {
      const { form: { validateFields }, state } = this
      this.confirmLoading = true
      // 参数检查
      validateFields({ force: true }, (err, values) => {
        if (!err) {
          state.passwordLevelChecked = false
          // 获取加密参数
          salt().then(saltRes => {
            if (saltRes != null) {
              const oldPassword = aesEncrypt(values.oldPassword, saltRes.iv)
              const newPassword = aesEncrypt(values.newPassword, saltRes.iv)
              // 请求修改密码
              changePassword(oldPassword, newPassword).then(changeRes => {
                if (changeRes != null) {
                  this.visible = false
                  this.confirmLoading = false
                  // 提示成功
                  this.$success({
                    title: '修改密码成功',
                    content: '点击确定重新登录',
                    okText: '确定',
                    onOk: () => {
                      // 注销
                      return this.Logout({})
                        .then(() => {
                          setTimeout(() => {
                            window.location.reload()
                          }, 16)
                        })
                        .catch(err => {
                          this.$message.error({
                            title: '错误',
                            description: err.message
                          })
                        })
                    }
                  })
                } else {
                  this.confirmLoading = false
                }
              })
            } else {
              this.confirmLoading = false
            }
          })
        } else {
          this.confirmLoading = false
        }
      })
    }
  },
  watch: {
    'state.passwordLevel' (val) {
    }
  }
}
</script>
