# Build Stage
FROM maven:3.8.7-openjdk-18 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM amazoncorretto:17
ARG APP_VERSION=1.0.0

WORKDIR /app
COPY --from=build /build/target/backend-*.jar /app/

EXPOSE 8088

ENV DB_URL=jdbc:postgresql://database:5432/postgres
ENV JAR_VERSION=${APP_VERSION}

CMD java -jar -Dspring.datasource.url=${DB_URL} backend-${JAR_VERSION}.jar