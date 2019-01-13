package com.bubble.sms.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;

/**
*
*/

@RedisHash
public class SmsRecordEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //手机号
    private String phone ;
    //手机号区号
    private String phoneExt;
    //短信验证码
    private String code ;
    //短信TOKEN
    @Id
    private String token ;
    //记录短信发送时间
    private Long sendTime ;
    //失效时间
    @TimeToLive
    private Integer timeToLive = 6000;

    public String getPhone(){
        return this.phone;
    }

    public void setPhone(String  phone){
        this.phone = phone;
    }
        
    public String getCode(){
        return this.code;
    }

    public void setCode(String  code){
        this.code = code;
    }
        
    public String getToken(){
        return this.token;
    }

    public void setToken(String  token){
        this.token = token;
    }

    public Long getSendTime(){
        return this.sendTime;
    }

    public void setSendTime(Long  sendTime){
        this.sendTime = sendTime;
    }

    public void setTimeToLive(Integer timeToLive) {
        this.timeToLive = timeToLive;
    }

    public String getPhoneExt() {
        return phoneExt;
    }

    public void setPhoneExt(String phoneExt) {
        this.phoneExt = phoneExt;
    }
}

