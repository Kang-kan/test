import Vue from 'vue'
import { login, getAccessRight, logout } from '@/api/login'
import { ACCESS_TOKEN } from '@/store/mutation-types'
import { welcome } from '@/utils/util'

const user = {
  state: {
    token: '',
    name: '',
    welcome: '',
    avatar: '',
    roles: [],
    info: {}
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, { name, welcome }) => {
      state.name = name
      state.welcome = welcome
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_INFO: (state, info) => {
      state.info = info
    }
  },

  actions: {
    // 登录
    Login ({ commit }, loginReq) {
      return new Promise((resolve, reject) => {
        login(loginReq).then(result => {
          const accessToken = Vue.ls.get(ACCESS_TOKEN)
          commit('SET_TOKEN', accessToken)

          result.avatar = '/avatar2.jpg'
          localStorage.setItem('USER_INFO', JSON.stringify(result))
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取菜单及权限
    GetAccessRight ({ commit }) {
      return new Promise((resolve, reject) => {
        getAccessRight().then(result => {
          if (result.roles && result.systemMenuDto) {
            commit('SET_ROLES', result.roles)
            commit('SET_INFO', result)
            commit('SET_NAME', { name: result.name, welcome: welcome() })
            commit('SET_AVATAR', '/avatar2.jpg')
          } else {
            reject(new Error('该用户还没分配权限!'))
          }

          resolve(result)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    Logout ({ commit, state }) {
      return new Promise((resolve) => {
        logout(state.token).then(() => {
          resolve()
        }).catch(() => {
          resolve()
        }).finally(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          Vue.ls.remove(ACCESS_TOKEN)
        })
      })
    }

  }
}

export default user
