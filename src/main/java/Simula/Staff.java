package Simula;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//tatanggalin ba tong dalawa sa taas?

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Staff {

    String line = "-".repeat(50);
    String border = "=".repeat(50);
    String mborder = "~".repeat(50);

    protected static final Scanner in = new Scanner(System.in); //share share na lahat ng subclass sa isang scanner, para walang duplicate scanners

    //protected final StartUp manager; // para share na rin ng pharmacist

    private String name;
    private String position; // "Owner", "Pharmacist", or "Staff"
    private String username;
    private String password;
    private static int nextStaffID = 1001;
    private String staffID;
    protected final Inventory inventory;

    public Staff(String name, String position, String password, String username, Inventory inventory) {
        this.staffID = String.valueOf(nextStaffID++);
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

    public String getStaffID() {
        return staffID;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    //a

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract void showDashboard();

    @Override
    public String toString() {
        return "ID no.: " + staffID + "    |    " + name + "\n";
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

        System.out.println("\n" + border);
        System.out.printf("%32s%n", "CHANGE PASSWORD");
        System.out.println(  border + "\nEnter Current password: ");
        String checkPassword = in.nextLine();

        if (checkPassword.equals(this.getPassword())) {

            String newPassword;

            while(true) {
                System.out.println(line);
                System.out.print("Enter new password: ");
                newPassword = in.nextLine();
                System.out.println(line);

                if (!newPassword.matches(".*[A-Z].*")) {
                    System.out.println(mborder);
                    System.out.printf("%48s\n", "Password must have at least one capital letter.");
                    System.out.println(mborder);
                }
                else if (!newPassword.matches(".*[0-9].*")) {
                    System.out.println(mborder);
                    System.out.printf("%44s\n", "Password must have at least one number.");
                    System.out.println(mborder);
                }
                else if (!newPassword.matches(".*[^a-zA-Z0-9\\s].*")) {
                    System.out.println(mborder);
                    System.out.printf("%49s\n", "Password must have at least one special character");
                    System.out.println(mborder);
                }
                else if (newPassword.length() < 10) {
                    System.out.println(mborder);
                    System.out.printf("%45s\n", "Password must have at least 10 characters");
                    System.out.println(mborder);
                }
                else {
                    break;
                }

            }

            setPassword(newPassword);
            System.out.println(mborder);
            System.out.printf("%40s\n","Password changed successfully!");
            System.out.println(mborder);
        } else {
            System.out.println(mborder);
            System.out.printf("%37s\n","Password does not match!");
            System.out.println(mborder);
        }

        System.out.println(border);

    }

    protected void displayProfile() {
        tag();
        System.out.println(border);
        System.out.printf("%30s\n", "MY PROFILE");
        System.out.println(border);

        System.out.println("Staff ID: " + getStaffID());
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
            System.out.print("\t[1] Input Product ID to Sell\n"+
                    "\t[2] Search Product Name\n" +
                    "\t[3] Checkout\n" +
                    border + "\nChoice: ");
            int choice = userIntInput();
            System.out.println(border);

            if(choice == 2) {
                if (!searchProductByName()) {
                    continue;
                }
            }

            else if (choice == 3) {
                break;
            }

            else if(choice!= 1 ) {
                System.out.println("Invalid Input!");
                continue;
            }

            Product product;
            while (true) {
                System.out.print("Product ID [0 to cancel]: ");
                String productID = in.nextLine().trim().toUpperCase();

                if (productID.equals("0")) {
                    end = true;
                    product = null;
                    break;
                }

                product = inventory.findProductById(productID);

                if (productID.isEmpty()) {
                    System.out.println("Product ID cannot be empty");
                }
                else if (product == null) {
                    System.out.println("Product not found. Try again.");
                }
                else break;
            }
            if (end) break;

            if (product instanceof Medicine && !((Medicine) product).soldBy(this)) {
                System.out.println(border + "\n");
                System.out.println(mborder);
                System.out.printf("%40s\n", "Only the Owner or a Pharmacist");
                System.out.printf("%40s\n", "can sell prescription medicine.");
                System.out.println(mborder);
                continue;
            }

            int availableStock = 0; // etong block na to, minamake sure na hindi kasama sa count yung expired na stock
            for (Batch b : product.getBatches()) {
                if (!b.isExpired()) {
                    availableStock += b.getQuantity();
                }
            }

            for (TransactionDetail detail : receipt.getTransactionDetails()) {
                if (detail.getProduct() == product) {
                    availableStock -= detail.getQuantity(); // already in this cart
                }
            }

            if (availableStock <= 0) {
                System.out.println("No more stock available for this product.");
                continue;
            }

            System.out.println(line);
            System.out.println("Product Name: " + product.getProductName());
            System.out.println("Selling Price: " + product.getSellingPrice());
            System.out.println("Available Stock: " + availableStock);

            int quantity;

            while (true) {
                System.out.print("Enter Quantity: ");
                quantity = userIntInput();

                if (quantity <= 0) {
                    System.out.println("Quantity must be greater than 0.");
                }
                else if (quantity > availableStock) {
                    System.out.println("Insufficient stock.\nAvailable: " + availableStock);
                }
                else break;
            }

            if(receipt.addTransactionDetail(quantity, product.getSellingPrice(), product)) {
                System.out.println("\n" + mborder);
                System.out.printf("%34s\n", "Added to Checkout!");
                System.out.println(mborder + "\n");
            }
            else {
                System.out.println(mborder);
                System.out.printf("%31s\n", "Input Failed!");
                System.out.printf("%45s\n", "Invalid Quantity, Cost, or Expiration Date");
                System.out.println(mborder);
            }

            System.out.println(border);

        }

        if (receipt.getTransactionDetails().isEmpty()) {
            System.out.println("No products added. Sale cancelled.");
            return;
        }
        boolean pwd = false;
        boolean senior = false;
        //taga check ng discount
        while (true) {
            System.out.printf("%33s","\nCUSTOMER DISCOUNT\n" +
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
                    System.out.println(border + "\n");
                    System.out.println(mborder);
                    System.out.printf("%38s\n", "Invalid Choice! try again...");
                    System.out.println(mborder);
                    continue;
            }
            break;
        }
        if (!inventory.recordSale(receipt)) {
            System.out.println(border + "\n");
            System.out.println(mborder);
            System.out.printf("%31s\n", "Sale Failed!");
            System.out.printf("%47s\n", "Not enough Stock, Expired Batch, or not Allowed");
            System.out.println(mborder);
            return;
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
            System.out.printf("%-40s%10.2f%n", "Total(with VAT):", receipt.getTotalWithVAT());
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
            System.out.printf("%34s\n", "Product not Found!");
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
                                System.out.println("\n" + mborder);
                                System.out.printf("%35s\n", "Product name updated!");
                                System.out.println(mborder);
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
                                System.out.println("\n" + mborder);
                                System.out.printf("%35s\n", "Generic name updated!");
                                System.out.println(mborder);
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
                                System.out.println("\n" + mborder);
                                System.out.printf("%34s\n", "Brand name updated!");
                                System.out.println(mborder);
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
                                System.out.println("\n" + mborder);
                                System.out.printf("%31s\n", "Unit updated!");
                                System.out.println(mborder);
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
                                System.out.println("\n" + mborder);
                                System.out.printf("%36s\n", "Selling price updated!");
                                System.out.println(mborder);
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
                                System.out.println("\n" + mborder);
                                System.out.printf("%36s\n", "Reorder level updated!");
                                System.out.println(mborder);
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
                                System.out.println("\n" + mborder);
                                System.out.printf("%33s\n", "Category updated!");
                                System.out.println(mborder);
                                break;
                            }
                            case 0:
                                System.out.println(border +"\n");
                                System.out.println(mborder);
                                System.out.printf("%32s\n", "Task cancelled!");
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
                        System.out.printf("%39s\n", "Product Deleted Successfully!");
                        System.out.println(mborder);
                    }

                case 3:
                    break;
                default:
                    System.out.println(mborder);
                    System.out.printf("%32s\n", "Invalid Input!");
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

        System.out.print("Enter Product ID[0 to View Products]: ");
        String productID = in.nextLine().trim().toUpperCase();
        if(productID.equals("0")) {
            displayProducts();
            System.out.print("Product ID: ");
            productID = in.nextLine().trim().toUpperCase();
            System.out.println(border);
        }

        Product product = inventory.findProductById(productID);

        if (product == null) {
            System.out.println(mborder);
            System.out.printf("%34s\n", "Product not found!");
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
                    "Transaction By", t.getStaff().getName());

            System.out.println(line);
            counter++;

        }
        System.out.println(border);
        if (counter == 0) System.out.println("\tNothing to see here...");

    }

    //checker ng low stock and expired

    protected void displayStockAlerts() {
        int expiredProducts = 0;
        int nearExpiryProducts =0;
        int lowStockProducts = 0;

        for (Product product : inventory.getProducts()) {
            boolean hasExpiredStock = false;
            int usableStock = 0;

            for (Batch batch : product.getBatches()) {
                if (batch.isExpired()) {
                    if (batch.getQuantity() > 0)
                        expiredProducts++;

                } else if (batch.isNearExpiration()) { {
                   if(batch.getQuantity()>0)
                    nearExpiryProducts++;
                   usableStock += batch.getQuantity();
                }

                } else {
                    usableStock += batch.getQuantity();
                }
            }

            if (usableStock < product.getReorderLevel()) {
                lowStockProducts++;
            }
        }

        if ((expiredProducts > 0 || nearExpiryProducts > 0) && lowStockProducts > 0) {
            System.out.println(line);
            System.out.printf("%28s%n", "!ALERTS!");
            System.out.print("[11] EXPIRED(" + (expiredProducts + nearExpiryProducts) + " items)");
            System.out.printf("%26s%n", "[12] LOW STOCK(" + lowStockProducts + " items)");
            System.out.println(line);
        } else if ((expiredProducts > 0 || nearExpiryProducts > 0) && lowStockProducts == 0) {
            System.out.println(line);
            System.out.printf("%28s%n", "!ALERTS!");
            System.out.println("[11] EXPIRED(" + (expiredProducts+ nearExpiryProducts) + " items)");
            System.out.println(line);
        } else if ((expiredProducts == 0 && nearExpiryProducts ==0) && lowStockProducts > 0) {
            System.out.println(line);
            System.out.printf("%28s%n", "!ALERTS!");
            System.out.println("[12] LOW STOCK(" + lowStockProducts + " items)");
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
                    System.out.printf("%35s\n", "ProductID not found!");
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
                            System.out.printf("%32s\n", "Invalid Input!");
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
                            System.out.printf("%32s\n", "Invalid Input!");
                            System.out.println(mborder);
                            break;
                    }

                }

                break;
            }

            case 0 : break;
            default: {
                System.out.println(mborder);
                System.out.printf("%32s\n", "Invalid Input!");
                System.out.println(mborder);
                break;
            }
        }

    }

    protected boolean searchProductByName() {

        String productName;
        ArrayList <Product> matchedProducts;

        while (true) {
            System.out.print("Product Name [0 to cancel]: ");
            productName = in.nextLine().trim().toUpperCase();

            if (productName.equals("0")) {
                return false;
            }

            if (productName.isEmpty()) {
                System.out.println("Product name cannot be empty.");
                continue;
            }
             matchedProducts = inventory.findProductByName(productName);

            if (matchedProducts.isEmpty()) {
                System.out.println(mborder);
                System.out.printf("%40s\n", "No matching product. Try again.");
                System.out.println(mborder);
            }
            else break;
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
        System.out.print("Category ID/Name[0 to View Categories]: ");
        String givenCategoryString = in.nextLine().trim().toUpperCase();
        if(givenCategoryString.equals("0")) {
            displayCategories();
            System.out.print("Category ID: ");
            givenCategoryString = in.nextLine().trim().toUpperCase();
            System.out.println(border);
        }

        System.out.println(line+"\n");

        ArrayList<Product> matchedProducts = inventory.findProductByCategory(givenCategoryString);

        if(matchedProducts.isEmpty()) {
            System.out.println(mborder);
            System.out.printf("%33s\n", "No Category Match");
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


    protected int returnNearExpiration() {

        if(!(this instanceof Owner)) {
            System.out.println(mborder);
            System.out.println("Owner Access Needed!");
            System.out.println(mborder);
            return 0;
        }

        int returnedBatches=0;
        for(Product p : inventory.getProducts()) {

            ArrayList<Batch> batches = p.getBatches();

            for(int i=batches.size()-1; i>=0;i--) {
                if(batches.get(i).isNearExpiration()) {
                    batches.remove(i);
                    returnedBatches++;
                }

            }
        }
        return returnedBatches;
    }

    protected int removeAllExpiredBatches() {

        if(!(this instanceof Owner)) {
            System.out.println(mborder);
            System.out.println("Owner Access Needed!");
            System.out.println(mborder);
            return 0;
        }

        int removed = 0;
        for (Product p : inventory.getProducts()) {

            ArrayList<Batch> batches = p.getBatches();

            for (int i=batches.size()-1; i>=0;i--) {
                if (batches.get(i).isExpired()) {
                    batches.remove(i);
                    removed++;
                }

            }

        }
        return removed;
    }


    protected void displayExpiredBatches() {

        System.out.println(border);
        System.out.printf("%31s%n", "EXPIRED BATCHES");
        System.out.println(border);

        for(Product p : inventory.getProducts()) {

            ArrayList<Batch> batches = p.getBatches();

            for(int i=0;i<batches.size();i++) {
                if(batches.get(i).isExpired()) {
                    System.out.printf("%27s%n", p.getProductName() );
                    System.out.println("\nBatch ID       : " + batches.get(i).getBatchID());
                    System.out.println("Quantity       : " + batches.get(i).getQuantity());
                    System.out.println("Expiration Date: " + batches.get(i).getExpirationDate());
                    System.out.println(mborder);

                }

            }

        }

        System.out.print("\t[1] Remove All Expired Batches\n" +
                "\t[0] Exit\n" + border +
                "\nChoice: ");
        int choice = userIntInput();

        switch (choice) {

            case 1 : System.out.println("[Removed] "+ removeAllExpiredBatches() + " Batches\n"+border); break;
            case 0 : return;
            default: {
                System.out.println(mborder);
                System.out.printf("%36s\n", "Invalid input. Try Again");
                System.out.println(mborder);
                pause();
                break;
            }
        }

    }



    protected void displayNearExpiryBatches () {

        System.out.println(border);
        System.out.printf("%31s%n", "NEAR EXPIRY BATCHES");
        System.out.println(border);

        for(Product p : inventory.getProducts()) {

            ArrayList<Batch> batches = p.getBatches();

            for(int i =0; i<batches.size();i++) {

                if(batches.get(i).isNearExpiration()) {

                    System.out.printf("%27s%n", p.getProductName() );
                    System.out.println("\nBatch ID       : " + batches.get(i).getBatchID());
                    System.out.println("Quantity       : " + batches.get(i).getQuantity());
                    System.out.println("Expiration Date: " + batches.get(i).getExpirationDate());
                    System.out.println(mborder);

                }

            }


        }

        System.out.print("\t[1] Return All Near Expiry Batches\n" +
                "\t[0] Exit\n" + border +
                "\nChoice: ");
        int choice = userIntInput();
        System.out.println(border);

        switch (choice) {

            case 1 :
                System.out.println("[Returned] "+ returnNearExpiration() + " Batches\n"+border); break;
            case 0 : return;
            default: {
                System.out.println(mborder);
                System.out.printf("%36s\n", "Invalid input. Try Again");
                System.out.println(mborder);
                pause();
                break;
            }
        }


    }

    protected void displayLowStockProducts() {

        System.out.println(border);
        System.out.printf("%31s%n", "LOW STOCK PRODUCTS");
        System.out.println(border);

        for(Product p : inventory.getProducts()) {
            int totalProductStock = 0;

            ArrayList<Batch> batches = p.getBatches();

            for(Batch b : batches) {
                if(!b.isExpired() || !b.isNearExpiration()) {
                    totalProductStock += b.getQuantity();
                }
            }

            if(totalProductStock<p.getReorderLevel()) {
                int stockNeeded = p.getReorderLevel()-totalProductStock;

                System.out.printf("%27s%n", p.getProductName() );
                System.out.println("\nProduct ID      : " + p.getProductID());
                System.out.println("Available Stock : " + totalProductStock);
                System.out.println("Reorder Level   : " + p.getReorderLevel());
                System.out.println("Stock Needed    : " + stockNeeded );
                System.out.println(mborder);

            }

        }
    }

    //MENUS _________________________________________________________________________________________________________________


    protected void expiredItemsMenu() {

        System.out.println("\n"+ border);

        System.out.printf("%31s%n", "EXPIRATION" );
        System.out.print(border +
                "\n\t[1] View Expired Batches\n" +
                "\t[2] View Near Expiry Batches\n" +
                "\t[0] Exit\n" + border +
                "\nChoice: ");
        int choice = userIntInput();

        switch (choice) {

            case 1 : displayExpiredBatches(); pause(); break;
            case 2 : displayNearExpiryBatches(); pause(); break;
            case 0 : return;
            default: {
                System.out.println(mborder);
                System.out.printf("%36s\n", "Invalid input. Try Again");
                System.out.println(mborder);
                pause();
                break;
            }
        }

    }

    protected void moreProductDetailsMenu() {

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
                System.out.printf("%32s\n", "Invalid Input!");
                System.out.println(mborder);
                break;
        }


    }




}