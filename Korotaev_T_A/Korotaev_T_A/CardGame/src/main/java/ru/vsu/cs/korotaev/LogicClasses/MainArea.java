package ru.vsu.cs.korotaev.LogicClasses;

import javafx.scene.image.Image;
import ru.vsu.cs.korotaev.Enums.Color;
import ru.vsu.cs.korotaev.Enums.Phase;
import ru.vsu.cs.korotaev.Enums.Rank;
import ru.vsu.cs.korotaev.ObjectClasses.Card;

import java.util.*;

public class MainArea {
    private static List<Card> deck = createDeck();
    private static Phase phase = Phase.FirstAttack;
    private static Color trumpCol;
    private static boolean isStartGame;
    private static int cardNum = 54;
    private static HashMap<Rank, Integer> massMap = new HashMap<Rank, Integer>();
    private static Card[] gameFieldCardAttack = new Card[6];
    private static Card[] gameFieldCardDefence = new Card[6];


    public static void giveMassToMap() {
        massMap.put(Rank.Two, 200);
        massMap.put(Rank.Three, 300);
        massMap.put(Rank.Four, 400);
        massMap.put(Rank.Five, 500);
        massMap.put(Rank.Six, 600);
        massMap.put(Rank.Seven, 700);
        massMap.put(Rank.Eight, 800);
        massMap.put(Rank.Nine, 900);
        massMap.put(Rank.Ten, 1000);
        massMap.put(Rank.Jack, 1100);
        massMap.put(Rank.Queen, 1200);
        massMap.put(Rank.King, 1300);
        massMap.put(Rank.Ace, 1400);
        massMap.put(Rank.Joker, 1500);
    }

    private static <T> void shuffle(List<T> list) {
        Random random = new Random();
        for (int i = list.size() - 1; i >= 1; i--) {
            int j = random.nextInt(i + 1);
            T obj = list.get(i);
            list.set(i, list.get(j));
            list.set(j, obj);
        }
    }

    public static List<Card> createDeck() {
        List<Card> list = new ArrayList<>();
        for (Color allColor : Color.values()) {
            if (allColor != Color.Black && allColor != Color.Red) {
                for (Rank allRank : Rank.values()) {
                    if (allRank != Rank.Joker) {
                        Image image = new Image("file:Sprites/cards_" + allColor + "_" + allRank + ".png.png");
                        list.add(new Card(allColor, allRank, false, image));
                    }
                }
            }
        }
        Image image1 = new Image("file:Sprites/cards_Red_Joker.png");
        Image image2 = new Image("file:Sprites/cards_Black_Joker.png");
        list.add(new Card(Color.Black, Rank.Joker, false, image2));
        list.add(new Card(Color.Red, Rank.Joker, false, image1));
        return list;
    }

    public static void randomDeck() {
        shuffle(deck);
        trumpCol = deck.get(deck.size() - 1).getColor();
        for (Card el : deck) {
            if (el.getColor() == trumpCol) {
                el.setTrump(true);
            }
            if (el.getColor() == Color.Red && (trumpCol == Color.Diamonds || trumpCol == Color.Hearts)) {
                el.setTrump(true);
            }
            if (el.getColor() == Color.Black && (trumpCol == Color.Clubs || trumpCol == Color.Spades)) {
                el.setTrump(true);
            }
        }
    }

    public static List<Card> distribution(List<Card> first) {
        if (first.size() < 6) {
            for (int i = first.size(); i < 6; i++) {
                for (Card card : deck) {
                    if (!card.isBroken()) {
                        first.add(card);
                        card.setBroken(true);
                        cardNum -= 1;
                        break;
                    }
                }
            }
        }
        return first;
    }

    public static boolean cardComparator(Card first, Card second) {
        int mass1 = 0;
        int mass2 = 0;
        for (HashMap.Entry<Rank, Integer> entry : MainArea.getMassMap().entrySet()) {
            Rank key = entry.getKey();
            Integer value = entry.getValue();
            if (first.getRank() == key) {
                mass1 = value;
                if (first.isTrump()) {
                    mass1 += 1400;
                }
            }
            if (second.getRank() == key) {
                mass2 = value;
                if (second.isTrump()) {
                    mass2 += 1400;
                }
            }
        }
        Color trumpColJ;
        if (trumpCol == Color.Diamonds || trumpCol == Color.Hearts || trumpCol == Color.Red) {
            trumpColJ = Color.Red;
        } else {
            trumpColJ = Color.Black;
        }
        if (first.getRank() == Rank.Joker && first.getColor() == trumpColJ) {
            return true;
        }
        if (second.getRank() == Rank.Joker && second.getColor() == trumpColJ) {
            return false;
        }
        if (first.getRank() != Rank.Joker && second.getRank() != Rank.Joker && first.getColor() != second.getColor()) {
            return first.isTrump();
        }
        if (first.getRank() != Rank.Joker && second.getRank() != Rank.Joker && first.getColor() == second.getColor()) {
            return mass1 > mass2;
        }
        if (first.getRank() == Rank.Joker && first.getColor() != trumpColJ) {
            if (first.getColor() == Color.Red) {
                if (second.getColor() == Color.Hearts || second.getColor() == Color.Diamonds) {
                    return true;
                }
            } else if (first.getColor() == Color.Black) {
                if (second.getColor() == Color.Spades || second.getColor() == Color.Clubs) {
                    return true;
                }
            } else {
                return false;
            }
        }
        if (second.getRank() == Rank.Joker && second.getColor() == trumpColJ) {
            return first.isTrump();
        }
        return false;
    }



    public static List<Card> getDeck() {
        return deck;
    }

    public static void setDeck(List<Card> deck) {
        MainArea.deck = deck;
    }

    public static Phase getPhase() {
        return phase;
    }

    public static void setPhase(Phase phase) {
        MainArea.phase = phase;
    }

    public static Color getTrumpCol() {
        return trumpCol;
    }

    public static void setTrumpCol(Color trumpCol) {
        MainArea.trumpCol = trumpCol;
    }

    public static HashMap<Rank, Integer> getMassMap() {
        return massMap;
    }

    public static void setMassMap(HashMap<Rank, Integer> massMap) {
        MainArea.massMap = massMap;
    }

    public static Card[] getGameFieldCardAttack() {
        return gameFieldCardAttack;
    }

    public static void setGameFieldCardAttack(Card[] gameFieldCardAttack) {
        MainArea.gameFieldCardAttack = gameFieldCardAttack;
    }

    public static Card[] getGameFieldCardDefence() {
        return gameFieldCardDefence;
    }

    public static void setGameFieldCardDefence(Card[] gameFieldCardDefence) {
        MainArea.gameFieldCardDefence = gameFieldCardDefence;
    }

    public static boolean isIsStartGame() {
        return isStartGame;
    }

    public static void setIsStartGame(boolean isStartGame) {
        MainArea.isStartGame = isStartGame;
    }

    public static int getCardNum() {
        return cardNum;
    }

    public static void setCardNum(int cardNum) {
        MainArea.cardNum = cardNum;
    }
}