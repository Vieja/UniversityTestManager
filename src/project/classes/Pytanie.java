package project.classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Pytanie {
    private int id;
    private String tresc;
    private float punkty;

    public Pytanie(int id, String tresc, float punkty) {
        this.id = id;
        this.tresc = tresc;
        this.punkty = punkty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public float getPunkty() {
        return punkty;
    }

    public void setPunkty(float punkty) {
        this.punkty = punkty;
    }

    public StringProperty getIdProperty() { return new SimpleStringProperty(String.valueOf(id)); }
    public StringProperty getTrescProperty() {
        return new SimpleStringProperty(tresc);
    }
    public StringProperty getPunktyProperty() { return new SimpleStringProperty(String.valueOf(punkty)); }
}
