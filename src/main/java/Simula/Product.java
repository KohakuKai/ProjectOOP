package Simula;

import java.util.ArrayList;
import java.util.List;


//mas maganda ata gawin nalang to abstract di ko sure

public class Product {
    String productID, productName, genericName, brand, unit;
    double sellingPrice;

    int reorderLevel;

    private Category category;
    private ArrayList<Batch> batch = new ArrayList<>();

    //constructor
    Product(String productID, String productName, String genericName, String brand, String unit, double sellingPrice){
        this.productID = productID;
        this.productName = productName;
        this.genericName = genericName;
        this.brand = brand;
        this.unit = unit;
        this.sellingPrice = sellingPrice;
    }
    //i ooveride to
    public String getProductType(){
        return null;
    }
}

    //public int getTotalStock()
    //maya na to

    //public boolean isBelowOrderLevel()
    //ano ba ung order level?

    //public void


    //tingin ko abstract nalang dapat to
