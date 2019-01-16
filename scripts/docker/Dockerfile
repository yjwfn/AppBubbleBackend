FROM rcntech/ubuntu-grpc:v0.0.4
EXPOSE 8080
EXPOSE 8090

#将当前目录添加文件到/bubble
ARG APP_PROJECT_NAME
#复制父pom.xml
ADD /pom.xml /app/pom.xml
ADD /protos /app/protos
ADD $APP_PROJECT_NAME /app/$APP_PROJECT_NAME
ADD scripts/gateway /app/gateway
ADD scripts/docker/entrypoint.sh /app/entrypoint.sh
RUN chmod u+x /app/entrypoint.sh

ENTRYPOINT ["/app/entrypoint.sh"]