package ru.vsu.cs.korotaev.ObjectClasses;

import javafx.scene.image.Image;
import ru.vsu.cs.korotaev.Enums.Color;
import ru.vsu.cs.korotaev.Enums.Rank;

public class Card {
    private Color color;
    private Rank rank;
    private boolean trump;
    private Image image;
    private boolean broken = false;

    public Card(Color color, Rank rank, boolean trump, Image image) {
        this.color = color;
        this.rank = rank;
        this.trump = trump;
        this.image = image;
    }

    public Card(){}

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public boolean isTrump() {
        return trump;
    }

    public void setTrump(boolean trump) {
        this.trump = trump;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isBroken() {
        return broken;
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }
}
