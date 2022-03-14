package com.xxgame.admin.web.util;

import com.xxgame.admin.web.exception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 加密解密工具类
 * @author gil
 *
 */
public class CryptUtils {
	
	/**
	 * 密钥算法
	 */

	private static final String KEY_ALGORITHM = "AES";
	
	/**
	 * 加密、解密算法/工作模式/填充模式
	 */
	private static final String CHIPHER_ALGOTITHM = "AES/CBC/PKCS5Padding";
	
	private static final Logger logger = LoggerFactory.getLogger(CryptUtils.class);
	
	/**
	 * AES解密
	 * @param cipherText 待解密数据
	 * @param cipherKey 密钥
	 * @param ivBytes 初始化向量
	 * @return
	 */
	public static byte[] aesDecrypt(byte[] cipherText, byte[] cipherKey, byte[] ivBytes) {
		try {
			SecretKey secretKey = new SecretKeySpec(cipherKey, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(CHIPHER_ALGOTITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, generateIV(ivBytes));
			return cipher.doFinal(cipherText);
		} catch (Exception e) {
			logger.error("AES解密异常", e);
			throw new ServerException("服务器异常");
		}
	}

	/**
	 * AES加密
	 * @param plainText 待加密数据
	 * @param cipherKey 密钥，要求密钥长度为128、192、256位，16、24、32字节。
	 * @param ivBytes 初始化向量
	 * @return
	 */
	public static byte[] aesEncrypt(byte[] plainText , byte[] cipherKey, byte[] ivBytes) {
		try {
			SecretKey secretKey = new SecretKeySpec(cipherKey, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(CHIPHER_ALGOTITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, generateIV(ivBytes));
			return cipher.doFinal(plainText );
		} catch (Exception e) {
			logger.error("AES加密异常", e);
			throw new ServerException("服务器异常");
		}
	}
    
	/**
	 * 生成iv  
	 * @param iv
	 * @return
	 * @throws Exception
	 */
    private static AlgorithmParameters generateIV(byte[] iv) throws Exception{  
        AlgorithmParameters params = AlgorithmParameters.getInstance(KEY_ALGORITHM);  
        params.init(new IvParameterSpec(iv));  
        return params;  
    }  
	
	/**
	 * 生成密钥
	 * @return
	 */
	public static byte[] initKey() {
		KeyGenerator keyGenerator;
		try {
			keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
			keyGenerator.init(256, new SecureRandom());
			SecretKey secretKey = keyGenerator.generateKey();
			return secretKey.getEncoded();
		} catch (NoSuchAlgorithmException e) {
			logger.error("生成密钥异常", e);
			throw new ServerException("服务器异常");
		}
	}
	
}
