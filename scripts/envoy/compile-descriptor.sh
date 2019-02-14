#!/bin/bash

rootProjectDir=../../../AppBubbleBackend
javaOut=/tmp/protos
protosDir=$rootProjectDir/protos
googleapis=/Users/hb/go/src/github.com/googleapis/googleapis

mkdir -p ${javaOut}

protoc  \
    --descriptor_set_out=./app.protobin \
    --include_imports \
    --proto_path=${rootProjectDir}/protos \
    --proto_path=${googleapis} \
    --proto_path=${HOME}/go/src \
    --java_out=${javaOut} \
    ${rootProjectDir}/protos/sms/SmsMessage.proto \
    ${rootProjectDir}/protos/sms/SmsService.proto \
    ${rootProjectDir}/protos/user/UserMessage.proto \
    ${rootProjectDir}/protos/user/UserService.proto \
    ${rootProjectDir}/protos/user/SessionService.proto

