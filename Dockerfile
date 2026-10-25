FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/temp_localization.jar app.jar

ENV LANG=C.UTF-8

CMD ["java", "-Dfile.encoding=UTF-8", "-jar", "app.jar"]
