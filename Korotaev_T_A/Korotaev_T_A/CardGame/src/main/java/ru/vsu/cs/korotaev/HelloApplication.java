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
        imageView.setImage(image);
        imageView2.setImage(image2);
        imageView3.setImage(image3);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch();
    }
}