package com.bubble.user.grpc;

import com.bubble.sms.grpc.user.message.UserMessageProto;
import com.bubble.sms.grpc.user.service.UserServiceGrpc;
import com.bubble.user.dto.user.PhoneRegistryDto;
import com.bubble.user.dto.user.UserDto;
import com.bubble.user.service.UserService;
import com.google.common.base.Strings;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrcpUserServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    @Autowired
    private UserService userService;


    @Override
    public void registryUserWithPhone(UserMessageProto.RegistryWithPhone request, StreamObserver<UserMessageProto.UserProfile> responseObserver) {
        PhoneRegistryDto phoneRegistryDto = new PhoneRegistryDto(request.getPhoneExt(), request.getPhone(), request.getPassword(), request.getToken(), request.getVerificationCode());
        UserDto userDto = userService.registerWithPhone(phoneRegistryDto);

        UserMessageProto.UserProfile.Builder userProfileBuilder = UserMessageProto.UserProfile.newBuilder();

        if(userDto.getId() != null){
            userProfileBuilder.setId(userDto.getId());
        }

        if(!Strings.isNullOrEmpty(userDto.getNickName())){
            userProfileBuilder.setNickName(userDto.getNickName());
        }


        if(userDto.getCreateTime() != null){
            userProfileBuilder.setCreateTime(userDto.getCreateTime());
        }


        if(!Strings.isNullOrEmpty(userDto.getAvatarUrl())){
            userProfileBuilder.setAvatarUrl(userDto.getAvatarUrl());
        }


        if(!Strings.isNullOrEmpty(userDto.getNickName())){
            userProfileBuilder.setNickName(userDto.getNickName());
        }


        if(userDto.getBirthday() != null){
            userProfileBuilder.setBirthday(userDto.getBirthday());
        }


        if(!Strings.isNullOrEmpty(userDto.getCountry())){
            userProfileBuilder.setCountry(userDto.getCountry());
        }


        if(!Strings.isNullOrEmpty(userDto.getState())){
            userProfileBuilder.setState(userDto.getState());
        }

        if(!Strings.isNullOrEmpty(userDto.getEmail())){
            userProfileBuilder.setEmail(userDto.getEmail());
        }


        if(!Strings.isNullOrEmpty(userDto.getCity())){
            userProfileBuilder.setCity(userDto.getCity());
        }


        if(userDto.getGender() != null){
            userProfileBuilder.setGender(userDto.getGender());
        }


        if(!Strings.isNullOrEmpty(userDto.getPhoneExt())){
            userProfileBuilder.setPhoneExt(userDto.getPhoneExt());
        }


        if(!Strings.isNullOrEmpty(userDto.getPhone())){
            userProfileBuilder.setPhone(userDto.getPhone());
        }


        responseObserver.onNext(userProfileBuilder.build());
        responseObserver.onCompleted();
    }
}
