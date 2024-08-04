import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    component: () => import('../views/AboutView.vue')
  },
  {
    path:'/user',
    name:'ListView',
    component: () => import('../views/ListView.vue')
  },
  {
    path:'/fileup',
    name:'UpLoadView',
    component: () => import('../views/UpLoadView.vue')
  },
  {
    // DetailView 
    //path: '/user/detail/',
    //path: '/user/detail/:num/:name/:email', //params 방식
    path: '/user/detail/:num', //axios 방식
    name:'DetailView',
    component : () => import('../views/DetailView.vue')
  },
  {
    path:'/member',
    name:'MemListView',
    component: () => import('../views/MemListView.vue')
  },
 // member입력폼 라우터 추가하기 ----------------------------------- 
  {
      // form
      path: '/user/save',
      name: 'AddForm',
      component: () => import('../views/AddForm.vue')
  },
  {
    // form
    path: '/login',
    name: 'login',
    component: () => import('../views/Login.vue')
},
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
