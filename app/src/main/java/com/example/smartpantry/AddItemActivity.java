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
    private EditText editExpiryDate;
    private Button btnSaveItem;

    private Button btnBackHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connect Java to the Add Pantry Item XML screen
        setContentView(R.layout.activity_add_item);

        // Connect Java variables to the XML fields
        editItemName = findViewById(R.id.editItemName);
        editCategory = findViewById(R.id.editCategory);
        editQuantity = findViewById(R.id.editQuantity);
        editExpiryDate = findViewById(R.id.editExpiryDate);
        btnSaveItem = findViewById(R.id.btnSaveItem);
        btnBackHome = findViewById(R.id.btnBackHome);

        // Save button
        btnSaveItem.setOnClickListener(view -> {

            // Get the information entered by the user
            String itemName = editItemName.getText().toString().trim();
            String category = editCategory.getText().toString().trim();
            String quantityText = editQuantity.getText().toString().trim();
            String expiryDate = editExpiryDate.getText().toString().trim();

            // Check that all fields have been completed
            if (itemName.isEmpty() ||
                    category.isEmpty() ||
                    quantityText.isEmpty() ||
                    expiryDate.isEmpty()) {

                Toast.makeText(
                        AddItemActivity.this,
                        "Please complete all fields",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            // Check that quantity is a valid number
            try {
                int quantity = Integer.parseInt(quantityText);

                if (quantity <= 0) {
                    Toast.makeText(
                            AddItemActivity.this,
                            "Quantity must be greater than 0",
                            Toast.LENGTH_SHORT
                    ).show();

                    return;
                }

// Create a PantryItem object using the information entered
                PantryItem pantryItem = new PantryItem(
                        itemName,
                        category,
                        quantity,
                        expiryDate
                );

// Store the pantry item
                PantryStorage.addItem(pantryItem);

// Tell the user that the item was saved
                Toast.makeText(
                        AddItemActivity.this,
                        itemName + " saved successfully!",
                        Toast.LENGTH_SHORT
                ).show();

// Clear the form after saving
                editItemName.setText("");
                editCategory.setText("");
                editQuantity.setText("");
                editExpiryDate.setText("");

            } catch (NumberFormatException e) {

                Toast.makeText(
                        AddItemActivity.this,
                        "Please enter a valid quantity",
                        Toast.LENGTH_SHORT
                ).show();
            }

        });

        btnBackHome.setOnClickListener(view ->{
            finish();
        });
    }
}