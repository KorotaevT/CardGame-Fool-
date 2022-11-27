package ru.vsu.cs.korotaev;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class SecondScene {
    @FXML
    Button nextBut;

    FirstPlayer firstPlayer = new FirstPlayer();
    SecondPlayer secondPlayer = new SecondPlayer();

    @FXML
    protected void nextButClick() {

        firstPlayer.setFpd(MainArea.distribution(firstPlayer.getFpd()));
        secondPlayer.setSpd(MainArea.distribution(secondPlayer.getSpd()));
    }
}