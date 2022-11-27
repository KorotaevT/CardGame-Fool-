package ru.vsu.cs.korotaev;

import java.util.ArrayList;
import java.util.List;

public class FirstPlayer {
    private List<Card> fpd = new ArrayList<>();

    public FirstPlayer(){

    }

    public List<Card> getFpd() {
        return this.fpd;
    }

    public void setFpd(List<Card> fpd) {
        this.fpd = fpd;
    }
}
