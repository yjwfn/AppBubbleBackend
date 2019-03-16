package com.bubble.user.relation.dao;

import com.bubble.user.relation.entity.UserRelationEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRelationDao {

    /**
     * 查询已经关注{@code  userId}的信息。
     * @param userId
     * @return
     */
    List<UserRelationEntity> findFollowerByUserId(Long userId);

    /**
     * 查询{@code followerId}关注的人。
     * @param followerId
     * @return
     */
    List<UserRelationEntity> findUserByFollowerId(Long followerId);


    /**
     * 创建一条关注记录
     * @return
     */
    void createRelation(UserRelationEntity userRelationEntity);


    /**
     * 取消关注
     * @param userId 被关注的人
     * @param followerId 关注者
     */
    void deleteRelation(Long userId, Long followerId);

    /**
     * 判断关系是否存在
     * @param userId
     * @param followerId
     * @return
     */
    boolean isExists(Long userId, Long followerId );
}
