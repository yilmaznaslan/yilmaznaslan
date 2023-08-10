# Creating a JDBC Application

There are following six steps involved in building a JDBC application −

- Import the packages − Requires that you include the packages containing the JDBC classes needed for database
programming. Most often, using import java.sql.* will suffice.

- **Open a connection** − Requires using the `DriverManager.getConnection()` method to create a `Connection` object, which
represents a **physical** connection with the database.

- **Execute a query** − Requires using an object of type `Statement` for **building and submitting an SQL statement** to the
database.

-  **Extract data from result set** − Requires that you use the appropriate ResultSet.getXXX() method to retrieve the data from
the result set.

- Clean up the environment − Requires explicitly closing all database resources versus relying on the JVM's garbage
collection.

```java
import java.sql.*;

public class FirstExample {
   static final String DB_URL = "jdbc:mysql://localhost/TUTORIALSPOINT";
   static final String USER = "guest";
   static final String PASS = "guest123";
   static final String QUERY = "SELECT id, first, last, age FROM Employees";

   public static void main(String[] args) {
      // Open a connection
      try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(QUERY);) {
         // Extract data from result set
         while (rs.next()) {
            // Retrieve by column name
            System.out.print("ID: " + rs.getInt("id"));
            System.out.print(", Age: " + rs.getInt("age"));
            System.out.print(", First: " + rs.getString("first"));
            System.out.println(", Last: " + rs.getString("last"));
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } 
   }
}
```