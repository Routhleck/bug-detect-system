import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'

const routes = [
  {
    // 当访问斜杠的时候
    path: '/',
    // 主体的框架界面
    name: 'Layout',
    /// 这里的意思是主体的框架用的是Layout，然后下面有一个路由转向,最后通过一个children的径向路由使下面的路由都在这个布局里面
    component: Layout,
    // 路由的重定向跳转
    redirect: "/user",
    children: [
      {
        path: 'user',
        name: 'User',
        component: () => import("@/views/User"),

      },
      {
        path: '/person',
        name: 'Person',
        component: () => import("@/views/Person")
      },
      {
        path: '/predict',
        name: 'Predict',
        component: () => import("@/views/Login")
      },
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import("@/views/Predict")
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import("@/views/Register")
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
