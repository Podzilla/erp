FROM openjdk:25-ea-4-jdk-oraclelinux9

WORKDIR /app

COPY ./target/*.jar analytics_app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "analytics_app.jar"]