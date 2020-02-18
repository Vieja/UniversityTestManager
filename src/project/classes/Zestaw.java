package project.classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Zestaw {
    private String nazwa;
    private String data;

    public Zestaw(String nazwa, String data) {
        this.nazwa = nazwa;
        this.data = data;
    }

    public String getNazwa() {
        return nazwa;
    }

    public StringProperty getNazwaProperty() {
        return new SimpleStringProperty(nazwa);
    }

    public String getData() {
        return data;
    }

    public StringProperty getDataProperty() {
        return new SimpleStringProperty(data);
    }
}
