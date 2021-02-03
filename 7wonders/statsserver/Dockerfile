# docker build -t 7wonders:statsserver statsserver
# docker run --name stats 7wonders:statsserver

FROM openjdk:11-jdk-slim
ENTRYPOINT ["mvn"]
CMD ["-f", "statsserver/pom.xml", "exec:java"]