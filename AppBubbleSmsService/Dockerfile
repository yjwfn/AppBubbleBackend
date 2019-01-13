#docker run --rm --network apollo_default -e ENV=dev --link apollo_apollo-configservice-dev_1:apollo-configserver bubble/sms-service:0.0.1-SNAPSHOT
FROM openjdk:8-jre
EXPOSE 8080
ARG JAR_FILE
RUN mkdir /grpc
ADD target/$JAR_FILE /bubble/app.jar
ENTRYPOINT ["java", "-jar" , "/bubble/app.jar"]