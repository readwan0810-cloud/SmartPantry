package com.example.smartpantry;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {

    private EditText editItemName;
    private EditText editCategory;
    private EditText editQuantity;
    private EditText editUnit;
    private EditText editExpiryDate;

    private Button btnSaveItem;
    private Button btnBackHome;

    private PantryDBHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_item);

        editItemName = findViewById(R.id.editItemName);
        editCategory = findViewById(R.id.editCategory);
        editQuantity = findViewById(R.id.editQuantity);
        editUnit = findViewById(R.id.editUnit);
        editExpiryDate = findViewById(R.id.editExpiryDate);

        btnSaveItem = findViewById(R.id.btnSaveItem);
        btnBackHome = findViewById(R.id.btnBackHome);

        databaseHelper = new PantryDBHelper(this);

        btnSaveItem.setOnClickListener(view -> savePantryItem());

        btnBackHome.setOnClickListener(view -> finish());
    }

    private void savePantryItem() {

        String itemName = editItemName.getText().toString().trim();
        String category = editCategory.getText().toString().trim();
        String quantityText = editQuantity.getText().toString().trim();
        String unit = editUnit.getText().toString().trim();
        String expiryDate = editExpiryDate.getText().toString().trim();

        // Validate item name
        if (itemName.isEmpty()) {
            editItemName.setError("Please enter an item name");
            editItemName.requestFocus();
            return;
        }

        // Validate category
        if (category.isEmpty()) {
            editCategory.setError("Please enter a category");
            editCategory.requestFocus();
            return;
        }

        // Validate quantity
        if (quantityText.isEmpty()) {
            editQuantity.setError("Please enter a quantity");
            editQuantity.requestFocus();
            return;
        }

        double quantity;

        try {
            quantity = Double.parseDouble(quantityText);
        } catch (NumberFormatException e) {
            editQuantity.setError("Please enter a valid number");
            editQuantity.requestFocus();
            return;
        }

        if (quantity <= 0) {
            editQuantity.setError("Quantity must be greater than zero");
            editQuantity.requestFocus();
            return;
        }

        // Validate unit
        if (unit.isEmpty()) {
            editUnit.setError("Please enter a unit");
            editUnit.requestFocus();
            return;
        }

        // Add item to SQLite database
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
                    "Pantry item saved successfully",
                    Toast.LENGTH_SHORT
            ).show();

            // Clear the form
            editItemName.setText("");
            editCategory.setText("");
            editQuantity.setText("");
            editUnit.setText("");
            editExpiryDate.setText("");

            editItemName.requestFocus();

        } else {

            Toast.makeText(
                    this,
                    "Failed to save pantry item",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}