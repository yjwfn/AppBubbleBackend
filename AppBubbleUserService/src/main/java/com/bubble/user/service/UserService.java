package com.bubble.user.service;

import com.bubble.user.dto.user.*;

import java.util.List;

/**
 *用户Service
 */
public interface UserService {


    /**
     * 检查是否存在手机号为{@code phone}的用户
     * @return TRUE如果用户存在
      */
    boolean isExists(String phoneExt, String phone)    ;



    /**
     * 使用{@code phone}注册一个新的用户。
     * @param phoneRegistryDto 手机注册参数
     * @return
     */
    UserDto registerWithPhone(PhoneRegistryDto phoneRegistryDto)    ;
  ;


    /**
     * 绑定手机
     * @param userId
     * @param phoneRegistryDto
     * @return
      */
    UserDto bindPhone(Long userId, PhoneRegistryDto phoneRegistryDto)    ;

    /**
     * 更新用户密码
     * @param updatePassword
     * @return
      */
    UserDto updatePassword(UpdatePassword updatePassword)    ;

    /**
     * 重置用户密码
     * @param resetPassword
     * @return
      */
    UserDto resetPassword(ResetPassword resetPassword)    ;


    /**
     * 匹配用户手机通讯录
     * @param userContacts
     * @return
      */
    List<UserDto> matchContacts(List<UserContactDTO> userContacts, int page, int size)    ;


}
