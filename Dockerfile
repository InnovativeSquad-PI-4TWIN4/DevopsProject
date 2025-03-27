FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Print the contents of the target directory to verify the JAR name
RUN ls -l /app/target

FROM openjdk:17-jdk-alpine

WORKDIR /app

# Use the correct JAR name for your project
COPY --from=build /app/target/ChaiebDhia_4twin4_InnovativeSquad.jar app.jar

EXPOSE 8089

ENTRYPOINT ["java", "-jar", "app.jar"]