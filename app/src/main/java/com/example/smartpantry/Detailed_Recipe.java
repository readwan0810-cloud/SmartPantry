package com.example.smartpantry;
import androidx.appcompat.app.AppCompatActivity;
import android.os.*;
import android.widget.*;
import java.util.*;

public class Detailed_Recipe extends AppCompatActivity {

    private TextView txtFoodName;
    private TextView txtFoodIngredients;
    private TextView txtFoodInstructions;
    private Button btnBack;

    private Pantry_DB databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detailed_recipe);

        txtFoodName = findViewById(R.id.txtRecipeName);
        txtFoodIngredients = findViewById(R.id.txtRecipeIngredients);
        txtFoodInstructions = findViewById(R.id.txtRecipeInstructions);
        btnBack = findViewById(R.id.btnBackRecipes);

        databaseHelper = new Pantry_DB(this);

        btnBack.setOnClickListener(view -> {
            finish();
        });

        int recipeId = getIntent().getIntExtra("RECIPE_ID", -1);

        if (recipeId != -1) {
            loadRecipeDetails(recipeId);
        }
    }

    private void loadRecipeDetails(int recipeId) {

        ArrayList<Recipes> recipes =
                databaseHelper.getAllRecipes();

        Recipes selectedRecipe = null;

        for (Recipes recipe : recipes) {

            if (recipe.getId() == recipeId) {
                selectedRecipe = recipe;
                break;
            }
        }

        if (selectedRecipe == null) {
            txtFoodName.setText("This Recipe was not found");
            return;
        }

        txtFoodName.setText(
                selectedRecipe.getRecipeName()
        );

        ArrayList<Recipe_Ingredients> ingredients =
                databaseHelper.getIngredientsForRecipe(recipeId);

        StringBuilder ingredientText =
                new StringBuilder();

        for (Recipe_Ingredients ingredient : ingredients) {

            ingredientText
                    .append("• ")
                    .append(ingredient.getIngredientName())
                    .append(" - ")
                    .append(ingredient.getRequiredQuantity())
                    .append(" ")
                    .append(ingredient.getUnit())
                    .append("\n");
        }

        txtFoodIngredients.setText(
                ingredientText.toString()
        );

        txtFoodInstructions.setText(
                selectedRecipe.getInstructions()
        );
    }
}
