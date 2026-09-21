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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connect Java to the Expiry Dates XML screen
        setContentView(R.layout.activity_expiry);

        // Connect Java variables to the XML components
        listExpiryItems = findViewById(R.id.listExpiryItems);
        btnBackHome = findViewById(R.id.btnBackHome);

        // Get all pantry items
        ArrayList<PantryItem> pantryItems = PantryStorage.getItems();

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

            try {

                // Convert the expiry date from text to a date
                LocalDate expiryDate =
                        LocalDate.parse(expiryDateText, formatter);

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
                // Otherwise the item is still safe based on its date
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

        // Display the expiry information
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                expiryItems
        );

        listExpiryItems.setAdapter(adapter);

        // Return to the Smart Pantry home screen
        btnBackHome.setOnClickListener(view -> {
            finish();
        });
    }
}