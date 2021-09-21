# Corbeau

Corbeau is post sharing website project written in Java by using Spring Boot, Spring WebFlux, Spring Security, Spring Data R2DBC.


### Local Deployment

Requirements

- Java 11 or newest version
- Docker
- Maven (wrapper instead)

Execute these commands sequentially in the root directory of the project.
```console
mvn clean package -Dmaven.test.skip=true
```
```console
docker build -t=corbeau  .
```
```console
docker-compose up
```

Default values

- port: 9000
- admin name: admin
- admin password: password

Menu page url: http://localhost:9000 \
Moderation page url: http://localhost:9000/moderation 
