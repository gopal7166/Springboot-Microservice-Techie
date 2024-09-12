FROM openjdk:21

ARG JAVA_FILE=target/*.jar

COPY ${JAVA_FILE} gatewayservice.jar

ENTRYPOINT ["java", "-jar", "/gatewayservice.jar"]

EXPOSE 9191
