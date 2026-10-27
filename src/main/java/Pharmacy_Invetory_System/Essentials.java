package Pharmacy_Invetory_System;

public class Essentials extends Product{

    Essentials( String productName, String genericName, String brand, String unit, double sellingPrice,int reorderLevel, Category category){
        super( productName, genericName, brand, unit, sellingPrice,reorderLevel, category);
    }

    @Override
    public String getProductType() {
        return "Essentials";
    }

    @Override
    public void getInfo() {
            System.out.printf("%-15s: %s%n",   "Product ID", super.getProductID());
            System.out.printf("%-15s: %s%n",   "Product Name", super.getProductName());
            System.out.printf("%-15s: %s%n",   "Brand", super.getBrand());
            System.out.printf("%-15s: %s%n",   "Unit", super.getUnit());
            System.out.printf("%-15s: %.2f%n", "Selling Price", super.getSellingPrice());
            System.out.printf("%-15s: %d%n",   "Reorder Level", super.getReorderLevel());
            System.out.printf("%-15s: %s%n", "Category: ", super.getCategory().getCategory_Name());
        }

    }


