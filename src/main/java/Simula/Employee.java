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

        System.out.printf("\n%50s\n", userInfo);
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
        System.out.printf("%32s%n", "CHANGE PASSWORD");
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

        System.out.println(border);

    }

    protected void displayProfile() {
        tag();
        System.out.println(border);
        System.out.printf("%30s\n", "MY PROFILE");
        System.out.println(border);

        System.out.println("Employee ID: " + getEmpID());
        System.out.println("Name: " + getName());
        System.out.println("Position: " + getPosition());
        System.out.println("Username: " + getUsername());
        System.out.println("Password: " + getPassword());

    }


    //PRODUCT METHODS

    protected void sellProduct() {
        int i =0;

        tag();
        System.out.println("\n" + border);
        System.out.printf("%31s%n", "SELL PRODUCT");
        System.out.println(border);

        Sale receipt = new Sale(this, inventory);
        boolean end = false;

        while (!end) {
            System.out.print("\t[1]Input Product ID to Sell\n"+
                    "\t[2]Search Product Name\n" +
                    "\t[3]Checkout\n" +
                    border + "\nChoice:");
            int choice = userIntInput();
            System.out.println(border);


            if(choice == 2) {
                searchProductByName();
            }

            else if (choice == 3) {
                break;
            }

            else if(choice!= 1 ) {
                System.out.println("Invalid Input!");
                continue;
            }

            System.out.println("[Enter 0 to End]");
            System.out.print("Enter Product ID: ");
            String productID = in.nextLine().trim().toUpperCase();
            System.out.println(border);
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
            if(removed>0)System.out.println(
                    "\n"+mborder+"\n[Removed] "+ removed+
                    " Expired Batches from "+
                    product.getProductName()+"\n"+mborder+"\n");

            if (product instanceof Medicine && !((Medicine) product).soldBy(this)) {
                System.out.println(border + "\n\n" + mborder + "\nOnly the Owner or a Pharmacist can sell prescription medicine." + mborder);
                continue;
            }

            System.out.println(line);
            System.out.println("Product Name: " + product.getProductName());
            System.out.println("Selling Price: " + product.getSellingPrice());
            System.out.println("Available Stock: " + product.getTotalStock());

            System.out.print("Enter Quantity: ");
            int quantity = userIntInput();
            System.out.println(line);
            if (quantity <= 0) {
                System.out.println("\n"+border + "\n");
                System.out.println(mborder);
                System.out.println("Invalid quantity.");
                System.out.println(mborder);
                continue;
            }
            if (quantity > product.getTotalStock()) {
                System.out.println("\n"+border + "\n");
                System.out.println(mborder);
                System.out.println("Insufficient stock.");
                System.out.println(mborder);
                continue;
            }

            if(receipt.addTransactionDetail(quantity, product.getSellingPrice(), product)) {
                System.out.println("\n" + mborder + "\nAdded to Checkout!\n" + mborder +"\n");
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
        System.out.printf("%33s\n","CUSTOMER DISCOUNT\n" +
                "\t[1] PWD\n" +
                "\t[2] Senior\n" +
                "\t[3] No Discount\n" + border +
                "\nChoice: ");
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
        System.out.printf("%28s%n", "RECEIPT");
        System.out.println(border);
        System.out.println("Transaction ID   : " + receipt.getTransactionID());
        System.out.println("Date             : " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("Cashier          : " + getName() + " (" + getPosition() + ")");
        System.out.println("Transaction Type : " + receipt.getTransactionType());

        System.out.println(line);
        System.out.printf("%-6s%-34s%10s%n", "Qty", "Product(s)", "Price");
        System.out.println(line);
        for (i=0; i<lines.size();i++) { //etong for loop nato para lang sa individual products, pinapakita yung Product name | quantity x price = subtotal | expirationDate
            TransactionDetail line = lines.get(i);
            System.out.printf("%-6d%-34s%10.2f%n", line.getQuantity(), line.getProduct().getProductName(), line.getUnitPrice());
        }
        System.out.println("--------------------" + i +
                " item(s)---------------------");

        System.out.printf("%-40s%10.2f%n", "Subtotal:", receipt.getSubTotalPrice());
        if (!pwd && !senior) {
            System.out.printf("%-40s%10.2f%n", "VAT(12%):", receipt.getVATAmount());
            System.out.println(line);
            System.out.printf("%-140s%10.2f%n", "Total(with VAT):", receipt.getTotalWithVAT());
        }

        if (pwd) {
            System.out.printf("%-40s%10.2f%n", "PWD Discount(20%):", receipt.getPwdDiscount());
            System.out.println(line);
            System.out.printf("%-40s%10.2f%n", "Total(with PWD Discount):", receipt.getTotalWithPwdDiscount());
        }
        if (senior) {
            System.out.printf("%-40s%10.2f%n", "Senior Citizen Discount(20%):", receipt.getSeniorDiscount());
            System.out.println(line);
            System.out.printf("%-40s%10.2f%n", "Total(with Senior Discount):", receipt.getTotalWithSeniorDiscount());
        }

        System.out.println(border);

    }

    //PRODUCTS METHODS

    protected void displayProducts() {

        int counter = 1;
        tag();
        System.out.println(border);
        System.out.printf("%33s\n", "DISPLAY PRODUCTS");
        System.out.println(border);
        System.out.printf("%-4s %-6s %-26s %10s%n", "#", "ID", "NAME", "PRICE");
        System.out.println(line);
        for (Product p : inventory.getProducts()) {
            System.out.printf("%-4d %-6s %-26.26s %10.2f%n", counter, p.getProductID(), p.getProductName(), p.getSellingPrice());
            counter++;
        }
        System.out.println(line);

        System.out.print("\t[1] View More Product Details\n" +
                "\t[0] Exit\n" +
                border +
                "\nChoice: ");
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
            System.out.println("\n"+mborder);
            System.out.println("Product not Found!");
            System.out.println(mborder);
            return;
        }

        tag();
        System.out.println(border);
        System.out.printf("%32s\n","PRODUCT DETAILS");
        System.out.println(border);
        product.getInfo();
        System.out.println(border);


        if (this instanceof Owner) {
            System.out.print("\t[1] Edit Product Details\n" +
                    "\t[2] Delete Product\n" +
                    "\t[3] Exit\n" + border +
                    "\nChoice: ");
            int choice = userIntInput();
            System.out.println(border);
            switch (choice) {
                case 1: {
                    while (true) {
                        tag();
                        System.out.println(border);
                        System.out.printf("%35s%n", "EDIT PRODUCT DETAILS");
                        System.out.println(border);

                        product.getInfo();

                        System.out.println(border);
                        System.out.println("\t[1] Product Name\n" +
                                "\t[2] Generic Name\n" +
                                "\t[3] Brand\n" +
                                "\t[4] Unit\n" +
                                "\t[5] Selling Price\n" +
                                "\t[6] Reorder Level\n" +
                                "\t[7] Category\n" +
                                "\t[0] Exit");
                        System.out.println(border);
                        System.out.print("Choose detail to edit: ");


                        choice = userIntInput();
                        switch (choice) {
                            case 1: {
                                System.out.println(border + "\n\n" +line);
                                System.out.print("Enter new Product Name: ");
                                String name = in.nextLine().trim();
                                System.out.println(line);

                                if (name.isEmpty()) {
                                    System.out.println("Product name cannot be empty!");
                                    break;
                                }

                                product.setProductName(name);
                                System.out.println("\n" + mborder + "\nProduct name updated!\n" + mborder);
                                break;
                            }
                            case 2: {
                                System.out.println(border + "\n\n" +line);
                                System.out.print("Enter new Generic Name: ");
                                String genericName = in.nextLine().trim();
                                System.out.println(line);

                                if (genericName.isEmpty()) {
                                    System.out.println("Generic name cannot be empty!");
                                    break;
                                }

                                product.setGenericName(genericName);
                                System.out.println("\n" + mborder + "\nGeneric name updated!\n" + mborder);
                                break;
                            }
                            case 3: {
                                System.out.println(border + "\n\n" +line);
                                System.out.print("Enter new Brand: ");
                                String brand = in.nextLine().trim();
                                System.out.println(line);

                                if (brand.isEmpty()) {
                                    System.out.println("Brand cannot be empty!");
                                    break;
                                }

                                product.setBrand(brand);
                                System.out.println("\n" + mborder + "\nBrand name updated!\n" + mborder);
                                break;
                            }
                            case 4: {
                                System.out.println(border + "\n\n" +line);
                                System.out.print("Enter new Unit: ");
                                String unit = in.nextLine().trim();
                                System.out.println(line);

                                if (unit.isEmpty()) {
                                    System.out.println("Unit cannot be empty!");
                                    break;
                                }

                                product.setUnit(unit);
                                System.out.println("\n" + mborder + "\nUnit updated!\n" + mborder);
                                break;
                            }
                            case 5: {
                                System.out.println(border + "\n\n" +line);
                                System.out.print("Enter new Selling Price: ");
                                double price = userDoubleInput();
                                System.out.println(line);

                                if (price < 0) {
                                    System.out.println("Price cannot be negative!");
                                    break;
                                }

                                product.setSellingPrice(price);
                                System.out.println("\n" + mborder + "\nSelling price updated!\n" + mborder);
                                break;
                            }
                            case 6: {
                                System.out.println(border + "\n\n" +line);
                                System.out.print("Enter new Reorder Level: ");
                                int reorderLevel = userIntInput();
                                System.out.println(line);

                                if (reorderLevel < 0) {
                                    System.out.println("Reorder level cannot be negative!");
                                    break;
                                }

                                product.setReorderLevel(reorderLevel);
                                System.out.println("\n" + mborder + "\nReorder level updated!\n" + mborder);
                                break;
                            }
                            case 7: {
                                System.out.println(border + "\n\n" +line);
                                System.out.print("Enter new Category ID: ");
                                String categoryID = in.nextLine().trim().toUpperCase();
                                System.out.println(line);

                                Category category = inventory.findCategoryById(categoryID);

                                if (category == null) {
                                    System.out.println("Category ID not found!");
                                    break;
                                }

                                product.setCategory(category);
                                System.out.println("\n" + mborder + "\nCategory updated!\n" + mborder);
                                break;
                            }
                            case 0:
                                System.out.println(border +"\n");
                                System.out.println(mborder);
                                System.out.println("Task cancelled!");
                                System.out.println(mborder);
                                return;

                            default:
                                System.out.println("Invalid choice!");
                        }
                    }
                }

                case 2:
                    if (inventory.removeProduct(productID)) {
                        System.out.println("\n"+mborder);
                        System.out.println("Product Deleted Successfully!");
                        System.out.println(mborder);
                    }

                case 3:
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
        System.out.printf("%34s\n","DISPLAY CATEGORIES");
        System.out.println(border);
        System.out.printf("%-3s %-6s %-15s %-23s%n", "#", "ID", "NAME", "DESCRIPTION");
        System.out.println(line);
        for (Category c : inventory.getCategories()) {
            System.out.printf("%-3d %-6s %-15.15s %-23.23s%n", counter, c.getCategory_ID(),
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
        System.out.printf("%32s\n","PRODUCT BATCHES");
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
        System.out.printf("%32s\n","PRODUCT DETAILS");
        System.out.println(line);

        product.getInfo();
        System.out.printf("%-15s: %d%n", "Total Stock: ", product.getTotalStock());

        System.out.println(line);
        System.out.printf("%31s\n","BATCH DETAILS");
        System.out.println(line);
        product.checkExpiration();
        System.out.println(line);
        System.out.println(border);
    }


    //TRANSACTION METHODS

    protected void viewTransactionHistory() {

        int counter = 1;
        tag();
        System.out.println(border);
        System.out.printf("%34s\n", "TRANSACTION HISTORY");
        System.out.println(border)
        ;
        for (Transaction t : inventory.getTransactions()) {

            System.out.println("Transaction #" + counter);
            System.out.println(line);

            System.out.printf("%-18s: %s\n",
                    "Transaction ID", t.getTransactionID());
            System.out.printf("%-18s: %s\n",
                    "Transaction Type", t.getTransactionType());
            System.out.printf("%-18s: %s\n",
                    "Transaction Date", t.getTransactionDate());
            System.out.printf("%-18s: %s\n",
                    "Transaction By", t.getEmployee().getName());

            System.out.println(line);
            counter++;

        }
        System.out.println(border);
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
            System.out.printf("%29s%n", "!ALERTS!");
            System.out.print("[EXPIRED] " + expiredProducts + " items");
            System.out.printf("%25s%n", "[LOW] " + lowStockProducts + " items");
            System.out.println(line);
        } else if (expiredProducts > 0 && lowStockProducts == 0) {
            System.out.println(line);
            System.out.printf("%29s%n", "!ALERTS!");
            System.out.println("[EXPIRED] " + expiredProducts + " items");
            System.out.println(line);
        } else if (expiredProducts == 0 && lowStockProducts > 0) {
            System.out.println(line);
            System.out.printf("%29s%n", "!ALERTS!");
            System.out.println("[LOW] " + lowStockProducts + " items");
            System.out.println(line);
        }
    }

    protected void searchProduct() {

        tag();
        System.out.println(border);
        System.out.printf("%32s%n", "SEARCH PRODUCT");
        System.out.println(border);


        System.out.print("\t[1]Search by ID\n" +
                "\t[2]Search by Name\n" +
                "\t[3]Search by Category\n" +
                "\t[0]Exit\n");
        System.out.println(border);
        System.out.print("Choice: ");
        int choice = userIntInput();
        System.out.println(border);
        switch (choice) {

            case 1: {

                System.out.println("\n"+line);
                System.out.print("Product ID: ");
                String productID = in.nextLine().trim().toUpperCase();
                System.out.println(line+"\n");

                Product product = inventory.findProductById(productID);

                if (product == null) {
                    System.out.println("\n"+mborder);
                    System.out.println("ProductID not found!");
                    System.out.println(mborder);
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

                if(searchProductByName()) {

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
                            System.out.println("\n"+mborder);
                            System.out.println("Invalid Input!");
                            System.out.println(mborder+"\n");
                            break;
                    }


                }



                break;

            }
            case 3 : {

                if(searchProductByCategory()) {

                System.out.println(border);
                System.out.print("\t[1]View More Product Details\n" +
                        "\t[0]Exit\n" +
                        border +
                        "\nChoice:");
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

    protected boolean searchProductByName() {

        System.out.print("\n" + line +
                "\nProduct Name: ");
        String productName = in.nextLine().trim().toUpperCase();
        System.out.println(line+"\n");
        if(productName.equals("0")) {
            return false;
        }

        ArrayList<Product> matchedProducts = inventory.findProductByName(productName);

        if(matchedProducts.isEmpty()) {
            System.out.println(mborder);
            System.out.println("No Product Name Match!");
            System.out.println(mborder);
            return false;
        }

        System.out.println(border);
        System.out.printf("%27s%n","SEARCH RESULTS");
        System.out.println(border);
        System.out.printf("%-8s %-33s%7s%n","ID","Name","Price");

        System.out.println(line);
        for(Product p : matchedProducts) {
            System.out.printf("%-8s %-33s%7.2f%n",p.getProductID(), p.getProductName(),p.getSellingPrice());
        }
        System.out.println(line);

        return true;
    }

    protected boolean searchProductByCategory() {

        System.out.println("\n"+line);
        System.out.print("Category Name/ID: ");
        String givenCategoryString = in.nextLine().trim().toUpperCase();
        System.out.println(line+"\n");

        ArrayList<Product> matchedProducts = inventory.findProductByCategory(givenCategoryString);

        if(matchedProducts.isEmpty()) {
            System.out.println(mborder);
            System.out.println("No Category Match");
            System.out.println(mborder);
            return false;
        }

        tag();
        System.out.println(border);
        System.out.printf("%32s%n","SEARCH RESULTS");
        System.out.println(border);
        System.out.printf("%-8s%-24s%8s  %-8s%n","ID","Name","Price", "Category");
        System.out.println(line);

        for(Product p : matchedProducts) {
            System.out.printf("%-8s%-24s%8.2f  %-8s%n",p.getProductID(), p.getProductName(),p.getSellingPrice(),p.getCategory().getCategory_Name());
        }
        System.out.println(line);

        return true;


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