package Simula;

import java.util.Scanner;

public abstract class Employee {

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

    //pang pause sa action bago bumalik sa dashboard
    public void pause() {
        System.out.print("\nPress Enter to return to dashboard...");
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

        receipt.addTransactionDetail(quantity, product.getSellingPrice(), product);

        if(!inventory.recordSale(receipt)) {
            System.out.println("Sale Failed!(Not enough Stock, Expired Batch, or not Allowed)");
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
        System.out.println("VAT(12%): "+ receipt.getVATAmount());
        System.out.println("Total(with VAT): " + receipt.getTotalWithVAT());

    }


    protected void displayProducts () {

        System.out.println("=== Display Products ===\n");

        int counter = 1;
        for(Product p : inventory.getProducts()) {
            System.out.println("\nPRODUCT#"+counter +
                    "\nProduct ID: " + p.getProductID() +
                    "\nProduct Name: " + p.getProductName() +
                    "\nGeneric Name: " + p.getGenericName() +
                    "\nBrand : " + p.getBrand() +
                    "\nUnit: " + p.getUnit() +
                    "\nSelling Price: P" + p.getSellingPrice()+
                    "\nReorder Level: " + p.getReorderLevel() +
                    "\nCategory: "+p.getCategory().getCategory_Name());
            counter++;
        }

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

            System.out.println("              "+ counter + "             ");
            System.out.println("Transaction ID: "+ t.getTransactionID());
            System.out.println("Transaction type: "+ t.getTransactionType());
            System.out.println("Transaction date: "+ t.getTransactionID());
            System.out.println("Transaction by: "+ t.getEmployee().getName());
            counter++;

        }

        if(counter == 0) System.out.println("\tNothing to see here...");

    }


}