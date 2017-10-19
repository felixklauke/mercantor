FROM maven:3.5.0-jdk-9

EXPOSE 8080

ADD server/target/mercantor-server-1.0-SNAPSHOT.jar server.jar

ENTRYPOINT ["java", "-jar", "server.jar"]
