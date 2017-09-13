package myIOC.test;

import myIOC.container.AutoWired;

/**
 * Created by Dell on 2017-08-15.
 */
public class Hero {

    // 通过String类型的bean注入
//    @AutoWired(value = String.class)
    private String name;
    // 通过name为String的bean注入
//    @AutoWired(name="String")
    private String outfit;

    public Hero() {
    }
    @AutoWired
    public Hero(String name, String outfit) {
        this.name = "construct";
        this.outfit = "construct";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOutfit() {
        return outfit;
    }

    public void setOutfit(String outfit) {
        this.outfit = outfit;
    }

    public void say(){
        System.out.println(name + "购买了" + outfit);
    }

    @Override
    public String toString() {
        return "Hero{" +
                "name='" + name + '\'' +
                ", outfit='" + outfit + '\'' +
                '}';
    }
}