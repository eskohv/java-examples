package org.example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PrimaryController {

    public TextField usnField;
    public Label initLabel;
    public PasswordField pswField;
    public Button cancelButton;
    public Button primaryButton;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
        try {
            DBController.read();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String psw = pswField.getText();
        System.out.println(DBController.hash(psw));
    }

    public void exitButton(ActionEvent actionEvent) {
    }

    // TODO: 22.9.2020 password validity checking - login method "SELECT * FROM account WHERE username, password LIKE usn, psw" if null return false 
    @FXML
    private void login() throws IOException {
        String username = usnField.getText();
        String password = pswField.getText();


        switchToSecondary();
    }

}
