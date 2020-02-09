package project.view;

import javafx.application.Platform;
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
    public TextField indeksField1;
    public TextField imieField1;
    public TextField nazwiskoField1;
    public TextField ocena1Field1;
    public TextField ocena2Field1;
    public ChoiceBox<String> studentChoiceBox;
    public ChoiceBox<String> dateChoiceBox;
    public ChoiceBox<String> zestawChoiceBox;
    public TextField searchBarStudent;
    private Main main;

    public SplitPane split1;
    public SplitPane split2;
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

        SplitPane.Divider divider2 = split2.getDividers().get(0);
        divider2.positionProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldvalue, Number newvalue )
            {
                divider2.setPosition(0.9385);
            }
        });

        dateChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldvalue, Number newvalue) {
                String data = (String) dateChoiceBox.getItems().get((Integer) newvalue);
                ObservableList<String> zestawy = FXCollections.observableArrayList();
                zestawy.addAll(main.selectZestawyZDaty(data));
                zestawChoiceBox.setItems(zestawy);
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
        indeksField1.setText(String.valueOf(student.getIndeks()));
        imieField1.setText(student.getImie());
        nazwiskoField1.setText(student.getNazwisko());
        if (student.getOcena_1() == null) {
            ocena1Field.setText("");
            ocena1Field1.setText("");
        } else {
            ocena1Field.setText(student.getOcena_1());
            ocena1Field1.setText(student.getOcena_1());
        }
        if (student.getOcena_2() == null) {
            ocena2Field.setText("");
            ocena2Field1.setText("");
        } else {
            ocena2Field.setText(student.getOcena_2());
            ocena2Field1.setText(student.getOcena_2());
        }
        wybrany = student;
    }

    public void setApp(Main main){
        this.main = main;
        TableStudent.setItems(main.getObserListStudents());

        ObservableList<String> studentAttributes = FXCollections.observableArrayList();
        studentAttributes.addAll("Nazwisko", "Indeks", "Imię", "Ocena I", "Ocena II");
        studentChoiceBox.setItems(studentAttributes);
        studentChoiceBox.getSelectionModel().selectFirst();

        dateChoiceBox.setItems(main.getObserListDatyEgz());
        dateChoiceBox.getSelectionModel().selectFirst();
        zestawChoiceBox.getSelectionModel().selectFirst();
    }

    public void dodajStud() {
        Student student = new Student(Integer.parseInt(indeksField.getText()), imieField.getText(), nazwiskoField.getText(), ocena1Field.getText(), ocena2Field.getText());
        main.dodajStudentaDoBazy(student);
    }


    public void edytujStud() {
        main.edytujStudentaWBazie(wybrany, imieField.getText(), nazwiskoField.getText(), ocena1Field.getText(), ocena2Field.getText());
        TableStudent.refresh();
        TableStudent.getColumns().get(0).setVisible(false);
        TableStudent.getColumns().get(0).setVisible(true);
    }

    public void usunStud() {
        main.usunStudentaZBazy(wybrany);
        reloadStudenci();
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
            List<Integer> results = main.selectIntegers("SELECT INDEKS FROM STUDENCI " +
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
        TableStudent.setVisible(false);
        TableStudent.setItems(main.getObserListStudents());
        TableStudent.setVisible(true);
    }

    public void dodajStudDoPod() {
        if (zestawChoiceBox.getSelectionModel().isEmpty() || dateChoiceBox.getSelectionModel().isEmpty()) {
            main.showError("Błąd dodawania studenta do podejścia", "Upewnij się, że wybrałeś datę oraz nazwę odpowiedniego podejścia.");
        } else if (wybrany==null) {
            main.showError("Błąd dodawania studenta do podejścia", "Nie wybrano studenta");
        } else {
            String data = dateChoiceBox.getValue();
            String zestaw = zestawChoiceBox.getValue();
            main.dodajPodejscieDoBazy(wybrany, data, zestaw);
        }
    }

    public void usunStudentowCoZdali() {
        main.usunZaliczonychZBazy();
    }

    public void resetSpadochroniarzy() {
        main.czyscOcenySpadochroniarzom();
    }
}
