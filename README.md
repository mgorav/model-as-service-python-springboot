# Salary Predecition As Service

## Setup
1. Install java 11
2. Install maven
3. Install java project
````
mvn clean install -DskipTests
````
4. Python artifacts are managed by poetry

````
poetry install
# to directory hr-model/src/main/python
python random_forest_regression.py
````

5. Execute 

````
java -jar target/hr-model-0.0.1-SNAPSHOT.jar
````

6. Open the broser and hit the URL - http://localhost:4200/swagger-ui.html



Play time!!!




# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/maven-plugin/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

