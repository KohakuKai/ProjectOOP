package Pharmacy_Invetory_System.product;

import Pharmacy_Invetory_System.employees.Owner;
import Pharmacy_Invetory_System.employees.Pharmacist;
import Pharmacy_Invetory_System.employees.Staff;

public class PrescriptionMedicine extends Medicine {

    public PrescriptionMedicine( String productName, String genericName, String brand, String unit, double sellingPrice,int reorderLevel, Category category){
        super(productName, genericName, brand, unit, sellingPrice,reorderLevel, category );
    }

    @Override
    public String getProductType() {
        return "Prescription Medicine";
    }

    @Override
    public String getMedicineType(){
        return "Prescription Product";
    }

    @Override
    public boolean soldBy(Staff varname) {
        return varname instanceof Owner || varname instanceof Pharmacist;
    }

}
