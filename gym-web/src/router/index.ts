import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import {useUserStore} from "@/stores";

const routes: Array<RouteRecordRaw> = [
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/login/LoginPage.vue')
  },
  {
    path: '/',
    name: 'home',
    component: () => import('../views/layout/LayoutContainer.vue'),

    children: [
      {
        path: '/member/list', component: () => import('../views/member/MemberList.vue')
      },
      {
        path: '/coach/list', component: () => import('../views/coach/CoachList.vue')
      },
      {
        path: '/course/list', component: () => import('../views/course/CourseList.vue')
      },
      {
        path: '/user/profile', component: () => import('../views/user/UserProfile.vue')
      },
      {
        path: '/order/list', component: () => import('../views/order/OrderList.vue')
      },
      {
        path: '/user/avatar', component: () => import('../views/user/UserAvatar.vue')
      },
      {
        path: '/user/password', component: () => import('../views/user/UserPassword.vue')
      }
  ]}
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from) => {

  const useStore = useUserStore()

  console.log(useStore.token)

  if (!useStore.token && to.path !== '/login') {
    return '/login'
  }

  return true
})

export default router
