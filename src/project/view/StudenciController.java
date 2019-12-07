package project.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import project.Main;
import project.classes.Student;

public class StudenciController extends TabController {

    public TextField indeksField;
    public TextField imieField;
    public TextField nazwiskoField;
    public TextField ocena1Field;
    public TextField ocena2Field;
    private Main main;

    public SplitPane split1;
    public TableView<Student> TableStudent;
    public TableColumn<Student, String> ColumnStudentIndeks;
    public TableColumn<Student, String> ColumnStudentImie;
    public TableColumn<Student, String> ColumnStudentNazwisko;
    public TableColumn<Student, String> ColumnStudentOcena1;
    public TableColumn<Student, String> ColumnStudentOcena2;

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

    private void showStudent(Student pracownik) {
        indeksField.setText(String.valueOf(pracownik.getIndeks()));
    }

    public void setApp(Main main){
        this.main = main;
        TableStudent.setItems(main.getObserListStudents());
    }
}
