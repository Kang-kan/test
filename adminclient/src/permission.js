import Vue from 'vue'
import router from './router'
import store from './store'

import NProgress from 'nprogress' // progress bar
import '@/components/NProgress/nprogress.less' // progress bar custom style
import notification from 'ant-design-vue/es/notification'
import {
  setDocumentTitle,
  domTitle
} from '@/utils/domUtil'
import {
  ACCESS_TOKEN
} from '@/store/mutation-types'

NProgress.configure({
  showSpinner: false
}) // NProgress Configuration

const whiteList = ['login', 'register', 'registerResult'] // no redirect whitelist

router.beforeEach((to, from, next) => {
  NProgress.start() // start progress bar
  to.meta && (typeof to.meta.title !== 'undefined' && setDocumentTitle(`${to.meta.title} - ${domTitle}`))
  if (Vue.ls.get(ACCESS_TOKEN)) {
    /* has token */
    if (to.path === '/user/login' || to.path === '/') {
      next({
        path: '/dashboard/workplace'
      })
      NProgress.done()
    } else {
      if (store.getters.roles.length === 0) {
        // ./store modules
        store
          .dispatch('GetAccessRight')
          .then(res => {
            const systemMenus = res.systemMenuDto;
            systemMenus.sort((a,b) => {
              if(a.id == 200 || b.id == 200) {
                return 1;
              }
              return a.id - b.id;
            });
            store.dispatch('GenerateRoutes', { systemMenus }).then(() => {
              // 根据roles权限生成可访问的路由表
              // 动态添加可访问路由表
              router.addRoutes(store.getters.addRouters)
              const redirect = decodeURIComponent(from.query.redirect || to.path)
              if (to.path === redirect) {
                // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
                next({ ...to,
                  replace: true
                })
              } else {
                // 跳转到目的路由
                next({
                  path: redirect
                })
              }
            })
          })
          .catch(error => {
            notification.error({
              message: '获取权限失败',
              description: '错误信息：' + error
            })
            store.dispatch('Logout').then(() => {
              next({
                path: '/user/login',
                query: {
                  redirect: to.fullPath
                }
              })
            })
            // 测试
          })
      } else {
        next()
      }
    }
  } else {
    if (whiteList.includes(to.name)) {
      // 在免登录白名单，直接进入
      next()
    } else {
      next({
        path: '/user/login',
        query: {
          redirect: to.fullPath
        }
      })
      NProgress.done() // if current page is login will not trigger afterEach hook, so manually handle it
    }
  }
})

router.afterEach(() => {
  NProgress.done() // finish progress bar
})
