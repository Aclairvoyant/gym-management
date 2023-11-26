import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router'
import {useUserStore} from "@/stores";
import UserProfile from '@/components/user/UserProfile.vue';
import UserAvatar from '@/components/user/UserAvatar.vue';
import UserPassword from '@/components/user/UserPassword.vue';

const routes: Array<RouteRecordRaw> = [
    {
        path: '/login',
        name: 'login',
        component: () => import('../views/login/LoginPage.vue')
    },
    {
        path: '/adminDashboard',
        name: 'admin-dashboard',
        component: () => import('../views/layout/AdminLayoutContainer.vue'),

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
            },
            {
                path: '/log/list', component: () => import('../views/log/LogList.vue')
            }
        ]
    },
    {
        path: '/memberDashboard',
        name: 'member-dashboard',
        component: () => import('../views/layout/MemberLayoutContainer.vue'),

        children: [
            {
                path: '/member/course/list', component: () => import('../views/course/member/CourseList.vue')
            },
            {
                path: '/member/user/profile', component: () => import('../views/user/UserProfile.vue')
            },
            {
                path: '/member/user/avatar', component: () => import('../views/user/UserAvatar.vue')
            },
            {
                path: '/member/user/password', component: () => import('../views/user/UserPassword.vue')
            },
            {
                path: '/member/payment/manage', component: () => import('../views/payment/PaymentManage.vue')
            },

        ]
    },
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

// router.beforeEach((to, from) => {
//
//   const useStore = useUserStore()
//
//   //console.log(useStore.token)
//
//   if (!useStore.token && to.path !== '/login') {
//     return '/login'
//   }
//
//   return true
// })


router.beforeEach((to, from, next) => {
    const userStore = useUserStore();

    // 检查用户是否登录
    if (!userStore.token) {
        if (to.path !== '/login') {
            next('/login'); // 未登录，重定向到登录页
        } else {
            next(); // 允许访问登录页
        }
    } else {
        // 用户已登录，根据用户类型重定向到不同的页面
        if (userStore.userType === '1' && to.path === '/') {
            next('/adminDashboard'); // 管理员重定向到管理仪表板
        } else if (userStore.userType === '2' && to.path === '/') {
            next('/memberDashboard'); // 会员重定向到会员仪表板
        } else {
            next(); // 其他情况正常进行
        }
    }
});


export default router
