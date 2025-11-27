

## Java ORM Frameworks
A persistent framework is an ORM service that stores and retrieves objects into a relational database.
- Enterprise JavaBeans Entity Beans
- Java Data Objects
- Castor
- TopLink
- Spring DAO
- Hibernate


## Hibernate Advantages

- Hibernate takes care of mapping Java classes to database tables using XML files and without writing any line of code.

- Provides simple APIs for storing and retrieving Java objects directly to and from the database.

- If there is change in the database or in any table, then you need to change the XML file properties only.

## Supported Databases
Hibernate supports almost all the major RDBMS. Following is a list of few of the database engines supported by Hibernate −

- HSQL Database Engine
- DB2/NT
- MySQL
- PostgreSQL ...


## SessionFactory
- **Role**: It's a factory of Session objects and client of Configuration. It holds second-level cache (optional) of data.
- **Lifespan**: The SessionFactory is typically created at the start of the application and destroyed at the end. There should be one SessionFactory instance per database in an application. It is thread-safe and is often shared among threads in an application.
- **Creation**: It's created using the Configuration object, which contains the database and mapping details.