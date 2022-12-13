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
import ru.vsu.cs.korotaev.Enums.Rank;
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
    Button phaseBut;

    @FXML
    Text phaseText;

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

    @FXML
    ImageView enemyDeck;

    @FXML
    AnchorPane enemyPane;

    @FXML
    ImageView enemyCarpet;

    @FXML
    ImageView friendCarpet;

    @FXML
    Text cardsNumText;



    private int pageNum = 0;
    private Card takenCard = new Card();
    private boolean cardIsTaken = false;
    private boolean[] isTaped = new boolean[6];
    private boolean[] isDefFirstTaped = new boolean[6];
    private boolean[] isDefSecondTaped = new boolean[6];
    private boolean gameIsEnded = false;
    private ImageView[] gameCardSlots;
    private ImageView[] gameCardSlotsDefFirst;
    private ImageView[] gameCardSlotsDefSecond;
    private ImageView[] cardSlots;
    private MainArea mainArea;
    private FirstPlayer firstPlayer;
    private SecondPlayer secondPlayer;

    @FXML
    private void nextButClick() throws Exception {
        if (!checkEqNum()) {
            return;
        }
        if (mainArea.getPhase() == Phase.FirstTossing) {
            if (!checkToss()) {
                return;
            }
        }
        Phase thisPhase = mainArea.getPhase();
        thisPhase = phaseChanger(thisPhase);
        if (thisPhase == Phase.FirstAttack) {
            firstAttackNextButTapped();
        } else if (thisPhase == Phase.SecondDefence) {
            secondDefenceNextButTapped();
        } else if (thisPhase == Phase.FirstTossing) {
            firstTossingNextButTapped();
        } else if (thisPhase == Phase.SecondAttack) {
            secondAttackNextButTapped();
        } else if (thisPhase == Phase.FirstDefence) {
            firstDefenceNextButTapped();
        } else if (thisPhase == Phase.SecondTossing) {
            secondTossingNextButTapped();
        }
    }

    @FXML
    private void firstAttackNextButTapped() {
        visBut();
        phaseBut.setStyle("-fx-background-color:  #0e0e8f; -fx-effect:  dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 ); -fx-background-radius:  40em; -fx-border-radius: 40em");
        phaseText.setText("Атака");
        defSlotsDis();
        endGame();
    }

    @FXML
    private void firstDefenceNextButTapped() {
        phaseBut.setStyle("-fx-background-color:  #0e0e8f; -fx-effect:  dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 ); -fx-background-radius:  40em; -fx-border-radius: 40em");
        phaseText.setText("Защита");
        attSlotsDis();
        endGame();
    }

    @FXML
    private void firstTossingNextButTapped() {
        visBut();
        phaseBut.setStyle("-fx-background-color:  #0e0e8f; -fx-effect:  dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 ); -fx-background-radius:  40em; -fx-border-radius: 40em");
        phaseText.setText("Подкидывание");
        defSlotsDis();
        endGame();
    }

    @FXML
    private void secondAttackNextButTapped() throws Exception {
        deVisBut();
        phaseBut.setStyle("-fx-background-color:  #8f0e0e; -fx-effect:  dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 ); -fx-background-radius:  40em; -fx-border-radius: 40em");
        phaseText.setText("Атака");
        mainArea.setGameFieldCardAttack(secondPlayer.ifAttack(mainArea.getMassMap()));
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
    }

    @FXML
    private void secondDefenceNextButTapped() throws Exception {
        if (secondPlayer.ifTransfer(mainArea.getGameFieldCardAttack(), mainArea.getGameFieldCardDefence(), firstPlayer.getFpd())) {
            phaseBut.setStyle("-fx-background-color:  #8f0e0e; -fx-effect:  dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 ); -fx-background-radius:  40em; -fx-border-radius: 40em");
            phaseText.setText("Перевод");
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> {
                endGame();
                defSlotsDis();
                try {
                    enemyStepVisual();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                updatePage(pageNum);
                mainArea.setPhase(Phase.SecondAttack);
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
        phaseBut.setStyle("-fx-background-color:  #8f0e0e; -fx-effect:  dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 ); -fx-background-radius:  40em; -fx-border-radius: 40em");
        phaseText.setText("Защита");
        if (secondPlayer.ifDefence(isDefSecondTaped, mainArea)) {
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
            Arrays.fill(mainArea.getGameFieldCardDefence(), null);
            Arrays.fill(mainArea.getGameFieldCardAttack(), null);
            mainArea.distribution(firstPlayer.getFpd());
            mainArea.distribution(secondPlayer.getSpd());
            firstPlayer.getMass(mainArea.getMassMap());
            secondPlayer.getMass(mainArea.getMassMap());
            mainArea.setPhase(Phase.SecondTossing);
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
    }

    @FXML
    private void secondTossingNextButTapped() {
        phaseBut.setStyle("-fx-background-color:  #8f0e0e; -fx-effect:  dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 ); -fx-background-radius:  40em; -fx-border-radius: 40em");
        phaseText.setText("Подкидывание");
        deVisBut();
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            try {
                if (!secondPlayer.ifTossing(isTaped, mainArea.getGameFieldCardAttack(), mainArea.getGameFieldCardDefence(), mainArea.getMassMap())) {
                    endClearGameField();
                    nextButClick();
                    Arrays.fill(mainArea.getGameFieldCardDefence(), null);
                    Arrays.fill(mainArea.getGameFieldCardAttack(), null);
                    mainArea.distribution(secondPlayer.getSpd());
                    mainArea.distribution(firstPlayer.getFpd());
                    firstPlayer.getMass(mainArea.getMassMap());
                    secondPlayer.getMass(mainArea.getMassMap());
                    updatePage(pageNum);
                    endGame();
                } else {
                    mainArea.setPhase(Phase.FirstDefence);
                    phaseBut.setStyle("-fx-background-color:  #0e0e8f; -fx-effect:  dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 ); -fx-background-radius:  40em; -fx-border-radius: 40em");
                    phaseText.setText("Защита");
                    visBut();
                    attSlotsDis();
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

    @FXML
    private Phase phaseChanger(Phase thisPhase) {
        List<Phase> l = new ArrayList<>(Arrays.asList(Phase.values()));
        Phase nPhase = thisPhase;
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i) == thisPhase) {
                if (i == l.size() - 1) {
                    nPhase = l.get(0);
                    mainArea.setPhase(l.get(0));
                    break;
                } else {
                    nPhase = l.get(i + 1);
                    mainArea.setPhase(l.get(i + 1));
                    break;
                }

            }
        }
        return nPhase;
    }

    @FXML
    private void defSlotsDis() {
        for (ImageView cardSlot : gameCardSlots) {
            cardSlot.setDisable(false);
        }
        for (ImageView cardSlot : gameCardSlotsDefFirst) {
            cardSlot.setDisable(true);
        }
    }

    @FXML
    private void attSlotsDis() {
        for (ImageView cardSlot : gameCardSlots) {
            cardSlot.setDisable(true);
        }
        for (ImageView cardSlot : gameCardSlotsDefFirst) {
            cardSlot.setDisable(false);
        }
    }

    @FXML
    private void cardsTransfer() throws Exception {
        endGame();
        boolean isDefPrev = false;
        int notNullAttack = 0;
        for (int i = 0; i < mainArea.getGameFieldCardDefence().length; i++) {
            if (mainArea.getGameFieldCardDefence()[i] != null) {
                isDefPrev = true;
            }
            if (mainArea.getGameFieldCardAttack()[i] != null) {
                notNullAttack++;
            }
        }
        int indexTakenCard = 0;
        for (int i = 0; i < firstPlayer.getFpd().size(); i++) {
            if (firstPlayer.getFpd().get(i) == takenCard) {
                indexTakenCard = i;
                break;
            }
        }
        if (mainArea.getPhase() == Phase.FirstDefence && !isDefPrev) {
            assert mainArea.getGameFieldCardAttack()[0] != null;
            if (mainArea.getGameFieldCardAttack()[0].getRank() == takenCard.getRank()) {
                if (notNullAttack + 1 <= secondPlayer.getSpd().size()) {
                    if (notNullAttack <= 5) {
                        for (int i = 0; i < mainArea.getGameFieldCardAttack().length; i++) {
                            if (mainArea.getGameFieldCardAttack()[i] == null) {
                                mainArea.getGameFieldCardAttack()[i] = takenCard;
                                enemyStepVisual();
                                takenCard = new Card();
                                cardIsTaken = false;
                                for (ImageView cardSlot : cardSlots) {
                                    if (Integer.parseInt(cardSlot.getId().split("_")[1]) == (indexTakenCard - pageNum * 6) + 1) {
                                        cardSlot.setOpacity(1);
                                    }
                                }
                                firstPlayer.getFpd().remove(indexTakenCard);
                                updatePage(pageNum);
                                mainArea.setPhase(Phase.FirstAttack);
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
        if (mainArea.getPhase() == Phase.FirstDefence) {
            for (int i = 0; i < 6; i++) {
                if (mainArea.getGameFieldCardDefence()[i] != null) {
                    firstPlayer.getFpd().add(mainArea.getGameFieldCardDefence()[i]);
                }
                if (mainArea.getGameFieldCardAttack()[i] != null) {
                    firstPlayer.getFpd().add(mainArea.getGameFieldCardAttack()[i]);
                }
            }
            Arrays.fill(mainArea.getGameFieldCardDefence(), null);
            Arrays.fill(mainArea.getGameFieldCardAttack(), null);
            mainArea.distribution(secondPlayer.getSpd());
            mainArea.distribution(firstPlayer.getFpd());
            firstPlayer.getMass(mainArea.getMassMap());
            secondPlayer.getMass(mainArea.getMassMap());
            updatePage(pageNum);
            mainArea.setPhase(Phase.FirstTossing);
            cardsNumText.setText(String.valueOf(firstPlayer.getFpd().size()));
            nextButClick();
        }
    }

    @FXML
    private boolean checkToss() throws Exception {
        int counterAt = 0;
        int counterDef = 0;
        for (int i = 0; i < 6; i++) {
            if (mainArea.getGameFieldCardDefence()[i] != null) {
                counterDef++;
            }
            if (mainArea.getGameFieldCardAttack()[i] != null) {
                counterAt++;
            }
        }
        if (counterDef == counterAt) {
            endClearGameField();
            Arrays.fill(mainArea.getGameFieldCardDefence(), null);
            Arrays.fill(mainArea.getGameFieldCardAttack(), null);
            mainArea.distribution(firstPlayer.getFpd());
            mainArea.distribution(secondPlayer.getSpd());
            firstPlayer.getMass(mainArea.getMassMap());
            secondPlayer.getMass(mainArea.getMassMap());
            updatePage(pageNum);
            return true;
        } else {
            mainArea.setPhase(Phase.FirstAttack);
            return true;
        }
    }

    @FXML
    private boolean checkEqNum() {
        Card[] a = mainArea.getGameFieldCardAttack();
        Card[] b = mainArea.getGameFieldCardDefence();
        int num1 = 0;
        int num2 = 0;
        for (Card el : a) {
            if (el != null) {
                num1++;
            }
        }
        for (Card el1 : b) {
            if (el1 != null) {
                num2++;
            }
        }
        if (mainArea.getPhase() == Phase.FirstDefence) {
            return num1 == num2;
        } else if (mainArea.getPhase() == Phase.FirstAttack) {
            return num1 > 0;
        }
        return true;
    }

    @FXML
    private void endGame() {
        if (mainArea.getCardNum() == 0) {
            trumpCard.setImage(null);
            if (firstPlayer.getFpd().size() == 0) {
                deVisBut();
                phaseBut.setStyle("-fx-background-color:  #0e0e8f; -fx-effect:  dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 ); -fx-background-radius:  40em; -fx-border-radius: 40em");
                phaseText.setText("Победа");
                endSettings();
                return;
            }
            if (secondPlayer.getSpd().size() == 0) {
                deVisBut();
                phaseBut.setStyle("-fx-background-color:  #8f0e0e; -fx-effect:  dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 ); -fx-background-radius:  40em; -fx-border-radius: 40em");
                phaseText.setText("Поражение");
                endSettings();
            }
        }
        cardsNumText.setText(String.valueOf(firstPlayer.getFpd().size()));
    }

    @FXML
    private void endSettings() {
        gameIsEnded = true;
        endBut.setVisible(true);
        endBut.setDisable(false);
        mainArea.setStartGame(false);
        firstPlayer.setFpd(new ArrayList<>());
        firstPlayer.setMass(new ArrayList<>());
        secondPlayer.setSpd(new ArrayList<>());
        secondPlayer.setMass(new ArrayList<>());
        mainArea.setDeck(mainArea.createDeck());
        mainArea.setPhase(Phase.FirstAttack);
        mainArea.setCardNum(54);
        pageNum = 0;
        mainArea.setGameFieldCardAttack(new Card[6]);
        mainArea.setGameFieldCardDefence(new Card[6]);
        takenCard = new Card();
        cardIsTaken = false;
        isTaped = new boolean[6];
        isDefFirstTaped = new boolean[6];
        isDefSecondTaped = new boolean[6];
    }

    @FXML
    private void endGameBut(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        Scene scene = new Scene(parent, 1300, 750);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("CardGame");
        ImageView imageView = (ImageView) parent.getChildrenUnmodifiable().get(0);
        ImageView imageView2 = (ImageView) parent.getChildrenUnmodifiable().get(2);
        ImageView imageView5 = (ImageView) parent.getChildrenUnmodifiable().get(4);
        ImageView imageView6 = (ImageView) parent.getChildrenUnmodifiable().get(5);
        ImageView imageView7 = (ImageView) parent.getChildrenUnmodifiable().get(6);
        ImageView imageView8 = (ImageView) parent.getChildrenUnmodifiable().get(7);
        ImageView imageView9 = (ImageView) parent.getChildrenUnmodifiable().get(8);
        ImageView imageView10 = (ImageView) parent.getChildrenUnmodifiable().get(9);
        ImageView imageView11 = (ImageView) parent.getChildrenUnmodifiable().get(10);
        ImageView imageView12 = (ImageView) parent.getChildrenUnmodifiable().get(11);
        Image image = new Image("file:Sprites/BackGround.jpg");
        Image image3 = new Image("file:Sprites/logo2.png");
        Image image2 = new Image("file:Sprites/Logo.png");
        Image image6 = new Image("file:Sprites/cards_Hearts_Jack.png.png");
        Image image7 = new Image("file:Sprites/cards_Clubs_Jack.png.png");
        Image image8 = new Image("file:Sprites/cards_Diamonds_King.png.png");
        Image image9 = new Image("file:Sprites/cards_Spades_King.png.png");
        Image image10 = new Image("file:Sprites/cards_Red_Joker.png");
        Image image11 = new Image("file:Sprites/cards_Black_Joker.png");
        Image image12 = new Image("file:Sprites/pushkaLogo.png");
        imageView6.setImage(image6);
        imageView7.setImage(image7);
        imageView8.setImage(image8);
        imageView9.setImage(image9);
        imageView10.setImage(image10);
        imageView11.setImage(image11);
        imageView12.setImage(image12);
        imageView2.setImage(image2);
        imageView.setImage(image);
        imageView5.setImage(image3);
        gameIsEnded = false;
        window.setScene(scene);
        window.show();
        window.setResizable(false);
        window.setResizable(false);

    }

    @FXML
    private void endClearGameField() {
        Image thisImage = new Image("file:Sprites/tracing.png");
        for (ImageView el : gameCardSlots) {
            String s = el.getId().split("_")[1];
            isTaped[Integer.parseInt(s) - 1] = false;
            el.setImage(thisImage);
        }
        for (ImageView el1 : gameCardSlotsDefFirst) {
            String s = el1.getId().split("_")[1];
            isDefFirstTaped[Integer.parseInt(s) - 1] = false;
            el1.setImage(null);
        }
        for (ImageView el2 : gameCardSlotsDefSecond) {
            String s = el2.getId().split("_")[1];
            isDefSecondTaped[Integer.parseInt(s) - 1] = false;
            el2.setImage(null);
        }
    }

    @FXML
    private void giveCards() throws Exception {
        mainArea = new MainArea();
        firstPlayer = new FirstPlayer();
        secondPlayer = new SecondPlayer();
        enemyPane.setDisable(false);
        if (!mainArea.isStartGame()) {
            gameCardSlots = new ImageView[]{gameCardSlot_1, gameCardSlot_2, gameCardSlot_3, gameCardSlot_4, gameCardSlot_5, gameCardSlot_6};
            gameCardSlotsDefFirst = new ImageView[]{gameCardSlotDefFirst_1, gameCardSlotDefFirst_2, gameCardSlotDefFirst_3, gameCardSlotDefFirst_4, gameCardSlotDefFirst_5, gameCardSlotDefFirst_6};
            gameCardSlotsDefSecond = new ImageView[]{gameCardSlotDefSecond_1, gameCardSlotDefSecond_2, gameCardSlotDefSecond_3, gameCardSlotDefSecond_4, gameCardSlotDefSecond_5, gameCardSlotDefSecond_6};
            cardSlots = new ImageView[]{cardSlot_1, cardSlot_2, cardSlot_3, cardSlot_4, cardSlot_5, cardSlot_6};
            mainArea.randomDeck();
            mainArea.giveMassToMap();
            Image thisImage = new Image("file:Sprites/EnemyDeck.png");
            enemyDeck.setImage(thisImage);
            firstPlayer.setFpd(mainArea.distribution(firstPlayer.getFpd()));
            secondPlayer.setSpd(mainArea.distribution(secondPlayer.getSpd()));
            firstPlayer.getMass(mainArea.getMassMap());
            updatePage(pageNum);
            mainArea.setStartGame(true);
            deckNum.setText(String.valueOf(mainArea.getCardNum()));
            deckNum.setVisible(true);
            deckNumText.setVisible(false);
            enemyDeckNum.setText(String.valueOf(secondPlayer.getSpd().size()));
            enemyDeckNum.setVisible(true);
            enemyDeckCircle.setVisible(true);
            prevBut1.setVisible(true);
            nextBut1.setVisible(true);
            Image trumpIm = mainArea.getDeck().get(mainArea.getDeck().size() - 1).getImage();
            trumpCard.setImage(trumpIm);
            visBut();
            phaseText.setVisible(true);
            phaseBut.setVisible(true);
            cardsNumText.setText(String.valueOf(firstPlayer.getFpd().size()));
            Random rand = new Random();
            int n = rand.nextInt(2) + 1;
            if (n == 1) {
                mainArea.setPhase(Phase.SecondTossing);
                nextButClick();
            } else {
                mainArea.setPhase(Phase.FirstTossing);
                nextButClick();
            }
        }

    }


    @FXML
    private void nextPageBut() {
        int num =  (int)Math.floor((firstPlayer.getFpd().size() - 1)/6.0);
        if (pageNum < num) {
            pageNum = pageNum + 1;
        } else {
            pageNum = 0;
        }
        updatePage(pageNum);
    }


    @FXML
    private void prevPageBut() {
        int num =  (int)Math.floor((firstPlayer.getFpd().size() - 1)/6.0);
        if (pageNum > 0) {
            pageNum = pageNum - 1;
        } else {
            pageNum = num;
        }
        updatePage(pageNum);
    }

    @FXML
    private void enemyStepVisual() throws InterruptedException {
        Card[] attack = mainArea.getGameFieldCardAttack();
        Card[] deff = mainArea.getGameFieldCardDefence();
        ImageView[] curDef = new ImageView[6];
        boolean[] curTaped = new boolean[6];
        if (mainArea.getPhase() == Phase.SecondAttack || mainArea.getPhase() == Phase.FirstDefence) {
            curDef = gameCardSlotsDefFirst;
            curTaped = isDefFirstTaped;
        } else if (mainArea.getPhase() == Phase.SecondDefence || mainArea.getPhase() == Phase.SecondTossing) {
            curDef = gameCardSlotsDefSecond;
            curTaped = isDefSecondTaped;
        }
        for (int i = 0; i < attack.length; i++) {
            if (attack[i] != null) {
                gameCardSlots[i].setImage(attack[i].getImage());
                isTaped[i] = true;
            }
        }
        for (int i = 0; i < deff.length; i++) {
            if (deff[i] != null) {
                curDef[i].setImage(deff[i].getImage());
                curTaped[i] = true;
            }
        }
    }

    @FXML
    private void visBut() {
        nextBut.setVisible(true);
        reverseBut.setVisible(true);
        takeCardBut.setVisible(true);
    }

    @FXML
    private void deVisBut() {
        nextBut.setVisible(false);
        reverseBut.setVisible(false);
        takeCardBut.setVisible(false);
    }

    @FXML
    private void updatePage(int pageNum) {
        try {
            List<Card> list = firstPlayer.getFpd();
            List<Card> curList = new ArrayList<>();
            for (int e = pageNum * 6; e < pageNum * 6 + 6; e++) {
                if (e < list.size()) {
                    curList.add(list.get(e));
                }
            }
            AnchorPane thisAnchor = (AnchorPane) test.getChildren().get(6);
            for (int i = 0; i < 6; i++) {
                ImageView thisView = (ImageView) thisAnchor.getChildren().get(i+3);
                ImageView thisViewGame = (ImageView) test.getChildren().get(i + 11);
                ImageView thisViewGameDef = (ImageView) test.getChildren().get(i + 17);
                ImageView thisViewGameDefEnemy = (ImageView) test.getChildren().get(i + 23);
                Image thisImage = new Image("file:Sprites/tracing.png");
                thisView.setImage(thisImage);
                if (!isTaped[i]) {
                    thisViewGame.setImage(thisImage);
                }
                if (!isDefFirstTaped[i]) {
                    thisViewGameDef.setImage(null);
                }
                if (!isDefSecondTaped[i]) {
                    thisViewGameDefEnemy.setImage(null);
                }
            }
            for (int i = 0; i < curList.size(); i++) {
                ImageView thisView = (ImageView) thisAnchor.getChildren().get(i+3);
                Image thisImage = curList.get(i).getImage();
                thisView.setImage(thisImage);
            }
            enemyDeckNum.setText(String.valueOf(secondPlayer.getSpd().size()));
            deckNum.setText(String.valueOf(mainArea.getCardNum()));
            cardsNumText.setText(String.valueOf(firstPlayer.getFpd().size()));
        } catch (NullPointerException e) {
            System.out.println("You clicked on an empty cell");
        }
    }

    @FXML
    private void takeCard() {
        try {
            if (gameIsEnded) {
                return;
            }
            for (ImageView cardSlot : cardSlots) {
                cardSlot.setOpacity(1);
            }
            for (ImageView cardSlot : cardSlots) {
                if (cardSlot.isHover()) {
                    String s = cardSlot.getId().split("_")[1];
                    int page = pageNum;
                    Card takenCardC = firstPlayer.getFpd().get(6 * page + (Integer.parseInt(s) - 1));
                    if (takenCardC == takenCard) {
                        takenCard = null;
                        cardIsTaken = false;
                        break;
                    }
                    takenCard = takenCardC;
                    cardIsTaken = true;
                    cardSlot.setOpacity(0.5);
                    break;
                }
                cardsNumText.setText(String.valueOf(firstPlayer.getFpd().size()));
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("You clicked on an empty cell");
        }
    }

    private boolean checkEqualsAttack() {
        if (!cardIsTaken) {
            return true;
        }
        boolean isNotEmpty = false;
        for (int i = 0; i < mainArea.getGameFieldCardAttack().length; i++) {
            if (mainArea.getGameFieldCardAttack()[i] != null) {
                isNotEmpty = true;
                break;
            }
        }
        if (!isNotEmpty) {
            return true;
        } else {
            boolean check = true;
            for (int i = 0; i < mainArea.getGameFieldCardAttack().length; i++) {
                if (mainArea.getGameFieldCardAttack()[i] != null) {
                    if (!(takenCard.getRank() == mainArea.getGameFieldCardAttack()[i].getRank())) {
                        check = false;
                        break;
                    }
                }
            }
            return check;
        }
    }

    @FXML
    private boolean checkEqualsToss() {
        if (!cardIsTaken) {
            return true;
        }
        boolean check = false;
        int notNullAttack = 0;
        int notNullDefence = 0;
        for (int i = 0; i < mainArea.getGameFieldCardAttack().length; i++) {
            if (mainArea.getGameFieldCardAttack()[i] != null) {
                notNullAttack++;
            }

            if (mainArea.getGameFieldCardDefence()[i] != null) {
                notNullDefence++;
            }
        }
        if(notNullAttack == (notNullDefence + secondPlayer.getSpd().size())){
            return check;
        }
        for (int i = 0; i < mainArea.getGameFieldCardAttack().length; i++) {
            if (mainArea.getGameFieldCardAttack()[i] != null) {
                if (takenCard.getRank() == mainArea.getGameFieldCardAttack()[i].getRank()) {
                    check = true;
                    break;
                }
            }
        }
        for (int i = 0; i < mainArea.getGameFieldCardDefence().length; i++) {
            if (mainArea.getGameFieldCardDefence()[i] != null) {
                if (takenCard.getRank() == mainArea.getGameFieldCardDefence()[i].getRank()) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }


    @FXML
    private void putCard() {
        try {
            if (gameIsEnded) {
                return;
            }
            ImageView[] cur = new ImageView[6];
            boolean[] curTaped = new boolean[6];
            Card[] curDoing = new Card[6];
            Card[] curDoingCompare = new Card[6];
            if (mainArea.getPhase() == Phase.FirstAttack || mainArea.getPhase() == Phase.FirstTossing) {
                if (!checkEqualsAttack() && mainArea.getPhase() == Phase.FirstAttack) {
                    return;
                }
                if (!checkEqualsToss() && mainArea.getPhase() == Phase.FirstTossing) {
                    return;
                }
                cur = gameCardSlots;
                curTaped = isTaped;
                curDoing = mainArea.getGameFieldCardAttack();
                curDoingCompare = mainArea.getGameFieldCardDefence();
            } else if (mainArea.getPhase() == Phase.FirstDefence) {
                cur = gameCardSlotsDefFirst;
                curTaped = isDefFirstTaped;
                curDoing = mainArea.getGameFieldCardDefence();
                curDoingCompare = mainArea.getGameFieldCardAttack();
            } else if (mainArea.getPhase() == Phase.SecondDefence) {
                return;
            } else if (mainArea.getPhase() == Phase.SecondAttack) {
                return;
            }
            for (ImageView cardSlot : cur) {
                String s = cardSlot.getId().split("_")[1];
                if (cardSlot.isHover() && cardIsTaken) {
                    putInto(curDoingCompare, curTaped, curDoing, cardSlot, s);
                } else if (cardSlot.isHover() && curTaped[Integer.parseInt(s) - 1]) {
                    putFrom(curDoing, curTaped, cardSlot, s);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("You need to select a new card");
        } catch (ArrayIndexOutOfBoundsException e1) {
            System.out.println("There is no enemy card here");
        }
    }

    @FXML
    private void putInto(Card[] curDoingCompare, boolean[] curTaped, Card[] curDoing, ImageView cardSlot, String s) {
        if (mainArea.getPhase() == Phase.FirstDefence) {
            if (!mainArea.cardComparator(takenCard, curDoingCompare[Integer.parseInt(s) - 1])) {
                return;
            }
        }
        if (mainArea.getPhase() == Phase.FirstTossing) {
            if (isDefSecondTaped[Integer.parseInt(s) - 1]) {
                return;
            }
        }
        if (curTaped[Integer.parseInt(s) - 1]) {
            Card transpCard = curDoing[Integer.parseInt(s) - 1];
            curDoing[Integer.parseInt(s) - 1] = takenCard;
            cardSlot.setImage(takenCard.getImage());
            cardsNumText.setText(String.valueOf(firstPlayer.getFpd().size()));
            for (int i = 0; i < firstPlayer.getFpd().size(); i++) {
                if (firstPlayer.getFpd().get(i) == takenCard) {
                    firstPlayer.getFpd().set(i, transpCard);
                    firstPlayer.getMass(mainArea.getMassMap());
                    endGame();
                    break;
                }
            }
            for (ImageView cardSlotO : cardSlots) {
                cardSlotO.setOpacity(1);
            }
            cardIsTaken = false;
            takenCard = null;
            updatePage(pageNum);
            endGame();
        } else {
            if (mainArea.getPhase() == Phase.FirstDefence) {
                if (!mainArea.cardComparator(takenCard, curDoingCompare[Integer.parseInt(s) - 1])) {
                    return;
                }
            }
            curDoing[Integer.parseInt(s) - 1] = takenCard;
            cardSlot.setImage(takenCard.getImage());
            cardsNumText.setText(String.valueOf(firstPlayer.getFpd().size()));
            curTaped[Integer.parseInt(s) - 1] = true;
            for (int i = 0; i < firstPlayer.getFpd().size(); i++) {
                if (firstPlayer.getFpd().get(i) == takenCard) {
                    firstPlayer.getFpd().remove(i);
                    firstPlayer.getMass(mainArea.getMassMap());
                    endGame();
                    break;
                }
            }
            for (ImageView cardSlotO : cardSlots) {
                cardSlotO.setOpacity(1);
            }
            cardIsTaken = false;
            takenCard = null;
            updatePage(pageNum);
            endGame();
        }
    }

    @FXML
    private void putFrom(Card[] curDoing, boolean[] curTaped, ImageView cardSlot, String s){
        if (mainArea.getPhase() == Phase.FirstTossing) {
            if (isDefSecondTaped[Integer.parseInt(s) - 1]) {
                return;
            }
        }
        firstPlayer.getFpd().add(curDoing[Integer.parseInt(s) - 1]);
        curDoing[Integer.parseInt(s) - 1] = null;
        updatePage(pageNum);
        if (mainArea.getPhase() == Phase.FirstDefence) {
            cardSlot.setImage(null);
            curTaped[Integer.parseInt(s) - 1] = false;
        } else {
            Image thisImage = new Image("file:Sprites/tracing.png");
            cardSlot.setImage(thisImage);
            cardsNumText.setText(String.valueOf(firstPlayer.getFpd().size()));
            curTaped[Integer.parseInt(s) - 1] = false;
        }
        firstPlayer.getMass(mainArea.getMassMap());
        endGame();
    }
}