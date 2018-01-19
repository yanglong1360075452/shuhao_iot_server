import Vue from 'vue';
import Router from 'vue-router';
import Index from '../views/index/index.vue';
import Login from '../views/login/login.vue';
import Util from '../assets/lib/util';
import Users from '../views/app_user/users.vue';
import User from '../views/app_user/user.vue';
import Devices from '../views/device/devices.vue';
import Device from '../views/device/device.vue';
import data from '../views/deviceData/deviceData.vue';
import log from '../views/log/log.vue';
import Appver from '../views/app_ver/appver.vue';


Vue.use(Router);

let router = new Router({
  routes: [
    /**首页*/
    {
      path: '/',
      name: 'index',
      component: Index,
      children: [
        /**用户列表页*/
        {
          path: 'users',
          component: Users,
          name: 'users',
        },
        /**用户详情页*/
        {
          path: 'user/:id',
          component: User,
          name: 'user',
        },
        /**设备列表页*/
        {
          path: 'devices',
          component: Devices,
          name: 'devices',
        },
        /**设备详情页*/
        {
          path: 'device',
          component: Device,
          name: 'device',
        }
        ,
        /**设备数据列表页*/
        {
          path: 'data',
          component: data,
          name: 'data',
        },
        /**app版本升级页面*/
        {
          path: 'appver',
          component: Appver,
          name: 'appver',
        }
        ,
        {
          path: 'log',
          component: log,
          name: 'log',
        }
      ]
    },
    /**登录页*/
    {
      path: '/login',
      name: 'login',
      component: Login
    }

  ]
});

/**
 * 判断用户是否有登录，没有登录则跳转到登录页面
 * 如果用户已经登录，则回跳到登录的页的时候，要转到首页
 * */
router.beforeEach((to, from, next) => {
  var user = Util.dataToSessionStorageOperate.achieve('user');
  if (!user && to.path != '/login') {
    next('/login');
  } else {
    next();
  }
});
export default router
