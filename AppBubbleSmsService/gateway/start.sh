#!/bin/sh

export http_proxy=http://127.0.0.1:1087
export https_proxy=http://127.0.0.1:1087

#https://github.com/grpc-ecosystem/grpc-gateway
export GOPATH=/Users/yjwfn/go
export PATH=$PATH:$GOPATH/bin

projectDir=/Users/yjwfn/development/web-project/AppBubbleBackend/AppBubbleSmsService
goOut=${projectDir}/target/generated-sources/protobuf/grpc-go
swaggerOut=${projectDir}/target/generated-sources/protobuf/grpc-swagger

serviceDir=${projectDir}/src/main/proto/

mkdir -p ${goOut}
mkdir -p ${swaggerOut}

# Generate gRPC stub
protoc -I/usr/local/include \
    -I${serviceDir}   -I$GOPATH/src  \
    -I$GOPATH/src/github.com/grpc-ecosystem/grpc-gateway/third_party/googleapis \
    --grpc-gateway_out=logtostderr=true:${goOut} ${serviceDir}/SmsService.proto

# Generate reverse-proxy
protoc -I/usr/local/include -I${serviceDir} \
  -I$GOPATH/src \
    -I$GOPATH/src/github.com/grpc-ecosystem/grpc-gateway/third_party/googleapis  \
     --go_out=plugins=grpc:${goOut}  ${serviceDir}/SmsService.proto

# (Optional) Generate swagger definitions
protoc -I/usr/local/include -I${serviceDir}  \
  -I$GOPATH/src \
  -I$GOPATH/src/github.com/grpc-ecosystem/grpc-gateway/third_party/googleapis \
  --swagger_out=logtostderr=true:${swaggerOut} \
   ${serviceDir}/SmsService.proto

# Note: After generating the code for each of the stubs,
# in order to build the code, you will want to run go get .
# from the directory containing the stubs.

cd ${goOut}
go get .

