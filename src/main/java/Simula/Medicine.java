package Simula;

public abstract class Medicine extends Product {

    Medicine(String productID, String productName, String genericName, String brand, String unit, double sellingPrice, Category category) {
        super(productID, productName, genericName, brand, unit, sellingPrice, category);
    }
    @Override
    public String getProductType() {
        return "Product Type: Essentials";
    }

    public abstract String getMedicineType();
}
