# spring-boot2-sleuth-integration-demo
Spring Cloud Sleuth integration with a spring boot 2 functional endpoint.

A demo app to show Spring Cloud Sleuth integration for a Spring Boot 2 WebFulx app.

To run the app, use following command (you need Java 8 for this)
```
./gradlew clean build bootRun
```

To test the app, you can use following URLs

**Success cases**
```
curl -v http://localhost:5002/users/U1
```

**Failure cases**
```
curl -v http://localhost:5002/users/U-1
```
