package com.example.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private Button btnAddItem;
    private Button btnViewItems;
    private Button btnExpiry;
    private Button btnSuggestedRecipes;
    private Button btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        // Connect buttons from the Home screen
        btnAddItem = findViewById(R.id.btnAddItem);
        btnViewItems = findViewById(R.id.btnViewItems);
        btnExpiry = findViewById(R.id.btnExpiry);
        btnSuggestedRecipes = findViewById(R.id.btnSuggestedRecipes);
        btnSettings = findViewById(R.id.btnSettings);

        // Add Pantry Item
        btnAddItem.setOnClickListener(view -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AddItemActivity.class
            );
            startActivity(intent);
        });

        // View Pantry Items
        btnViewItems.setOnClickListener(view -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    ViewItemsActivity.class
            );
            startActivity(intent);
        });

        // Check Expiry Dates
        btnExpiry.setOnClickListener(view -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    ExpiryActivity.class
            );
            startActivity(intent);
        });

        // Suggested Recipes
        btnSuggestedRecipes.setOnClickListener(view -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    SuggestedRecipesActivity.class
            );
            startActivity(intent);
        });

        // Settings
        btnSettings.setOnClickListener(view -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    SettingsActivity.class
            );
            startActivity(intent);
        });
    }

    // Create the toolbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(
                R.menu.main_menu,
                menu
        );

        return true;
    }

    // Handle toolbar menu selections
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.menuPantry) {

            Intent intent = new Intent(
                    MainActivity.this,
                    ViewItemsActivity.class
            );

            startActivity(intent);

            return true;
        }

        if (itemId == R.id.menuRecipes) {

            Intent intent = new Intent(
                    MainActivity.this,
                    SuggestedRecipesActivity.class
            );

            startActivity(intent);

            return true;
        }

        if (itemId == R.id.menuExpiry) {

            Intent intent = new Intent(
                    MainActivity.this,
                    ExpiryActivity.class
            );

            startActivity(intent);

            return true;
        }

        if (itemId == R.id.menuSettings) {

            Intent intent = new Intent(
                    MainActivity.this,
                    SettingsActivity.class
            );

            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}