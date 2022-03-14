import types from "@/components/Exception/type"

export function timeFix() {
  const time = new Date()
  const hour = time.getHours()
  return hour < 9 ? '早上好' : hour <= 11 ? '上午好' : hour <= 13 ? '中午好' : hour < 20 ? '下午好' : '晚上好'
}

export function welcome() {
  const arr = ['休息一会儿吧', '准备吃什么呢?', '要不要打一把 DOTA', '我猜你可能累了']
  const index = Math.floor(Math.random() * arr.length)
  return arr[index]
}

/**
 * 触发 window.resize
 */
export function triggerWindowResizeEvent() {
  const event = document.createEvent('HTMLEvents')
  event.initEvent('resize', true, true)
  event.eventType = 'message'
  window.dispatchEvent(event)
}

export function handleScrollHeader(callback) {
  let timer = 0

  let beforeScrollTop = window.pageYOffset
  callback = callback || function () { }
  window.addEventListener(
    'scroll',
    event => {
      clearTimeout(timer)
      timer = setTimeout(() => {
        let direction = 'up'
        const afterScrollTop = window.pageYOffset
        const delta = afterScrollTop - beforeScrollTop
        if (delta === 0) {
          return false
        }
        direction = delta > 0 ? 'down' : 'up'
        callback(direction)
        beforeScrollTop = afterScrollTop
      }, 50)
    },
    false
  )
}

/**
 * Remove loading animate
 * @param id parent element id or class
 * @param timeout
 */
export function removeLoadingAnimate(id = '', timeout = 1500) {
  if (id === '') {
    return
  }
  setTimeout(() => {
    document.body.removeChild(document.getElementById(id))
  }, timeout)
}

/**
 * 将时间戳转换成日期格式 yyyy-MM-dd HH:MM:SS
 */
export function formatTimestamp(timestamp) {
  const date = new Date()
  date.setTime(timestamp)
  const month = (date.getMonth() + 1).toString().padStart(2, '0')
  const strDate = date.getDate().toString().padStart(2, '0')
  const hour = date.getHours().toString().padStart(2, '0')
  const minute = date.getMinutes().toString().padStart(2, '0')
  const second = date.getSeconds().toString().padStart(2, '0')
  return `${date.getFullYear()}-${month}-${strDate} ${hour}:${minute}:${second}`
}
/**
 * 获取boss玩法类型
 * @param {*} id 
 */
export function playerTypeName(type) {
  let typestr = '';
  switch (Number(type)) {
    case 1:
      typestr = '活动boss'
      break;
      case 2:
      typestr = '奇遇boss'
      break;
      case 3:
      typestr = '召唤boss'
      break;
      case 4:
      typestr = '印记塔'
      break;
      case 5:
      typestr = '仙盟试炼'
      break;
      case 6:
      typestr = '天域争霸'
      break;
      case 7:
      typestr = '帝君神殿'
      break;
      case 8:
      typestr = '帝君'
      break;
      case 9:
      typestr = '帝君塔'
      break;
      case 10:
      typestr = '秘境boss'
      break;
      case 11:
      typestr = 'boss之家'
      break;
      case 12:
      typestr = '仙戒boss'
      break;
      case 13:
      typestr = '主线boss'
      break;
      case 14:
      typestr = '关卡协助'
      break;
      case 15:
      typestr = '结婚场景'
      break;
      case 16:
      typestr = '密境boss'
      break;
      case 17:
      typestr = '守卫剑阁'
      break;
      case 18:
      typestr = '世界boss'
      break;
      case 19:
      typestr = '个人boss'
      break;
      case 20:
      typestr = '万妖塔'
      break;
      case 21:
      typestr = '翎羽boss'
      break;
      case 22:
      typestr = '仙缘副本'
      break;
      case 23:
      typestr = '历练之地'
      break;
      case 24:
      typestr = '仙魔战场'
      break;
      case 25:
      typestr = '九重天'
      break;
      case 26:
      typestr = '仙界魔神'
      break;
      case 27:
      typestr = '荒古禁地'
      break;
  }
  return typestr;
}
/**
 * 获取货币类型名
 * @param {*} id 
 */
export function CurrencyTypeName(id) {

  var CurrencyType = [
    "不消耗", 0,
    "仙晶", 1,
    "金币", 2,
    "内币", 3,
    "经验", 4,
    "修为", 5,
    "灵压", 6,
    "声望", 7,
    "翅膀经验", 8,
    "成就点", 9,
    "VIP经验", 10,
    "真气", 11,
    "神器", 12,
    "复仇次数", 13,
    "天梯挑战次数", 14,
    "召唤boss次数", 15,
    "世界boss攻击次数", 16,
    "翅膀boss攻击次数", 17,
    "神魂boss攻击次数", 18,
    "神秘商店刷新次数", 19,
    "法阵经验", 20,
    "仙盟贡献", 21,
    "仙盟经验", 22,
    "boss之家攻击次数", 23,
    "法阵附灵经验", 24,
    "竞技积分", 25,
    "符文精华", 26,
    "成就积分", 27,
    "神魔boss次数", 28,
    "活跃度", 29,
    "异闻录经验", 30,
    "荣耀竞技次数", 31,
    "战神boss挑战次数", 32,
    "骰子数量", 33,
    "商店积分", 34,
    "魅力值", 35,
    "器灵熔炼经验", 36,
    "善恶值", 37,
    "跨服boss次数", 38,
    "跨服boss采集次数", 39,
    "跨服boss协助次数", 40,
    "消消乐次数", 41,
    "荣耀赛季筹码", 42,
    "奖池1积分", 43,
    "奖池2积分", 44,
    "一元夺宝积分", 45,
    "奖池10%", 46,
    "奖池30%", 47,
    "奖池100%", 48,
    "亲密度", 49,
    "飘渺币", 50,
    "魂力", 51,
  ]
  let index = CurrencyType.indexOf(Number(id));
  return CurrencyType[index - 1];
}

