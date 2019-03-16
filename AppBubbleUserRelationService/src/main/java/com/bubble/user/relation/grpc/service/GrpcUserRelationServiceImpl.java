package com.bubble.user.relation.grpc.service;

import com.bubble.common.ResponseProto;
import com.bubble.common.utils.ProtobufUtils;
import com.bubble.common.utils.ResponseHelper;
import com.bubble.grpc.user.relation.service.UserRelationProto;
import com.bubble.grpc.user.relation.service.UserRelationServiceGrpc;
import com.bubble.user.relation.dto.UserRelationDto;
import com.bubble.user.relation.service.UserRelationService;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrpcUserRelationServiceImpl extends UserRelationServiceGrpc.UserRelationServiceImplBase {



    @Autowired
    private UserRelationService userRelationService;



    @Override
    public void getFollowersByUserId(UserRelationProto.UserId request, StreamObserver<ResponseProto.Response> responseObserver) {
        List<UserRelationDto> relationDtoList =  userRelationService.findFollowersByUserId(request.getUserId());
        UserRelationProto.RelationList relationList = toMessage(relationDtoList);
        responseObserver.onNext(ResponseHelper.ok( relationList));
        responseObserver.onCompleted();
    }

    @Override
    public void findUsersByFollowerId(UserRelationProto.UserId request, StreamObserver<ResponseProto.Response> responseObserver) {
        List<UserRelationDto> relationDtoList =  userRelationService.findUsersByFollowerId(request.getUserId());
        UserRelationProto.RelationList relationList = toMessage(relationDtoList);
        responseObserver.onNext(ResponseHelper.ok( relationList));
        responseObserver.onCompleted();
    }

    @Override
    public void follow(UserRelationProto.FollowArgs request, StreamObserver<ResponseProto.Response> responseObserver) {
        UserRelationDto userRelationDto = new UserRelationDto();
        userRelationDto.setFollowerId(request.getUserId());
        userRelationDto.setUserId(request.getFollowBy());
        userRelationService.follow(userRelationDto);
        responseObserver.onNext(ResponseHelper.ok(null));
        responseObserver.onCompleted();
    }

    @Override
    public void unfollow(UserRelationProto.UnfollowArgs request, StreamObserver<ResponseProto.Response> responseObserver) {
        userRelationService.unfollow(request.getFollowBy(), request.getUserId());
        responseObserver.onNext(ResponseHelper.ok(null));
        responseObserver.onCompleted();
    }

    private static UserRelationProto.RelationList toMessage(List<UserRelationDto> relationDtoList){
        final Logger logger = LoggerFactory.getLogger(GrpcUserRelationServiceImpl.class.getName());
        UserRelationProto.RelationList.Builder relationList = UserRelationProto.RelationList.newBuilder();
        for(UserRelationDto userRelationDto : relationDtoList){
            logger.debug("Relation {}", userRelationDto);
            UserRelationProto.UserRelation.Builder builder = UserRelationProto.UserRelation.newBuilder();
            ProtobufUtils.merge(userRelationDto, builder);
            relationList.addRelation(builder.build());
        }

        return relationList.build();
    }
}
