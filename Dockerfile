FROM openjdk:17-oracle

COPY build/libs/artinfo-server-1.1.0.jar /artinfo-server.jar

ENTRYPOINT ["java","-jar","/artinfo-server.jar"]
