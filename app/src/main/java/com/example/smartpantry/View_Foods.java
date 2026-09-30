package com.example.smartpantry;
import androidx.appcompat.app.AppCompatActivity;
import android.content.*;
import android.os.*;
import android.widget.*;
import java.util.*;

public class View_Foods extends AppCompatActivity {

    Pantry_DB databaseHelper;
    Button btnBack;
    ListView listViewFoods;

     ArrayList<Pantry_Foods> pantryFoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_foods);

        listViewFoods = findViewById(R.id.listFoodItems);
        btnBack = findViewById(R.id.btnBack);

        databaseHelper = new Pantry_DB(this);

        btnBack.setOnClickListener(view -> finish());

        listViewFoods.setOnItemClickListener((parent, view, position, id) -> {

            if (!pantryFoods.isEmpty()) {

                Pantry_Foods selectedItem = pantryFoods.get(position);

                Intent intent = new Intent(
                        View_Foods.this,
                        Edit_Foods.class
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

        pantryFoods = databaseHelper.getAllPantryItems();

        ArrayList<String> displayItems = new ArrayList<>();

        for (Pantry_Foods item : pantryFoods) {

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
                    "Pantry empty."
            );
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        displayItems
                );

        listViewFoods.setAdapter(adapter);
    }
}