package com.example.smartpantry;

public class PantryItem {

    private String itemName;
    private String category;
    private int quantity;
    private String expiryDate;

    public PantryItem(String itemName, String category, int quantity, String expiryDate) {
        this.itemName = itemName;
        this.category = category;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }

    public String getItemName() {
        return itemName;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getExpiryDate() {
        return expiryDate;
    }
}
