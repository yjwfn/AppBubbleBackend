package com.bubble.user.relation.service.impl;

import com.bubble.common.exception.biz.BizRuntimeException;
import com.bubble.common.exception.biz.ServiceStatus;
import com.bubble.user.relation.dao.UserRelationDao;
import com.bubble.user.relation.dto.UserRelationDto;
import com.bubble.user.relation.entity.UserRelationEntity;
import com.bubble.user.relation.service.UserRelationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserRelationServiceImpl implements UserRelationService
{

    @Autowired
    UserRelationDao userRelationDao;

    @Override
    public List<UserRelationDto> findFollowerByUserId(Long userId) {
        if (userId == null) {
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST , "userId == null");
        }

        List<UserRelationEntity> entities = userRelationDao.findFollowerByUserId(userId);


        return entities.stream()
                .map(userRelationEntity -> {
                    UserRelationDto userRelationDto = new UserRelationDto();
                    BeanUtils.copyProperties(userRelationEntity, userRelationDto);
                    return userRelationDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<UserRelationDto> findUserByFollowerId(Long followerId) {
        if (followerId == null) {
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST , "followerId == null");
        }


        List<UserRelationEntity> entities = userRelationDao.findUserByFollowerId(followerId);


        return entities.stream()
                .map(userRelationEntity -> {
                    UserRelationDto userRelationDto = new UserRelationDto();
                    BeanUtils.copyProperties(userRelationEntity, userRelationDto);
                    return userRelationDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void createRelation(UserRelationDto userRelationDto) {
        if (userRelationDto == null) {
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST , "null == userRelationDto");
        }

        if (userRelationDto.getUserId() == null || userRelationDto.getFollowerId() == null) {
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST , "userId and followerId is required.");
        }

        UserRelationEntity userRelationEntity = new UserRelationEntity();
        BeanUtils.copyProperties(userRelationDto, userRelationEntity);
        userRelationDao.createRelation(userRelationEntity);
    }

    @Override
    public void deleteRelation(Long userId, Long followerId) {
        if (userId == null || followerId == null) {
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST , "userId and followerId is required.");
        }

        userRelationDao.deleteRelation(userId, followerId);
    }
}
