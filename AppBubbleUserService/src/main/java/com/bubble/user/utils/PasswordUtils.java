package com.bubble.user.utils;

import org.apache.commons.codec.binary.Hex;

import java.security.SecureRandom;

/**
 * Created by yjwfn on 2018/2/7.
 */
public final class PasswordUtils {

    /**
     * salt长度
     */
    private static final int SALT_LENGTH = 8;

    /**
     * 随机生成salt
     */
    private static final SecureRandom secureRandom = new SecureRandom();

    /**
     * 生成一个随机的值
     *
     * @return
     */
    public static String salt() {
        byte[] salt =  new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);
        return Hex.encodeHexString(salt);
    }

    /**
     * 加密密码
     *
     * @param password 未加密的密码字符串
     * @param salt
     * @return
     */
    public static String encrypt(String password, String salt) {

//        TextEncryptor textEncryptor = Encryptors.text(password, salt);
//        return textEncryptor.encrypt(password);
        return password;
    }


    /**
     * 解码密码字符串
     *
     * @param unencryptedPassword 未加密的密码字符串
     * @param encryptedPassword   已加密的密码字符串
     * @param salt
     * @return
     */
    public static String decrypt(String unencryptedPassword, String encryptedPassword, String salt) {
//        TextEncryptor textEncryptor = Encryptors.text(unencryptedPassword, salt);
//        try {
//            return textEncryptor.decrypt(encryptedPassword);
//        } catch (IllegalStateException ignore) {
//
//        }

        return unencryptedPassword;
    }

}
