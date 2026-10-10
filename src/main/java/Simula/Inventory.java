package Simula;

import java.util.ArrayList;

public class Inventory {

    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Category> categories = new ArrayList<>();

    public Inventory() {
        defaultCategories();
        defaultProducts();
    }

    //Product Methods
    public ArrayList<Product> viewProducts() {
        return products;
    }

    //Add product
    public boolean addProduct(Employee employee, Product newProduct) {
        if(!(employee instanceof  Owner))  return false; //pagka hindi owner gumamit
        if(newProduct == null) return false; //pagka null yung laman ng newProduct
        if(findProductById(newProduct.getProductID()) != null)  return false; //pagka may ka same na ID
        if(newProduct.getCategory() == null) return false; //pagka naginput ng invalid na category name

        products.add(newProduct);return true;
    }

    //Find ProductByID
    public Product findProductById(String givenProductID) {
        for(Product p : products) {
            if(p.getProductID().equals(givenProductID)) return p;
        }
        return null;
    }


    //Category Methods
    public ArrayList<Category> viewCategories() { return categories; }

    //Add category
    public boolean addCategory(Employee employee,Category newCategory) {
        if(!(employee instanceof Owner)) return false; //pagka hindi owner ang gumamit
        if(newCategory == null) return false; //walang laman yung inadd
        if(findCategoryById(newCategory.getCategory_ID()) != null) return false; //ibigsabihin may nahanap na existing category

        categories.add(newCategory); return true;
    }

    public Category findCategoryById(String givenCategoryID) {
        for(Category c : categories) {
            if(c.getCategory_ID().equals(givenCategoryID)) return c;
        }
        return null; //pagkawalang nahanap null i rereturn
    }

    private void defaultProducts() {

        products.add(new OverTheCounter("Biogesic","Paracetamol","Unilab","Tablet",7,50,findCategoryById("C005")));
        products.add(new OverTheCounter("Ascorbic Acid Vitamin C","Ascorbic Acid","Celine","Syrup",50,50,findCategoryById("C001")));


    }

    private void defaultCategories() { //dummy values lang para sa testing
        categories.add(new Category("Vitamins","Makes the body stronger"));
        categories.add(new Category("Antibiotics","Medicine for bacterial infections"));
        categories.add(new Category("Personal Care","Hygiene and Care Products"));
        categories.add(new Category("Pain Reliever","Alleviates Pain"));
        categories.add(new Category("Antipyretic","Fever Reducer"));

    }


}
