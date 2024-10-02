FROM maven:3.9-eclipse-temurin-21-alpine AS build

COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .

RUN sed -i 's/\r$//' mvnw
RUN chmod +x mvnw && ./mvnw -B dependency:go-offline -ntp -q

COPY src src

RUN ./mvnw -B package -DskipTests -Pprod -ntp


FROM openjdk:22-ea-21-jdk-oraclelinux8

WORKDIR /usr/src/app

COPY --from=build target/*.jar build.jar

RUN useradd -m user
USER user

ENTRYPOINT java -jar -Dserver.port=${PORT:-8080} build.jar