FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml pom.xml
COPY Notifications/pom.xml Notifications/pom.xml
COPY Notifications/src Notifications/src
RUN mvn -f Notifications/pom.xml package -Dmaven.test.skip=true

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /build/Notifications/target/*.jar app.jar
EXPOSE 8087
ENTRYPOINT ["java","-jar","app.jar"]