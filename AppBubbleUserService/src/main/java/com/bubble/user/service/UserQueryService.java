package com.bubble.user.service;

import com.bubble.user.dto.user.UserDto;
import com.bubble.user.dto.user.UserQueryDTO;

import java.util.List;


public interface UserQueryService {

    /**
     * 分页查询
     * @param queryDTO
     * @return
     */
    List<UserDto> queryUsers(UserQueryDTO queryDTO)    ;


}
