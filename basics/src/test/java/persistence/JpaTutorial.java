package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JpaTutorial {

    public static void main(String[] args) {
        String url = "jdbc:database://localhost:port/database_name";
        String username = "your_username";
        String password = "your_password";

        try {
            Class.forName("com.example.DriverClass");
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            // Execute a SELECT query
            String selectQuery = "SELECT * FROM my_table";
            ResultSet resultSet = statement.executeQuery(selectQuery);

            // Process the result set
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                // Process retrieved data
            }

            resultSet.close();

            // Execute an INSERT query
            String insertQuery = "INSERT INTO my_table (name) VALUES ('John')";
            int rowsAffected = statement.executeUpdate(insertQuery);
            // Process the number of rows affected

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
