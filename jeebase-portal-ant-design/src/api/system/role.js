import request from '@/utils/request'

export function fetchList (query) {
  return request({
    url: '/role/list',
    method: 'get',
    params: query
  })
}

export function createRole (data) {
  return request({
    url: '/role/create',
    method: 'post',
    data
  })
}

export function updateRole (data) {
  return request({
    url: '/role/update',
    method: 'post',
    data
  })
}

export function batchDeleteRole (data) {
  return request({
    url: '/role/batch/delete',
    method: 'post',
    data
  })
}

export function updateRoleStatus (roleId, status) {
  return request({
    url: '/role/status/' + roleId + '/' + status,
    method: 'post'
  })
}

export function deleteRole (id) {
  return request({
    url: '/role/delete/' + id,
    method: 'post'
  })
}

export function queryRoleResource (roleId) {
  return request({
    url: '/role/resource/' + roleId,
    method: 'get'
  })
}

export function updateRoleResources (data) {
  return request({
    url: '/role/resource/update',
    method: 'post',
    data
  })
}

export function checkRoleName (data) {
  return request({
    url: '/role/name/check',
    method: 'post',
    params: data
  })
}

export function checkRoleKey (data) {
  return request({
    url: '/role/key/check',
    method: 'post',
    params: data
  })
}
