package com.example.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewItemsActivity extends AppCompatActivity {

    private ListView listPantryItems;
    private Button btnBackHome;

    private PantryDBHelper databaseHelper;

    private ArrayList<PantryItem> pantryItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_items);

        listPantryItems = findViewById(R.id.listPantryItems);
        btnBackHome = findViewById(R.id.btnBackHome);

        databaseHelper = new PantryDBHelper(this);

        btnBackHome.setOnClickListener(view -> finish());

        listPantryItems.setOnItemClickListener((parent, view, position, id) -> {

            if (!pantryItems.isEmpty()) {

                PantryItem selectedItem = pantryItems.get(position);

                Intent intent = new Intent(
                        ViewItemsActivity.this,
                        EditItemActivity.class
                );

                intent.putExtra("ITEM_ID", selectedItem.getId());

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadPantryItems();
    }

    private void loadPantryItems() {

        pantryItems = databaseHelper.getAllPantryItems();

        ArrayList<String> displayItems = new ArrayList<>();

        for (PantryItem item : pantryItems) {

            String expiry;

            if (item.getExpiryDate() == null ||
                    item.getExpiryDate().isEmpty()) {

                expiry = "No expiry date";

            } else {

                expiry = "Expiry: " + item.getExpiryDate();
            }

            String displayText =
                    item.getItemName()
                            + " | "
                            + item.getCategory()
                            + "\nQuantity: "
                            + item.getQuantity()
                            + " "
                            + item.getUnit()
                            + "\n"
                            + expiry;

            displayItems.add(displayText);
        }

        if (displayItems.isEmpty()) {

            displayItems.add(
                    "Your pantry is currently empty."
            );
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        displayItems
                );

        listPantryItems.setAdapter(adapter);
    }
}