package com.xxgame.admin.web.util;

import com.xxgame.admin.web.exception.ServerException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 简单的md5 签名工具类，如需要安全的签名用 PBKDF2Utils
 * @author gil
 *
 */
public class Md5DigestUtils {

    private static final int DEFAULT_ITERATIONS = 1;

    private static final char[] HEX_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * md5签名
     * @param content
     * @return
     */
    public static String digest(String content) {
        byte[] hashed = digest(content.getBytes(), null, DEFAULT_ITERATIONS);
        return encodeHex(hashed);
    }

    /**
     * md5签名
     * @param content
     * @param salt
     * @return
     */
    public static String digest(String content, String salt) {
        byte[] hashed = digest(content.getBytes(), salt.getBytes(), DEFAULT_ITERATIONS);
        return encodeHex(hashed);
    }

    /**
     * md5签名
     * @param content
     * @param salt
     * @return
     */
    public static String digest(byte[] content, byte[] salt) {
        byte[] hashed = digest(content, salt, DEFAULT_ITERATIONS);
        return encodeHex(hashed);
    }

    /**
     *
     * @param bytes
     * @param salt
     * @param hashIterations
     * @return
     */
    public static byte[] digest(byte[] bytes, byte[] salt, int hashIterations) {
        MessageDigest digest = getDigest();
        if (salt != null) {
            digest.reset();
            digest.update(salt);
        }
        byte[] hashed = digest.digest(bytes);
        int iterations = hashIterations - 1; //already hashed once above
        //iterate remaining number:
        for (int i = 0; i < iterations; i++) {
            digest.reset();
            hashed = digest.digest(hashed);
        }

        return hashed;
    }

    /**
     * hex 编码
     * @param bytes
     * @return
     */
    private static String encodeHex(byte[] bytes) {
        char chars[] = new char[32];
        for (int i = 0; i < chars.length; i = i + 2) {
            byte b = bytes[i / 2];
            chars[i] = HEX_CHARS[(b >>> 0x4) & 0xf];
            chars[i + 1] = HEX_CHARS[b & 0xf];
        }

        return new String(chars);
    }

    /**
     * Returns the JDK MessageDigest instance to use for executing the hash.
     */
    protected static MessageDigest getDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new ServerException("错误的签名算法。");
        }
    }

}

