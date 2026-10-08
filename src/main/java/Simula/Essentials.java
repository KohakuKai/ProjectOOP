package Simula;

public class Essentials extends Product{

    Essentials(String productID, String productName, String genericName, String brand, String unit, double sellingPrice,int reorderLevel, Category category){
        super(productID, productName, genericName, brand, unit, sellingPrice,reorderLevel, category);
    }

    @Override
    public String getProductType() {
        return "Essentials";
    }
}

//eto lang talaga lahat ng lalagay rito

