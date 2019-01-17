package com.bubble.user.dto.user;


/**
 * Created by yjwfn on 2018/1/26.
 */
public class PhoneRegistry {


    private String phoneExt;
    private String phone;
    private String password;
    private String token;
    private String verificationCode;


    public PhoneRegistry(String phoneExt, String phone, String password, String token, String verificationCode) {
        this.phoneExt = phoneExt;
        this.phone = phone;
        this.password = password;
        this.token = token;
        this.verificationCode = verificationCode;
    }

    public PhoneRegistry() {



    }

    public String getPhoneExt() {
        return phoneExt;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public String getVerificationCode() {
        return verificationCode;
    }
}
