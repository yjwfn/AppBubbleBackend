#!/bin/bash

export GOPATH=${HOME}/go
export PATH=$PATH:/usr/local/go/bin:$GOPATH/bin

rootProjectDir="/app"
projectDir="${rootProjectDir}/${APP_PROJECT_NAME}"
descriptorSetsFile=${projectDir}/target/generated-resources/protobuf/descriptor-sets/app.protobin
shareProtobufVolumePath=/app/protobuf


cd ${rootProjectDir}/AppBubbleCommon
./mvnw install

cd $projectDir
#打包app.jar
./mvnw package -DskipTests -P docker-build
#编译proto文件
./mvnw protobuf:compile protobuf:compile-custom -P docker-build

# 复制Descriptor文件到共享目录
cp $descriptorSetsFile $shareProtobufVolumePath/${APP_PROJECT_NAME}.protobin


# Run service
java -jar ${projectDir}/target/app.jar