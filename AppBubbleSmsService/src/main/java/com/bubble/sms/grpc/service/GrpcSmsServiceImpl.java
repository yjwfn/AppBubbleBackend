package com.bubble.sms.grpc.service;

import com.bubble.common.ResponseProto;
import com.bubble.common.utils.ProtobufUtils;
import com.bubble.common.utils.ResponseHelper;
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
    public void sendRegistrySms(SmsMessageProto.SendSms request, StreamObserver<ResponseProto.Response> responseObserver) {
        String token = smsService.sendRegistrySms(request.getPhoneExt(), request.getPhone());
        SmsMessageProto.SmsToken smsToken = SmsMessageProto.SmsToken.newBuilder().setToken(token).build();
        responseObserver.onNext(ResponseHelper.ok(smsToken) );
        responseObserver.onCompleted();
    }



    @Override
    public void findRecordByToken(SmsMessageProto.SmsToken request, StreamObserver<ResponseProto.Response> responseObserver) {
        SmsRecord smsRecord = smsService.findRecordByToken(request.getToken());
        SmsMessageProto.SmsRecord.Builder response = SmsMessageProto.SmsRecord.newBuilder();
        ProtobufUtils.merge(smsRecord, response);
        responseObserver.onNext(ResponseHelper.ok(response.build()));
        responseObserver.onCompleted();
    }
}
