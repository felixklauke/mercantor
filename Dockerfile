FROM openjdk:11

MAINTAINER Felix Klauke <info@felix-klauke>

EXPOSE 8080

ADD server/target/mercantor-server-1.0-SNAPSHOT.jar server.jar

ENTRYPOINT ["java", "-jar", "server.jar"]
