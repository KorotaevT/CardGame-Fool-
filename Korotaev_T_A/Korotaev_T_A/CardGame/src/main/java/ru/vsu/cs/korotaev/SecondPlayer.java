package ru.vsu.cs.korotaev;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SecondPlayer {
    private static List<Card> spd = new ArrayList<>();
    private static List<Integer> mass = new ArrayList<>();

    public static List<Card> getSpd() {
        return spd;
    }

    public static void setSpd(List<Card> spd) {
        SecondPlayer.spd = spd;
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

    public static void getMass() throws Exception {
        mass.clear();
        for(int e = 0; e<spd.size(); e++){
            mass.add(0);
        }
        for(int i = 0; i<spd.size(); i++){
            for (HashMap.Entry<Rank, Integer> entry : MainArea.getMassMap().entrySet()) {
                Rank key = entry.getKey();
                Integer value = entry.getValue();
                if(spd.get(i).getRank() == key){
                    mass.set(i, value);
                    if(spd.get(i).isTrump()){
                        mass.set(i, mass.get(i)+1400);
                    }
                    break;
                }
            }
        }
        Object[] sortSpd = spd.stream().toArray();
        int[] sortMass = mass.stream().mapToInt(i->i).toArray();
        HeapSort.sort(sortSpd, sortMass);
        spd = convertCArrayToList(sortSpd);
        mass = convertMArrayToList(sortMass);
    }
}
