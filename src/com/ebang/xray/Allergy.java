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


    public static boolean anySelected(){
        for (Allergy al : all){
            if (al.selected){
                return  true;
            }
        }
        return  false;
    }

    public static ArrayList<Allergy> selected(){
        ArrayList<Allergy> t = new ArrayList<Allergy>();
        for (Allergy al: all){
            if (al.selected){
                t.add(al);
            }
        }
        return t;
    }
}
