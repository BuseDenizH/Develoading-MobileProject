import { createRouter, createWebHistory } from '@ionic/vue-router';
import { RouteRecordRaw } from 'vue-router';
import TabsPage from '../views/TabsPage.vue';
import SignUpForm from '@/views/SignUpForm.vue';
import LoginPage from '@/views/LoginPage.vue';
import ForgotPassword from '@/views/ForgotPassword.vue';
import ResetPassword from '@/views/ResetPassword.vue';
import VerifyCode from '@/views/VerifyCode.vue';
import ProfilPopup from '@/views/ProfilPopup.vue';
import AyarlarPopup from '@/views/AyarlarPopup.vue';
import BizeUlasin from '@/views/BizeUlasin.vue';
import Gizlilik from '@/views/Gizlilik.vue';


const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'SignUp',
    component: SignUpForm,
    /*
    beforeEnter: (to, from, next) => {
      const isRegistered = localStorage.getItem('userRegistered');
      if (isRegistered) {
        next({ name: 'Login' });
      } else {
        next();
      }
    }*/
  },
  {
    path: '/login',
    name: 'Login',
    component: LoginPage
  },
  {
    path: '/profilpopup',
    name: 'ProfilPopup',
    component: ProfilPopup
  },
  {
    path: '/gizlilik',
    name: 'Gizlilik',
    component: Gizlilik
  },
  {
    path: '/bizeulasin',
    name: 'BizeUlasin',
    component: BizeUlasin
  },
  {
    path: '/ayarlarpopup',
    name: 'AyarlarPopup',
    component: AyarlarPopup
  },
  {
    path: '/forgot-password',
    name: 'ForgotPassword',
    component: ForgotPassword
  },
  {
    path: '/reset-password',
    name: 'ResetPassword',
    component: ResetPassword
  },
  {
    path: '/verify-code',
    name: 'VerifyCode',
    component: VerifyCode
  },
  {
    path: '/tabs/',
    component: TabsPage,
    children: [
      {
        path: '',
        redirect: '/tabs/tab1'
      },
      {
        path: 'tab1',
        component: () => import('@/views/Home.vue')
      },
      {
        path: 'tab2',
        component: () => import('@/views/Companies.vue')
      },
      {
        path: 'tab3',
        component: () => import('@/views/Cards.vue')
      },
      {
        path: 'tab4',
        component: () => import('@/views/Profile.vue')
      },
      {
        path: 'tab5/:type/:id',
        name: 'DetailsPopPage',
        component: () => import('@/views/DetailsPopPage.vue'),
        props: true
      },
      {
        path: 'tab6/:category/:companyName',
        name: 'FirmsPopPage',
        component: () => import('@/views/FirmsPopPage.vue'),
        props: true
      },
      {
        path: 'tab7',
        name: 'FavorilerPopPage',
        component: () => import('@/views/FavorilerPopPage.vue')
      },
      {
        path: 'tab8',
        name: 'UsedCampaignPopPage',
        component: () => import('@/views/UsedCampaignPopPage.vue')
      }

    ]
  }
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
});

export default router;
