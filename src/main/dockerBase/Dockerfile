FROM openjdk:17.0.1-jdk-slim

ENV JAVA_OPS " -Xms512m -Xmx512m -Djava.security.edg=file:/dev/./urandom"

WORKDIR application

COPY ../../../target/yahoofinance-webapi-0.0.1-SNAPSHOT.jar ./

ENTRYPOINT ["java", "-jar", "yahoofinance-webapi-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=dev"]