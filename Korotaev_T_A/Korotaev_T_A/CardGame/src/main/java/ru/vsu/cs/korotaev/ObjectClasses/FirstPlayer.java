package ru.vsu.cs.korotaev.ObjectClasses;

import ru.vsu.cs.korotaev.Enums.Rank;
import ru.vsu.cs.korotaev.LogicClasses.MainArea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FirstPlayer {
    private List<Card> fpd = new ArrayList<>();

    private List<Integer> mass = new ArrayList<>();

    public void getMass(HashMap<Rank, Integer> massMap){
            mass.clear();
            for (int e = 0; e < fpd.size(); e++) {
                mass.add(0);
            }
            for (int i = 0; i < fpd.size(); i++) {
                for (HashMap.Entry<Rank, Integer> entry : massMap.entrySet()) {
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

    public List<Card> getFpd() {
        return fpd;
    }

    public void setFpd(List<Card> fpd) {
        this.fpd = fpd;
    }

    public List<Integer> getMass() {
        return mass;
    }

    public void setMass(List<Integer> mass) {
        this.mass = mass;
    }
}
