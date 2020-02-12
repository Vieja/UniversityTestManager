package project.classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;

public class Grupa {
    private int id;
    private String nazwa;
    private String data;

    public Grupa(int id, String nazwa, String data) {
        this.id = id;
        this.nazwa = nazwa;
        this.data = data;
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

    public StringProperty getIdProperty() { return new SimpleStringProperty(String.valueOf(id)); }
    public StringProperty getNazwaProperty() {
        return new SimpleStringProperty(nazwa);
    }
    public StringProperty getDataProperty() { return new SimpleStringProperty(String.valueOf(data)); }

}