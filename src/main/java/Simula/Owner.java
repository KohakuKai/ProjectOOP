package Simula;


import java.util.Scanner;

public class Owner extends User {
    Scanner in = new Scanner (System.in);

    private final StartUp manager;

    public Owner(String name, StartUp manager, String password, String username) {
        super(name, "Owner", password, username);
        this.manager = manager;

    }

    @Override
    public void showDashboard() {
        System.out.println("\n=== OWNER DASHBOARD ===");
        System.out.println(getEmpID() + getName());
        System.out.println(this);

        System.out.print("[1] View Employees\n[2] Logout\nChoice: ");
        int choice = in.nextInt();
        in.nextLine(); // consume the leftover Enter

        switch (choice) {
            case 1: {
                displayEmployees();
                break;
            }
            case 2: {
                System.out.println("Logging out...");
                break;
            }
            default: {
                System.out.println("Invalid choice. Please try again.");
                showDashboard();
                break;
            }
        }
    }

    private void displayEmployees() {
        for (User user : manager.getEmployees()) {
            System.out.println(user);
        }
    }
}