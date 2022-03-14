package com.xxgame.admin.web.util;

import com.xxgame.admin.web.exception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

/**
 * PBKDF2(Password-Based Key Derivation Function) 密钥扩展算法如，PBKDF2简单而言就是将salted hash进行多次重复计算。
 * 
 * 它的基本原理是通过一个伪随机函数，例如HMAC-SHA256函数，把明文和一个盐值作为输入参数，然后重复进行运算，并最终产生用于后续加密的密钥。如果重复的次数足够大（虽然hmac-sha256计算速度很快，假设需要0.001秒，
 * 次数如果是10000次，那就需要10秒才能产生密钥）。破解的成本就会变得很高而运算轮数的增加及盐值的添加也会增加“彩虹表”攻击的难度。
 * 
 * Work factor：iteration（轮数）。
 * 
 * 优点 
 * 1.发布的时间长，经历了足够的考验，暂无有效攻击。 
 * 2.各种语言的实现都有，方便使用。 
 * 3.高度可配置，基于安全基于性能一定程度上都可以满足。
 * 4.知名产品加成（Django，veracrypt，lastpass，微软部分产品）。
 * 
 * 缺点：算法是cpu密集型，对多gpu及专用设备如各种矿机等，无法有效拖慢攻击速度
 * 
 * @author gil
 *
 */
public class PBKDF2Utils {

	/**
	 * 算法名
	 */
	private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA512";
	
    /** 
     * 生成密文的长度 
     */  
    private static final int HASH_BIT_SIZE = 512;  
  
    /** 
     * 迭代次数 
     */  
    private static final int PBKDF2_ITERATIONS = 1000; 
	
    private static final Logger logger = LoggerFactory.getLogger(CryptUtils.class);
    
    /** 
     * 通过提供加密的强随机数生成器 生成key 
     *  
     * @return 
     */  
    public static byte[] createKey(int length) {  
        SecureRandom random;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
	        byte[] salt = new byte[length];
	        random.nextBytes(salt);
	        return salt;
		} catch (Exception e) {
			logger.error("生成随机盐字符串异常", e);
			throw new ServerException("服务器异常");
		}  
    }
    
    /** 
     * 生成密文 
     *  
     * @param password  明文密码 
     * @param salt 盐值 
     * @return 
     */  
    public static byte[] hashPassword(String password, byte[] salt) {
    	try {
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, PBKDF2_ITERATIONS, HASH_BIT_SIZE);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
            return secretKeyFactory.generateSecret(keySpec).getEncoded();
    	} catch (Exception e) {
			logger.error("生成密文 异常", e);
			throw new ServerException("服务器异常");
    	}
    }

    /** 
     * 生成密文 
     *  
     * @param password  明文密码 
     * @param password 盐值
     * @return 
     */  
    public static byte[] hashPassword(String password) {
    	return hashPassword(password, HASH_BIT_SIZE);
    }
    
    /** 
     * 生成密文 
     *  
     * @param password  明文密码 
     * @param length 盐值
     * @return 
     */  
    public static byte[] hashPassword(String password, int length) {
    	byte[] salt = PBKDF2Utils.createKey(512);
    	
    	try {
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, PBKDF2_ITERATIONS, length);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
            return secretKeyFactory.generateSecret(keySpec).getEncoded();
    	} catch (Exception e) {
			logger.error("生成密文 异常", e);
			throw new ServerException("服务器异常");
    	}
    }
    
}
