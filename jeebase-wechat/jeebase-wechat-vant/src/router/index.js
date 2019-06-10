import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Router Modules */

/** note: Submenu only appear when children.length>=1
 *  detail see  https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 **/

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    roles: ['admin','editor']     will control the page roles (you can set multiple roles)
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
    noCache: true                if true ,the page will no be cached(default is false)
  }
**/
export const constantRouterMap = [
  {
    path: '/redirect',
    hidden: true,
    children: [
      {
        path: '/redirect/:path*',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '',
    redirect: 'index'
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/login/index'),
    meta: {
      title: 'login'
    }
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('@/views/register/index'),
    meta: {
      title: 'register'
    }
  },
  {
    path: '/index',
    name: 'index',
    component: () => import('@/views/index/index'),
    meta: {
      title: 'index'
    }
  },
  {
    path: '/member',
    name: 'member',
    component: () => import('@/views/member/index'),
    meta: {
      title: 'member'
    }
  },
  {
    path: '/oauth2',
    name: 'oauth2',
    meta: {
      title: '',
      keepAlive: false
    },
    hidden: true,
    component: () => import('@/views/oauth2')
  },
  {
    path: '/404',
    component: () => import('@/views/errorPage/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/errorPage/401'),
    hidden: true
  }
]

export default new Router({
  mode: 'history', // require service support
  scrollBehavior(to, from, savedPosition) {
    // if (to.name === 'list' && from.name === 'topic') {
    //     return {x: 0, y: +sessionStorage.getItem('scrollTop') || 0}
    // } else {
    if (savedPosition) {
      return savedPosition
    } else {
      return { x: 0, y: 0 }
    }
    // }
  },
  routes: constantRouterMap
})

export const asyncRouterMap = [
  /** When your routing table is too long, you can split it into small modules**/
  {
    path: '',
    redirect: 'member'
  },
  { path: '*', redirect: '/404', hidden: true }
]
