import lacksPermission from './lacksPermission'

const install = function(Vue) {
  Vue.directive('lacksPermission', lacksPermission)
}

if (window.Vue) {
  window['lacksPermission'] = lacksPermission
  Vue.use(install); // eslint-disable-line
}

lacksPermission.install = install
export default lacksPermission
