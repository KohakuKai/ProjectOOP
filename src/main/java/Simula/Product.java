package Simula;

import java.util.ArrayList;

public abstract class Product {
    private String productID, productName, genericName, brand, unit;
    private double sellingPrice;
    private int reorderLevel;

    private Category category;
    private ArrayList<Batch> batch = new ArrayList<>();

    //constructor
    Product(String productID, String productName, String genericName, String brand, String unit, double sellingPrice,int reorderLevel, Category category){
        this.productID = productID;
        this.productName = productName;
        this.genericName = genericName;
        this.brand = brand;
        this.unit = unit;
        this.sellingPrice = sellingPrice;
        this.reorderLevel = reorderLevel;
        this.category = category;
    }

    //mga getter
    public String getProductID() {
        return productID;
    }
    public String getProductName() {
        return productName;
    }
    public String getGenericName() {
        return genericName;
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
    public void setProductID(String productID) {
        this.productID = productID;
    }
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

    public int getTotalStock(){
        int total = 0;
        for(Batch b : batch){
            total += b.getQuantity();
        }
        return total;
    }

    //kailangan na set na reorder level para rito
    public boolean isBelowOrderLevel(){
        return getTotalStock() < reorderLevel;
    }

    //get info method
    public void getInfo(){
        System.out.println("Product ID: " + productID);
        System.out.println("Product Name: " + productName);
        System.out.println("Generic Name: " + genericName);
        System.out.println("Brand: " + brand);
        System.out.println("Unit: " + unit);
        System.out.println("Selling Price: " + sellingPrice);
        System.out.println("Reorder Level: " + reorderLevel);
        System.out.println("Category: " + category);
        System.out.println("Total Stock: " + getTotalStock());
    }

    public void checkExpiration(){
        for(Batch b : batch){
            System.out.println("Batch ID: " + b.getBatchID());
            System.out.println("Expiration date: " + b.getExpirationDate());
            System.out.println("Expired: " + b.isExpired());
        }
    }
}




