package com.xxgame.admin.web.util;

import com.xxgame.admin.web.exception.ServerException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA hash工具类
 */
public class ShaDigestUtils {

    /**
     * sha256
     * @param bytes
     * @return
     */
    public static byte[] sha256(byte[] bytes) {
        return digest(bytes, null, 1, "SHA-256");
    }

    /**
     * sha 256
     * @param bytes
     * @param salt
     * @param iterations
     * @return
     */
    public static byte[] sha256(byte[] bytes, byte[] salt, int iterations) {
        return digest(bytes, salt, iterations,"SHA-256");
    }

    /**
     * sha
     * @param bytes
     * @return
     */
    public static byte[] sha512(byte[] bytes) {
        return digest(bytes, null, 1,"SHA-512");
    }

    /**
     *
     * @param bytes
     * @param salt
     * @param iterations
     * @param algorithm
     * @return
     */
    private static byte[] digest(byte[] bytes, byte[] salt, int iterations, String algorithm) {
        try {
            MessageDigest digester = MessageDigest.getInstance(algorithm);
            if (salt != null) {
                digester.reset();
                digester.update(salt);
            }

            byte[] hashed = digester.digest(bytes);
            int times = iterations - 1; //already hashed once above
            // iterate remaining times:
            for (int i = 0; i < times; i++) {
                digester.reset();
                hashed = digester.digest(hashed);
            }

            return hashed;
        } catch (NoSuchAlgorithmException e) {
            throw new ServerException("错误的签名算法。");
        }
    }
}
