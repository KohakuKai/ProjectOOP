package Simula;

public class OverTheCounter extends Medicine {

    OverTheCounter( String productName, String genericName, String brand, String unit, double sellingPrice,int reorderLevel, Category category){
        super( productName, genericName, brand, unit, sellingPrice,reorderLevel, category);
    }

    @Override
    public String getProductType() {
        return "Over the Counter Medicine";
    }

    @Override
    public String getMedicineType(){
        return "Over The Counter";
    }

    @Override
    public boolean soldBy(Staff varname) {
        return varname instanceof Owner || varname instanceof Pharmacist || varname instanceof Employee;
    }


}
