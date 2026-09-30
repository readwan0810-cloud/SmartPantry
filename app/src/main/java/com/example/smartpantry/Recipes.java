package com.example.smartpantry;

public class Recipes {

    private int id;
    private String recipeName;
    private String instructions;

    public Recipes(
            int id,
            String recipeName,
            String instructions) {

        this.id = id;
        this.recipeName = recipeName;
        this.instructions = instructions;
    }

    public int getId() {
        return id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getInstructions() {
        return instructions;
    }
}
