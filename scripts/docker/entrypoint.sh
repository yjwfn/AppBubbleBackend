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
protosDir=${rootProjectDir}/protos
# 服务目录
serviceProtoDirs=$(ls $protosDir)
# 创建输出目录
protoOutput=${gatewayProjectDir}
mkdir -p ${protoOutput}


for serviceDir in $serviceProtoDirs
do
    # 需要绝对路径，相对路径protoc会报重复定义错误
    protoFiles=$(ls $protosDir/$serviceDir | sed "s:\(.*\):$protosDir/$serviceDir/\1:")

    protoc \
      -I/usr/local/include \
      -I${protosDir} \
      -I$GOPATH/src \
      -I$GOPATH/src/github.com/grpc-ecosystem/grpc-gateway/third_party/googleapis \
      --go_out=plugins=grpc:${GOPATH}/src \
      ${protoFiles}

    protoc \
      -I/usr/local/include \
      -I${protosDir} \
      -I$GOPATH/src \
      -I$GOPATH/src/github.com/grpc-ecosystem/grpc-gateway/third_party/googleapis \
      --grpc-gateway_out=logtostderr=true:${GOPATH}/src \
      ${protoFiles}
done


# Run gateway
cd ${gatewayProjectDir}
sed -i -e "s/\${APP_PROTO_PACKAGE}/${APP_PROTO_PACKAGE}/;s/\${APP_GATEWAY_SERVICE}/${APP_GATEWAY_SERVICE}/" app.go
go run app.go &

# Run service
java -jar ${projectDir}/target/app.jar