FROM maven:3.8.4-openjdk-17
WORKDIR /foyer
EXPOSE 8083
ADD target/dhiashayeb-4twin4-g1-tpfoyer-3.0.0.jar dhiashayeb-4twin4-g1-tpfoyer-3.0.0.jar
ENTRYPOINT ["java","-jar","/dhiashayeb-4twin4-g1-tpfoyer-3.0.0.jar"]

