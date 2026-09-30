package com.example.smartpantry;
import androidx.appcompat.app.AppCompatActivity;
import android.os.*;
import android.widget.*;

public class Add_Foods extends AppCompatActivity {

     EditText editFoodName;
     EditText editCategory;
     EditText editQuantity;
     EditText editUnit;
     EditText editFoodExpiry;
     Button btnSaveFood;
     Button btnBack;
     Pantry_DB databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_foods);

        editFoodName = findViewById(R.id.editItemName);
        editCategory = findViewById(R.id.editCategory);
        editQuantity = findViewById(R.id.editQuantity);
        editUnit = findViewById(R.id.editUnit);
        editFoodExpiry = findViewById(R.id.editFoodExpiry);

        btnSaveFood = findViewById(R.id.btnSaveItem);
        btnBack = findViewById(R.id.btnBack);

        databaseHelper = new Pantry_DB(this);

        btnSaveFood.setOnClickListener(view -> savePantryItem());

        btnBack.setOnClickListener(view -> finish());
    }

    private void savePantryItem() {

        String itemName = editFoodName.getText().toString().trim();
        String category = editCategory.getText().toString().trim();
        String quantityText = editQuantity.getText().toString().trim();
        String unit = editUnit.getText().toString().trim();
        String expiryDate = editFoodExpiry.getText().toString().trim();

        // Item name is validated
        if (itemName.isEmpty()) {
            editFoodName.setError("Enter food name");
            editFoodName.requestFocus();
            return;
        }

        // The category also validated
        if (category.isEmpty()) {
            editCategory.setError("Enter food category");
            editCategory.requestFocus();
            return;
        }

        // Quantity validation
        if (quantityText.isEmpty()) {
            editQuantity.setError("Enter food quantity");
            editQuantity.requestFocus();
            return;
        }

        double quantity;

        try {
            quantity = Double.parseDouble(quantityText);
        } catch (NumberFormatException e) {
            editQuantity.setError("Valid number required");
            editQuantity.requestFocus();
            return;
        }

        if (quantity <= 0) {
            editQuantity.setError("Zero is prohibited on quantity");
            editQuantity.requestFocus();
            return;
        }

        // Validating the units
        if (unit.isEmpty()) {
            editUnit.setError("Enter a unit");
            editUnit.requestFocus();
            return;
        }

        // Adds the items to the SQLite database
        long result = databaseHelper.addPantryItem(
                itemName,
                category,
                quantity,
                unit,
                expiryDate
        );

        if (result != -1) {

            Toast.makeText(
                    this,
                    "Food saved successfully",
                    Toast.LENGTH_SHORT
            ).show();

            //Clears the form for new entries
            editFoodName.setText("");
            editCategory.setText("");
            editQuantity.setText("");
            editUnit.setText("");
            editFoodExpiry.setText("");

            editFoodName.requestFocus();

        } else {

            Toast.makeText(
                    this,
                    "This Pantry food item is not saved",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}