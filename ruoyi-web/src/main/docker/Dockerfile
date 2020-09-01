FROM openjdk:8-jre
VOLUME /tmp
ADD lecshop_web_applets-3.0.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Duser.timezone=GMT+08","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.profiles.active=prod"]