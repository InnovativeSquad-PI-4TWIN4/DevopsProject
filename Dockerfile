FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Vérification du contenu du répertoire target pour le nom du fichier JAR
RUN ls -l /app/target

FROM openjdk:17-jdk-alpine

WORKDIR /app

# Utilisation du bon nom de fichier JAR
COPY --from=build /app/target/mouadhfersi-4twin4-g1-tpfoyer-3.0.0.jar app.jar

EXPOSE 8089

ENTRYPOINT ["java", "-jar", "app.jar"]