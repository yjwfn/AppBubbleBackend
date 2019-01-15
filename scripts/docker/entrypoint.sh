#!/bin/bash

export GOPATH=${HOME}/go
export PATH=$PATH:/usr/local/go/bin:$GOPATH/bin

rootProjectDir="/app"
projectDir="${rootProjectDir}/${APP_PROJECT_NAME}"
gatewayProjectDir="${rootProjectDir}/gateway"


cd $projectDir
#打包app.jar
./mvnw package -DskipTests -P docker-build
#编译proto文件
./mvnw protobuf:compile protobuf:compile-custom


# Generate gRPC stub
protoDir=${projectDir}/src/main/proto
protoFiles=$(ls $protoDir)
protoOutput=${gatewayProjectDir}/${APP_PROTO_PACKAGE}

mkdir -p ${protoOutput}

protoc -I/usr/local/include -I${protoDir} \
  -I$GOPATH/src \
  -I$GOPATH/src/github.com/grpc-ecosystem/grpc-gateway/third_party/googleapis \
  --go_out=plugins=grpc:${protoOutput} \
  ${protoFiles}

# Generate reverse-proxy
protoc -I/usr/local/include -I${protoDir}  \
  -I$GOPATH/src \
  -I$GOPATH/src/github.com/grpc-ecosystem/grpc-gateway/third_party/googleapis \
  --grpc-gateway_out=logtostderr=true:${protoOutput} \
  ${protoFiles}


## Generate gateway
cd ${gatewayProjectDir}
sed -i -e "s/\${APP_PROTO_PACKAGE}/${APP_PROTO_PACKAGE}/;s/\${APP_GATEWAY_SERVICE}/${APP_GATEWAY_SERVICE}/" app.go


# Run service
java -jar ${projectDir}/target/app.jar