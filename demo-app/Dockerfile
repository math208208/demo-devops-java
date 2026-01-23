FROM amazoncorretto:25-alpine
COPY target/demo-app-1.0-SNAPSHOT.jar /app/demo-app.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "demo-app.jar"]