#FROM maven:3.5.3-jdk-10-slim as build
#
#WORKDIR /app
#COPY pom.xml .
#COPY src src
#RUN mvn package -q -Dmaven.test.skip=true
#
#FROM openjdk:10.0.1-10-jre-slim
#
#WORKDIR /app
#EXPOSE 8080
#ENV STORE_ENABLED=true
#ENV WORKER_ENABLED=true
#COPY --from=build target/*.jar /app.jar
#
#CMD ["java", "-jar", "app.jar"]


FROM openjdk:10.0.1-10-jre-slim
COPY target/*.jar /app.jar
EXPOSE 8080/tcp
ENTRYPOINT ["java", "-jar", "/app.jar"]
