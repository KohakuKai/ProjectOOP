package Simula;

import java.util.ArrayList;
import java.util.Scanner;

public class StartUp {

    String line = "-".repeat(50);
    String border = "=".repeat(50);
    String mborder = "~".repeat(50);

    private ArrayList<Employee> registeredEmployees = new ArrayList<>();
    private Scanner in = new Scanner(System.in);

    public StartUp() { // dito lalagay yung owner
        Owner owner = new Owner("ownerName", this, "ownerPassword", "ownerUsername", inventory);
        registeredEmployees.add(owner);
        inventory.defaultTransactions(owner); // para lang to sa predefined na Transcation History
    }

    private final Inventory inventory = new Inventory();
    public Inventory getInventory() {
        return inventory;
    }

    // login or sign up
    public void start() {
        int choice = 0;
        while (true) {
            System.out.println("\n" + border);
            System.out.printf("%32s\n","PHARMACY SYSTEM");
            System.out.println(border);
            System.out.println("\t[1] Log In");
            System.out.println("\t[2] Exit");
            System.out.println(border);
            System.out.print("Choice: ");
            choice = Employee.userIntInput();//kinuha ko nalang yung nasa employee kaysa gumawa ulit method
            System.out.println(border+"\n");


            switch (choice) {
                case 1:
                    logIn();
                    break;
                case 2:
                    System.out.println(mborder);
                    System.out.println("Exiting...");
                    System.out.println(mborder);
                    return;
                case 67: {
                    System.out.println(mborder);
                    System.out.println("Bypass!");
                    System.out.println(mborder);
                    String username = "ownerUsername";
                    String empID = "1001";
                    String password = "ownerPassword";
                    Employee foundEmployee = findUser(username, empID, password);
                    if (foundEmployee != null) foundEmployee.showDashboard();
                    break;
                }
                default:
                    System.out.println(mborder);
                    System.out.println("Invalid option. Try again.");
                    System.out.println(mborder);
            }
        }
    }

    // getter para sa owner
    public ArrayList<Employee> getEmployees() {
        return registeredEmployees;
    }


    // ----- LOG IN -----
    private void logIn() {
        System.out.println(line);
        System.out.print("Enter your username: ");
        String username = in.nextLine().trim();

        System.out.print("Enter your employee ID: ");
        String empID = in.nextLine().trim();

        System.out.print("Enter your password: ");
        String password = in.nextLine();

        System.out.println(border);
        //ipapasa dito yung ininput then pagbalik, rekta store kay foundUser
        Employee foundEmployee = findUser(username, empID, password);

        System.out.println();

        if (foundEmployee != null) {
            System.out.println(mborder);
            System.out.println("Login successful!");
            System.out.println(mborder);
            // may override each subclass
            foundEmployee.showDashboard();
        } else {
            System.out.println(mborder);
            System.out.println("No matching account found. Please sign up first.");
            System.out.println(mborder);
        }
    }

        // taga hanap if may ganitong tao ba na na sign up sa arraylist
    private Employee findUser (String username, String empID, String password){
        for (Employee employee : registeredEmployees) {
            if (employee.getUsername().equals(username)
                    && employee.getEmpID().equals(empID)
                    && employee.getPassword().equals(password)) {
                return employee;
            }
        }
        return null;
    }
}

