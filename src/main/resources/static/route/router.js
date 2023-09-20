// 注册组件
const Home = httpVueLoader('view/Home.vue');
var Header = httpVueLoader('components/common/Header.vue');
Vue.component('my-header', Header);
var Sidebar = httpVueLoader('components/common/Sidebar.vue');
Vue.component('my-sidebar', Sidebar);
var Tags = httpVueLoader('components/common/Tags.vue');
Vue.component('my-tags', Tags);

const first = httpVueLoader('view/system/first.vue');
const uploaddepart = httpVueLoader('view/system/uploaddepart.vue');
const uploadincome = httpVueLoader('view/system/uploadincome.vue');
const jfcar = httpVueLoader('view/system/jfcar.vue');
const wrwcar = httpVueLoader('view/system/wrwcar.vue');
const jfandwrwcar = httpVueLoader('view/system/jfandwrwcar.vue');
const incomequery = httpVueLoader('view/system/incomequery.vue');
const jfdownload = httpVueLoader('view/system/jfdownload.vue');
const wrwdownload = httpVueLoader('view/system/wrwdownload.vue');
const fuleconsumption = httpVueLoader('view/system/fuleconsumption.vue');
const index = httpVueLoader('view/system/index.vue');
const system = httpVueLoader('view/system/system.vue');
const uploadAll = httpVueLoader('view/system/uploadall.vue');


//路由
const Foo = {
    template: '<div>测试foo界面</div>'
};
const Bar = {
    template: '<div>测试bar界面</div>'
};

const routes = [
    /*     {
            path: '/',
            redirect: '/foo'
        }, */
    {
        path: '/',
        component: Home,
        meta: {title: '系统首页'},
        children: [/*{
            path: '/foo',
            component: Foo,
            meta: {title: 'foo'}
        },
            {
                path: '/bar',
                component: Bar,
                meta: {title: 'bar'}
            },
*/
            {
                path: "/index",
                component: index,
                meta: {title: '数据统计'}
            },
            {
                path: "/system",
                component: system,
                meta: {title: '系统设置'}
            },
            {
                path: "/first",
                component: first,
                meta: {title: '沃尔沃'}
            },
            {
                path: "/uploaddepart",
                component: uploaddepart,
                meta: {title: '台账上传'}
            },
            {
                path: "/uploadincome",
                component: uploadincome,
                meta: {title: '损益表上传'}
            },
            {
                path: "/uploadAll",
                component: uploadAll,
                meta: {title: '上传'}
            },
            {
                path: "/fuleconsumption",
                component: fuleconsumption,
                meta: {title: '油耗上传'}
            },
            {
                path: "/jfcar",
                component: jfcar,
                meta: {title: '解放'}
            },
            {
                path: "/wrwcar",
                component: wrwcar,
                meta: {title: '沃尔沃'}
            },
            {
                path: "/jfandwrwcar",
                component: jfandwrwcar,
                meta: {title: '台账综合查询'}
            },
            {
                path: "/incomequery",
                component: incomequery,
                meta: {title: '损益表综合查询'}
            },
          /*  {
                path: "/jfdownload",
                component: jfdownload,
                meta: {title: '解放车数据导出'}
            },*/
            {
                path: "/wrwdownload",
                component: wrwdownload,
                meta: {title: '数据导出'}
            },
        ]
    },];
const router = new VueRouter({
    routes // (缩写) 相当于 routes: routes
})




