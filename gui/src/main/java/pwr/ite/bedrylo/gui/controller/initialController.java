package pwr.ite.bedrylo.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class initialController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}