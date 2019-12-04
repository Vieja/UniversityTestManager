package project.sql;

import java.sql.*;
import java.util.Properties;

public class SQLHandler {

    private Connection conn;

    public int connect(String login, String password) {
        Properties connectionProps = new Properties();
        connectionProps.put("user", login);
        connectionProps.put("password", password);
        try {
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@//admlab2.cs.put.poznan.pl:1521/dblab02_students.cs.put.poznan.pl",
                    connectionProps);
            System.out.println("Connection with database established.");
            return 0;
        } catch (Exception exception) {
            System.out.println("Couldn't connect with database.");
            //exception.printStackTrace();
            if(exception.getCause() == null) {
                System.out.println("Błędny login lub hasło");
                return 1;
            } else {
                System.out.println("Błąd przy próbie połączenia");
                return 2;
            }
        }
    }

}

