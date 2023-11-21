import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'

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
    redirect: '/member/list',
    children: [
      {
        path: '/member/list', component: () => import('../views/member/MemberList.vue')
      },
      {
        path: '/member/add', component: () => import('../views/member/MemberAdd.vue')
      },
      {
        path: '/coach/list', component: () => import('../views/coach/CoachList.vue')
      },
      {
        path: '/coach/add', component: () => import('../views/coach/CoachAdd.vue')
      },
      {
        path: '/course/list', component: () => import('../views/course/CourseList.vue')
      },
      {
        path: '/course/add', component: () => import('../views/course/CourseAdd.vue')
      },
      {
        path: '/user/profile', component: () => import('../views/user/UserProfile.vue')
      }
  ]}
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
