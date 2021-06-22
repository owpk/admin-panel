FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -DskipTests -f /home/app/pom.xml clean install

FROM tomcat:9.0-alpine
COPY --from=build /home/app/target/*SNAPSHOT.war /usr/local/tomcat/webapps/rpg.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
