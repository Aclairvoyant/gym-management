FROM openjdk:17
MAINTAINER sjd
ADD gym-server-1.0.0-SNAPSHOT.jar gym.jar
RUN bash -c 'touch /gym.jar'
EXPOSE 8081
ENTRYPOINT ["java","-jar","/gym.jar","--spring.profiles.active=dev"]
