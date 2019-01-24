package com.bubble.user.grpc;

import com.bubble.sms.grpc.user.message.UserMessageProto;
import com.bubble.sms.grpc.user.service.UserServiceGrpc;
import com.bubble.user.dto.user.PhoneRegistryDto;
import com.bubble.user.dto.user.UserDto;
import com.bubble.user.service.UserProfileService;
import com.bubble.user.service.UserService;
import com.bubble.common.utils.ProtobufUtils;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrcpUserServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    @Autowired
    private UserService userService;

    @Autowired
    private UserProfileService userProfileService;

    @Override
    public void registryUserWithPhone(UserMessageProto.RegistryWithPhone request, StreamObserver<UserMessageProto.UserProfile> responseObserver) {
        PhoneRegistryDto phoneRegistryDto = new PhoneRegistryDto(request.getPhoneExt(), request.getPhone(), request.getPassword(), request.getToken(), request.getVerificationCode());
        UserDto userDto = userService.registerWithPhone(phoneRegistryDto);

        UserMessageProto.UserProfile.Builder userProfileBuilder = UserMessageProto.UserProfile.newBuilder();
        ProtobufUtils.merge(userDto, userProfileBuilder);
        responseObserver.onNext(userProfileBuilder.build());
        responseObserver.onCompleted();
    }


    @Override
    public void getUserProfile(UserMessageProto.UserId request, StreamObserver<UserMessageProto.UserProfile> responseObserver) {
        UserDto userDto = userProfileService.getUserProfile(request.getUsreId());
        UserMessageProto.UserProfile.Builder builder = UserMessageProto.UserProfile.newBuilder();
        ProtobufUtils.merge(userDto, builder);

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
