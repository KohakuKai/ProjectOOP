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
    //create employee ng owner yan ah
    public void createEmployee() {
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

        registeredEmployees.add(newEmployee);

        System.out.println("Employee created successfully!");
        System.out.println("Employee ID: " + newEmployee.getEmpID());
        System.out.println("Position: " + newEmployee.getPosition());
    }

    // pinagpasahan ng input. taga gawa object
    private Employee createUserByChoice(int choice, String name,
                                        String username, String password) {
        switch (choice) {
            case 1:
                return new Owner(name, this, password, username);

            case 2:
                return new Pharmacist(name, password, username);

            case 3:
                return new Staff(name, password, username);

            default:
                return null;
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

