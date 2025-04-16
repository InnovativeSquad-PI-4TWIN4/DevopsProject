# Build stage
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the built JAR with the correct name
COPY --from=build /app/target/ChaiebDhia_4twin4_InnovativeSquad.jar app.jar

# Add non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

EXPOSE 8089

ENTRYPOINT ["java", "-jar", "app.jar"]