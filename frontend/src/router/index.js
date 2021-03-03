import Vue from 'vue'
import VueRouter from 'vue-router'
import CommonIndex from './common/index'
import MainIndex from './main/index'
import axios from 'axios'

Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'history',
  routes: [
    ...CommonIndex,
    ...MainIndex
  ]
})

router.beforeEach((to, from, next) => {
  console.log('process.env.NODE_ENV=' + process.env.NODE_ENV)
  if (to.matched.some(record => record.meta.requiresAuth)) {
    console.log('인증필요O')
    // 이 라우트는 인증이 필요하며 로그인 한 경우 확인하십시오.
    // 그렇지 않은 경우 로그인 페이지로 리디렉션하십시오.
    if (process.env.NODE_ENV !== 'development') {
      // 개발에서는 인증 패스 하기 위해, 개발이 아닐때만
      axios.get('/user/loginChk')
        .then((res) => {
          var resResult = JSON.parse(res.request.response)
          if (resResult.success) {
            next()
          } else {
            location.href = axios.defaults.baseURL + '/user/login?redirect=' + to.fullPath
          }
        })
        .catch((error) => {
          console.log(error)
        })
    } else {
      // 개발일 경우
      next()
    }
  } else {
    console.log('인증필요X')
    next() // 반드시 next()를 호출하십시오!
  }
})

export default router
