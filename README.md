# Stock Exchange API

This is a web service (HTTP REST API) to work with stock exchanges and includes basic CRUD operations.


* After running application you can click below link to see API related documentation:

    1. http://localhost:8080/swagger-ui.html#

Technologies and libraries are basically like below:

1. `Embeded H2` for database
2. `Spring data and JPA` for persistence and crud support
3. `Spring Web` for rest api
4. `Swagger` for API documentation
5. `Junit 5` for tests
6. `Lombok` for data and logging support to get rid of boilerplate code (like getters and setters)
7. `Docker` for running app in container
8. `Spring Security` for basic jwt based authentication

How to run locally with docker compose :
1. `./start-app.sh` --> running tests, building application jar, and start docker-compose
2. `./stop-app.sh` --> stop docker container

A default admin user is created at the beginning with 'admin' & 'admin' username and password.

How to run locally with mvn:
1. `mvn clean install` --> run tests and build application artifact
2. `mvn spring-boot:run` --> run application

How to run locally with docker :
1. `mvn clean install` --> run tests and build application artifact
2. `docker build . -t stock-exchange-image` --> build docker image
3. `docker run -p 8080:8080  stock-exchange-image` --> run docker image