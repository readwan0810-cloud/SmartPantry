package com.example.smartpantry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class PantryDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 3;

    // Pantry table
    private static final String TABLE_PANTRY = "pantry_items";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "item_name";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_UNIT = "unit";
    private static final String COLUMN_EXPIRY_DATE = "expiry_date";

    // Recipe tables
    private static final String TABLE_RECIPES = "recipes";
    private static final String TABLE_RECIPE_INGREDIENTS = "recipe_ingredients";

    private static final String COLUMN_RECIPE_ID = "recipe_id";
    private static final String COLUMN_RECIPE_NAME = "recipe_name";
    private static final String COLUMN_INSTRUCTIONS = "instructions";

    private static final String COLUMN_INGREDIENT_ID = "id";
    private static final String COLUMN_INGREDIENT_NAME = "ingredient_name";
    private static final String COLUMN_REQUIRED_QUANTITY = "required_quantity";
    private static final String COLUMN_INGREDIENT_UNIT = "unit";

    public PantryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create pantry table
        String createPantryTable = "CREATE TABLE " + TABLE_PANTRY + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_QUANTITY + " REAL NOT NULL, " +
                COLUMN_UNIT + " TEXT NOT NULL, " +
                COLUMN_EXPIRY_DATE + " TEXT" +
                ")";

        db.execSQL(createPantryTable);

        // Create recipes table
        String createRecipesTable = "CREATE TABLE " + TABLE_RECIPES + " (" +
                COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RECIPE_NAME + " TEXT NOT NULL, " +
                COLUMN_INSTRUCTIONS + " TEXT NOT NULL" +
                ")";

        db.execSQL(createRecipesTable);

        // Create recipe ingredients table
        String createRecipeIngredientsTable =
                "CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + " (" +
                        COLUMN_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_RECIPE_ID + " INTEGER NOT NULL, " +
                        COLUMN_INGREDIENT_NAME + " TEXT NOT NULL, " +
                        COLUMN_REQUIRED_QUANTITY + " REAL NOT NULL, " +
                        COLUMN_INGREDIENT_UNIT + " TEXT NOT NULL" +
                        ")";

        db.execSQL(createRecipeIngredientsTable);

        // Add starter recipes
        seedRecipes(db);
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion) {

        // Upgrade from version 1 to version 2.
        // Existing pantry data is preserved.
        if (oldVersion < 2) {

            String createRecipesTable =
                    "CREATE TABLE IF NOT EXISTS " + TABLE_RECIPES + " (" +
                            COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            COLUMN_RECIPE_NAME + " TEXT NOT NULL, " +
                            COLUMN_INSTRUCTIONS + " TEXT NOT NULL" +
                            ")";

            db.execSQL(createRecipesTable);

            String createRecipeIngredientsTable =
                    "CREATE TABLE IF NOT EXISTS " +
                            TABLE_RECIPE_INGREDIENTS + " (" +
                            COLUMN_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            COLUMN_RECIPE_ID + " INTEGER NOT NULL, " +
                            COLUMN_INGREDIENT_NAME + " TEXT NOT NULL, " +
                            COLUMN_REQUIRED_QUANTITY + " REAL NOT NULL, " +
                            COLUMN_INGREDIENT_UNIT + " TEXT NOT NULL" +
                            ")";

            db.execSQL(createRecipeIngredientsTable);
        }

        // Upgrade from version 2 to version 3.
        // Replace the temporary test recipes with the
        // complete recipe collection.
        if (oldVersion < 3) {
            seedRecipes(db);
        }
    }

    private void seedRecipes(SQLiteDatabase db) {

        // Remove existing recipe seed data before loading
        // the complete recipe collection.
        db.delete(TABLE_RECIPE_INGREDIENTS, null, null);
        db.delete(TABLE_RECIPES, null, null);

        // Recipe 1
        long chickenFriedRiceId = addRecipe(
                db,
                "Chicken Fried Rice",
                "Cook the rice and chicken. Stir-fry the ingredients together and serve."
        );

        addRecipeIngredient(db, chickenFriedRiceId, "rice", 1.0, "kg");
        addRecipeIngredient(db, chickenFriedRiceId, "chicken", 0.5, "kg");
        addRecipeIngredient(db, chickenFriedRiceId, "onion", 1.0, "item");

        // Recipe 2
        long tomatoRiceId = addRecipe(
                db,
                "Tomato Rice",
                "Cook the rice with tomatoes and onion until the ingredients are soft and combined."
        );

        addRecipeIngredient(db, tomatoRiceId, "rice", 1.0, "kg");
        addRecipeIngredient(db, tomatoRiceId, "tomato", 2.0, "item");
        addRecipeIngredient(db, tomatoRiceId, "onion", 1.0, "item");

        // Recipe 3
        long chickenOnionId = addRecipe(
                db,
                "Chicken and Onion",
                "Cook the chicken thoroughly and add chopped onion. Cook until the onion is soft."
        );

        addRecipeIngredient(db, chickenOnionId, "chicken", 0.5, "kg");
        addRecipeIngredient(db, chickenOnionId, "onion", 1.0, "item");

        // Recipe 4
        long potatoEggId = addRecipe(
                db,
                "Potato and Egg",
                "Cook the potatoes until tender, prepare the eggs, and serve together."
        );

        addRecipeIngredient(db, potatoEggId, "potato", 2.0, "item");
        addRecipeIngredient(db, potatoEggId, "egg", 2.0, "item");

        // Recipe 5
        long vegetableRiceId = addRecipe(
                db,
                "Vegetable Rice",
                "Cook the rice and vegetables together until everything is tender."
        );

        addRecipeIngredient(db, vegetableRiceId, "rice", 1.0, "kg");
        addRecipeIngredient(db, vegetableRiceId, "carrot", 1.0, "item");
        addRecipeIngredient(db, vegetableRiceId, "onion", 1.0, "item");

        // Recipe 6
        long chickenRiceId = addRecipe(
                db,
                "Simple Chicken Rice",
                "Cook the chicken and rice separately, then combine and serve."
        );

        addRecipeIngredient(db, chickenRiceId, "chicken", 0.5, "kg");
        addRecipeIngredient(db, chickenRiceId, "rice", 1.0, "kg");

        // Recipe 7
        long eggRiceId = addRecipe(
                db,
                "Egg Fried Rice",
                "Cook the rice, scramble the eggs, and stir-fry them together."
        );

        addRecipeIngredient(db, eggRiceId, "rice", 1.0, "kg");
        addRecipeIngredient(db, eggRiceId, "egg", 2.0, "item");
        addRecipeIngredient(db, eggRiceId, "onion", 1.0, "item");

        // Recipe 8
        long potatoChickenId = addRecipe(
                db,
                "Chicken and Potato",
                "Cook the chicken and potatoes thoroughly and combine before serving."
        );

        addRecipeIngredient(db, potatoChickenId, "chicken", 0.5, "kg");
        addRecipeIngredient(db, potatoChickenId, "potato", 2.0, "item");

        // Recipe 9
        long vegetableChickenId = addRecipe(
                db,
                "Chicken Vegetable Stir Fry",
                "Cook the chicken and vegetables in a pan until thoroughly cooked."
        );

        addRecipeIngredient(db, vegetableChickenId, "chicken", 0.5, "kg");
        addRecipeIngredient(db, vegetableChickenId, "carrot", 1.0, "item");
        addRecipeIngredient(db, vegetableChickenId, "onion", 1.0, "item");

        // Recipe 10
        long potatoRiceId = addRecipe(
                db,
                "Potato Rice",
                "Cook the potatoes and rice until tender, then combine and serve."
        );

        addRecipeIngredient(db, potatoRiceId, "potato", 2.0, "item");
        addRecipeIngredient(db, potatoRiceId, "rice", 1.0, "kg");

        // Recipe 11
        long tomatoEggId = addRecipe(
                db,
                "Tomato and Egg",
                "Cook the tomatoes and add the eggs. Stir gently until the eggs are cooked."
        );

        addRecipeIngredient(db, tomatoEggId, "tomato", 2.0, "item");
        addRecipeIngredient(db, tomatoEggId, "egg", 2.0, "item");

        // Recipe 12
        long vegetableEggId = addRecipe(
                db,
                "Vegetable Egg Scramble",
                "Cook the vegetables and add scrambled eggs. Cook until the eggs are ready."
        );

        addRecipeIngredient(db, vegetableEggId, "egg", 2.0, "item");
        addRecipeIngredient(db, vegetableEggId, "carrot", 1.0, "item");
        addRecipeIngredient(db, vegetableEggId, "onion", 1.0, "item");

        // Recipe 13
        long tomatoChickenId = addRecipe(
                db,
                "Tomato Chicken",
                "Cook the chicken thoroughly and add tomatoes and onion."
        );

        addRecipeIngredient(db, tomatoChickenId, "chicken", 0.5, "kg");
        addRecipeIngredient(db, tomatoChickenId, "tomato", 2.0, "item");
        addRecipeIngredient(db, tomatoChickenId, "onion", 1.0, "item");

        // Recipe 14
        long potatoVegetableId = addRecipe(
                db,
                "Potato Vegetable Mix",
                "Cook the potatoes and vegetables until tender and serve together."
        );

        addRecipeIngredient(db, potatoVegetableId, "potato", 2.0, "item");
        addRecipeIngredient(db, potatoVegetableId, "carrot", 1.0, "item");
        addRecipeIngredient(db, potatoVegetableId, "onion", 1.0, "item");

        // Recipe 15
        long chickenEggId = addRecipe(
                db,
                "Chicken and Egg",
                "Cook the chicken thoroughly, prepare the eggs, and serve together."
        );

        addRecipeIngredient(db, chickenEggId, "chicken", 0.5, "kg");
        addRecipeIngredient(db, chickenEggId, "egg", 2.0, "item");
    }

    private long addRecipe(
            SQLiteDatabase db,
            String recipeName,
            String instructions) {

        ContentValues values = new ContentValues();

        values.put(COLUMN_RECIPE_NAME, recipeName);
        values.put(COLUMN_INSTRUCTIONS, instructions);

        return db.insert(TABLE_RECIPES, null, values);
    }

    private long addRecipeIngredient(
            SQLiteDatabase db,
            long recipeId,
            String ingredientName,
            double requiredQuantity,
            String unit) {

        ContentValues values = new ContentValues();

        values.put(COLUMN_RECIPE_ID, recipeId);
        values.put(COLUMN_INGREDIENT_NAME, ingredientName);
        values.put(COLUMN_REQUIRED_QUANTITY, requiredQuantity);
        values.put(COLUMN_INGREDIENT_UNIT, unit);

        return db.insert(
                TABLE_RECIPE_INGREDIENTS,
                null,
                values
        );
    }

    // -----------------------------
    // PANTRY CRUD METHODS
    // -----------------------------

    public long addPantryItem(
            String name,
            String category,
            double quantity,
            String unit,
            String expiryDate) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, name);
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_UNIT, unit);

        if (expiryDate == null || expiryDate.isEmpty()) {
            values.putNull(COLUMN_EXPIRY_DATE);
        } else {
            values.put(COLUMN_EXPIRY_DATE, expiryDate);
        }

        return db.insert(TABLE_PANTRY, null, values);
    }

    public ArrayList<PantryItem> getAllPantryItems() {

        ArrayList<PantryItem> pantryItems = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_PANTRY,
                null,
                null,
                null,
                null,
                null,
                COLUMN_NAME + " ASC"
        );

        while (cursor.moveToNext()) {

            int id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(COLUMN_ID));

            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_NAME));

            String category = cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_CATEGORY));

            double quantity = cursor.getDouble(
                    cursor.getColumnIndexOrThrow(COLUMN_QUANTITY));

            String unit = cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_UNIT));

            String expiryDate = cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_EXPIRY_DATE));

            PantryItem item = new PantryItem(
                    id,
                    name,
                    category,
                    quantity,
                    unit,
                    expiryDate
            );

            pantryItems.add(item);
        }

        cursor.close();

        return pantryItems;
    }

    public int updatePantryItem(
            int id,
            String name,
            String category,
            double quantity,
            String unit,
            String expiryDate) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, name);
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_UNIT, unit);

        if (expiryDate == null || expiryDate.isEmpty()) {
            values.putNull(COLUMN_EXPIRY_DATE);
        } else {
            values.put(COLUMN_EXPIRY_DATE, expiryDate);
        }

        return db.update(
                TABLE_PANTRY,
                values,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
    }

    public PantryItem getPantryItemById(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_PANTRY,
                null,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        PantryItem item = null;

        if (cursor.moveToFirst()) {

            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_NAME));

            String category = cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_CATEGORY));

            double quantity = cursor.getDouble(
                    cursor.getColumnIndexOrThrow(COLUMN_QUANTITY));

            String unit = cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_UNIT));

            String expiryDate = cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_EXPIRY_DATE));

            item = new PantryItem(
                    id,
                    name,
                    category,
                    quantity,
                    unit,
                    expiryDate
            );
        }

        cursor.close();

        return item;
    }

    public int deletePantryItem(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(
                TABLE_PANTRY,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
    }

    // -----------------------------
    // RECIPE METHODS
    // -----------------------------

    public ArrayList<Recipe> getAllRecipes() {

        ArrayList<Recipe> recipes = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_RECIPES,
                null,
                null,
                null,
                null,
                null,
                COLUMN_RECIPE_NAME + " ASC"
        );

        while (cursor.moveToNext()) {

            int id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(COLUMN_RECIPE_ID));

            String recipeName = cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_RECIPE_NAME));

            String instructions = cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_INSTRUCTIONS));

            Recipe recipe = new Recipe(
                    id,
                    recipeName,
                    instructions
            );

            recipes.add(recipe);
        }

        cursor.close();

        return recipes;
    }

    public ArrayList<RecipeIngredient> getIngredientsForRecipe(
            int recipeId) {

        ArrayList<RecipeIngredient> ingredients =
                new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_RECIPE_INGREDIENTS,
                null,
                COLUMN_RECIPE_ID + " = ?",
                new String[]{String.valueOf(recipeId)},
                null,
                null,
                COLUMN_INGREDIENT_NAME + " ASC"
        );

        while (cursor.moveToNext()) {

            int id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(
                            COLUMN_INGREDIENT_ID));

            String ingredientName = cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            COLUMN_INGREDIENT_NAME));

            double requiredQuantity = cursor.getDouble(
                    cursor.getColumnIndexOrThrow(
                            COLUMN_REQUIRED_QUANTITY));

            String unit = cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            COLUMN_INGREDIENT_UNIT));

            RecipeIngredient ingredient =
                    new RecipeIngredient(
                            id,
                            recipeId,
                            ingredientName,
                            requiredQuantity,
                            unit
                    );

            ingredients.add(ingredient);
        }

        cursor.close();

        return ingredients;
    }
    public int getRecipeCount() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_RECIPES,
                null
        );

        int count = 0;

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();

        return count;
    }
}
