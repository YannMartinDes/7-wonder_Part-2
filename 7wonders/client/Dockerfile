# docker build -t 7wonders:client client
# docker run --name game 7wonders:client

FROM openjdk:11-jdk-slim
ARG JAR_FILE=./target/client-1.0.jar
COPY ${JAR_FILE} client.jar
ENTRYPOINT ["java"]
CMD ["-jar", "client.jar"]