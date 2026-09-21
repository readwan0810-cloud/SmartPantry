package com.example.smartpantry;

import java.util.ArrayList;

public class PantryStorage {

    // Stores all pantry items while the app is running
    private static ArrayList<PantryItem> pantryItems = new ArrayList<>();

    // Add a new pantry item
    public static void addItem(PantryItem item) {
        pantryItems.add(item);
    }

    // Get all pantry items
    public static ArrayList<PantryItem> getItems() {
        return pantryItems;
    }
}
