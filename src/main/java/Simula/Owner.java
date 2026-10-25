package Simula;

import java.time.LocalDate;
import java.util.ArrayList;

public class Owner extends Employee {


    private final StartUp manager;

    public Owner(String name, StartUp manager, String password, String username,Inventory inventory) {
        super(name, "Owner", password, username, inventory);
        this.manager = manager;

    }


    @Override
    public void showDashboard() {
        while (true) {
            tag();
            System.out.println(border);
            System.out.println("\t\t\t\tPHARMACY");
            System.out.println(border);

            displayStockAlerts();

            System.out.print("PERSON\n"+
                    "\t[1] Manage Employees\n" +
                    "INVENTORY\n"+
                    "\t[2] Manage Products\n" +
                    "\t[3] Manage Categories\n" +
                    "\t[4] Manage Stocks\n" +
                    "TRANSACT\n" +
                    "\t[5] Sell Product\n" +
                    "\t[6] Purchase Product\n" +
                    "\t[7] View Transaction History\n "+
                    "ACCOUNT\n" +
                    "\t[8] Change Password\n" +
                    "\t[9] View My Profile\n" +
                    "\t[10] Logout\n" +
                    border + "\n" +
                    "Choice: ");
            int choice = userIntInput();
            System.out.println(border+"\n");

            switch (choice) {
                case 1: {

                    tag();
                    System.out.println(border);
                    System.out.printf("%27s%n", "MANAGE EMPLOYEES");
                    System.out.println(border);
                    System.out.print("\t[1] Create Employee\n" +
                            "\t[2] View Employee\n" +
                            "\t[3] Delete Employee\n" +
                            "\t[0] Exit\n" +
                            border+ "\n" +
                            "Choice: ");
                    int temp = userIntInput();
                    System.out.println(border+"\n");
                    switch (temp) {
                        case 1: {
                            createEmployee();
                            pause();
                            break;
                        }
                        case 2: {
                            displayEmployees();
                            pause();
                            break;
                        }
                        case 3: {
                            deleteEmployee();
                            pause();
                            break;
                        }
                        case 0: {
                            break;
                        }
                        default:{
                            System.out.println(mborder);
                            System.out.println("Invalid input. Try Again");
                            System.out.println(mborder);
                            pause();
                            break;
                        }
                    }
                    break;
                }

                case 2 :{

                    tag();
                    System.out.println(border);
                    System.out.printf("%27s%n", "MANAGE PRODUCTS");
                    System.out.println(border);
                    System.out.print("\t[1]Create Product\n" +
                            "\t[2]Display Products\n" +
                            "\t[3]Delete Product\n" +
                            "\t[4]Search Product\n" +
                            "\t[0] Exit\n" + border +
                            "\nChoice:");
                    int choice2 = userIntInput();
                    System.out.println(border+"\n");

                    //case 3 : editProductDetails(); pause(); break;

                    switch(choice2) {

                        case 1 : createProduct(); pause(); break;
                        case 2 : displayProducts(); pause(); break;
                        case 3 : deleteProduct(); pause(); break;
                        case 4 : searchProduct(); pause(); break;
                        case 0 : break;
                        default : {
                            System.out.println(mborder);
                            System.out.println("Invalid input. Try Again");
                            System.out.println(mborder);
                            pause();
                            break;
                        }
                    }
                    break;
                }

                case 3 : {

                    tag();
                    System.out.println(border);
                    System.out.printf("%27s%n", "MANAGE CATEGORIES");
                    System.out.println(border);
                    System.out.print("\t[1]Add Category\n" +
                            "\t[2]Display Categories\n" +
                            "\t[3]Delete Category\n" +
                            "\t[0] Exit\n" + border +
                            "\nChoice:");
                    int choice2 = userIntInput();
                    System.out.println(border+"\n");

                    switch(choice2) {

                        case 1 : addCategory(); pause(); break;
                        case 2 : displayCategories(); pause(); break;
                        case 3 : deleteCategory(); pause(); break;
                        case 0 : break;
                        default : {
                            System.out.println(mborder);
                            System.out.println("Invalid input. Try Again");
                            System.out.println(mborder);
                            pause();
                            break;
                        }
                    }
                    break;

                }
                case 4:{
                    tag();
                    System.out.println(border);
                    System.out.printf("%27s%n", "MANAGE STOCKS");
                    System.out.println(border);
                    System.out.print("\t[1]View a Product's Batches\n" +
                            "\t[2]Add Stock(Manual Add)\n" +
                            "\t[3]Reduce Stock(Manual Remove)\n" +
                            "\t[4]Remove All Expired Batches\n"+
                            "\t[0] Exit\n"+ border +
                            "\nChoice: ");
                    int choice4 = userIntInput();
                    System.out.println(border+"\n");

                    switch(choice4){
                        case 1:{
                            viewProductBatchDetails();
                            pause();
                            break;
                        }
                        case 2:{
                            addStock();
                            pause();
                            break;
                        }
                        case 3:{
                            reduceStock();
                            pause();
                            break;
                        }

                        case 4 : {

                            System.out.println(border);
                            System.out.println("\t[Removed] "+removeAllExpiredBatches() + " Batches");
                            System.out.println(border);
                            pause();
                            break;
                        }

                        case 0: {
                            break;
                        }

                        default:{
                            System.out.println(mborder);
                            System.out.println("Invalid input. Try Again");
                            System.out.println(mborder);
                            pause();
                            break;
                        }

                    }
                    break;
                }
                case 5:{
                    sellProduct();
                    pause();
                    break;
                }

                case 6 : {
                    purchaseProduct();
                    pause();
                    break;
                }

                case 7:{
                    viewTransactionHistory();
                    pause();
                    break;
                }

                case 8:{
                    changePassword();
                    pause();
                    break;
                }

                case 9:{
                    displayProfile();
                    pause();
                    break;
                }

                case 10: {
                    System.out.println(mborder);
                    System.out.println("Logging out...");
                    System.out.println(mborder);
                    return;
                }

                default: {
                    System.out.println(mborder);
                    System.out.println("Invalid choice. Please try again.");
                    System.out.println(mborder);
                    pause();
                    break;
                }
            }
        }
    }

    private String inputNames(String input) {
        while(true) {
            System.out.print(input);
            String name = in.nextLine().trim();

            if(name.matches("[a-zA-Z ]+")) {
                return name;
            }

            System.out.println("Invalid input...");
        }
    }

    //create employee ng owner yan ah
    private void createEmployee() {
        tag();
        System.out.println(border);
        System.out.printf("%27s%n", "CREATE EMPLOYEE");
        System.out.println(border);
        System.out.println(line);
        String firstName = inputNames("Enter first name: ");
        String middleName = inputNames("Enter middle name: ");
        String lastName = inputNames("Enter last name: ");

        if (firstName.isEmpty() || lastName.isEmpty() ) {
            System.out.println("First name and last name are required!");
            return;
        }

        String name = firstName
                + (middleName.isEmpty() ? "" : " " + middleName)
                + " " + lastName;

        System.out.print("Enter employee username: ");
        String username = in.nextLine().trim();

        String password;

        while(true) {
            System.out.println(line);
            System.out.print("Enter employee password: ");
            password = in.nextLine();
            System.out.println(line);

            if (!password.matches(".*[A-Z].*")) {
                System.out.println(mborder);
                System.out.println("Password mush have at least one capital letter.");
                System.out.println(mborder);
            }
            else if (!password.matches(".*[0-9].*")) {
                System.out.println(mborder);
                System.out.println("Password must have at least one number.");
                System.out.println(mborder);
            }
            else if (!password.matches(".*[^a-zA-Z0-9\\s].*")) {
                System.out.println(mborder);
                System.out.println("Password must have at least one special character");
                System.out.println(mborder);
            }
            else if (password.length() < 10) {
                System.out.println(mborder);
                System.out.println("Password must have at least 10 characters");
                System.out.println(mborder);
            }
            else {
                break;
            }

        }

        for (Employee employee : manager.getEmployees()) {
            if (employee.getName().equalsIgnoreCase(name)
                    && employee.getUsername().equalsIgnoreCase(username)){
                System.out.println(line+"\n"+border+"\n");
                System.out.println(mborder);
                System.out.println("Account already exists.");
                System.out.println(mborder);
                return;
            }
        }

        System.out.println(line);
        System.out.println("Assign position:");
        System.out.println("\t[1] Pharmacist");
        System.out.println("\t[2] Staff");
        System.out.println(line);
        System.out.println(border);
        System.out.print("Choice: ");
        int choice = userIntInput();
        System.out.println(border+"\n");

        Employee newEmployee = createUserByChoice(choice, name, username, password);
        if (newEmployee == null) {
            System.out.println(mborder);
            System.out.println("Invalid position. Employee creation cancelled.");
            System.out.println(mborder);
            return;
        }

        manager.getEmployees().add(newEmployee);
        System.out.println(mborder);
        System.out.println("Employee created successfully!");
        System.out.println("Employee ID: " + newEmployee.getEmpID());
        System.out.println("Position: " + newEmployee.getPosition());
        System.out.println(mborder);
    }
    private void displayEmployees() {
        tag();
        System.out.println(border);
        System.out.printf("%27s%n", "DISPLAY EMPLOYEES");
        System.out.println(border);
        System.out.printf("%-10s  %-26s  %-10s%n",
                "ID NO.", "NAME", "POSITION");
        System.out.println(line);
        for (Employee employee : manager.getEmployees()) {
            System.out.printf("%-10s  %-26s  %-10s%n",
                    employee.getEmpID(),
                    employee.getName(),
                    employee.getPosition());
        }
        System.out.println(line);
        System.out.println(border);
    }

    //delete employee
    private void deleteEmployee() {
        tag();
        System.out.println(border);
        System.out.printf("%27s%n", "DELETE EMPLOYEE");
        System.out.println(border);
        System.out.printf("%-7s  %-15s  %-10s%n",
                "ID NO.", "NAME", "POSITION");
        System.out.println(line);
        for (Employee employee : manager.getEmployees()) {
            System.out.printf("%-7s  %-15s  %-10s%n",
                    employee.getEmpID(),
                    employee.getName(),
                    employee.getPosition());
        }
        System.out.println(line+"\n"+border);

        System.out.print("Enter employee ID to delete: ");
        String empID = in.nextLine().trim();
        System.out.println(border);
        System.out.println();

        for (Employee employee : manager.getEmployees()) {
            if (employee.getEmpID().equals(empID)) {

                if (employee == this) {
                    System.out.println(mborder);
                    System.out.println("You cannot delete your own account.");
                    System.out.println(mborder);
                    return;
                }

                manager.getEmployees().remove(employee);
                System.out.println(mborder);
                System.out.println("Employee deleted successfully!");
                System.out.println(mborder);
                return;
            }
        }

        System.out.println(mborder);
        System.out.println("Employee ID not found.");
        System.out.println(mborder);
    }

    // pinagpasahan ng input. taga gawa object
    private Employee createUserByChoice(int choice, String name,
                                        String username, String password) {
        switch (choice) {
            case 1:
                return new Pharmacist(name, password, username,inventory);

            case 2:
                return new Staff(name, password, username,inventory);

            default:
                return null;
        }
    }

    //Product Methods
    private void createProduct() {

        tag();
        System.out.println(border);
        System.out.printf("%27s%n", "CREATE PRODUCT");
        System.out.println(border);

        System.out.print("PRODUCT TYPE\n" +
                "\t[1] Medicine\n" +
                "\t[2] Essentials\n" +
                "\t[0]Exit\n" +line+ "\nChoice: ");
        int choice = userIntInput();
        System.out.println(line);
        if (choice == 0) {
            return;
        }

        if (choice != 1 && choice != 2) {
            System.out.println(mborder);
            System.out.println("Invalid product type!");
            System.out.println(mborder);
            return;
        }

        System.out.println(line);
        System.out.print("Product Name: ");
        String productName = in.nextLine().trim();

        String genericName = "";

        if (choice == 1) { //walang generic na attribute for essentials
            System.out.print("Generic Name: ");
            genericName = in.nextLine().trim();
        }

        System.out.print("Brand: ");
        String brand = in.nextLine().trim();


        System.out.print("Unit: ");
        String unit = in.nextLine().trim();


        System.out.print("Selling Price: ");
        double sellingPrice = userDoubleInput();

        System.out.print("Reorder Level: ");
        int reorderLevel = userIntInput();

        System.out.print("Category ID: ");
        String categoryID = in.nextLine().trim().toUpperCase();

        Category category = manager.getInventory().findCategoryById(categoryID); //dito hinanap yugn actual category object using String CategoryID

        if( productName.isEmpty() || sellingPrice<=0 || reorderLevel <=0 || category ==null) { //validation kung tama ba yung details nilagay sa fields
            System.out.println(mborder);
            System.out.println("Invalid Input in one of the Fields. Please Try Again.");
            System.out.println(mborder);
            return;
        }

        Product newProduct = null;
        switch(choice) {

            case 1 : {

                System.out.print(line+"\nMedicine Type:\n" +
                        "\t[1]Over-The-Counter\n" +
                        "\t[2]Prescription Medicine\n" +
                        "\t[3]Exit\n"+line+"\nChoice: ");
                        //if medicine papapiliin kung OTP or PRESCRIPTION
                int choice2  = userIntInput();
                System.out.println(line);

                switch(choice2) {

                    case 1: {
                         newProduct = new OverTheCounter(productName,genericName,brand,unit,sellingPrice,reorderLevel,category);
                        System.out.println(border);
                        break;
                    }
                    case 2: {
                         newProduct = new PrescriptionMedicine(productName,genericName,brand,unit,sellingPrice,reorderLevel,category);
                        System.out.println(border);
                         break;
                    }
                    case 3: {
                        System.out.println(border);
                        System.out.println("\n" +mborder);
                        System.out.println("Task Cancelled!");
                        System.out.println(mborder);
                        break;
                    }
                    default: {
                        System.out.println(mborder);
                        System.out.print("Invalid choice. Please try again.");
                        System.out.println(mborder);
                        break;
                    }
                }
                break;
            }
            case 2 : { //kung Essentials, diretso create na
                 newProduct = new Essentials(productName,genericName,brand,unit,sellingPrice,reorderLevel,category);
                System.out.println(border);
                break;
            }
            case 3 : {
                System.out.println(border);
                System.out.println("\n" +mborder);
                System.out.println("Task Cancelled!");
                System.out.println(mborder);
                break;
            }
            default: {
                System.out.println(mborder);
                System.out.print("Invalid choice. Please try again.");
                System.out.println(mborder);
                break;
            }
        }

        if(newProduct!= null) {
            if (manager.getInventory().addProduct(this, newProduct)) {
                System.out.println("\n"+mborder);
                System.out.println("Product Added Successfully");
                System.out.println(mborder);
            } else {
                System.out.println(mborder);
                System.out.println("Unable to add the Product");
                System.out.println(mborder);
            }
        }

    }


    //methods ng Category
    private void addCategory() {

        tag();
        System.out.println(border);
        System.out.printf("%27s%n", "ADD CATEGORY");
        System.out.println(border);

        System.out.print("Category Name: ");
        String categoryName = in.nextLine().trim();

        System.out.print("Category Description: ");
        String description = in.nextLine().trim();
        System.out.println(border);

        if( categoryName.isEmpty()) {
            System.out.println("\n"+mborder);
            System.out.println("Category ID and Category Name are Required!");
            System.out.println(mborder);
            return;
        }

        Category newCategory = new Category(categoryName,description); //dito gumawa ng bagong object

        if(manager.getInventory().addCategory(this,newCategory)) { //dito pinasok yung object(eto yung inventory class)
            System.out.println("\n" + mborder + "\nCategory Added Successfully!" +"\n"+mborder);
        }
        else {
            System.out.println(mborder);
            System.out.println("Unable to Add the Category!");
            System.out.println(mborder);
        }

    }


    private void addBatch(){

        tag();
        System.out.println(border);
        System.out.printf("%27s%n", "ADD BATCH");
        System.out.println(border);
        System.out.print("Enter Product ID: ");
        String ProductID = in.nextLine().trim().toUpperCase();

        //hahanapin yung product gamit product id
        Product product = manager.getInventory().findProductById(ProductID);

        if(product == null){
            System.out.println(border +"\n");
            System.out.println(mborder);
            System.out.println("Unable to find product!");
            System.out.println(mborder);
            return;
        }

        //pag initialize ng variables

        System.out.print("Enter quantity: ");
        int quantity = userIntInput();
        System.out.print("Enter year of expiration: ");
        int yearExpiration = userIntInput();
        System.out.print("Enter month of expiration: ");
        int monthExpiration = userIntInput();
        System.out.print("Enter day of expiration: ");
        int dayExpiration = userIntInput();
        System.out.println(border);

        if(monthExpiration<1 || dayExpiration <1 || monthExpiration > 12 || dayExpiration >31) {
            System.out.println("\n"+ mborder);
            System.out.println("Invalid Date");
            System.out.println(mborder);
            return;
        }

        if(quantity<=0) {
            System.out.println("\n"+ mborder);
            System.out.println("Invalid Quantity");
            System.out.println(mborder);
            return;
        }

        Batch addbatch = new Batch( quantity, product, LocalDate.of(yearExpiration, monthExpiration, dayExpiration));
        if(product.addBatch(addbatch)) {
            System.out.println("\n"+mborder);
            System.out.println("Batch added successfully!");
            System.out.println(mborder);
        }
        else{
            System.out.println("\n"+mborder);
            System.out.println("Failed to add new batch");
            System.out.println(mborder);
        }
    }

    private void addStock() {
        tag();
        System.out.println(border);
        System.out.printf("%27s%n", "ADD STOCK");
        System.out.println(border);
        System.out.print("Enter Product ID: ");
        String productID = in.nextLine().trim().toUpperCase();

        Product product = manager.getInventory()
                .findProductById(productID);

        if (product == null) {
            System.out.println(border);
            System.out.println("\n"+mborder);
            System.out.println("Product not found!");
            System.out.println(mborder);
            return;
        }

        System.out.print("Enter Batch ID: ");
        String batchID = in.nextLine().trim().toUpperCase();

        Batch batch = product.findBatchById(batchID);

        if (batch == null) {
            System.out.println(border);
            System.out.println("\n"+mborder);
            System.out.println("Batch not found!");
            System.out.println(mborder);
            return;
        }

        System.out.print("Enter quantity to add: ");
        int amount = userIntInput();
        System.out.println(border);

        if(batch.increaseQuantity(amount)) {
            System.out.println(border);
            System.out.println("\n"+mborder);
            System.out.println("Stock Increased Successfully!");
            System.out.println(mborder);
        }
        else {
            System.out.println(border);
            System.out.println("\n"+mborder);
            System.out.println("Invalid Amount.");
            System.out.println(mborder);
        }
    }

    private void reduceStock() {
        tag();
        System.out.println(border);
        System.out.printf("%27s%n", "REDUCE STOCK");
        System.out.println(border);
        System.out.print("Enter Product ID: ");
        String productID = in.nextLine().trim().toUpperCase();

        Product product = manager.getInventory()
                .findProductById(productID);

        if (product == null) {
            System.out.println(border);
            System.out.println("\n"+mborder);
            System.out.println("Product not found!");
            System.out.println(mborder);
            return;
        }

        System.out.print("Enter Batch ID: ");
        String batchID = in.nextLine().trim().toUpperCase();

        Batch batch = product.findBatchById(batchID);

        if (batch == null) {
            System.out.println(border);
            System.out.println("\n"+mborder);
            System.out.println("Batch not found!");
            System.out.println(mborder);
            return;
        }

        System.out.print("Enter quantity to reduce: ");
        int amount = userIntInput();

        if (batch.reduceQuantity(amount)) {
            System.out.println(border);
            System.out.println("\n"+mborder);
            System.out.println("Stock Reduced Succesfully!");
            System.out.println(mborder);
        }
        else{
            System.out.println(border);
            System.out.println("\n"+mborder);
            System.out.println("Invalid Amount.");
            System.out.println(mborder);
        }
    }

    private void purchaseProduct() {

        tag();
        System.out.println("\n" + border);
        System.out.printf("%27s%n", "PURCHASE PRODUCT");
        System.out.println(border);

        Purchase purchase = new Purchase(this, inventory);
        boolean end = false;

        while(!end) {//Loop to, add lang ng add ng product sa checkout hanggang mag input ng 0, tapos don matatapos
            System.out.print("\t[1]Input Product ID to Purchase\n"+
                    "\t[2]Search Product Name\n" +
                    "\t[3]Checkout\n" +
                    border + "\nChoice:");
            int choice = userIntInput();
            System.out.println(border);

            if(choice == 2) {
                if(!searchProductByName())
                    continue;
            }

            else if (choice == 3) {
                break;
            }

            else if(choice!= 1) {
                System.out.println("Invalid Input!");
                continue;
            }

            System.out.print("Product ID(Enter 0 to end): ");
            String productID = in.nextLine().trim().toUpperCase();
            if(productID.equals("0")) {
                end = true;
                break;
            }

            Product product = inventory.findProductById(productID);

            if(product ==null ) {
                System.out.println(mborder);
                System.out.println("Product not Found!");
                System.out.println(mborder);
                continue;
            }

            System.out.print("Quantity: ");
            int quantity = userIntInput();

            if(quantity<=0) {
                System.out.println(mborder);
                System.out.println("Invalid Quantity");
                System.out.println(mborder);
                continue;
            }

            double unitCost = product.getSellingPrice();

            System.out.print("Expiration Year: ");
            int year = userIntInput();

            System.out.print("Expiration Month: ");
            int month = userIntInput();

            System.out.print("Expiration day: ");
            int day = userIntInput();


            if(month<1 || month>12 || day <1 || day > 31) {
                System.out.println(mborder);
                System.out.println("Invalid Date");
                System.out.println(mborder);
                continue;
            }

            LocalDate expiration = LocalDate.of(year,month,day);
            LocalDate minimum = LocalDate.now().plusYears(3);

            if(expiration.isBefore(minimum)) {
                System.out.println(mborder);
                System.out.println("Expiration date must be at least 3 years from today");
                System.out.println(mborder);
                continue;
            }

            if(purchase.addPurchaseLine(quantity,unitCost,product,expiration)) { //pagg add eto sa TransactionDetail ArrayList
                System.out.println("\n" + mborder + "Added to Checkout!" + mborder +"\n");
            }
            else {
                System.out.println(mborder);
                System.out.println("Input Failed! Invalid Quantity, Cost, or Expiration Date");
                System.out.println(mborder);
            }

            System.out.println(border);

        }

        if(purchase.getTransactionDetails().isEmpty()) { //kapag 0 agad pinindot at walang na add
            System.out.println(mborder);
            System.out.println("No Products Added");
            System.out.println(mborder);
            return;
        }

        if(!inventory.recordPurchase(purchase)) { //pagrecord na ng purchase, pag d nag go through mag eerror
            System.out.println(mborder);
            System.out.println("Purchased Failed");
            System.out.println(mborder);
            return;
        }

        ArrayList <TransactionDetail> lines  = purchase.getTransactionDetails(); //para lang to sa pagn print ng receipt
        ArrayList<LocalDate> expirationDates = purchase.getExpirationDates(); //para lang to sa pag print ng receipt

        System.out.println(border);
        System.out.printf("%27s%n", "RECEIPT");
        System.out.println("Transaction ID: "+ purchase.getTransactionID());
        System.out.println("Transaction Type: "+ purchase.getTransactionType());
        System.out.println("Purchase No.: " + purchase.getPurchaseOrderNo());

        System.out.println("\n" + border);
        for (int i=0; i<lines.size();i++) { //etong for loop nato para lang sa individual products, pinapakita yung Product name | quantity x price = subtotal | expirationDate
            TransactionDetail line = lines.get(i);
            System.out.println( line.getProduct().getProductName() + " | " + line.getQuantity() + " x " + line.getUnitPrice() + " = " + line.getSubTotal() + " | Exp:" + expirationDates.get(i) +"");
        }

        System.out.println(mborder);

        System.out.printf("\nSubtotal: %.2f%n", purchase.getSubTotalPrice());
        System.out.printf("VAT(12%%): %.2f%n", purchase.getVATAmount());
        System.out.printf("Bulk Discount(20%%): %.2f%n", purchase.getSubTotalPrice()*purchase.getBULKDISCOUNT());
        System.out.printf("Total: %.2f%n%n", purchase.getTotalWithVAT() - (purchase.getSubTotalPrice()*purchase.getBULKDISCOUNT()));

    }

    private void deleteProduct() {
        tag();
        System.out.println(border);
        System.out.printf("%31s%n", "DELETE PRODUCT");
        System.out.println(border);

        while (true) {
            System.out.println("\t[1] Input Product ID to Delete");
            System.out.println("\t[2] Search Product Name");
            System.out.println("\t[0] Exit");
            System.out.println(border);
            System.out.print("Choice: ");

            int choice = userIntInput();
            System.out.println(border);

            switch (choice) {
                case 1: {
                    break;
                }

                case 2: {
                    if (!searchProductByName()) {
                        continue;
                    }
                    break;
                }

                case 0 : {
                    return;
                }
                default: {
                    System.out.println(mborder);
                    System.out.println("Invalid Choice! Try again.");
                    System.out.println(mborder);
                    continue;
                }
            }

            while (true) {
                System.out.println("Enter Product ID [Enter 0 to Return]: ");
                String productID = in.nextLine().trim().toUpperCase();
                System.out.println(border);

                if (productID.equals("0")) {
                    break;
                }

                if (productID.isEmpty()) {
                    System.out.println(mborder);
                    System.out.println("Product ID cannot be empty.");
                    System.out.println(mborder);
                }
                else if (inventory.removeProduct(productID)) {
                    System.out.println(mborder);
                    System.out.println("Product Deleted Successfully");
                    System.out.println(mborder);
                    return;
                }
                else {
                    System.out.println(mborder);
                    System.out.println("Product ID not found! Try again.");
                    System.out.println(mborder);
                }
            }
        }

    }

    /*
    System.out.print("Enter Product ID: ");
        String productID = in.nextLine().trim().toUpperCase();
        System.out.println(border);

        if (inventory.removeProduct(productID)) {
            System.out.println("\n"+mborder);
            System.out.println("Product Deleted Successfully!");
            System.out.println(mborder);
        } else {
            System.out.println("\n"+mborder);
            System.out.println("Product ID not found!");
            System.out.println(mborder);
        }
     */

    private void deleteCategory() {
        tag();
        System.out.println(border);
        System.out.printf("%27s%n", "DELETE CATEGORY");
        System.out.println(border);

        System.out.print("Enter Category ID: ");
        String categoryID = in.nextLine().trim().toUpperCase();
        System.out.println(border);

        if (inventory.removeCategory(categoryID)) {
            System.out.println("\n"+mborder);
            System.out.println("Category Deleted Successfully!");
            System.out.println(mborder);
        } else {
            System.out.println("\n"+mborder);
            System.out.println("Category ID not found!");
            System.out.println(mborder);
        }
    }

}

//malapit na tayo mag 1000 lines