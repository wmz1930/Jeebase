import { asyncRoutes, constantRoutes } from '@/router'
import Layout from '@/layout'

/**
 * Use meta.role to determine if the current user has permission
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {
  if (route.meta && route.meta.roles) {
    return roles.some(role => route.meta.roles.includes(role))
  } else {
    return true
  }
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param roles
 */
export function filterAsyncRoutes(routes, roles) {
  const res = []

  routes.forEach(route => {
    const tmp = { ...route }
    if (hasPermission(roles, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, roles)
      }
      res.push(tmp)
    }
  })

  return res
}

/**
 * 递归组装路由表，返回符合用户角色权限的路由表（路由表后台配置时使用）
 * @param resources
 */
function assembleAsyncRoutes(resources) {
  const accessedRouters = []
  resources.forEach(resource => {
    var route = {}
    if (resource.resourceUrl.indexOf('Layout') >= 0) {
      route = {
        path: '/' + resource.resourcePath,
        component: Layout,
        redirect: resource.resourceUrl,
        name: resource.resourcePageName,
        meta: {
          title: resource.resourceName,
          icon: resource.resourceIcon
        }
      }
    } else if (resource.resourceUrl.indexOf('nested') >= 0 && resource.children && resource.children.length) { // 包含子菜单的二级以下菜单
      route = {
        path: resource.resourcePath,
        component: () => import(`@/views/${resource.resourceUrl}`),
        redirect: resource.children[0].resourceUrl,
        name: resource.resourcePageName,
        meta: {
          title: resource.resourceName,
          noCache: !resource.resourceCache,
          icon: resource.resourceIcon
        },
        hidden: !resource.resourceShow
      }
    } else { // 最后一层菜单
      route = {
        path: resource.resourcePath,
        component: () => import(`@/views/${resource.resourceUrl}`),
        name: resource.resourcePageName,
        meta: {
          title: resource.resourceName,
          noCache: !resource.resourceCache,
          icon: resource.resourceIcon
        },
        hidden: !resource.resourceShow
      }
    }

    if (resource.children && resource.children.length) {
      route.children = assembleAsyncRoutes(resource.children)
    }
    accessedRouters.push(route)
  })
  return accessedRouters
}

const state = {
  routes: constantRoutes,
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  generateRoutes({ commit }, roles) {
    return new Promise(resolve => {
      let accessedRoutes
      if (roles.includes('admin')) {
        accessedRoutes = asyncRoutes
      } else {
        accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
      }
      commit('SET_ROUTES', accessedRoutes)
      resolve(accessedRoutes)
    })
  },
  generateResourcesRoutes({ commit }, resources) {
    return new Promise(resolve => {
      let accessedRoutes
      if (resources && resources.length > 0) {
        accessedRoutes = asyncRoutes.concat(assembleAsyncRoutes(resources))
      } else {
        accessedRoutes = filterAsyncRoutes(asyncRoutes, [])
      }
      accessedRoutes.push({ path: '*', redirect: '/404', hidden: true })
      commit('SET_ROUTES', accessedRoutes)
      resolve(accessedRoutes)
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
