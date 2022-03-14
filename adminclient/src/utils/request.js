import Vue from 'vue'
import axios from 'axios'
import store from '@/store'
import notification from 'ant-design-vue/es/notification'
import {
  VueAxios
} from './axios'
import {
  ACCESS_TOKEN
} from '@/store/mutation-types'

// 创建 axios 实例
const service = axios.create({
  baseURL: process.env.VUE_APP_API_BASE_URL, // api base_url
  timeout: 6000 // 请求超时时间
})

const err = (error) => {
  if (error.response) {
    const data = error.response.data
    const token = Vue.ls.get(ACCESS_TOKEN)
    if (error.response.status === 403) {
      notification.error({
        message: 'Forbidden',
        description: data.message
      })
    }
    if (error.response.status === 401 && !(data.result && data.result.isLogin)) {
      notification.error({
        message: 'Unauthorized',
        description: 'Authorization verification failed'
      })
      if (token) {
        store.dispatch('Logout').then(() => {
          setTimeout(() => {
            window.location.reload()
          }, 1500)
        })
      }
    }
  }
  return Promise.reject(error)
}

// request interceptor
service.interceptors.request.use(config => {
  const token = Vue.ls.get(ACCESS_TOKEN)
  if (token) {
    config.headers['Access-Token'] = token // 让每个请求携带自定义 token 请根据实际情况自行修改
  }
  return config
}, err)

// response interceptor
service.interceptors.response.use((response) => {
  if (response.data.code && response.data.code != 0) {
    let msg = '';
    switch (response.data.code) {
      case -1:
        msg = "请求失败"
        break;
      case -101:
        msg = "未授权"
        break;
      case -102:
        msg = "token过期"
        break;
      case -103:
        msg = "微信登录异常"
        break;
      case -104:
        msg = "参数不能为空"
        break;
      case -105:
        msg = "参数错误"
        break;
      case -106:
        msg = "http请求异常"
        break;
      case -107:
        msg = "请求结果为空"
        break;
      case -108:
        msg = "参数长度过短"
        break;
      case -109:
        msg = "参数长度过长"
        break;
      case -110:
        msg = "登录参数过期"
        break;
      default:
        msg = "请求失败"
        break;
    }
    if(response.data.message) {
      msg = response.data.message
    }
    notification.error({
      message: '服务器返回错误',
      description: '结果码：' + response.data.code + '，错误信息：' + msg
    })
    return null
  }
  const accessToken = response.headers['access-token']
  if (accessToken != null && accessToken != undefined) {
    // 30 分钟过期
    Vue.ls.set(ACCESS_TOKEN, accessToken, 30 * 60 * 1000)
  }
  if (response.data.content) {
    return response.data.content
  } else {
    return response.data
  }
}, err)

const installer = {
  vm: {},
  install(Vue) {
    Vue.use(VueAxios, service)
  }
}

export {
  installer as VueAxios,
  service as axios
}
