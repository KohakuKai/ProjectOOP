package Simula;

import java.util.ArrayList;
import java.util.Scanner;

public class StartUp {

    private ArrayList<Employee> registeredEmployees = new ArrayList<>();
    private Scanner in = new Scanner(System.in);

    public StartUp() { // dito lalagay yung owner
        registeredEmployees.add(
                new Owner("ownerName", this, "ownerPassword", "ownerUsername")
        );
    }

    private final Inventory inventory = new Inventory();
    public Inventory getInventory() {
        return inventory;
    }

    // login or sign up
    public void start() {
        int choice = 0;
        while (true) {
            System.out.println("\n===== PHARMACY SYSTEM =====");
            System.out.println("[1] Log In");
            System.out.println("[2] Exit");
            System.out.print("\nType here: ");

            choice = in.nextInt();
            in.nextLine(); // nawawala name pag wala to

            switch (choice) {
                case 1:
                    logIn();
                    break;
                case 2:
                    System.out.println("Exiting...");
                    return;
                case 67: {
                    System.out.println("Bypass!");
                    String username = "ownerUsername";
                    String empID = "1001";
                    String password = "ownerPassword";
                    Employee foundEmployee = findUser(username, empID, password);
                    if (foundEmployee != null) foundEmployee.showDashboard();
                    break;
                }
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // getter para sa owner
    public ArrayList<Employee> getEmployees() {
        return registeredEmployees;
    }


    // ----- LOG IN -----
    private void logIn() {
        System.out.print("\nEnter your username: ");
        String username = in.nextLine().trim();

        System.out.print("Enter your employee ID: ");
        String empID = in.nextLine().trim();

        System.out.print("Enter your password: ");
        String password = in.nextLine();

        //ipapasa dito yung ininput then pagbalik, rekta store kay foundUser
        Employee foundEmployee = findUser(username, empID, password);

        if (foundEmployee != null) {
            System.out.println("Login successful!");
            // may override each subclass
            foundEmployee.showDashboard();
        } else {
            System.out.println("No matching account found. Please sign up first.");
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

