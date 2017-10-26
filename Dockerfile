FROM openjdk:8-jre-alpine

RUN mkdir /app

WORKDIR /app

ADD ./target/users-1.0.0.jar /app

EXPOSE 8080

CMD ["java", "-jar", "users-1.0.0.jar"]
