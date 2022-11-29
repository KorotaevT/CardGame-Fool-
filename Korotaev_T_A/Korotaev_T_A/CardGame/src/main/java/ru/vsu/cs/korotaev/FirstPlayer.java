package ru.vsu.cs.korotaev;

import java.util.ArrayList;
import java.util.List;

public class FirstPlayer {
    private static List<Card> fpd = new ArrayList<>();

    public FirstPlayer(){

    }

    public static List<Card> getFpd() {
        return fpd;
    }

    public static void setFpd(List<Card> fpd) {
        FirstPlayer.fpd = fpd;
    }
}
