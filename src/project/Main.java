package project;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import project.classes.Pytanie;
import project.classes.Student;
import project.sql.SQLHandler;
import project.view.RootController;
import project.view.SignINController;

import java.io.IOException;
import java.util.List;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private SQLHandler sqlHandler;

    private volatile ObservableList<Student> students = FXCollections.observableArrayList();
    private volatile ObservableList<Pytanie> pytania = FXCollections.observableArrayList();

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

    public static void main(String[] args) {
        launch(args);
    }

    public void usunStudentaZBazy(Student wybrany) {
        getObserListStudents().remove(wybrany);
        sqlHandler.deleteFrom("DELETE FROM STUDENCI WHERE INDEKS = " + wybrany.getIndeks());
    }

    public void usunPytanieZBazy(Pytanie wybrany) {
        getObserListPytania().remove(wybrany);
        sqlHandler.deleteFrom("DELETE FROM ZADANIA WHERE ID = " + wybrany.getId());
    }

    public void edytujStudentaWBazie(Student wybrany, String id, String imie, String nazw, String oc1, String oc2) {
        int position = getObserListStudents().indexOf(wybrany);
        getObserListStudents().remove(wybrany);
        if (oc1.equals("")) oc1 = null;
        if (oc2.equals("")) oc2 = null;
        sqlHandler.updateWhere("UPDATE STUDENCI SET IMIE = '" + imie +"', NAZWISKO = '" + nazw + "', OCENA_1 = " + oc1 +", OCENA_2 = " + oc2 + " WHERE INDEKS = " + id);
        wybrany.setIndeks(Integer.parseInt(id));
        wybrany.setImie(imie);
        wybrany.setNazwisko(nazw);
        wybrany.setOcena_1(oc1);
        wybrany.setOcena_2(oc2);
        getObserListStudents().add(position, wybrany);
    }

    public void edytujPytanieWBazie(Pytanie wybrany, String tresc, String punkty) {
        int position = getObserListPytania().indexOf(wybrany);
        getObserListPytania().remove(wybrany);
        String id = String.valueOf(wybrany.getId());
        Exception update_error = sqlHandler.updateWhere("UPDATE ZADANIA SET TRESC = '" + tresc +"', PUNKTY = " + punkty + " WHERE ID_ZAD = " + id);
        if (update_error == null) {
            wybrany.setId(Integer.parseInt(id));
            wybrany.setTresc(tresc);
            wybrany.setPunkty(Float.parseFloat(punkty));
            getObserListPytania().add(position, wybrany);
        }
    }

    public List<Integer> sqlSelect(String sqlSelectCode) {
        return sqlHandler.searchWhere(sqlSelectCode);
    }
}
