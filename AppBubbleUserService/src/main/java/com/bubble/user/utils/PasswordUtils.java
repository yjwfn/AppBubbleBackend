package com.bubble.user.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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

        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            secureRandom.setSeed(salt.getBytes());

            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(128, secureRandom);
            SecretKey secretKey = generator.generateKey();

            Cipher cipher = Cipher.getInstance(generator.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] finalBytes = cipher.doFinal(password.getBytes());
            return Hex.encodeHexString(finalBytes);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }

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
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(unencryptedPassword.getBytes());
            secureRandom.setSeed(salt.getBytes());

            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128, secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();

            Cipher cipher = Cipher.getInstance(keyGenerator.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] textBytes = cipher.doFinal( Hex.decodeHex(encryptedPassword.toCharArray()) );
            return new String(textBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | DecoderException e) {
            throw new IllegalStateException(e);
        }

    }


    public static void main(String[] args){
        //eddc17b26f2834ed457ca6fb115eab0e

        String salt = "f6d0a44be96c1453";
        String password = "18817096723";
        String encryptedPassword = encrypt(password, salt);
        System.out.println(encryptedPassword);
        System.out.println(decrypt(password, encryptedPassword, salt));
    }

}
