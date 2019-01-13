package com.bubble.sms.service;

import com.bubble.sms.dto.SmsRecord;
import org.springframework.lang.Nullable;


/**
 * Created by yjwfn on 2018/1/22.
 */
public interface SmsService {


    /**
     * 发送一条用户手号注册验证码
     * @param mobile 接收短信手机号
     * @return  接收此次发送短信的TOKEN
     */
    String sendRegistrySms(String phoneExt, String mobile)  ;

    /**
     * 根据{@code mobile}和{@code token}查找短信发送记录
     * @param mobile    手机号
     * @param token 发送难码后返回的token
     * @return 反回null，如果没有找到或已经过期
     */
    SmsRecord findRecordByPhoneExtAndPhoneAndToken(@Nullable String phoneExt, String mobile, String token)  ;

}
