package Simula;

public abstract class Medicine extends Product {


    Medicine(String productID, String productName, String genericName, String brand, String unit, double sellingPrice,int reorderLevel, Category category) {
        super(productID, productName, genericName, brand, unit, sellingPrice,reorderLevel, category);

    }
    @Override
    public String getProductType() {
        return "Medicine";
    }

    public abstract String getMedicineType();
    public abstract boolean soldBy(Employee varname);
}
