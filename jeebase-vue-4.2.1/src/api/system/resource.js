import request from '@/utils/request'

export function fetchResourceList(data) {
  return request({
    url: '/resource/tree',
    method: 'get',
    params: data
  })
}

export function createResource(data) {
  return request({
    url: '/resource/create',
    method: 'post',
    data
  })
}

export function updateResource(data) {
  return request({
    url: '/resource/update',
    method: 'post',
    data
  })
}

export function deleteResource(id) {
  return request({
    url: '/resource/delete/' + id,
    method: 'post'
  })
}

export function checkResourceKey(data) {
  return request({
    url: '/resource/key/check',
    method: 'post',
    params: data
  })
}
