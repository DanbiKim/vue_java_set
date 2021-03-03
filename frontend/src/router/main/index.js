export default [
  {
    path: '/router/jjy/Mypage',
    name: 'JjyMypage',
    component: loadView('jjy/Mypage'),
    meta: { requiresAuth: true, layout: 'DefaultLayout' }
  },
  {
    path: '/router/jjy/Mypage2',
    name: 'JjyMypage2',
    component: loadView('jjy/Mypage2'),
    meta: { requiresAuth: true, layout: 'DefaultLayout' }
  },
  {
    path: '/router/jjy/TreeTest',
    name: 'JjyTree',
    component: loadView('jjy/TreeTest'),
    meta: { requiresAuth: true, layout: 'DefaultLayout' }
  },
  {
    path: '/router/jjy/TreeTest2',
    name: 'JjyTree2',
    component: loadView('jjy/TreeTest2'),
    meta: { requiresAuth: true, layout: 'DefaultLayout' }
  },
  {
    path: '/router/jjy/TreeTestLast',
    name: 'TreeTestLast',
    component: loadView('jjy/TreeTestLast'),
    meta: { requiresAuth: true, layout: 'DefaultLayout' }
  },
  {
    path: '/router/mng/menu',
    name: 'Menu',
    component: loadView('mng/Menu'),
    meta: { requiresAuth: true, layout: 'DefaultLayout' }
  }
]

function loadView (view) {
  return () => import(/* webpackChunkName: "view-[request]" */ `@/components/${view}`)
}
