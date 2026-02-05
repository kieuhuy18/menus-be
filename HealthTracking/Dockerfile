FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml pom.xml
COPY HealthTracking/pom.xml HealthTracking/pom.xml
COPY HealthTracking/src HealthTracking/src
RUN mvn -f HealthTracking/pom.xml package -Dmaven.test.skip=true

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /build/HealthTracking/target/*.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","app.jar"]