FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml -DskipTests=true clean install

FROM tomcat:9.0-alpine
COPY --from=build /home/app/target/*SNAPSHOT.war /usr/local/tomcat/webapps/jr.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
