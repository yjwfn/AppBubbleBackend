package com.bubble.sms.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class SmsUtils {

    public static String createToken(String phoneExt, String mobile, String verificationCode, long sendTime) {
        String seq = mobile + verificationCode + String.valueOf(sendTime);
        return DigestUtils.md5Hex(seq);
    }

    public static String createVerificationCode(){
        //验证码
        StringBuilder vcode = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            vcode.append((int) (Math.random() * 10));
        }
        return vcode.toString();
    }
}
