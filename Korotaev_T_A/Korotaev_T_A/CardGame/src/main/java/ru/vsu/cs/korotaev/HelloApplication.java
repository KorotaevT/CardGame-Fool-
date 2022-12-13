package ru.vsu.cs.korotaev;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 750);
        stage.setTitle("CardGame");
        ImageView imageView = (ImageView)fxmlLoader.getNamespace().get("back");
        Image image = new Image("file:Sprites/BackGround.jpg");
        ImageView imageView2 = (ImageView)fxmlLoader.getNamespace().get("logo");
        ImageView imageView3 = (ImageView)fxmlLoader.getNamespace().get("logo1");
        Image image2 = new Image("file:Sprites/Logo.png");
        Image image3 = new Image("file:Sprites/logo2.png");
        ImageView imageView4 = (ImageView)fxmlLoader.getNamespace().get("jackMenu");
        Image image4 = new Image("file:Sprites/cards_Hearts_Jack.png.png");
        ImageView imageView5 = (ImageView)fxmlLoader.getNamespace().get("jackMenu1");
        Image image5 = new Image("file:Sprites/cards_Clubs_Jack.png.png");
        ImageView imageView6 = (ImageView)fxmlLoader.getNamespace().get("kingMenu");
        Image image6 = new Image("file:Sprites/cards_Diamonds_King.png.png");
        ImageView imageView7 = (ImageView)fxmlLoader.getNamespace().get("kingMenu1");
        Image image7 = new Image("file:Sprites/cards_Spades_King.png.png");
        ImageView imageView8 = (ImageView)fxmlLoader.getNamespace().get("jokerMenu");
        Image image8 = new Image("file:Sprites/cards_Red_Joker.png");
        ImageView imageView9 = (ImageView)fxmlLoader.getNamespace().get("jokerMenu1");
        Image image9 = new Image("file:Sprites/cards_Black_Joker.png");
        ImageView imageView10 = (ImageView)fxmlLoader.getNamespace().get("pushkaLogo");
        Image image10 = new Image("file:Sprites/pushkaLogo.png");
        imageView.setImage(image);
        imageView2.setImage(image2);
        imageView3.setImage(image3);
        imageView4.setImage(image4);
        imageView5.setImage(image5);
        imageView6.setImage(image6);
        imageView7.setImage(image7);
        imageView8.setImage(image8);
        imageView9.setImage(image9);
        imageView10.setImage(image10);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch();
    }
}