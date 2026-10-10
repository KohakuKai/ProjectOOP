package Simula;

import java.time.LocalDate;

public class Owner extends Employee {


    private final StartUp manager;

    public Owner(String name, StartUp manager, String password, String username) {
        super(name, "Owner", password, username);
        this.manager = manager;

    }

    @Override
    public void showDashboard() {
        while (true) {
            System.out.println("\n=== OWNER DASHBOARD ===");
            System.out.println(this);

            System.out.print("\n[1] Manage Employee\n" +
                    "[2] Manage Products\n" +
                    "[3] Manage Categories\n" +
                    "[4] Manage Stocks\n" +
                    "[5] Change Password\n" +
                    "[6] Logout\n" +
                    "Choice: ");
            int choice = in.nextInt();
            in.nextLine(); // consume the leftover Enter

            switch (choice) {
                case 1: {

                    System.out.print("\n[1] Create Employee\n" +
                            "[2] Delete Employee\n" +
                            "[3] View Employee\n" +
                            "[0] Exit\n" +
                            "Choice: ");
                    int temp = in.nextInt();
                    in.nextLine();
                    switch (temp) {
                        case 1: {
                            createEmployee();
                            break;
                        }
                        case 2: {
                            deleteEmployee();
                            break;
                        }
                        case 3: {
                            displayEmployees();
                            break;
                        }
                        case 0: {
                            break;
                        }
                    }
                    break;
                }

                case 2 :{

                    System.out.print("\n=== Manage Products ===\n\n[1]Add Product\n[2]Display Products\n[0] Exit\nChoice:");
                    int choice2 = in.nextInt();
                    in.nextLine();

                    switch(choice2) {

                        case 1 : addProduct(); break;
                        case 2 : displayProducts(); break;
                        case 0 : break;
                        default : {
                            System.out.println("Invalid input. Try Again");
                            break;
                        }
                    }
                    break;
                }

                case 3 : {

                    System.out.print("\n=== Manage Categories ===\n\n[1]Add Category\n[2]Display Categories\n[0] Exit\nChoice:");
                    int choice2 = in.nextInt();
                    in.nextLine();

                    switch(choice2) {

                        case 1 : addCategory(); break;
                        case 2 : displayCategories(); break;
                        case 0 : break;
                        default : {
                            System.out.println("Invalid input. Try Again");
                            break;
                        }
                    }
                    break;

                }
                case 4:{
                    System.out.print("\n===Manage Stock ===\n\n" +
                            "[1]Add Batch\n" +
                            "[2]Add Stock\n" +
                            "[3]Reduce Stock\n" +
                            "[4]View a Product's Batch  es\n" +
                            "Choice: ");
                    int choice4 = in.nextInt();
                    in.nextLine();

                    switch(choice4){
                        case 1:{
                            addBatch();
                            break;
                        }
                        case 2:{
                            addSupply();
                            break;
                        }
                        case 3:{
                            reduceSupply();
                            break;
                        }
                        case 4:{
                            viewProductBatchDetails();
                            break;
                        }
                        default:{
                            System.out.println("Invalid input. Try Again");
                            break;
                        }

                    }
                    break;
                }
                case 5:{
                    changePassword();
                    break;
                }

                case 6: {
                    System.out.println("Logging out...");
                    return;
                }

                default: {
                    System.out.println("Invalid choice. Please try again.");
                    break;
                }
            }
        }
    }

    //create employee ng owner yan ah
    private void createEmployee() {
        System.out.print("\nEnter employee name: ");
        String name = in.nextLine().trim();

        System.out.print("Enter employee username: ");
        String username = in.nextLine().trim();

        System.out.print("Enter employee password: ");
        String password = in.nextLine();

        System.out.println("\nAssign position:");
        System.out.println("[1] Owner");
        System.out.println("[2] Pharmacist");
        System.out.println("[3] Staff");
        System.out.print("Choice: ");
        int choice = in.nextInt();
        in.nextLine(); // consume the leftover Enter

        Employee newEmployee = createUserByChoice(choice, name, username, password);
        if (newEmployee == null) {
            System.out.println("Invalid position. Employee creation cancelled.");
            return;
        }

        manager.getEmployees().add(newEmployee);

        System.out.println("Employee created successfully!");
        System.out.println("Employee ID: " + newEmployee.getEmpID());
        System.out.println("Position: " + newEmployee.getPosition());
    }
    private void displayEmployees() {
        for (Employee employee : manager.getEmployees()) {
            System.out.println(employee);
        }
    }

    //delete employee
    private void deleteEmployee() {
        displayEmployees();

        System.out.print("\nEnter employee ID to delete: ");
        String empID = in.nextLine().trim();

        for (Employee employee : manager.getEmployees()) {
            if (employee.getEmpID().equals(empID)) {

                if (employee == this) {
                    System.out.println("You cannot delete your own account.");
                    return;
                }

                manager.getEmployees().remove(employee);
                System.out.println("Employee deleted successfully!");
                return;
            }
        }

        System.out.println("Employee ID not found.");
    }

    // pinagpasahan ng input. taga gawa object
    private Employee createUserByChoice(int choice, String name,
                                        String username, String password) {
        switch (choice) {
            case 1:
                return new Owner(name,manager, password, username);

            case 2:
                return new Pharmacist(name, password, username);

            case 3:
                return new Staff(name, password, username);

            default:
                return null;
        }
    }

    //Product Methods
    private void addProduct() {

        System.out.println("=== Add Product ===\n");

        System.out.print("Product ID: ");
        String productID = in.nextLine().trim().toUpperCase();

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
        String categoryID = in.nextLine().trim();

        Category category = manager.getInventory().findCategoryById(categoryID); //dito hinanap yugn actual category object using String CategoryID

        if(productID.isEmpty() || productName.isEmpty() || sellingPrice<0 || reorderLevel <0 || category ==null) { //validation kung tama ba yung details nilagay sa fields
            System.out.println("Invalid Input in one of the Fields. Please Try Again.");
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
                         newProduct = new OverTheCounter(productID,productName,genericName,brand,unit,sellingPrice,reorderLevel,category);
                        break;
                    }
                    case 2: {
                         newProduct = new PrescriptionMedicine(productID,productName,genericName,brand,unit,sellingPrice,reorderLevel,category);
                        break;
                    }
                    case 3: {
                        break;
                    }
                    default: {
                        System.out.print("Invalid choice. Please try again.");
                        break;
                    }
                }
                break;
            }
            case 2 : { //kung Essentials, diretso create na
                 newProduct = new Essentials(productID,productName,genericName,brand,unit,sellingPrice,reorderLevel,category);
                break;
            }
            case 3 : {
                break;
            }
            default: {
                System.out.print("Invalid choice. Please try again.");
                break;
            }
        }

        if(newProduct!= null) {
            if (manager.getInventory().addProduct(this, newProduct)) {
                System.out.println("Product Added Successfully");
            } else {
                System.out.println("Unable to add the Product");
            }
        }

    }


    private void displayProducts () {

        System.out.println("=== Display Products ===\n");

        int counter = 1;
        for(Product p : manager.getInventory().viewProducts()) {
            System.out.println("\nPRODUCT#"+counter +
                    "\nProduct ID: " + p.getProductID() +
                    "\nProduct Name: " + p.getProductName() +
                    "\nGeneric Name: " + p.getGenericName() +
                    "\nBrand : " + p.getBrand() +
                    "\nUnit: " + p.getUnit() +
                    "\nSelling Price: P" + p.getSellingPrice()+
                    "\nReorder Level: " + p.getReorderLevel());
            counter++;
        }

    }

    //methods ng Category
    private void addCategory() {

        System.out.println("=== Add Category ===\n");

        System.out.print("Category ID: ");
        String categoryID = in.nextLine().trim().toUpperCase();

        System.out.print("Category Name: ");
        String categoryName = in.nextLine().trim();

        System.out.print("Category Description: ");
        String description = in.nextLine().trim();

        if(categoryID.isEmpty() || categoryName.isEmpty()) {
            System.out.println("Category ID and Category Name are Required!");
            return;
        }

        Category newCategory = new Category(categoryID,categoryName,description); //dito gumawa ng bagong object

        if(manager.getInventory().addCategory(this,newCategory)) { //dito pinasok yung object(eto yung inventory class)
            System.out.println("\nCategory Added Successfully!");
        }
        else {
            System.out.println("Unable to Add the Category!");
        }

    }

    private void displayCategories() {

        System.out.println("=== Display Categories ===");
        int counter = 1;

        for(Category c : manager.getInventory().viewCategories()) {
            System.out.println("\nCATEGORY#"+counter+
                    "\nCategory ID: "+ c.getCategory_ID() +
                    "\nCategory Name: "+ c.getCategory_Name()+
                    "\nCategory Description: "+ c.getDescription());
            counter++;

        }

    }

    private void addBatch(){
        System.out.print("Enter Product ID: ");
        String ProductID = in.nextLine().trim().toUpperCase();

        //hahanapin yung product gamit product id
        Product product = manager.getInventory().findProductById(ProductID);

        if(product == null){
            System.out.println("Unable to find product!");
            return;
        }

        //pag initialize ng variables
        System.out.print("Enter batch ID: ");
        String batchID = in.nextLine().trim().toUpperCase();
        System.out.print("Enter quantity: ");
        int quantity = in.nextInt(); in.nextLine();
        System.out.print("Enter year of expiration: ");
        int yearExpiration = in.nextInt(); in.nextLine();
        System.out.print("Enter month of expiration: ");
        int monthExpiration = in.nextInt(); in.nextLine();
        System.out.print("Enter day of expiration: ");
        int dayExpiration = in.nextInt(); in.nextLine();

        if(monthExpiration<1 || dayExpiration <1 || monthExpiration > 12 || dayExpiration >31) {
            System.out.print("Invalid Date");
            return;
        }

        Batch addbatch = new Batch(batchID, quantity, product, LocalDate.of(yearExpiration, monthExpiration, dayExpiration));
        if(product.addBatch(addbatch)) {
            System.out.println("Batch added successfully!");
        }
        else{
            System.out.println("Failed to add new batch");
        }
    }

    private void addSupply() {
        System.out.print("Enter Product ID: ");
        String productID = in.nextLine().trim().toUpperCase();

        Product product = manager.getInventory()
                .findProductById(productID);

        if (product == null) {
            System.out.println("Product not found!");
            return;
        }

        System.out.print("Enter Batch ID: ");
        String batchID = in.nextLine().trim().toUpperCase();

        Batch batch = product.findBatchById(batchID);

        if (batch == null) {
            System.out.println("Batch not found!");
            return;
        }

        System.out.print("Enter quantity to add: ");
        int amount = in.nextInt();
        in.nextLine();

        batch.increaseQuantity(amount);

    }

    private void reduceSupply() {
        System.out.print("Enter Product ID: ");
        String productID = in.nextLine().trim().toUpperCase();

        Product product = manager.getInventory()
                .findProductById(productID);

        if (product == null) {
            System.out.println("Product not found!");
            return;
        }

        System.out.print("Enter Batch ID: ");
        String batchID = in.nextLine().trim().toUpperCase();

        Batch batch = product.findBatchById(batchID);

        if (batch == null) {
            System.out.println("Batch not found!");
            return;
        }

        System.out.print("Enter quantity to reduce: ");
        int amount = in.nextInt();
        in.nextLine();

        batch.reduceQuantity(amount);

    }

    private void viewProductBatchDetails() {
        System.out.print("Enter Product ID: ");
        String productID = in.nextLine().trim().toUpperCase();

        Product product = manager.getInventory().findProductById(productID);

        if (product == null) {
            System.out.println("Product not found!");
            return;
        }

        System.out.println("\n=== Product Details ===");
        product.getInfo();

        System.out.println("\n=== Batch Details ===");
        product.checkExpiration();
    }

}

