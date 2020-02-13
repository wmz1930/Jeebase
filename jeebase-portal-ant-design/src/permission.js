import Vue from 'vue'
import router from './router'
import store from './store'

import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import notification from 'ant-design-vue/es/notification'
import { setDocumentTitle, domTitle } from '@/utils/domUtil'
import { ACCESS_TOKEN } from '@/store/mutation-types'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['login', 'register', 'registerResult'] // no redirect whitelist

router.beforeEach(async (to, from, next) => {
  NProgress.start() // start progress bar
  to.meta && (typeof to.meta.title !== 'undefined' && setDocumentTitle(`${to.meta.title} - ${domTitle}`))
  if (Vue.ls.get(ACCESS_TOKEN)) {
    /* has token */
    if (to.path === '/user/login') {
      next({ path: '/dashboard/workplace' })
      NProgress.done()
    } else {
      if (store.getters.roles.length === 0) {
        // store
        //   .dispatch('GetInfo')
        //   .then(res => {
        //     const roles = res.result && res.result.roles
        //     store.dispatch('generateResourcesRouters', { roles }).then(() => {
        //       // 根据roles权限生成可访问的路由表
        //       // 动态添加可访问路由表
        //       router.addRoutes(store.getters.addRouters)
        //       const redirect = decodeURIComponent(from.query.redirect || to.path)
        //       if (to.path === redirect) {
        //         // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
        //         next({ ...to, replace: true })
        //       } else {
        //         // 跳转到目的路由
        //         next({ path: redirect })
        //       }
        //     })
        //   })
        //   .catch(() => {
        //     notification.error({
        //       message: '错误',
        //       description: '请求用户信息失败，请重试'
        //     })
        //     store.dispatch('Logout').then(() => {
        //       next({ path: '/user/login', query: { redirect: to.fullPath } })
        //     })
        //   })
        try {
          // get user info
          // note: roles must be a object array! such as: ['admin'] or ,['developer','editor']
          const data = await store.dispatch('GetInfo')
          const roles = data.roles
          const resources = data.resources

          const accessRouters = await store.dispatch('GenerateRouters', roles)
          router.addRoutes(accessRouters)

          // generate accessible routes map based on roles
          const accessResourcesRouters = await store.dispatch('GenerateResourcesRouters', resources)

          // dynamically add accessible routes
          router.addRoutes(accessResourcesRouters)

          // hack method to ensure that addRoutes is complete
          // set the replace: true, so the navigation will not leave a history record
          const redirect = decodeURIComponent(from.query.redirect || to.path)
          if (to.path === redirect) {
            // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
            next({ ...to, replace: true })
          } else {
            // 跳转到目的路由
            next({ path: redirect })
          }
        } catch (error) {
          // remove token and go to login page to re-login
          console.log(error)
          await store.dispatch('Logout')
          notification.error({
            message: '错误',
            description: '请求用户信息失败，请重试'
          })
          next({ path: '/user/login', query: { redirect: to.fullPath } })
          NProgress.done()
        }
      } else {
        next()
      }
    }
  } else {
    if (whiteList.includes(to.name)) {
      // 在免登录白名单，直接进入
      next()
    } else {
      next({ path: '/user/login', query: { redirect: to.fullPath } })
      NProgress.done() // if current page is login will not trigger afterEach hook, so manually handle it
    }
  }
})

router.afterEach(() => {
  NProgress.done() // finish progress bar
})
