package Simula;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class Inventory {

    //mga controllers
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Category> categories = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public Inventory() {
        defaultCategories();
        defaultProducts();
    }

    //Product Methods ------------------------------------------------------
    public ArrayList<Product> getProducts() {
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


    //Category Methods -------------------------------------------------------------
    public ArrayList<Category> getCategories() { return categories; }

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


    //Transaction/Stock methods -------------------------------------------------------------------------

    public ArrayList<Transaction> getTransactions() {return transactions;}

    public boolean recordSale(Sale sale) {
        if(sale ==null || sale.getTransactionDetails().isEmpty()) return false; //chceck kung null or empty

        ArrayList<TransactionDetail> productLines  = sale.getTransactionDetails(); //para sa mamaya to, pag p print na yung kada individual transaction detail

        for(TransactionDetail line : productLines) {
            Product p = line.getProduct(); //kinuha yung product sa transactionDetail na yon

            if(!products.contains(p)) return false; //pagka yung p is wala sa arraylist na products, return false
            if(p instanceof Medicine && !((Medicine) p).soldBy(sale.getEmployee())) return false; //if medicine sya tapos hindi sya sold by pharmacist/owner , yung (Medicine) p, casting sya, DOWNCASTING

            int totalNeeded = 0; //total ng isang product accross the entire transaction
            for(TransactionDetail otherLines : productLines) { //traverse yung transactionDetail uli, titignan kung may kasame yung current product
                if(otherLines.getProduct() == p)  //condition na naghahanap if may ka same product
                    totalNeeded += otherLines.getQuantity(); //if meron, i aadd dito
            }
            if(getSellableStocks(p) < totalNeeded ) return false; //pagka kulang yung sellableStock, cancel
        }

        for(TransactionDetail line : productLines) {
            reduceBatchQuantity(line.getProduct(),line.getQuantity());
        }
        transactions.add(sale);
        return  true;
    }

    private int getSellableStocks(Product p) {  //kukunin yung amount na hindi expired na product
        int total = 0;

        for(Batch b : p.getBatches()) {
            if(!b.isExpired())   total+=b.getQuantity();
        }
        return total;

    }

    private void reduceBatchQuantity(Product product, int quantity) {

        ArrayList<Batch> sortedBatch = product.getBatches();  //bagong ArrayLIst, dito lang ilalagay yung sorted na batch by expiration date
        sortedBatch.sort(Comparator.comparing(Batch::getExpirationDate)); //comaparator na object, i sosort sya by soonest expiration date

        for(Batch b : sortedBatch) {
            if(quantity ==0 ) break; //kapag zero yung nilagay na irereduce, end the loop
            if(b.isExpired() || b.getQuantity() == 0) continue; //kapag expired or walang laman yung batch, skip current batch

            int quantityToReduce = Math.min(quantity, b.getQuantity()); //pipili kung yung ibabawas ba na number ay yung quantity(eto yung nasa method mismo) or b.getquantity(pagkakunwari mas maliit na yung quantity ng nasa batch kesa sa dapat iremove, pipiliin muna to)
            b.reduceQuantity(quantityToReduce); //pagreduce na ng quantity
            quantity -= quantityToReduce; //inupdate yung quantity kung ilan yung binawas, tapos next iteration na

        }

    }

    public boolean recordPurchase(Purchase purchase) {

        if(purchase ==null || purchase.getTransactionDetails().isEmpty()) return false; //if walang laman yung purchase or if walang laman yung TransactionDetails arraylist ni Purchase
        if(!(purchase.getEmployee() instanceof  Owner)) return false; //pagka hindi si owner yung gumawa ng purchase

        ArrayList<TransactionDetail> lines = purchase.getTransactionDetails();
        ArrayList<LocalDate> expirationDates = purchase.getExpirationDates();

        for(TransactionDetail line : lines) {
            if(!products.contains(line.getProduct())) return false; //chinecheck kung nasa products ArrayList yung nasa purchase
        }

        for(int i=0; i< lines.size();i++) {
            Product product = lines.get(i).getProduct(); //kinukuha yung product ng specific na productLine sa transactionDetail
            product.addBatch(new Batch(lines.get(i).getQuantity(),product,expirationDates.get(i))); // yung addBatch na method lang to, tapos yung arguements ay yung input ni user sa menu
        }

        transactions.add(purchase);
        return true;

        }

    //dito sa baba mga dummy objects--------------------------------------------------------------------------------------------

    private void defaultProducts() {

        Product biogesic =new OverTheCounter("Biogesic","Paracetamol","Unilab","Tablet",7,50,findCategoryById("C005"));
        Product vitaminC = new OverTheCounter("Ascorbic Acid Vitamin C","Ascorbic Acid","Celine","Syrup",50,50,findCategoryById("C001"));

        biogesic.addBatch(new Batch(100,biogesic,LocalDate.parse("2026-10-10")));
        vitaminC.addBatch(new Batch(200,vitaminC,LocalDate.parse("2028-09-11")));
        biogesic.addBatch(new Batch(300,biogesic,LocalDate.parse("2026-11-11")));

        products.add(biogesic);
        products.add(vitaminC);

    }

    private void defaultCategories() { //dummy values lang para sa testing
        categories.add(new Category("Vitamins","Makes the body stronger"));
        categories.add(new Category("Antibiotics","Kills harmful bacteria"));
        categories.add(new Category("Personal Care","Hygiene and Care Products"));
        categories.add(new Category("Pain Reliever","Alleviates Pain"));
        categories.add(new Category("Antipyretic","Fever Reducer"));

    }

    public void defaultTransactions(Employee owner) { //sa start up na constructor to ilalagay, kasi kailangan na malaman na owner ang gumamit
        Product biogesic = products.get(0);
        Product vitaminC = products.get(1);

        Purchase purchase = new Purchase(owner,this); //transaction1 to
        purchase.addPurchaseLine(100,3.5,biogesic,LocalDate.parse("2026-10-10"));
        purchase.addPurchaseLine(200,3,vitaminC,LocalDate.parse("2028-09-11"));
        recordPurchase(purchase);

        purchase = new Purchase(owner,this); //transactoion2 to
        purchase.addPurchaseLine(300,3.5,biogesic,LocalDate.parse("2026-11-11"));
        recordPurchase(purchase);


    }


}
