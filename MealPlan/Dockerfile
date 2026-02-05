FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml pom.xml
COPY MealPlan/pom.xml MealPlan/pom.xml
COPY MealPlan/src MealPlan/src
RUN mvn -f MealPlan/pom.xml package -Dmaven.test.skip=true

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /build/MealPlan/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","app.jar"]