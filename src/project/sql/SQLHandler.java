package project.sql;

import project.Main;
import project.classes.Podejscie;
import project.classes.Pytanie;
import project.classes.Student;
import project.classes.Zestaw;

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

    public Exception selectPytania() {
        ResultSet rsPytanie = selectALL("zadania");
        main.getObserListPytania().removeAll();
        try{
            while (rsPytanie.next()) {
                Pytanie pytanie = new Pytanie(
                        rsPytanie.getInt(1), rsPytanie.getString(2),
                        rsPytanie.getFloat(3));
                main.getObserListPytania().add(pytanie);
            }
        } catch (Exception exception) {
            return exception;
        }
        return null;
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

    public Exception selectZestawy() {
        ResultSet rsZestaw = selectALL("zestawy");
        main.getObserListZestawy().removeAll();
        try{
            while (rsZestaw.next()) {
                Zestaw zestaw = new Zestaw(
                        rsZestaw.getInt(1), rsZestaw.getString(2),
                        rsZestaw.getString(3), rsZestaw.getString(4));
                main.getObserListZestawy().add(zestaw);
            }
        } catch (Exception exception) {
            return exception;
        }
        return null;
    }

    public Exception selectPodejscia(int id_zes) {
        Statement stmt;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select p.indeks, p.ocena, s.imie, s.nazwisko "+
                            "from podejscia p, studenci s where p.indeks = s.indeks and id_zes = "+id_zes);
        } catch (SQLException exception) {
            System.out.println("Couldn't execute SELECT query.");
        }
        try{
            while (rs.next()) {
                Student student = new Student(rs.getInt(1),rs.getString(3),
                        rs.getString(4),null,null);
                Podejscie pod = new Podejscie(student, id_zes);
                main.getObserListPodejscia().add(pod);
            }
        } catch (Exception exception) {
            return exception;
        }
        return null;
    }

    public int insertInto (String request){
        Statement stmt;
        int rows;
        try {
            stmt = conn.createStatement();
            System.out.println(request);
            rows = stmt.executeUpdate(request);
            System.out.println(rows + " for 'INSERT'");
        } catch (SQLException exception) {
            System.out.println("Couldn't execute INSERT INTO query.");
            System.out.println("Error Code: " + exception.getErrorCode());
            System.out.println("SQLState: " + exception.getSQLState());
            return exception.getErrorCode();
        }
        return 0;

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

    public int updateWhere (String request){
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
            return exception.getErrorCode();
        }
        return 0;
    }

    public List<Integer> selectIntegers(String sqlSelectCode){
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

    public List<String> selectStringList(String sqlSelectCode) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sqlSelectCode);
            List<String> results = new ArrayList<>();
            while (resultSet.next()) {
                results.add(resultSet.getString(1));
            }
            return results;
        } catch (SQLException exception) {
            String title = "SQLException";
            String content = "Couldn't execute SELECT DISTINCT DATA_EGZ query.\n" +
                    "Error Code: " + exception.getErrorCode() + "\n" +
                    "SQLState: " + exception.getSQLState();
            System.out.println(content);
            return null;
        }
    }

    public int getID(String zadania_sequence) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT current_value FROM sys.sequences WHERE name = '"+zadania_sequence+"'");
            resultSet.next();
            int result = resultSet.getInt(1);
            return result;
        } catch (SQLException exception) {
            String title = "SQLException";
            String content = "Couldn't execute SELECT DISTINCT DATA_EGZ query.\n" +
                    "Error Code: " + exception.getErrorCode() + "\n" +
                    "SQLState: " + exception.getSQLState();
            System.out.println(content);
            return -1;
        }
    }
}

