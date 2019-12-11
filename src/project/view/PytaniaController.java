package project.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import project.Main;
import project.classes.Pytanie;
import project.classes.Student;

import java.util.List;

public class PytaniaController extends TabController {

    public TextArea trescField;
    public TextField punktyField;
    public TextArea trescField1;
    public TextField punktyField1;
    public ChoiceBox<String> pytanieChoiceBox;
    public TextField searchBarPytanie;
    public ChoiceBox dateChoiceBox;
    public ChoiceBox zestawChoiceBox;
    private Main main;

    public SplitPane split1;
    public SplitPane split2;
    public TableView<Pytanie> TablePytanie;
    public TableColumn<Pytanie, String> ColumnPytanieTresc;
    public TableColumn<Pytanie, String> ColumnPytaniePunkty;

    public Pytanie wybrany = null;

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

        TablePytanie.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    if(newValue != null) {
                        showPytanie(newValue);
                    }
                })
        );
        ColumnPytanieTresc.setCellValueFactory(cellData -> cellData.getValue().getTrescProperty());
        ColumnPytaniePunkty.setCellValueFactory(cellData -> cellData.getValue().getPunktyProperty());
    }

    private void showPytanie(Pytanie pytanie) {
        trescField.setText(pytanie.getTresc());
        punktyField.setText(String.valueOf(pytanie.getPunkty()));
        trescField1.setText(pytanie.getTresc());
        punktyField1.setText(String.valueOf(pytanie.getPunkty()));
        wybrany = pytanie;
    }

    public void setApp(Main main){
        this.main = main;
        TablePytanie.setItems(main.getObserListPytania());

        ObservableList<String> pytaniaAttributes = FXCollections.observableArrayList();
        pytaniaAttributes.addAll("Treść", "Punkty");
        pytanieChoiceBox.setItems(pytaniaAttributes);
        pytanieChoiceBox.getSelectionModel().selectFirst();
    }

    public void dodajPyt() {
    }

    public void edytujPyt() {
        main.edytujPytanieWBazie(wybrany, trescField.getText(), punktyField.getText());
        TablePytanie.refresh();
        TablePytanie.getColumns().get(0).setVisible(false);
        TablePytanie.getColumns().get(0).setVisible(true);
    }

    public void usunPyt(){
        main.usunPytanieZBazy(wybrany);
    }

    public void searchPytanie() {
        String attribute = pytanieChoiceBox.getValue();
        String searchText = searchBarPytanie.getText();

        String attr = null;
        switch (attribute) {
            case "Treść":
                attr = "TRESC";
                break;

            case "Punkty":
                attr = "PUNKTY";
                break;

            case "Id":
                attr = "ID_ZAD";
                break;
        }

        if(!searchText.isEmpty()) {
            List<Integer> results = main.sqlSelect("SELECT ID_ZAD FROM ZADANIA " +
                    "WHERE lower(" + attr + ") like lower('%" + searchText + "%')");
            ObservableList<Pytanie> toShow = FXCollections.observableArrayList();
            for(Pytanie pytanie : main.getObserListPytania()) {
                for(Integer index : results) {
                    if(index == pytanie.getId()) {
                        toShow.add(pytanie);
                    }
                }
            }
            TablePytanie.getSelectionModel().clearSelection();
            TablePytanie.setItems(toShow);
        }
    }

    public void reloadPytania() {
        TablePytanie.setItems(main.getObserListPytania());
    }
}

