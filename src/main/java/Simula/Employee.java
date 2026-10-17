package Simula;

import java.util.Scanner;

public abstract class Employee {

    String line = "-".repeat(110);
    String border = "=".repeat(33);
    String mborder = "~".repeat(33);

    protected static final Scanner in = new Scanner(System.in); //share share na lahat ng subclass sa isang scanner, para walang duplicate scanners

    //protected final StartUp manager; // para share na rin ng pharmacist

    private String name;
    private String position; // "Owner", "Pharmacist", or "Staff"
    private String username;
    private String password;
    private static int nextEmpID = 1001;
    private String empID;
    protected final Inventory inventory;

    public Employee(String name, String position, String password, String username, Inventory inventory) {
        this.empID = String.valueOf(nextEmpID++);
        this.name = name;
        this.position = position;
        this.password = password;
        this.username = username;
        this.inventory = inventory;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmpID () {
        return empID;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public abstract void showDashboard();

    @Override
    public String toString() {
        return "ID no.: " + empID + "    |    " + name + "\n";
    }

    //mga shared methods dito---------------------------------------------------------------------------
    public void tag() {
        String firstName = getName().trim().split(" ")[0];
        String userInfo = "[" + firstName + "|" + getPosition() + "]";

        System.out.printf("\n%33s\n", userInfo);
    }
    //pang pause sa action bago bumalik sa dashboard
    public void pause() {
        System.out.print("Press [Enter] to exit.");
        in.nextLine();
    }

    //ACCOUNT METHODS --------------------------------------------------------------

    protected void changePassword() {

        System.out.print("\n=== CHANGE PASSWORD ===\nEnter Current password: ");
        String checkPassword = in.nextLine();

        if(checkPassword.equals(this.getPassword())) {

            System.out.print("Enter new password: ");
            String newPassword = in.nextLine();

            setPassword(newPassword);
            System.out.println("Password changed successfully!");
        }

        else System.out.println("Password does not match!");

    }

    protected void displayProfile() {
        System.out.println("\n=== MY PROFILE ===");
        System.out.println("Employee ID: " + getEmpID());
        System.out.println("Name: " + getName());
        System.out.println("Position: " + getPosition());
        System.out.println("Username: " + getUsername());
        System.out.println("Password: " + getPassword());

    }


    //PRODUCT METHODS

    protected void sellProduct() {
        Sale receipt = new Sale(this, inventory);

        System.out.println("\n=== SELL PRODUCT ===");
        System.out.print("Enter Product ID: ");
        String productID = in.nextLine().trim().toUpperCase();

        Product product = inventory.findProductById(productID);

        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        if (product instanceof Medicine && !((Medicine) product).soldBy(this)) {
            System.out.println("Only the Owner or a Pharmacist can sell prescription medicine.");
            return;
        }

        System.out.println("Product Name: " + product.getProductName());
        System.out.println("Selling Price: " + product.getSellingPrice());
        System.out.println("Available Stock: " + product.getTotalStock());

        System.out.print("Enter Quantity: ");
        int quantity = in.nextInt();
        in.nextLine();
        if (quantity <= 0) {
            System.out.println("Invalid quantity.");
            return;
        }
        if (quantity > product.getTotalStock()) {
            System.out.println("Insufficient stock.");
            return;
        }

        //taga check ng discount
        boolean pwd = false;
        boolean senior = false;
        System.out.print("\nCustomer Discount:\n" +
                "[1] PWD\n" +
                "[2] Senior\n" +
                "[3] No Discount\n" +
                "Enter Input: ");
        int customerDiscount = in.nextInt();
        in.nextLine();
        switch(customerDiscount){
            case 1: pwd = true; break;
            case 2: senior = true; break;
            case 3: break;
            default: System.out.println("Invalid Choice! try again..."); break;
        }

        receipt.addTransactionDetail(quantity, product.getSellingPrice(), product);

        if(!inventory.recordSale(receipt)) {
            System.out.println("Sale Failed! Not enough Stock, Expired Batch, or not Allowed ");
            return;
        }

        System.out.println("\n=== RECEIPT ===");
        System.out.println("Transaction ID: "+ receipt.getTransactionID());
        System.out.println("Transaction Type: "+ receipt.getTransactionType());
        System.out.println("Sale No.: " + receipt.getSaleNo());
        System.out.println("Product: " + product.getProductName());
        System.out.println("Quantity: " + quantity);
        System.out.println("Unit Price: " + product.getSellingPrice());
        System.out.println("Subtotal: " + receipt.getSubTotalPrice());
        System.out.println("VAT: "+ receipt.getVATAmount());
        if(!pwd || !senior)System.out.println("Total(with VAT): " + receipt.getTotalWithVAT());
        if(pwd) System.out.println("Total(with PWD Discount): " + receipt.getTotalWithPwdDiscount());
        if(senior) System.out.println("Total(with Senior Discount): " + receipt.getTotalWithSeniorDiscount());

        System.out.println();

    }

    protected void displayProducts () {

        int counter = 1;
        for(Product p : inventory.getProducts()) {
            System.out.print("\n=================================\nPRODUCT#"+counter +
                    "\nProduct ID: " + p.getProductID() +
                    "\nProduct Name: " + p.getProductName() +
                    "\nSelling Price: P" + p.getSellingPrice() + "\n");
            counter++;
        }

        System.out.print("\n=================================\n[1]View More Product Details\n[0]Exit\n=================================\nChoice:");
        int choice = in.nextInt(); in.nextLine();

        switch (choice) {
            case 1 : displayMoreProductDetails();
            break;
            case 0 : break;
            default:
                System.out.println("Invalid Input!");
                break;
        }

    }

    protected void displayMoreProductDetails() {

        System.out.print("Product ID: ");
        String productID = in.nextLine().trim().toUpperCase();

        Product product = inventory.findProductById(productID);

        if(product == null) {
            System.out.println("Product not Found!");
            return;
        }

        System.out.println("\n=================================\n\t"+ product.getProductName()+ " Details");
        System.out.println("\nProduct ID: "+ product.getProductID() +
                "\nProduct Name: " + product.getProductName() +
                "\nGeneric Name: " + product.getGenericName() +
                "\nBrand : " + product.getBrand() +
                "\nUnit: " + product.getUnit() +
                "\nSelling Price: " + product.getSellingPrice() +
                "\nReorder Level: "+ product.getReorderLevel() + "\n"
                );
    }





    //CATEGORIES METHODS

    protected void displayCategories() {

        System.out.println("=== Display Categories ===");
        int counter = 1;

        for(Category c : inventory.getCategories()) {
            System.out.println("\nCATEGORY#"+counter+
                    "\nCategory ID: "+ c.getCategory_ID() +
                    "\nCategory Name: "+ c.getCategory_Name()+
                    "\nCategory Description: "+ c.getDescription());
            counter++;

        }

    }


    //BATCH METHODS

    protected void viewProductBatchDetails() {
        System.out.print("Enter Product ID: ");
        String productID = in.nextLine().trim().toUpperCase();

        Product product = inventory.findProductById(productID);

        if (product == null) {
            System.out.println("Product not found!");
            return;
        }

        System.out.println("\n=== Product Details ===");
        product.getInfo();

        System.out.println("\n=== Batch Details ===");
        product.checkExpiration();
    }


    //TRANSACTION METHODS

    protected void viewTransactionHistory() {

        int counter = 1;
        System.out.println("=== Transaction History ===\n");
        for(Transaction t : inventory.getTransactions()) {

            System.out.println("\t\t   "+ counter );
            System.out.println("Transaction ID: "+ t.getTransactionID());
            System.out.println("Transaction type: "+ t.getTransactionType());
            System.out.println("Transaction date: "+ t.getTransactionDate());
            System.out.println("Transaction by: "+ t.getEmployee().getName() + "\n");
            counter++;

        }

        if(counter == 0) System.out.println("\tNothing to see here...");

    }


}