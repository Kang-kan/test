<template>
  <a-locale-provider :locale="locale">
    <div id="app">
      <router-view />
    </div>
  </a-locale-provider>
</template>

<script>
import xmlConfig from '@/config/xmlConfig'
import zhCN from 'ant-design-vue/lib/locale-provider/zh_CN'
import { AppDeviceEnquire } from '@/utils/mixin'

export default {
  mixins: [AppDeviceEnquire],
  data() {
    return {
      locale: zhCN
    }
  },
  methods: {
    sortStr(str) {
      let s = this
      let list = JSON.parse(s.$crypto.decrypt(str))
      let len = list.serverVos.length
      let newList = []
      for (let i = 0; i < len; i++) {
        if (list.serverVos[i].status == '1') {
          newList.push(list.serverVos[i])
        }
      }
      localStorage.setItem('serverList', JSON.stringify(newList))
    },
    getServerList() {
      let s = this
      let servestr = 'http://192.168.0.47:18866/data/handleMsg.do'
      let signStr = `?cmd=1000&channel=1&type=3&version=0.0.0`
      let sign = s.$crypto.encrypted(signStr)
      servestr += '?msg=' + sign
      s.$ajax
        .get(servestr)
        .then(function(res) {
          if (res.status == 200) {
            s.sortStr(res.data)

          }
        })
        .catch(function(err) {
       console.log("拉取失败", err);
          if (err) {
            throw err
          }
        })
    }
  },
  mounted: function() {
    this.getServerList();
    xmlConfig.run();
  },
  updated: function() {
    this.getServerList();
  },
  beforeCreate() {
    let s = this
    s.$ajax.get('../fashionConfig.xml').then(res => {})
    s.$ajax.get('../itemConfig.xml').then(res => {})
    s.$ajax.get('../equipConfig.xml').then(res => {})
  }
}
</script>
<style>
#app {
  height: 100%;
}
</style>
