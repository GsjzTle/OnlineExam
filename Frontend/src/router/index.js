import { createRouter, createWebHashHistory } from "vue-router";
import Home from "../views/Home.vue";

const routes = [
    {
        path: '/',
        redirect: '/dashboard'
    }, {
        path: "/",
        name: "Home",
        component: Home,
        children: [
            {
                path: "/dashboard",
                name: "dashboard",
                meta: {
                    title: '系统首页'
                },
                component: () => import( /* webpackChunkName: "dashboard" */ "../views/Dashboard.vue")
            }, {
                path: "/table",
                name: "basetable",
                meta: {
                    title: '表格'
                },
                component: () => import( /* webpackChunkName: "table" */ "../views/BaseTable.vue")
            }, {
                path: "/charts",
                name: "basecharts",
                meta: {
                    title: '图表'
                },
                component: () => import( /* webpackChunkName: "charts" */ "../views/BaseCharts.vue")
            }, {
                path: "/form",
                name: "baseform",
                meta: {
                    title: '表单'
                },
                component: () => import( /* webpackChunkName: "form" */ "../views/BaseForm.vue")
            }, {
                path: "/tabs",
                name: "tabs",
                meta: {
                    title: 'tab标签'
                },
                component: () => import( /* webpackChunkName: "tabs" */ "../views/Tabs.vue")
            }, {
                path: "/donate",
                name: "donate",
                meta: {
                    title: '鼓励作者'
                },
                component: () => import( /* webpackChunkName: "donate" */ "../views/Donate.vue")
            }, {
                path: "/permission",
                name: "permission",
                meta: {
                    title: '权限管理',
                    permission: true
                },
                component: () => import( /* webpackChunkName: "permission" */ "../views/Permission.vue")
            }, {
                path: "/i18n",
                name: "i18n",
                meta: {
                    title: '国际化语言'
                },
                component: () => import( /* webpackChunkName: "i18n" */ "../views/I18n.vue")
            }, {
                path: "/upload",
                name: "upload",
                meta: {
                    title: '上传插件'
                },
                component: () => import( /* webpackChunkName: "upload" */ "../views/Upload.vue")
            }, {
                path: "/icon",
                name: "icon",
                meta: {
                    title: '自定义图标'
                },
                component: () => import( /* webpackChunkName: "icon" */ "../views/Icon.vue")
            }, {
                path: '/404',
                name: '404',
                meta: {
                    title: '找不到页面'
                },
                component: () => import(/* webpackChunkName: "404" */ '../views/404.vue')
            }, {
                path: '/403',
                name: '403',
                meta: {
                    title: '没有权限'
                },
                component: () => import(/* webpackChunkName: "403" */ '../views/403.vue')
            }, {
                path: '/user',
                name: 'user',
                meta: {
                    title: '个人中心'
                },
                component: () => import(/* webpackChunkName: "user" */ '../views/User.vue')
            }, {
                path: '/editor',
                name: 'editor',
                meta: {
                    title: '富文本编辑器'
                },
                component: () => import(/* webpackChunkName: "editor" */ '../views/Editor.vue')
            }, {
                path: "/userlist",
                name: "userlist",
                meta: {
                    title: '用户列表'
                },
                component: () => import( /* webpackChunkName: "dashboard" */'../views/User/UserList.vue')
            }, {
                path: "/announcementlist",
                name: "announcementlist",
                meta: {
                    title: '公告管理'
                },
                component: () => import( /* webpackChunkName: "dashboard" */'../views/Announcement/AnnouncementList.vue')
            },
            {
                path: "/examonline",
                name: "examonline",
                meta: {
                    title: '在线考试'
                },
                component: () => import('../views/Exam/ExamOnline.vue')
            },
            {
                path: "/examjudge",
                name: "examjudge",
                meta: {
                    title: '教师阅卷'
                },
                component: () => import('../views/Exam/ExamJudge.vue')
            },
            {
                path: "/addexam",
                name: "addexam",
                meta: {
                    title: '添加考试'
                },
                component: () => import('../views/Exam/AddExam.vue')
            },
            {
                path: "/showexam",
                name: "showexam",
                meta: {
                    title: '查看考试'
                },
                component: () => import('../views/Exam/ShowExam.vue')
            },
            {
                path: "/myexam",
                name: "myexam",
                meta: {
                    title: '我的考试'
                },
                component: () => import('../views/Exam/MyExam.vue')
            },
            {
                path: "/test",
                name: "test",
                meta: {
                    title: '测试界面'
                },
                component: () => import('../views/Exam/Test.vue')
            },
            {
                path: "/SelectProblem",
                name: "SelectProblem",
                meta: {
                    title: '选择题管理'
                },
                component: () => import( '../views/Problem/SelectProblem.vue')
            },
            {
                path: "/SubjectiveProblem",
                name: "SubjectiveProblem",
                meta: {
                    title: '主观题管理'
                },
                component: () => import( '../views/Problem/SubjectiveProblem.vue')
            }
        ]
    },
    {
        path: "/login",
        name: "Login",
        meta: {
            title: '登录'
        },
        component: () => import( /* webpackChunkName: "login" */ "../views/Login.vue")
    },
    {
        path: "/register",
        name: "Register",
        meta: {
            title: '注册'
        },
        component: () => import( /* webpackChunkName: "login" */ "../views/Register.vue")
    }
];

const router = createRouter({
    history: createWebHashHistory(),
    routes
});

router.beforeEach((to, from, next) => {
    document.title = `${to.meta.title} | 在线考试系统`;
    const role = localStorage.getItem('_User');
    if (!role && to.path !== '/login' && to.path !== '/register') {
        next('/login');
    } else if (to.meta.permission) {
        // 如果是管理员权限则可进入，这里只是简单的模拟管理员权限而已
        role === 'admin'
            ? next()
            : next('/403');
    } else {
        next();
    }
});

export default router;