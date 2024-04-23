# xyz

How to start the xyz application
---

1. Run `mvn clean install` to build your application
2. If on windows run `$env:GOOGLE_APPLICATION_CREDENTIALS="<enter full path for service-account-details.json file in this directory>"` else run `export GOOGLE_APPLICATION_CREDENTIALS="<enter full path for service-account-details.json file in this directory>"`
3. Start application with `java -jar target/xyz-1.0-SNAPSHOT.jar server config.yml`
4. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`
