FROM openjdk:8
RUN mkdir -p /data/img/users
RUN mkdir -p /data/img/activities
ADD build/libs/pes-laresistencia-server-0.0.1-SNAPSHOT.jar /data/server.jar
CMD ["java", "-jar", "/data/server.jar"]
