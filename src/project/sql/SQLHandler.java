package project.sql;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.Main;
import project.classes.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SQLHandler {

    private Main main;
    private Connection conn;
    private Statement stmt;

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

    public Exception selectPytania() {
        ResultSet rsPytanie = null;
        main.getObserListPytania().clear();
        try{
            stmt = conn.createStatement();
            rsPytanie = stmt.executeQuery("select * from zadania order by id_zad");
            while (rsPytanie.next()) {
                Pytanie pytanie = new Pytanie(
                        rsPytanie.getInt(1), rsPytanie.getString(2),
                        rsPytanie.getFloat(3));
                main.getObserListPytania().add(pytanie);
            }
        } catch (Exception exception) {
            return exception;
        } finally {
            if (rsPytanie != null) {
                try {
                    rsPytanie.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
        }
        return null;
    }

    public Exception selectStudenci() {
        ResultSet rsStudent = null;
        main.getObserListStudents().clear();
        try{
            stmt = conn.createStatement();
            rsStudent = stmt.executeQuery("select * from studenci order by indeks");
            while (rsStudent.next()) {
                Student student = new Student(
                        rsStudent.getInt(1), rsStudent.getString(2),
                        rsStudent.getString(3), rsStudent.getString(4),
                        rsStudent.getString(5));
                main.getObserListStudents().add(student);
            }
        } catch (Exception exception) {
            return exception;
        } finally {
            if (rsStudent != null) {
                try {
                    rsStudent.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
        }
        return null;
    }

    public Exception selectGrupy() {
        ResultSet rs = null;
        main.getObserListGrupy().clear();
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from grupy order by zes_nazwa");
            while (rs.next()) {
                Grupa grupa = new Grupa(
                        rs.getInt(1), rs.getString(2),
                        rs.getString(3));
                main.getObserListGrupy().add(grupa);
            }
        } catch (Exception exception) {
            return exception;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
        }
        return null;
    }

    public Exception selectEgzaminy() {
        ResultSet rs = null;
        main.getObserListEgzaminy().clear();
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from egzaminy order by data_egz");
            while (rs.next()) {
                Egzamin egzamin = new Egzamin(
                        rs.getString(1), rs.getString(2));
                        main.getObserListEgzaminy().add(egzamin);
            }
        } catch (Exception exception) {
            return exception;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
        }
        return null;
    }

    public Exception selectZestawy() {
        ResultSet rs = null;
        main.getObserListZestawy().clear();
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select zes_nazwa, to_char(data_utw, 'DD-MM-YYYY') from zestawy order by zes_nazwa");
            while (rs.next()) {
                Zestaw zestaw = new Zestaw(rs.getString(1), rs.getString(2));
                main.getObserListZestawy().add(zestaw);
            }
        } catch (Exception exception) {
            return exception;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
        }
        return null;
    }

    public Exception selectPodejscia(int id) {
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select p.indeks, p.ocena, s.imie, s.nazwisko "+
                            "from podejscia p, studenci s where p.indeks = s.indeks and id_grupy = "+id);
        } catch (SQLException exception) {
            System.out.println("Couldn't execute SELECT query.");
        }
        try{
            while (rs.next()) {
                Student student = new Student(rs.getInt(1),rs.getString(3),
                        rs.getString(4),null,null);
                Podejscie pod = new Podejscie(student, id, rs.getString(2));
                main.getObserListPodejscia().add(pod);
            }
        } catch (Exception exception) {
            return exception;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
        }
        return null;
    }

    public int insertInto (String request){
        int rows;
        try {
            stmt = conn.createStatement();
            conn.setAutoCommit(false);
            System.out.println(request);
            rows = stmt.executeUpdate(request);
            System.out.println(rows + " for 'INSERT'");
            conn.commit();
        } catch (SQLException exception) {
            System.out.println("Couldn't execute INSERT INTO query.");
            System.out.println("Error Code: " + exception.getErrorCode());
            System.out.println("SQLState: " + exception.getSQLState());
            return exception.getErrorCode();
        }
        return 0;

    }

    public void deleteFrom (String request){
        int rows;
        try {
            stmt = conn.createStatement();
            conn.setAutoCommit(false);
            System.out.println(request);
            rows = stmt.executeUpdate(request);
            System.out.println(rows + " for 'DELETE'");
            conn.commit();
        } catch (SQLException exception) {
            System.out.println("Couldn't execute DELETE FROM query.");
            System.out.println("Error Code: " + exception.getErrorCode());
            System.out.println("SQLState: " + exception.getSQLState());
        }
    }

    public int updateWhere (String request){
        int rows;
        try {
            stmt = conn.createStatement();
            conn.setAutoCommit(false);
            System.out.println(request);
            rows = stmt.executeUpdate(request);
            System.out.println(rows + " for 'UPDATE'");
            conn.commit();
        } catch (SQLException exception) {
            System.out.println("Couldn't execute UPDATE WHERE query.");
            System.out.println("Error Code: " + exception.getErrorCode());
            System.out.println("SQLState: " + exception.getSQLState());
            return exception.getErrorCode();
        }
        return 0;
    }

    public List<Integer> selectIntegers(String sqlSelectCode){
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            System.out.println(sqlSelectCode);
            rs = stmt.executeQuery(sqlSelectCode);
            List<Integer> results = new ArrayList<>();
            while (rs.next()) {
                results.add(rs.getInt(1));
            }
            return results;
        } catch (SQLException exception) {
            String title = "SQLException";
            String content = "Couldn't execute SELECT WHERE query.\n" +
                    "Error Code: " + exception.getErrorCode() + "\n" +
                    "SQLState: " + exception.getSQLState();
            System.out.println(content);
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
        }
    }

    public ObservableList<String> selectStringList(String sqlSelectCode) {
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlSelectCode);
            ObservableList<String> results = FXCollections.observableArrayList();
            while (rs.next()) {
                String nowy = rs.getString(1);
                results.add(nowy);
            }
            return results;
        } catch (SQLException exception) {
            String title = "SQLException";
            String content = "Couldn't execute SELECT DISTINCT DATA_EGZ query.\n" +
                    "Error Code: " + exception.getErrorCode() + "\n" +
                    "SQLState: " + exception.getSQLState();
            System.out.println(content);
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
        }
    }

    public int getID(String sequence) {
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT "+sequence+".currval S FROM dual");
            rs.next();
            int result = rs.getInt(1);
            return result;
        } catch (SQLException exception) {
            String title = "SQLException";
            String content = "Couldn't execute SELECT sequence query.\n" +
                    "Error Code: " + exception.getErrorCode() + "\n" +
                    "SQLState: " + exception.getSQLState();
            System.out.println(content);
            return -1;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
        }
    }

    public Exception selectPytania(String nazwa) {
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select zad.id_zad, zad.tresc, zad.punkty from zawartosc zaw, zadania zad "+
                    "where zaw.id_zad = zad.id_zad and zaw.zes_nazwa = '"+nazwa+"'");
        } catch (SQLException exception) {
            System.out.println("Couldn't execute SELECT query.");
        }
        try{
            while (rs.next()) {
                Pytanie pytanie = new Pytanie(rs.getInt(1),rs.getString(2),
                        rs.getFloat(3));
                main.getObserListPytania().add(pytanie);
            }
        } catch (Exception exception) {
            return exception;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
        }
        return null;
    }

    public float selectLiczbaPunktowZestawu(int id) {
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select SUM(zad.punkty)\n" +
                    "from zawartosc zaw, zadania zad, zestawy zes\n" +
                    "where zaw.id_zes = zes.id_zes\n" +
                    "and zaw.id_zad = zad.id_zad\n" +
                    "and zes.id_zes = " + id + "\n" +
                    "group by zes.nazwa");
            float odp = 0;
            while (rs.next()) {
                odp = rs.getFloat(1);
                return odp;
            }
            return odp;
        } catch (SQLException exception) {
            System.out.println("Couldn't execute SELECT query.");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
        }

        return -1;
    }

    public Exception wprowadzOcenyStudentom1(String data, String nazwa) {
        try {
            CallableStatement cstmt = conn.prepareCall("{CALL zaktualizuj_ocene1(?,?)}");
            cstmt.setTimestamp(1, Timestamp.valueOf(data));
            cstmt.setString(2, nazwa);
            cstmt.executeUpdate();
            cstmt.close();
        } catch (SQLException exception) {
            System.out.println("Couldn't execute procedure.");
            System.out.println(exception.getErrorCode());
            return exception;
        }
        return null;
    }

    public Exception wprowadzOcenyStudentom2(String data, String nazwa) {
        try {
            CallableStatement cstmt = conn.prepareCall("{CALL zaktualizuj_ocene2(?,?)}");
            cstmt.setTimestamp(1, Timestamp.valueOf(data));
            cstmt.setString(2, nazwa);
            cstmt.executeUpdate();
            cstmt.close();
        } catch (SQLException exception) {
            System.out.println("Couldn't execute procedure.");
            System.out.println(exception.getErrorCode());
            return exception;
        }
        return null;
    }

    public Exception usunTychCoZdali() {
        try {
            CallableStatement cstmt = conn.prepareCall("{CALL usun_tych_co_zdali}");
            cstmt.execute();
            cstmt.close();
        } catch (SQLException exception) {
            System.out.println("Couldn't execute procedure.");
            System.out.println(exception.getErrorCode());
            return exception;
        }
        return null;
    }

    public Exception czyscOceny() {
        try {
            CallableStatement cstmt = conn.prepareCall("{CALL czysc_oceny_powtarzajacym}");
            cstmt.executeUpdate();
            cstmt.close();
        } catch (SQLException exception) {
            System.out.println("Couldn't execute procedure.");
            System.out.println(exception.getErrorCode());
            return exception;
        }
        return null;
    }

    public String iluStudentowPodeszloWBazie(String data) {
        try {
            String wynik = null;
            CallableStatement cstmt = conn.prepareCall("{? = call iluStudentowPodeszloDoEgzaminu(?)}");
            cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.setTimestamp(2, Timestamp.valueOf(data));
            cstmt.execute();
            int liczba = cstmt.getInt(1);
            wynik = String.valueOf(liczba);
            cstmt.close();
            return wynik;
        } catch (SQLException exception) {
            System.out.println("Couldn't execute function.");
            System.out.println(exception.getErrorCode());
            return null;
        }
    }

    public String ilePunktowMaZestaw(String nazwa) {
        try {
            String wynik = null;
            CallableStatement cstmt = conn.prepareCall("{? = call ilePunktowMaZestaw(?)}");
            cstmt.registerOutParameter(1, Types.FLOAT);
            cstmt.setString(2, nazwa);
            cstmt.execute();
            float liczba = cstmt.getFloat(1);
            wynik = String.valueOf(liczba);
            cstmt.close();
            return wynik;
        } catch (SQLException exception) {
            System.out.println("Couldn't execute function.");
            System.out.println(exception.getErrorCode());
            return null;
        }
    }

    public String selectCurrentDate() {
        try {
            String date;
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT TO_CHAR(SYSDATE, 'DD-MM-YYYY') FROM dual");
            rs.next();
            date = rs.getString(1);
            return date;
        } catch (Exception e) {
            System.out.println("Problem with selecting current_date");
            return null;
        }
    }

    public String selectTerminEgzaminu(String data) {
        try {
            String date;
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT termin from egzaminy where data_egz = to_date('"+data+"','DD-MM-YYYY')");
            rs.next();
            date = rs.getString(1);
            return date;
        } catch (Exception e) {
            System.out.println("Problem with selecting termin egzaminu");
            return null;
        }
    }
}

