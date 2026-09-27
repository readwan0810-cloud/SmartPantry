package com.example.smartpantry;

public class PantryItem {

    private int id;
    private String itemName;
    private String category;
    private double quantity;
    private String unit;
    private String expiryDate;

    public PantryItem(
            int id,
            String itemName,
            String category,
            double quantity,
            String unit,
            String expiryDate) {

        this.id = id;
        this.itemName = itemName;
        this.category = category;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
    }

    public int getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public String getCategory() {
        return category;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public String getExpiryDate() {
        return expiryDate;
    }
}
