package ru.vsu.cs.korotaev;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Stack;

public class HelloController {

    @FXML
    protected void startGame(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("secondScene.fxml")));
        Scene scene = new Scene(parent, 1200, 700);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setTitle("CardGame");

        ImageView imageView = (ImageView) parent.getChildrenUnmodifiable().get(0);
        Image image = new Image("file:Sprites/BackGround.jpg");
        imageView.setImage(image);

        ImageView imageView2 = (ImageView) parent.getChildrenUnmodifiable().get(1);
        Image image2 = new Image("file:Sprites/cardsback.png");
        imageView2.setImage(image2);

        window.setScene(scene);
        window.show();
        window.setResizable(false);
        MainArea.randomDeck();
    }

    @FXML
    protected void endGame(ActionEvent event) throws IOException{
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }
}