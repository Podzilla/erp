# Stage 1: Build the application (This stage has JDK and Maven)
FROM maven:3.8.7-openjdk-21 AS builder # Use a Maven image with JDK 21
WORKDIR /app
COPY pom.xml . # Copy the build file
COPY src ./src # <--- This is where you copy the source code in the BUILD stage
RUN mvn clean package -DskipTests # Build the project (skipping tests to speed up build)

# Stage 2: Run the application (This stage has just a JRE)
FROM eclipse-temurin:21-jre-alpine # Use a smaller JRE image for running
WORKDIR /app
# Copy only the JAR artifact from the 'builder' stage to the current stage
COPY --from=builder /app/target/analytics-monolith-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080