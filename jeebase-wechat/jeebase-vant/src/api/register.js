import request from '@/utils/request'

export function sendRegisterSms(data) {
  return request({
    url: '/wechat/register/sms/send',
    method: 'post',
    data
  })
}

export function registerNormal(data) {
  return request({
    url: '/wechat/register/normal',
    method: 'post',
    data
  })
}
