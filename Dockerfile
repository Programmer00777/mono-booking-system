FROM amazoncorretto:17.0.0
ARG JAR_FILE=target/*.jar
COPY ./target/bookingsystem-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]