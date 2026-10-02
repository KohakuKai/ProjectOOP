package Simula;


import java.util.Scanner;

public class Owner extends User {
    Scanner in = new Scanner (System.in);

    private final StartUp manager;

    public Owner(String name, StartUp manager) {
        super(name, "Owner");
        this.manager = manager;
    }

    @Override
    public void showDashboard() {
        System.out.println("\n=== OWNER DASHBOARD ===");
        System.out.println(this);

        System.out.print("[1] View Employees\n[2] Logout\nChoice: ");
        int choice = in.nextInt();
        in.nextLine(); // consume the leftover Enter
        if (choice == 1) {
            displayEmployees();
        } else if (choice == 2) {
            System.out.println("Logging out...");
        }
    }

    private void displayEmployees() {
        for (User user : manager.getEmployees()) {
            System.out.println(user);
        }
    }
}