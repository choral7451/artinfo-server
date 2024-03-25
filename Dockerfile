FROM openjdk:17-oracle AS builder

#RUN apt-get update && \
#    apt-get install -y curl && \
#    curl -fsSL https://deb.nodesource.com/setup_16.x | bash - && \
#    apt-get install -y nodejs
#
#RUN apt-get update && apt-get install -y unzip
#WORKDIR /gradle
#RUN curl -L https://services.gradle.org/distributions/gradle-5.6.1-bin.zip -o gradle-5.6.1-bin.zip
#RUN unzip gradle-5.6.1-bin.zip
#ENV GRADLE_HOME=/gradle/gradle-5.6.1
#ENV PATH=$PATH:$GRADLE_HOME/bin
#RUN gradle --version
#
#WORKDIR /app
#COPY . .
#RUN ./gradlew clean build

FROM openjdk:17-oracle
WORKDIR /app
COPY --from=builder /app/build/libs/artinfo-server-1.1.0.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
