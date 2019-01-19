package com.bubble.user.service;

import com.bubble.user.dto.user.UserDto;
import com.bubble.user.dto.user.UserDetailDto;

public interface UserProfileService {

    /**
     * 上传用户头像
     * @param userId
     * @param url
     * @return
     */
    UserDto updateAvatarUrl(Long userId, String url)    ;

    /**
     * 获取用户详情
     *
     * @param myUserId    当前用户ID
     * @param otherUserId 他人用户ID
     * @return
     */
    UserDetailDto getUserDetail(Long myUserId, Long otherUserId)    ;



    /**
     * 更新用户信息
     * @param userDto
     * @return
     */
    UserDto updateUserProfile(UserDto userDto)      ;

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    UserDto getUserProfile(Long userId) ;

}
