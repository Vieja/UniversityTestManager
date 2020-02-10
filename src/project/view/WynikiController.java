package project.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import project.Main;
import project.classes.Podejscie;
import project.classes.Student;
import project.classes.Zestaw;

import java.util.ArrayList;
import java.util.List;

public class WynikiController extends TabController{


    Main main;

    public TableView<Zestaw> TableZestaw;
    public TableView<Podejscie> TableStudent;
    public ChoiceBox dateChoiceBox;
    public TableColumn<Zestaw, String> ColumnZestawNazwa;
    public TableColumn<Zestaw, String> ColumnZestawTermin;
    public TableColumn<Podejscie, String> ColumnStudentIndeks;
    public TableColumn<Podejscie, String> ColumnStudentImie;
    public TableColumn<Podejscie, String> ColumnStudentNazwisko;
    public TableColumn<Podejscie, String> ColumnStudentOcena;
    public TextField iluStudentow;
    public TextField ocenaField;
    public TextField imieField1;
    public TextField nazwiskoField1;
    public TextField indeksField1;
    public SplitPane split1;
    public SplitPane split2;

    public Podejscie wybrany = null;
    public Zestaw wybranyZestaw = null;

    @FXML
    private void initialize() {
        SplitPane.Divider divider = split1.getDividers().get(0);
        divider.positionProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldvalue, Number newvalue) {
                divider.setPosition(0.5);
            }
        });

        SplitPane.Divider divider2 = split2.getDividers().get(0);
        divider2.positionProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldvalue, Number newvalue) {
                divider2.setPosition(0.9385);
            }
        });

        dateChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldvalue, Number newvalue) {
                String data;
                if (!newvalue.equals(-1)) {
                    data = (String) dateChoiceBox.getItems().get((Integer) newvalue);
                    data = data.split(" ")[0];
                    TableZestaw.setItems(main.getObserListZestawyKtoreSaZDaty(data));
                    iluStudentow.setText(main.iluStudentowWDacie(data));
                } else {
                    TableZestaw.setItems(null);
                    TableStudent.setItems(null);
                    iluStudentow.setText(null);
                }
            }
        });

        TableZestaw.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    if(newValue != null) {
                        TableStudent.setItems(main.getObserListPodejsciaZZestawu(newValue));
                        wybranyZestaw = newValue;
                    }
                })
        );

        TableStudent.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    if(newValue != null) {
                        showStudent(newValue);
                    }
                })
        );

        ColumnZestawNazwa.setCellValueFactory(cellData -> cellData.getValue().getNazwaProperty());
        ColumnZestawTermin.setCellValueFactory(cellData -> cellData.getValue().getTerminProperty());

        ColumnStudentIndeks.setCellValueFactory(cellData -> cellData.getValue().getStudentIndeksProperty());
        ColumnStudentImie.setCellValueFactory(cellData -> cellData.getValue().getStudentImieProperty());
        ColumnStudentNazwisko.setCellValueFactory(cellData -> cellData.getValue().getStudentNazwiskoProperty());
        ColumnStudentOcena.setCellValueFactory(cellData -> cellData.getValue().getOcenaProperty());
    }

    private void showStudent(Podejscie pod) {
        indeksField1.setText(String.valueOf(pod.getStudentIndeks()));
        imieField1.setText(pod.getStudentImie());
        nazwiskoField1.setText(pod.getStudentNazwisko());
        if (pod.getOcena() == null) {
            ocenaField.setText("");
        } else {
            ocenaField.setText(pod.getOcena());
        }
        wybrany = pod;
    }

    public void zaktualizujOceny() {
        if (wybrany!=null) {
            String data = (String) dateChoiceBox.getValue();
            data = data.split(" ")[0];
            String[] dane = data.split("-");
            main.zaktualizujOcene(dane[2] + "-" + dane[1] + "-" + dane[0] + " 00:00:00.0", wybranyZestaw);
        }
    }

    public void zapiszOcene() {
        main.zmienOcenePodejscia(wybrany, ocenaField.getText());
        TableStudent.refresh();
        TableStudent.getColumns().get(0).setVisible(false);
        TableStudent.getColumns().get(0).setVisible(true);
    }

    public void usunZPodejscia() {
        main.usunPodejscieZBazy(wybrany);
    }

    @Override
    public void setApp(Main main) {
        this.main = main;
        dateChoiceBox.setItems(main.getObserListDatyEgz());
        dateChoiceBox.getSelectionModel().selectFirst();
    }
}
