package Pharmacy_Invetory_System;

public class Category {
    private static int currentIDNumber = 1;
    private final String Category_ID, Category_Name, Description;
    // mga attributes

    Category( String Category_Name, String Description){
        this.Category_ID = String.format("C%03d", currentIDNumber++);
        this.Category_Name = Category_Name;     //constructor
        this.Description = Description;

        int lastID = Integer.parseInt(getCategory_ID().substring(1)); //Kinuha lang dito yung "0001" mula sa "P0001"
        if(lastID>= currentIDNumber) { //pede to tanggalin pagkawala na yung dummytable
            currentIDNumber = lastID+1;
        }
    }

    //mga getter
    public String getCategory_ID() {
        return Category_ID;
    }
    public String getCategory_Name() {
        return Category_Name;
    }
    public String getDescription() {
        return Description;
    }
}
