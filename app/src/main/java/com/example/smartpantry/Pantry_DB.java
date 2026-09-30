package com.example.smartpantry;
import android.content.ContentValues;
import android.content.Context;
import android.database.*;
import android.database.sqlite.*;
import java.util.*;

public class Pantry_DB extends SQLiteOpenHelper {

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
    private static final String COLUMN_INGREDIENT_NAME = "food_name";
    private static final String COLUMN_REQUIRED_QUANTITY = "required_quantity";
    private static final String COLUMN_INGREDIENT_UNIT = "unit";

    public Pantry_DB(Context context) {
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

        // Creating a recipes table
        String createRecipesTable = "CREATE TABLE " + TABLE_RECIPES + " (" +
                COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RECIPE_NAME + " TEXT NOT NULL, " +
                COLUMN_INSTRUCTIONS + " TEXT NOT NULL" +
                ")";

        db.execSQL(createRecipesTable);

        // Creating a recipe ingredients table
        String createRecipeIngredientsTable =
                "CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + " (" +
                        COLUMN_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_RECIPE_ID + " INTEGER NOT NULL, " +
                        COLUMN_INGREDIENT_NAME + " TEXT NOT NULL, " +
                        COLUMN_REQUIRED_QUANTITY + " REAL NOT NULL, " +
                        COLUMN_INGREDIENT_UNIT + " TEXT NOT NULL" +
                        ")";

        db.execSQL(createRecipeIngredientsTable);

        // Add the starting recipes
        seedRecipes(db);
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion) {

        // Updates from version 1 to version 2.

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

        // Updates from version 2 to version 3.

        if (oldVersion < 3) {
            seedRecipes(db);
        }
    }

    private void seedRecipes(SQLiteDatabase db) {


        // Completes food recipe collection.
        db.delete(TABLE_RECIPE_INGREDIENTS, null, null);
        db.delete(TABLE_RECIPES, null, null);

        // 1st Recipe

        long breadJamId = addRecipe(
                db,
                "Bread and Jam",
                "Take you 2 slices of bread, spread jam all over the two slices, cut in triangular slices and enjoy"
        );
        addRecipeIngredient(db,breadJamId, "bread",2.0,"item");
        addRecipeIngredient(db, breadJamId,"jam",1.0,"item");



        // 2nd Recipe
        long cheeseBreadId = addRecipe(
                db,
                "Bread and Cheese",
                "Take you 2 slices of bread, grate your cheese and sprinkle it on the bread, toast it or enjoy as is"
        );
        addRecipeIngredient(db,cheeseBreadId, "bread",2.0,"item");
        addRecipeIngredient(db,cheeseBreadId,"cheese",1.0,"item");


        // 3rd Recipe
        long peanutbutterBreadId = addRecipe(
                db,
                "Bread and Peanut Butter",
                "Cook the rice and vegetables together until everything is tender."
        );

        addRecipeIngredient(db, peanutbutterBreadId, "bread", 2.0, "item");
        addRecipeIngredient(db, peanutbutterBreadId, "peanut butter", 1.0, "item");


        // 4th Recipe
        long frenchToastId = addRecipe(
                db,
                "French Toast",
                "Scramble 2 eggs in a bowl, dip the bread into the eggs, fry on a hot pan and enjoy"
        );

        addRecipeIngredient(db, frenchToastId, "bread", 2.0, "item");
        addRecipeIngredient(db, frenchToastId, "eggs", 2.0, "item");

        // 5th Recipe
        long chickenSandwichId = addRecipe(
                db,
                "Chicken Sandwich",
                "Cutt chicken into small strips, add mayo, mix together and spread on bread, enjoy."
        );

        addRecipeIngredient(db,chickenSandwichId, "bread", 2.0, "item");
        addRecipeIngredient(db,chickenSandwichId, "chicken", 2.0, "g");


        // 6th Recipe
        long noodlesId = addRecipe(
                db,
                "Simple Noodles",
                "Boil all ingredients in noodles pre-pack for 5 minutes, add fried onions"
        );

        addRecipeIngredient(db, noodlesId, "noodles", 1.0, "item");
        addRecipeIngredient(db, noodlesId, "onion", 1.0, "item");

        // 7th Recipe
        long eggNoodlesId = addRecipe(
                db,
                "Egg noodles",
                "Fry 1 egg scrambled, boil noodles with pre-packed ingredients, mix together and enjoy."
        );

        addRecipeIngredient(db, eggNoodlesId, "noodles", 1.0, "item");
        addRecipeIngredient(db, eggNoodlesId, "egg", 1.0, "item");


        // 8th Recipe
        long viennaNoodlesId = addRecipe(
                db,
                "Noodles and Viennas",
                "Boil the pre-packed noodles, boil the viennas, slice the viennas, add to noodles and enjoy"
        );

        addRecipeIngredient(db, viennaNoodlesId, "noodles", 1.0, "item");
        addRecipeIngredient(db, viennaNoodlesId, "viennas", 2.0, "g");

        // 9th Recipe
        long vegNoodlesId = addRecipe(
                db,
                "Noodles and Vegetables",
                "Boil the pre-packed noodles, with 1 sliced carrot and one sliced onion, mix together and enjoy."
        );

        addRecipeIngredient(db, vegNoodlesId, "noodles", 1.0, "item");
        addRecipeIngredient(db, vegNoodlesId, "carrot", 1.0, "item");
        addRecipeIngredient(db, vegNoodlesId, "onion", 1.0, "item");

        // 10th Recipe
        long chickenNoodleId = addRecipe(
                db,
                "Noodles and Chicken",
                "Boil pre-packed noodles, fry 1 piece of chicken in oil and spices, and enjoy."
        );

        addRecipeIngredient(db, chickenNoodleId, "noodles", 1.0, "item");
        addRecipeIngredient(db, chickenNoodleId, "chicken", 1.0, "kg");


        // 11th Recipe
        long crackersCheeseId = addRecipe(
                db,
                "Crackers and Cheese",
                "Cut or grate cheese, sprinkle on cracker and enjoy"
        );

        addRecipeIngredient(db, crackersCheeseId, "crackers", 4.0, "item");
        addRecipeIngredient(db, crackersCheeseId, "cheese", 1.0, "item");


        // 12th Recipe
        long tunaCrackersId = addRecipe(
                db,
                "Crackers and Tuna",
                "Spread tuna on crackers and enjoy"
        );

        addRecipeIngredient(db, tunaCrackersId, "crackers", 4.0, "item");
        addRecipeIngredient(db, tunaCrackersId, "tuna", 1.0, "item");


        // 13th Recipe
        long peanutbutterCrackerId = addRecipe(
                db,
                "Crackers and Peanut Butter",
                "Spread peanut butter on cracker and enjoy"
        );

        addRecipeIngredient(db, peanutbutterCrackerId, "cracker", 4.0, "item");
        addRecipeIngredient(db, peanutbutterCrackerId, "peanut butter", 2.0, "g");

        // 14th Recipe
        long plainCrackerId = addRecipe(
                db,
                "Plain Crackers",
                "Enjoy cracker as is with a cup of tea"
        );

        addRecipeIngredient(db, plainCrackerId, "cracker", 4.0, "item");
        addRecipeIngredient(db, plainCrackerId, "tea", 1.0, "item");

        // 15th Recipe
        long eggCrackerId = addRecipe(
                db,
                "Crackers and Eggs",
                "Boil one egg, add salt and pepper, enjoy with cracker"
        );

        addRecipeIngredient(db, eggCrackerId, "cracker", 4.0, "item");
        addRecipeIngredient(db, eggCrackerId, "egg", 1.0, "item");

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

    public ArrayList<Pantry_Foods> getAllPantryItems() {

        ArrayList<Pantry_Foods> pantryItems = new ArrayList<>();

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

            Pantry_Foods item = new Pantry_Foods(
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

    public Pantry_Foods getPantryItemById(int id) {

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

        Pantry_Foods item = null;

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

            item = new Pantry_Foods(
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



    public ArrayList<Recipes> getAllRecipes() {

        ArrayList<Recipes> recipes = new ArrayList<>();

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

            Recipes recipe = new Recipes(
                    id,
                    recipeName,
                    instructions
            );

            recipes.add(recipe);
        }

        cursor.close();

        return recipes;
    }

    public ArrayList<Recipe_Ingredients> getIngredientsForRecipe(
            int recipeId) {

        ArrayList<Recipe_Ingredients> ingredients =
                new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_RECIPE_INGREDIENTS,
                null,
                COLUMN_RECIPE_ID + " = ?",
                new String[]{String.valueOf(recipeId)},
                null,
                null,
                  COLUMN_INGREDIENT_NAME+ " ASC"
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

            Recipe_Ingredients ingredient =
                    new Recipe_Ingredients(
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
