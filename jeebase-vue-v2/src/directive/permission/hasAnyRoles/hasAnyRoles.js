
import store from '@/store'

export default {
  inserted(el, binding, vnode) {
    const { value } = binding
    const roles = store.getters && store.getters.roles
    if (value && value instanceof Array && value.length > 0) {
      const myRoles = value

      const hasRoles = roles.some(role => {
        return myRoles.includes(role)
      })

      if (!hasRoles) {
        el.parentNode && el.parentNode.removeChild(el)
      }
    } else {
      throw new Error(`need roles! Like v-hasAnyRoles="['admin','editor']"`)
    }
  }
}
