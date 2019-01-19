package com.bubble.user.service.impl;

import com.bubble.common.exception.biz.BizRuntimeException;
import com.bubble.common.snowflake.SequenceGenerator;
import com.bubble.sms.grpc.message.SmsMessageProto;
import com.bubble.sms.grpc.service.SmsServiceGrpc;
import com.bubble.user.dao.UserDao;
import com.bubble.user.dto.user.*;
import com.bubble.user.entity.UserEntity;
import com.bubble.user.enums.UserServiceStatus;
import com.bubble.user.event.UserRegistry;
import com.bubble.user.service.UserService;
import com.bubble.user.utils.PasswordUtils;
import com.bubble.user.utils.UserUtils;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    ApplicationEventPublisher publishEvent;

    @Autowired
    private  SmsServiceGrpc.SmsServiceBlockingStub smsServiceBlockingStub;

    @Override
    public boolean isExists(String phoneExt, String phone) {
        return userDao.countByPhoneExtAndPhone(phoneExt, phone) > 0;
    }

    @Override
    public UserDto registerWithPhone(PhoneRegistryDto phoneRegistryDto) {

        if(phoneRegistryDto == null){
            throw BizRuntimeException
                    .from(UserServiceStatus.BAT_PARAMS, "Cannot not to create user with null.");
        }


        String phoneExt = phoneRegistryDto.getPhoneExt();
        String phone = phoneRegistryDto.getPhone();
        String password = phoneRegistryDto.getPassword();
        String token = phoneRegistryDto.getToken();
        String code = phoneRegistryDto.getVerificationCode();

        if(Strings.isNullOrEmpty(phone) || Strings.isNullOrEmpty(password) || Strings.isNullOrEmpty(token) || Strings.isNullOrEmpty(code)){
            throw BizRuntimeException.from(UserServiceStatus.BAT_PARAMS, "Cannot not to create user, please check your params.");
        }

        SmsMessageProto.SmsToken smsToken = SmsMessageProto.SmsToken.newBuilder()
                .setToken(token)
                .build();



        SmsMessageProto.SmsRecord smsRecord = smsServiceBlockingStub.findRecordByToken(smsToken);smsRecord.isInitialized();
//        if(smsRecord == null){
//            throw BizRuntimeException
//                    .from(UserServiceStatus.BAT_PARAMS, "Invalid ");
//
//        }
        /*
            手机号或验证码未匹配。
         */
        if(!(Objects.equal(smsRecord.getCode(), code)
                && Objects.equal(smsRecord.getPhone(), phone)
                && Objects.equal(smsRecord.getPhoneExt(), phoneExt))){

            throw BizRuntimeException
                    .from(UserServiceStatus.INVALID_VERIFICATION_CODE, "Invalid verification code.");
        }





        boolean isExists = isExists(phoneExt, phone);
        if(isExists){
            throw BizRuntimeException
                    .from(UserServiceStatus.ALREADY_EXISTS, "The phone has been registry.");

        }


        UserEntity userEntity = new UserEntity();
        userEntity.setId(sequenceGenerator.next());
        userEntity.setPhone(phone);
        userEntity.setPhoneExt(phoneExt);
        userEntity.setNickName(UserUtils.randonNickname());

        String salt = PasswordUtils.salt();
        String encryptedPassword = PasswordUtils.encrypt(password, salt);
        userEntity.setPassword(encryptedPassword);
        userEntity.setPasswordSalt(salt);
        userDao.insertUser(userEntity);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userEntity, userDto);
        publishEvent.publishEvent(new UserRegistry(this, userDto));
        return userDto;
    }

    @Override
    public UserDto bindPhone(Long userId, PhoneRegistryDto phoneRegistryDto) {
        return null;
    }

    @Override
    public UserDto updatePassword(UpdatePassword updatePassword) {
        return null;
    }

    @Override
    public UserDto resetPassword(ResetPassword resetPassword) {
        return null;
    }

    @Override
    public List<UserDto> matchContacts(List<UserContactDTO> userContacts, int page, int size) {
        return null;
    }
}
