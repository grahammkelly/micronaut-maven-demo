# Micronaut (maven) demo

Demonstration of a Micronaut service.

To run the application and have changes auto-reload, use `./mvnw mn:run`.

Tests can run against MongoDB, using the `testcontainers` library, or against an in-memory database. Use of the `testcontainers` library means that the application spins up docker containers for MongoDB, and the tests connect to that. This is the default behaviour. _Please be aware_, this means you MUST have docker desktop running if testing on Mac.

## Native compilation

You can compile into native code using `./mvnw package -Dpackaging=native-image`. **However**, be aware, the testcontainers library is not supported by GraalVM, so the tests will fail against the native application, _and_ if you run the application natively, without redirecting the application config to use a 'real' MongoDB instance (either hosted seperately, or in-memory), the service will startup, however there may be no functionality.

---

## Micronaut 4.2.0 Documentation

- [User Guide](https://docs.micronaut.io/4.2.0/guide/index.html)
- [API Reference](https://docs.micronaut.io/4.2.0/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/4.2.0/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

- [Micronaut Maven Plugin documentation](https://micronaut-projects.github.io/micronaut-maven-plugin/latest/)

## Feature test-resources documentation

- [Micronaut Test Resources documentation](https://micronaut-projects.github.io/micronaut-test-resources/latest/guide/)

## Feature data-mongodb documentation

- [Micronaut Data MongoDB documentation](https://micronaut-projects.github.io/micronaut-data/latest/guide/#mongo)
- [https://docs.mongodb.com](https://docs.mongodb.com)

## Feature testcontainers documentation

- [https://www.testcontainers.org/](https://www.testcontainers.org/)

## Feature serialization-jackson documentation

- [Micronaut Serialization Jackson Core documentation](https://micronaut-projects.github.io/micronaut-serialization/latest/guide/)

## Feature management documentation

- [Micronaut Management documentation](https://docs.micronaut.io/latest/guide/index.html#management)

## Feature lombok documentation

- [Micronaut Project Lombok documentation](https://docs.micronaut.io/latest/guide/index.html#lombok)
- [https://projectlombok.org/features/all](https://projectlombok.org/features/all)

## Feature micronaut-aot documentation

- [Micronaut AOT documentation](https://micronaut-projects.github.io/micronaut-aot/latest/guide/)

## Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#nettyHttpClient)

## Feature mongo-sync documentation

- [Micronaut MongoDB Synchronous Driver documentation](https://micronaut-projects.github.io/micronaut-mongodb/latest/guide/index.html)
- [https://docs.mongodb.com](https://docs.mongodb.com)

## Feature maven-enforcer-plugin documentation

- [https://maven.apache.org/enforcer/maven-enforcer-plugin/](https://maven.apache.org/enforcer/maven-enforcer-plugin/)

## Feature jetty-server documentation

- [Micronaut Jetty Server documentation](https://micronaut-projects.github.io/micronaut-servlet/1.0.x/guide/index.html#jetty)
