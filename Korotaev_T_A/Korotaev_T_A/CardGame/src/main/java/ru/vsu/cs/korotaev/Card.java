package ru.vsu.cs.korotaev;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card {
    private Color color;
    private Rank rank;
    private boolean trump;
    private Image image;

    public Card(Color color, Rank rank, boolean trump, Image image) {
        this.color = color;
        this.rank = rank;
        this.trump = trump;
        this.image = image;
    }
}
