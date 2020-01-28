package project.classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class Podejscie {
    private Student student;
    private int id_zes;
    private String ocena;

    public Podejscie(Student student, int id_zes, String ocena) {
        this.student = student;
        this.id_zes = id_zes;
        this.ocena = ocena;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getOcena() {
        return ocena;
    }

    public void setOcena(String ocena) {
        this.ocena = ocena;
    }

    public int getStudentIndeks() {
        return student.getIndeks();
    }

    public String getStudentImie() {
        return student.getImie();
    }

    public String getStudentNazwisko() {
        return student.getNazwisko();
    }

    public int getId_zes() {
        return id_zes;
    }

    public void setId_zes(int id_zes) {
        this.id_zes = id_zes;
    }

    public StringProperty getStudentImieProperty() {
        return new SimpleStringProperty(student.getImie());
    }

    public StringProperty getStudentNazwiskoProperty() {
        return new SimpleStringProperty(student.getNazwisko());
    }

    public StringProperty getStudentIndeksProperty() {
        return new SimpleStringProperty(String.valueOf(student.getIndeks()));
    }
    public StringProperty getOcenaProperty() { return new SimpleStringProperty(ocena); }

}
