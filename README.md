<p align="center">
  <img src="https://github.com/owpk/java-rush-test-task/blob/master/img/screen.png" title="admin panel" width="350" height="350"/>
</p>

<h2> Play with docker  </h2>

  First make sure you have docker and docker-compose installed
```bash
$ docker-compose version
docker-compose version 1.29.2, build unknown
docker-py version: 5.0.0
CPython version: 3.9.5
OpenSSL version: OpenSSL 1.1.1k  25 Mar 2021
```

<h3>Deploy in docker:  </h3>

You HAVE to modify jdbc url connection line  

- go to ./src/main/java/com/game/config/AppConfig.java 

```java
// switch this line:
dataSource.setUrl("jdbc:mysql://localhost:3306/rpg?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8");
// to this:
dataSource.setUrl("jdbc:mysql://db:3306/rpg?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8");
// this is necessary in order to connect our rpg.war tomcat application to mysql server in docker environment, 
// you can't use localhost because of mysql use ipv6 address bindings, in future i'll try to fix docker-compose.yml file to avoid this problem.
```

from project root run (first time running should take some time - it's ok):

```bash
$ docker-compose up -d
# to see logs run 'docker-compose up' (without -d option) 
# or find running container id with 'docker ps' and run 'docker logs <container-id>'
```

open browser and enter url: http://localhost/rpg/  
Have fun :)  
