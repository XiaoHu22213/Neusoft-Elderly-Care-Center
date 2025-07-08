import {
	createRouter,
	createWebHistory
} from 'vue-router'
import VueCookie from 'vue-cookie'
const router = createRouter({
	history: createWebHistory(import.meta.env.BASE_URL),
	routes: [{
			path: '/index',
			name: 'Index',
			component: () => import('@/views'),
			children: [{
					path: '/main',
					name: 'Main',
					component: () => import('@/views/main')
				},
				{
					path: '/consult',
					name: 'Consult',
					component: () => import('@/views/consult')
				}, {
					path: '/admin',
					name: 'AdminList',
					component: () => import('@/views/sys/admin')
				},
				{
					path: '/dietarystatistics',
					name: 'dietarystatisticsList',
					component: () => import('@/views/meal/dietarystatistics')
				},
				{
					path: '/role',
					name: 'RoleList',
					component: () => import('@/views/sys/role')
				},
				{
					path: '/resource',
					name: 'ResourseList',
					component: () => import('@/views/sys/resource')
				},
				{
					path: '/user',
					name: 'User',
					component: () => import('@/views/sys/user')
				},
				{
					path: '/dietarycalendar',
					name: 'DietarycalendarList',
					component: () => import('@/views/meal/dietarycalendar')
				},
				{
					path: '/dietarymanagement',
					name: 'DietarymanagementList',
					component: () => import('@/views/meal/dietarymanagement')
				},
				{
					path: '/checkIn',
					name: 'checkInList',
					component: () => import('@/views/registration/checkIn')
				},
				{
					path: '/checkOut',
					name: 'checkOutList',
					component: () => import('@/views/registration/checkOut')
				},
				{
					path: '/goOut',
					name: 'goOutList',
					component: () => import('@/views/registration/goOut')
				},
				// {
				// 	path: '/custom',
				// 	name: 'customList',
				// 	component: () => import('@/views/nurse/custom')
				// },
				{
					path: '/records',
					name: 'recordsList',
					component: () => import('@/views/nurse/records')
				},
				{
					path: '/level',
					name: 'levelList',
					component: () => import('@/views/nurse/level')
				},
				{
					path: '/levelcontent',
					name: 'Levelcontent',
					component: () => import('@/views/nurse/level/levelcontent')
				},
				{
					path: '/content',
					name: 'contentList',
					component: () => import('@/views/nurse/content')
				},
				{
					path: '/bed',
					name: 'bedist',
					component: () => import('@/views/bedroom/bed')
				},
				{
					path: '/outin',
					name: 'outinist',
					component: () => import('@/views/bedroom/outin')
				},
				{
					path: '/service',
					name: 'service',
					component: () => import('@/views/health/service')
				},
				{
					path: '/focus',
					name: 'focusist',
					component: () => import('@/views/health/focus')
				},
				{
					path: '/lifereminder',
					name: 'lifereminder',
					component: () => import('@/views/health/lifereminder')
				},
				{
					path: '/feedback',
					name: 'feedback',
					component: () => import('@/views/health/feedback')
				},
				{
					path: '/busroute',
					name: 'busroute',
					component: () => import('@/views/lifemanagement/busroute')
				},
				{
					path: '/weather',
					name: 'weather',
					component: () => import('@/views/lifemanagement/weather')
				}

			]
		},
		{
			path: '/',
			name: 'Login',
			component: () => import('@/views/login/index.vue')
		},
		{
			path: '/register',
			name: 'Register',
			component: () => import('@/views/login/register')
		},
		{
			path: '/password',
			name: 'Password',
			component: () => import('@/views/login/password')
		},
		{
			path: '/login',
			name: 'login',
			component: () => import('@/views/login/login.vue')
		},
		{
			path: '/test',
			name: 'Test',
			component: () => import('@/views/login/test.vue')
		}
	]
})

router.beforeEach((to, from, next) => {
	const token = VueCookie.get('token')
	const publicPages = ['/', '/register', '/password']
	if (publicPages.includes(to.fullPath) || token) {
		next()
		return
	}
	next('/')
})
export default router