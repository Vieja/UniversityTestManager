package project.classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
    private int indeks;
    private String imie;
    private String nazwisko;
    private String ocena_1;
    private String ocena_2;

    public Student(int indeks, String imie, String nazwisko, String ocena_1, String ocena_2) {
        this.indeks = indeks;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.ocena_1 = ocena_1;
        this.ocena_2 = ocena_2;
    }

    public void setIndeks(int indeks) {
        this.indeks = indeks;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void setOcena_1(String ocena_1) {
        this.ocena_1 = ocena_1;
    }

    public void setOcena_2(String ocena_2) {
        this.ocena_2 = ocena_2;
    }

    public int getIndeks() {
        return indeks;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getOcena_1() {
        return ocena_1;
    }

    public String getOcena_2() {
        return ocena_2;
    }

    public StringProperty getIndeksProperty() {
        return new SimpleStringProperty(String.valueOf(indeks));
    }
    public StringProperty getImieProperty() {
        return new SimpleStringProperty(imie);
    }
    public StringProperty getNazwiskoProperty() { return new SimpleStringProperty(nazwisko); }
    public StringProperty getOcena1Property() { return new SimpleStringProperty(ocena_1); }
    public StringProperty getOcena2Property() { return new SimpleStringProperty(ocena_2); }
}
