package com.bubble.user.service;


import com.bubble.user.dto.user.UserDto;

public interface JWTService {

    /**
     * 创建一个用户JWT Token。
     * @return
     */
    String createUserToken(UserDto userDto);

    /**
     * 从jwt中解析用户ID
     * @param token
     * @return
     */
    Long parseUserId(String token);
}
