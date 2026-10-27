package Pharmacy_Invetory_System;

import java.util.ArrayList;

public abstract class Product {

    private static int currentIDNumber = 1;
    private final String productID;
    private String productName, genericName, brand, unit;
    private double sellingPrice;
    private int reorderLevel;
    private Category category;
    private final ArrayList<Batch> batches = new ArrayList<>();

    //2.) constructor para sa pag aadd mismo ng user ng products
    Product(String productName, String genericName, String brand, String unit, double sellingPrice, int reorderLevel, Category category) {
        this.productID = String.format("P%03d", currentIDNumber++); //automatic nag g generate yung id, hindi na kailangan pang i type
        this.productName = productName;
        this.genericName = genericName;
        this.brand = brand;
        this.unit = unit;
        this.sellingPrice = sellingPrice;
        this.reorderLevel = reorderLevel;
        this.category = category;

        int lastID = Integer.parseInt(productID.substring(1)); //Kinuha lang dito yung "0001" mula sa "P0001"
        if (lastID >= currentIDNumber) { //pede to tanggalin pagkawala na yung dummytable
            currentIDNumber = lastID + 1;
        }

    }

    //mga getter
    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public String getBrand() {
        return brand;
    }

    public String getUnit() {
        return unit;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public Category getCategory() {
        return category;
    }

    //mga setter
    //walang product id kasi bawal yun mabago ata
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    //i ooverwrite to
    public abstract String getProductType();

    public boolean addBatch(Batch addBatch) {
        if (addBatch == null) return false;
        if (findBatchById(addBatch.getBatchID()) != null) return false;

        batches.add(addBatch);
        return true;
    }

    public Batch findBatchById(String batchID) {
        for (Batch b : batches) {
            if (b.getBatchID().equals(batchID)) {
                return b;
            }
        }
        return null;
    }

    public int getTotalStock() {
        int total = 0;
        for (Batch b : batches) {
            total += b.getQuantity();
        }
        return total;
    }

    //get info method
    public void getInfo() {
        System.out.printf("%-15s: %s%n", "Product ID", productID);
        System.out.printf("%-15s: %s%n", "Product Name", productName);
        System.out.printf("%-15s: %s%n", "Generic Name", genericName);
        System.out.printf("%-15s: %s%n", "Brand", brand);
        System.out.printf("%-15s: %s%n", "Unit", unit);
        System.out.printf("%-15s: %.2f%n", "Selling Price", sellingPrice);
        System.out.printf("%-15s: %d%n", "Reorder Level", reorderLevel);
        System.out.printf("%-15s: %s%n", "Category: ", category.getCategory_Name());
    }

    public void checkExpiration() {
        for (Batch b : batches) {
            String status;

            if (b.isExpired()) {
                status = "EXPIRED";
            } else if (b.isNearExpiration()) {
                status = "NEAR EXPIRATION";
            } else {
                status = "OK";
            }

            if (b.getQuantity() <= 0) continue;

            System.out.println("Batch ID: " + b.getBatchID());
            System.out.println("Expiration date: " + b.getExpirationDate());
            System.out.println("Date received: " + b.getDateReceived());
            System.out.println("Batch Quantity: " + b.getQuantity());
            System.out.println("Status: " + status + "\n");
        }
    }

    public ArrayList<Batch> getBatches() {
        return batches;
    }

}






