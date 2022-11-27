package ru.vsu.cs.korotaev;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class MainArea {
    private static List<Card> deck = new ArrayList<>();

    private static void createDeck(){
        for (Color allColor : Color.values()){
            for (Rank allRank : Rank.values()){
                Image image = new Image("file:Sprites/cards_");
                deck.add(new Card(allColor, allRank, false, image));
            }
        }
    }
}
