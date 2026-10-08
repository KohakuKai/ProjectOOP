package Simula;

public class PRESCRIPTION_PRODUCT extends Medicine {

    PRESCRIPTION_PRODUCT(String productID, String productName, String genericName, String brand, String unit, double sellingPrice, Category category){
        super(productID, productName, genericName, brand, unit, sellingPrice, category);
    }

    @Override
    public String getProductType() {
        return "Product Type: Medicine";
    }

    @Override
    public String getMedicineType(){
        return "Medicine Type: Prescription Product";
    }

}
