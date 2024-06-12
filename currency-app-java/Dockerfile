FROM eclipse-temurin:17.0.9_9-jre

ENV SERVER_PORT=8080

EXPOSE 8080

COPY target/currency-app-1.0.0-SNAPSHOT.jar /opt/currency-app/currency-app.jar

CMD ["java", "-jar", "/opt/currency-app/currency-app.jar"]
