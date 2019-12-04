package project.view;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import project.Main;
import project.sql.SQLHandler;

public class SignINController {
    Main main;
    public TextField loginField;
    public TextField passwordField;

    public void tryToSignIn() {
        String login = loginField.getText();
        String password = passwordField.getText();
        if (login!=null & password!=null) {
            SQLHandler sql_handler = new SQLHandler();
            boolean success = sql_handler.connect(login,password);
            if (success) {
                System.out.println("Wooohooo");
                main.initRootLayout();
            }
        }
    }

    public void setMain(Main main) {
        this.main=main;
    }

    public void tryToSignInEnter(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER))
            tryToSignIn();
    }
}
