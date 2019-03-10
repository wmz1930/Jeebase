import Cookies from 'js-cookie'

const TokenKey = 'hbg-webchat-Token'
const TempId = 'hbg-webchat-TempId'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function getTempId() {
  return Cookies.get(TempId)
}

export function setTempId(id) {
  return Cookies.set(TempId, id)
}

export function removeTempId() {
  return Cookies.remove(TempId)
}
