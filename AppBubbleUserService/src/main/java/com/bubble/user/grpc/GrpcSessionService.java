package com.bubble.user.grpc;

import com.bubble.common.utils.ProtobufUtils;
import com.bubble.sms.grpc.session.service.SessionServiceGrpc;
import com.bubble.sms.grpc.session.service.SessionServiceProto;
import com.bubble.user.dto.user.LoginResultDto;
import com.bubble.user.dto.user.PhoneLoginDto;
import com.bubble.user.service.SessionService;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrpcSessionService extends SessionServiceGrpc.SessionServiceImplBase{

    private Logger logger = LoggerFactory.getLogger(GrpcSessionService.class);

    @Autowired
    private SessionService sessionService;

    @Override
    public void loginByPhone(SessionServiceProto.LoginByPhone request, StreamObserver<SessionServiceProto.LoginResult> responseObserver) {
        logger.trace("Login by phone {}", request);
        logger.trace("Request for host {} ", Keys.CONTEXT_KEY_HEADER_X_CLIENT.get());

        PhoneLoginDto phoneLoginDto = ProtobufUtils.toBean(request, PhoneLoginDto.class);
        LoginResultDto loginResultDto = sessionService.loginByPhone(phoneLoginDto);

        SessionServiceProto.LoginResult.Builder loginResultBuilder = SessionServiceProto.LoginResult.newBuilder();
        ProtobufUtils.merge(loginResultDto, loginResultBuilder);

        responseObserver.onNext(loginResultBuilder.build());
        responseObserver.onCompleted();
    }
}
