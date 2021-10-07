<p align="center">
  <img src="https://github.com/owpk/java-rush-test-task/blob/master/img/screen.png" title="admin panel" width="350" height="350"/>
</p>

<h2> Play with docker  </h2>

  First make sure you have docker and docker compose installed
```bash
$ docker -v
# ... some valid output
$ docker compose --help
# ... some valid output

# or older docker compose version:
$ docker-compose -v
```

<h3>Deploy in docker:  </h3>

 - first time running should take some time - it's ok
 - from project root run:
```bash
$ docker compose up 

# or older version:
$ docker-compose up
```

open browser and enter url: http://localhost/rpg/  
Have fun :)  