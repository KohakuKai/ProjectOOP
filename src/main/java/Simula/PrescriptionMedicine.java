package Simula;

public class PrescriptionMedicine extends Medicine {

    PrescriptionMedicine( String productName, String genericName, String brand, String unit, double sellingPrice,int reorderLevel, Category category){
        super(productName, genericName, brand, unit, sellingPrice,reorderLevel, category );
    }

    @Override
    public String getProductType() {
        return "Medicine";
    }

    @Override
    public String getMedicineType(){
        return "Prescription Product";
    }

    @Override
    public boolean soldBy(Employee varname) {
        return varname instanceof Owner || varname instanceof Pharmacist;
    }

}
