# Salary Predecition As Service
This service demonstrates Model serving as REST API. It's an opinionated architecture design, where best of the bread programing language is picked, which they are good at. For example, python is used to created ML model & Java/Spring is used to build REST APIs.

It also uses PYRO4/pickle serialization to serialize ML object and accessed via name server in Java. Further Spring BOOT based REST API uses Hazelcast based distributed caching to store remote python object, to provide HA and scalability along with disaster recovery.

## Setup
1. Install java 11
2. Install maven
3. Install java project
````
mvn clean install -DskipTests
````
4. Python artifacts are managed by poetry

````
export PYRO_SERIALIZERS_ACCEPTED=serpent,json,marshal,pickle,dill

pyro4-ns

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

