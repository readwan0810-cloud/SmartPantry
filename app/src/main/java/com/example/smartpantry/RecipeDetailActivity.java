package com.example.smartpantry;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView txtRecipeName;
    private TextView txtRecipeIngredients;
    private TextView txtRecipeInstructions;
    private Button btnBackRecipes;

    private PantryDBHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail);

        txtRecipeName = findViewById(R.id.txtRecipeName);
        txtRecipeIngredients = findViewById(R.id.txtRecipeIngredients);
        txtRecipeInstructions = findViewById(R.id.txtRecipeInstructions);
        btnBackRecipes = findViewById(R.id.btnBackRecipes);

        databaseHelper = new PantryDBHelper(this);

        btnBackRecipes.setOnClickListener(view -> {
            finish();
        });

        int recipeId = getIntent().getIntExtra("RECIPE_ID", -1);

        if (recipeId != -1) {
            loadRecipeDetails(recipeId);
        }
    }

    private void loadRecipeDetails(int recipeId) {

        ArrayList<Recipe> recipes =
                databaseHelper.getAllRecipes();

        Recipe selectedRecipe = null;

        for (Recipe recipe : recipes) {

            if (recipe.getId() == recipeId) {
                selectedRecipe = recipe;
                break;
            }
        }

        if (selectedRecipe == null) {
            txtRecipeName.setText("Recipe not found");
            return;
        }

        txtRecipeName.setText(
                selectedRecipe.getRecipeName()
        );

        ArrayList<RecipeIngredient> ingredients =
                databaseHelper.getIngredientsForRecipe(recipeId);

        StringBuilder ingredientText =
                new StringBuilder();

        for (RecipeIngredient ingredient : ingredients) {

            ingredientText
                    .append("• ")
                    .append(ingredient.getIngredientName())
                    .append(" - ")
                    .append(ingredient.getRequiredQuantity())
                    .append(" ")
                    .append(ingredient.getUnit())
                    .append("\n");
        }

        txtRecipeIngredients.setText(
                ingredientText.toString()
        );

        txtRecipeInstructions.setText(
                selectedRecipe.getInstructions()
        );
    }
}
