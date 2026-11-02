package Pharmacy_Invetory_System.product;

import Pharmacy_Invetory_System.employees.Employee;
import Pharmacy_Invetory_System.employees.Owner;
import Pharmacy_Invetory_System.employees.Pharmacist;
import Pharmacy_Invetory_System.employees.Staff;

public class OverTheCounter extends Medicine {

    public OverTheCounter( String productName, String genericName, String brand, String unit, double sellingPrice,int reorderLevel, Category category){
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
