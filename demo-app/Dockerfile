FROM amazoncorretto:25-alpine
COPY target/demo-app.jar /app/demo-app.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "demo-app.jar"]