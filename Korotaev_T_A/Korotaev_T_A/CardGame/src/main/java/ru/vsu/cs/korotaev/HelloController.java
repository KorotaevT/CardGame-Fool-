package ru.vsu.cs.korotaev;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ru.vsu.cs.korotaev.LogicClasses.MainArea;

import java.io.IOException;
import java.util.Objects;

public class HelloController {

    @FXML
    protected void startGame(ActionEvent event) throws Exception {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("secondScene.fxml")));
        Scene scene = new Scene(parent, 1300, 750);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setTitle("CardGame");

        ImageView imageView = (ImageView) parent.getChildrenUnmodifiable().get(0);
        Image image = new Image("file:Sprites/BackGround.jpg");
        imageView.setImage(image);

        ImageView imageView2 = (ImageView) parent.getChildrenUnmodifiable().get(2);
        Image image2 = new Image("file:Sprites/cardsback.png");
        imageView2.setImage(image2);

        ImageView imageView3 = (ImageView) parent.getChildrenUnmodifiable().get(6).lookup("#friendCarpet");
        Image image3 = new Image("file:Sprites/blue.png");
        imageView3.setImage(image3);

        ImageView imageView4 = (ImageView) parent.getChildrenUnmodifiable().get(10).lookup("#enemyCarpet");
        Image image4 = new Image("file:Sprites/red.png");
        imageView4.setImage(image4);

        window.setScene(scene);
        window.show();
        window.setResizable(false);
    }

    @FXML
    protected void endGame(ActionEvent event) throws IOException{
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }
}