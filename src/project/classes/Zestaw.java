package project.classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

public class Zestaw {
    private int id;
    private String nazwa;
    private String data;
    private String termin;
    ArrayList<Pytanie> listaPytan = new ArrayList<>();
    ArrayList<Podejscie> listaPodejsc = new ArrayList<>();

    public Zestaw(int id, String nazwa, String data, String termin) {
        this.id = id;
        this.nazwa = nazwa;
        this.data = data;
        this.termin = termin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ArrayList<Pytanie> getListaPytan() {
        return listaPytan;
    }

    public void setListaPytan(ArrayList<Pytanie> listaPytan) {
        this.listaPytan = listaPytan;
    }

    public StringProperty getIdProperty() { return new SimpleStringProperty(String.valueOf(id)); }
    public StringProperty getNazwaProperty() {
        return new SimpleStringProperty(nazwa);
    }
    public StringProperty getDataProperty() { return new SimpleStringProperty(String.valueOf(data)); }
}