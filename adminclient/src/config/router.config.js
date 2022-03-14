// eslint-disable-next-line
import {
  UserLayout,
  BasicLayout,
  RouteView,
  BlankLayout,
  PageView
} from '@/layouts'

export const asyncRouterMap = [{
  path: '/',
  name: 'index',
  component: BasicLayout,
  meta: {
    title: '首页'
  },
  redirect: '/dashboard/workplace',
  children: [
    // forms
    {
      path: '/form',
      redirect: '/form/base-form',
      component: PageView,
      meta: {
        title: '表单页',
        icon: 'form',
        permission: ['form']
      },
      children: [{
        path: '/form/base-form',
        name: 'BaseForm',
        component: () => import('@/views/form/BasicForm'),
        meta: {
          title: '基础表单',
          keepAlive: true,
          permission: ['form']
        }
      },
      {
        path: '/form/step-form',
        name: 'StepForm',
        component: () => import('@/views/form/stepForm/StepForm'),
        meta: {
          title: '分步表单',
          keepAlive: true,
          permission: ['form']
        }
      },
      {
        path: '/form/advanced-form',
        name: 'AdvanceForm',
        component: () => import('@/views/form/advancedForm/AdvancedForm'),
        meta: {
          title: '高级表单',
          keepAlive: true,
          permission: ['form']
        }
      }
      ]
    },
    // list
    {
      path: '/list',
      name: 'list',
      component: PageView,
      redirect: '/list/table-list',
      meta: {
        title: '列表页',
        icon: 'table',
        permission: ['table']
      },
      children: [{
        path: '/list/table-list/:pageNo([1-9]\\d*)?',
        name: 'TableListWrapper',
        hideChildrenInMenu: true, // 强制显示 MenuItem 而不是 SubMenu
        component: () => import('@/views/list/TableList'),
        meta: {
          title: '查询表格',
          keepAlive: true,
          permission: ['table']
        }
      },
      {
        path: '/list/basic-list',
        name: 'BasicList',
        component: () => import('@/views/list/StandardList'),
        meta: {
          title: '标准列表',
          keepAlive: true,
          permission: ['table']
        }
      },
      {
        path: '/list/card',
        name: 'CardList',
        component: () => import('@/views/list/CardList'),
        meta: {
          title: '卡片列表',
          keepAlive: true,
          permission: ['table']
        }
      },
      {
        path: '/list/search',
        name: 'SearchList',
        component: () => import('@/views/list/search/SearchLayout'),
        redirect: '/list/search/article',
        meta: {
          title: '搜索列表',
          keepAlive: true,
          permission: ['table']
        },
        children: [{
          path: '/list/search/article',
          name: 'SearchArticles',
          component: () => import('../views/list/search/Article'),
          meta: {
            title: '搜索列表（文章）',
            permission: ['table']
          }
        },
        {
          path: '/list/search/project',
          name: 'SearchProjects',
          component: () => import('../views/list/search/Projects'),
          meta: {
            title: '搜索列表（项目）',
            permission: ['table']
          }
        },
        {
          path: '/list/search/application',
          name: 'SearchApplications',
          component: () => import('../views/list/search/Applications'),
          meta: {
            title: '搜索列表（应用）',
            permission: ['table']
          }
        }
        ]
      }
      ]
    },

    // profile
    {
      path: '/profile',
      name: 'profile',
      component: RouteView,
      redirect: '/profile/basic',
      meta: {
        title: '详情页',
        icon: 'profile',
        permission: ['profile']
      },
      children: [{
        path: '/profile/basic',
        name: 'ProfileBasic',
        component: () => import('@/views/profile/basic/Index'),
        meta: {
          title: '基础详情页',
          permission: ['profile']
        }
      },
      {
        path: '/profile/advanced',
        name: 'ProfileAdvanced',
        component: () => import('@/views/profile/advanced/Advanced'),
        meta: {
          title: '高级详情页',
          permission: ['profile']
        }
      }
      ]
    },

    // result
    {
      path: '/result',
      name: 'result',
      component: PageView,
      redirect: '/result/success',
      meta: {
        title: '结果页',
        icon: 'check-circle-o',
        permission: ['result']
      },
      children: [{
        path: '/result/success',
        name: 'ResultSuccess',
        component: () => import( /* webpackChunkName: "result" */ '@/views/result/Success'),
        meta: {
          title: '成功',
          keepAlive: false,
          hiddenHeaderContent: true,
          permission: ['result']
        }
      },
      {
        path: '/result/fail',
        name: 'ResultFail',
        component: () => import( /* webpackChunkName: "result" */ '@/views/result/Error'),
        meta: {
          title: '失败',
          keepAlive: false,
          hiddenHeaderContent: true,
          permission: ['result']
        }
      }
      ]
    },

    // Exception
    {
      path: '/exception',
      name: 'exception',
      component: RouteView,
      redirect: '/exception/403',
      meta: {
        title: '异常页',
        icon: 'warning',
        permission: ['exception']
      },
      children: [{
        path: '/exception/403',
        name: 'Exception403',
        component: () => import( /* webpackChunkName: "fail" */ '@/views/exception/403'),
        meta: {
          title: '403',
          permission: ['exception']
        }
      },
      {
        path: '/exception/404',
        name: 'Exception404',
        component: () => import( /* webpackChunkName: "fail" */ '@/views/exception/404'),
        meta: {
          title: '404',
          permission: ['exception']
        }
      },
      {
        path: '/exception/500',
        name: 'Exception500',
        component: () => import( /* webpackChunkName: "fail" */ '@/views/exception/500'),
        meta: {
          title: '500',
          permission: ['exception']
        }
      }
      ]
    },

    // account
    {
      path: '/account',
      component: RouteView,
      redirect: '/account/center',
      name: 'account',
      meta: {
        title: '个人页',
        icon: 'user',
        keepAlive: true,
        permission: ['user']
      },
      children: [{
        path: '/account/center',
        name: 'center',
        component: () => import('@/views/account/center/Index'),
        meta: {
          title: '个人中心',
          keepAlive: true,
          permission: ['user']
        }
      },
      {
        path: '/account/settings',
        name: 'settings',
        component: () => import('@/views/account/settings/Index'),
        meta: {
          title: '个人设置',
          hideHeader: true,
          permission: ['user']
        },
        redirect: '/account/settings/base',
        hideChildrenInMenu: true,
        children: [{
          path: '/account/settings/base',
          name: 'BaseSettings',
          component: () => import('@/views/account/settings/BaseSetting'),
          meta: {
            title: '基本设置',
            hidden: true,
            permission: ['user']
          }
        },
        {
          path: '/account/settings/security',
          name: 'SecuritySettings',
          component: () => import('@/views/account/settings/Security'),
          meta: {
            title: '安全设置',
            hidden: true,
            keepAlive: true,
            permission: ['user']
          }
        },
        {
          path: '/account/settings/custom',
          name: 'CustomSettings',
          component: () => import('@/views/account/settings/Custom'),
          meta: {
            title: '个性化设置',
            hidden: true,
            keepAlive: true,
            permission: ['user']
          }
        },
        {
          path: '/account/settings/binding',
          name: 'BindingSettings',
          component: () => import('@/views/account/settings/Binding'),
          meta: {
            title: '账户绑定',
            hidden: true,
            keepAlive: true,
            permission: ['user']
          }
        },
        {
          path: '/account/settings/notification',
          name: 'NotificationSettings',
          component: () => import('@/views/account/settings/Notification'),
          meta: {
            title: '新消息通知',
            hidden: true,
            keepAlive: true,
            permission: ['user']
          }
        }
        ]
      }
      ]
    }
  ]
},
{
  path: '*',
  redirect: '/404',
  hidden: true
}
]

/**
 * 基础路由
 * @type { *[] }
 */
export const constantRouterMap = [{
  path: '/user',
  component: UserLayout,
  redirect: '/user/login',
  hidden: true,
  children: [{
    path: 'login',
    name: 'login',
    component: () => import( /* webpackChunkName: "user" */ '@/views/user/Login')
  },
  {
    path: 'register',
    name: 'register',
    component: () => import( /* webpackChunkName: "user" */ '@/views/user/Register')
  },
  {
    path: 'register-result',
    name: 'registerResult',
    component: () => import( /* webpackChunkName: "user" */ '@/views/user/RegisterResult')
  }
  ]
},

{
  path: '/404',
  component: () => import( /* webpackChunkName: "fail" */ '@/views/exception/404')
}
]
const menuRouteConfig = new Map([
  [100,
    {
      path: '/',
      name: 'dashboard',
      component: PageView,
      meta: {
        title: '仪表板',
        icon: 'appstore-o'
      },
      redirect: '/dashboard/workplace',
      // hideChildrenInMenu: true
    }
  ],
  [101,
    {
      path: '/dashboard/workplace',
      name: 'workplace',
      component: () => import('@/views/dashboard/Workplace'),
      meta: {
        title: '工作台',
        icon: 'appstore-o'
      },
      // hideChildrenInMenu: true
    }
  ],
  // 系统管理从200-299
  [200,
    {
      path: '/system',
      name: 'system',
      component: PageView,
      meta: {
        title: '系统管理',
        icon: 'appstore-o'
      },
      redirect: '/system/user'
    }
  ],
  [201,
    {
      path: '/system/user',
      name: 'userList',
      component: () => import('@/views/system/user/UserList'),
      meta: {
        title: '用户管理',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [202,
    {
      path: '/system/systemrole',
      name: 'systemrole',
      component: () => import('@/views/system/SystemRole'),
      meta: {
        title: '角色管理',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [203,
    {
      path: '/system/systemroleright',
      name: 'systemroleright',
      component: () => import('@/views/system/SystemRoleRight'),
      meta: {
        title: '角色权限管理',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [204,
    {
      path: '/system/systemuserrole',
      name: 'systemuserrole',
      component: () => import('@/views/system/SystemUserRole'),
      meta: {
        title: '角色用户关系管理',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [205,
    {
      path: '/gameServer/serverController',
      name: 'serverController',
      component: () => import('@/views/system/ServerController'),
      meta: {
        title: '服务器管理',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [206,
    {
      path: '/gameServer/selfServerCtl',
      name: 'selfServerCtl',
      component: () => import('@/views/system/SelfServerCtl'),
      meta: {
        title: '角色服务器管理',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  // 充值查询相关 300-399
  [300,
    {
      path: '/dataSummary',
      name: 'dataSummary',
      component: PageView,
      meta: {
        title: '数据汇总',
        icon: 'table'
      },
      redirect: '/dataSummary/todaySummary'
    }
  ],
  [301,
    {
      path: '/dataSummary/todaySummary',
      name: 'todaySummary',
      component: () => import('@/views/dataSummary/TodaySummary'),
      meta: {
        title: '当日汇总',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [302,
    {
      path: '/dataSummary/hourSummary',
      name: 'hourSummary',
      component: () => import('@/views/dataSummary/HourSummary'),
      meta: {
        title: '小时统计',
        icon: 'switcher',
        keepAlive: true
      },
    }
  ],
  [
    303,
    {
      path: '/dataSummary/dailysummary',
      name: 'dataSummary',
      component: () => import('@/views/dataSummary/DailySummary'),
      meta: {
        title: '每日汇总',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    304,
    {
      path: '/dataSummary/singleSrv',
      name: 'singleSrv',
      component: () => import('@/views/dataSummary/SingleSrv'),
      meta: {
        title: '单服时间简表',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    305,
    {
      path: '/dataSummary/serverCharge',
      name: 'serverCharge',
      component: () => import('@/views/dataSummary/ServerCharge'),
      meta: {
        title: '区服充值汇总',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    306,
    {
      path: '/dataSummary/monthlyActive',
      name: 'monthlyactive',
      component: () => import('@/views/dataSummary/MonthlyActive'),
      meta: {
        title: '月活跃汇总',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    307,
    {
      path: '/dataSummary/serverListCharge',
      name: 'serverListCharge',
      component: () => import('@/views/dataSummary/ServerListCharge'),
      meta: {
        title: '各服时间段充值',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    308,
    {
      path: '/dataSummary/createRoleUiCounter',
      name: 'createRoleUiCounter',
      component: () => import('@/views/dataSummary/CreateRoleUiCounter'),
      meta: {
        title: '创角埋点',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    309,
    {
      path: '/dataSummary/expCopyStatis',
      name: 'expCopyStatis',
      component: () => import('@/views/dataSummary/ExpCopyStatis'),
      meta: {
        title: '经验副本统计',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    400,
    {
      path: '/dailystatis',
      name: 'dailystatis',
      component: PageView,
      meta: {
        title: '日常数据统计',
        icon: 'table'
      },
      redirect: '/dailystatis/loginretain'
    }
  ],
  [
    401,
    {
      path: '/dailystatis/loginretain',
      name: 'loginretain',
      component: () => import('@/views/dailystatis/LoginRetain'),
      meta: {
        title: '登录留存',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    402,
    {
      path: '/dailystatis/registStatis',
      name: 'registStatis',
      component: () => import('@/views/dailystatis/RegistStatis'),
      meta: {
        title: '注册统计',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    403,
    {
      path: '/dailystatis/dailyonlinestatis',
      name: 'dailyonlinestatis',
      component: () => import('@/views/dailystatis/DailyOnlineStatis'),
      meta: {
        title: '在线统计',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    404,
    {
      path: '/dailystatis/chargeStatis',
      name: 'chargeStatis',
      component: () => import('@/views/dailystatis/ChargeStatis'),
      meta: {
        title: '充值统计',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    500,
    {
      path: '/chargestatis',
      name: 'chargestatis',
      component: PageView,
      meta: {
        title: '充值统计',
        icon: 'table'
      },
      redirect: '/chargestatis/chargemonthsummary'
    }
  ],
  [
    501,
    {
      path: '/chargestatis/chargemonthsummary',
      name: 'chargemonthsummary',
      component: () => import('@/views/chargestatis/ChargeMonthSummary'),
      meta: {
        title: '月充值汇总',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    502,
    {
      path: '/chargestatis/chargesingledist',
      name: 'chargesingledist',
      component: () => import('@/views/chargestatis/ChargeSingleDist'),
      meta: {
        title: '单笔充值分布',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    503,
    {
      path: '/chargestatis/chargetotaldist',
      name: 'chargetotaldist',
      component: () => import('@/views/chargestatis/ChargeTotalDist'),
      meta: {
        title: '累计充值分布',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    504,
    {
      path: '/chargestatis/chargefirstleveldist',
      name: 'chargefirstleveldist',
      component: () => import('@/views/chargestatis/ChargeFirstLevelDist'),
      meta: {
        title: '首充等级分布',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    505,
    {
      path: '/chargestatis/chargeleveldist',
      name: 'chargeleveldist',
      component: () => import('@/views/chargestatis/ChargeLevelDist'),
      meta: {
        title: '付费等级分布',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    506,
    {
      path: '/chargestatis/chargeloginloss',
      name: 'chargeloginloss',
      component: () => import('@/views/chargestatis/ChargeLoginLoss'),
      meta: {
        title: '付费登录流失',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    507,
    {
      path: '/chargestatis/costgoldrank',
      name: 'costgoldrank',
      component: () => import('@/views/chargestatis/CostGoldRank'),
      meta: {
        title: '仙晶消耗排行',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    600,
    {
      path: '/onlinestatis',
      name: 'onlinestatis',
      component: PageView,
      meta: {
        title: '在线统计',
        icon: 'table'
      },
      redirect: '/onlinestatis/realTimeOnline'
    }
  ],
  [
    601,
    {
      path: '/onlinestatis/realTimeOnline',
      name: 'realTimeOnline',
      component: () => import('@/views/onlinestatis/RealTimeOnline'),
      meta: {
        title: '实时在线统计',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    602,
    {
      path: '/onlinestatis/onlineTimeDist',
      name: 'onlineTimeDist',
      component: () => import('@/views/onlinestatis/OnlineTimeDist'),
      meta: {
        title: '在线时长分布',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    700,
    {
      path: '/consumestatis',
      name: 'consumestatis',
      component: PageView,
      meta: {
        title: '消耗统计',
        icon: 'table'
      },
      redirect: '/consumestatis/goldFuntionStatis'
    }
  ],
  [
    701,
    {
      path: '/consumestatis/goldFuntionStatis',
      name: 'goldFuntionStatis',
      component: () => import('@/views/consumestatis/GoldFunctionStatis'),
      meta: {
        title: '仙晶消耗统计',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    702,
    {
      path: '/consumestatis/markConsumeStatis',
      name: 'markConsumeStatis',
      component: () => import('@/views/consumestatis/MarkConsumeStatis'),
      meta: {
        title: '商城消耗统计',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    800,
    {
      path: '/levelstatis',
      name: 'levelstatis',
      component: PageView,
      meta: {
        title: '等级统计',
        icon: 'table'
      },
      redirect: '/levelstatis/leveldist'
    }
  ],
  [
    801,
    {
      path: '/levelstatis/leveldist',
      name: 'leveldist',
      component: () => import('@/views/levelstatis/LevelDist'),
      meta: {
        title: '等级分布',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    802,
    {
      path: '/levelstatis/levelloss',
      name: 'levelloss',
      component: () => import('@/views/levelstatis/LevelLoss'),
      meta: {
        title: '等级流失',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    900,
    {
      path: '/cs',
      name: 'cs',
      component: PageView,
      meta: {
        title: '客服管理',
        icon: 'table'
      },
      redirect: '/cs/listCsMessage'
    }
  ],
  [
    901,
    {
      path: '/cs/listCsMessage',
      name: 'listCsMessage',
      component: () => import('@/views/customerservice/CustomerServiceList'),
      meta: {
        title: '获取留言列表',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    902,
    {
      path: '/cs/findByPlayer',
      name: 'findByPlayer',
      component: () => import('@/views/customerservice/SingleCustomerService'),
      meta: {
        title: '单用户反馈',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    903,
    {
      path: '/familyManager/listFamily',
      name: 'listFamily',
      component: () => import('@/views/familyManager/ListFamily'),
      meta: {
        title: '仙盟列表',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    904,
    {
      path: '/familyManager/updateFamilyNotice',
      name: 'updateFamilyNotice',
      component: () => import('@/views/familyManager/UpdateFamilyNotice'),
      meta: {
        title: '修改仙盟公告',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], 
  [
    905,
    {
      path: '/cs/chargeRank',
      name: 'chargeRank',
      component: () => import('@/views/customerservice/ChargeRank'),
      meta: {
        title: '充值金额排名统计',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    906,
    {
      path: '/cs/sendGmNoticeMail',
      name: 'sendGmNoticeMail',
      component: () => import('@/views/customerservice/SendGmNoticeMail'),
      meta: {
        title: '发送全服邮件',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],
  [
    907,
    {
      path: '/chatManager/blockWordList',
      name: 'blockWordList',
      component: () => import('@/views/chatwatch/BlockWordList'),
      meta: {
        title: '聊天管理',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],[
    1000,
    {
      path: '/gamelog',
      name: 'gamelog',
      component: PageView,
      meta: {
        title: '日志查询',
        icon: 'table'
      },
      redirect: '/gamelog/queryPlayerLoginLog'
    }
  ], [
    1001,
    {
      path: '/gamelog/queryPlayerLoginLog',
      name: 'queryPlayerLoginLog',
      component: () => import('@/views/gamelog/QueryPlayerLoginLog'),
      meta: {
        title: '查询玩家登录日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1002,
    {
      path: '/gamelog/queryPlayerLogoutLog',
      name: 'queryPlayerLogoutLog',
      component: () => import('@/views/gamelog/QueryPlayerLogoutLog'),
      meta: {
        title: '查询玩家登出日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1003,
    {
      path: '/gamelog/queryPlayerGoldLog',
      name: 'queryPlayerGoldLog',
      component: () => import('@/views/gamelog/QueryPlayerGoldLog'),
      meta: {
        title: '查询玩家仙晶日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1004,
    {
      path: '/gamelog/queryPlayerMoneyLog',
      name: 'queryPlayerMoneyLog',
      component: () => import('@/views/gamelog/QueryPlayerMoneyLog'),
      meta: {
        title: '查询玩家金币日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1005,
    {
      path: '/gamelog/queryPlayerItemLog',
      name: 'queryPlayerItemLog',
      component: () => import('@/views/gamelog/QueryPlayerItemLog'),
      meta: {
        title: '查询玩家道具日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1006,
    {
      path: '/gamelog/queryPlayerMarketLog',
      name: 'queryPlayerMarketLog',
      component: () => import('@/views/gamelog/QueryPlayerMarketLog'),
      meta: {
        title: '查询玩家商场日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1007,
    {
      path: '/gamelog/queryPlayerChargeLog',
      name: 'queryPlayerChargeLog',
      component: () => import('@/views/gamelog/QueryPlayerChargeLog'),
      meta: {
        title: '查询玩家充值日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1008,
    {
      path: '/gamelog/queryPlayerExpLog',
      name: 'queryPlayerExpLog',
      component: () => import('@/views/gamelog/QueryPlayerExpLog'),
      meta: {
        title: '查询玩家经验日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1009,
    {
      path: '/gamelog/queryPlayerRExpLog',
      name: 'queryPlayerRExpLog',
      component: () => import('@/views/gamelog/QueryPlayerRExpLog'),
      meta: {
        title: '查询玩家修为日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1010,
    {
      path: '/gamelog/queryPlayerFashionLog',
      name: 'queryPlayerFashionLog',
      component: () => import('@/views/gamelog/QueryPlayerFashionLog'),
      meta: {
        title: '查询玩家时装日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1011,
    {
      path: '/gamelog/queryPlayerMailLog',
      name: 'queryPlayerMailLog',
      component: () => import('@/views/gamelog/QueryPlayerMailLog'),
      meta: {
        title: '查询玩家邮件日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1012,
    {
      path: '/gamelog/queryPlayerShareLog',
      name: 'queryPlayerShareLog',
      component: () => import('@/views/gamelog/QueryPlayerShareLog'),
      meta: {
        title: '查询玩家分享日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1013,
    {
      path: '/gamelog/queryPlayerExpliotLog',
      name: 'queryPlayerExpliotLog',
      component: () => import('@/views/gamelog/QueryPlayerExpliotLog'),
      meta: {
        title: '查询玩家灵压日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1014,
    {
      path: '/gamelog/queryPlayerSealStoneLog',
      name: 'queryPlayerSealStoneLog',
      component: () => import('@/views/gamelog/QueryPlayerSealStoneLog'),
      meta: {
        title: '查询玩家符文日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1015,
    {
      path: '/gamelog/queryPlayerOfflineRewardLog',
      name: 'queryPlayerOfflineRewardLog',
      component: () => import('@/views/gamelog/QueryPlayerOfflineRewardLog'),
      meta: {
        title: '查询玩家离线日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1016,
    {
      path: '/gamelog/queryPlayerSerialGiftLog',
      name: 'queryPlayerSerialGiftLog',
      component: () => import('@/views/gamelog/QueryPlayerSerialGiftLog'),
      meta: {
        title: '查询玩家礼包码日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1017,
    {
      path: '/gamelog/queryPlayerCopyLog',
      name: 'queryPlayerCopyLog',
      component: () => import('@/views/gamelog/QueryPlayerCopyLog'),
      meta: {
        title: '查询玩家副本日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1018,
    {
      path: '/gamelog/queryPlayerRebirthLog',
      name: 'queryPlayerRebirthLog',
      component: () => import('@/views/gamelog/QueryPlayerRebirthLog'),
      meta: {
        title: '查询玩家转生日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1019,
    {
      path: '/gamelog/queryFamilyLog',
      name: 'queryFamilyLog',
      component: () => import('@/views/gamelog/QueryFamilyLog'),
      meta: {
        title: '查询仙盟日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1020,
    {
      path: '/gamelog/queryOtherResourceLog',
      name: 'queryOtherResourceLog',
      component: () => import('@/views/gamelog/QueryOtherResourceLog'),
      meta: {
        title: '查询其他货币日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1021,
    {
      path: '/gamelog/queryPlayerAchieveLog',
      name: 'queryPlayerAchieveLog',
      component: () => import('@/views/gamelog/QueryPlayerAchieveLog'),
      meta: {
        title: '查询成就日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1022,
    {
      path: '/gamelog/queryPlayerTitleLog',
      name: 'queryPlayerTitleLog',
      component: () => import('@/views/gamelog/QueryPlayerTitleLog'),
      meta: {
        title: '查询称号日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1023,
    {
      path: '/gamelog/queryPlayerChopLog',
      name: 'queryPlayerChopLog',
      component: () => import('@/views/gamelog/QueryPlayerChopLog'),
      meta: {
        title: '查询神器日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1024,
    {
      path: '/gamelog/queryPlayerPokedexLog',
      name: 'queryPlayerPokedexLog',
      component: () => import('@/views/gamelog/QueryPlayerPokedexLog'),
      meta: {
        title: '查询异闻录日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1025,
    {
      path: '/gamelog/queryPlayerPracticeLog',
      name: 'queryPlayerPracticeLog',
      component: () => import('@/views/gamelog/QueryPlayerPracticeLog'),
      meta: {
        title: '查询圣魂、伙伴、守护兽日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1026,
    {
      path: '/gamelog/queryPlayerSkillLog',
      name: 'queryPlayerSkillLog',
      component: () => import('@/views/gamelog/QueryPlayerSkillLog'),
      meta: {
        title: '查询技能日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1027,
    {
      path: '/gamelog/queryPlayerSkillBookLog',
      name: 'queryPlayerSkillBookLog',
      component: () => import('@/views/gamelog/QueryPlayerSkillBookLog'),
      meta: {
        title: '查询心法技能日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1028,
    {
      path: '/gamelog/queryPlayerMeridianLog',
      name: 'queryPlayerMeridianLog',
      component: () => import('@/views/gamelog/QueryPlayerMeridianLog'),
      meta: {
        title: '查询星宿日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1029,
    {
      path: '/gamelog/queryPlayerEnhanceLog',
      name: 'queryPlayerEnhanceLog',
      component: () => import('@/views/gamelog/QueryPlayerEnhanceLog'),
      meta: {
        title: '查询强化日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1030,
    {
      path: '/gamelog/queryPlayerWeaponSoulLog',
      name: 'queryPlayerWeaponSoulLog',
      component: () => import('@/views/gamelog/QueryPlayerWeaponSoulLog'),
      meta: {
        title: '查询器灵日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1031,
    {
      path: '/gamelog/queryPlayerTotemLog',
      name: 'queryPlayerTotemLog',
      component: () => import('@/views/gamelog/QueryPlayerTotemLog'),
      meta: {
        title: '查询仙兽蕴养日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1032,
    {
      path: '/gamelog/queryPlayerTotemExpandLog',
      name: 'queryPlayerTotemExpandLog',
      component: () => import('@/views/gamelog/QueryPlayerTotemExpandLog'),
      meta: {
        title: '查询仙兽灵感日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1033,
    {
      path: '/gamelog/queryBossDeadLog',
      name: 'queryBossDeadLog',
      component: () => import('@/views/gamelog/QueryBossDeadLog'),
      meta: {
        title: '查询boss击杀日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1034,
    {
      path: '/gamelog/queryPlayerGmGoldLog',
      name: 'queryPlayerGmGoldLog',
      component: () => import('@/views/gamelog/QueryPlayerGmGoldLog'),
      meta: {
        title: '玩家内币日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1035,
    {
      path: '/gamelog/queryPlayerHyzLog',
      name: 'queryPlayerHyzLog',
      component: () => import('@/views/gamelog/QueryPlayerHyzLog'),
      meta: {
        title: '玩家混元珠日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ], [
    1036,
    {
      path: '/gamelog/queryPowerLog',
      name: 'queryPowerLog',
      component: () => import('@/views/gamelog/QueryPowerLog'),
      meta: {
        title: '玩家战斗力日志',
        icon: 'switcher',
        keepAlive: true
      }
    }
  ],[
    1100,
    {
      path: '/playerManager',
      name: 'playerManager',
      component: PageView,
      meta: {
        title: '角色管理',
        icon: 'form',
        keepAlive: true
      },
      redirect: '/playerManager/blockChat'
    }
  ], [
    1101,
    {
      path: '/playerManager/playerList',
      name: 'playerList',
      component: () => import('@/views/playerManager/PlayerList'),
      meta: {
        title: '角色列表',
        icon: 'form',
        keepAlive: true
      }
    }
  ], [
    1102,
    {
      path: '/playerManager/queryPlayerInfos',
      name: 'queryPlayerInfos',
      component: () => import('@/views/playerManager/QueryPlayerInfos'),
      meta: {
        title: '查询玩家基本信息',
        icon: 'form',
        keepAlive: true
      }
    }
  ], [
    1103,
    {
      path: '/playerManager/blockPlayer',
      name: 'blockPlayer',
      component: () => import('@/views/playerManager/BlockPlayerList'),
      meta: {
        title: '封号',
        icon: 'form',
        keepAlive: true
      }
    }
  ], [
    1104,
    {
      path: '/playerManager/blockChat',
      name: 'blockChat',
      component: () => import('@/views/playerManager/BlockChatList'),
      meta: {
        title: '禁言',
        icon: 'form',
        keepAlive: true
      }
    }
  ], [
    1105,
    {
      path: '/playerManager/getRankInfo',
      name: 'getRankInfo',
      component: () => import('@/views/playerManager/GetRankInfo'),
      meta: {
        title: '获取排行榜信息',
        icon: 'form',
        keepAlive: true
      }
    }
  ], [
    1106,
    {
      path: '/playerManager/queryPlayerItems',
      name: 'queryPlayerItems',
      component: () => import('@/views/playerManager/QueryPlayerItems'),
      meta: {
        title: '查询玩家道具',
        icon: 'form',
        keepAlive: true
      }
    }
  ], [
    1107,
    {
      path: '/playerManager/queryPlayerEquips',
      name: 'queryPlayerEquips',
      component: () => import('@/views/playerManager/QueryPlayerEquips'),
      meta: {
        title: '查询玩家装备',
        icon: 'form',
        keepAlive: true
      }
    }
  ],[
    1200,
    {
      path: '/giftManager',
      name: 'createCdKey',
      component: PageView,
      meta: {
        title: '运营管理',
        icon: 'form',
        keepAlive: true
      },
      redirect: '/giftManager/createGift'
    }
  ], 
  [
    1201,
    {
      path: '/giftManager/createGift',
      name: 'createCdKey',
      component: () => import('@/views/giftManager/GiftList'),
      meta: {
        title: '生成礼包码',
        icon: 'form',
        keepAlive: true
      }
    }
  ], 
  [
    1202,
    {
      path: '/giftManager/queryGift',
      name: 'queryGift',
      component: () => import('@/views/giftManager/QueryGift'),
      meta: {
        title: '获取可用礼包码',
        icon: 'form',
        keepAlive: true
      }
    }
  ], 
  [
    1203,
    {
      path: '/noticeManager/setLoginNoticer',
      name: 'setLoginNoticer',
      component: () => import('@/views/noticeManager/SetLoginNoticer'),
      meta: {
        title: '设置登录公告',
        icon: 'form',
        keepAlive: true
      }
    }
  ], [
    1204,
    {
      path: '/noticeManager/setGameNotice',
      name: 'setGameNotice',
      component: () => import('@/views/noticeManager/SetGameNotice'),
      meta: {
        title: '设置游戏内公告',
        icon: 'form',
        keepAlive: true
      }
    }
  ],
   [
    1205,
    { 
      path: '/noticeManager/sendChatNotice',
      name: 'sendChatNotice',
      component: () => import('@/views/noticeManager/ChatNotice'),
      meta: {
        title: '发送跑马灯公告',
        icon: 'form',
        keepAlive: true
      }
    }
  ],
  [
   1206,
   { 
     path: '/mailManager/sendOpMail',
     name: 'sendOpMail',
     component: () => import('@/views/mailManager/SendOpMail'),
     meta: {
       title: '本服邮件',
       icon: 'form',
       keepAlive: true
     }
   }
 ],
 [
  1207,
  { 
    path: '/mailManager/sendRewardMail',
    name: 'sendRewardMail',
    component: () => import('@/views/mailManager/SendRewardMail'),
    meta: {
      title: '奖励邮件',
      icon: 'form',
      keepAlive: true
    }
  }
],
[
 1208,
 { 
   path: '/mailManager/reviewRewardMail',
   name: 'reviewRewardMail',
   component: () => import('@/views/mailManager/ReviewRewardMail'),
   meta: {
     title: '审核奖励邮件',
     icon: 'form',
     keepAlive: true
   }
 }
],
[
 1209,
 { 
   path: '/gsOperator/listServers',
   name: 'listServers',
   component: () => import('@/views/gsOperator/ListServers'),
   meta: {
     title: '服务器列表',
     icon: 'form',
     keepAlive: true
   }
 }
],
[
 1210,
 { 
   path: '/whitelist/getWhiteAccounts',
   name: 'getWhiteAccounts',
   component: () => import('@/views//whitelist/GetWhiteAccounts'),
   meta: {
     title: '黑/白名单',
     icon: 'form',
     keepAlive: true
   }
 }
],
[
 1211,
 { 
   path: '/crossSrvManager/crossSrvList',
   name: 'crossSrvList',
   component: () => import('@/views//crossSrvManager/CrossSrvList'),
   meta: {
     title: '跨服管理',
     icon: 'form',
     keepAlive: true
   }
 }
]
])


const testRouter = [
  // 系统管理从200-299

  {
    path: '/system',
    name: 'system',
    component: PageView,
    meta: {
      title: '系统管理',
      icon: 'appstore-o'
    },
    redirect: '/system/user',
    children: [
      {
        path: '/system/user',
        name: 'userList',
        component: () => import('@/views/system/user/UserList'),
        meta: {
          title: '用户管理',
          icon: 'switcher',
          keepAlive: true
        }
      }

      // [202,
      //   {
      //     path: '/system/role',
      //     name: 'roleList',
      //     component: () => import('@/views/system/user/RoleList'),
      //     meta: {
      //       title: '角色管理',
      //       icon: 'switcher',
      //       keepAlive: true
      //     }
      //   }
      // ],
    ]
  }
  // 充值查询相关 300-399
  ,
  {
    path: '/',
    name: 'dataSummary',
    component: PageView,
    meta: {
      title: '数据汇总',
      icon: 'table'
    },
    redirect: '/dataSummary/singleSrv',
    children: [
      {
        path: '/dataSummary/todaySummary',
        name: 'todaySummary',
        component: () => import('@/views/dataSummary/TodaySummary'),
        meta: {
          title: '当日汇总',
          icon: 'switcher',
          keepAlive: true
        }
      }
      ,
      {
        path: '/dataSummary/hourSummary',
        name: 'hourSummary',
        component: () => import('@/views/dataSummary/HourSummary'),
        meta: {
          title: '小时统计',
          icon: 'switcher',
          keepAlive: true
        },
      }
      ,
      {
        path: '/dataSummary/dailysummary',
        name: 'dataSummary',
        component: () => import('@/views/dataSummary/DailySummary'),
        meta: {
          title: '每日汇总',
          icon: 'switcher',
          keepAlive: true
        }
      }
      ,
      {
        path: '/dataSummary/singleSrv',
        name: 'singleSrv',
        component: () => import('@/views/dataSummary/SingleSrv'),
        meta: {
          title: '单服时间简表',
          icon: 'switcher',
          keepAlive: true
        }
      }
      ,
      {
        path: '/dataSummary/serverCharge',
        name: 'serverCharge',
        component: () => import('@/views/dataSummary/ServerCharge'),
        meta: {
          title: '区服充值汇总',
          icon: 'switcher',
          keepAlive: true
        }
      }
      ,
      {
        path: '/dataSummary/monthlyActive',
        name: 'monthlyactive',
        component: () => import('@/views/dataSummary/MonthlyActive'),
        meta: {
          title: '月活跃汇总',
          icon: 'switcher',
          keepAlive: true
        }
      }
      ,
      {
        path: '/dataSummary/serverListCharge',
        name: 'serverListCharge',
        component: () => import('@/views/dataSummary/ServerListCharge'),
        meta: {
          title: '各服时间段充值',
          icon: 'switcher',
          keepAlive: true
        }
      }
    ]
  }
  ,
  {
    path: '/dailystatis',
    name: 'dailystatis',
    component: PageView,
    meta: {
      title: '日常数据统计',
      icon: 'table'
    },
    redirect: '/dailystatis/loginretain',
    children: [
      {
        path: '/dailystatis/loginretain',
        name: 'loginretain',
        component: () => import('@/views/dailystatis/LoginRetain'),
        meta: {
          title: '登录留存',
          icon: 'switcher',
          keepAlive: true
        }
      }
      ,
      {
        path: '/dailystatis/registStatis',
        name: 'registStatis',
        component: () => import('@/views/dailystatis/RegistStatis'),
        meta: {
          title: '注册统计',
          icon: 'switcher',
          keepAlive: true
        }
      }
      ,
      {
        path: '/dailystatis/dailyonlinestatis',
        name: 'dailyonlinestatis',
        component: () => import('@/views/dailystatis/DailyOnlineStatis'),
        meta: {
          title: '在线统计',
          icon: 'switcher',
          keepAlive: true
        }
      }
      ,
      {
        path: '/dailystatis/chargeStatis',
        name: 'chargeStatis',
        component: () => import('@/views/dailystatis/ChargeStatis'),
        meta: {
          title: '充值统计',
          icon: 'switcher',
          keepAlive: true
        }
      }
    ]
  }
  ,
  {
    path: '/chargestatis',
    name: 'chargestatis',
    component: PageView,
    meta: {
      title: '充值统计',
      icon: 'table'
    },
    redirect: '/chargestatis/chargemonthsummary',
    children: [
      {
        path: '/chargestatis/chargemonthsummary',
        name: 'chargemonthsummary',
        component: () => import('@/views/chargestatis/ChargeMonthSummary'),
        meta: {
          title: '月充值汇总',
          icon: 'switcher',
          keepAlive: true
        }
      }
      ,
      {
        path: '/chargestatis/chargesingledist',
        name: 'chargesingledist',
        component: () => import('@/views/chargestatis/ChargeSingleDist'),
        meta: {
          title: '单笔充值分布',
          icon: 'switcher',
          keepAlive: true
        }
      }
      ,
      {
        path: '/chargestatis/chargetotaldist',
        name: 'chargetotaldist',
        component: () => import('@/views/chargestatis/ChargeTotalDist'),
        meta: {
          title: '累计充值分布',
          icon: 'switcher',
          keepAlive: true
        }
      }
      ,
      {
        path: '/chargestatis/chargefirstleveldist',
        name: 'chargefirstleveldist',
        component: () => import('@/views/chargestatis/ChargeFirstLevelDist'),
        meta: {
          title: '首充等级分布',
          icon: 'switcher',
          keepAlive: true
        }
      }
      ,
      {
        path: '/chargestatis/chargeleveldist',
        name: 'chargeleveldist',
        component: () => import('@/views/chargestatis/ChargeLevelDist'),
        meta: {
          title: '付费等级分布',
          icon: 'switcher',
          keepAlive: true
        }
      }
      ,
      {
        path: '/chargestatis/chargeloginloss',
        name: 'chargeloginloss',
        component: () => import('@/views/chargestatis/ChargeLoginLoss'),
        meta: {
          title: '付费登录流失',
          icon: 'switcher',
          keepAlive: true
        }
      }
      ,
      {
        path: '/chargestatis/costgoldrank',
        name: 'costgoldrank',
        component: () => import('@/views/chargestatis/CostGoldRank'),
        meta: {
          title: '仙晶消耗排行',
          icon: 'switcher',
          keepAlive: true
        }
      }
    ]
  }
  ,
  {
    path: '/onlinestatis',
    name: 'onlinestatis',
    component: PageView,
    meta: {
      title: '在线统计',
      icon: 'table'
    },
    redirect: '/onlinestatis/realTimeOnline',
    children: [
      {
        path: '/onlinestatis/realTimeOnline',
        name: 'realTimeOnline',
        component: () => import('@/views/onlinestatis/RealTimeOnline'),
        meta: {
          title: '实时在线统计',
          icon: 'switcher',
          keepAlive: true
        }
      }
      ,
      {
        path: '/onlinestatis/onlineTimeDist',
        name: 'onlineTimeDist',
        component: () => import('@/views/onlinestatis/OnlineTimeDist'),
        meta: {
          title: '在线时长分布',
          icon: 'switcher',
          keepAlive: true
        }
      }
    ]
  }
  ,
  {
    path: '/consumestatis',
    name: 'consumestatis',
    component: PageView,
    meta: {
      title: '消耗统计',
      icon: 'table'
    },
    redirect: '/consumestatis/goldFuntionStatis',
    children: [
      {
        path: '/consumestatis/goldFuntionStatis',
        name: 'goldFuntionStatis',
        component: () => import('@/views/consumestatis/GoldFunctionStatis'),
        meta: {
          title: '仙晶消耗统计',
          icon: 'switcher',
          keepAlive: true
        }
      }
      ,
      {
        path: '/consumestatis/markConsumeStatis',
        name: 'markConsumeStatis',
        component: () => import('@/views/consumestatis/MarkConsumeStatis'),
        meta: {
          title: '商城消耗统计',
          icon: 'switcher',
          keepAlive: true
        }
      }
    ]
  }
  ,
  {
    path: '/levelstatis',
    name: 'levelstatis',
    component: PageView,
    meta: {
      title: '等级统计',
      icon: 'table'
    },
    redirect: '/levelstatis/leveldist',
    children: [
      {
        path: '/levelstatis/leveldist',
        name: 'leveldist',
        component: () => import('@/views/levelstatis/LevelDist'),
        meta: {
          title: '等级分布',
          icon: 'switcher',
          keepAlive: true
        }
      }
      ,
      {
        path: '/levelstatis/levelloss',
        name: 'levelloss',
        component: () => import('@/views/levelstatis/LevelLoss'),
        meta: {
          title: '等级流失',
          icon: 'switcher',
          keepAlive: true
        }
      }
    ]
  },{
    path: '/cs',
    name: 'cs',
    component: PageView,
    meta: {
      title: '客服管理',
      icon: 'table'
    },
    redirect: '/cs/listCsMessage',
    children: [
      {
        path: '/cs/listCsMessage',
        name: 'listCsMessage',
        component: () => import('@/views/customerservice/CustomerServiceList'),
        meta: {
          title: '获取留言列表',
          icon: 'switcher',
          keepAlive: true
        }
      },
      {
        path: '/cs/findByPlayer',
        name: 'findByPlayer',
        component: () => import('@/views/customerservice/SingleCustomerService'),
        meta: {
          title: '单用户反馈',
          icon: 'switcher',
          keepAlive: true
        }
      }
    ]
  },
  // {
  //   path: '/playerinfo',
  //   name: 'playerinfo',
  //   component: PageView,
  //   meta: {
  //     title: '角色信息查询',
  //     icon: 'table'
  //   },
  //   redirect: '/playerinfo/searchplayer',
  //   children: [
  //     {
  //       path: '/playerinfo/searchplayer',
  //       name: 'searchplayer',
  //       component: () => import('@/views/playerinfo/SearchPlayer'),
  //       meta: {
  //         title: '查询玩家信息',
  //         icon: 'switcher',
  //         keepAlive: true
  //       }
  //     },
  //     {
  //       path: '/playerinfo/searchequip',
  //       name: 'searchequip',
  //       component: () => import('@/views/playerinfo/SearchEquip'),
  //       meta: {
  //         title: '查询玩家装备信息',
  //         icon: 'switcher',
  //         keepAlive: true
  //       }
  //     },
  //     {
  //       path: '/playerinfo/searchitem',
  //       name: 'searchitem',
  //       component: () => import('@/views/playerinfo/SearchItem'),
  //       meta: {
  //         title: '查询玩家道具信息',
  //         icon: 'switcher',
  //         keepAlive: true
  //       }
  //     }
  //   ]
  // },{
  //   path: '/sendform',
  //   name: 'sendform',
  //   component: PageView,
  //   meta: {
  //     title: '表单相关',
  //     icon: 'table'
  //   },
  //   redirect: '/cs/listCsMessage',
  //   children: [
  //     {
  //       path: '/sendform/playerstatus',
  //       name: 'playerstatus',
  //       component: () => import('@/views/sendform/playerstatus'),
  //       meta: {
  //         title: '玩家状态',
  //         icon: 'switcher',
  //         keepAlive: true
  //       }
  //     },{
  //       path: '/sendform/sendeamiltoplayer',
  //       name: 'sendeamiltoplayer',
  //       component: () => import('@/views/sendform/SendEamilToPlayer'),
  //       meta: {
  //         title: '发送玩家邮件',
  //         icon: 'switcher',
  //         keepAlive: true
  //       }
  //     },{
  //       path: '/sendform/sendeamiltoall',
  //       name: 'sendeamiltoall',
  //       component: () => import('@/views/sendform/SendEamilToAll'),
  //       meta: {
  //         title: '邮件公告',
  //         icon: 'switcher',
  //         keepAlive: true
  //       }
  //     },{
  //       path: '/sendform/sendeamilwithgift',
  //       name: 'sendeamilwithgift',
  //       component: () => import('@/views/sendform/SendEamilWithGift'),
  //       meta: {
  //         title: '邮件发奖',
  //         icon: 'switcher',
  //         keepAlive: true
  //       }
  //     },{
  //       path: '/sendform/sendlight',
  //       name: 'sendlight',
  //       component: () => import('@/views/sendform/SendLight'),
  //       meta: {
  //         title: '跑马灯公告',
  //         icon: 'switcher',
  //         keepAlive: true
  //       }
  //     },{
  //       path: '/sendform/sendbord',
  //       name: 'sendbord',
  //       component: () => import('@/views/sendform/SendBord'),
  //       meta: {
  //         title: '设置公告',
  //         icon: 'switcher',
  //         keepAlive: true
  //       }
  //     },{
  //       path: '/sendform/keywords',
  //       name: 'keywords',
  //       component: () => import('@/views/sendform/KeyWords'),
  //       meta: {
  //         title: '禁言',
  //         icon: 'switcher',
  //         keepAlive: true
  //       }
  //     },
  //     {
  //       path: '/sendform/addorder',
  //       name: 'addorder',
  //       component: () => import('@/views/sendform/AddOrder'),
  //       meta: {
  //         title: '补单',
  //         icon: 'switcher',
  //         keepAlive: true
  //       }
  //     },{
  //       path: '/sendform/setservertime',
  //       name: 'setservertime',
  //       component: () => import('@/views/sendform/SetServerTime'),
  //       meta: {
  //         title: '修改服务器时间',
  //         icon: 'switcher',
  //         keepAlive: true
  //       }
  //     }
  //   ]
  // }
]
export function getMenuConfig(id) {
  const origin = menuRouteConfig.get(id)
  if (!origin) {
    return null;
  }
  return Object.assign(origin)
}

export function toMenuRoute(menuIds) {
  const menuInfos = []
  menuIds.sort((a, b) => {
    if (a.id != 200 && b.id != 200) {
      return a.id - b.id;
    } else if (a.id == 200) {
      return 1;
    } else if (b.id == 200) {
      return -1;
    }
  })
  // if(menuIds.length == 10) {
  //   menuIds.push({id: 2000})
  // }
  for (const menu of menuIds) {
    const menuInfo = getMenuConfig(menu.id);
    if (!menuInfo) {
      continue;
    }
    if (menu.resource && menu.resource.length) {
      menuInfo.children = toMenuRoute(menu.resource)
    }
    menuInfos.push(menuInfo)
  }
  return menuInfos;
}
