package Simula;

import java.util.ArrayList;
import java.util.List;

//di pa to tapos ata

public abstract class Product {
    private String productID, productName, genericName, brand, unit;
    private double sellingPrice;

    private int reorderLevel;

    private Category category;
    private ArrayList<Batch> batch = new ArrayList<>();

    //constructor
    Product(String productID, String productName, String genericName, String brand, String unit, double sellingPrice, Category category){
        this.productID = productID;
        this.productName = productName;
        this.genericName = genericName;
        this.brand = brand;
        this.unit = unit;
        this.sellingPrice = sellingPrice;
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


    public abstract String getProductType();
    public abstract int getTotalStock();
}




