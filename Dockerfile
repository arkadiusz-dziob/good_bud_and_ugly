FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/demo-0.0.1.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8080
EXPOSE 9092
ENTRYPOINT ["java","-jar","/usr/local/lib/application.jar"]