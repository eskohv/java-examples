package org.example;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
        try {
            DBController.read();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}