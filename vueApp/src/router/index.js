import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/login'
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
                    path: '/homePage',
                    name: 'homePage',
                    component: resolve => require(['../components/page/HomePage.vue'], resolve),
                    meta: { title: '系统首页', keepAlive: true }
                },
                {
                    path: '/cases',
                    name: 'cases',
                    component: resolve => require(['../components/page/Cases.vue'], resolve),
                    meta: { title: '案件管理',keepAlive: true}
                },
                {
                    path: '/templates',
                    name: 'templates',
                    component: resolve => require(['../components/page/Templates.vue'], resolve),
                    meta: { title: '模板批次',keepAlive: true}
                },
                {
                    path: '/dict',
                    name: 'dict',
                    component: resolve => require(['../components/page/Dict.vue'], resolve),
                    meta: { title: '字典名管理', keepAlive: true }
                },
                {
                    path: '/dictItem',
                    name: 'dictItem',
                    component: resolve => require(['../components/page/DictItem.vue'], resolve),
                    meta: { title: '字典项管理', keepAlive: true }
                },
                {
                    path: '/users',
                    name: 'users',
                    component: resolve => require(['../components/page/Users.vue'], resolve),
                    meta: { title: '用户管理', keepAlive: true }
                },
                {
                    path: '/messages',
                    name: 'messages',
                    component: resolve => require(['../components/page/Messages.vue'], resolve),
                    meta: { title: '消息通知', keepAlive: true }
                },

                ///**/








                {
                    path: '/order',
                    name: 'order',
                    component: resolve => require(['../components/page/Order.vue'], resolve),
                    meta: { title: '案件-派单',keepAlive: true}
                },
                {
                    path: '/orders',
                    name: 'orders',
                    component: resolve => require(['../components/page/Orders.vue'], resolve),
                    meta: { title: '派单信息', keepAlive: true }
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

                /*
                1.以下为模板测试路由,请勿删除
                2.开发过程中将具有典型代表的组件收录进去
                */
                {
                    path: '/icon',
                    component: resolve => require(['../components/template/Icon.vue'], resolve),
                    meta: { title: '自定义图标' }
                },
                {
                    path: '/table',
                    component: resolve => require(['../components/template/BaseTable.vue'], resolve),
                    meta: { title: '基础表格' }
                },
                {
                    path: '/tabs',
                    component: resolve => require(['../components/template/Tabs.vue'], resolve),
                    meta: { title: 'tab选项卡' }
                },
                {
                    path: '/form',
                    component: resolve => require(['../components/template/BaseForm.vue'], resolve),
                    meta: { title: '基本表单' }
                },
                {
                    // 富文本编辑器组件
                    path: '/editor',
                    component: resolve => require(['../components/template/VueEditor.vue'], resolve),
                    meta: { title: '富文本编辑器' }
                },
                {
                    // markdown组件
                    path: '/markdown',
                    component: resolve => require(['../components/template/Markdown.vue'], resolve),
                    meta: { title: 'markdown编辑器' }
                },
                {
                    // 图片上传组件
                    path: '/upload',
                    component: resolve => require(['../components/template/Upload.vue'], resolve),
                    meta: { title: '文件上传' }
                },
                {
                    // vue-schart组件
                    path: '/charts',
                    component: resolve => require(['../components/template/BaseCharts.vue'], resolve),
                    meta: { title: 'schart图表' }
                },
                {
                    // 拖拽列表组件
                    path: '/drag',
                    component: resolve => require(['../components/template/DragList.vue'], resolve),
                    meta: { title: '拖拽列表' }
                },
                {
                    // 权限页面
                    path: '/permission',
                    component: resolve => require(['../components/template/Permission.vue'], resolve),
                    meta: { title: '权限测试', permission: true }
                },
                {
                    path: '/404',
                    component: resolve => require(['../components/template/404.vue'], resolve),
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
