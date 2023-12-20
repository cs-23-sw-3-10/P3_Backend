
# Blade Test Management System

A brief description of what this project does and who it's for




## Installation

Prerequisites:

Java 17

Maven 3.5 or later

postgres 16

clone the repository to the desired directory
```bash
  git clone https://github.com/cs-23-sw-3-10/P3_Backend
```
Direct to root of repostiory and run the following command to build the project

```bash
  mvn clean install -DskipTests
```
Leaving out -DskipTests will run the test, this requires Docker to be running else all tests will fail.

Navigate to P3_Backend/src/main/resources/application.properties and update database information direct to you postgreSQL.

Direct to root of repostiory the Run the following command to run the application

```bash
  mvn spring-boot:run
```

The default port the server will run on is http://localhost:8080 make sure you frontend alings with this.
