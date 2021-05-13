- Play with docker
From project root run:
```
$ docker build -t jr-test .
$ docker run -d --rm -p 8080:8080 jr-test
```
go to browser and enter url: http://localhost:8080/jr/
