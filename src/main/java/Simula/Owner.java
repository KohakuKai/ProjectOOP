package Simula;


import java.util.Scanner;

public class Owner extends Employee {
    Scanner in = new Scanner (System.in);

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
                    "[2] Change Password\n" +
                    "[3] Logout\n" +
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
                            continue;
                        }
                    }
                    break;
                }
                case 2:{
                    System.out.print("\n=== CHANGE PASSWORD ===\nEnter Current password: ");
                    String checkPassword = in.nextLine();

                    if(checkPassword.equals(this.getPassword())) {

                        System.out.print("Enter new password: ");
                        String newPassword = in.nextLine();

                        setPassword(newPassword);
                        System.out.println("Password changed successfully!");
                    }

                    else System.out.println("Password does not match!");
                    break;
                }

                case 3: {
                    System.out.println("Logging out...");
                    return;
                }

                default: {
                    System.out.println("Invalid choice. Please try again.");
                    showDashboard();
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
}

