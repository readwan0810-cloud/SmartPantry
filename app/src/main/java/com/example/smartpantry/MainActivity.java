package com.example.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnAddItem;
    private Button btnViewItems;
    private Button btnExpiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connects MainActivity to the home screen XML
        setContentView(R.layout.activity_main);

        // Connects Java variables to the buttons in activity_main.xml
        btnAddItem = findViewById(R.id.btnAddItem);
        btnViewItems = findViewById(R.id.btnViewItems);
        btnExpiry = findViewById(R.id.btnExpiry);

        // Opens the Add Pantry Item screen
        btnAddItem.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
            startActivity(intent);
        });

        // Temporary message for View Pantry Items
        btnViewItems.setOnClickListener(view -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    ViewItemsActivity.class
            );
            startActivity(intent);
        });

        // Temporary message for Check Expiry Dates
        btnExpiry.setOnClickListener(view -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    ExpiryActivity.class
            );
            startActivity(intent);
        });
    }
}