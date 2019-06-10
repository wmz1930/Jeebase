import lacksRoles from './lacksRoles'

const install = function(Vue) {
  Vue.directive('lacksRoles', lacksRoles)
}

if (window.Vue) {
  window['lacksRoles'] = lacksRoles
  Vue.use(install); // eslint-disable-line
}

lacksRoles.install = install

export default lacksRoles
