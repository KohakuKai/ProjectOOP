package Simula;

import java.util.ArrayList;

public class InventoryController {

    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Category> categories = new ArrayList<>();

    public ArrayList<Product> viewProducts() {
        return products;
    }

    public void addProduct(Employee employee, Product newProduct) {
        products.add(newProduct);
    }

    public ArrayList<Category> viewCategories() {
        return categories;
    }



}
