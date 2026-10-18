package Simula;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.Scanner;

public abstract class Employee {

    String line = "-".repeat(50);
    String border = "=".repeat(50);
    String mborder = "~".repeat(50);

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

        System.out.printf("\n%40s\n", userInfo);
    }
    //pang pause sa action bago bumalik sa dashboard
    public void pause() {
        System.out.print("Press [Enter] to exit.");
        String junk = in.nextLine();
    }

    //ACCOUNT METHODS --------------------------------------------------------------

    protected void changePassword() {

        System.out.print("\n"+border);
        System.out.printf("%27s%n", "CHANGE PASSWORD");
        System.out.println("\n" + border + "\nEnter Current password: ");
        String checkPassword = in.nextLine();

        if(checkPassword.equals(this.getPassword())) {

            System.out.print("Enter new password: ");
            String newPassword = in.nextLine();

            setPassword(newPassword);
            System.out.println(mborder);
            System.out.println("Password changed successfully!");
            System.out.println(mborder);
        }

        else {
            System.out.println(mborder);
            System.out.println("Password does not match!");
            System.out.println(mborder);
        }

    }

    protected void displayProfile() {
        System.out.println("\n" + border);
        System.out.printf("%27s%n", "MY PROFILE");
        System.out.println(border);
        System.out.println("Employee ID: " + getEmpID());
        System.out.println("Name: " + getName());
        System.out.println("Position: " + getPosition());
        System.out.println("Username: " + getUsername());
        System.out.println("Password: " + getPassword());

    }


    //PRODUCT METHODS

    protected void sellProduct() {
        Sale receipt = new Sale(this, inventory);

        System.out.println("\n" + border);
        System.out.printf("%27s%n", "SELL PRODUCT");
        System.out.println(border);
        System.out.print("Enter Product ID: ");
        String productID = in.nextLine().trim().toUpperCase();

        Product product = inventory.findProductById(productID);

        if (product == null) {
            System.out.println(mborder);
            System.out.println("Product not found.");
            System.out.println(mborder);
            return;
        }

        if (product instanceof Medicine && !((Medicine) product).soldBy(this)) {
            System.out.println(mborder + "\nOnly the Owner or a Pharmacist can sell prescription medicine." + mborder);
            return;
        }

        System.out.println("Product Name: " + product.getProductName());
        System.out.println("Selling Price: " + product.getSellingPrice());
        System.out.println("Available Stock: " + product.getTotalStock());

        System.out.print("Enter Quantity: ");
        int quantity = in.nextInt();
        in.nextLine();
        if (quantity <= 0) {
            System.out.println(mborder);
            System.out.println("Invalid quantity.");
            System.out.println(mborder);
            return;
        }
        if (quantity > product.getTotalStock()) {
            System.out.println(mborder);
            System.out.println("Insufficient stock.");
            System.out.println(mborder);
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
            default: System.out.println(mborder+"\nInvalid Choice! try again...\n"+mborder); break;
        }

        receipt.addTransactionDetail(quantity, product.getSellingPrice(), product);

        if(!inventory.recordSale(receipt)) {
            System.out.println(mborder);
            System.out.println("Sale Failed! Not enough Stock, Expired Batch, or not Allowed ");
            System.out.println(mborder);
            return;
        }

        System.out.printf("%27s%n", "RECEIPT");
        System.out.println("Transaction ID: "+ receipt.getTransactionID());
        System.out.println("Transaction Type: "+ receipt.getTransactionType());
        System.out.println("Sale No.: " + receipt.getSaleNo());
        System.out.println("Product: " + product.getProductName());
        System.out.println("Quantity: " + quantity);
        System.out.println("Unit Price: " + product.getSellingPrice());
        System.out.println("Subtotal: " + receipt.getSubTotalPrice());
        if(!pwd && !senior)System.out.println("VAT: "+ receipt.getVATAmount());
        if(!pwd && !senior)System.out.println("Total(with VAT): " + receipt.getTotalWithVAT());
        if(pwd) System.out.println("Total(with PWD Discount): " + receipt.getTotalWithPwdDiscount());
        if(senior) System.out.println("Total(with Senior Discount): " + receipt.getTotalWithSeniorDiscount());

        System.out.println();

    }

    protected void displayProducts () {

        int counter = 1;
        tag();
        System.out.println(border);
        System.out.printf("%27s%n", "DISPLAY PRODUCTS");
        System.out.println(border);
        System.out.printf("%-4s %-6s %-26s %10s%n", "#", "ID", "NAME", "PRICE");
        System.out.println(line);
        for(Product p : inventory.getProducts()) {
            System.out.printf("%-4d %-6s %-26.26s %10.2f%n", counter, p.getProductID(), p.getProductName(),p.getSellingPrice() );
            counter++;
        }
        System.out.println(line);

        System.out.print("\t[1]View More Product Details\n\t[0]Exit\n"+border+"\nChoice:");
        int choice = in.nextInt(); in.nextLine();
        System.out.println(border);

        switch (choice) {
            case 1 : displayMoreProductDetails();
            break;
            case 0 : break;
            default:
                System.out.println(mborder);
                System.out.println("Invalid Input!");
                System.out.println(mborder);
                break;
        }

    }

    protected void displayMoreProductDetails() {

        System.out.print("\n");
        System.out.println(line);
        System.out.print("Product ID: ");
        String productID = in.nextLine().trim().toUpperCase();
        System.out.println(line);

        Product product = inventory.findProductById(productID);

        if(product == null) {
            System.out.println(mborder);
            System.out.println("Product not Found!");
            System.out.println(mborder);
            return;
        }

        tag();
        System.out.println(border);
        System.out.println("\t\tPRODUCT DETAILS");
        System.out.println(border);
        System.out.printf("%-15s: %s%n",   "Product ID",     product.getProductID());
        System.out.printf("%-15s: %s%n",   "Product Name",   product.getProductName());
        System.out.printf("%-15s: %s%n",   "Generic Name",   product.getGenericName());
        System.out.printf("%-15s: %s%n",   "Brand",          product.getBrand());
        System.out.printf("%-15s: %s%n",   "Unit",           product.getUnit());
        System.out.printf("%-15s: %.2f%n", "Selling Price",  product.getSellingPrice());
        System.out.printf("%-15s: %d%n",   "Reorder Level",  product.getReorderLevel());
        System.out.println(border);
    }





    //CATEGORIES METHODS

    protected void displayCategories() {

        System.out.println(border);
        System.out.println("DISPLAY CATEGORIES");
        System.out.println(border);
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
            System.out.println(mborder);
            System.out.println("Product not found!");
            System.out.println(mborder);
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