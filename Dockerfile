FROM openjdk:11
EXPOSE 8081
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} quotationmanagement.jar
ENTRYPOINT ["java","-jar","/quotationmanagement.jar"]
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw  package -DskipTests