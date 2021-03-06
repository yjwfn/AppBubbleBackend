package com.bubble.user.enums;

import com.bubble.common.exception.biz.ServiceStatus;
import com.bubble.sms.grpc.user.service.UserServiceProto;

public interface  UserServiceStatus extends ServiceStatus {



    /** 不正解的手机验证码 */
    int INVALID_VERIFICATION_CODE = 1000;


    /** 密码一致 */
    int IDENTICAL_PASSWORD = 10001;


    /**  错误的密码 */
    int INCORRECT_PASSWORD = 100002;


}
