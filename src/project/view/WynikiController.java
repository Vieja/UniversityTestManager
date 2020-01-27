package project.view;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import project.Main;

public class WynikiController extends TabController{
    public TableView TableZestaw;
    public TableView TableStudent;
    public ChoiceBox dateChoiceBox;
    public TableColumn ColumnZestawNazwa;
    public TableColumn ColumnZestawLiczba;
    public TableColumn ColumnStudentIndeks;
    public TableColumn ColumnStudentImie;
    public TableColumn ColumnStudentNazwisko;
    public TableColumn ColumnStudentOcena;
    public TextField ocenaField;
    public TextField imieField1;
    public TextField nazwiskoField1;
    public TextField indeksField1;

    public void zaktualizujOceny(MouseEvent mouseEvent) {
    }

    public void zapiszOcene(MouseEvent mouseEvent) {
    }

    public void usunZPodejscia(MouseEvent mouseEvent) {
    }

    @Override
    public void setApp(Main main) {

    }
}
