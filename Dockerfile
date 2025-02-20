FROM openjdk-17
WORKDIR /foyer
EXPOSE 8083
ADD target/*.jar dhiashayeb-4twin4-g1-tpfoyer-3.0.0.jar
ENTRYPOINT ["java","-jar","/dhiashayeb-4twin4-g1-tpfoyer-3.0.0.jar"]

