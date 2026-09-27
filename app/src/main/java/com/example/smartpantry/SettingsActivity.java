package com.example.smartpantry;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Switch switchExpiryAlerts;
    private Spinner spinnerPreferredUnit;
    private Button btnBackHome;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        switchExpiryAlerts = findViewById(R.id.switchExpiryAlerts);
        spinnerPreferredUnit = findViewById(R.id.spinnerPreferredUnit);
        btnBackHome = findViewById(R.id.btnBackHome);

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

        // Load previously saved settings
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

        // Save settings when the user changes them
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

        btnBackHome.setOnClickListener(view -> finish());
    }
}