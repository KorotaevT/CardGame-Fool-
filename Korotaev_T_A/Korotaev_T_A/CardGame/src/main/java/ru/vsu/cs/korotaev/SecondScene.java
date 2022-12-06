package ru.vsu.cs.korotaev;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.vsu.cs.korotaev.Enums.Phase;
import ru.vsu.cs.korotaev.LogicClasses.MainArea;
import ru.vsu.cs.korotaev.ObjectClasses.Card;
import ru.vsu.cs.korotaev.ObjectClasses.FirstPlayer;
import ru.vsu.cs.korotaev.ObjectClasses.SecondPlayer;

import java.io.IOException;
import java.util.*;

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
    ImageView gameCardSlotDefSecond_1;

    @FXML
    ImageView gameCardSlotDefSecond_2;

    @FXML
    ImageView gameCardSlotDefSecond_3;

    @FXML
    ImageView gameCardSlotDefSecond_4;

    @FXML
    ImageView gameCardSlotDefSecond_5;

    @FXML
    ImageView gameCardSlotDefSecond_6;

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
    Button endBut;

    private static int pageNum = 0;
    private static Card takenCard = new Card();
    private static boolean cardIsTaken = false;
    private static boolean[] isTaped = new boolean[6];
    private static boolean[] isDefFirstTaped = new boolean[6];
    private static boolean[] isDefSecondTaped = new boolean[6];
    private static boolean gameIsEnded = false;

    @FXML
    protected void nextButClick() throws Exception {
        if (!checkout()) {
            return;
        }
        if (MainArea.getPhase() == Phase.FirstTossing) {
            if (!checkToss()) {
                return;
            }
        }
        Phase thisPhase = MainArea.getPhase();
        List<Phase> l = new ArrayList<>(Arrays.asList(Phase.values()));
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i) == thisPhase) {
                if (i == l.size() - 1) {
                    thisPhase = l.get(0);
                    MainArea.setPhase(l.get(0));
                    break;
                } else {
                    thisPhase = l.get(i + 1);
                    MainArea.setPhase(l.get(i + 1));
                    break;
                }

            }
        }
        if (thisPhase == Phase.FirstAttack) {
            endGame();
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
            endGame();
        } else if (thisPhase == Phase.SecondDefence) {
            if(SecondPlayer.ifTransfer()){
                phaseBack.setFill(Color.RED);
                phaseText.setText("Перевод");
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(event -> {
                    endGame();
                    ImageView[] gameCardSlots = {gameCardSlot_1, gameCardSlot_2, gameCardSlot_3, gameCardSlot_4, gameCardSlot_5, gameCardSlot_6};
                    ImageView[] gameCardSlotsDefFirst = {gameCardSlotDefFirst_1, gameCardSlotDefFirst_2, gameCardSlotDefFirst_3, gameCardSlotDefFirst_4, gameCardSlotDefFirst_5, gameCardSlotDefFirst_6};
                    for (ImageView cardSlot : gameCardSlots) {
                        cardSlot.setDisable(false);
                    }
                    for (ImageView cardSlot : gameCardSlotsDefFirst) {
                        cardSlot.setDisable(true);
                    }
                    try {
                        enemyStepVisual();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    updatePage(pageNum);
                    MainArea.setPhase(Phase.SecondAttack);
                    endGame();
                    try {
                        nextButClick();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                pause.play();
                return;
            }
            endGame();
            deVisBut();
            phaseBack.setFill(Color.RED);
            phaseText.setText("Защита");
            if (SecondPlayer.ifDefence(isDefSecondTaped)) {
                endGame();
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(event -> {
                    try {
                        enemyStepVisual();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                pause.play();

                PauseTransition pause2 = new PauseTransition(Duration.seconds(2));
                pause2.setOnFinished(event -> {
                    try {
                        nextButClick();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    visBut();
                });
                pause2.play();
                endGame();
            } else {
                endGame();
                Arrays.fill(MainArea.getGameFieldCardDefence(), null);
                Arrays.fill(MainArea.getGameFieldCardAttack(), null);
                MainArea.distribution(FirstPlayer.getFpd());
                MainArea.distribution(SecondPlayer.getSpd());
                FirstPlayer.getMass();
                SecondPlayer.getMass();
                MainArea.setPhase(Phase.SecondTossing);
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(event -> {
                    endClearGameField();
                    updatePage(pageNum);
                });
                pause.play();

                PauseTransition pause2 = new PauseTransition(Duration.seconds(2));
                pause2.setOnFinished(event -> {
                    try {
                        nextButClick();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    visBut();
                });
                pause2.play();
                endGame();
            }
            endGame();
        } else if (thisPhase == Phase.FirstTossing) {
            visBut();
            phaseBack.setFill(Color.LAWNGREEN);
            phaseText.setText("Добавить");
        } else if (thisPhase == Phase.SecondAttack) {
            endGame();
            deVisBut();
            phaseBack.setFill(Color.RED);
            phaseText.setText("Атака");
            SecondPlayer.ifAttack();
            endGame();
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> {
                try {
                    enemyStepVisual();
                    endGame();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            pause.play();

            PauseTransition pause2 = new PauseTransition(Duration.seconds(2));
            pause2.setOnFinished(event -> {
                try {
                    nextButClick();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                visBut();
            });
            pause2.play();
            updatePage(pageNum);
            endGame();
        } else if (thisPhase == Phase.FirstDefence) {
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
            endGame();
        } else if (thisPhase == Phase.SecondTossing) {
            phaseBack.setFill(Color.RED);
            phaseText.setText("Добавить");
            deVisBut();
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> {
                try {
                    if (!SecondPlayer.ifTossing(isTaped)) {
                        endClearGameField();
                        nextButClick();
                        Arrays.fill(MainArea.getGameFieldCardDefence(), null);
                        Arrays.fill(MainArea.getGameFieldCardAttack(), null);
                        MainArea.distribution(SecondPlayer.getSpd());
                        MainArea.distribution(FirstPlayer.getFpd());
                        FirstPlayer.getMass();
                        SecondPlayer.getMass();
                        updatePage(pageNum);
                        endGame();
                    } else {
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
                        enemyStepVisual();

                        updatePage(pageNum);
                        endGame();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            pause.play();
        }
    }

    @FXML
    private void cardsTransfer() throws Exception {
        ImageView[] cardSlots = {cardSlot_1, cardSlot_2, cardSlot_3, cardSlot_4, cardSlot_5, cardSlot_6};
        ImageView[] gameCardSlots = {gameCardSlot_1, gameCardSlot_2, gameCardSlot_3, gameCardSlot_4, gameCardSlot_5, gameCardSlot_6};
        ImageView[] gameCardSlotsDefFirst = {gameCardSlotDefFirst_1, gameCardSlotDefFirst_2, gameCardSlotDefFirst_3, gameCardSlotDefFirst_4, gameCardSlotDefFirst_5, gameCardSlotDefFirst_6};
        for (ImageView cardSlot : gameCardSlots) {
            cardSlot.setDisable(false);
        }
        for (ImageView cardSlot : gameCardSlotsDefFirst) {
            cardSlot.setDisable(true);
        }
        endGame();
        boolean isDefPrev = false;
        int notNullAttack = 0;
        for (int i = 0; i < MainArea.getGameFieldCardDefence().length; i++) {
            if (MainArea.getGameFieldCardDefence()[i] != null) {
                isDefPrev = true;
            }
            if (MainArea.getGameFieldCardAttack()[i] != null) {
                notNullAttack++;
            }
        }
        int indexTakenCard = 0;
        for (int i = 0; i<FirstPlayer.getFpd().size(); i++){
            if(FirstPlayer.getFpd().get(i)==takenCard){
                indexTakenCard = i;
                break;
            }
        }
        if (MainArea.getPhase() == Phase.FirstDefence && !isDefPrev) {
            assert MainArea.getGameFieldCardAttack()[0] != null;
            if (MainArea.getGameFieldCardAttack()[0].getRank() == takenCard.getRank()) {
                if (notNullAttack + 1 <= SecondPlayer.getSpd().size()) {
                    if (notNullAttack <= 5) {
                        for (int i = 0; i < MainArea.getGameFieldCardAttack().length; i++) {
                            if (MainArea.getGameFieldCardAttack()[i] == null) {
                                MainArea.getGameFieldCardAttack()[i] = takenCard;
                                enemyStepVisual();
                                takenCard = new Card();
                                cardIsTaken = false;
                                for(ImageView cardSlot: cardSlots){
                                    if(Integer.parseInt(cardSlot.getId().split("_")[1])==(indexTakenCard-pageNum*6)+1){
                                        cardSlot.setOpacity(1);
                                    }
                                }
                                FirstPlayer.getFpd().remove(indexTakenCard);
                                updatePage(pageNum);
                                MainArea.setPhase(Phase.FirstAttack);
                                endGame();
                                nextButClick();
                                break;
                            }
                        }
                    }
                }
            }
        }
    }


    @FXML
    private void pickUpCards() throws Exception {

        if(MainArea.getPhase()==Phase.FirstDefence){
        for(int i = 0; i<6; i++) {
            if (MainArea.getGameFieldCardDefence()[i] != null) {
                FirstPlayer.getFpd().add(MainArea.getGameFieldCardDefence()[i]);
            }
            if (MainArea.getGameFieldCardAttack()[i] != null) {
                FirstPlayer.getFpd().add(MainArea.getGameFieldCardAttack()[i]);
            }
        }
            Arrays.fill(MainArea.getGameFieldCardDefence(), null);
            Arrays.fill(MainArea.getGameFieldCardAttack(), null);
            MainArea.distribution(SecondPlayer.getSpd());
            MainArea.distribution(FirstPlayer.getFpd());
            FirstPlayer.getMass();
            SecondPlayer.getMass();
            updatePage(pageNum);
            MainArea.setPhase(Phase.FirstTossing);
            nextButClick();
        }
    }

    @FXML
    private boolean checkToss() throws Exception {
        int counterAt = 0;
        int counterDef = 0;
        for(int i =0; i<6; i++){
            if(MainArea.getGameFieldCardDefence()[i]!=null){
                counterDef++;
            }
            if(MainArea.getGameFieldCardAttack()[i]!=null){
                counterAt++;
            }
        }
        if(counterDef==counterAt){
            endClearGameField();
            Arrays.fill(MainArea.getGameFieldCardDefence(), null);
            Arrays.fill(MainArea.getGameFieldCardAttack(), null);
            MainArea.distribution(FirstPlayer.getFpd());
            MainArea.distribution(SecondPlayer.getSpd());
            FirstPlayer.getMass();
            SecondPlayer.getMass();
            updatePage(pageNum);
            return true;
        }else {
            MainArea.setPhase(Phase.FirstAttack);
            return true;
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
        if(MainArea.getPhase()==Phase.FirstDefence) {
            return num1 == num2;
        }else if(MainArea.getPhase()==Phase.FirstAttack){
                return num1>0;
        }
        return true;
    }

    @FXML
    private void endGame(){
        if(MainArea.getCardNum()==0){
            trumpCard.setImage(null);
            if(FirstPlayer.getFpd().size()==0){
                deVisBut();
                phaseBack.setFill(Color.LAWNGREEN);
                phaseText.setText("Победа");
                gameIsEnded = true;
                endBut.setVisible(true);
                endBut.setDisable(false);
                MainArea.setIsStartGame(false);
                FirstPlayer.setFpd(new ArrayList<>());
                FirstPlayer.setMass(new ArrayList<>());
                SecondPlayer.setSpd(new ArrayList<>());
                SecondPlayer.setMass(new ArrayList<>());
                MainArea.setDeck(MainArea.createDeck());
                MainArea.setPhase(Phase.FirstAttack);
                MainArea.setCardNum(54);
                pageNum = 0;
                MainArea.setGameFieldCardAttack(new Card[6]);
                MainArea.setGameFieldCardDefence(new Card[6]);
                takenCard = new Card();
                cardIsTaken = false;
                isTaped = new boolean[6];
                isDefFirstTaped = new boolean[6];
                isDefSecondTaped = new boolean[6];
                return;
            }
            if(SecondPlayer.getSpd().size()==0){
                deVisBut();
                phaseBack.setFill(Color.RED);
                phaseText.setText("Поражение");
                gameIsEnded = true;
                endBut.setVisible(true);
                endBut.setDisable(false);
                MainArea.setIsStartGame(false);
                FirstPlayer.setFpd(new ArrayList<>());
                FirstPlayer.setMass(new ArrayList<>());
                SecondPlayer.setSpd(new ArrayList<>());
                SecondPlayer.setMass(new ArrayList<>());
                MainArea.setDeck(MainArea.createDeck());
                MainArea.setPhase(Phase.FirstAttack);
                MainArea.setCardNum(54);
                pageNum = 0;
                MainArea.setGameFieldCardAttack(new Card[6]);
                MainArea.setGameFieldCardDefence(new Card[6]);
                takenCard = new Card();
                cardIsTaken = false;
                isTaped = new boolean[6];
                isDefFirstTaped = new boolean[6];
                isDefSecondTaped = new boolean[6];
            }
        }
    }

    @FXML
    private void endGameBut(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        Scene scene = new Scene(parent, 1300, 750);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setTitle("CardGame");
        ImageView imageView = (ImageView) parent.getChildrenUnmodifiable().get(0);
        Image image = new Image("file:Sprites/BackGround.jpg");
        imageView.setImage(image);
        gameIsEnded = false;
        window.setScene(scene);
        window.show();
        window.setResizable(false);
        window.setResizable(false);

    }

    @FXML
    private void endClearGameField(){
        ImageView[] gameCardSlots = {gameCardSlot_1, gameCardSlot_2, gameCardSlot_3, gameCardSlot_4, gameCardSlot_5, gameCardSlot_6};
        ImageView[] gameCardSlotsDefFirst = {gameCardSlotDefFirst_1, gameCardSlotDefFirst_2, gameCardSlotDefFirst_3, gameCardSlotDefFirst_4, gameCardSlotDefFirst_5, gameCardSlotDefFirst_6};
        ImageView[] gameCardSlotsDefSecond = {gameCardSlotDefSecond_1, gameCardSlotDefSecond_2, gameCardSlotDefSecond_3, gameCardSlotDefSecond_4, gameCardSlotDefSecond_5, gameCardSlotDefSecond_6};
        Image thisImage = new Image("file:Sprites/tracing.png");
        for (ImageView el: gameCardSlots){
            String s = el.getId().split("_")[1];
            isTaped[Integer.parseInt(s) - 1] = false;
            el.setImage(thisImage);
        }
        for (ImageView el1: gameCardSlotsDefFirst){
            String s = el1.getId().split("_")[1];
            isDefFirstTaped[Integer.parseInt(s) - 1] = false;
            el1.setImage(null);
        }
        for (ImageView el2: gameCardSlotsDefSecond){
            String s = el2.getId().split("_")[1];
            isDefSecondTaped[Integer.parseInt(s) - 1] = false;
            el2.setImage(null);
        }
    }

    @FXML
    protected void giveCards() throws Exception {
        if(!MainArea.isIsStartGame()) {
            FirstPlayer.setFpd(MainArea.distribution(FirstPlayer.getFpd()));
            SecondPlayer.setSpd(MainArea.distribution(SecondPlayer.getSpd()));
            FirstPlayer.getMass();
            SecondPlayer.getMass();
            updatePage(pageNum);
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
            Random rand = new Random();
            int n = rand.nextInt(2)+1;
            if(n==1){
                MainArea.setPhase(Phase.SecondTossing);
                nextButClick();
            }else {
                MainArea.setPhase(Phase.FirstTossing);
                nextButClick();
            }
        }
    }



    @FXML
    protected void nextPageBut(){
        if(pageNum<8) {
            pageNum = pageNum + 1;
        }else{
            pageNum = 0;
        }
        updatePage(pageNum);
    }



    @FXML
    protected void prevPageBut(){
        if(pageNum>0) {
            pageNum = pageNum - 1;
        }else{
            pageNum = 8;
        }
        updatePage(pageNum);
    }

    @FXML
    protected void enemyStepVisual() throws InterruptedException {
        ImageView[] gameCardSlots = {gameCardSlot_1, gameCardSlot_2, gameCardSlot_3, gameCardSlot_4, gameCardSlot_5, gameCardSlot_6};
        ImageView[] gameCardSlotsDefFirst = {gameCardSlotDefFirst_1, gameCardSlotDefFirst_2, gameCardSlotDefFirst_3, gameCardSlotDefFirst_4, gameCardSlotDefFirst_5, gameCardSlotDefFirst_6};
        ImageView[] gameCardSlotsDefSecond = {gameCardSlotDefSecond_1, gameCardSlotDefSecond_2, gameCardSlotDefSecond_3, gameCardSlotDefSecond_4, gameCardSlotDefSecond_5, gameCardSlotDefSecond_6};
        Card[] attack = MainArea.getGameFieldCardAttack();
        Card[] deff = MainArea.getGameFieldCardDefence();
        ImageView[] curDef = new ImageView[6];
        boolean[] curTaped = new boolean[6];
        Phase a = MainArea.getPhase();
        if(MainArea.getPhase()==Phase.SecondAttack || MainArea.getPhase()==Phase.FirstDefence){
            curDef = gameCardSlotsDefFirst;
            curTaped = isDefFirstTaped;
        }else if(MainArea.getPhase()==Phase.SecondDefence || MainArea.getPhase()==Phase.SecondTossing){
            curDef = gameCardSlotsDefSecond;
            curTaped = isDefSecondTaped;
        }
        for(int i = 0; i<attack.length; i++){
            if(attack[i]!=null) {
                gameCardSlots[i].setImage(attack[i].getImage());
                isTaped[i] = true;
            }
        }
        for(int i = 0; i<deff.length; i++){
            if(deff[i]!=null) {
                curDef[i].setImage(deff[i].getImage());
                curTaped[i] = true;
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
                ImageView thisViewGameDefEnemy = (ImageView) test.getChildren().get(i+23);
                Image thisImage = new Image("file:Sprites/tracing.png");
                thisView.setImage(thisImage);
                if(!isTaped[i]) {
                    thisViewGame.setImage(thisImage);
                }
                if(!isDefFirstTaped[i]) {
                    thisViewGameDef.setImage(null);
                }
                if(!isDefSecondTaped[i]) {
                    thisViewGameDefEnemy.setImage(null);
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
            if(gameIsEnded){
                return;
            }
            ImageView[] cardSlots = {cardSlot_1, cardSlot_2, cardSlot_3, cardSlot_4, cardSlot_5, cardSlot_6};
            for (ImageView cardSlot : cardSlots) {
                cardSlot.setOpacity(1);
            }
            for (ImageView cardSlot : cardSlots) {
                if (cardSlot.isHover()) {
                    String s = cardSlot.getId().split("_")[1];
                    int page = pageNum;
                    Card takenCardC = FirstPlayer.getFpd().get(6 * page + (Integer.parseInt(s) - 1));
                    if(takenCardC==takenCard){
                        takenCard = null;
                        cardIsTaken = false;
                        break;
                    }
                    takenCard = takenCardC;
                    cardIsTaken = true;
                    cardSlot.setOpacity(0.5);
                    break;
                }
            }
        }catch (IndexOutOfBoundsException e){
            System.out.println("You clicked on an empty cell");
        }
    }

    private boolean checkEqualsAttack(){
        if(!cardIsTaken){
            return true;
        }
        boolean isNotEmpty = false;
            for (int i = 0; i<MainArea.getGameFieldCardAttack().length; i++) {
                if (MainArea.getGameFieldCardAttack()[i] != null) {
                    isNotEmpty = true;
                    break;
                }
            }
            if(!isNotEmpty){
                return true;
            }else {
                boolean check = true;
                for (int i = 0; i<MainArea.getGameFieldCardAttack().length; i++) {
                    if (MainArea.getGameFieldCardAttack()[i] != null){
                        if(!(takenCard.getRank() == MainArea.getGameFieldCardAttack()[i].getRank())){
                            check = false;
                            break;
                        }
                    }
                }
                return check;
            }
    }

    @FXML
    private boolean checkEqualsToss(){
        if(!cardIsTaken){
            return true;
        }
        boolean check = false;
        for (int i = 0; i<MainArea.getGameFieldCardAttack().length; i++) {
            if (MainArea.getGameFieldCardAttack()[i] != null){
                if(takenCard.getRank() == MainArea.getGameFieldCardAttack()[i].getRank()){
                    check = true;
                    break;
                }
            }
        }
        for (int i = 0; i<MainArea.getGameFieldCardDefence().length; i++) {
            if (MainArea.getGameFieldCardDefence()[i] != null){
                if(takenCard.getRank() == MainArea.getGameFieldCardDefence()[i].getRank()){
                    check = true;
                    break;
                }
            }
        }
        return check;
    }


    @FXML
    protected void putCard() {
        try {
            if(gameIsEnded){
                return;
            }
            ImageView[] gameCardSlots = {gameCardSlot_1, gameCardSlot_2, gameCardSlot_3, gameCardSlot_4, gameCardSlot_5, gameCardSlot_6};
            ImageView[] gameCardSlotsDefFirst = {gameCardSlotDefFirst_1, gameCardSlotDefFirst_2, gameCardSlotDefFirst_3, gameCardSlotDefFirst_4, gameCardSlotDefFirst_5, gameCardSlotDefFirst_6};
            ImageView[] cur = new ImageView[6];
            boolean[] curTaped = new boolean[6];
            Card[] curDoing = new Card[6];
            Card[] curDoingCompare = new Card[6];
            if (MainArea.getPhase() == Phase.FirstAttack || MainArea.getPhase() == Phase.FirstTossing) {
                if(!checkEqualsAttack() && MainArea.getPhase()==Phase.FirstAttack){
                    return;
                }
                if(!checkEqualsToss() && MainArea.getPhase()==Phase.FirstTossing){
                    return;
                }
                cur = gameCardSlots;
                curTaped = isTaped;
                curDoing = MainArea.getGameFieldCardAttack();
                curDoingCompare = MainArea.getGameFieldCardDefence();
            } else if (MainArea.getPhase() == Phase.FirstDefence) {
                cur = gameCardSlotsDefFirst;
                curTaped = isDefFirstTaped;
                curDoing = MainArea.getGameFieldCardDefence();
                curDoingCompare = MainArea.getGameFieldCardAttack();
            } else if (MainArea.getPhase() == Phase.SecondDefence) {
                return;
            } else if (MainArea.getPhase() == Phase.SecondAttack) {
                return;
            }
            for (ImageView cardSlot : cur) {
                String s = cardSlot.getId().split("_")[1];
                if (cardSlot.isHover() && cardIsTaken) {
                    if(MainArea.getPhase()==Phase.FirstDefence) {
                        if (!MainArea.cardComparator(takenCard, curDoingCompare[Integer.parseInt(s) - 1])) {
                            return;
                        }
                    }
                    if(MainArea.getPhase()==Phase.FirstTossing){
                        if(isDefSecondTaped[Integer.parseInt(s)-1]){
                            return;
                        }
                    }
                    if (curTaped[Integer.parseInt(s) - 1]) {
                        Card transpCard = curDoing[Integer.parseInt(s) - 1];
                        curDoing[Integer.parseInt(s) - 1] = takenCard;
                        cardSlot.setImage(takenCard.getImage());
                        for (int i = 0; i < FirstPlayer.getFpd().size(); i++) {
                            if (FirstPlayer.getFpd().get(i) == takenCard) {
                                FirstPlayer.getFpd().set(i, transpCard);
                                FirstPlayer.getMass();
                                endGame();
                                break;
                            }
                        }
                        ImageView[] cardSlots = {cardSlot_1, cardSlot_2, cardSlot_3, cardSlot_4, cardSlot_5, cardSlot_6};
                        for (ImageView cardSlotO : cardSlots) {
                            cardSlotO.setOpacity(1);
                        }
                        cardIsTaken = false;
                        takenCard = null;
                        updatePage(pageNum);
                        endGame();
                        break;
                    }else {
                        if(MainArea.getPhase()==Phase.FirstDefence) {
                            if (!MainArea.cardComparator(takenCard, curDoingCompare[Integer.parseInt(s) - 1])) {
                                return;
                            }
                        }
                            curDoing[Integer.parseInt(s) - 1] = takenCard;
                            cardSlot.setImage(takenCard.getImage());
                            curTaped[Integer.parseInt(s) - 1] = true;
                            for (int i = 0; i < FirstPlayer.getFpd().size(); i++) {
                                if (FirstPlayer.getFpd().get(i) == takenCard) {
                                    FirstPlayer.getFpd().remove(i);
                                    FirstPlayer.getMass();
                                    endGame();
                                    break;
                                }
                            }
                            ImageView[] cardSlots = {cardSlot_1, cardSlot_2, cardSlot_3, cardSlot_4, cardSlot_5, cardSlot_6};
                            for (ImageView cardSlotO : cardSlots) {
                                cardSlotO.setOpacity(1);
                            }
                            cardIsTaken = false;
                            takenCard = null;
                            updatePage(pageNum);
                            endGame();
                            break;
                }
                } else if (cardSlot.isHover() && curTaped[Integer.parseInt(s)-1]) {
                    if(MainArea.getPhase()==Phase.FirstTossing){
                        if(isDefSecondTaped[Integer.parseInt(s)-1]){
                            return;
                        }
                    }
                    FirstPlayer.getFpd().add(curDoing[Integer.parseInt(s) - 1]);
                    curDoing[Integer.parseInt(s) - 1] = null;
                    updatePage(pageNum);
                    if(MainArea.getPhase()==Phase.FirstDefence) {
                        cardSlot.setImage(null);
                        curTaped[Integer.parseInt(s) - 1]=false;
                    }else {
                        Image thisImage = new Image("file:Sprites/tracing.png");
                        cardSlot.setImage(thisImage);
                        curTaped[Integer.parseInt(s) - 1] = false;
                    }
                    FirstPlayer.getMass();
                    endGame();
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