# Stage 1: Build the application using Maven and Eclipse Temurin (OpenJDK 21)
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set the working directory
WORKDIR /app

# Copy the Maven pom.xml file
COPY pom.xml .

# Download dependencies (this step is cached unless pom.xml changes)
RUN mvn dependency:go-offline -B

# Copy the rest of the application code
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Stage 2: Runtime stage using a smaller OpenJDK image
FROM eclipse-temurin:21-jdk-jammy

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/QuizAppFullStack-0.0.1-SNAPSHOT.jar .

# Expose the port your Spring Boot app runs on
EXPOSE 9192

# Use ENTRYPOINT to define the main command
ENTRYPOINT ["java", "-jar", "QuizAppFullStack-0.0.1-SNAPSHOT.jar"]