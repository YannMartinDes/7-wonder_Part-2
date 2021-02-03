# docker build -t 7wonders:statsserver statsserver
# docker run --name stats 7wonders:statsserver

FROM openjdk:11-jdk-slim
ARG JAR_FILE=./target/statsserver-1.0-jar-with-dependencies.jar
COPY ${JAR_FILE} stats.jar
ENTRYPOINT ["java"]
CMD ["-jar", "stats.jar"]