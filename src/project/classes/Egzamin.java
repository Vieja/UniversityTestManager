package project.classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Egzamin {

    private String data;
    private String termin;

    public Egzamin(String data, String termin) {
        this.data = data;
        this.termin = termin;
    }

    public String getTermin() {
        return termin;
    }

    public void setNazwa(String termin) {
        this.termin = termin;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public StringProperty getTerminProperty() {
        return new SimpleStringProperty(termin);
    }
    public StringProperty getDataProperty() { return new SimpleStringProperty(String.valueOf(data)); }

}

