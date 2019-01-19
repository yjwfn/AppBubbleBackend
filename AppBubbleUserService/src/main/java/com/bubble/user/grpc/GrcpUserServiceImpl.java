package com.bubble.user.grpc;

import com.bubble.sms.grpc.user.message.UserMessageProto;
import com.bubble.sms.grpc.user.service.UserServiceGrpc;
import com.bubble.user.dto.user.PhoneRegistryDto;
import com.bubble.user.dto.user.UserDto;
import com.bubble.user.service.UserService;
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

        UserMessageProto.UserProfile userProfile = UserMessageProto.UserProfile.newBuilder()
                .setId(userDto.getId())
                .setNickName(userDto.getNickName())
                .setCreateTime(userDto.getCreateTime())
                .setAvatarUrl(userDto.getAvatarUrl())
                .setBirthday(userDto.getBirthday())
                .setCountry(userDto.getCountry())
                .setState(userDto.getState())
                .setEmail(userDto.getEmail())
                .setCity(userDto.getCity())
                .setGender(userDto.getGender())
                .setPhoneExt(userDto.getPhoneExt())
                .setPhone(userDto.getPhone())
                .build();

        responseObserver.onNext(userProfile);
        responseObserver.onCompleted();
    }
}
