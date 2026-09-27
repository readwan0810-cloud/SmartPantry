package com.example.smartpantry;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class ExpiryActivity extends AppCompatActivity {

    private ListView listExpiryItems;
    private Button btnBackHome;
    private PantryDBHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connect Java to the Expiry Dates XML screen
        setContentView(R.layout.activity_expiry);

        // Connect Java variables to the XML components
        listExpiryItems = findViewById(R.id.listExpiryItems);
        btnBackHome = findViewById(R.id.btnBackHome);

        // Connect to the SQLite database
        databaseHelper = new PantryDBHelper(this);

        // Return to the Smart Pantry home screen
        btnBackHome.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Reload expiry information whenever the screen is opened
        loadExpiryItems();
    }

    private void loadExpiryItems() {

        // Get pantry items from the SQLite database
        ArrayList<PantryItem> pantryItems =
                databaseHelper.getAllPantryItems();

        // Create a list to display expiry information
        ArrayList<String> expiryItems = new ArrayList<>();

        // Get today's date
        LocalDate today = LocalDate.now();

        // Date format used by our app
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Check every pantry item
        for (PantryItem item : pantryItems) {

            String itemName = item.getItemName();
            String expiryDateText = item.getExpiryDate();

            // Expiry date is optional
            if (expiryDateText == null || expiryDateText.trim().isEmpty()) {

                expiryItems.add(
                        itemName
                                + " | No expiry date"
                );

                continue;
            }

            try {

                // Convert the expiry date from text to a date
                LocalDate expiryDate =
                        LocalDate.parse(
                                expiryDateText,
                                formatter
                        );

                // Work out the number of days until expiry
                long daysUntilExpiry =
                        java.time.temporal.ChronoUnit.DAYS.between(
                                today,
                                expiryDate
                        );

                String status;

                // Check whether the item has expired
                if (daysUntilExpiry < 0) {

                    status = "EXPIRED";

                }
                // Check whether the item expires within 7 days
                else if (daysUntilExpiry <= 7) {

                    status = "EXPIRING SOON";

                }
                // Otherwise the item is not expiring soon
                else {

                    status = "NOT EXPIRED";
                }

                // Add the item information to the list
                String itemDetails =
                        itemName
                                + " | Expiry: " + expiryDateText
                                + " | " + status;

                expiryItems.add(itemDetails);

            } catch (DateTimeParseException e) {

                // Display an error if the date format is incorrect
                expiryItems.add(
                        itemName
                                + " | Expiry: " + expiryDateText
                                + " | INVALID DATE"
                );
            }
        }

        // Display a message if the pantry is empty
        if (expiryItems.isEmpty()) {

            expiryItems.add(
                    "No pantry items available."
            );
        }

        // Create the ListView adapter
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        expiryItems
                );

        // Display the expiry information
        listExpiryItems.setAdapter(adapter);
    }
}