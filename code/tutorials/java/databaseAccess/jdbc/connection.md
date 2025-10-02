# Database Connection

After you've installed the appropriate driver, it is time to establish a database connection using JDBC.

The programming involved to establish a JDBC connection is fairly simple. Here are these simple four steps −

Import JDBC Packages − Add import statements to your Java program to import required classes in your Java code.

Register JDBC Driver − This step causes the JVM to load the desired driver implementation into memory so it can fulfill
your JDBC requests.

Database URL Formulation − This is to create a properly formatted address that points to the database to which you wish
to connect.

Create Connection Object − Finally, code a call to the DriverManager object's getConnection( ) method to establish
actual database connection.


## Closing JDBC Connections

At the end of your JDBC program, it is required explicitly to close all the connections to the database to end each
database session. However, if you forget, Java's garbage collector will close the connection when it cleans up stale
objects.

Relying on the garbage collection, especially in database programming, is a very poor programming practice. You should
make a habit of always closing the connection with the close() method associated with connection object.

To ensure that a connection is closed, you could provide a 'finally' block in your code. A finally block always
executes, regardless of an exception occurs or not.

To close the above opened connection, you should call close() method as follows −

conn.close();
Explicitly closing a connection conserves DBMS resources, which will make your database administrator happy.