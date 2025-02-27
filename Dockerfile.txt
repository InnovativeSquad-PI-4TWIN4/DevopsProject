FROM maven:3.8.4-openjdk-17
WORKDIR /foyer
EXPOSE 8083
ADD target/firas090-4twin4-g1-tpfoyer-3.0.0.jar firas090-4twin4-g1-tpfoyer-3.0.0.jar
ENTRYPOINT ["java","-jar","/firasbenromdhane-4twin4-g1-tpfoyer-3.0.0.jar"]
