package project.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import project.Main;
import project.classes.Pracownik;

public class StudenciController extends TabController {

    private Main main;

    public SplitPane split1;
    @FXML
    public TableView<Pracownik> TableStudent;
    @FXML
    public TableColumn<Pracownik, String> ColumnStudentIndeks;
    @FXML
    public TableColumn<Pracownik, String> ColumnStudentImie;
    @FXML
    public TableColumn<Pracownik, String> ColumnStudentNazwisko;
    @FXML
    public TableColumn<Pracownik, String> ColumnStudentOcena1;
    @FXML
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

        ColumnStudentIndeks.setCellValueFactory(cellData -> cellData.getValue().getIDProperty());
        ColumnStudentImie.setCellValueFactory(cellData -> cellData.getValue().getEtatProperty());
        ColumnStudentNazwisko.setCellValueFactory(cellData -> cellData.getValue().getNazwiskoProperty());
        ColumnStudentOcena1.setCellValueFactory(cellData -> cellData.getValue().getPlacaProperty());

    }

    private void showStudent(Pracownik newValue) {
    }

    public void setApp(Main main){
        this.main = main;
        TableStudent.setItems(main.getWorkers());
    }
}
