FROM openjdk:12-alpine

WORKDIR /tmp
COPY . /tmp
RUN mvn clean install
ENTRYPOINT ["java","-jar","/tmp/target/test-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080