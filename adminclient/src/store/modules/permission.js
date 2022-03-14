import { constantRouterMap, toMenuRoute } from '@/config/router.config'
import { BasicLayout } from '@/layouts'

/**
 * 过滤账户是否拥有某一个权限，并将菜单从加载列表移除
 *
 * @param permission
 * @param route
 * @returns {boolean}
 */
function hasPermission (permission, route) {
  if (route.meta && route.meta.permission) {
    let flag = false
    for (let i = 0, len = permission.length; i < len; i++) {
      flag = route.meta.permission.includes(permission[i])
      if (flag) {
        return true
      }
    }
    return false
  }
  return true
}

/**
 * 单账户多角色时，使用该方法可过滤角色不存在的菜单
 *
 * @param roles
 * @param route
 * @returns {*}
 */
// eslint-disable-next-line
function hasRole(roles, route) {
  if (route.meta && route.meta.roles) {
    return route.meta.roles.includes(roles.id)
  } else {
    return true
  }
}

const permission = {
  state: {
    routers: constantRouterMap,
    addRouters: []
  },
  mutations: {
    SET_ROUTERS: (state, systemMenus) => {
      const routers = toMenuRoute(systemMenus)
      const routeConfigs = [
        { path: '/', name: 'index', component: BasicLayout, meta: { title: '首页', icon: 'home' }, redirect: '/dashboard/workplace', children: routers },
        { path: '*', name: '404', component: () => import('@/views/exception/404'), hidden: true }
      ]
      state.addRouters = routeConfigs
      state.routers = constantRouterMap.concat(routeConfigs)
    }
  },
  actions: {
    GenerateRoutes ({ commit }, data) {
      return new Promise(resolve => {
        const { systemMenus } = data
        commit('SET_ROUTERS', systemMenus)
        resolve()
      })
    }
  }
}

export default permission
