FROM amazoncorretto:17
WORKDIR /finance-track-api
COPY target/finance-track-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]