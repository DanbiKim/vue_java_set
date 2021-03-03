// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import { BootstrapVue, BootstrapVueIcons } from 'bootstrap-vue'
import 'bootstrap-vue/dist/bootstrap-vue-icons.min.css'
import axios from 'axios'

import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import './assets/vendor/fontawesome-free/css/all.min.css'
import './assets/css/sb-admin-2.min.css'
import './assets/css/plum3.css'

// font 관련 : https://github.com/FortAwesome/vue-fontawesome
import { library } from '@fortawesome/fontawesome-svg-core'
import { faUserSecret } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon, FontAwesomeLayers, FontAwesomeLayersText } from '@fortawesome/vue-fontawesome'
import { faFontAwesome } from '@fortawesome/free-brands-svg-icons'

library.add(faUserSecret, faFontAwesome)

Vue.component('font-awesome-icon', FontAwesomeIcon)
Vue.component('font-awesome-layers', FontAwesomeLayers)
Vue.component('font-awesome-layers-text', FontAwesomeLayersText)

// axios 글로벌 설정
if (process.env.NODE_ENV === 'production') {
  axios.defaults.baseURL = 'http://localhost:8081'
} else {
  axios.defaults.baseURL = 'http://localhost:8081'
}
axios.defaults.headers.common['Authoriztion'] = 'any'
axios.defaults.headers.get['Accepts'] = 'application/json'

Vue.use(BootstrapVue)
Vue.use(BootstrapVueIcons)

Vue.prototype.$axios = axios
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
  methods: {
    getDynamicRoutes (url) {
      axios
        .get(url)
        .then(this.processData)
        .catch(err => console.log(err))
    },

    processData (data) {
      const datalist = data.data.data
      // console.log(datalist)
      // webpackChunkName 은 webpack 에 Magic Comments 라는 기능이다. 찾아보자
      datalist.forEach(function (element) {
        let newRoute = {
          path: element.url,
          component: () => import(/* webpackChunkName: "view-[request]" */ '@/components' + element.url),
          name: element.page_id,
          meta: {
            requiresAuth: true,
            layout: 'DefaultLayout'
          }
        }
        router.addRoutes([newRoute])
      })
      // datalist.forEach(element => this.createAndAppendRoute)
      // datalist.forEach(this.createAndAppendRoute)
      // console.log(router)
    }
  },

  created () {
    this.getDynamicRoutes('/api/menu/list')
  }
})
