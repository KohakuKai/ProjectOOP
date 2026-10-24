package Simula;
import java.sql.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jdk.swing.interop.SwingInterOpUtils;
import java.sql.SQLOutput;

//tatanggalin ba tong dalawa sa taas?

import java.util.ArrayList;
import java.util.Locale;
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

    public String getEmpID() {
        return empID;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract void showDashboard();

    @Override
    public String toString() {
        return "ID no.: " + empID + "    |    " + name + "\n";
    }

    //mga shared methods dito---------------------------------------------------------------------------
    protected void tag() {
        String firstName = getName().trim().split(" ")[0];
        String userInfo = "[" + firstName + "|" + getPosition() + "]";

        System.out.printf("\n%40s\n", userInfo);
    }

    //pang pause sa action bago bumalik sa dashboard
    protected void pause() {
        System.out.print("Press [Enter] to exit.");
        String junk = in.nextLine();
    }

    //eto gamitin sa scanner para hindi nagcracrash
    public static int userIntInput() {
        while (!in.hasNextInt()) {
            System.out.print("Invalid Input!\n" +
                    "Please Enter a Number: ");
            in.nextLine();
        }
        int input = in.nextInt();
        in.nextLine();
        return input;
    }

    //pang double
    public static double userDoubleInput() {
        while (!in.hasNextDouble()) {
            System.out.print("Invalid Input!\n" +
                    "Please Enter a Number: ");
            in.nextLine();
        }

        double input = in.nextDouble();
        in.nextLine();
        return input;
    }


    //ACCOUNT METHODS --------------------------------------------------------------

    protected void changePassword() {

        System.out.print("\n" + border);
        System.out.printf("%27s%n", "CHANGE PASSWORD");
        System.out.println("\n" + border + "\nEnter Current password: ");
        String checkPassword = in.nextLine();

        if (checkPassword.equals(this.getPassword())) {

            System.out.print("Enter new password: ");
            String newPassword = in.nextLine();

            setPassword(newPassword);
            System.out.println(mborder);
            System.out.println("Password changed successfully!");
            System.out.println(mborder);
        } else {
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

        tag();
        System.out.println("\n" + border);
        System.out.printf("%27s%n", "SELL PRODUCT");
        System.out.println(border);

        Sale receipt = new Sale(this, inventory);
        boolean end = false;

        while (!end) {
            System.out.print("\t[1]Input Product ID to Sell\n"+
                    "\t[2]Search Product Name\n" +
                    "\t[0]Exit]\n" +
                    border + "\nChoice:");
            int choice = userIntInput();
            System.out.println(border);


            if(choice == 2) {
                searchProductByName();
            }

            else if (choice == 0) {
                break;
            }

            else if(choice!= 1 || choice!= 2 || choice !=0) {
                System.out.println("Invalid Input!");
                continue;
            }

            System.out.print("Enter Product ID[Enter 0 to End]: ");
            String productID = in.nextLine().trim().toUpperCase();
            if(productID.equals("0")) {
                end = true;
                break;
            }

            Product product = inventory.findProductById(productID);

            if (product == null) {
                System.out.println(border + "\n");
                System.out.println(mborder);
                System.out.println("Product not found.");
                System.out.println(mborder);
                continue;
            }

            int removed = product.removeExpiredBatches();//pangremove ng expired batches para sa product nayon
            if(removed>0)System.out.println("\n[Removed] "+ removed+ " Expired Batches from "+ product.getProductName()+"\n");

            if (product instanceof Medicine && !((Medicine) product).soldBy(this)) {
                System.out.println(border + "\n\n" + mborder + "\nOnly the Owner or a Pharmacist can sell prescription medicine." + mborder);
                continue;
            }

            System.out.println("Product Name: " + product.getProductName());
            System.out.println("Selling Price: " + product.getSellingPrice());
            System.out.println("Available Stock: " + product.getTotalStock());

            System.out.print("Enter Quantity: ");
            int quantity = userIntInput();
            if (quantity <= 0) {
                System.out.println(border + "\n");
                System.out.println(mborder);
                System.out.println("Invalid quantity.");
                System.out.println(mborder);
                continue;
            }
            if (quantity > product.getTotalStock()) {
                System.out.println(border + "\n");
                System.out.println(mborder);
                System.out.println("Insufficient stock.");
                System.out.println(mborder);
                continue;
            }

            if(receipt.addTransactionDetail(quantity, product.getSellingPrice(), product)) {
                System.out.println("\n" + mborder + "Added to Checkout!" + mborder +"\n");
            }
            else {
                System.out.println(mborder);
                System.out.println("Input Failed! Invalid Quantity, Cost, or Expiration Date");
                System.out.println(mborder);
            }

            System.out.println(border);

        }

        if (!inventory.recordSale(receipt)) {
            System.out.println(border + "\n");
            System.out.println(mborder);
            System.out.println("Sale Failed! Not enough Stock, Expired Batch, or not Allowed ");
            System.out.println(mborder);
            return;
        }

        //taga check ng discount
        boolean pwd = false;
        boolean senior = false;
        System.out.print("\nCustomer Discount:\n" +
                "\t[1] PWD\n" +
                "\t[2] Senior\n" +
                "\t[3] No Discount\n" +
                "Enter Input: ");
        int customerDiscount = userIntInput();
        switch (customerDiscount) {
            case 1:
                pwd = true;
                break;
            case 2:
                senior = true;
                break;
            case 3:
                break;
            default:
                System.out.println(border + "\n\n" + mborder + "\nInvalid Choice! try again...\n" + mborder);
                break;
        }

        ArrayList<TransactionDetail> lines = receipt.getTransactionDetails();

        System.out.println(border);
        System.out.printf("%27s%n", "RECEIPT");
        System.out.println(border);
        System.out.println("Transaction ID: " + receipt.getTransactionID());
        System.out.println("Date:           " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("Cashier:        " + getName() + " (" + getPosition() + ")");
        System.out.println("Transaction Type: " + receipt.getTransactionType());

        System.out.println(border);
        for (int i=0; i<lines.size();i++) { //etong for loop nato para lang sa individual products, pinapakita yung Product name | quantity x price = subtotal | expirationDate
            TransactionDetail line = lines.get(i);
            System.out.println( line.getProduct().getProductName() + " | " + line.getQuantity() + " x " + line.getUnitPrice() + " = " + line.getSubTotal() +"");
        }
        System.out.println(mborder);

        System.out.printf("Subtotal: %.2f%n", receipt.getSubTotalPrice());
        System.out.println(line);
        if (!pwd && !senior) {
            System.out.printf("VAT(12%%): %.2f%n", receipt.getVATAmount());
            System.out.printf("Total(with VAT): %.2f%n", receipt.getTotalWithVAT());
        }

        if (pwd) {
            System.out.printf("PWD Discount(20%%): %.2f%n", receipt.getPwdDiscount());
            System.out.printf("Total(with PWD Discount) %.2f%n", receipt.getTotalWithPwdDiscount());
        }
        if (senior) {
            System.out.printf("Senior Cirizen Discount(20%%): %.2f%n", receipt.getSeniorDiscount());
            System.out.printf("Total(with Senior Discount): %.2f%n", receipt.getTotalWithSeniorDiscount());
        }

        System.out.println();

    }

    //PRODUCTS METHODS

    protected void displayProducts() {

        int counter = 1;
        tag();
        System.out.println(border);
        System.out.printf("%27s%n", "DISPLAY PRODUCTS");
        System.out.println(border);
        System.out.printf("%-4s %-6s %-26s %10s%n", "#", "ID", "NAME", "PRICE");
        System.out.println(line);
        for (Product p : inventory.getProducts()) {
            System.out.printf("%-4d %-6s %-26.26s %10.2f%n", counter, p.getProductID(), p.getProductName(), p.getSellingPrice());
            counter++;
        }
        System.out.println(line);

        System.out.print("\t[1]View More Product Details\n\t[0]Exit\n" + border + "\nChoice:");
        int choice = userIntInput();
        System.out.println(border);

        switch (choice) {
            case 1:
                displayMoreProductDetails();
                break;
            case 0:
                break;
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

        if (product == null) {
            System.out.println(mborder);
            System.out.println("Product not Found!");
            System.out.println(mborder);
            return;
        }

        tag();
        System.out.println(border);
        System.out.println("\t\tPRODUCT DETAILS");
        System.out.println(border);
        product.getInfo();
        System.out.println(border);

        if (this instanceof Owner) {
            System.out.print("[1] Edit Product Details\n" +
                    "[0] Exit\nChoice: ");
            int choice = userIntInput();

            switch (choice) {
                case 1: {
                    while (true) {
                        tag();
                        System.out.println(border);
                        System.out.printf("%27s%n", "EDIT PRODUCT DETAILS");
                        System.out.println(border);

                        product.getInfo();

                        System.out.println(border);
                        System.out.println("[1] Product Name\n" +
                                "[2] Generic Name\n" +
                                "[3] Brand\n" +
                                "[4] Unit\n" +
                                "[5] Selling Price\n" +
                                "[6] Reorder Level\n" +
                                "[7] Category\n" +
                                "[0] Exit\n");
                        System.out.println(border);
                        System.out.print("Choose detail to edit: ");

                        choice = userIntInput();
                        System.out.println(border);

                        switch (choice) {
                            case 1: {
                                System.out.print("Enter new Product Name: ");
                                String name = in.nextLine().trim();

                                if (name.isEmpty()) {
                                    System.out.println("Product name cannot be empty!");
                                    break;
                                }

                                product.setProductName(name);
                                break;
                            }
                            case 2: {
                                System.out.print("Enter new Generic Name: ");
                                String genericName = in.nextLine().trim();

                                if (genericName.isEmpty()) {
                                    System.out.println("Generic name cannot be empty!");
                                    break;
                                }

                                product.setGenericName(genericName);
                                break;
                            }
                            case 3: {
                                System.out.print("Enter new Brand: ");
                                String brand = in.nextLine().trim();

                                if (brand.isEmpty()) {
                                    System.out.println("Brand cannot be empty!");
                                    break;
                                }

                                product.setBrand(brand);
                                break;
                            }
                            case 4: {
                                System.out.print("Enter new Unit: ");
                                String unit = in.nextLine().trim();

                                if (unit.isEmpty()) {
                                    System.out.println("Unit cannot be empty!");
                                    break;
                                }

                                product.setUnit(unit);
                                break;
                            }
                            case 5: {
                                System.out.print("Enter new Selling Price: ");
                                double price = userDoubleInput();

                                if (price < 0) {
                                    System.out.println("Price cannot be negative!");
                                    break;
                                }

                                product.setSellingPrice(price);
                                break;
                            }
                            case 6: {
                                System.out.print("Enter new Reorder Level: ");
                                int reorderLevel = userIntInput();

                                if (reorderLevel < 0) {
                                    System.out.println("Reorder level cannot be negative!");
                                    break;
                                }

                                product.setReorderLevel(reorderLevel);
                                break;
                            }
                            case 7: {
                                System.out.print("Enter new Category ID: ");
                                String categoryID = in.nextLine().trim().toUpperCase();

                                Category category = inventory.findCategoryById(categoryID);

                                if (category == null) {
                                    System.out.println("Category ID not found!");
                                    break;
                                }

                                product.setCategory(category);
                                break;
                            }
                            case 0:
                                System.out.println(mborder);
                                System.out.println("Product details updated successfully!");
                                System.out.println(mborder);
                                return;

                            default:
                                System.out.println("Invalid choice!");
                        }
                    }
                }

                case 0:
                    break;
                default:
                    System.out.println(mborder);
                    System.out.println("Invalid Input!");
                    System.out.println(mborder);
                    break;
            }
        }
    }


    //CATEGORIES METHODS

    protected void displayCategories() {

        int counter = 1;
        tag();
        System.out.println(border);
        System.out.println("\t\t\tDISPLAY CATEGORIES");
        System.out.println(border);
        System.out.printf("%-3s %-6s %-13s %-21.25s%n", "#", "ID", "NAME", "DESCRIPTION");
        System.out.println(line);
        for (Category c : inventory.getCategories()) {
            System.out.printf("%-3d %-6s %-13.15s %-21.25s%n", counter, c.getCategory_ID(),
                    c.getCategory_Name(), c.getDescription());
            counter++;
        }
        System.out.println(line);
        System.out.println(border);

    }


    //BATCH METHODS

    protected void viewProductBatchDetails() {
        tag();
        System.out.println(border);
        System.out.println("\t\t\tPRODUCT BATCHES");
        System.out.println(border);
        System.out.print("Enter Product ID: ");
        String productID = in.nextLine().trim().toUpperCase();

        Product product = inventory.findProductById(productID);

        if (product == null) {
            System.out.println(mborder);
            System.out.println("Product not found!");
            System.out.println(mborder);
            return;
        }

        System.out.println(line);
        System.out.println("\t\t\tPRODUCT DETAILS");
        product.getInfo();
        System.out.printf("%-15s: %d%n", "Total Stock: ", product.getTotalStock());

        System.out.println(line);
        System.out.println("\t\t\tBATCH DETAILS");
        product.checkExpiration();
        System.out.println(line);
        System.out.println(border);
    }


    //TRANSACTION METHODS

    protected void viewTransactionHistory() {

        int counter = 1;
        System.out.println("=== Transaction History ===\n");
        for (Transaction t : inventory.getTransactions()) {

            System.out.println("\t\t   " + counter);
            System.out.println("Transaction ID: " + t.getTransactionID());
            System.out.println("Transaction type: " + t.getTransactionType());
            System.out.println("Transaction date: " + t.getTransactionDate());
            System.out.println("Transaction by: " + t.getEmployee().getName() + "\n");
            counter++;

        }

        if (counter == 0) System.out.println("\tNothing to see here...");

    }

    //checker ng low stock and expired

    protected void displayStockAlerts() {
        int expiredProducts = 0;
        int lowStockProducts = 0;

        for (Product product : inventory.getProducts()) {
            boolean hasExpiredStock = false;
            int usableStock = 0;

            for (Batch batch : product.getBatches()) {
                if (batch.isExpired()) {
                    if (batch.getQuantity() > 0) {
                        hasExpiredStock = true;
                    }
                } else {
                    usableStock += batch.getQuantity();
                }
            }
            if (hasExpiredStock) {
                expiredProducts++;
            }

            if (usableStock < product.getReorderLevel()) {
                lowStockProducts++;
            }
        }

        if (expiredProducts > 0 && lowStockProducts > 0) {
            System.out.println(line);
            System.out.printf("%27s%n", "!ALERTS!");
            System.out.print("[EXPIRED] " + expiredProducts + " items");
            System.out.printf("%25s%n", "[LOW] " + lowStockProducts + " items");
            System.out.println(line);
        } else if (expiredProducts > 0 && lowStockProducts == 0) {
            System.out.println(line);
            System.out.printf("%27s%n", "!ALERTS!");
            System.out.println("[EXPIRED] " + expiredProducts + " items");
            System.out.println(line);
        } else if (expiredProducts == 0 && lowStockProducts > 0) {
            System.out.println(line);
            System.out.printf("%27s%n", "!ALERTS!");
            System.out.println("[LOW] " + lowStockProducts + " items");
            System.out.println(line);
        }
    }

    protected void searchProduct() {

        System.out.println(border);
        System.out.printf("%27s%n", "SEARCH PRODUCT");
        System.out.println(border);


        System.out.print("\t[1]Search by ID\n" +
                "\t[2]Search by Name\n" +
                "\t[0]Exit\n");
        System.out.println(border);
        System.out.print("Choice: ");
        int choice = userIntInput();
        System.out.printf(border);
        switch (choice) {

            case 1: {

                System.out.print("\nProduct ID: ");
                String productID = in.nextLine().trim().toUpperCase();

                Product product = inventory.findProductById(productID);

                if (product == null) {
                    System.out.println("ProductID not found!");
                    break;
                }

                System.out.println(border);
                System.out.println("\t\tPRODUCT DETAILS");
                System.out.println(border);
                product.getInfo();
                System.out.println(border);

                break;
            }

            case 2 : {

                searchProductByName();

                System.out.println(border);
                System.out.print("\t[1]View More Product Details\n\t[0]Exit\n" + border + "\nChoice:");
                choice = userIntInput();
                System.out.println(border);

                switch (choice) {
                    case 1:
                        displayMoreProductDetails();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println(mborder);
                        System.out.println("Invalid Input!");
                        System.out.println(mborder);
                        break;
                }

                break;

            }

            case 0 : break;
            default: {
                System.out.println(mborder);
                System.out.println("Invalid Input! ");
                System.out.println(mborder);
                break;
            }
        }

    }

    protected void searchProductByName() {

        System.out.print("\nProduct Name: ");
        String productName = in.nextLine().trim().toUpperCase();
        if(productName.equals("0")) {
            return;
        }

        ArrayList<Product> matchedProducts = inventory.findProductByName(productName);

        if(matchedProducts == null) {
            System.out.println("No Product Name Found!");
            return;
        }

        System.out.println(border);
        System.out.printf("%27s%n","SEARCH RESULTS");
        System.out.println(border);
        System.out.printf("%-15s %-15s%s%n","ID","Name","Price");

        for(Product p : matchedProducts) {
            System.out.printf("%-15s %-15s%.2f%n",p.getProductID(), p.getProductName(),p.getSellingPrice());
        }
        System.out.println();


    }

    protected int removeAllExpiredBatches() {

        int removed = 0;
        for (Product p : inventory.getProducts()) {

            ArrayList<Batch> batches = p.getBatches();


            for (int i = 0; i < batches.size(); i++) {
                if (batches.get(i).isExpired()) {
                    batches.remove(i);
                    removed++;
                }

            }

        }
        return removed;
    }



}