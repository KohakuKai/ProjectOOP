package Simula;

import java.util.ArrayList;

public class Inventory {

    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Category> categories = new ArrayList<>();

    public Inventory() {
        defaultCategories();
    }

    //Product Methods
    public ArrayList<Product> viewProducts() {
        return products;
    }
    public boolean addProduct(Employee employee, Product newProduct) {
        if(!(employee instanceof  Owner)) {
            return false; //pagka hindi owner gumamit
        }
        if(newProduct == null) {
            return false; //pagka null yung laman ng newProduct
        }
        if(findProductById(newProduct.getProductID()) != null) {
            return false; //pagka may ka same na ID
        }
        products.add(newProduct);
        return true;
    }

    public Product findProductById(String givenProductID) {
        for(Product p : products) {
            if(p.getProductID().equals(givenProductID)) {
                return p;
            }
        }
        return null;
    }


    //Category Methods
    public ArrayList<Category> viewCategories() { return categories; }
    public boolean addCategory(Employee employee,Category newCategory) {
        if(!(employee instanceof Owner)){
            return false; //pagka hindi owner ang gumamit
        }
        if(newCategory == null){
            return false; //walang laman yung inadd
        }
        if(findCategoryById(newCategory.getCategory_ID()) != null){
            return false; //ibigsabihin may nahanap na existing category
        }
        categories.add(newCategory);
        return true;
    }

    public Category findCategoryById(String givenCategoryID) {
        for(Category c : categories) {
            if(c.getCategory_ID().equals(givenCategoryID)){
                return c;
            }
        }
        return null; //pagkawalang nahanap null i rereturn
    }

    private void defaultCategories() { //dummy values lang para sa testing
        categories.add(new Category("C001","Vitamins","Makes the body stronger"));
        categories.add(new Category("C002","Antibiotics","Medicine for bacterial infections"));
        categories.add(new Category("C003","Personal Care","Hygiene and Care Products"));
        categories.add(new Category("C004","Pain Reliever","Alleviates Pain"));
    }


}
