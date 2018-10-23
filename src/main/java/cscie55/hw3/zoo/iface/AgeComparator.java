package cscie55.hw3.zoo.iface;

import java.util.Comparator;

import cscie55.hw3.zoo.animals.Animal;


public class AgeComparator implements Comparator {

    @Override
    public int compare(Object a1, Object a2) {
        return ((Integer)a1 - (Integer)a2);
        // ascending order for reverse, swap order of terms
    }

}
