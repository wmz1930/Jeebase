import request from '@/utils/request'

export function fetchList (data) {
  return request({
    url: '/common/dict/tree',
    method: 'get',
    params: data
  })
}

export function createDict (data) {
  return request({
    url: '/common/dict/create',
    method: 'post',
    data
  })
}

export function updateDict (data) {
  return request({
    url: '/common/dict/update',
    method: 'post',
    data
  })
}

export function deleteDict (id) {
  return request({
    url: '/common/dict/delete/' + id,
    method: 'post'
  })
}

export function listDict (dictCode) {
  return request({
    url: '/common/dict/list/' + dictCode,
    method: 'post'
  })
}
