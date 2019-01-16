package com.bubble.user.dao;


import com.bubble.user.entity.UserEntity;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

/**
*用户Repository
*/
public interface UserDao {
    /**
     * 查找手机区号@{code phoneExt}的手机号{@code phone}的数量
     * @param phone 手机号
     * @return
     */
    long countByPhoneExtAndPhone( String phoneExt, String phone);

    /**
     * 统计用户数量
     * @param userId
     * @return
     */
    long countById(Long userId);

    /**
     * 通过呢称搜索用户
     * @param nickName
     * @return
     */
    List<UserEntity> findByNickNameLike( String nickName);


    /**
     * 通过ID搜索用户
     * @param id
     * @return
     */
    Optional<UserEntity> findById(Long id);


    /**
     * 根据手机号查找用户
     * @param phone
     * @return
     */
    Optional<UserEntity> findByPhoneExtAndPhone(@Nullable String ext, String phone);



    /**
     * 查看匹配{@code phone}的手机用户
     * @param phone
     * @return
     */
    List<UserEntity> findByPhoneIn(List<String> phone);


    /**
     * 获取多个id用户信息
     * @param ids
     * @return
     */
    List<UserEntity> findByIdIn(List<Long> ids);


}
