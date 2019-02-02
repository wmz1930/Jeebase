import { asyncRouterMap, constantRouterMap } from '@/router'
import Layout from '@/views/layout/Layout'

/**
 * 通过meta.role判断是否与当前用户权限匹配
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
 * 递归过滤异步路由表，返回符合用户角色权限的路由表
 * @param routes asyncRouterMap
 * @param roles
 */
function filterAsyncRouter(routes, roles) {
  const res = []

  routes.forEach(route => {
    const tmp = { ...route }
    if (hasPermission(roles, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRouter(tmp.children, roles)
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
function assembleAsyncRouter(resources) {
  const accessedRouters = []
  resources.forEach(resource => {
    var route = {}
    if (resource.resourceUrl.indexOf('Layout') >= 0) { // 一级菜单
      route = {
        path: '/' + resource.resourcePath,
        component: Layout,
        redirect: resource.resourceUrl,
        name: resource.resourceKey,
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
        name: resource.resourceKey,
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
        name: resource.resourceKey,
        meta: {
          title: resource.resourceName,
          noCache: !resource.resourceCache,
          icon: resource.resourceIcon
        },
        hidden: !resource.resourceShow
      }
    }

    if (resource.children && resource.children.length) {
      route.children = assembleAsyncRouter(resource.children)
    }
    accessedRouters.push(route)
  })
  return accessedRouters
}

const permission = {
  state: {
    routers: constantRouterMap,
    addRouters: []
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.addRouters = routers
      state.routers = constantRouterMap.concat(routers)
    }
  },
  actions: {
    GenerateRoutes({ commit }, data) { // 前后端权限分离时使用此方法
      return new Promise(resolve => {
        const { roles } = data
        let accessedRouters
        if (roles.includes('admin')) {
          accessedRouters = asyncRouterMap
        } else {
          accessedRouters = filterAsyncRouter(asyncRouterMap, roles)
        }
        commit('SET_ROUTERS', accessedRouters)
        resolve()
      })
    },
    GenerateResourcesRoutes({ commit }, data) { // 此处修改，使用后台读取的菜单显示权限，上面的前后端单独配置的废弃不用
      return new Promise(resolve => {
        const { resources } = data
        let accessedRouters
        if (resources && resources.length > 0) {
          accessedRouters = asyncRouterMap.concat(assembleAsyncRouter(resources))
        } else {
          accessedRouters = filterAsyncRouter(asyncRouterMap, [])
        }
        accessedRouters.push({ path: '*', redirect: '/404', hidden: true })
        commit('SET_ROUTERS', accessedRouters)
        resolve()
      })
    }
  }
}

export default permission
