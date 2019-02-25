import hasAnyRoles from './hasAnyRoles'

const install = function(Vue) {
  Vue.directive('hasAnyRoles', hasAnyRoles)
}

if (window.Vue) {
  window['hasAnyRoles'] = hasAnyRoles
  Vue.use(install); // eslint-disable-line
}
hasAnyRoles.install = install
export default hasAnyRoles
