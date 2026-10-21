package Simula;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL =
            "jdbc:sqlserver://localhost:62217;"
                    + "databaseName=PharmacyDB;"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;"
                    + "integratedSecurity=true;";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
