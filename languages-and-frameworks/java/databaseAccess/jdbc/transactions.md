# JDBC - Transactions

If your JDBC Connection is in auto-commit mode, which it is by default, then every SQL statement is committed to the
database upon its completion.

That may be fine for simple applications, but there are three reasons why you may want to turn off the auto-commit and
manage your own transactions −

- To increase performance.

- To maintain the integrity of business processes.

- To use distributed transactions.

Transactions enable you to control if, and when, changes are applied to the database. It treats a single SQL statement
or a group of SQL statements as one logical unit, and if any statement fails, the whole transaction fails.

To enable manual- transaction support instead of the auto-commit mode that the JDBC driver uses by default, use the
Connection object's setAutoCommit() method. If you pass a boolean false to setAutoCommit( ), you turn off auto-commit.
You can pass a boolean true to turn it back on again.

For example, if you have a Connection object named conn, code the following to turn off auto-commit −

conn.setAutoCommit(false);

## Commit & Rollback

Once you are done with your changes and you want to commit the changes then call commit() method on connection object as
follows −

`conn.commit( );`

Otherwise, to roll back updates to the database made using the Connection named conn, use the following code −

`conn.rollback( );`

The following example illustrates the use of a commit and rollback object −

```
java
try{
//Assume a valid connection object conn
conn.setAutoCommit(false);
Statement stmt = conn.createStatement();

String SQL = "INSERT INTO Employees  " +
"VALUES (106, 20, 'Rita', 'Tez')";
stmt.executeUpdate(SQL);  
//Submit a malformed SQL statement that breaks
String SQL = "INSERTED IN Employees  " +
"VALUES (107, 22, 'Sita', 'Singh')";
stmt.executeUpdate(SQL);
// If there is no error.
conn.commit();
}catch(SQLException se){
// If there is any error.
conn.rollback();
}
```

In this case, none of the above INSERT statement would success and everything would be rolled back.