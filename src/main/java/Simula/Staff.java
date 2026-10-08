package Simula;
import java.util.Scanner;

public class Staff extends User {

    public Staff(String name, String password, String username) {
        super(name, "Staff", password, username);
    }
    public static Scanner in = new Scanner(System.in);
    @Override
    public void showDashboard() {
        System.out.println("\n=== STAFF DASHBOARD ===");
        System.out.println(this);

        System.out.println("[1] Change Password");
        System.out.println("[2] Logout");
        System.out.print("Choice: ");

        int choice = in.nextInt();
        in.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Enter new password: ");
                String newPassword = in.nextLine();

                setPassword(newPassword);
                System.out.println("Password changed successfully!");
                break;

            case 2:
                System.out.println("Logging out...");
                return;

            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}