package com.xxgame.admin.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * 登录服工具
 * @author copy form login server
 */
public class LoginSrvUtil {
	
	private static Logger log = LoggerFactory.getLogger(LoginSrvUtil.class);
	
	// 密匙
    private static final String KEY = "THVja3kzNzExNzN5a2N1TA==";
    // 偏移量
    private static final String IV = "eWtjdUwxNzMzNzFMdWNreQ==";
    // 编码
    private static final String ENCODING = "UTF-8";
    // 算法
    private static final String ALGORITHM = "AES";
    // 默认的加密算法
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

	/**
	 * 密码解密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public final static String decrypt(String data) {
		String result = null;
		try {
			byte[] key = Base64.getMimeDecoder().decode(KEY.getBytes(ENCODING));
			byte[] off = Base64.getMimeDecoder().decode(IV.getBytes(ENCODING));
			byte[] dataArray = Base64.getMimeDecoder().decode(data.getBytes(ENCODING));
			
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			SecretKeySpec skeySpec = new SecretKeySpec(key, ALGORITHM);
			IvParameterSpec iv = new IvParameterSpec(off);//使用CBC模式，需要一个向量iv，可增加加密算法的强度
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			result = new String(cipher.doFinal(dataArray), ENCODING);
		} catch (Exception e) {
			log.error("decrypt", e);
		}
		return result;
	}

	/**
	 * 密码加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public final static String encrypt(String data) {
		String result = null;
		try {
			byte[] key = Base64.getMimeDecoder().decode(KEY.getBytes(ENCODING));
			byte[] iv = Base64.getMimeDecoder().decode(IV.getBytes(ENCODING));
			
			SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
			result = Base64.getMimeEncoder().encodeToString(cipher.doFinal(data.getBytes(ENCODING)));
		} catch (Exception e) {
			log.error("encrypt", e);
		}
		return result;
	}

}
