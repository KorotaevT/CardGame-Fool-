package ru.vsu.cs.korotaev;

import javafx.scene.image.Image;

import java.util.*;

public class MainArea {
    private static List<Card> deck = createDeck();
    private static Phase phase;
    private static Color trumpCol;

    private static List<Card> createDeck(){
        List<Card> list = new ArrayList<>();
        for (Color allColor : Color.values()){
            if(allColor != Color.Black && allColor !=Color.Red) {
                for (Rank allRank : Rank.values()) {
                    if (allRank != Rank.Joker) {
                        Image image = new Image("file:Sprites/cards_" + allColor + "_" + allRank +".png");
                        list.add(new Card(allColor, allRank, false, image));
                    }
                }
            }
        }
        Image image1 = new Image("file:Sprites/cards_Red_Joker" );
        Image image2 = new Image("file:Sprites/cards_Black_Joker");
        list.add(new Card(Color.Black, Rank.Joker, false, image2));
        list.add(new Card(Color.Red, Rank.Joker, false, image1));
        return list;
    }

    public static void randomDeck(){
        Collections.shuffle(deck, new Random());
        trumpCol = deck.get(deck.size()-1).getColor();
    }

    public static List<Card> distribution(List<Card> first) {
        if (first.size() < 6) {
            for (int i = first.size(); i < 6; i++) {
                for (Card card : deck) {
                    if (!card.isBroken()) {
                        first.add(card);
                        card.setBroken(true);
                        break;
                    }
                    }
                }
        }
        return first;
    }
}
