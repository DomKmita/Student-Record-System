package com.example.assignment1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class dbConnection {
    private static final String SQCONN = "jdbc:sqlite:studentDB.sqlite";

    /**
     * @return null. A getter for the SQLite DB Connection
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException{
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(SQCONN);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
