package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import project.sql.SQLHandler;
import project.view.RootController;
import project.view.SignINController;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private SQLHandler sqlHandler;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("University Tests Manager");
        this.primaryStage = primaryStage;

        signToDatabase();
        //initRootLayout();
    }

    public void initRootLayout(SQLHandler sql) {
        try {
            sqlHandler = sql;
            primaryStage.close();
            primaryStage = new Stage();
            primaryStage.setTitle("University Tests Manager");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            RootController controller = loader.getController();
            controller.setMain(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void signToDatabase() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/SignIN.fxml"));
            BorderPane sign = loader.load();
            SignINController controller = loader.getController();
            controller.setMain(this);
            Scene scene = new Scene(sign);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
