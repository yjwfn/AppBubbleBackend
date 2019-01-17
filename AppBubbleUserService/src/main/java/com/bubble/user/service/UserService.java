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
    boolean isExistsByPhone(String phone)    ;



    /**
     * 使用{@code phone}注册一个新的用户。
     * @param phoneRegistry 手机注册参数
     * @return
     */
    UserDTO registerByPhone(PhoneRegistry phoneRegistry)    ;
  ;


    /**
     * 绑定手机
     * @param userId
     * @param phoneRegistry
     * @return
      */
    UserDTO bindPhone(Long userId, PhoneRegistry phoneRegistry)    ;

    /**
     * 更新用户密码
     * @param updatePassword
     * @return
      */
    UserDTO updatePassword(UpdatePassword updatePassword)    ;

    /**
     * 重置用户密码
     * @param resetPassword
     * @return
      */
    UserDTO resetPassword(ResetPassword resetPassword)    ;


    /**
     * 匹配用户手机通讯录
     * @param userContacts
     * @return
      */
    List<UserDTO> matchContacts(List<UserContactDTO> userContacts, int page, int size)    ;


}
