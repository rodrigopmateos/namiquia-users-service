FROM openjdk:8
VOLUME /tmp
EXPOSE 8183
ADD ./target/users-service-1.0.0-RELEASE.jar users-service.jar
ENTRYPOINT ["java", "-jar", "/users-service.jar"]