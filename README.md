
# Blade Test Management System

The final product allows for dynamic planning by utilizing drag-and-drop functionality. Furthermore, the system automatically manages and monitors the allocation of test activities and equipment, while notifying conflicts.


## Installation

Prerequisites:

Java 17

Maven 3.5 or later

postgres 16

clone the repository to the desired directory
```bash
  git clone https://github.com/cs-23-sw-3-10/P3_Backend
```
Direct to the root of the repository and run the following command to build the project

```bash
  mvn clean install -DskipTests
```
Leaving out -DskipTests will run the test, this requires Docker to be running else all tests will fail.

Navigate to P3_Backend/src/main/resources/application.properties and update database information to direct to your PostgreSQL.

Direct to the root of repository the Run the following command to run the application

```bash
  mvn spring-boot:run
```

The default port the server will run on is http://localhost:8080 make sure your frontend aligns with this.
