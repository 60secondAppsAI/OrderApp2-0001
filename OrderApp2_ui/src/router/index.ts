import { createRouter, createWebHashHistory } from 'vue-router';
import { useAuth } from '../stores/auth';
import SignIn from '../components/SignIn.vue';
import Settings from '../components/Settings.vue';
import MyDashboard from '../components/MyDashboard.vue';

// Dynamic imports for module components
import Markets from '../components/Markets.vue';
import MarketDetail from '../components/MarketDetail.vue';
import Users from '../components/Users.vue';
import UserDetail from '../components/UserDetail.vue';
import Portfolios from '../components/Portfolios.vue';
import PortfolioDetail from '../components/PortfolioDetail.vue';
import Trades from '../components/Trades.vue';
import TradeDetail from '../components/TradeDetail.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    redirect: '/dashboard'
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: MyDashboard
  },
  
  // Dynamic module routes
  {
    path: '/markets',
    name: 'Markets',
    component: Markets
  },
  {
    path: '/market/:id',
    name: 'MarketDetail',
    component: MarketDetail,
    props: true
  },
  {
    path: '/users',
    name: 'Users',
    component: Users
  },
  {
    path: '/user/:id',
    name: 'UserDetail',
    component: UserDetail,
    props: true
  },
  {
    path: '/portfolios',
    name: 'Portfolios',
    component: Portfolios
  },
  {
    path: '/portfolio/:id',
    name: 'PortfolioDetail',
    component: PortfolioDetail,
    props: true
  },
  {
    path: '/trades',
    name: 'Trades',
    component: Trades
  },
  {
    path: '/trade/:id',
    name: 'TradeDetail',
    component: TradeDetail,
    props: true
  },

  // Core application routes
  {
    path: '/signin',
    name: 'SignIn',
    component: SignIn
  },
//  {
//    path: '/users',
//    name: 'Users',
//    component: Users,
//    meta: { requiresAuth: true }
//  },
  {
    path: '/settings',
    name: 'Settings',
    component: Settings,
    meta: { requiresAuth: true }
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/dashboard'
  }
];

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    return savedPosition || { top: 0 };
  }
});

// Navigation guard for authentication
router.beforeEach((to, from, next) => {
  const auth = useAuth();
  
  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    next({ name: 'SignIn', query: { redirect: to.fullPath } });
  } else if (to.name === 'SignIn' && auth.isAuthenticated) {
    next({ name: 'Dashboard' });
  } else {
    next();
  }
});

export default router;
