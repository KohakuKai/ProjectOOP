package Simula;

public class Essentials extends Product{

    Essentials( String productName, String genericName, String brand, String unit, double sellingPrice,int reorderLevel, Category category){
        super( productName, genericName, brand, unit, sellingPrice,reorderLevel, category);
    }

    @Override
    public String getProductType() {
        return "Essentials";
    }
}

//eto lang talaga lahat ng lalagay rito

