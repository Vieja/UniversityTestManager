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

    private ResultSet selectALL(String relation){
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
        main.getObserListPytania().clear();
        try{
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
        ResultSet rsStudent = selectALL("studenci");
        main.getObserListStudents().clear();
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
        } finally {
            if (rsStudent != null) {
                try {
                    rsStudent.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
        }
        return null;
    }

    public Exception selectZestawy() {
        ResultSet rsZestaw = null;
        try {
            stmt = conn.createStatement();
            rsZestaw = stmt.executeQuery("select zes.id_zes, zes.nazwa, zes.data_egz, zes.termin, \n" +
                                        "(       select SUM(zad.punkty)\n" +
                                        "        from zawartosc zaw, zadania zad\n" +
                                        "        where zaw.id_zes = zes.id_zes\n" +
                                        "        and zaw.id_zad = zad.id_zad\n" +
                                        "        group by zes.nazwa\n" +
                                        ") as suma \n" +
                                        "from zestawy zes");
        } catch (SQLException exception) {
            System.out.println("Couldn't execute SELECT query.");
        }
        main.getObserListZestawy().clear();
        try{
            while (rsZestaw.next()) {
                Zestaw zestaw = new Zestaw(
                        rsZestaw.getInt(1), rsZestaw.getString(2),
                        rsZestaw.getString(3), rsZestaw.getString(4),
                        rsZestaw.getFloat(5));
                main.getObserListZestawy().add(zestaw);
            }
        } catch (Exception exception) {
            System.out.println(exception);
            return exception;
        } finally {
            if (rsZestaw != null) {
                try {
                    rsZestaw.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
        }
        return null;
    }

    public Exception selectPodejscia(int id_zes) {
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
                Podejscie pod = new Podejscie(student, id_zes, rs.getString(2));
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

    public List<String> selectStringList(String sqlSelectCode) {
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlSelectCode);
            List<String> results = new ArrayList<>();
            while (rs.next()) {
                results.add(rs.getString(1));
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

    public Exception selectPytania(int id_zes) {
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select zad.id_zad, zad.tresc, zad.punkty from zawartosc zaw, zadania zad "+
                    "where zaw.id_zad = zad.id_zad and zaw.id_zes = "+id_zes);
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
}

