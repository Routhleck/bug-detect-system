import { createRouter, createWebHistory } from 'vue-router'
import Layout from "@/layout/Layout";
import User from "@/views/User";
import Book from "@/views/Book";
import Person from "@/views/Person";
import Predict from "@/views/Predict";

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect:"/predict",
    children:[
        {
          path: 'user',
          name: 'User',
          component: User,
        },
        {
          path: 'book',
          name: 'Book',
          component: Book,
        },
        {
          path: '/predict',
          name: 'Predict',
          component: Predict,
        },
        {
          path: 'person',
          name: 'Person',
          component: Person,
        }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: ()=>import("@/views/Login")
  },
  {
    path: '/register',
    name: 'Register',
    component: ()=>import("@/views/Register")
  },

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
