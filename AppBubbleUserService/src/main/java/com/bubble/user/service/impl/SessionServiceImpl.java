package com.bubble.user.service.impl;

import com.bubble.common.exception.biz.BizRuntimeException;
import com.bubble.common.exception.biz.ServiceStatus;
import com.bubble.user.dao.UserDao;
import com.bubble.user.dto.social.SocialPlatformOAuthDTO;
import com.bubble.user.dto.user.LoginResultDto;
import com.bubble.user.dto.user.PhoneLoginDto;
import com.bubble.user.dto.user.UserDto;
import com.bubble.user.entity.UserEntity;
import com.bubble.user.enums.UserServiceStatus;
import com.bubble.user.service.JWTService;
import com.bubble.user.service.SessionService;
import com.bubble.user.utils.PasswordUtils;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private JWTService jwtService;

    @Override
    public UserDto loginByOAuth(SocialPlatformOAuthDTO socialPlatformDTO) {
        return null;
    }

    @Override
    public LoginResultDto loginByPhone(PhoneLoginDto phoneLogin) {
        if(phoneLogin == null){
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "Invalid params");
        }

        String phoneExt = phoneLogin.getPhoneExt();
        String phone = phoneLogin.getPhone();
        String password = phoneLogin.getPassword();

        if(Strings.isNullOrEmpty(phone)){
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "Your Phone is empty.");
        }

        if (Strings.isNullOrEmpty(password)) {
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "Your Password is empty.");
        }

        UserEntity userEntity = userDao.findUserByPhoneWhenLogin(phoneExt, phone);
        if (userEntity == null) {
            throw BizRuntimeException.from(ServiceStatus.NOT_FOUND, "Not found user.");
        }


        String decoded = PasswordUtils.decrypt(password, userEntity.getPassword(), userEntity.getPasswordSalt());
        if(!Objects.equal(decoded, password)){
            throw BizRuntimeException.from(UserServiceStatus.INCORRECT_PASSWORD, "Incorrect password.");
        }

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userEntity, userDto);
        String token = jwtService.createUserToken(userDto);

        LoginResultDto loginResultDto = new LoginResultDto();
        loginResultDto.setToken(token);
        BeanUtils.copyProperties(userEntity, loginResultDto);
        return loginResultDto;
    }
}
