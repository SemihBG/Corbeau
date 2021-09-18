# Corbeau

It is post sharing web site project written in Java by using Spring Boot, Spring Web Flux, Spring Security, R2DBC, and Hazelcast.

[Posts Repository](https://github.com/SemihBKGR/corbeau-posts)

### Local Deployment

Requirements
- Java 1.8 or newest version
- Docker
- Maven (wrapper instead)

```console
mvn clean package -Dmaven.test.skip=true
```
```console
docker build .
```
