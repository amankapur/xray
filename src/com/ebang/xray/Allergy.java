package com.ebang.xray;

import java.util.ArrayList;

/**
 * Created by amankapur91 on 4/1/14.
 */
public class Allergy {

    public String name;
    public boolean selected;

    public static ArrayList<Allergy> all = new ArrayList<Allergy>();

    public Allergy(String name){
        this.name = name;
        this.selected = false;
        all.add(this);
    }

    public static Allergy find(String name){
        for(Allergy al: all){
            if (al.name.equals(name)){
                return al;
            }
        }
        return null;
    }

}
