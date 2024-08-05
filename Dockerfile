FROM amazoncorretto:21-alpine-jdk
ARG JAR_FILE=target/*.jar
COPY ./target/stock-exchange-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]