FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml pom.xml
COPY Recipe/pom.xml Recipe/pom.xml
COPY Recipe/src Recipe/src
RUN mvn -f Recipe/pom.xml package -Dmaven.test.skip=true

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /build/Recipe/target/*.jar app.jar
EXPOSE 8086
ENTRYPOINT ["java","-jar","app.jar"]
