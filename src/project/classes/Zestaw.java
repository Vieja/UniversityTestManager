package project.classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;

public class Zestaw {
    private int id;
    private String nazwa;
    private String data;
    private String termin;

    private float liczbaPunktow = 0;

    public Zestaw(int id, String nazwa, String data, String termin, float liczbaPunktow) {
        this.id = id;
        this.nazwa = nazwa;
        this.data = data;
        this.termin = termin;
        this.liczbaPunktow = liczbaPunktow;
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

    public String getTermin() {return termin;}

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setLiczbaPunktow(float liczbaPunktow) {
        this.liczbaPunktow = liczbaPunktow;
    }

    public StringProperty getIdProperty() { return new SimpleStringProperty(String.valueOf(id)); }
    public StringProperty getNazwaProperty() {
        return new SimpleStringProperty(nazwa);
    }
    public StringProperty getDataProperty() { return new SimpleStringProperty(String.valueOf(data)); }
    public StringProperty getTerminProperty() {
        return new SimpleStringProperty(termin);
    }
    public StringProperty getPunktyProperty() {
        return new SimpleStringProperty(String.valueOf(liczbaPunktow));
    }

}