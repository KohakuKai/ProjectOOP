package Simula;

import java.util.Scanner;

public class Pharmacist extends Employee {

    public Pharmacist(String name, String password, String username) {
        super(name, "Pharmacist",password, username);
    }

    @Override
    public void showDashboard() {
        System.out.println("\n=== PHARMACIST DASHBOARD ===");
        System.out.println(this);

        System.out.println("[1] Change Password");
        System.out.println("[2] Logout");
        System.out.print("Choice: ");

        int choice = in.nextInt();
        in.nextLine();

        switch (choice) {
            case 1:
                changePassword();
                break;

            case 2:
                System.out.println("Logging out...");
                return;

            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}