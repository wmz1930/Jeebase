import request from '@/utils/request'

export function getWeChatLoginUrl(params) {
  return request({
    url: '/wechat/auth/url',
    method: 'post',
    params
  })
}

export function loginByWeChat(params) {
  return request({
    url: '/wechat/auth/login',
    method: 'post',
    params
  })
}

export function loginByUserAccount(userAccount, userPassword, openId, remember) {
  const data = {
    mobile: userAccount,
    password: userPassword,
    openId: openId,
    remember: remember
  }
  return request({
    url: '/wechat/auth/username/login',
    method: 'post',
    data
  })
}

export function logout() {
  return request({
    url: '/wechat/auth/logout',
    method: 'post'
  })
}

export function getUserInfo(token) {
  return request({
    url: '/auth/user/info',
    method: 'get',
    params: { token }
  })
}

