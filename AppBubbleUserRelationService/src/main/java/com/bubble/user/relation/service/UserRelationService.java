package com.bubble.user.relation.service;

import com.bubble.user.relation.dto.UserRelationDto;
import com.bubble.user.relation.entity.UserRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserRelationService {

    /**
     * 查询已经关注{@code  userId}的信息。
     * @param userId
     * @return
     */
    List<UserRelationDto> findFollowerByUserId(Long userId);

    /**
     * 查询{@code followerId}关注的人。
     * @param followerId
     * @return
     */
    List<UserRelationDto> findUserByFollowerId(Long followerId);


    /**
     * 创建一条关注记录
     * @return
     */
    void createRelation(UserRelationDto userRelationDto);


    /**
     * 取消关注
     * @param userId 被关注的人
     * @param followerId 关注者
     */
    void deleteRelation(Long userId, Long followerId);
}
