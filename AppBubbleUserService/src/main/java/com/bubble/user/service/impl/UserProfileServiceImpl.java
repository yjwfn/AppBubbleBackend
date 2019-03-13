package com.bubble.user.service.impl;

import com.bubble.common.exception.biz.BizRuntimeException;
import com.bubble.common.exception.biz.ServiceStatus;
import com.bubble.user.dao.UserDao;
import com.bubble.user.dto.user.UserDetailDto;
import com.bubble.user.dto.user.UserDto;
import com.bubble.user.entity.UserEntity;
import com.bubble.user.service.UserProfileService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService{

    @Autowired
    private UserDao userDao;

    @Override
    public UserDto updateAvatarUrl(Long userId, String url) {
        return null;
    }

    @Override
    public UserDetailDto getUserDetail(Long myUserId, Long otherUserId) {
        if (myUserId == null) {
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "User id must not be null.");
        }

        if (otherUserId == null) {
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "User id must not be null.");
        }

        UserEntity userEntity = userDao.findById(otherUserId);
        if (userEntity == null) {
            throw BizRuntimeException.from(ServiceStatus.NOT_FOUND, "Not found user.");
        }

        UserDto userDto = new UserDto();

        return null;
    }

    @Override
    public UserDto updateUserProfile(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto getUserProfile(Long userId) {
        if (userId == null) {
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "User id must not be null.");
        }

        if (userDao.countById(userId) <= 0) {
            throw BizRuntimeException.from(ServiceStatus.NOT_FOUND, "Not found user.");
        }


        UserEntity userEntity = userDao.findById(userId);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userEntity, userDto);
        return userDto;
    }
}
