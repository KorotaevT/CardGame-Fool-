package ru.vsu.cs.korotaev.ObjectClasses;

import ru.vsu.cs.korotaev.Enums.Rank;
import ru.vsu.cs.korotaev.LogicClasses.HeapSort;
import ru.vsu.cs.korotaev.LogicClasses.MainArea;

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
        if(sortMass.length>1) {
            HeapSort.sort(sortSpd, sortMass);
        }
        spd = convertCArrayToList(sortSpd);
        mass = convertMArrayToList(sortMass);
    }

    public static void ifAttack() throws Exception {
        List<Card> attackDeck = new ArrayList<>();
        attackDeck.add(spd.get(0));
        Card cur = spd.get(0);
        Integer curM = mass.get(0);
        spd.remove(0);
        getMass();
        for(int i = 0; i<spd.size(); i++){
            if(Objects.equals(mass.get(i), curM)) {
                attackDeck.add(spd.get(i));
                spd.remove(i);
                getMass();
                i=0;
            }
        }
        Card[] attack = new Card[6];
        for(int i = 0; i<attackDeck.size(); i++){
            attack[i] = attackDeck.get(i);
        }
        MainArea.setGameFieldCardAttack(attack);

    }

    public static boolean ifTossing(boolean[] isTaped) throws Exception {
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
                                isTaped[k]=true;
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

    public static boolean ifTransfer(){
        boolean isDefPrev = false;
        int notNullAttack = 0;
        for (int i = 0; i < MainArea.getGameFieldCardDefence().length; i++) {
            if (MainArea.getGameFieldCardDefence()[i] != null) {
                isDefPrev = true;
            }
            if (MainArea.getGameFieldCardAttack()[i] != null) {
                notNullAttack++;
            }
        }
        if (!isDefPrev) {
            for(int e = 0; e< spd.size(); e++) {
                Card check = new Card();
                for(int i = 0; i<MainArea.getGameFieldCardAttack().length; i++){
                    if(MainArea.getGameFieldCardAttack()[i]!=null){
                        check = MainArea.getGameFieldCardAttack()[i];
                    }
                }
                if (check.getRank() == spd.get(e).getRank() && !spd.get(e).isTrump()) {
                    if (notNullAttack + 1 <= FirstPlayer.getFpd().size()) {
                        if (notNullAttack <= 5) {
                            for (int i = 0; i < MainArea.getGameFieldCardAttack().length; i++) {
                                if (MainArea.getGameFieldCardAttack()[i] == null) {
                                    MainArea.getGameFieldCardAttack()[i] = spd.get(e);
                                    spd.remove(e);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void pickUpEnemyCards() {
        for(int i = 0; i<6; i++) {
            if (MainArea.getGameFieldCardDefence()[i] != null) {
               SecondPlayer.getSpd().add(MainArea.getGameFieldCardDefence()[i]);
            }
            if (MainArea.getGameFieldCardAttack()[i] != null) {
                SecondPlayer.getSpd().add(MainArea.getGameFieldCardAttack()[i]);
            }
        }
    }

    public static boolean ifDefence(boolean[] isDefSecondTaped){
        Card[] defenceDeck = MainArea.getGameFieldCardDefence();
        for(int e = 0; e<MainArea.getGameFieldCardAttack().length; e++){
            for(int i = 1; i<spd.size(); i++){
                if(!isDefSecondTaped[e] && MainArea.getGameFieldCardAttack()[e]!=null && MainArea.cardComparator(spd.get(i), MainArea.getGameFieldCardAttack()[e])) {
                    defenceDeck[e] = spd.get(i);
                    spd.remove(i);
                    break;
                }
            }
        }
        int defence_num = 0;
        int at_num = 0;
        for(int e = 0; e<MainArea.getGameFieldCardAttack().length; e++){
            if(MainArea.getGameFieldCardAttack()[e]!=null){
                at_num++;
            }
            if(MainArea.getGameFieldCardDefence()[e]!=null){
                defence_num++;
            }
        }
        if(defence_num==at_num || spd.size()==0) {
            MainArea.setGameFieldCardDefence(defenceDeck);
            return true;
        }else {
            pickUpEnemyCards();
            return false;
        }
    }

    public static void setMass(List<Integer> mass) {
        SecondPlayer.mass = mass;
    }
}