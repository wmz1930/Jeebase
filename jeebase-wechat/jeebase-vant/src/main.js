import Vue from 'vue'

import '@/styles/index.scss' // global css
import 'font-awesome/css/font-awesome.css'

import App from './App'
import router from './router'
import store from './store'

import i18n from './lang' // Internationalization
import './icons' // icon
import './errorLog' // error log
import './permission' // permission control
import './mock' // simulation data

import * as filters from './filters' // global filters

import Vant from 'vant'
import 'vant/lib/index.css'

import VeeValidate, { Validator } from 'vee-validate' // 引入校验框架
import zh_CN from '@/utils/vee-validate-zh_CN'
Validator.localize('zh_CN', zh_CN)
Vue.use(VeeValidate, {
  locale: 'zh_CN'
})

Vue.use(Vant)

// register global utility filters.
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  i18n,
  render: h => h(App)
})
