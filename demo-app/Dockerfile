FROM amazoncorretto:25-alpine
COPY target/*-jar-with-dependencies.jar /app/demo-app.jar

WORKDIR /app

EXPOSE 8080
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "demo-app.jar"]