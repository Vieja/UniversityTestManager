package project;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import project.classes.Podejscie;
import project.classes.Pytanie;
import project.classes.Student;
import project.classes.Zestaw;
import project.sql.SQLHandler;
import project.view.RootController;
import project.view.SignINController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private SQLHandler sqlHandler;

    private volatile ObservableList<Student> students = FXCollections.observableArrayList();
    private volatile ObservableList<Pytanie> pytania = FXCollections.observableArrayList();
    private volatile ObservableList<Zestaw> zestawy = FXCollections.observableArrayList();
    private volatile ObservableList<Podejscie> podejscia = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("University Tests Manager");
        this.primaryStage = primaryStage;

        signToDatabase();
    }

    public void initRootLayout(SQLHandler sql) {
        try {
            sqlHandler = sql;
            sqlHandler.selectStudenci();
            sqlHandler.selectPytania();
            sqlHandler.selectZestawy();
            primaryStage.close();
            primaryStage = new Stage();
            primaryStage.setTitle("University Tests Manager");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            RootController controller = loader.getController();
            controller.setMain(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void signToDatabase() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/SignIN.fxml"));
            BorderPane sign = loader.load();
            SignINController controller = loader.getController();
            controller.setMain(this);
            Scene scene = new Scene(sign);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Student> getObserListStudents() {
        return students;
    }

    public ObservableList<Pytanie> getObserListPytania() {
        return pytania;
    }

    public ObservableList<Zestaw> getObserListZestawy() {
        return zestawy;
    }

    public ObservableList<Podejscie> getObserListPodejscia() {
        return podejscia;
    }

    public ObservableList<Podejscie> getObserListPodejsciaZZestawu(Zestaw zestaw) {
        podejscia = FXCollections.observableArrayList();
        sqlHandler.selectPodejscia(zestaw.getId());
        return podejscia;
    }

    public ObservableList<Zestaw> getObserListZestawyKtoreSaZDaty(String data) {
        ObservableList<Zestaw> tmp = FXCollections.observableArrayList();
        String[] dane = data.split("-");
        for (Zestaw zestaw : zestawy) {
            if (zestaw.getData().equals(dane[2]+"-"+dane[1]+"-"+dane[0]+" 00:00:00.0")) tmp.add(zestaw);
        }
        return tmp;
    }

    public static void main(String[] args) {
        launch(args);
    }


    public void dodajStudentaDoBazy(Student student) {
        int error;
        try {
            String indeks = String.valueOf(student.getIndeks());
            String imie = student.getImie();
            String nazwisko = student.getNazwisko();
            String oc1 = student.getOcena_1();
            String oc2 = student.getOcena_2();
            if (oc1.equals("")) oc1 = null;
            else oc1 = "'" + oc1 + "'";
            if (oc2.equals("")) oc2 = null;
            else oc2 = "'" + oc2 + "'";
            error = sqlHandler.insertInto("INSERT INTO STUDENCI (INDEKS, IMIE, NAZWISKO, OCENA_1, OCENA_2) VALUES (" + indeks + ", '" + imie + "', '" + nazwisko + "', " + oc1 + ", " + oc2 + ")");
        } catch (NumberFormatException e) {
            error = -1;
        }
        switch (error) {
            case 0:
                getObserListStudents().add(student);
                break;
            case 2290:
                showError("Błąd dodawania","Naruszono więzy integralności. Sprawdź wprowadzone dane.");
                break;
            case 1:
                showError("Błąd dodawania","Istnieje już student z takim indeksem");
                break;
            case 12899:
                showError("Błąd dodawania","Zbyt duża wartość. Sprawdź wsprowadzone dane");
                break;
            case -1:
                showError("Błąd dodawania","Indeks musi być liczbą");
                break;

        }
    }

    public void edytujStudentaWBazie(Student wybrany, String imie, String nazw, String oc1, String oc2) {
        String id = String.valueOf(wybrany.getIndeks());
        String oc1_string = oc1;
        String oc2_string = oc2;
        if (oc1_string.equals("")) oc1_string = null; else oc1_string = "'"+oc1_string+"'";
        if (oc2_string.equals("")) oc2_string = null; else oc2_string = "'"+oc2_string+"'";
        int error = sqlHandler.updateWhere("UPDATE STUDENCI SET IMIE = '" + imie +"', NAZWISKO = '" + nazw + "', OCENA_1 = " + oc1_string +", OCENA_2 = " + oc2_string + " WHERE INDEKS = " + id);
        switch (error) {
            case 0:
                wybrany.setImie(imie);
                wybrany.setNazwisko(nazw);
                wybrany.setOcena_1(oc1);
                wybrany.setOcena_2(oc2);
                break;
            case 2290:
                showError("Błąd edycji","Naruszono więzy integralności. Sprawdź wprowadzone dane.");
                break;
            case 12899:
                showError("Błąd edycji","Zbyt duża wartość. Sprawdź wsprowadzone dane");
                break;
        }
    }

    public void usunStudentaZBazy(Student wybrany) {
        boolean czy = getObserListStudents().contains(wybrany);
        if (czy) {
            getObserListStudents().remove(wybrany);
            sqlHandler.deleteFrom("DELETE FROM STUDENCI WHERE INDEKS = " + wybrany.getIndeks());
        } else showError("Błąd usuwania","Nie wybrałeś żadnego studenta");

    }

    public void dodajPytanieDoBazy(String tresc, String punkty) {
        int error;
        float punkciki = 0;
        try {
            punkciki = Float.parseFloat(punkty);
            error = sqlHandler.insertInto("INSERT INTO ZADANIA (ID_ZAD, TRESC, PUNKTY) VALUES (zadania_sequence.nextval, '"+tresc+"', "+punkciki+")");
        } catch (NumberFormatException e) {
            error = -1;
        }
        switch (error) {
            case 0:
                int id_pyt = sqlHandler.getID("zadania_sequence");
                Pytanie pyt = new Pytanie(id_pyt, tresc, punkciki);
                getObserListPytania().add(pyt);
                break;
            case 2290:
                showError("Błąd dodawania", "Naruszono więzy integralności. Sprawdź wprowadzone dane.");
                break;
            case 12899:
                showError("Błąd dodawania","Zbyt duża wartość. Sprawdź wsprowadzone dane");
                break;
            case -1:
                showError("Błąd dodawania","Niepoprawna wartość punktów");
                break;
        }
    }

    public void edytujPytanieWBazie(Pytanie wybrany, String tresc, String punkty) {
        int error;
        float punkciki = 0;
        try {
            String id = String.valueOf(wybrany.getId());
            punkciki = Float.parseFloat(punkty);
            error = sqlHandler.updateWhere("UPDATE ZADANIA SET TRESC = '" + tresc +"', PUNKTY = " + punkty + " WHERE ID_ZAD = " + id);
        } catch (NumberFormatException e) {
            error = -1;
        }
        switch (error) {
            case 0:
                wybrany.setTresc(tresc);
                wybrany.setPunkty(punkciki);
                break;
            case 1438:
                showError("Błąd edycji","Naruszono więzy integralności. Sprawdź wprowadzone dane.");
                break;
            case -1:
                showError("Błąd edycji","Naruszono więzy integralności. Sprawdź wprowadzone dane.");
                break;
        }
    }

    public void usunPytanieZBazy(Pytanie wybrany) {
        boolean czy = getObserListPytania().contains(wybrany);
        if (czy) {
            getObserListPytania().remove(wybrany);
            sqlHandler.deleteFrom("DELETE FROM ZADANIA WHERE ID_ZAD = " + wybrany.getId());
        } else showError("Błąd usuwania","Nie wybrałeś żadnego pytania");
    }

    public List<Integer> selectIntegers(String sqlSelectCode) {
        return sqlHandler.selectIntegers(sqlSelectCode);
    }

    public List<String> selectDatyEgzaminow() {return sqlHandler.selectStringList("select distinct to_char(data_egz, 'DD-MM-YYYY day') from zestawy"); }
    public List<String> selectZestawyZDaty(String data) {return sqlHandler.selectStringList(
            "select nazwa || ' - ' || termin || ' termin' from zestawy where data_egz = to_date('"+data+"', 'DD-MM-YYYY day')" ); }

    public void showError(String title, String content) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(content);
        alert.showAndWait();

    }

    public void zmienOcenePodejscia(Podejscie wybrany, String oc1) {
        String id = String.valueOf(wybrany.getStudentIndeks());
        String id_zes = String.valueOf(wybrany.getId_zes());
        if (oc1.equals("")) oc1 = null;
        String oc11 = oc1;
        if (oc11 != null) oc11 = "'"+oc11+"'";
        int error = sqlHandler.updateWhere("UPDATE PODEJSCIA SET OCENA = " + oc11 +" WHERE INDEKS = " + id + " AND ID_ZES = "+id_zes);
        switch (error) {
            case 0:
                wybrany.setOcena(oc1);
                break;
            case 2290:
                showError("Błąd edycji","Naruszono więzy integralności. Sprawdź wprowadzone dane.");
                break;
            case 12899:
                showError("Błąd edycji","Zbyt duża wartość. Sprawdź wsprowadzone dane");
                break;
        }
    }

    public void usunPodejscieZBazy(Podejscie wybrany) {
        boolean czy = getObserListPodejscia().contains(wybrany);
        if (czy) {
            getObserListPodejscia().remove(wybrany);
            sqlHandler.deleteFrom("DELETE FROM PODEJSCIA WHERE INDEKS = " + wybrany.getStudentIndeks() + " AND ID_ZES = " + wybrany.getId_zes());
        } else showError("Błąd usuwania","Nie wybrałeś żadnego studenta");
    }

    public void dodajPodejscieDoBazy(Student student, String data, String zestaw) {
        data = data.split(" ")[0];
        String nazwa = zestaw.split(" ")[0];
        String indeks = String.valueOf(student.getIndeks());
        List<Integer> list_id_zes = sqlHandler.selectIntegers("select distinct p.id_zes\n" +
                "from podejscia p, zestawy z\n" +
                "where p.id_zes = z.id_zes\n" +
                "and z.data_egz = to_date('" + data + "', 'DD-MM-YYYY')\n" +
                "and z.nazwa = '" + nazwa + "'");
        int error;
        try {
            String id_zes = String.valueOf(list_id_zes.get(0));
            error = sqlHandler.insertInto("INSERT INTO PODEJSCIA (INDEKS, ID_ZES) VALUES (" + indeks + ", " + id_zes + ")");
        } catch (NumberFormatException e) {
            error = -1;
        }
        switch (error) {
            case 0:
                getObserListStudents().add(student);
                break;
            case 2290:
                showError("Błąd dodawania","Naruszono więzy integralności. Sprawdź wprowadzone dane.");
                break;
            case 1:
                showError("Błąd dodawania","Student już został przydzielony do tego podejścia");
                break;
            case 12899:
                showError("Błąd dodawania","Zbyt duża wartość. Sprawdź wsprowadzone dane");
                break;
            case -1:
                showError("Błąd dodawania","Indeks musi być liczbą");
                break;

        }
    }

    public ObservableList getObserListPytaniaZZestawu(Zestaw zestaw) {
        pytania = FXCollections.observableArrayList();
        sqlHandler.selectPytania(zestaw.getId());
        return pytania;
    }

    public void usunZawartosc(Pytanie wybranePytanie, Zestaw wybranyZestaw) {
        boolean czy = getObserListPytania().contains(wybranePytanie);
        if (czy) {
            getObserListPytania().remove(wybranePytanie);
            String id_zad = String.valueOf(wybranePytanie.getId());
            String id_zes = String.valueOf(wybranyZestaw.getId());
            sqlHandler.deleteFrom("DELETE FROM ZAWARTOSC WHERE ID_ZES = " + id_zes + " AND ID_ZAD = " + id_zad);
        } else showError("Błąd usuwania","Nie wybrałeś żadnego pytania");
    }

    public void updateObserbableListZestawLiczbaPunktow(Zestaw wybranyZestaw) {
        float nowe;
        zestawy.remove(wybranyZestaw);
        nowe = sqlHandler.selectLiczbaPunktowZestawu(wybranyZestaw.getId());
        if (nowe != -1)  {
            wybranyZestaw.setLiczbaPunktow(nowe);
        }
        zestawy.add(wybranyZestaw);
    }

    public void dodajZestawDoBazy(String data, String nazwa, String termin, String data_wyswietlana) {
        int error;
            error = sqlHandler.insertInto("INSERT INTO ZESTAWY (ID_ZES, NAZWA, DATA_EGZ, TERMIN) VALUES (zestawy_sequence.nextval, '"+nazwa+"', to_date('"+data+"','DD-MM-YYYY'), '"+termin+"')");
        switch (error) {
            case 0:
                int id_zes = sqlHandler.getID("zestawy_sequence");
                Zestaw zes = new Zestaw(id_zes, nazwa, data, termin, 0);
                if (data_wyswietlana.equals(data)) getObserListZestawy().add(zes);
                break;
            case 2290:
                showError("Błąd dodawania", "Naruszono więzy integralności. Sprawdź wprowadzone dane.");
                break;
            case 12899:
                showError("Błąd dodawania","Zbyt duża wartość. Sprawdź wsprowadzone dane");
                break;
            case 942:
                showError("Błąd dodawania","Niepoprawna wartość punktów");
                break;
        }
    }

    public void dodajPytanieDoZestawu(Pytanie pytanie, String data, String zestaw) {
        data = data.split(" ")[0];
        String nazwa = zestaw.split(" ")[0];
        String indeks = String.valueOf(pytanie.getId());
        List<Integer> list_id_zes = sqlHandler.selectIntegers("select distinct p.id_zes\n" +
                "from podejscia p, zestawy z\n" +
                "where p.id_zes = z.id_zes\n" +
                "and z.data_egz = to_date('" + data + "', 'DD-MM-YYYY')\n" +
                "and z.nazwa = '" + nazwa + "'");
        int error;
        try {
            String id_zes = String.valueOf(list_id_zes.get(0));
            error = sqlHandler.insertInto("INSERT INTO ZAWARTOSC (ID_ZAD, ID_ZES) VALUES (" + pytanie.getId() + ", " + id_zes + ")");
        } catch (NumberFormatException e) {
            error = -1;
        }
        switch (error) {
            case 2290:
                showError("Błąd dodawania","Naruszono więzy integralności. Sprawdź wprowadzone dane.");
                break;
            case 1:
                showError("Błąd dodawania","Zadanie znajduje się już w danym zestawie");
                break;

        }
    }
}