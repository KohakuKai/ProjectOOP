package Simula;
import java.time.LocalDate;
import java.util.Scanner;

public class Staff extends Employee {

    public Staff(String name, String password, String username) {
        super(name, "Staff", password, username);
    }

    @Override
    public void showDashboard() {
        while (true) {
            System.out.println("\n=== STAFF DASHBOARD ===");
            System.out.println(this);

            System.out.println("[1] Change Password");
            System.out.println("[2] View Profile");
            System.out.println("[0] Logout");
            System.out.print("Choice: ");

            int choice = in.nextInt();
            in.nextLine();

            switch (choice) {
                case 1:{
                    changePassword();
                    break;
                }

                case 2: {
                    displayProfile();
                    break;
                }

                case 0: {
                    System.out.println("Logging out...");
                    return;
                }



                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private LocalDate hireDate = LocalDate.now();

    @Override
    public void displayProfile() {
        super.displayProfile();
        System.out.println("Hire Date: " + hireDate);
    }
}