package Simula;

import java.util.ArrayList;
import java.util.List;

//di pa to tapos ata

public abstract class Product {
    private final String productID;
    private String productName, genericName, brand, unit;
    private double sellingPrice;

    private int reorderLevel;


    private Category category;
    private ArrayList<Batch> batches = new ArrayList<>();

    //constructor
    Product(String productID, String productName, String genericName, String brand, String unit,  double sellingPrice,int reorderLevel, Category category){
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
    public int getReorderLevel() {return reorderLevel;}
    public Category getCategory() { return category;}


    //mga setter
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
        if(sellingPrice>=0) {
            this.sellingPrice = sellingPrice;
        }
        else {
            System.out.println("Invalid Selling Price!");
        }

    }
    public void setReorderLevel(int reorderLevel) {
        if(reorderLevel>=0) {
            this.reorderLevel = reorderLevel;
        }
        else {
            System.out.println("Invalid Reorder Level!");
        }

    }
    public void setCategory(Category category) {this.category = category;}


    public abstract int getTotalStock(); //hindi ata to abstract? kasi pwede i desplay lahat ng overall products
    public abstract String getProductType();
    public abstract boolean isBelowReorderLevel();
    public abstract boolean isNearExpiration();
    public abstract void displayInfo();

}




