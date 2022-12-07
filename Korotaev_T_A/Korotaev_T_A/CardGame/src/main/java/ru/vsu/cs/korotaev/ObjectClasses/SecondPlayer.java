package ru.vsu.cs.korotaev.ObjectClasses;

import ru.vsu.cs.korotaev.Enums.Rank;
import ru.vsu.cs.korotaev.LogicClasses.HeapSort;
import ru.vsu.cs.korotaev.LogicClasses.MainArea;

import java.util.*;

public class SecondPlayer {
    private List<Card> spd = new ArrayList<>();
    private List<Integer> mass = new ArrayList<>();

    public <Card> List<Card> convertCArrayToList(Object[] array)
    {
        List<Card> list = new ArrayList<>();
        for (Object t : array) {
            Card c = (Card) t;
            list.add(c);
        }
        return list;
    }

    public <Integer> List<Integer> convertMArrayToList(int[] array)
    {
        List<Integer> list = new ArrayList<>();
        for (Object t : array) {
            Integer c = (Integer) t;
            list.add(c);
        }
        return list;
    }

    public void getMass(HashMap<Rank, Integer> massMap) throws Exception {
        mass.clear();
        for(int e = 0; e<spd.size(); e++){
            mass.add(0);
        }
        for(int i = 0; i<spd.size(); i++){
            for (HashMap.Entry<Rank, Integer> entry : massMap.entrySet()) {
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

    public Card[] ifAttack(HashMap<Rank, Integer> massMap) throws Exception {
        List<Card> attackDeck = new ArrayList<>();
        attackDeck.add(spd.get(0));
        Integer curM = mass.get(0);
        spd.remove(0);
        getMass(massMap);
        for(int i = 0; i<spd.size(); i++){
            if(Objects.equals(mass.get(i), curM)) {
                attackDeck.add(spd.get(i));
                spd.remove(i);
                getMass(massMap);
                i=-1;
            }
        }
        Card[] attack = new Card[6];
        for(int i = 0; i<attackDeck.size(); i++){
            attack[i] = attackDeck.get(i);
        }
        return attack;

    }

    public boolean ifTossing(boolean[] isTaped, Card[] gameFieldCardAttack, Card[] gameFieldCardDefence, HashMap<Rank, Integer> massMap) throws Exception {
        boolean isAdd = false;
        for(int i = 0; i<spd.size(); i++){
            for (Card card : gameFieldCardDefence) {
                if (card!=null) {
                    if(spd.get(i).getRank() == card.getRank() && !spd.get(i).isTrump()){
                        for (int k = 0; k< gameFieldCardAttack.length; k++) {
                            if(gameFieldCardAttack[k]==null) {
                                gameFieldCardAttack[k]=spd.get(i);
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
        getMass(massMap);
        return isAdd;
    }

    public boolean ifTransfer(Card[] gameFieldCardAttack, Card[] gameFieldCardDefence, List<Card> fpd){
        List<Card> spd = getSpd();
        boolean isDefPrev = false;
        int notNullAttack = 0;
        for (int i = 0; i < gameFieldCardDefence.length; i++) {
            if (gameFieldCardDefence[i] != null) {
                isDefPrev = true;
            }
            if (gameFieldCardAttack[i] != null) {
                notNullAttack++;
            }
        }
        if (!isDefPrev) {
            for(int e = 0; e< spd.size(); e++) {
                Card check = new Card();
                for (Card card : gameFieldCardAttack) {
                    if (card != null) {
                        check = card;
                    }
                }
                if (check.getRank() == spd.get(e).getRank() && !spd.get(e).isTrump()) {
                    if (notNullAttack + 1 <= fpd.size()) {
                        if (notNullAttack <= 5) {
                            for (int i = 0; i < gameFieldCardAttack.length; i++) {
                                if (gameFieldCardAttack[i] == null) {
                                    gameFieldCardAttack[i] = spd.get(e);
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

    public void pickUpEnemyCards(MainArea mainArea) {
        for(int i = 0; i<6; i++) {
            if (mainArea.getGameFieldCardDefence()[i] != null) {
               spd.add(mainArea.getGameFieldCardDefence()[i]);
            }
            if (mainArea.getGameFieldCardAttack()[i] != null) {
                spd.add(mainArea.getGameFieldCardAttack()[i]);
            }
        }
    }

    public boolean ifDefence(boolean[] isDefSecondTaped, MainArea mainArea){
        Card[] defenceDeck = mainArea.getGameFieldCardDefence();
        for(int e = 0; e<mainArea.getGameFieldCardAttack().length; e++){
            for(int i = 1; i<spd.size(); i++){
                if(!isDefSecondTaped[e] && mainArea.getGameFieldCardAttack()[e]!=null && mainArea.cardComparator(spd.get(i), mainArea.getGameFieldCardAttack()[e])) {
                    defenceDeck[e] = spd.get(i);
                    spd.remove(i);
                    break;
                }
            }
        }
        int defence_num = 0;
        int at_num = 0;
        for(int e = 0; e<mainArea.getGameFieldCardAttack().length; e++){
            if(mainArea.getGameFieldCardAttack()[e]!=null){
                at_num++;
            }
            if(mainArea.getGameFieldCardDefence()[e]!=null){
                defence_num++;
            }
        }
        if(defence_num==at_num || spd.size()==0) {
            mainArea.setGameFieldCardDefence(defenceDeck);
            return true;
        }else {
            pickUpEnemyCards(mainArea);
            return false;
        }
    }

    public List<Integer> getMass() {
        return mass;
    }

    public void setMass(List<Integer> mass) {
        this.mass = mass;
    }

    public List<Card> getSpd() {
        return spd;
    }

    public void setSpd(List<Card> spd) {
        this.spd = spd;
    }
}
