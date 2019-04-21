
import store from '@/store'

export default {
  inserted(el, binding, vnode) {
    const { value } = binding
    const permissionList = store.getters && store.getters.permissions

    if (value && value instanceof Array && value.length > 0) {
      const permissions = value

      const hasPermission = permissionList.some(permission => {
        return permissions.includes(permission)
      })

      if (!hasPermission) {
        el.parentNode && el.parentNode.removeChild(el)
      }
    } else {
      throw new Error(`need permissions! Like v-hasPermission="['org:create','org:update']"`)
    }
  }
}
