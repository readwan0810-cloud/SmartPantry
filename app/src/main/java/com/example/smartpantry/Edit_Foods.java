package com.example.smartpantry;
import androidx.appcompat.app.AppCompatActivity;
import android.os.*;
import android.widget.*;


public class Edit_Foods extends AppCompatActivity {

    private EditText editFoodName;
    private EditText editCategory;
    private EditText editQuantity;
    private EditText editUnit;
    private EditText editFoodExpiry;
    private Button btnUpdateFood;
    private Button btnDeleteFood;
    private Button btnCancel;

    private Pantry_DB databaseHelper;

    private int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_foods);

        editFoodName = findViewById(R.id.editItemName);
        editCategory = findViewById(R.id.editCategory);
        editQuantity = findViewById(R.id.editQuantity);
        editUnit = findViewById(R.id.editUnit);
        editFoodExpiry = findViewById(R.id.editExpiryDate);

        btnUpdateFood = findViewById(R.id.btnUpdateItem);
        btnDeleteFood = findViewById(R.id.btnDeleteItem);
        btnCancel = findViewById(R.id.btnCancel);

        databaseHelper = new Pantry_DB(this);

        itemId = getIntent().getIntExtra("ITEM_ID", -1);

        loadItem();

        btnUpdateFood.setOnClickListener(view -> updateItem());

        btnDeleteFood.setOnClickListener(view -> deleteItem());

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

        Pantry_Foods item = databaseHelper.getPantryItemById(itemId);

        if (item == null) {

            Toast.makeText(
                    this,
                    "Pantry item not found",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        editFoodName.setText(item.getItemName());
        editCategory.setText(item.getCategory());
        editQuantity.setText(String.valueOf(item.getQuantity()));
        editUnit.setText(item.getUnit());

        if (item.getExpiryDate() != null) {
            editFoodExpiry.setText(item.getExpiryDate());
        }
    }


    private void updateItem() {

        String itemName = editFoodName.getText().toString().trim();
        String category = editCategory.getText().toString().trim();
        String quantityText = editQuantity.getText().toString().trim();
        String unit = editUnit.getText().toString().trim();
        String expiryDate = editFoodExpiry.getText().toString().trim();

        if (itemName.isEmpty()) {
            editFoodName.setError("Enter food name");
            editFoodName.requestFocus();
            return;
        }

        if (category.isEmpty()) {
            editCategory.setError("Enter food category");
            editCategory.requestFocus();
            return;
        }

        if (quantityText.isEmpty()) {
            editQuantity.setError("Enter food quantity");
            editQuantity.requestFocus();
            return;
        }

        double quantity;

        try {
            quantity = Double.parseDouble(quantityText);
        } catch (NumberFormatException e) {
            editQuantity.setError("Enter a valid number for food quantity");
            editQuantity.requestFocus();
            return;
        }

        if (quantity <= 0) {
            editQuantity.setError("Enter food quantity greater than zero");
            editQuantity.requestFocus();
            return;
        }

        if (unit.isEmpty()) {
            editUnit.setError("Please enter a unit amount");
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
                    "Food item updated successfully",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } else {

            Toast.makeText(
                    this,
                    "Failed to update food item",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
    private void deleteItem() {

        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Delete Food Item")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Delete", (dialog, which) -> {

                    int result = databaseHelper.deletePantryItem(itemId);

                    if (result > 0) {

                        Toast.makeText(
                                this,
                                "Food item deleted",
                                Toast.LENGTH_SHORT
                        ).show();

                        finish();

                    } else {

                        Toast.makeText(
                                this,
                                "Failed to delete food item",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                })
                .show();
    }
    }



