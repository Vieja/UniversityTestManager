package project.view;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import project.Main;
import project.sql.SQLHandler;

public class SignINController {
    public Label error;
    Main main;
    public TextField loginField;
    public TextField passwordField;

    public void tryToSignIn() {
        String login = loginField.getText();
        String password = passwordField.getText();
        SQLHandler sql_handler = new SQLHandler();
        int success = sql_handler.connect(login,password);
        switch (success) {
            case 0:
                main.initRootLayout(sql_handler);
            break;
            case 1:
                error.setText("Błędny login lub hasło");
            break;
            case 2:
                error.setText("Błąd przy próbie połączenia");
            break;
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
