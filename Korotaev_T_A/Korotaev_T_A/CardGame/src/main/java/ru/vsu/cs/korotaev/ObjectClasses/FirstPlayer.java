package ru.vsu.cs.korotaev.ObjectClasses;

import ru.vsu.cs.korotaev.Enums.Rank;
import ru.vsu.cs.korotaev.LogicClasses.MainArea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FirstPlayer {
    private static List<Card> fpd = new ArrayList<>();

    private static List<Integer> mass = new ArrayList<>();

    public FirstPlayer(){

    }

    public static List<Card> getFpd() {
        return fpd;
    }

    public static void setFpd(List<Card> fpd) {
        FirstPlayer.fpd = fpd;
    }


    public static <Card> List<Card> convertCArrayToList(Object[] array)
    {
        List<Card> list = new ArrayList<>();
        for (Object t : array) {
            Card c = (Card) t;
            list.add(c);
        }
        return list;
    }

    public static <Integer> List<Integer> convertMArrayToList(int[] array)
    {
        List<Integer> list = new ArrayList<>();
        for (Object t : array) {
            Integer c = (Integer) t;
            list.add(c);
        }
        return list;
    }

    public static void setMass(List<Integer> mass) {
        FirstPlayer.mass = mass;
    }

    public static void getMass(){
            mass.clear();
            for (int e = 0; e < fpd.size(); e++) {
                mass.add(0);
            }
            for (int i = 0; i < fpd.size(); i++) {
                for (HashMap.Entry<Rank, Integer> entry : MainArea.getMassMap().entrySet()) {
                    Rank key = entry.getKey();
                    Integer value = entry.getValue();
                    if (fpd.get(i).getRank() == key) {
                        mass.set(i, value);
                        if (fpd.get(i).isTrump()) {
                            mass.set(i, mass.get(i) + 1400);
                        }
                        break;
                    }
                }
            }


    }
}
