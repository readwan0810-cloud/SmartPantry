package com.example.smartpantry;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.*;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.content.*;

public class Main_Screen extends AppCompatActivity {

    Button btnAdd_Item;
    Button btnView_Item;
    Button btnExpiry;
    Button btnAvailableRecipes;
    Button btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_screen);

        // The toolbar setup
        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        // Buttons connecting the home screen
        btnAdd_Item = findViewById(R.id.btnAdd_Item);
        btnView_Item = findViewById(R.id.btnView_Items);
        btnExpiry = findViewById(R.id.btnExpiry);
        btnAvailableRecipes = findViewById(R.id.btnAvailableRecipes);
        btnSettings = findViewById(R.id.btnSettings);

        // Pantry Items button
        btnAdd_Item.setOnClickListener(view -> {
            Intent intent = new Intent(
                    Main_Screen.this,
                    Add_Foods.class
            );
            startActivity(intent);
        });
        // The pantry items view button
        btnView_Item.setOnClickListener(view -> {
            Intent intent = new Intent(
                    Main_Screen.this,
                    View_Foods.class
            );
            startActivity(intent);
        });

        // Expiry dates button
        btnExpiry.setOnClickListener(view -> {
            Intent intent = new Intent(
                    Main_Screen.this,
                    Food_Expiry.class
            );
            startActivity(intent);
        });

        // The available recipes button
        btnAvailableRecipes.setOnClickListener(view -> {
            Intent intent = new Intent(
                    Main_Screen.this,
                    Available_Recipe.class
            );
            startActivity(intent);
        });

        // Settings button on home screen
        btnSettings.setOnClickListener(view -> {
            Intent intent = new Intent(
                    Main_Screen.this,
                    Screen_Settings.class
            );
            startActivity(intent);
        });
    }

    // The toolbar menu is created
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(
                R.menu.main_menu,
                menu
        );

        return true;
    }

    // Toolbar menu selections
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.menuPantry) {

            Intent intent = new Intent(
                    Main_Screen.this,
                    View_Foods.class
            );

            startActivity(intent);

            return true;
        }

        if (itemId == R.id.menuRecipes) {

            Intent intent = new Intent(
                    Main_Screen.this,
                    Available_Recipe.class
            );

            startActivity(intent);

            return true;
        }

        if (itemId == R.id.menuExpiry) {

            Intent intent = new Intent(
                    Main_Screen.this,
                    Food_Expiry.class
            );

            startActivity(intent);

            return true;
        }

        if (itemId == R.id.menuSettings) {

            Intent intent = new Intent(
                    Main_Screen.this,
                    Screen_Settings.class
            );

            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
