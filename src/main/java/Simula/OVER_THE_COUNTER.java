package Simula;

public class OVER_THE_COUNTER extends Medicine {

    OVER_THE_COUNTER(String productID, String productName, String genericName, String brand, String unit, double sellingPrice, Category category){
        super(productID, productName, genericName, brand, unit, sellingPrice, category);
    }

    @Override
    public String getProductType() {
        return "Product Type: Medicine";
    }

    @Override
    public String getMedicineType(){
        return "Medicine Type: Over The Counter";
    }

}
