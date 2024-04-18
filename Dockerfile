FROM openjdk:17
ARG JAR_FILE=target/autofix-backend.jar
COPY ${JAR_FILE} autofix-backend.jar
ENTRYPOINT ["java","-jar","/autofix-backend.jar"]