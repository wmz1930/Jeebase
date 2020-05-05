import request from '@/utils/request'

export function fetchList (query) {
  return request({
    url: '/user/list',
    method: 'get',
    params: query
  })
}

export function createUser (data) {
  return request({
    url: '/user/create',
    method: 'post',
    data
  })
}

export function updateUser (data) {
  return request({
    url: '/user/update',
    method: 'post',
    data
  })
}

export function updateUserInfo (data) {
  return request({
    url: '/user/update/info',
    method: 'post',
    data
  })
}

export function queryUserInfo (data) {
  return request({
    url: '/auth/user/info',
    method: 'get',
    data
  })
}

export function updatePwd (data) {
  return request({
    url: '/user/pwd',
    method: 'post',
    data
  })
}

export function updateUserStatus (userId, status) {
  return request({
    url: '/user/status/' + userId + '/' + status,
    method: 'post'
  })
}

export function resetUserPassword (id) {
  return request({
    url: '/user/password/' + id,
    method: 'post'
  })
}

export function deleteUser (id) {
  return request({
    url: '/user/delete/' + id,
    method: 'post'
  })
}

export function batchDeleteUser (data) {
  return request({
    url: '/user/batch/delete',
    method: 'post',
    data
  })
}

export function fetchRoleList (data) {
  return request({
    url: '/role/all',
    method: 'get',
    data
  })
}

export function updateUserDataPermission (data) {
  return request({
    url: '/user/update/data/permission',
    method: 'post',
    data
  })
}

export function checkUserAccount (data) {
  return request({
    url: '/user/account/check',
    method: 'post',
    params: data
  })
}

export function checkUserNickName (data) {
  return request({
    url: '/user/nickname/check',
    method: 'post',
    params: data
  })
}

export function checkUserMobile (data) {
  return request({
    url: '/user/mobile/check',
    method: 'post',
    params: data
  })
}

export function checkUserEmail (data) {
  return request({
    url: '/user/email/check',
    method: 'post',
    params: data
  })
}
