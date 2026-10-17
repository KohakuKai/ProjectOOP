package Simula;

import java.time.LocalDate;
import java.util.Locale;

public class Owner extends Employee {

    String firstName = getName().trim().split(" ")[0];
    String userInfo = "[" + firstName + "|" + getPosition() + "]";


    private final StartUp manager;

    public Owner(String name, StartUp manager, String password, String username,Inventory inventory) {
        super(name, "Owner", password, username, inventory);
        this.manager = manager;

    }


    @Override
    public void showDashboard() {
        while (true) {
            System.out.printf("\n%33s\n", userInfo);
            System.out.println(border);
            System.out.println("\t\t\tPHARMACY");
            System.out.println(border);

            System.out.print("PERSON\n"+
                    "\t[1] Manage Employee\n" +
                    "INVENTORY\n"+
                    "\t[2] Manage Products\n" +
                    "\t[3] Manage Categories\n" +
                    "\t[4] Manage Stocks\n" +
                    "TRANSACT\n" +
                    "\t[5] Sell product\n" +
                    "\t[6] View Transaction History\n "+
                    "ACCOUNT\n" +
                    "\t[7] Change Password\n" +
                    "\t[8] Logout\n" +
                    border + "\n" +
                    "Choice: ");
            int choice = in.nextInt();
            in.nextLine(); // consume the leftover Enter
            System.out.println(border+"\n");

            switch (choice) {
                case 1: {

                    System.out.printf("\n%33s\n", userInfo);
                    System.out.println(border);
                    System.out.println("\t\tMANAGE EMPLOYEE");
                    System.out.println(border);
                    System.out.print("\t[1] Create Employee\n" +
                            "\t[2] View Employee\n" +
                            "\t[3] Delete Employee\n" +
                            "\t[0] Exit\n" +
                            border+ "\n" +
                            "Choice: ");
                    int temp = in.nextInt();
                    in.nextLine();
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
                    }
                    break;
                }

                case 2 :{

                    System.out.printf("\n%33s\n", userInfo);
                    System.out.println(border);
                    System.out.println("\t\tMANAGE PRODUCTS");
                    System.out.println(border);
                    System.out.print("\t[1]Add Product\n\t[2]Display Products\n\t[0] Exit\n"+border+"\nChoice:");
                    int choice2 = in.nextInt();
                    in.nextLine();

                    switch(choice2) {

                        case 1 : addProduct(); pause(); break;
                        case 2 : displayProducts(); pause(); break;
                        case 0 : break;
                        default : {
                            System.out.println(mborder);
                            System.out.println("Invalid input. Try Again");
                            System.out.println(mborder);
                            break;
                        }
                    }
                    break;
                }

                case 3 : {

                    System.out.printf("\n%33s\n", userInfo);
                    System.out.println(border);
                    System.out.println("\t\tMANAGE CATEGORIES");
                    System.out.println(border);
                    System.out.println("\t[1]Add Category\t[2]Display Categories\t[0] Exit\nChoice:");
                    int choice2 = in.nextInt();
                    in.nextLine();

                    switch(choice2) {

                        case 1 : addCategory(); pause(); break;
                        case 2 : displayCategories(); pause(); break;
                        case 0 : break;
                        default : {
                            System.out.println(mborder);
                            System.out.println("Invalid input. Try Again");
                            System.out.println(mborder);
                            break;
                        }
                    }
                    break;

                }
                case 4:{
                    System.out.printf("\n%33s\n", userInfo);
                    System.out.println(border);
                    System.out.println("\t\tMANAGE STOCKS");
                    System.out.println(border);
                    System.out.println("\t[1]Add Batch\n" +
                            "\t[2]Add Stock\n" +
                            "\t[3]Reduce Stock\n" +
                            "\t[4]View a Product's Batches\n" +
                            "\t[0] Exit\n"+
                            "Choice: ");
                    int choice4 = in.nextInt();
                    in.nextLine();

                    switch(choice4){
                        case 1:{
                            addBatch();
                            pause();
                            break;
                        }
                        case 2:{
                            addSupply();
                            pause();
                            break;
                        }
                        case 3:{
                            reduceSupply();
                            pause();
                            break;
                        }
                        case 4:{
                            viewProductBatchDetails();
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

                case 6:{
                    viewTransactionHistory();
                    pause();
                    break;
                }

                case 7:{
                    changePassword();
                    pause();
                    break;
                }

                case 8: {
                    System.out.println(mborder);
                    System.out.println("Logging out...");
                    System.out.println(mborder);
                    return;
                }

                default: {
                    System.out.println(mborder);
                    System.out.println("Invalid choice. Please try again.");
                    System.out.println(mborder);
                    break;
                }
            }
        }
    }

    //create employee ng owner yan ah
    private void createEmployee() {
        System.out.printf("\n%33s\n", userInfo);
        System.out.println(border);
        System.out.println("\t\tCREATE EMPLOYEE");
        System.out.println(border);
        System.out.print("Enter employee name: ");
        String name = in.nextLine().trim();

        System.out.print("Enter employee username: ");
        String username = in.nextLine().trim();

        System.out.print("Enter employee password: ");
        String password = in.nextLine();

        System.out.println("\nAssign position:");
        System.out.println("\t[1] Owner");
        System.out.println("\t[2] Pharmacist");
        System.out.println("\t[3] Staff");
        System.out.println(border);
        System.out.print("Choice: ");
        int choice = in.nextInt();
        in.nextLine(); // consume the leftover Enter
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
        System.out.printf("\n%33s\n", userInfo);
        System.out.println(border);
        System.out.println("\t\tDISPLAY EMPLOYEE");
        System.out.println(border);
        System.out.printf("%-7s  %-15s  %-15s%n",
                "ID NO.", "NAME", "POSITION");
        System.out.println(line);
        for (Employee employee : manager.getEmployees()) {
            System.out.printf("%-7s  %-15s  %-15s%n",
                    employee.getEmpID(),
                    employee.getName(),
                    employee.getPosition());
        }
        System.out.println(line);
        System.out.println(border);
    }

    //delete employee
    private void deleteEmployee() {
        System.out.printf("\n%33s\n", userInfo);
        System.out.println(border);
        System.out.println("\t\tDELETE EMPLOYEE");
        System.out.println(border);
        System.out.printf("%-7s | %-15s | %-15s%n",
                "ID NO.", "NAME", "POSITION");

        for (Employee employee : manager.getEmployees()) {
            System.out.printf("%-7s | %-15s | %-15s%n",
                    employee.getEmpID(),
                    employee.getName(),
                    employee.getPosition());
        }
        System.out.println(border);

        System.out.print("Enter employee ID to delete: ");
        String empID = in.nextLine().trim();
        System.out.println("\n");

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
                return new Owner(name,manager, password, username,inventory);

            case 2:
                return new Pharmacist(name, password, username,inventory);

            case 3:
                return new Staff(name, password, username,inventory);

            default:
                return null;
        }
    }

    //Product Methods
    private void addProduct() {

        System.out.printf("\n%33s\n", userInfo);
        System.out.println(border);
        System.out.println("\t\tADD PRODUCT");
        System.out.println(border);

        System.out.print("Product Name: ");
        String productName = in.nextLine().trim();

        System.out.print("Generic Name: ");
        String genericName = in.nextLine().trim();

        System.out.print("Brand: ");
        String brand = in.nextLine().trim();

        System.out.print("Unit: ");
        String unit = in.nextLine().trim();

        System.out.print("Selling Price: ");
        double sellingPrice = in.nextDouble();
        in.nextLine();

        System.out.print("Reorder Level: ");
        int reorderLevel = in.nextInt();
        in.nextLine();

        System.out.print("Category ID: ");
        String categoryID = in.nextLine().trim().toUpperCase();

        Category category = manager.getInventory().findCategoryById(categoryID); //dito hinanap yugn actual category object using String CategoryID

        if( productName.isEmpty() || sellingPrice<0 || reorderLevel <0 || category ==null) { //validation kung tama ba yung details nilagay sa fields
            System.out.println(mborder);
            System.out.println("Invalid Input in one of the Fields. Please Try Again.");
            System.out.println(mborder);
            return;
        }

        System.out.print("\nProduct Type:\n[1]Medicine\n[2]Essentials\n[3]Exit\nChoice: "); //pagpili ng product type
        int choice  = in.nextInt();
        in.nextLine();

        Product newProduct = null;
        switch(choice) {

            case 1 : {

                System.out.print("\nMedicine Type:\n[1]Over-The-Counter\n[2]Prescription Medicine\n[3]Exit\nChoice: "); //if medicine papapiliin kung OTP or PRESCRIPTION
                int choice2  = in.nextInt();
                in.nextLine();

                switch(choice2) {

                    case 1: {
                         newProduct = new OverTheCounter(productName,genericName,brand,unit,sellingPrice,reorderLevel,category);
                        break;
                    }
                    case 2: {
                         newProduct = new PrescriptionMedicine(productName,genericName,brand,unit,sellingPrice,reorderLevel,category);
                        break;
                    }
                    case 3: {
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
                break;
            }
            case 3 : {
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
                System.out.println(mborder);
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

        System.out.printf("\n%33s\n", userInfo);
        System.out.println("=================================");
        System.out.println("\t\tADD CATEGORY");
        System.out.println("=================================");

        System.out.print("Category Name: ");
        String categoryName = in.nextLine().trim();

        System.out.print("Category Description: ");
        String description = in.nextLine().trim();

        if( categoryName.isEmpty()) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Category ID and Category Name are Required!");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            return;
        }

        Category newCategory = new Category(categoryName,description); //dito gumawa ng bagong object

        if(manager.getInventory().addCategory(this,newCategory)) { //dito pinasok yung object(eto yung inventory class)
            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + "\nCategory Added Successfully!" +"\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        else {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Unable to Add the Category!");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }

    }


    private void addBatch(){

        System.out.print("Enter Product ID: ");
        String ProductID = in.nextLine().trim().toUpperCase();

        //hahanapin yung product gamit product id
        Product product = manager.getInventory().findProductById(ProductID);

        if(product == null){
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Unable to find product!");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            return;
        }

        //pag initialize ng variables

        System.out.print("Enter quantity: ");
        int quantity = in.nextInt(); in.nextLine();
        System.out.print("Enter year of expiration: ");
        int yearExpiration = in.nextInt(); in.nextLine();
        System.out.print("Enter month of expiration: ");
        int monthExpiration = in.nextInt(); in.nextLine();
        System.out.print("Enter day of expiration: ");
        int dayExpiration = in.nextInt(); in.nextLine();

        if(monthExpiration<1 || dayExpiration <1 || monthExpiration > 12 || dayExpiration >31) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.print("Invalid Date");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            return;
        }

        Batch addbatch = new Batch( quantity, product, LocalDate.of(yearExpiration, monthExpiration, dayExpiration));
        if(product.addBatch(addbatch)) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Batch added successfully!");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        else{
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Failed to add new batch");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
    }

    private void addSupply() {
        System.out.print("Enter Product ID: ");
        String productID = in.nextLine().trim().toUpperCase();

        Product product = manager.getInventory()
                .findProductById(productID);

        if (product == null) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Product not found!");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            return;
        }

        System.out.print("Enter Batch ID: ");
        String batchID = in.nextLine().trim().toUpperCase();

        Batch batch = product.findBatchById(batchID);

        if (batch == null) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Batch not found!");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            return;
        }

        System.out.print("Enter quantity to add: ");
        int amount = in.nextInt();
        in.nextLine();

        if(batch.increaseQuantity(amount)) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Stock Increased Succesfully!");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        else {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Invalid Amount.");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
    }

    private void reduceSupply() {
        System.out.print("Enter Product ID: ");
        String productID = in.nextLine().trim().toUpperCase();

        Product product = manager.getInventory()
                .findProductById(productID);

        if (product == null) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Product not found!");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            return;
        }

        System.out.print("Enter Batch ID: ");
        String batchID = in.nextLine().trim().toUpperCase();

        Batch batch = product.findBatchById(batchID);

        if (batch == null) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Batch not found!");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            return;
        }

        System.out.print("Enter quantity to reduce: ");
        int amount = in.nextInt();
        in.nextLine();

        if (batch.reduceQuantity(amount)) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Stock Reduced Succesfully!");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        else{
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Invalid Amount.");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
    }

}

