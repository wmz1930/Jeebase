
import router from './router'
import store from './store'
import { Toast } from 'vant'
import { getToken, getTempId, setTempId } from '@/utils/auth' // getToken from cookie
import { getWeChatLoginUrl } from '@/api/login'

// permission judge function
function hasPermission(roles, permissionRoles) {
  if (roles.indexOf('admin') >= 0) return true // admin permission passed directly
  if (!permissionRoles) return true
  return roles.some(role => permissionRoles.indexOf(role) >= 0)
}

const whiteList = ['/login', '/loginMobile', '/register', '/authRedirect', '/index', '/member']// no redirect whitelist

const OAUTH2 = 'oauth2'

router.beforeEach((to, from, next) => {
  const token = getToken()
  const tempId = getTempId()
  if ((!token || token === 'undefined') && (!tempId || tempId === 'undefined') && to.name !== OAUTH2) {
    // api接口请求微信授权地址
    const params = {
      state: `${to.fullPath}`
    }
    getWeChatLoginUrl(params).then(response => {
      window.location.href = response.data
    })
  } else if (!token && to.name === OAUTH2) {
    // ?code=CODE&state=STATE
    const params = { ...to.query }
    // api接口登录
    store.dispatch('LoginByWeChat', params).then(res => {
      const data = res
      if (data.reg) {
        if (data.rember) {
          // 直接跳转到登录前的页面
          next({ path: params.state, replace: true, query: { noGoBack: true }})
        } else {
          // 跳转到登录页面
          // next({ path: `/login?redirect=${params.state}`, replace: true, query: { noGoBack: true }})
          next({ path: params.state, replace: true, query: { noGoBack: true }})
        }
      } else {
        setTempId(data.openId)
        // 跳转到注册页面
        next({ path: '/login', replace: true, query: { noGoBack: true }})
      }
    }).catch(() => {
    })
  } else if (token) { // determine if there has token
    /* has token*/
    if (to.path === '/login') {
      next({ path: '/memberCompany' })
    } else {
      if (store.getters.roles.length === 0) { // 判断当前用户是否已拉取完user_info信息
        store.dispatch('GetUserInfo').then(res => { // 拉取user_info
          const roles = res.data.roles // note: roles must be a array! such as: ['editor','develop']
          store.dispatch('GenerateRoutes', { roles }).then(() => { // 根据roles权限生成可访问的路由表
            router.addRoutes(store.getters.addRouters) // 动态添加可访问路由表
            next({ ...to, replace: true }) // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
          })
        }).catch((err) => {
          store.dispatch('FedLogOut').then(() => {
            Toast('登录超时，请重新登录！')
            console.log(err)
            next({ path: '/login' })
          })
        })
      } else {
        // 没有动态改变权限的需求可直接next() 删除下方权限判断
        if (hasPermission(store.getters.roles, to.meta.roles)) {
          next()
        } else {
          next({ path: '/401', replace: true, query: { noGoBack: true }})
        }
        // 可删
      }
    }
  } else {
    /* has no token*/
    if (whiteList.indexOf(to.path) !== -1 || (to.path && to.path.indexOf('detail') !== -1)) { // 在免登录白名单，直接进入
      if (to.path.indexOf('register') !== -1 && (!tempId || tempId === 'undefined')) {
        // api接口请求微信授权地址
        const params = {
          state: `${to.fullPath}`
        }
        getWeChatLoginUrl(params).then(response => {
          window.location.href = response.data
        })
      } else {
        next()
      }
    } else {
      next(`/login?redirect=${to.path}`) // 否则全部重定向到登录页
    }
  }
})

router.afterEach(() => {

})
