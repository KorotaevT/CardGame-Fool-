package ru.vsu.cs.korotaev;

import java.lang.reflect.Array;
import java.util.*;

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

    public static void ifAttack(){
        List<Card> attackDeck = new ArrayList<>();
        List<Integer> index = new ArrayList<>();
        attackDeck.add(spd.get(0));
        index.add(0);
        for(int i = 1; i<spd.size(); i++){
            if(Objects.equals(mass.get(i), mass.get(index.get(0)))) {
                attackDeck.add(spd.get(i));
                index.add(i);
            }
        }
        for (Integer integer : index) {
            spd.remove((int) integer);
        }
        Card[] attack = new Card[6];
        for(int i = 0; i<attackDeck.size(); i++){
            attack[i] = attackDeck.get(i);
        }
        MainArea.setGameFieldCardAttack(attack);

    }

    public static boolean ifTossing() throws Exception {
        boolean isAdd = false;
        Card[] defDeck = MainArea.getGameFieldCardDefence();
        Card[] atDeck = MainArea.getGameFieldCardAttack();
        for(int i = 0; i<spd.size(); i++){
            for (Card card : defDeck) {
                if (card!=null) {
                    if(spd.get(i).getRank() == card.getRank() && !spd.get(i).isTrump()){
                        for (int k = 0; k<atDeck.length; k++) {
                            if(atDeck[k]==null) {
                                atDeck[k]=spd.get(i);
                                MainArea.getIsTaped()[k]=true;
                                spd.remove(i);
                                if(i!=0) {
                                    i--;
                                }
                                isAdd = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        MainArea.setGameFieldCardAttack(atDeck);
        getMass();
        return isAdd;
    }

    public static void ifDefence(){
        Card[] defenceDeck = MainArea.getGameFieldCardDefence();
        for(int e = 0; e<MainArea.getGameFieldCardAttack().length; e++){
            for(int i = 1; i<spd.size(); i++){
                if(!MainArea.getIsDefSecondTaped()[e] && MainArea.getGameFieldCardAttack()[e]!=null && MainArea.cardComparator(spd.get(i), MainArea.getGameFieldCardAttack()[e])) {
                    defenceDeck[e] = spd.get(i);
                    spd.remove(i);
                    break;
                }
            }
        }
        MainArea.setGameFieldCardDefence(defenceDeck);
    }
}
