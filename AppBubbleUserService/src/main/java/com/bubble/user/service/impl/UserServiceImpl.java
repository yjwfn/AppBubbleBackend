package com.bubble.user.service.impl;

import com.bubble.common.exception.biz.BizRuntimeException;
import com.bubble.common.exception.biz.ServiceStatus;
import com.bubble.common.snowflake.SequenceGenerator;
import com.bubble.common.utils.PhoneUtils;
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
import java.util.Optional;

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
    public UserDto bindPhone(Long userId, PhoneRegistryDto phoneRegistry) {

        if(phoneRegistry == null){
            throw BizRuntimeException.from(ServiceStatus.BAT_PARAMS, "PhoneRegistryDto cannot be null.");
        }

        if (userId == null) {
            throw BizRuntimeException.from(ServiceStatus.BAT_PARAMS, "userId cannot be null.");
        }


        String phoneExt = phoneRegistry.getPhoneExt();
        String phone = phoneRegistry.getPhone();
        String password = phoneRegistry.getPassword();
        String token = phoneRegistry.getToken();
        String code = phoneRegistry.getVerificationCode();

        if(Strings.isNullOrEmpty(phone)){
            throw BizRuntimeException.from(ServiceStatus.BAT_PARAMS, "The Phone field must not be null.");
        }

        if(Strings.isNullOrEmpty(password)){
            throw BizRuntimeException.from(ServiceStatus.BAT_PARAMS, "The Password field must not be null.");
        }


        if(Strings.isNullOrEmpty(token)){
            throw BizRuntimeException.from(ServiceStatus.BAT_PARAMS, "The Token field must not be null.");
        }

        if(Strings.isNullOrEmpty(code)){
            throw BizRuntimeException.from(ServiceStatus.BAT_PARAMS, "The verification field code must not be null.");
        }

        SmsMessageProto.SmsToken smsToken = SmsMessageProto.SmsToken
                .newBuilder()
                .setToken(token)
                .build();

        SmsMessageProto.SmsRecord smsRecord = smsServiceBlockingStub.findRecordByToken(smsToken);
        if(smsRecord == null){
            throw BizRuntimeException.from(ServiceStatus.BAT_PARAMS, "Not found verification code for phone");
        }


        if (!Objects.equal(smsRecord.getCode(), code)) {
            throw BizRuntimeException.from(ServiceStatus.BAT_PARAMS, "The verification is't correct.");
        }

        boolean isExists = userDao.countByPhoneExtAndPhone(phoneExt, phone) > 0;
        if(isExists){
            throw BizRuntimeException.from(ServiceStatus.ALREADY_EXISTS, "The phone already exists.");
        }

        isExists = userDao.findById(userId) != null;
        if(!isExists){
            throw BizRuntimeException.from(ServiceStatus.NOT_FOUND, "Not found user with id #" + userId);
        }

        UserEntity userEntity = userDao.findById(userId).orElseGet(null);
        userEntity.setPhone(phone);
        userEntity.setPhoneExt(phoneExt);

        if (!Strings.isNullOrEmpty(password)) {
            String salt = PasswordUtils.salt();
            String encryptedPassword = PasswordUtils.encrypt(password, salt);
            userEntity.setPasswordSalt(salt);
            userEntity.setPassword(encryptedPassword);
        }

        userDao.insertUser(userEntity);

        UserDto userDTO = new UserDto();
        BeanUtils.copyProperties(userEntity, userDTO);
        return userDTO;
    }

    @Override
    public UserDto updatePassword(UpdatePassword updatePassword) {

    }

    @Override
    public UserDto resetPassword(ResetPassword resetPassword) {
        if(resetPassword == null){
            throw BizRuntimeException.from(ServiceStatus.BAT_PARAMS, "Bad params");
        }

        String phoneExt = resetPassword.getPhoneExt();
        String phone = resetPassword.getPhone();
        String token = resetPassword.getToken();
        String verificationCode = resetPassword.getVerificationCode();
        String newPassword = resetPassword.getNewPassword();
        String confirmationPassword = resetPassword.getConfirmPassword();

        if (Strings.isNullOrEmpty(phone) || !PhoneUtils.isPhoneNumber(phone, false)) {
            throw BizRuntimeException.from(ServiceStatus.BAT_PARAMS, "Bad phone.");

        }

        if(Strings.isNullOrEmpty(token)){
            throw BizRuntimeException.from(ServiceStatus.BAT_PARAMS, "The token field must not be null.");
        }

        if(Strings.isNullOrEmpty(verificationCode)){
            throw BizRuntimeException.from(ServiceStatus.BAT_PARAMS, "The verification field code must not null.");
        }

        if(Strings.isNullOrEmpty(newPassword)){
            throw BizRuntimeException.from(ServiceStatus.BAT_PARAMS, "The password field code must not null.");
        }

        if(Strings.isNullOrEmpty(confirmationPassword)){
            throw BizRuntimeException.from(ServiceStatus.BAT_PARAMS, "The confirmation password field  must not null.");

        }

        if(!Objects.equal(newPassword, confirmationPassword)){
            throw BizRuntimeException.from(ServiceStatus.BAT_PARAMS, "The new password does not match the confirmation password.");

        }

        SmsMessageProto.SmsToken smsToken = SmsMessageProto.SmsToken
                .newBuilder()
                .setToken(token)
                .build();

        SmsMessageProto.SmsRecord smsRecord = smsServiceBlockingStub.findRecordByToken(smsToken);
        if(smsRecord == null){
            throw BizRuntimeException.from(ServiceStatus.BAT_PARAMS, "The verification code does not exists..");
        }

        if (!Objects.equal(verificationCode, smsRecord.getCode())) {
            throw BizRuntimeException.from(ServiceStatus.BAT_PARAMS, "The verification code does not exists.");
        }

        Optional<UserEntity> userEntityOptional = userDao.findByPhoneExtAndPhone(phoneExt, phone);
        if(!userEntityOptional.isPresent()){
            throw BizRuntimeException.from(ServiceStatus.BAT_PARAMS, "Cannot find user with phone #" + phone);
        }

        UserEntity userEntity = userEntityOptional.orElse(null);
        String salt = PasswordUtils.salt();
        String encryptedPassword = PasswordUtils.encrypt(newPassword, salt);
        userEntity.setPasswordSalt(salt);
        userEntity.setPassword(encryptedPassword);
//        userDao.update(userEntity);
        UserDto userDTO = new UserDto();
        BeanUtils.copyProperties(userEntity, userDTO);
        return userDTO;
    }

    @Override
    public List<UserDto> matchContacts(List<UserContactDTO> userContacts, int page, int size) {
        return null;
    }
}
