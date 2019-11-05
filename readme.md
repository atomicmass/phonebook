# Phonebook App
This phonebook app uses Java 1.8 (or higher) and runs on a Quarkus service (http://quarkus.io). The dependencies and build are managed my Maven (https://maven.apache.org). The database is an H2 file database.

## Starting in dev mode
To compile the app and dowload all the Quarkus dependencies execute this on the command line:
> mvn compile quarkus:dev

## Website
Once the Quarkus is up brose to this address:
> http://localhost:9001/index.html

Users can register themselves and login thereafter.

## Executing the unit tests
> mvn test

## Open API Definition
> http://localhost:9001/openapi
