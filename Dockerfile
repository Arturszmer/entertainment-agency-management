FROM openjdk:17-jdk

WORKDIR /app

COPY agency-web-0.0.1-SNAPSHOT.jar /app/agency-web-0.0.1-SNAPSHOT.jar

EXPOSE 31002

CMD ["java", "-jar", "agency-web-0.0.1-SNAPSHOT.jar"]
