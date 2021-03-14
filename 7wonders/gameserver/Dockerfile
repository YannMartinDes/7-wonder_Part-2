# docker build -t 7wonders:gameserver gameserver
# docker run --name game 7wonders:gameserver

FROM openjdk:11-jdk-slim
ARG JAR_FILE=./target/gameserver-1.0.jar
COPY ${JAR_FILE} game.jar
ENTRYPOINT ["java"]
CMD ["-jar", "game.jar"]