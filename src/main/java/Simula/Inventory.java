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
    public boolean addProduct(Staff staff, Product newProduct) {
        if(!(staff instanceof  Owner))  return false; //pagka hindi owner gumamit
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

    public ArrayList<Product> findProductByName(String givenProductName) {

        ArrayList<Product> matchedProducts = new ArrayList<>();

        for(Product p : products) {
            if(p.getProductName().toUpperCase().contains(givenProductName)){
                matchedProducts.add(p);
            }
        }

        return matchedProducts;
    }

    public ArrayList<Product> findProductByCategory(String givenCategoryString) {

        ArrayList<Product> matchedProducts = new ArrayList<>();

        for(Product p : products) {
            if(p.getCategory().getCategory_Name().toUpperCase().contains(givenCategoryString) || //if match yung category name
            p.getCategory().getCategory_ID().toUpperCase().equals(givenCategoryString)) { //if match yung category id
                matchedProducts.add(p);
            }
        }

        return matchedProducts;

    }

    public boolean removeProduct(String productID) {
        Product product = findProductById(productID);

        if (product == null) {
            return false;
        }

        return products.remove(product);
    }


    //Category Methods -------------------------------------------------------------
    public ArrayList<Category> getCategories() { return categories; }

    //Add category
    public boolean addCategory(Staff staff, Category newCategory) {
        if(!(staff instanceof Owner)) return false; //pagka hindi owner ang gumamit
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

    public boolean removeCategory(String categoryID) {
        Category category = findCategoryById(categoryID);

        if (category == null) {
            return false;
        }

        return categories.remove(category);
    }


    //Transaction/Stock methods -------------------------------------------------------------------------

    public ArrayList<Transaction> getTransactions() {return transactions;}

    public boolean recordSale(Sale sale) {
        if(sale ==null || sale.getTransactionDetails().isEmpty()) return false; //chceck kung null or empty

        ArrayList<TransactionDetail> productLines  = sale.getTransactionDetails(); //para sa mamaya to, pag p print na yung kada individual transaction detail

        for(TransactionDetail line : productLines) {
            Product p = line.getProduct(); //kinuha yung product sa transactionDetail na yon

            if(!products.contains(p)) return false; //pagka yung p is wala sa arraylist na products, return false
            if(p instanceof Medicine && !((Medicine) p).soldBy(sale.getStaff())) return false; //if medicine sya tapos hindi sya sold by pharmacist/owner , yung (Medicine) p, casting sya, DOWNCASTING

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
        if(!(purchase.getStaff() instanceof  Owner)) return false; //pagka hindi si owner yung gumawa ng purchase

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
        Product vitaminC = new OverTheCounter("Ascorbic Acid Vitamin C","Ascorbic Acid","Celine","Syrup",5,50,findCategoryById("C001"));
        Product bioflu = new OverTheCounter("Bioflu","Paracetamol","Unilab","Tablet",10,50,findCategoryById("C005"));

        // ---------- bagong products (30) ----------
        // Pain Reliever (C004)
        Product medicol = new OverTheCounter("Medicol Advance","Ibuprofen","Dyna Drug","Tablet",9,50,findCategoryById("C004"));              // index 3
        Product alaxan = new OverTheCounter("Alaxan FR","Ibuprofen + Paracetamol","Unilab","Capsule",11,50,findCategoryById("C004"));         // index 4
        Product ponstan = new PrescriptionMedicine("Ponstan 500mg","Mefenamic Acid","Pfizer","Capsule",18,40,findCategoryById("C004"));       // index 5
        // Antipyretic (C005)
        Product calpol = new OverTheCounter("Calpol 250mg/5ml","Paracetamol","GSK","Syrup",120,20,findCategoryById("C005"));                  // index 6
        Product tempra = new OverTheCounter("Tempra Drops","Paracetamol","Taisho","Drops",150,15,findCategoryById("C005"));                   // index 7
        // Antibiotics (C002) - prescription
        Product amoxil = new PrescriptionMedicine("Amoxil 500mg","Amoxicillin","GSK","Capsule",14,60,findCategoryById("C002"));               // index 8
        //LOW STOCK ALLERT
        Product zithromax = new PrescriptionMedicine("Zithromax 500mg","Azithromycin","Pfizer","Tablet",120,80,findCategoryById("C002"));     // index 9

        Product cipro = new PrescriptionMedicine("Ciprofloxacin 500mg","Ciprofloxacin","Generic","Tablet",20,30,findCategoryById("C002"));    // index 10
        Product augmentin = new PrescriptionMedicine("Augmentin 625mg","Amoxicillin + Clavulanic Acid","GSK","Tablet",65,30,findCategoryById("C002")); // index 11
        // Vitamins (C001)
        Product conzace = new OverTheCounter("Conzace","Multivitamins + Zinc","Pfizer","Capsule",12,50,findCategoryById("C001"));             // index 12
        Product enervon = new OverTheCounter("Enervon","Multivitamins","Unilab","Tablet",8,60,findCategoryById("C001"));                      // index 13
        Product caltrate = new OverTheCounter("Caltrate","Calcium Carbonate + Vitamin D3","Pfizer","Tablet",14,40,findCategoryById("C001"));  // index 14
        Product ceelin = new OverTheCounter("Ceelin Plus","Ascorbic Acid + Zinc","Unilab","Syrup",135,20,findCategoryById("C001"));          // index 15
        // Antihistamines (C006)
        Product allerta = new OverTheCounter("Allerta 10mg","Cetirizine","Unilab","Tablet",8,50,findCategoryById("C006"));                    // index 16
        Product claritin = new OverTheCounter("Claritin 10mg","Loratadine","Bayer","Tablet",20,30,findCategoryById("C006"));                  // index 17
        // Cough and Cold (C007)
        Product neozep = new OverTheCounter("Neozep Forte","Phenylephrine + Chlorphenamine + Paracetamol","Unilab","Tablet",8,60,findCategoryById("C007")); // index 18
        Product solmux = new OverTheCounter("Solmux 500mg","Carbocisteine","Unilab","Capsule",13,50,findCategoryById("C007"));               // index 19
        Product lagundi = new OverTheCounter("Lagundi 600mg","Vitex negundo","Ascof","Tablet",10,50,findCategoryById("C007"));               // index 20

        // Digestive Health (C008) LOW STOCK TESTER
        Product kremil = new OverTheCounter("Kremil-S","Aluminum Hydroxide + Magnesium Hydroxide + Simethicone","Unilab","Tablet",8,50,findCategoryById("C008")); // index 21

        Product imodium = new OverTheCounter("Imodium 2mg","Loperamide","Johnson & Johnson","Capsule",18,30,findCategoryById("C008"));       // index 22
        Product omeprazole = new PrescriptionMedicine("Omeprazole 20mg","Omeprazole","Generic","Capsule",12,40,findCategoryById("C008"));    // index 23
        // Cardiovascular (C010) - prescription
        Product amlodipine = new PrescriptionMedicine("Norvasc 5mg","Amlodipine","Pfizer","Tablet",15,40,findCategoryById("C010"));           // index 24
        Product losartan = new PrescriptionMedicine("Losartan 50mg","Losartan","Generic","Tablet",14,40,findCategoryById("C010"));            // index 25
        // Diabetes Care (C011) - prescription
        Product metformin = new PrescriptionMedicine("Glucophage 500mg","Metformin","Merck","Tablet",7,60,findCategoryById("C011"));          // index 26
        // First Aid (C009) - essentials
        Product betadine = new Essentials("Betadine Solution 60ml","Povidone Iodine","Mundipharma","Bottle",95,20,findCategoryById("C009")); // index 27
        Product alcohol = new Essentials("Alcohol 70% 500ml","Ethyl Alcohol","Green Cross","Bottle",85,25,findCategoryById("C009"));         // index 28
        Product bandaid = new Essentials("Adhesive Bandage Strips","Adhesive Bandage","Johnson & Johnson","Box",60,20,findCategoryById("C009")); // index 29
        // Personal Care (C003) - essentials
        Product safeguard = new Essentials("Safeguard Bar Soap","Antibacterial Soap","P&G","Bar",45,30,findCategoryById("C003"));            // index 30
        // Baby Care (C012) - essentials
        Product babyPowder = new Essentials("Baby Powder","Talcum Powder","Johnson & Johnson","Bottle",110,20,findCategoryById("C012"));     // index 31


        // Medical Devices (C013) - essentials //LOW STOCK TEST
        Product thermometer = new Essentials("Digital Thermometer","Digital Thermometer","Omron","Piece",350,30,findCategoryById("C013"));   // index 32

        biogesic.addBatch(new Batch(100,biogesic,LocalDate.parse("2026-10-10")));  //EXPIRED na, para ma-test ang removeExpiredBatches
        imodium.addBatch(new Batch(20,imodium,LocalDate.parse("2026-08-30")));   // EXPIRED

        thermometer.addBatch(new Batch(5,thermometer,LocalDate.parse("2030-01-01"))); // mababa sa reorder level (10)
        kremil.addBatch(new Batch(100,kremil,LocalDate.parse("2026-11-15")));        // malapit nang ma-expire para ma-test ang near expiry
        zithromax.addBatch(new Batch(10,zithromax,LocalDate.parse("2027-11-30")));   // mababa sa reorder level (20) para ma-test ang low stock

        // ---------- batches ng bagong products ----------
        vitaminC.addBatch(new Batch(200,vitaminC,LocalDate.parse("2025-09-11")));
        biogesic.addBatch(new Batch(300,biogesic,LocalDate.parse("2027-11-11")));
        bioflu.addBatch(new Batch(200, bioflu,LocalDate.parse("2028-07-11")));
        medicol.addBatch(new Batch(120,medicol,LocalDate.parse("2028-03-15")));
        alaxan.addBatch(new Batch(150,alaxan,LocalDate.parse("2028-06-20")));
        ponstan.addBatch(new Batch(80,ponstan,LocalDate.parse("2028-01-10")));
        calpol.addBatch(new Batch(40,calpol,LocalDate.parse("2027-12-01")));
        tempra.addBatch(new Batch(30,tempra,LocalDate.parse("2027-09-30")));
        amoxil.addBatch(new Batch(100,amoxil,LocalDate.parse("2028-02-28")));
        cipro.addBatch(new Batch(60,cipro,LocalDate.parse("2028-04-15")));
        augmentin.addBatch(new Batch(50,augmentin,LocalDate.parse("2027-10-31")));
        conzace.addBatch(new Batch(90,conzace,LocalDate.parse("2028-05-05")));
        enervon.addBatch(new Batch(120,enervon,LocalDate.parse("2028-08-18")));
        caltrate.addBatch(new Batch(70,caltrate,LocalDate.parse("2028-07-07")));
        ceelin.addBatch(new Batch(35,ceelin,LocalDate.parse("2027-08-12")));
        allerta.addBatch(new Batch(100,allerta,LocalDate.parse("2028-09-09")));
        claritin.addBatch(new Batch(50,claritin,LocalDate.parse("2028-11-11")));
        neozep.addBatch(new Batch(150,neozep,LocalDate.parse("2028-02-14")));
        solmux.addBatch(new Batch(90,solmux,LocalDate.parse("2028-04-04")));
        lagundi.addBatch(new Batch(80,lagundi,LocalDate.parse("2028-10-10")));
        imodium.addBatch(new Batch(40,imodium,LocalDate.parse("2028-03-03")));
        omeprazole.addBatch(new Batch(70,omeprazole,LocalDate.parse("2028-06-06")));
        amlodipine.addBatch(new Batch(60,amlodipine,LocalDate.parse("2028-12-12")));
        losartan.addBatch(new Batch(60,losartan,LocalDate.parse("2028-12-01")));
        metformin.addBatch(new Batch(100,metformin,LocalDate.parse("2029-01-15")));
        betadine.addBatch(new Batch(30,betadine,LocalDate.parse("2028-03-30")));
        alcohol.addBatch(new Batch(40,alcohol,LocalDate.parse("2028-05-25")));
        bandaid.addBatch(new Batch(50,bandaid,LocalDate.parse("2029-02-20")));
        safeguard.addBatch(new Batch(80,safeguard,LocalDate.parse("2029-06-01")));
        babyPowder.addBatch(new Batch(30,babyPowder,LocalDate.parse("2029-03-03")));


        products.add(biogesic);
        products.add(vitaminC);
        products.add(bioflu);

        // dapat sunod-sunod ito, kasi index ang gamit sa defaultTransactions
        products.add(medicol);      // 3
        products.add(alaxan);       // 4
        products.add(ponstan);      // 5
        products.add(calpol);       // 6
        products.add(tempra);       // 7
        products.add(amoxil);       // 8
        products.add(zithromax);    // 9
        products.add(cipro);        // 10
        products.add(augmentin);    // 11
        products.add(conzace);      // 12
        products.add(enervon);      // 13
        products.add(caltrate);     // 14
        products.add(ceelin);       // 15
        products.add(allerta);      // 16
        products.add(claritin);     // 17
        products.add(neozep);       // 18
        products.add(solmux);       // 19
        products.add(lagundi);      // 20
        products.add(kremil);       // 21
        products.add(imodium);      // 22
        products.add(omeprazole);   // 23
        products.add(amlodipine);   // 24
        products.add(losartan);     // 25
        products.add(metformin);    // 26
        products.add(betadine);     // 27
        products.add(alcohol);      // 28
        products.add(bandaid);      // 29
        products.add(safeguard);    // 30
        products.add(babyPowder);   // 31
        products.add(thermometer);  // 32

    }

    private void defaultCategories() { //dummy values lang para sa testing
        categories.add(new Category("Vitamins","Makes the body stronger"));
        categories.add(new Category("Antibiotics","Kills harmful bacteria"));
        categories.add(new Category("Personal Care","Hygiene and Care Products"));
        categories.add(new Category("Pain Reliever","Alleviates Pain"));
        categories.add(new Category("Antipyretic","Fever Reducer"));

        // bagong categories (C006 - C013)
        categories.add(new Category("Antihistamines","Relieves allergy symptoms"));        // C006
        categories.add(new Category("Cough and Cold","Relieves cough, colds and flu"));    // C007
        categories.add(new Category("Digestive Health","Antacids and stomach medicines")); // C008
        categories.add(new Category("First Aid","Wound care and antiseptics"));            // C009
        categories.add(new Category("Cardiovascular","Heart and blood pressure medicines"));// C010
        categories.add(new Category("Diabetes Care","Blood sugar management"));            // C011
        categories.add(new Category("Baby Care","Products for infants and babies"));       // C012
        categories.add(new Category("Medical Devices","Health monitoring devices"));       // C013

    }

    public void defaultTransactions(Staff owner) { //sa start up na constructor to ilalagay, kasi kailangan na malaman na owner ang gumamit
        Product biogesic = products.get(0);
        Product vitaminC = products.get(1);
        Product bioflu = products.get(2);

        Purchase purchase = new Purchase(owner,this); //transaction1 to
        purchase.addPurchaseLine(100,3.5,biogesic,LocalDate.parse("2026-10-10"));
        purchase.addPurchaseLine(200,3,vitaminC,LocalDate.parse("2028-09-11"));
        recordPurchase(purchase);

        purchase = new Purchase(owner,this); //transactoion2 to
        purchase.addPurchaseLine(300,3.5,biogesic,LocalDate.parse("2026-11-11"));
        recordPurchase(purchase);

        purchase = new Purchase(owner, this) ;
        purchase.addPurchaseLine(200,5, bioflu,LocalDate.parse("2028-07-11"));
        recordPurchase(purchase);

        // ---------- bagong purchases (kapareho ng quantity at expiry ng batches sa taas) ----------
        purchase = new Purchase(owner, this); // pain reliever at fever
        purchase.addPurchaseLine(120,6,products.get(3),LocalDate.parse("2028-03-15"));    // Medicol
        purchase.addPurchaseLine(150,7.5,products.get(4),LocalDate.parse("2028-06-20"));  // Alaxan
        purchase.addPurchaseLine(80,12,products.get(5),LocalDate.parse("2028-01-10"));    // Ponstan
        purchase.addPurchaseLine(40,85,products.get(6),LocalDate.parse("2027-12-01"));    // Calpol
        purchase.addPurchaseLine(30,105,products.get(7),LocalDate.parse("2027-09-30"));   // Tempra
        recordPurchase(purchase);

        purchase = new Purchase(owner, this); // antibiotics
        purchase.addPurchaseLine(100,9,products.get(8),LocalDate.parse("2028-02-28"));    // Amoxil
        purchase.addPurchaseLine(10,85,products.get(9),LocalDate.parse("2027-11-30"));    // Zithromax
        purchase.addPurchaseLine(60,13,products.get(10),LocalDate.parse("2028-04-15"));   // Ciprofloxacin
        purchase.addPurchaseLine(50,45,products.get(11),LocalDate.parse("2027-10-31"));   // Augmentin
        recordPurchase(purchase);

        purchase = new Purchase(owner, this); // vitamins at antihistamines
        purchase.addPurchaseLine(90,8,products.get(12),LocalDate.parse("2028-05-05"));    // Conzace
        purchase.addPurchaseLine(120,5.5,products.get(13),LocalDate.parse("2028-08-18")); // Enervon
        purchase.addPurchaseLine(70,9.5,products.get(14),LocalDate.parse("2028-07-07"));  // Caltrate
        purchase.addPurchaseLine(35,95,products.get(15),LocalDate.parse("2027-08-12"));   // Ceelin Plus
        purchase.addPurchaseLine(100,5,products.get(16),LocalDate.parse("2028-09-09"));   // Allerta
        purchase.addPurchaseLine(50,14,products.get(17),LocalDate.parse("2028-11-11"));   // Claritin
        recordPurchase(purchase);

        purchase = new Purchase(owner, this); // cough/cold, digestive, heart, diabetes
        purchase.addPurchaseLine(150,5,products.get(18),LocalDate.parse("2028-02-14"));   // Neozep
        purchase.addPurchaseLine(90,9,products.get(19),LocalDate.parse("2028-04-04"));    // Solmux
        purchase.addPurchaseLine(80,6.5,products.get(20),LocalDate.parse("2028-10-10"));  // Lagundi
        purchase.addPurchaseLine(100,5,products.get(21),LocalDate.parse("2026-11-15"));   // Kremil-S
        purchase.addPurchaseLine(40,12,products.get(22),LocalDate.parse("2028-03-03"));   // Imodium
        purchase.addPurchaseLine(20,12,products.get(22),LocalDate.parse("2026-08-30"));   // Imodium (expired)
        purchase.addPurchaseLine(70,8,products.get(23),LocalDate.parse("2028-06-06"));    // Omeprazole
        purchase.addPurchaseLine(60,10,products.get(24),LocalDate.parse("2028-12-12"));   // Norvasc
        purchase.addPurchaseLine(60,9.5,products.get(25),LocalDate.parse("2028-12-01"));  // Losartan
        purchase.addPurchaseLine(100,4.5,products.get(26),LocalDate.parse("2029-01-15")); // Glucophage
        recordPurchase(purchase);

        purchase = new Purchase(owner, this); // essentials
        purchase.addPurchaseLine(30,65,products.get(27),LocalDate.parse("2028-03-30"));   // Betadine
        purchase.addPurchaseLine(40,58,products.get(28),LocalDate.parse("2028-05-25"));   // Alcohol
        purchase.addPurchaseLine(50,42,products.get(29),LocalDate.parse("2029-02-20"));   // Bandage
        purchase.addPurchaseLine(80,30,products.get(30),LocalDate.parse("2029-06-01"));   // Safeguard
        purchase.addPurchaseLine(30,75,products.get(31),LocalDate.parse("2029-03-03"));   // Baby Powder
        purchase.addPurchaseLine(5,250,products.get(32),LocalDate.parse("2030-01-01"));   // Thermometer
        recordPurchase(purchase);

    }


}



