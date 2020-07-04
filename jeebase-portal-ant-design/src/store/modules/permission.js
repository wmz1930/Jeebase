import { asyncRouterMap, constantRouterMap } from '@/config/router.config'
import * as viewComponent from '@/layouts'
import * as icons from '@/core/icons'
/**
 * 过滤账户是否拥有某一个权限，并将菜单从加载列表移除
 *
 * @param permission
 * @param route
 * @returns {boolean}
 */
function hasPermission (roles, route) {
  if (route.meta && route.meta.roles) {
    return roles.some(role => route.meta.roles.includes(role))
  } else {
    return true
  }
}

/**
 * 单账户多角色时，使用该方法可过滤角色不存在的菜单
 *
 * @param roles
 * @param route
 * @returns {*}
 */
// eslint-disable-next-line
function hasRole(roles, route) {
  if (route.meta && route.meta.roles) {
    return route.meta.roles.includes(roles.id)
  } else {
    return true
  }
}

function filterAsyncRouter (routerMap, roles) {
  const accessedRouters = routerMap.filter(route => {
    if (hasPermission(roles, route)) {
      if (route.children && route.children.length) {
        route.children = filterAsyncRouter(route.children, roles)
      }
      return true
    }
    return false
  })
  return accessedRouters
}

/**
 * 递归组装路由表，返回符合用户角色权限的路由表（路由表后台配置时使用）
 * add by jeebase
 * @param resources
 */
function assembleAsyncRoutes (resources) {
  const accessedRouters = []
  resources.forEach(resource => {
    var route = {}
    var resourceIcon = icons[resource.resourceIcon] ? icons[resource.resourceIcon] : resource.resourceIcon
    // 一级菜单配置为RouteView，二级菜单配置为PageView。PageView显示标签头。
    var resourceComponent = viewComponent[resource.resourcePageName] ? viewComponent[resource.resourcePageName] : viewComponent['RouteView']
    if (resource.resourceUrl.indexOf('Layout') >= 0) {
      route = {
        path: '/' + resource.resourcePath,
        component: resourceComponent,
        redirect: '/' + resource.resourceUrl,
        name: resource.resourcePageName,
        meta: {
          title: resource.resourceName,
          icon: resourceIcon
        }
      }
    } else if (resource.resourceUrl.indexOf('nested') >= 0 && resource.children && resource.children.length) { // 包含子菜单的二级以下菜单
      route = {
        path: '/' + resource.resourcePath,
        component: resourceComponent,
        redirect: '/' + resource.children[0].resourceUrl,
        name: resource.resourcePageName,
        meta: {
          title: resource.resourceName,
          keepAlive: resource.resourceCache,
          icon: resourceIcon
        },
        hidden: !resource.resourceShow
      }
    } else { // 最后一层菜单
      if (resource.resourcePath.indexOf('https://') === -1 && resource.resourcePath.indexOf('http://') === -1) {
        route = {
          path: '/' + resource.resourcePath,
          component: () => import(`@/views/${resource.resourceUrl}`),
          name: resource.resourcePageName,
          meta: {
            title: resource.resourceName,
            keepAlive: resource.resourceCache,
            icon: resourceIcon
          },
          hidden: !resource.resourceShow
        }
      } else {
        route = {
          path: resource.resourcePath,
          component: () => import(`@/views/${resource.resourceUrl}`),
          name: resource.resourcePageName,
          meta: {
            title: resource.resourceName,
            keepAlive: resource.resourceCache,
            target: '_blank',
            icon: resourceIcon
          },
          hidden: !resource.resourceShow
        }
      }
    }

    if (resource.children && resource.children.length) {
      route.children = assembleAsyncRoutes(resource.children)
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
    GenerateRouters ({ commit }, roles) {
      return new Promise(resolve => {
        const accessedRouters = filterAsyncRouter(asyncRouterMap, roles)
        commit('SET_ROUTERS', accessedRouters)
        resolve(accessedRouters)
      })
    },
    GenerateResourcesRouters ({ commit }, resources) { // add by jeebase
      return new Promise(resolve => {
        let accessedRouters
        if (resources && resources.length > 0) {
          const basicMenus = asyncRouterMap.find(item => item.path === '/').children
          asyncRouterMap.find(item => item.path === '/').children = basicMenus.concat(assembleAsyncRoutes(resources))
          accessedRouters = asyncRouterMap
        } else {
          accessedRouters = filterAsyncRouter(asyncRouterMap, [])
        }
        accessedRouters.push({ path: '*', redirect: '/404', hidden: true })
        commit('SET_ROUTERS', accessedRouters)
        console.log(accessedRouters)
        resolve(accessedRouters)
      })
    }
  }
}

export default permission
