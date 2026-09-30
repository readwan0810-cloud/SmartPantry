package com.example.smartpantry;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.*;
import android.widget.*;
import java.util.*;

public class Available_Recipe extends AppCompatActivity {

    private ListView listAvailableRecipes;
    private Button btnBack;

    private Pantry_DB databaseHelper;

    private ArrayList<Recipes> availableRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.available_recipe);

        listAvailableRecipes =
                findViewById(R.id.listSuggestedRecipes);

        btnBack =
                findViewById(R.id.btnBackHome);

        databaseHelper = new Pantry_DB(this);


        btnBack.setOnClickListener(view -> {
            finish();
        });

        listAvailableRecipes.setOnItemClickListener(
                (parent, view, position, id) -> {

                    Recipes selectedRecipe =
                            availableRecipes.get(position);

                    Intent intent = new Intent(
                            Available_Recipe.this,
                            Detailed_Recipe.class
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

        ArrayList<Recipes> allRecipes =
                databaseHelper.getAllRecipes();

        ArrayList<Pantry_Foods> pantryItems =
                databaseHelper.getAllPantryItems();

        availableRecipes = new ArrayList<>();

        for (Recipes recipe : allRecipes) {

            ArrayList<Recipe_Ingredients> ingredients =
                    databaseHelper.getIngredientsForRecipe(
                            recipe.getId()
                    );

            boolean canMakeRecipe = true;

            for (Recipe_Ingredients ingredient : ingredients) {

                if (!hasEnoughIngredient(
                        ingredient,
                        pantryItems)) {

                    canMakeRecipe = false;
                    break;
                }
            }

            if (canMakeRecipe) {
                availableRecipes.add(recipe);
            }
        }

        if (availableRecipes.isEmpty()) {

            Toast.makeText(
                    this,
                    "No recipes match your pantry yet - add more ingredients.",
                    Toast.LENGTH_LONG
            ).show();

            return;
        }

        Adapter_Recipe adapter =
                new Adapter_Recipe(
                        this,
                        availableRecipes
                );

        listAvailableRecipes.setAdapter(adapter);
    }

    private boolean hasEnoughIngredient(
            Recipe_Ingredients requiredIngredient,
            ArrayList<Pantry_Foods> pantryItems) {

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

        for (Pantry_Foods pantryItem : pantryItems) {

            String pantryName =
                    normalizeIngredientName(
                            pantryItem.getItemName()
                    );

            String pantryUnit =
                    normalizeUnit(
                            pantryItem.getUnit()
                    );

            System.out.println(
                    "RECIPE CHECK: " +
                            "Required = " + requiredName +
                            " " + requiredQuantity + " " + requiredUnit +
                            " | Pantry = " + pantryName +
                            " " + pantryItem.getQuantity() + " " + pantryUnit
            );

            if (!requiredName.equals(pantryName)) {
                continue;
            }

            double convertedQuantity =
                    convertQuantity(
                            pantryItem.getQuantity(),
                            pantryUnit,
                            requiredUnit
                    );

            System.out.println(
                    "MATCH: " +
                            requiredName +
                            " | Converted quantity = " +
                            convertedQuantity
            );

            if (convertedQuantity >= 0) {
                availableQuantity += convertedQuantity;
            }
        }

        System.out.println(
                "RESULT: " +
                        requiredName +
                        " | Required = " +
                        requiredQuantity +
                        " | Available = " +
                        availableQuantity
        );

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
