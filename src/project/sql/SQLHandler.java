package project.sql;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLHandler {

    private Connection conn;

    public boolean connect(String login, String password) {
        Properties connectionProps = new Properties();
        connectionProps.put("user", login);
        connectionProps.put("password", password);
        try {
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@//admlab2.cs.put.poznan.pl:1521/dblab02_students.cs.put.poznan.pl",
                    connectionProps);
            System.out.println("Connection with database established.");
            return true;
        } catch (Exception exception) {
            // System.out.println("Couldn't connect with database.");
            System.out.println("Couldn't establish connection. Emergency start.");
            exception.printStackTrace();
            // System.exit(1);
            return false;
        }
    }

}

