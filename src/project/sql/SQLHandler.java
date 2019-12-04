package project.sql;

import project.Main;
import project.classes.Pracownik;

import java.sql.*;
import java.util.Properties;

public class SQLHandler {

    private Main main;
    private Connection conn;

    public SQLHandler(Main main) {
        this.main = main;
    }

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
            exception.printStackTrace();
            if(exception.getCause() == null) {
                System.out.println("Błędny login lub hasło");
                return 1;
            } else {
                System.out.println("Błąd przy próbie połączenia");
                return 2;
            }
        }
    }

    private ResultSet selectALL(String relation){
        Statement stmt;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from " + relation);
        } catch (SQLException exception) {
            System.out.println("Couldn't execute SELECT query.");
        }
        return rs;

    }

    public Exception fillPracownik() {
        ResultSet rsWorker = selectALL("pracownicy");

        try{
            while (rsWorker.next()) {
                Pracownik worker = new Pracownik(
                        rsWorker.getInt(1), rsWorker.getString(2),
                        rsWorker.getString(3), rsWorker.getInt(4));
                main.getWorkers().add(worker);
            }
        } catch (Exception exception) {
            return exception;
        }
        return null;
    }

}

