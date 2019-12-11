package project.sql;

import project.Main;
import project.classes.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

    public Exception selectStudenci() {
        ResultSet rsStudent = selectALL("studenci");
        main.getObserListStudents().removeAll();
        try{
            while (rsStudent.next()) {
                Student student = new Student(
                        rsStudent.getInt(1), rsStudent.getString(2),
                        rsStudent.getString(3), rsStudent.getString(4),
                        rsStudent.getString(5));
                main.getObserListStudents().add(student);
            }
        } catch (Exception exception) {
            return exception;
        }
        return null;
    }

    public void deleteFrom (String request){
        Statement stmt;
        int rows;
        try {
            stmt = conn.createStatement();
            System.out.println(request);
            rows = stmt.executeUpdate(request);
            System.out.println(rows + " for 'DELETE'");
        } catch (SQLException exception) {
            System.out.println("Couldn't execute DELETE FROM query.");
            System.out.println("Error Code: " + exception.getErrorCode());
            System.out.println("SQLState: " + exception.getSQLState());
        }
    }

    public void updateWhere (String request){
        Statement stmt;
        int rows;
        try {
            stmt = conn.createStatement();
            System.out.println(request);
            rows = stmt.executeUpdate(request);
            System.out.println(rows + " for 'UPDATE'");
        } catch (SQLException exception) {
            System.out.println("Couldn't execute UPDATE WHERE query.");
            System.out.println("Error Code: " + exception.getErrorCode());
            System.out.println("SQLState: " + exception.getSQLState());
        }
    }

    public List<Integer> searchWhere (String sqlSelectCode){
        try {
            Statement stmt = conn.createStatement();
            System.out.println(sqlSelectCode);
            ResultSet resultSet = stmt.executeQuery(sqlSelectCode);
            List<Integer> results = new ArrayList<>();
            while (resultSet.next()) {
                results.add(resultSet.getInt(1));
//                System.out.println(results.get(results.size() - 1));
            }
            return results;
        } catch (SQLException exception) {
            String title = "SQLException";
            String content = "Couldn't execute SELECT WHERE query.\n" +
                    "Error Code: " + exception.getErrorCode() + "\n" +
                    "SQLState: " + exception.getSQLState();
            System.out.println(content);
            return null;
        }
    }
}

