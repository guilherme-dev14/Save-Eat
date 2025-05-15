import Vue from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'
import LoginView from '@/views/LoginView.vue'
import CadastroConsumidorView from '@/views/CadastroConsumidorView.vue'
import CadastroEmpresaView from '@/views/CadastroEmpresaView.vue'
import { isAuthenticated } from '@/services/authUtils'
import HomeEmpresa from '@/views/Empresa/HomeEmpresa.vue'
import HomeCliente from '@/views/Cliente/HomeCliente.vue'
Vue.use(VueRouter)

const routes: Array<RouteConfig> = [
  {
    path: '/',
    component: LoginView,
    meta: { hideHeader: true }
  },
  {
    path: '/cadastro-cliente',
    component: CadastroConsumidorView,
    meta: { hideHeader: true }
  },
  {
    path: '/cadastro-empresa',
    component: CadastroEmpresaView,
    meta: { hideHeader: true }
  },
  {
    path: '/empresa',
    name: 'EmpresaHome',
    component: HomeEmpresa,
    meta: { requiresAuth: true }
  },
  {
    path: '/cliente',
    name: 'ClienteHome',
    component: HomeCliente,
    meta: { requiresAuth: true }
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  const requiresAuth = to.meta!.requiresAuth;

  if (requiresAuth && !isAuthenticated()) {
    next('/');
  } else {
    next();
  }
});

export default router
