FROM maven:3.9-eclipse-temurin-21-alpine

WORKDIR /usr/src/app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw -B dependency:go-offline -ntp -q

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]
