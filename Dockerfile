# Build stage
FROM maven:3.8.6-openjdk-18-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package


# Package stage
FROM openjdk:18-jdk-slim
COPY --from=build /home/app/target/*.jar /usr/local/lib/app.jar
EXPOSE 80

#ENTRYPOINT ["java","-Dserver.port=80", "-Dspring.datasource.url=${DATASOURCE_URL:teste}","-Dspring.datasource.username=${DATASOURCE_USERNAME:teste}","-Dspring.datasource.password=${DATASOURCE_PASSWORD:teste}", "-jar","/usr/local/lib/app.jar"]
ENTRYPOINT ["java","-Dserver.port=80", "-Dspring.datasource.url=${DATASOURCE_URL:teste}","-Dspring.datasource.username=${DATASOURCE_USERNAME:teste}","-Dspring.datasource.password=${DATASOURCE_PASSWORD:teste}", "-jar","/usr/local/lib/app.jar"]
