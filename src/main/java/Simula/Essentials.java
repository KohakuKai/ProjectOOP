package Simula;

public class Essentials extends Product{

    Essentials(String productID, String productName, String genericName, String brand, String unit, double sellingPrice, Category category){
        super(productID, productName, genericName, brand, unit, sellingPrice, category);
    }

    @Override
    public String getProductType() {
        return "Product Type: Essentials";
    }
}

//eto lang talaga lahat ng lalagay rito

