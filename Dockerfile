FROM openjdk:11-jdk-alpine
EXPOSE 8081
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} quotationmanagement.jar
ENTRYPOINT ["java","-jar","/quotationmanagement.jar"]

