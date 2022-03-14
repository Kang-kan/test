import { axios } from '@/utils/request'

const api = {
  user: '/user',
  role: '/role',
  service: '/service',
  permission: '/permission',
  permissionNoPager: '/permission/no-pager',
  orgTree: '/org/tree'
}

export default api

/**
 * 获取用户列表
 * @param parameter 
 */
export function getUserList (parameter) {
  return axios({
    url: '/systemuser/userList',
    method: 'get',
    params: parameter
  })
}

/**
 * 创建用户
 * @param parameter 
 */
export function addUser (parameter) {
  return axios({
    url: '/systemuser/add',
    method: 'post',
    data: parameter
  })  
}
/**
 * 获取权限
 * @param {*} parameter  
 */
export function getAccessRight() {
  return axios({
    url: '/systemuser/accessRight',
    method: 'get'
  })
}
/**
 * 修改自己的密码
 * @param {*} parameter 
 */
export function changeOwnPsd (parameter) {
  return axios({
    url: '/systemuser/changepassword',
    method: 'post',
    data: parameter
  })  
}
/**
 * 删除管理后台用户
 * @param {*} parameter 
 */
export function deleteUser (parameter) {
  return axios({
    url: '/systemuser/delete/' + parameter.userId,
    method: 'post',
    data: parameter
  })  
}
/**
 * 重置他人密码
 * @param {*} parameter 
 */
export function resetOtherPassword (parameter) {
  return axios({
    url: '/systemuser/resetpassword/' + parameter.userId ,
    method: 'post',
    data: parameter
  })  
}
/**
 * 更新用户信息
 * @param {*} parameter 
 */
export function updataUserInfo (parameter) {
  return axios({
    url: '/systemuser/update/' + parameter.userId ,
    method: 'post',
    data: parameter
  })  
}
// export function getRoleList (parameter) {
//   return axios({
//     url: api.role,
//     method: 'get',
//     params: parameter
//   })
// }

export function getServiceList (parameter) {
  return axios({
    url: api.service,
    method: 'get',
    params: parameter
  })
}

export function getPermissions (parameter) {
  return axios({
    url: api.permissionNoPager,
    method: 'get',
    params: parameter
  })
}

export function getOrgTree (parameter) {
  return axios({
    url: api.orgTree,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveService (parameter) {
  return axios({
    url: api.service,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}
