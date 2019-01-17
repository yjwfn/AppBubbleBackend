package com.bubble.user.service;

import com.bubble.user.dto.user.UserDTO;
import com.bubble.user.dto.user.UserDetailDTO;

public interface UserProfileService {

    /**
     * 上传用户头像
     * @param userId
     * @param url
     * @return
     */
    UserDTO updateAvatarUrl(Long userId, String url)    ;

    /**
     * 获取用户详情
     *
     * @param myUserId    当前用户ID
     * @param otherUserId 他人用户ID
     * @return
     */
    UserDetailDTO getUserDetail(Long myUserId, Long otherUserId)    ;



    /**
     * 更新用户信息
     * @param userDTO
     * @return
     */
    UserDTO updateUserProfile(UserDTO userDTO)      ;

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    UserDTO getUserProfile(Long userId) ;

}
