FROM openjdk:17-oracle

ARG JAR_FILE=/build/libs/artinfo-server-1.1.0.jar

COPY ${JAR_FILE} /artinfo-server.jar

ENTRYPOINT ["java","-jar","/artinfo-server.jar"]