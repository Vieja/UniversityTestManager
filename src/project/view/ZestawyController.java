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
import project.classes.Pytanie;
import project.classes.Zestaw;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZestawyController extends TabController{

    public ChoiceBox<String> choiceBoxTermin;
    Main main;

    public SplitPane split1;
    public TableView<Zestaw> TableZestaw;
    public TableColumn<Zestaw, String> ColumnZestawNazwa;
    public ChoiceBox<String> dateChoiceBox;
    public TableView<Pytanie> TablePytanie;
    public TextField nazwaField;
    public DatePicker datePicker;
    public TableColumn<Zestaw, String> ColumnZestawLiczba;
    public TableColumn<Pytanie, String> ColumnPytanieTresc;
    public TableColumn<Pytanie, String> ColumnPytaniePunkty;

    public Zestaw wybranyZestaw = null;
    public Pytanie wybranePytanie = null;

    @FXML
    private void initialize() {
        SplitPane.Divider divider = split1.getDividers().get(0);
        divider.positionProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldvalue, Number newvalue) {
                divider.setPosition(0.5);
            }
        });

        dateChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldvalue, Number newvalue) {
                String data = (String) dateChoiceBox.getItems().get((Integer) newvalue);
                data = data.split(" ")[0];
                TableZestaw.setItems(main.getObserListZestawyKtoreSaZDaty(data));
            }
        });

        TableZestaw.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    if(newValue != null) {
                        main.updateObserbableListZestawLiczbaPunktow(newValue);
                        TableZestaw.refresh();
                        TableZestaw.getColumns().get(0).setVisible(false);
                        TableZestaw.getColumns().get(0).setVisible(true);
                        TablePytanie.setItems(main.getObserListPytaniaZZestawu(newValue));
                        wybranyZestaw = newValue;
                    }
                })
        );

        TablePytanie.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    if(newValue != null) {
                        wybranePytanie = newValue;
                    }
                })
        );

        ColumnZestawNazwa.setCellValueFactory(cellData -> cellData.getValue().getNazwaProperty());
        ColumnZestawLiczba.setCellValueFactory(cellData -> cellData.getValue().getPunktyProperty());
        ColumnPytanieTresc.setCellValueFactory(cellData -> cellData.getValue().getTrescProperty());
        ColumnPytaniePunkty.setCellValueFactory(cellData -> cellData.getValue().getPunktyProperty());
    }

    @Override
    public void setApp(Main main) {
        this.main = main;
        ObservableList<String> datyEgz = FXCollections.observableArrayList();
        datyEgz.addAll(main.selectDatyEgzaminow());
        dateChoiceBox.setItems(datyEgz);


        List<String> list = Arrays.asList("pierwszy", "drugi", "trzeci");
        ObservableList<String> terminy = FXCollections.observableArrayList();
        terminy.addAll(list);
        choiceBoxTermin.setItems(terminy);
        dateChoiceBox.getSelectionModel().selectFirst();
        choiceBoxTermin.getSelectionModel().selectFirst();
    }

    public void stworzZestaw() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedString = datePicker.getValue().format(formatter);
        String data = dateChoiceBox.getValue();
        data = data.split(" ")[0];
        main.dodajZestawDoBazy(formattedString, nazwaField.getText(), (String) choiceBoxTermin.getValue(), data);
    }

    public void usunZZestawu() {
        main.usunZawartosc(wybranePytanie, wybranyZestaw);
        main.updateObserbableListZestawLiczbaPunktow(wybranyZestaw);
        TableZestaw.refresh();
        TableZestaw.getColumns().get(0).setVisible(false);
        TableZestaw.getColumns().get(0).setVisible(true);
    }
}