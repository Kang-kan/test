// ie polyfill
import '@babel/polyfill'

import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store/'
import { VueAxios } from './utils/request'
import axios from 'axios'
import moment from 'moment'
// mock
// import './mock'

import bootstrap from './core/bootstrap'
import './core/use'
import './permission' // permission control
import './utils/filter' // global filter
import i18n from './locales'
import CryptoJS from './utils/crypto-js.js'
import x2js from 'x2js';
import xlsx  from 'xlsx'
import xmlConfig from './config/xmlConfig'
xmlConfig.run();
Vue.prototype.$x2js = new x2js();
Vue.prototype.$xlsx = xlsx;
var encrypted = function(aw) {
	var sessionKey = 'THVja3kzNzExNzN5a2N1TA==';
	var iv = 'eWtjdUwxNzMzNzFMdWNreQ==';

	var niv = CryptoJS.enc.Base64.parse(iv);
	var keyHex = CryptoJS.enc.Base64.parse(sessionKey);

	var encrypted = CryptoJS.AES.encrypt(aw, keyHex, {
		iv: niv,
		mode: CryptoJS.mode.CBC,
		padding: CryptoJS.pad.Pkcs7
	});
	return encrypted.toString();
}

var decrypt = function(mw) {
	var sessionKey = 'THVja3kzNzExNzN5a2N1TA==';
	var iv = 'eWtjdUwxNzMzNzFMdWNreQ==';

	var niv = CryptoJS.enc.Base64.parse(iv);
	var keyHex = CryptoJS.enc.Base64.parse(sessionKey);

	var dec = CryptoJS.AES.decrypt(mw, keyHex, {
		iv: niv,
		mode: CryptoJS.mode.CBC,
		padding: CryptoJS.pad.Pkcs7
	})
	var jiemi = dec.toString(CryptoJS.enc.Utf8);
	return jiemi;
}
let Crypto = {
	encrypted: encrypted,
	decrypt: decrypt
}
Vue.prototype.$crypto = Crypto;
Vue.config.productionTip = false
Vue.config.devtools = true;
// mount axios Vue.$http and this.$http
Vue.use(VueAxios)
Vue.prototype.$ajax = axios;
Vue.prototype.$moment = moment;
new Vue({
  router,
  store,
  i18n,
  created: bootstrap,
  render: h => h(App)
}).$mount('#app')
