package ru.vsu.cs.korotaev;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SecondScene {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}