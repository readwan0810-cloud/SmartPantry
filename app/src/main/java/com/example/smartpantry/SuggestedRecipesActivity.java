package com.example.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private ListView listSuggestedRecipes;
    private Button btnBackHome;

    private PantryDBHelper databaseHelper;

    private ArrayList<Recipe> suggestedRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_suggested_recipes);

        listSuggestedRecipes =
                findViewById(R.id.listSuggestedRecipes);

        btnBackHome =
                findViewById(R.id.btnBackHome);

        databaseHelper = new PantryDBHelper(this);


        btnBackHome.setOnClickListener(view -> {
            finish();
        });

        listSuggestedRecipes.setOnItemClickListener(
                (parent, view, position, id) -> {

                    Recipe selectedRecipe =
                            suggestedRecipes.get(position);

                    Intent intent = new Intent(
                            SuggestedRecipesActivity.this,
                            RecipeDetailActivity.class
                    );

                    intent.putExtra(
                            "RECIPE_ID",
                            selectedRecipe.getId()
                    );

                    startActivity(intent);
                }
        );

        loadSuggestedRecipes();
    }

    private void loadSuggestedRecipes() {

        ArrayList<Recipe> allRecipes =
                databaseHelper.getAllRecipes();

        ArrayList<PantryItem> pantryItems =
                databaseHelper.getAllPantryItems();

        suggestedRecipes = new ArrayList<>();

        for (Recipe recipe : allRecipes) {

            ArrayList<RecipeIngredient> ingredients =
                    databaseHelper.getIngredientsForRecipe(
                            recipe.getId()
                    );

            boolean canMakeRecipe = true;

            for (RecipeIngredient ingredient : ingredients) {

                if (!hasEnoughIngredient(
                        ingredient,
                        pantryItems)) {

                    canMakeRecipe = false;
                    break;
                }
            }

            if (canMakeRecipe) {
                suggestedRecipes.add(recipe);
            }
        }

        if (suggestedRecipes.isEmpty()) {

            Toast.makeText(
                    this,
                    "No recipes match your pantry yet - add more ingredients.",
                    Toast.LENGTH_LONG
            ).show();

            return;
        }

        RecipeAdapter adapter =
                new RecipeAdapter(
                        this,
                        suggestedRecipes
                );

        listSuggestedRecipes.setAdapter(adapter);
    }

    private boolean hasEnoughIngredient(
            RecipeIngredient requiredIngredient,
            ArrayList<PantryItem> pantryItems) {

        String requiredName =
                normalizeIngredientName(
                        requiredIngredient.getIngredientName()
                );

        double requiredQuantity =
                requiredIngredient.getRequiredQuantity();

        String requiredUnit =
                normalizeUnit(
                        requiredIngredient.getUnit()
                );

        double availableQuantity = 0;

        for (PantryItem pantryItem : pantryItems) {

            String pantryName =
                    normalizeIngredientName(
                            pantryItem.getItemName()
                    );

            if (!requiredName.equals(pantryName)) {
                continue;
            }

            String pantryUnit =
                    normalizeUnit(
                            pantryItem.getUnit()
                    );

            double convertedQuantity =
                    convertQuantity(
                            pantryItem.getQuantity(),
                            pantryUnit,
                            requiredUnit
                    );

            if (convertedQuantity >= 0) {
                availableQuantity += convertedQuantity;
            }
        }

        return availableQuantity >= requiredQuantity;
    }

    private String normalizeIngredientName(String name) {

        String result =
                name.trim().toLowerCase();

        if (result.endsWith("ies")) {
            result = result.substring(
                    0,
                    result.length() - 3
            ) + "y";

        } else if (result.endsWith("s")
                && !result.endsWith("ss")) {

            result = result.substring(
                    0,
                    result.length() - 1
            );
        }

        return result;
    }

    private String normalizeUnit(String unit) {

        String result =
                unit.trim().toLowerCase();

        if (result.equals("kilograms")
                || result.equals("kilogram")) {

            return "kg";
        }

        if (result.equals("grams")
                || result.equals("gram")) {

            return "g";
        }

        if (result.equals("litres")
                || result.equals("liters")
                || result.equals("liter")
                || result.equals("litre")) {

            return "l";
        }

        if (result.equals("millilitres")
                || result.equals("milliliters")
                || result.equals("milliliter")
                || result.equals("millilitre")) {

            return "ml";
        }

        if (result.equals("items")
                || result.equals("item")) {

            return "item";
        }

        return result;
    }

    private double convertQuantity(
            double quantity,
            String fromUnit,
            String toUnit) {

        if (fromUnit.equals(toUnit)) {
            return quantity;
        }

        if (fromUnit.equals("kg")
                && toUnit.equals("g")) {

            return quantity * 1000;
        }

        if (fromUnit.equals("g")
                && toUnit.equals("kg")) {

            return quantity / 1000;
        }

        if (fromUnit.equals("l")
                && toUnit.equals("ml")) {

            return quantity * 1000;
        }

        if (fromUnit.equals("ml")
                && toUnit.equals("l")) {

            return quantity / 1000;
        }

        return -1;
    }
}
