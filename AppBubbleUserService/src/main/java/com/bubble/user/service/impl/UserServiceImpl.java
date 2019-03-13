package com.bubble.user.service.impl;

import com.bubble.common.ResponseProto;
import com.bubble.common.exception.biz.BizRuntimeException;
import com.bubble.common.exception.biz.ServiceStatus;
import com.bubble.common.snowflake.SequenceGenerator;
import com.bubble.common.utils.PhoneUtils;
import com.bubble.common.utils.ResponseHelper;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class.getName());

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
                    .from(UserServiceStatus.BAD_REQUEST, "Cannot not to create user with null.");
        }


        String phoneExt = phoneRegistryDto.getPhoneExt();
        String phone = phoneRegistryDto.getPhone();
        String password = phoneRegistryDto.getPassword();
        String token = phoneRegistryDto.getToken();
        String code = phoneRegistryDto.getVerificationCode();

        if(Strings.isNullOrEmpty(phone) || Strings.isNullOrEmpty(password) || Strings.isNullOrEmpty(token) || Strings.isNullOrEmpty(code)){
            throw BizRuntimeException.from(UserServiceStatus.BAD_REQUEST, "Cannot not to create user, please check your params.");
        }

        SmsMessageProto.SmsToken smsToken = SmsMessageProto.SmsToken.newBuilder()
                .setToken(token)
                .build();



        ResponseProto.Response response = smsServiceBlockingStub.findRecordByToken(smsToken);
        if(!ResponseHelper.isSuccessful(response)){
            throw BizRuntimeException.from(response);
        }

        SmsMessageProto.SmsRecord smsRecord = ResponseHelper.unpack(response, SmsMessageProto.SmsRecord.class);
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
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "PhoneRegistryDto cannot be null.");
        }

        if (userId == null) {
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "userId cannot be null.");
        }


        String phoneExt = phoneRegistry.getPhoneExt();
        String phone = phoneRegistry.getPhone();
        String password = phoneRegistry.getPassword();
        String token = phoneRegistry.getToken();
        String code = phoneRegistry.getVerificationCode();

        if(Strings.isNullOrEmpty(phone)){
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "The Phone field must not be null.");
        }

        if(Strings.isNullOrEmpty(password)){
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "The Password field must not be null.");
        }


        if(Strings.isNullOrEmpty(token)){
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "The Token field must not be null.");
        }

        if(Strings.isNullOrEmpty(code)){
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "The verification field code must not be null.");
        }


        SmsMessageProto.SmsToken smsToken = SmsMessageProto.SmsToken
                .newBuilder()
                .setToken(token)
                .build();

        ResponseProto.Response response = smsServiceBlockingStub.findRecordByToken(smsToken);
        ResponseHelper.checkResponse(response);

        SmsMessageProto.SmsRecord smsRecord = ResponseHelper.unpack(response, SmsMessageProto.SmsRecord.class);
        if(smsRecord == null){
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "Not found verification code for phone");
        }


        if (!Objects.equal(smsRecord.getCode(), code)) {
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "The verification is't correct.");
        }

        boolean isExists = userDao.countByPhoneExtAndPhone(phoneExt, phone) > 0;
        if(isExists){
            throw BizRuntimeException.from(ServiceStatus.ALREADY_EXISTS, "The phone already exists.");
        }

        isExists = userDao.findById(userId) != null;
        if(!isExists){
            throw BizRuntimeException.from(ServiceStatus.NOT_FOUND, "Not found user with id #" + userId);
        }

        UserEntity userEntity = userDao.findById(userId);
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
        if(updatePassword == null){
            throw BizRuntimeException.from(UserServiceStatus.BAD_REQUEST, "Bad params.");
        }


        Long userId = updatePassword.getUserId();
        String newPassword = updatePassword.getNewPassword();
        String confirmPassword = updatePassword.getConfirmPassword();
        String oldPassword = updatePassword.getOldPassword();

        if(userId == null){
            throw BizRuntimeException.from(UserServiceStatus.BAD_REQUEST, "User id within request must not be null.");

        }

        if(Strings.isNullOrEmpty(oldPassword)){
            throw BizRuntimeException.from(UserServiceStatus.BAD_REQUEST, "Old password must not be null.");

        }

        if(Strings.isNullOrEmpty(newPassword)){
            throw BizRuntimeException.from(UserServiceStatus.BAD_REQUEST, "Did you provide a new password for your account?");
        }

        if(Strings.isNullOrEmpty(confirmPassword)){
            throw BizRuntimeException.from(UserServiceStatus.BAD_REQUEST, "No confirmation password.");

        }

        if(!confirmPassword.equals(newPassword)){
            throw BizRuntimeException.from(UserServiceStatus.BAD_REQUEST, "The new password is not match with the confirmation password.");
        }

        UserEntity userEntity = userDao.findById(userId);
        if(userEntity == null){
            throw BizRuntimeException.from(UserServiceStatus.BAD_REQUEST, "Not found user with id" + userId);
        }


        String salt = userEntity.getPasswordSalt();
        String encryptedPassword = userEntity.getPassword();


        if (Strings.isNullOrEmpty(encryptedPassword) || Strings.isNullOrEmpty(salt)) {
            throw BizRuntimeException.from(UserServiceStatus.BAD_REQUEST, "Did  you set your password? Please bind your phone first.");
        }


        String decryptedPassword = PasswordUtils.decrypt(oldPassword, encryptedPassword, salt);
        if (!Objects.equal(oldPassword, decryptedPassword)) {
            throw BizRuntimeException.from(UserServiceStatus.BAD_REQUEST, "Incorrect old password.");
        }

        if(newPassword.equals(userEntity.getPassword())){
            throw BizRuntimeException.from(UserServiceStatus.IDENTICAL_PASSWORD, "No change.");
        }

        salt = PasswordUtils.salt();
        encryptedPassword = PasswordUtils.encrypt(newPassword, salt);
        UserEntity updateEntity = new UserEntity();
        updateEntity.setId(userEntity.getId());
        updateEntity.setPasswordSalt(salt);
        updateEntity.setPassword(encryptedPassword);
        userDao.updateUser(userEntity);
        UserDto userDTO = new UserDto();
        BeanUtils.copyProperties(userEntity, userDTO);
        return userDTO;
    }

    @Override
    public UserDto resetPassword(ResetPassword resetPassword) {
        if(resetPassword == null){
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "Bad params");
        }

        String phoneExt = resetPassword.getPhoneExt();
        String phone = resetPassword.getPhone();
        String token = resetPassword.getToken();
        String verificationCode = resetPassword.getVerificationCode();
        String newPassword = resetPassword.getNewPassword();
        String confirmationPassword = resetPassword.getConfirmPassword();

        if (Strings.isNullOrEmpty(phone) || !PhoneUtils.isPhoneNumber(phone, false)) {
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "Bad phone.");

        }

        if(Strings.isNullOrEmpty(token)){
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "The token field must not be null.");
        }

        if(Strings.isNullOrEmpty(verificationCode)){
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "The verification field code must not null.");
        }

        if(Strings.isNullOrEmpty(newPassword)){
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "The password field code must not null.");
        }

        if(Strings.isNullOrEmpty(confirmationPassword)){
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "The confirmation password field  must not null.");

        }

        if(!Objects.equal(newPassword, confirmationPassword)){
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "The new password does not match the confirmation password.");

        }

        SmsMessageProto.SmsToken smsToken = SmsMessageProto.SmsToken
                .newBuilder()
                .setToken(token)
                .build();

        ResponseProto.Response response  = smsServiceBlockingStub.findRecordByToken(smsToken);
        ResponseHelper.checkResponse(response);
        SmsMessageProto.SmsRecord smsRecord = ResponseHelper.unpack(response, SmsMessageProto.SmsRecord.class);

        if(smsRecord == null){
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "The verification code does not exists..");
        }

        if (!Objects.equal(verificationCode, smsRecord.getCode())) {
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "The verification code does not exists.");
        }

        UserEntity userEntity = userDao.findByPhoneExtAndPhone(phoneExt, phone);
        if(userEntity == null){
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "Cannot find user with phone #" + phone);
        }


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
