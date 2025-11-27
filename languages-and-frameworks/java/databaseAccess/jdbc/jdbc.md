# Java Database  Connection

## What is JDBC

The JDBC library includes APIs for each of the tasks mentioned below that are commonly associated with database usage.

- Making a connection to a database.
- Creating SQL or MySQL statements.
- Executing SQL or MySQL queries in the database.
- Viewing & Modifying the resulting records.

## JDBC Architecture

In general, JDBC Architecture consists of two layers;

- JDBC API − This provides the application-to-JDBC Manager connection.
- JDBC Driver API − This supports the JDBC Manager-to-Driver Connection.

**The JDBC API** uses a driver manager and database-specific drivers to provide transparent connectivity to
heterogeneous databases.JDBC drivers implement the defined interfaces in the JDBC API, for interacting with your
database server. For example, using JDBC drivers enable you to open database connections and to interact with it by
sending SQL or database commands then receiving results with Java.

**The JDBC driver manager** ensures that the correct driver is used to access each data source. The driver manager is
capable of supporting multiple concurrent drivers connected to multiple heterogeneous databases.

![](https://www.tutorialspoint.com/jdbc/images/jdbc_architecture.jpg)

## Common JDBC Components

The JDBC API provides the following interfaces and classes −

- **DriverManager** − This class manages a list of database drivers. Matches connection requests from the java
  application with the proper database driver using communication sub protocol. The first driver that recognizes a
  certain subprotocol under JDBC will be used to establish a database Connection.

- **Driver** − This interface handles the communications with the database server. You will interact directly with
  Driver objects very rarely. Instead, you use DriverManager objects, which manages objects of this type. It also
  abstracts the details associated with working with Driver objects.

- **Connection** − This interface with all methods for contacting a database. The connection object represents
  communication context, i.e., all communication with database is through connection object only.

- **Statement** − You use objects created from this interface to submit the SQL statements to the database. Some derived
  interfaces accept parameters in addition to executing stored procedures.

- **ResultSet** − These objects hold data retrieved from a database after you execute an SQL query using Statement
  objects. It acts as an iterator to allow you to move through its data.

SQLException − This class handles any errors that occur in a database application.

## Packages

The **java.sql** and **javax.sql** are the primary packages for JDBC 4.0.