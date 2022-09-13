package ru.sokolov.RangeMap;

public class RangeMap<K extends Comparable<K>, V >  {



    class Range {
        K upperValue;
        K loverValue;

        public Range(K upperValue, K loverValue) {
            this.upperValue = upperValue;
            this.loverValue = loverValue;
        }

        public boolean isValueInRange(K value) {
            return  value.compareTo(loverValue) >= 0 && value.compareTo(upperValue) <= 0;
        }
    }

}
