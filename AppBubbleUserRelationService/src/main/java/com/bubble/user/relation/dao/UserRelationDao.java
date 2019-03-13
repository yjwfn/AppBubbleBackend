package com.bubble.user.relation.dao;

import com.bubble.user.relation.entity.UserRelationEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRelationDao {

    /**
     * 查询已经关注{@code  userId}的信息。
     * @param followingId
     * @return
     */
    List<UserRelationEntity> findFollowerByUserId(Long followingId);

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
     * @param followingId 被关注的人
     * @param followerId 关注者
     */
    void deleteRelation(Long followingId, Long followerId);
}
