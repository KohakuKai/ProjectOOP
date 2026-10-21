
package Simula;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        // Test the database connection
        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            System.out.println(
                    "Connected to PharmacyDB successfully!"
            );

        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            System.out.println(e.getMessage());
            return;
        }

        // Start the existing application
        StartUp system = new StartUp();
        system.start();
    }
}
