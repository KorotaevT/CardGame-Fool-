package ru.vsu.cs.korotaev;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.w3c.dom.events.MouseEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    ImageView gameCardSlotDefFirst_1;

    @FXML
    ImageView gameCardSlotDefFirst_2;

    @FXML
    ImageView gameCardSlotDefFirst_3;

    @FXML
    ImageView gameCardSlotDefFirst_4;

    @FXML
    ImageView gameCardSlotDefFirst_5;

    @FXML
    ImageView gameCardSlotDefFirst_6;

    @FXML
    ImageView trumpCard;

    @FXML
    Text deckNum;

    @FXML
    Text enemyDeckNum;

    @FXML
    Circle enemyDeckCircle;

    @FXML
    Text deckNumText;

    @FXML
    Text phaseText;

    @FXML
    Rectangle phaseBack;

    @FXML
    Button prevBut1;

    @FXML
    Button nextBut1;

    @FXML
    Button reverseBut;

    @FXML
    Button takeCardBut;

    @FXML
    protected void nextButClick() throws InterruptedException {
        if(MainArea.getPhase()==Phase.FirstDefence){
            if(!checkout()){
                return;
            }
    }
        for(int i = 0; i<SecondPlayer.getSpd().size(); i++) {
            System.out.println(SecondPlayer.getSpd().get(i).getRank() + " " + SecondPlayer.getSpd().get(i).getColor());
        }
        Phase thisPhase = MainArea.getPhase();
        List<Phase> l = new ArrayList<>(Arrays.asList(Phase.values()));
        for(int i = 0; i<l.size(); i++) {
            if(l.get(i) == thisPhase){
                if(i == l.size()-1){
                    thisPhase = l.get(0);
                    MainArea.setPhase(l.get(0));
                    break;
                }else {
                    thisPhase = l.get(i+1);
                    MainArea.setPhase(l.get(i+1));
                    break;
                }

            }
        }
        if(thisPhase == Phase.FirstAttack){
            visBut();
            phaseBack.setFill(Color.LAWNGREEN);
            phaseText.setText("Атака");
            ImageView[] gameCardSlots = {gameCardSlot_1, gameCardSlot_2, gameCardSlot_3, gameCardSlot_4, gameCardSlot_5, gameCardSlot_6};
            ImageView[] gameCardSlotsDefFirst = {gameCardSlotDefFirst_1, gameCardSlotDefFirst_2, gameCardSlotDefFirst_3, gameCardSlotDefFirst_4, gameCardSlotDefFirst_5, gameCardSlotDefFirst_6};
            for (ImageView cardSlot : gameCardSlots) {
                cardSlot.setDisable(false);
            }
            for (ImageView cardSlot : gameCardSlotsDefFirst) {
                cardSlot.setDisable(true);
            }
        }else if(thisPhase == Phase.SecondDefence){
            phaseBack.setFill(Color.RED);
            phaseText.setText("Защита");
        }else if(thisPhase == Phase.FirstTossing){
            phaseBack.setFill(Color.LAWNGREEN);
            phaseText.setText("Добавить");
        }else if(thisPhase == Phase.SecondAttack){
            deVisBut();
            phaseBack.setFill(Color.RED);
            phaseText.setText("Атака");
            SecondPlayer.ifAttack();
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event ->{
                try {
                    enemyAttackVisual();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            pause.play();

            PauseTransition pause2 = new PauseTransition(Duration.seconds(2));
            pause2.setOnFinished(event ->{
                try {
                    nextButClick();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                visBut();
            });
            pause2.play();
            enemyDeckNum.setText(String.valueOf(SecondPlayer.getSpd().size()));
        }else if(thisPhase == Phase.FirstDefence){
            phaseBack.setFill(Color.LAWNGREEN);
            phaseText.setText("Защита");
            ImageView[] gameCardSlots = {gameCardSlot_1, gameCardSlot_2, gameCardSlot_3, gameCardSlot_4, gameCardSlot_5, gameCardSlot_6};
            ImageView[] gameCardSlotsDefFirst = {gameCardSlotDefFirst_1, gameCardSlotDefFirst_2, gameCardSlotDefFirst_3, gameCardSlotDefFirst_4, gameCardSlotDefFirst_5, gameCardSlotDefFirst_6};
            for (ImageView cardSlot : gameCardSlots) {
                cardSlot.setDisable(true);
            }
            for (ImageView cardSlot : gameCardSlotsDefFirst) {
                cardSlot.setDisable(false);
            }
        }else if(thisPhase == Phase.SecondTossing) {
            endClearGameField();
            phaseBack.setFill(Color.RED);
            phaseText.setText("Добавить");
            deVisBut();
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event ->{
                try {
                    if(!SecondPlayer.ifTossing()){
                        nextButClick();
                        Arrays.fill(MainArea.getGameFieldCardDefence(), null);
                        Arrays.fill(MainArea.getGameFieldCardAttack(), null);
                        MainArea.distribution(FirstPlayer.getFpd());
                        MainArea.distribution(SecondPlayer.getSpd());
                        FirstPlayer.getMass();
                        SecondPlayer.getMass();
                        updatePage(MainArea.getPageNum());
                    }else {
                        MainArea.setPhase(Phase.FirstDefence);
                        phaseBack.setFill(Color.LAWNGREEN);
                        phaseText.setText("Защита");
                        visBut();
                        ImageView[] gameCardSlots = {gameCardSlot_1, gameCardSlot_2, gameCardSlot_3, gameCardSlot_4, gameCardSlot_5, gameCardSlot_6};
                        ImageView[] gameCardSlotsDefFirst = {gameCardSlotDefFirst_1, gameCardSlotDefFirst_2, gameCardSlotDefFirst_3, gameCardSlotDefFirst_4, gameCardSlotDefFirst_5, gameCardSlotDefFirst_6};
                        for (ImageView cardSlot : gameCardSlots) {
                            cardSlot.setDisable(true);
                        }
                        for (ImageView cardSlot : gameCardSlotsDefFirst) {
                            cardSlot.setDisable(false);
                        }
                        enemyAttackVisual();

                        updatePage(MainArea.getPageNum());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            pause.play();
        }
    }

    @FXML
    private boolean checkout(){
        Card[] a = MainArea.getGameFieldCardAttack();
        Card[] b = MainArea.getGameFieldCardDefence();
        int num1 = 0;
        int num2 = 0;
        for(Card el: a){
            if(el!=null){
                num1++;
            }
        }
        for(Card el1: b){
            if(el1!=null){
                num2++;
            }
        }
        return num1 == num2;
    }

    @FXML
    private void endClearGameField(){
        ImageView[] gameCardSlots = {gameCardSlot_1, gameCardSlot_2, gameCardSlot_3, gameCardSlot_4, gameCardSlot_5, gameCardSlot_6};
        ImageView[] gameCardSlotsDefFirst = {gameCardSlotDefFirst_1, gameCardSlotDefFirst_2, gameCardSlotDefFirst_3, gameCardSlotDefFirst_4, gameCardSlotDefFirst_5, gameCardSlotDefFirst_6};
        for (ImageView el: gameCardSlots){
            String s = el.getId().split("_")[1];
            MainArea.getIsTaped()[Integer.parseInt(s) - 1] = false;
        }
        for (ImageView el1: gameCardSlotsDefFirst){
            String s = el1.getId().split("_")[1];
            MainArea.getIsDefFirstTaped()[Integer.parseInt(s) - 1] = false;
        }
    }

    @FXML
    protected void giveCards() throws Exception {
        if(!MainArea.isIsStartGame()) {
            FirstPlayer.setFpd(MainArea.distribution(FirstPlayer.getFpd()));
            SecondPlayer.setSpd(MainArea.distribution(SecondPlayer.getSpd()));
            FirstPlayer.getMass();
            SecondPlayer.getMass();
            updatePage(MainArea.getPageNum());
            SecondPlayer.getMass();
            FirstPlayer.getMass();
            MainArea.setIsStartGame(true);
            deckNum.setText(String.valueOf(MainArea.getCardNum()));
            deckNum.setVisible(true);
            deckNumText.setVisible(false);
            enemyDeckNum.setText(String.valueOf(SecondPlayer.getSpd().size()));
            enemyDeckNum.setVisible(true);
            enemyDeckCircle.setVisible(true);
            prevBut1.setVisible(true);
            nextBut1.setVisible(true);
            Image trumpIm = MainArea.getDeck().get(MainArea.getDeck().size()-1).getImage();
            trumpCard.setImage(trumpIm);
            visBut();
            phaseText.setVisible(true);
            phaseBack.setVisible(true);
            ImageView[] gameCardSlots = {gameCardSlot_1, gameCardSlot_2, gameCardSlot_3, gameCardSlot_4, gameCardSlot_5, gameCardSlot_6};
            ImageView[] gameCardSlotsDefFirst = {gameCardSlotDefFirst_1, gameCardSlotDefFirst_2, gameCardSlotDefFirst_3, gameCardSlotDefFirst_4, gameCardSlotDefFirst_5, gameCardSlotDefFirst_6};
            for (ImageView cardSlot : gameCardSlots) {
                cardSlot.setDisable(false);
            }
            for (ImageView cardSlot : gameCardSlotsDefFirst) {
                cardSlot.setDisable(true);
            }
        }
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
    protected void enemyAttackVisual() throws InterruptedException {
        ImageView[] gameCardSlots = {gameCardSlot_1, gameCardSlot_2, gameCardSlot_3, gameCardSlot_4, gameCardSlot_5, gameCardSlot_6};
        ImageView[] gameCardSlotsDefFirst = {gameCardSlotDefFirst_1, gameCardSlotDefFirst_2, gameCardSlotDefFirst_3, gameCardSlotDefFirst_4, gameCardSlotDefFirst_5, gameCardSlotDefFirst_6};
        Card[] attack = MainArea.getGameFieldCardAttack();
        Card[] deff = MainArea.getGameFieldCardDefence();
        for(int i = 0; i<attack.length; i++){
            if(attack[i]!=null) {
                gameCardSlots[i].setImage(attack[i].getImage());
                MainArea.getIsTaped()[i] = true;
            }
        }
        for(int i = 0; i<deff.length; i++){
            if(deff[i]!=null) {
                gameCardSlotsDefFirst[i].setImage(deff[i].getImage());
                MainArea.getIsDefFirstTaped()[i] = true;
            }
        }
    }

    @FXML
    private void visBut(){
        nextBut.setVisible(true);
        reverseBut.setVisible(true);
        takeCardBut.setVisible(true);
    }

    @FXML
    private void deVisBut(){
        nextBut.setVisible(false);
        reverseBut.setVisible(false);
        takeCardBut.setVisible(false);
    }

    @FXML
    protected void updatePage(int pageNum){
        try {
            MainArea.setCurPage(new ArrayList<>());
            List<Card> list = FirstPlayer.getFpd();
            List<Card> curList = new ArrayList<>();
            for (int e = pageNum * 6; e < pageNum * 6 + 6; e++) {
                if (e < list.size()) {
                    curList.add(list.get(e));
                }
            }
            AnchorPane thisAnchor = (AnchorPane) test.getChildren().get(6);
            for (int i = 0; i < 6; i++) {
                ImageView thisView = (ImageView) thisAnchor.getChildren().get(i);
                ImageView thisViewGame = (ImageView) test.getChildren().get(i+11);
                ImageView thisViewGameDef = (ImageView) test.getChildren().get(i+17);
                Image thisImage = new Image("file:Sprites/tracing.png");
                thisView.setImage(thisImage);
                if(!MainArea.getIsTaped()[i]) {
                    thisViewGame.setImage(thisImage);
                }
                if(!MainArea.getIsDefFirstTaped()[i]) {
                    thisViewGameDef.setImage(null);
                }
            }
            for (int i = 0; i < curList.size(); i++) {
                ImageView thisView = (ImageView) thisAnchor.getChildren().get(i);
                Image thisImage = curList.get(i).getImage();
                thisView.setImage(thisImage);
            }
            enemyDeckNum.setText(String.valueOf(SecondPlayer.getSpd().size()));
            deckNum.setText(String.valueOf(MainArea.getCardNum()));
        }catch (NullPointerException e){
            System.out.println("You clicked on an empty cell");
        }
    }

    @FXML
    protected void takeCard() {
        try {
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
        }catch (IndexOutOfBoundsException e){
            System.out.println("You clicked on an empty cell");
        }
    }



    @FXML
    protected void putCard() {
        try {

            ImageView[] gameCardSlots = {gameCardSlot_1, gameCardSlot_2, gameCardSlot_3, gameCardSlot_4, gameCardSlot_5, gameCardSlot_6};
            ImageView[] gameCardSlotsDefFirst = {gameCardSlotDefFirst_1, gameCardSlotDefFirst_2, gameCardSlotDefFirst_3, gameCardSlotDefFirst_4, gameCardSlotDefFirst_5, gameCardSlotDefFirst_6};
            ImageView[] cur = new ImageView[6];
            boolean[] curTaped = new boolean[6];
            Card[] curDoing = new Card[6];
            Card[] curDoingCompare = new Card[6];
            if (MainArea.getPhase() == Phase.FirstAttack) {
                cur = gameCardSlots;
                curTaped = MainArea.getIsTaped();
                curDoing = MainArea.getGameFieldCardAttack();
                curDoingCompare = MainArea.getGameFieldCardDefence();
            } else if (MainArea.getPhase() == Phase.FirstDefence) {
                cur = gameCardSlotsDefFirst;
                curTaped = MainArea.getIsDefFirstTaped();
                curDoing = MainArea.getGameFieldCardDefence();
                curDoingCompare = MainArea.getGameFieldCardAttack();
            } else if (MainArea.getPhase() == Phase.SecondDefence) {
                return;
            } else if (MainArea.getPhase() == Phase.SecondAttack) {
                return;
            }
            for (ImageView cardSlot : cur) {
                String s = cardSlot.getId().split("_")[1];
                if (cardSlot.isHover() && MainArea.isCardIsTaken()) {
                    if(MainArea.getPhase()==Phase.FirstDefence) {
                        if (!MainArea.cardComparator(MainArea.getTakenCard(), curDoingCompare[Integer.parseInt(s) - 1])) {
                            return;
                        }
                    }
                    if (curTaped[Integer.parseInt(s) - 1]) {
                        Card transpCard = curDoing[Integer.parseInt(s) - 1];
                        curDoing[Integer.parseInt(s) - 1] = MainArea.getTakenCard();
                        cardSlot.setImage(MainArea.getTakenCard().getImage());
                        MainArea.setCardIsTaken(false);
                        for (int i = 0; i < FirstPlayer.getFpd().size(); i++) {
                            if (FirstPlayer.getFpd().get(i) == MainArea.getTakenCard()) {
                                FirstPlayer.getFpd().set(i, transpCard);
                                ;
                                FirstPlayer.getMass();
                                break;
                            }
                        }
                        ImageView[] cardSlots = {cardSlot_1, cardSlot_2, cardSlot_3, cardSlot_4, cardSlot_5, cardSlot_6};
                        for (ImageView cardSlotO : cardSlots) {
                            cardSlotO.setOpacity(1);
                        }
                        updatePage(MainArea.getPageNum());
                        break;
                    }else {
                        if(MainArea.getPhase()==Phase.FirstDefence) {
                            if (!MainArea.cardComparator(MainArea.getTakenCard(), curDoingCompare[Integer.parseInt(s) - 1])) {
                                return;
                            }
                        }
                            curDoing[Integer.parseInt(s) - 1] = MainArea.getTakenCard();
                            cardSlot.setImage(MainArea.getTakenCard().getImage());
                            MainArea.setCardIsTaken(false);
                            curTaped[Integer.parseInt(s) - 1] = true;
                            for (int i = 0; i < FirstPlayer.getFpd().size(); i++) {
                                if (FirstPlayer.getFpd().get(i) == MainArea.getTakenCard()) {
                                    FirstPlayer.getFpd().remove(i);
                                    FirstPlayer.getMass();
                                    break;
                                }
                            }
                            ImageView[] cardSlots = {cardSlot_1, cardSlot_2, cardSlot_3, cardSlot_4, cardSlot_5, cardSlot_6};
                            for (ImageView cardSlotO : cardSlots) {
                                cardSlotO.setOpacity(1);
                            }
                            updatePage(MainArea.getPageNum());
                            break;
                }
                } else if (cardSlot.isHover() && curTaped[Integer.parseInt(s)-1]) {
                    FirstPlayer.getFpd().add(curDoing[Integer.parseInt(s) - 1]);
                    curDoing[Integer.parseInt(s) - 1] = null;
                    updatePage(MainArea.getPageNum());
                    if(MainArea.getPhase()==Phase.FirstDefence) {
                        cardSlot.setImage(null);
                        curTaped[Integer.parseInt(s) - 1]=false;
                    }else {
                        Image thisImage = new Image("file:Sprites/tracing.png");
                        cardSlot.setImage(thisImage);
                        curTaped[Integer.parseInt(s) - 1] = false;
                    }
                    FirstPlayer.getMass();
                    break;
                }
            }
        }catch (NullPointerException e){
            System.out.println("You need to select a new card");
        }catch (ArrayIndexOutOfBoundsException e1){
            System.out.println("There is no enemy card here");
        }
    }
}