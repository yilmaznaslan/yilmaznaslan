# Database Access in  Java
Let's explore the concepts of database access in Java by considering the evolution and history of the technologies
involved. We'll cover the key concepts and technologies from the early days to the modern approaches.

### 1) Native Database Connectivity:

* In the early days of Java, database access was achieved using vendor-specific APIs or drivers provided by database
vendors.
* Developers had to manually write code to 
  * establish a connection, 
  * send SQL queries, and 
  * process the results.
* This approach lacked standardization and required significant effort to work with different databases.

###  2) JDBC (Java Database Connectivity):

JDBC was introduced as a standardized API in the mid-1990s to provide a uniform way to interact with databases in Java.
It offered a set of interfaces and classes for connecting to databases, executing SQL queries, and processing results.
JDBC allowed developers to write database-agnostic code by abstracting the differences between database vendors.
It became the de facto standard for database access in Java and is still widely used today.

![](https://4.bp.blogspot.com/-OIiezx01y7M/W78-Ort5u4I/AAAAAAAAEPY/a7zeUmu2McsUzoqS5_wSf_1jQmYFRCZHACLcBGAs/s1600/jdbc.jpg)

### 3) ORM (Object-Relational Mapping):

As applications grew in complexity, the need for a more **object-oriented approach to database access** arose.
ORM frameworks were introduced to bridge the gap between **object-oriented programming** and **relational databases**.
ORM maps database tables to Java objects, allowing developers to work with objects rather than directly dealing with SQL
and **result sets**.
The frameworks handle the `translation between objects and database tables, simplifying data access and reducing
boilerplate code`.
Examples of popular Java ORM frameworks include Hibernate, EclipseLink, and Apache Cayenne.

### 4) JPA (Java Persistence API):

JPA is a specification that defines a set of interfaces and annotations for ORM in Java.
It provides a standardized way to define the mapping between Java objects and database tables, allowing for portability
across different JPA implementations.
JPA is part of the Java EE (now Jakarta EE) and Java SE specifications.
ORM frameworks like Hibernate implement the JPA specification, making it easier to switch between different ORM
implementations.

### 5)  Connection Pooling:

Establishing a new database connection for every request can be resource-intensive and impact performance.
Connection pooling allows for the reuse of existing database connections instead of creating new ones for each request.
Connection pooling frameworks manage a pool of pre-established database connections, reducing connection overhead and
improving scalability.
Examples of Java connection pooling frameworks include Apache Commons DBCP, HikariCP, and C3P0.
## ORM Layer

![](https://4.bp.blogspot.com/-oS8h3K3NVVg/W_0yF7WXm0I/AAAAAAAAE_Y/4ROZk3akTmgJt3N42oQUoMtHfvs9odAyQCLcBGAs/s1600/architecture.PNG)