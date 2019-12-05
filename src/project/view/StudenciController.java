package project.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import project.Main;
import project.classes.Pracownik;

public class StudenciController extends TabController {

    public TextField indeksField;
    public TextField imieField;
    public TextField nazwiskoField;
    public TextField ocena1Field;
    public TextField ocena2Field;
    private Main main;

    public SplitPane split1;
    public TableView<Pracownik> TableStudent;
    public TableColumn<Pracownik, String> ColumnStudentIndeks;
    public TableColumn<Pracownik, String> ColumnStudentImie;
    public TableColumn<Pracownik, String> ColumnStudentNazwisko;
    public TableColumn<Pracownik, String> ColumnStudentOcena1;
    public TableColumn<Pracownik, String> ColumnStudentOcena2;

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

        ColumnStudentIndeks.setCellValueFactory(cellData -> cellData.getValue().getIDProperty());
        ColumnStudentImie.setCellValueFactory(cellData -> cellData.getValue().getEtatProperty());
        ColumnStudentNazwisko.setCellValueFactory(cellData -> cellData.getValue().getNazwiskoProperty());
        ColumnStudentOcena1.setCellValueFactory(cellData -> cellData.getValue().getPlacaProperty());

    }

    private void showStudent(Pracownik pracownik) {
        indeksField.setText(String.valueOf(pracownik.getId()));
    }

    public void setApp(Main main){
        this.main = main;
        TableStudent.setItems(main.getWorkers());
    }
}
