FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /MainService
COPY ./pom.xml .

RUN mvn -B dependency:go-offline

COPY ./src ./src
RUN mvn -B clean install -DskipTests


FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /MainService/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]