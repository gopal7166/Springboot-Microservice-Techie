FROM openjdk:21

ARG JAVA_FILE=target/*.jar

COPY ${JAVA_FILE} orderservice.jar

ENTRYPOINT ["java", "-jar", "/orderservice.jar"]

EXPOSE 9092
