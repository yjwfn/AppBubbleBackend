package com.bubble.sms.dto;

public class SmsRecord {

    private String phoneExt;
    private String phone ;
    //短信验证码
    private String code ;
    //短信TOKEN
    private String token ;
    //记录短信发送时间
    private Long sendTime ;


    public SmsRecord(String phoneExt, String phone, String code, String token, Long sendTime) {
        this.phoneExt = phoneExt;
        this.phone = phone;
        this.code = code;
        this.token = token;
        this.sendTime = sendTime;
    }

    public String getPhone() {
        return phone;
    }

    public String getCode() {
        return code;
    }

    public String getToken() {
        return token;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public String getPhoneExt() {
        return phoneExt;
    }
}
