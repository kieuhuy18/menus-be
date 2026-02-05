FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml pom.xml
COPY Stats/pom.xml Stats/pom.xml
COPY Stats/src Stats/src
RUN mvn -f Stats/pom.xml package -Dmaven.test.skip=true

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /build/Stats/target/*.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java","-jar","app.jar"]