package com.bubble.user.service;

import com.bubble.user.dto.social.SocialPlatformOAuthDTO;
import com.bubble.user.dto.user.LoginResultDto;
import com.bubble.user.dto.user.PhoneLoginDto;
import com.bubble.user.dto.user.UserDto;

public interface SessionService {

    /**
     * 通过第三方平台帐号注册、登陆
     * @param socialPlatformDTO 第三方帐号用户信息
     * @return
     */
    UserDto loginByOAuth(SocialPlatformOAuthDTO socialPlatformDTO);


    /**
     * 使用手机登陆系统
     * @param phoneLogin 登陆参数
     * @return
     */
    LoginResultDto loginByPhone(PhoneLoginDto phoneLogin) ;

}
