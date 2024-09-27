FROM openjdk:17
LABEL authors="ydewm"
EXPOSE 8080
ADD target/learner-service-docker.jar learner-service-docker.jar

ENTRYPOINT ["java", "-jar", "/learner-service-docker.jar"]