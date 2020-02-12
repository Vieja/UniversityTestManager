package project.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import project.Main;

import java.io.IOException;

public class RootController {
    @FXML
    private AnchorPane id_tab1;
    @FXML
    private AnchorPane id_tab2;
    @FXML
    private AnchorPane id_tab3;
    @FXML
    private AnchorPane id_tab4;

    private Main main;

    private void initTab(AnchorPane pane, String loc){

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RootController.class.getResource(loc));
        try {
            pane.getChildren().setAll((Node) loader.load());
        }
        catch(IOException e){
            System.out.println("Nie udało się załadować zawartości zakładek");
        }
        TabController cont=loader.getController();
        cont.setApp(main);
    }

    public void setMain(Main main) {
        this.main=main;
        initTab(id_tab1, "Studenci.fxml");
        initTab(id_tab4, "Pytania.fxml");
        initTab(id_tab2, "Wyniki.fxml");
        //initTab(id_tab3, "Zestawy.fxml");

    }
}
