#!/bin/bash

export GOPATH=${HOME}/go
export PATH=$PATH:/usr/local/go/bin:$GOPATH/bin

rootProjectDir="/app"
projectDir="${rootProjectDir}/${APP_PROJECT_NAME}"

cd ${rootProjectDir}
mvn clean install -DskipTests

cd $projectDir
#打包app.jar
mvn package -DskipTests -P docker-build
#编译proto文件
mvn protobuf:compile protobuf:compile-custom -P docker-build


# Run service
java -jar ${projectDir}/target/app.jar