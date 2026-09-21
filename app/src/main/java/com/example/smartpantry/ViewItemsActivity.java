package com.example.smartpantry;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewItemsActivity extends AppCompatActivity {

    private ListView listPantryItems;
    private Button btnBackHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connect Java to the View Pantry Items XML screen
        setContentView(R.layout.activity_view_items);

        // Connect Java variables to the XML components
        listPantryItems = findViewById(R.id.listPantryItems);
        btnBackHome = findViewById(R.id.btnBackHome);

        // Get the saved pantry items
        ArrayList<PantryItem> pantryItems = PantryStorage.getItems();

        // Create a list of text to display
        ArrayList<String> displayItems = new ArrayList<>();

        for (PantryItem item : pantryItems) {

            String itemDetails =
                    item.getItemName()
                            + " | " + item.getCategory()
                            + " | Quantity: " + item.getQuantity()
                            + " | Expiry: " + item.getExpiryDate();

            displayItems.add(itemDetails);
        }

        // Display the pantry items
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                displayItems
        );

        listPantryItems.setAdapter(adapter);

        // Return to the Smart Pantry home screen
        btnBackHome.setOnClickListener(view -> {
            finish();
        });
    }
}