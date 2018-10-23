package cscie55.hw3.zoo.animals;


import cscie55.hw3.zoo.iface.Walkable;

import java.util.ArrayList;
import java.util.List;

// by implementing Comparable with compareTo() below,
//  I ensure that collections of Animals return sorted base on (alphabetical) name property order
public abstract class Animal implements Walkable {

    private String name = "";
    private int age;
    private ArrayList<String> favoriteFoods = new ArrayList<>();

    /************* constructors   *************/
    public Animal(){
        this(5,"Annonymous");
    }

    public Animal(int age, String name){
        this(age, name, new String[] {"kibble"});
    }

    public Animal(int age, String name, String[] favoriteFoods){
        this.age = age;
        this.name = name;
        for(String food : favoriteFoods) {
            this.favoriteFoods.add(food);
        }
    }


    @Override
    public void walk() {
        System.out.println("The animal can walk.");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public ArrayList<String> getFavoriteFoods() {
        return favoriteFoods;
    }



}
