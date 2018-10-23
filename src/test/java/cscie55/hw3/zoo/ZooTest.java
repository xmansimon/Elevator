package cscie55.hw3.zoo;
import cscie55.hw3.zoo.animals.*;
import cscie55.hw3.zoo.iface.*;
import cscie55.hw3.zoo.iface.AgeComparator;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static cscie55.hw3.zoo.iface.NumUtil.getRandomBetween;

import static org.junit.Assert.*;

import cscie55.hw3.zoo.iface.Runnable;
import cscie55.hw3.zoo.iface.Walkable;
import cscie55.hw3.zoo.iface.Runnable;

import org.junit.Test;

public class ZooTest{
    @Test
    public void test(){
        Cat cat1 = new Cat(2);
        System.out.println(cat1.getName());
        System.out.println(cat1.getAge());
        System.out.println(cat1.getFavoriteFoods());

        cat1.walk();
        cat1.run(10);



    }




}



