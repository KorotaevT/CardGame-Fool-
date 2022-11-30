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
    ImageView cardSlot_1;

    @FXML
    ImageView cardSlot_2;

    @FXML
    ImageView cardSlot_3;

    @FXML
    ImageView cardSlot_4;

    @FXML
    ImageView cardSlot_5;

    @FXML
    ImageView cardSlot_6;

    @FXML
    ImageView gameCardSlot_1;

    @FXML
    ImageView gameCardSlot_2;

    @FXML
    ImageView gameCardSlot_3;

    @FXML
    ImageView gameCardSlot_4;

    @FXML
    ImageView gameCardSlot_5;

    @FXML
    ImageView gameCardSlot_6;

    @FXML
    protected void nextButClick() throws Exception {

        FirstPlayer.setFpd(MainArea.distribution(FirstPlayer.getFpd()));
        SecondPlayer.setSpd(MainArea.distribution(SecondPlayer.getSpd()));
        updatePage(MainArea.getPageNum());
        SecondPlayer.getMass();
        FirstPlayer.getMass();
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
        AnchorPane thisAnchor = (AnchorPane) test.getChildren().get(4);
        AnchorPane thisAnchorEnemy = (AnchorPane) test.getChildren().get(8);
        for (int i = 0; i<6; i++){
            ImageView thisView = (ImageView) thisAnchor.getChildren().get(i);
            ImageView thisViewEnemy = (ImageView) thisAnchorEnemy.getChildren().get(i);
            Image thisImage = new Image("file:Sprites/cardsback.png");
            thisView.setImage(thisImage);
            thisViewEnemy.setImage(thisImage);
        }
        for (int i = 0; i<curList.size(); i++){
            ImageView thisView = (ImageView) thisAnchor.getChildren().get(i);
            Image thisImage = curList.get(i).getImage();
            thisView.setImage(thisImage);
        }
    }

    @FXML
    protected void takeCard(){
        ImageView[] cardSlots = {cardSlot_1, cardSlot_2, cardSlot_3, cardSlot_4, cardSlot_5, cardSlot_6};
        for (ImageView cardSlot : cardSlots) {
            cardSlot.setOpacity(1);
        }
        for (ImageView cardSlot : cardSlots) {
            if (cardSlot.isHover()) {
                String s = cardSlot.getId().split("_")[1];
                int page = MainArea.getPageNum();
                Card takenCard = FirstPlayer.getFpd().get(6 * page + (Integer.parseInt(s) - 1));
                MainArea.setTakenCard(takenCard);
                MainArea.setCardIsTaken(true);
                cardSlot.setOpacity(0.5);
                break;
            }
        }
    }

    @FXML
    protected void putCard() throws Exception {
        ImageView[] gameCardSlots = {gameCardSlot_1, gameCardSlot_2, gameCardSlot_3, gameCardSlot_4, gameCardSlot_5, gameCardSlot_6};
        for (ImageView cardSlot : gameCardSlots) {
            if (cardSlot.isHover() && MainArea.isCardIsTaken()) {
                String s = cardSlot.getId().split("_")[1];
                MainArea.getGameFieldCardAttack()[Integer.parseInt(s) - 1] = MainArea.getTakenCard();
                cardSlot.setImage(MainArea.getTakenCard().getImage());
                MainArea.setCardIsTaken(false);
                for(int i = 0; i<FirstPlayer.getFpd().size(); i++){
                    if(FirstPlayer.getFpd().get(i)==MainArea.getTakenCard()){
                        FirstPlayer.getFpd().remove(i);
                        FirstPlayer.getMass();
                        break;
                    }
                }
                takeCard();
                updatePage(MainArea.getPageNum());
                break;
            }else if(cardSlot.isHover()){
                String s = cardSlot.getId().split("_")[1];
                FirstPlayer.getFpd().add(MainArea.getGameFieldCardAttack()[Integer.parseInt(s) - 1]);
                MainArea.getGameFieldCardAttack()[Integer.parseInt(s) - 1] = null;
                updatePage(MainArea.getPageNum());
                cardSlot.setImage(null);
                FirstPlayer.getMass();
                break;
            }
        }
    }
}