import request from '@/utils/request'

export function listDict(dictCode) {
  return request({
    url: '/common/dict/list/' + dictCode,
    method: 'post'
  })
}

export function getQiNiuToken() {
  return request({
    url: '/qiniu/token',
    method: 'get'
  })
}
