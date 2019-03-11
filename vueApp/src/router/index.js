import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/count'
        },
        {
            path: '/login',
            component: resolve => require(['../components/page/Login.vue'], resolve)
        },
        {
            path: '*',
            redirect: '/404'
        },
        {
            path: '/',
            component: resolve => require(['../components/common/Home.vue'], resolve),
            children:[
                {
                    path: '/case',
                    component: resolve => require(['../components/page/Case.vue'], resolve),
                    meta: { title: '委托案件', keepAlive: true }
                },
                {
                    path: '/order',
                    name: 'order',
                    component: resolve => require(['../components/page/Order.vue'], resolve),
                    meta: { title: '案件-派单'}
                },
                {
                    path: '/orders',
                    name: 'orders',
                    component: resolve => require(['../components/page/Orders.vue'], resolve),
                    meta: { title: '派单信息', keepAlive: true }
                },
                {
                    path: '/users',
                    name: 'users',
                    component: resolve => require(['../components/page/Users.vue'], resolve),
                    meta: { title: '用户管理', keepAlive: true }
                },

                {
                    path: '/count',
                    component: resolve => require(['../components/page/Count.vue'], resolve),
                    meta: { title: '系统首页', keepAlive: true }
                },
                {
                    path: '/dashboard',
                    component: resolve => require(['../components/page/Dashboard.vue'], resolve),
                    meta: { title: '系统首页', keepAlive: true }
                },
                {
                    path: '/files',
                    component: resolve => require(['../components/page/Files.vue'], resolve),
                    meta: { title: '文件管理', keepAlive: true }
                },
                {
                    path: '/products',
                    name: 'products',
                    component: resolve => require(['../components/page/Products.vue'], resolve),
                    meta: { title: '产品管理', keepAlive: false }
                },
                {
                    path: '/addProduct',
                    name: 'addProduct',
                    component: resolve => require(['../components/page/AddProduct.vue'], resolve),
                    meta: { title: '产品详情', keepAlive: false }
                },
                {
                    path: '/productType',
                    name: 'productType',
                    component: resolve => require(['../components/page/ProductType.vue'], resolve),
                    meta: { title: '产品类型', keepAlive: true}
                },
                {
                    path: '/news',
                    component: resolve => require(['../components/page/News.vue'], resolve),
                    meta: { title: '新闻管理', keepAlive: true }
                },
                {
                    path: '/projectCase',
                    component: resolve => require(['../components/page/ProjectCase.vue'], resolve),
                    meta: { title: '工程案例', keepAlive: true }
                },
                {
                    path: '/recruitment',
                    component: resolve => require(['../components/page/Recruitment.vue'], resolve),
                    meta: { title: '招聘信息管理', keepAlive: true }
                },
                {
                    path: '/services',
                    component: resolve => require(['../components/page/Services.vue'], resolve),
                    meta: { title: '网上订购列表', keepAlive: true }
                },
                {
                    path: '/company',
                    component: resolve => require(['../components/page/Company.vue'], resolve),
                    meta: { title: '公司信息', keepAlive: true }
                },
                {
                    path: '/404',
                    component: resolve => require(['../components/page/404.vue'], resolve),
                    meta: { title: '404' }
                },
                {
                    path: '/403',
                    component: resolve => require(['../components/page/403.vue'], resolve),
                    meta: { title: '403' }
                }
            ]
        }
    ]
})
