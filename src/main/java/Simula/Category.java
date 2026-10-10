package Simula;

public class Category {
    private static int currentIDNumber = 1;
    private String Category_ID, Category_Name, Description;
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

    //mga setter
    public void setCategory_ID(String Category_ID) {
        this.Category_ID = Category_ID;
    }
    public void setCategory_Name(String Category_Name) {
        this.Category_Name = Category_Name;
    }
    public void setDescription(String Description) {
        this.Description = Description;
    }

    //pag display ng mga info
    public void displayInfo(){
        System.out.println("Category ID: " + getCategory_ID());
        System.out.println("Category Name: " + getCategory_Name());
        System.out.println("Description: " + getDescription());
    }
}
