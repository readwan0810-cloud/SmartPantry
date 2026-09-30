package com.example.smartpantry;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.*;
import android.widget.*;



public class Screen_Settings extends AppCompatActivity {

    private Switch switchExpiryAlerts;
    private Spinner spinnerPreferredUnit;
    private Button btnBack;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.screen_settings);

        switchExpiryAlerts = findViewById(R.id.switchExpiryAlerts);
        spinnerPreferredUnit = findViewById(R.id.spinnerPreferredUnit);
        btnBack = findViewById(R.id.btnBack);

        preferences = getSharedPreferences(
                "SmartPantrySettings",
                MODE_PRIVATE
        );

        String[] units = {
                "kg",
                "g",
                "item",
                "litre",
                "ml"
        };

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        units
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerPreferredUnit.setAdapter(adapter);

        // Loading previously saved food items
        boolean expiryAlerts =
                preferences.getBoolean("expiryAlerts", true);

        String preferredUnit =
                preferences.getString("preferredUnit", "kg");

        switchExpiryAlerts.setChecked(expiryAlerts);

        for (int i = 0; i < units.length; i++) {

            if (units[i].equals(preferredUnit)) {
                spinnerPreferredUnit.setSelection(i);
                break;
            }
        }

        // Saves changes when user initiates them
        switchExpiryAlerts.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {

                    preferences.edit()
                            .putBoolean("expiryAlerts", isChecked)
                            .apply();
                }
        );

        spinnerPreferredUnit.setOnItemSelectedListener(
                new android.widget.AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            android.widget.AdapterView<?> parent,
                            android.view.View view,
                            int position,
                            long id) {

                        preferences.edit()
                                .putString(
                                        "preferredUnit",
                                        units[position]
                                )
                                .apply();
                    }

                    @Override
                    public void onNothingSelected(
                            android.widget.AdapterView<?> parent) {
                    }
                }
        );

        btnBack.setOnClickListener(view -> finish());
    }
}