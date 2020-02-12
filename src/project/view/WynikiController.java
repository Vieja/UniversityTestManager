package project.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import project.Main;
import project.classes.Podejscie;
import project.classes.Grupa;

public class WynikiController extends TabController{

    Main main;

    public TableView<Grupa> TableGrupa;
    public TableView<Podejscie> TableStudent;
    public ChoiceBox<String> dateChoiceBox;
    public TableColumn<Grupa, String> ColumnGrupaNazwa;
    public TableColumn<Podejscie, String> ColumnStudentIndeks;
    public TableColumn<Podejscie, String> ColumnStudentImie;
    public TableColumn<Podejscie, String> ColumnStudentNazwisko;
    public TableColumn<Podejscie, String> ColumnStudentOcena;
    public TextField iluStudentow;
    public TextField ocenaField;
    public TextField imieField1;
    public TextField nazwiskoField1;
    public TextField indeksField1;
    public ChoiceBox<String> zestawChoiceBox;
    public DatePicker datePicker;
    public ChoiceBox<String> terminChoiceBox;
    public SplitPane split1;
    public SplitPane split2;

    public Podejscie wybrany = null;
    public Grupa wybranaGrupa = null;

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
                divider2.setPosition(0.7);
            }
        });

        dateChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldvalue, Number newvalue) {
                String data;
                if (!newvalue.equals(-1)) {
                    data = (String) dateChoiceBox.getItems().get((Integer) newvalue);
                    TableGrupa.setItems(main.getObserListGrupyEgzaminu(data));
                    //iluStudentow.setText(main.iluStudentowWDacie(data));
                } else {
                    TableGrupa.setItems(null);
                    TableStudent.setItems(null);
                    //iluStudentow.setText(null);
                }
            }
        });

        TableGrupa.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    if(newValue != null) {
                        TableStudent.setItems(main.getObserListPodejsciaZGrupy(newValue));
                        wybranaGrupa = newValue;
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

        ColumnGrupaNazwa.setCellValueFactory(cellData -> cellData.getValue().getNazwaProperty());

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
            //main.zaktualizujOcene(dane[2] + "-" + dane[1] + "-" + dane[0] + " 00:00:00.0", wybranaGrupa);
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
        dateChoiceBox.setItems(main.selectEgzaminyDoChoiceBoxa());
        dateChoiceBox.getSelectionModel().selectFirst();
    }

    public void stworzEgzamin(MouseEvent mouseEvent) {
    }

    public void stworzGrupe(MouseEvent mouseEvent) {
    }
}
