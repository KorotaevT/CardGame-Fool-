package ru.vsu.cs.korotaev;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.w3c.dom.events.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class SecondScene {
    @FXML
    Button nextBut;

    @FXML
    AnchorPane test;

    @FXML
    AnchorPane cardPane1;

    @FXML
    protected void nextButClick() throws Exception {

        FirstPlayer.setFpd(MainArea.distribution(FirstPlayer.getFpd()));
        SecondPlayer.setSpd(MainArea.distribution(SecondPlayer.getSpd()));
        updatePage(MainArea.getPageNum());
        SecondPlayer.getMass();
    }



    @FXML
    protected void nextPageBut(){
        if(MainArea.getPageNum()<8) {
            MainArea.setPageNum(MainArea.getPageNum() + 1);
        }else{
            MainArea.setPageNum(0);
        }
        updatePage(MainArea.getPageNum());
    }



    @FXML
    protected void prevPageBut(){
        if(MainArea.getPageNum()>0) {
            MainArea.setPageNum(MainArea.getPageNum() - 1);
        }else{
            MainArea.setPageNum(8);
        }
        updatePage(MainArea.getPageNum());
    }

    @FXML
    protected void updatePage(int pageNum){
        MainArea.setCurPage(new ArrayList<>());
        List<Card> list = FirstPlayer.getFpd();
        List<Card> curList = new ArrayList<>();
        for (int e = pageNum * 6; e< pageNum * 6 + 6; e++){
            if (e<list.size()) {
                curList.add(list.get(e));
            }
        }
        AnchorPane thisAnchor = (AnchorPane) test.getChildren().get(5);
        for (int i = 0; i<6; i++){
            ImageView thisView = (ImageView) thisAnchor.getChildren().get(i);
            Image thisImage = new Image("file:Sprites/cardsback.png");
            thisView.setImage(thisImage);
        }
        for (int i = 0; i<curList.size(); i++){
            ImageView thisView = (ImageView) thisAnchor.getChildren().get(i);
            Image thisImage = curList.get(i).getImage();
            thisView.setImage(thisImage);
            System.out.println(curList.get(i).getColor() + " " + curList.get(i).getRank());
        }
    }
}