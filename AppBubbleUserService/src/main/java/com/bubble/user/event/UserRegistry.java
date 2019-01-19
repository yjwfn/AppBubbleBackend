package com.bubble.user.event;

import com.bubble.user.dto.user.UserDto;
import org.springframework.context.ApplicationEvent;

public class UserRegistry extends ApplicationEvent{

    private UserDto userDto;


    public UserRegistry(Object source, UserDto userDto) {
        super(source);
        this.userDto = userDto;
    }

    public UserDto getUserDto() {
        return userDto;
    }
}
