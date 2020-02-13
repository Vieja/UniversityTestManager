package project.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import project.Main;
import project.classes.Pytanie;
import project.classes.Grupa;
import project.classes.Zestaw;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class ZestawyController extends TabController{

    Main main;

    public SplitPane split1;
    public TableView<Zestaw> TableZestaw;
    public TableColumn<Zestaw, String> ColumnZestawNazwa;
    public TableColumn<Zestaw, String> ColumnZestawLiczba;
    public TableView<Pytanie> TablePytanie;
    public TextField nazwaField;
    public TextField liczbaPunktow;
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

        TableZestaw.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    if(newValue != null) {
//                        main.updateObserbableListZestawLiczbaPunktow(newValue);
//                        TableZestaw.refresh();
//                        TableZestaw.getColumns().get(0).setVisible(false);
//                        TableZestaw.getColumns().get(0).setVisible(true);
                        TablePytanie.setItems(main.getObserListPytaniaZZestawu(newValue));
                        wybranyZestaw = newValue;
                        liczbaPunktow.setText(main.ilePunktowMaZestaw(wybranyZestaw.getNazwa()));
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
        ColumnZestawLiczba.setCellValueFactory(cellData -> cellData.getValue().getDataProperty());
        ColumnPytanieTresc.setCellValueFactory(cellData -> cellData.getValue().getTrescProperty());
        ColumnPytaniePunkty.setCellValueFactory(cellData -> cellData.getValue().getPunktyProperty());
    }

    @Override
    public void setApp(Main main) {
        this.main = main;
        TableZestaw.setItems(main.getObserListZestawy());
    }

    public void stworzZestaw() {
        main.dodajZestawDoBazy(nazwaField.getText());
    }

    public void usunZZestawu() {
        main.usunZawartosc(wybranePytanie, wybranyZestaw);
//        main.updateObserbableListZestawLiczbaPunktow(wybranyZestaw);
//        TableZestaw.refresh();
//        TableZestaw.getColumns().get(0).setVisible(false);
//        TableZestaw.getColumns().get(0).setVisible(true);
    }

    public void usunZestaw() {
        main.usunZestawZBazy(wybranyZestaw);
        //reloadZestawy();
    }

}
