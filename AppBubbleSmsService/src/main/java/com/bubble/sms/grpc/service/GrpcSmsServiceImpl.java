package com.bubble.sms.grpc.service;

import com.bubble.sms.dto.SmsRecord;
import com.bubble.sms.grpc.message.SmsMessageProto;
import com.bubble.sms.service.SmsService;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrpcSmsServiceImpl extends SmsServiceGrpc.SmsServiceImplBase {

    @Autowired
    SmsService smsService;


    @Override
    public void sendRegistrySms(SmsMessageProto.SendSms request, StreamObserver<SmsMessageProto.SmsToken> responseObserver) {
        String token = smsService.sendRegistrySms(request.getPhoneExt(), request.getPhone());
        responseObserver.onNext( SmsMessageProto.SmsToken.newBuilder().setToken(token).build());
        responseObserver.onCompleted();
    }



    @Override
    public void findRecordByToken(SmsMessageProto.SmsToken request, StreamObserver<SmsMessageProto.SmsRecord> responseObserver) {
        SmsRecord smsRecord = smsService.findRecordByToken(request.getToken());
        SmsMessageProto.SmsRecord response = SmsMessageProto.SmsRecord.newBuilder()
                .setCode(smsRecord.getCode())
                .setPhone(smsRecord.getPhone())
                .setPhoneExt(smsRecord.getPhoneExt())
                .setSendTime(smsRecord.getSendTime())
                .setToken(smsRecord.getToken())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
