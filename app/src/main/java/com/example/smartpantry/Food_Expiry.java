package com.example.smartpantry;
import androidx.appcompat.app.AppCompatActivity;
import android.os.*;
import android.widget.*;
import java.time.*;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class Food_Expiry extends AppCompatActivity {

    private ListView listFoodExpiry;
    private Button btnBack;
    private Pantry_DB databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Connection for Java to connect with the Expiry screen
        setContentView(R.layout.food_expiry);

        //Connection for Java variable to connect with XML
        listFoodExpiry = findViewById(R.id.listExpiryItems);
        btnBack = findViewById(R.id.btnBackHome);

        // SQLite database connection
        databaseHelper = new Pantry_DB(this);

        // Return to the Smart Pantry home screen
        btnBack.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Reload expiry information when screen is opened
        loadExpiryItems();
    }

    private void loadExpiryItems() {

        // Gets food items in the SQLite database
        ArrayList<Pantry_Foods> pantryItems =
                databaseHelper.getAllPantryItems();

        // Creates a list that displays expiry food information
        ArrayList<String> expiryItems = new ArrayList<>();

        // Get the present date
        LocalDate today = LocalDate.now();

        // Date format used in app
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Checks food item
        for (Pantry_Foods item : pantryItems) {

            String itemName = item.getItemName();
            String expiryDateText = item.getExpiryDate();

            // Optional expiry date
            if (expiryDateText == null || expiryDateText.trim().isEmpty()) {

                expiryItems.add(
                        itemName
                                + " | No food expiry date"
                );

                continue;
            }

            try {

                // Converts text to a date
                LocalDate expiryDate =
                        LocalDate.parse(
                                expiryDateText,
                                formatter
                        );

                // Works out the days until expiry
                long daysUntilExpiry =
                        java.time.temporal.ChronoUnit.DAYS.between(
                                today,
                                expiryDate
                        );

                String status;

                // Checks whether food item is expired
                if (daysUntilExpiry < 0) {

                    status = "Expired";

                }
                // Checks whether food item expires in 7 days
                else if (daysUntilExpiry <= 7) {

                    status = "Expires soon";

                }
                // Confirms food item is not expired
                else {

                    status = "Not Expired";
                }

                // Adds food item to the list
                String itemDetails =
                        itemName
                                + " | Expiry: " + expiryDateText
                                + " | " + status;

                expiryItems.add(itemDetails);

            } catch (DateTimeParseException e) {

                // Displays error when the date is incorrect
                expiryItems.add(
                        itemName
                                + " | Expiry: " + expiryDateText
                                + " | INVALID DATE"
                );
            }
        }

        // Display a message when the pantry is empty
        if (expiryItems.isEmpty()) {

            expiryItems.add(
                    "No pantry items available."
            );
        }

        // Create the view adapter list
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        expiryItems
                );

        // Displays food expiry information
        listFoodExpiry.setAdapter(adapter);
    }
}