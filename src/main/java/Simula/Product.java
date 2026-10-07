package Simula;

import java.util.ArrayList;
import java.util.List;

public class Product {
    String productID, productName,  genericName, Brand, Unit;
    double sellingPrice;
    int reorderLevel;

    public Category category;
    public List<Batch> batch = new ArrayList<>();

}

// maya na to di ko maintindihan