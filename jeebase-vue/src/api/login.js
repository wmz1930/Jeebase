import request from '@/utils/request'

export function loginByUserAccount(userAccount, userPassword, vcode, verkey) {
  const data = {
    userAccount: userAccount,
    userPassword: userPassword,
    vcode: vcode,
    verkey: verkey
  }
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

export function logout() {
  return request({
    url: '/auth/logout',
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

