FROM openjdk:19
VOLUME /tmp
COPY target/*.jar app.jar
EXPOSE 9500
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=prod","/app.jar"]