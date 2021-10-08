package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {

    private static final String databaseName = "october_appointments";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + databaseName;
    private static final String username = "root";
    private static final String password = "mountainspring7";
    static Connection conn;


    public static Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection(DB_URL, username, password);
            //System.out.println("Connection successful");
            return conn;
        } catch (SQLException throwables) {
            System.out.println("Error: " + throwables.getMessage());
            return null;
        }
    }





}

