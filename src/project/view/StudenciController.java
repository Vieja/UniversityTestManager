package project.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import project.Main;
import project.classes.Student;

import java.util.List;

public class StudenciController extends TabController {

    public TextField indeksField;
    public TextField imieField;
    public TextField nazwiskoField;
    public TextField ocena1Field;
    public TextField ocena2Field;
    public ChoiceBox<String> studentChoiceBox;
    public TextField searchBarStudent;
    private Main main;

    public SplitPane split1;
    public TableView<Student> TableStudent;
    public TableColumn<Student, String> ColumnStudentIndeks;
    public TableColumn<Student, String> ColumnStudentImie;
    public TableColumn<Student, String> ColumnStudentNazwisko;
    public TableColumn<Student, String> ColumnStudentOcena1;
    public TableColumn<Student, String> ColumnStudentOcena2;

    public Student wybrany = null;

    @FXML
    private void initialize() {
        SplitPane.Divider divider = split1.getDividers().get(0);
        divider.positionProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldvalue, Number newvalue )
            {
                divider.setPosition(0.6);
            }
        });

        TableStudent.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    if(newValue != null) {
                        showStudent(newValue);
                    }
                })
        );
        ColumnStudentIndeks.setCellValueFactory(cellData -> cellData.getValue().getIndeksProperty());
        ColumnStudentImie.setCellValueFactory(cellData -> cellData.getValue().getImieProperty());
        ColumnStudentNazwisko.setCellValueFactory(cellData -> cellData.getValue().getNazwiskoProperty());
        ColumnStudentOcena1.setCellValueFactory(cellData -> cellData.getValue().getOcena1Property());
        ColumnStudentOcena2.setCellValueFactory(cellData -> cellData.getValue().getOcena2Property());
    }

    private void showStudent(Student student) {
        indeksField.setText(String.valueOf(student.getIndeks()));
        imieField.setText(student.getImie());
        nazwiskoField.setText(student.getNazwisko());
        if (student.getOcena_1() == null)
            ocena1Field.setText("");
        else ocena1Field.setText(student.getOcena_1());
        if (student.getOcena_2() == null)
            ocena2Field.setText("");
        else ocena2Field.setText(student.getOcena_2());
        wybrany = student;
    }

    public void setApp(Main main){
        this.main = main;
        TableStudent.setItems(main.getObserListStudents());

        ObservableList<String> studentAttributes = FXCollections.observableArrayList();
        studentAttributes.addAll("Nazwisko", "Indeks", "Imię", "Ocena I", "Ocena II");
        studentChoiceBox.setItems(studentAttributes);

        studentChoiceBox.getSelectionModel().selectFirst();
    }

    public void dodajStud() {
    }

    public void edytujStud() {
        main.edytujStudentaWBazie(wybrany, indeksField.getText(),imieField.getText(), nazwiskoField.getText(), ocena1Field.getText(), ocena2Field.getText());
        TableStudent.refresh();
        TableStudent.getColumns().get(0).setVisible(false);
        TableStudent.getColumns().get(0).setVisible(true);
    }

    public void usunStud() {
        main.usunStudentaZBazy(wybrany);
    }

    public void searchStudent() {
        String attribute = studentChoiceBox.getValue();
        String searchText = searchBarStudent.getText();

        String attr = null;
        switch (attribute) {
            case "Indeks":
                attr = "INDEKS";
            break;

            case "Imię":
                attr = "IMIE";
                break;

            case "Nazwisko":
                attr = "NAZWISKO";
                break;

            case "Ocena I":
                attr = "OCENA_1";
                break;

            case "Ocena II":
                attr = "OCENA_2";
                break;
        }

        if(!searchText.isEmpty()) {
            List<Integer> results = main.sqlSelect("SELECT INDEKS FROM STUDENCI " +
                    "WHERE lower(" + attr + ") like lower('" + searchText + "%')");
            ObservableList<Student> toShow = FXCollections.observableArrayList();
            for(Student worker : main.getObserListStudents()) {
                for(Integer index : results) {
                    if(index == worker.getIndeks()) {
                        toShow.add(worker);
                    }
                }
            }
            TableStudent.getSelectionModel().clearSelection();
            TableStudent.setItems(toShow);
        }
    }

    public void reloadStudenci() {
        TableStudent.setItems(main.getObserListStudents());
    }
}
