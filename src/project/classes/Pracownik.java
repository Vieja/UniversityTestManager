package project.classes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Pracownik {
    private int id;
    private String etat;
    private String nazwisko;
    private int placa;

    public Pracownik(int id, String etat, String nazwisko, int placa) {
        this.id = id;
        this.etat = etat;
        this.nazwisko = nazwisko;
        this.placa = placa;
    }

    public int getId() {
        return id;
    }

    public String getEtat() {
        return etat;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public int getPlaca() {
        return placa;
    }

    public StringProperty getIDProperty() {
        return new SimpleStringProperty(String.valueOf(id));
    }
    public StringProperty getEtatProperty() {
        return new SimpleStringProperty(etat);
    }
    public StringProperty getNazwiskoProperty() { return new SimpleStringProperty(nazwisko); }
    public StringProperty getPlacaProperty() { return new SimpleStringProperty(String.valueOf(placa)); }
}
