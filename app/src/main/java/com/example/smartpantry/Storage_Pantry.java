package com.example.smartpantry;
import java.util.*;
public class Storage_Pantry {

    // Stores all food items in pantry
    private static ArrayList<Pantry_Foods> pantryItems = new ArrayList<>();

    // Adds new food items
    public static void addItem(Pantry_Foods item) {
        pantryItems.add(item);
    }

    // Gets all food items
    public static ArrayList<Pantry_Foods> getItems() {
        return pantryItems;
    }
}
