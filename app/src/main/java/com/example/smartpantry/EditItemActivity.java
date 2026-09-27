package com.example.smartpantry;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditItemActivity extends AppCompatActivity {

    private EditText editItemName;
    private EditText editCategory;
    private EditText editQuantity;
    private EditText editUnit;
    private EditText editExpiryDate;
    private Button btnUpdateItem;
    private Button btnDeleteItem;
    private Button btnCancel;

    private PantryDBHelper databaseHelper;

    private int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_item);

        editItemName = findViewById(R.id.editItemName);
        editCategory = findViewById(R.id.editCategory);
        editQuantity = findViewById(R.id.editQuantity);
        editUnit = findViewById(R.id.editUnit);
        editExpiryDate = findViewById(R.id.editExpiryDate);

        btnUpdateItem = findViewById(R.id.btnUpdateItem);
        btnDeleteItem = findViewById(R.id.btnDeleteItem);
        btnCancel = findViewById(R.id.btnCancel);

        databaseHelper = new PantryDBHelper(this);

        itemId = getIntent().getIntExtra("ITEM_ID", -1);

        loadItem();

        btnUpdateItem.setOnClickListener(view -> updateItem());

        btnDeleteItem.setOnClickListener(view -> deleteItem());

        btnCancel.setOnClickListener(view -> finish());
    }

    private void loadItem() {

        if (itemId == -1) {
            Toast.makeText(
                    this,
                    "Invalid pantry item",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        PantryItem item = databaseHelper.getPantryItemById(itemId);

        if (item == null) {

            Toast.makeText(
                    this,
                    "Pantry item not found",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        editItemName.setText(item.getItemName());
        editCategory.setText(item.getCategory());
        editQuantity.setText(String.valueOf(item.getQuantity()));
        editUnit.setText(item.getUnit());

        if (item.getExpiryDate() != null) {
            editExpiryDate.setText(item.getExpiryDate());
        }
    }


    private void updateItem() {

        String itemName = editItemName.getText().toString().trim();
        String category = editCategory.getText().toString().trim();
        String quantityText = editQuantity.getText().toString().trim();
        String unit = editUnit.getText().toString().trim();
        String expiryDate = editExpiryDate.getText().toString().trim();

        if (itemName.isEmpty()) {
            editItemName.setError("Please enter an item name");
            editItemName.requestFocus();
            return;
        }

        if (category.isEmpty()) {
            editCategory.setError("Please enter a category");
            editCategory.requestFocus();
            return;
        }

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

        if (unit.isEmpty()) {
            editUnit.setError("Please enter a unit");
            editUnit.requestFocus();
            return;
        }

        int result = databaseHelper.updatePantryItem(
                itemId,
                itemName,
                category,
                quantity,
                unit,
                expiryDate
        );

        if (result > 0) {

            Toast.makeText(
                    this,
                    "Pantry item updated successfully",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } else {

            Toast.makeText(
                    this,
                    "Failed to update pantry item",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
    private void deleteItem() {

        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Delete Pantry Item")
                .setMessage("Are you sure you want to delete this pantry item?")
                .setNegativeButton("CANCEL", null)
                .setPositiveButton("DELETE", (dialog, which) -> {

                    int result = databaseHelper.deletePantryItem(itemId);

                    if (result > 0) {

                        Toast.makeText(
                                this,
                                "Pantry item deleted successfully",
                                Toast.LENGTH_SHORT
                        ).show();

                        finish();

                    } else {

                        Toast.makeText(
                                this,
                                "Failed to delete pantry item",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                })
                .show();
    }
    }



