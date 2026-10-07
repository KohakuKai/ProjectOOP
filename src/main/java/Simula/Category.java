package Simula;

public class Category {
    private String Category_ID, Category_Name, Description;
    // mga attributes

    Category(String Category_ID, String Category_Name, String Description){
        this.Category_ID = Category_ID;
        this.Category_Name = Category_Name;     //constructor
        this.Description = Description;
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

    //pag display ng mga info
    public void displayInfo(){
        System.out.println("Category ID: " + getCategory_ID());
        System.out.println("Category Name: " + getCategory_Name());
        System.out.println("Description: " + getDescription());
    }
}

// di ko lam kung gagawan ba to mga setter eh