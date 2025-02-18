FROM maven:3.8.4-openjdk-17
WORKDIR /foyer
EXPOSE 8083
ADD target/foyer-3.0.0.jar mouadhfersi-4TWIN4-G1-Tpfoyer-3.0.0.jar
ENTRYPOINT ["java","-jar","/mouadhfersi-4TWIN4-G1-Tpfoyer-3.0.0.jar"]

