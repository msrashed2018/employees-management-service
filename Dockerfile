FROM openjdk:8-jdk-alpine
EXPOSE 8080
ARG JAR_FILE=target/employees-management-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar

RUN mkdir -p /var/employee-management/data/

ENTRYPOINT ["java","-jar","/app.jar"]
