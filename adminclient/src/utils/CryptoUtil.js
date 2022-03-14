
/**
 * AES 加密
 * @param {*} plainText
 * @param {*} ivBytes
 */
export function aesEncrypt (plainText, ivBytes) {
  const CryptoJS = require('crypto-js')
  const key = CryptoJS.enc.Base64.parse(process.env.VUE_APP_LOGIN_KEY)
  const iv = CryptoJS.enc.Base64.parse(ivBytes)
  const srcs = CryptoJS.enc.Utf8.parse(plainText)
  const encrypted = CryptoJS.AES.encrypt(srcs, key, { iv: iv, mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7 })
  return encrypted.ciphertext.toString().toLowerCase()
}
