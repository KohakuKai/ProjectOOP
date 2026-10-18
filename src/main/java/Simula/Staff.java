package Simula;
import java.time.LocalDate;
import java.util.Scanner;

public class Staff extends Employee {

    public Staff(String name, String password, String username, Inventory inventory) {
        super(name, "Staff", password, username, inventory);
    }

    @Override
    public void showDashboard() {
        while (true) {
            tag();
            System.out.println(border);
            System.out.println("\t\tPHARMACY");
            System.out.println(border);

            displayStockAlerts();

            System.out.print("INVENTORY\n"
                    + "\t[1] View Products\n"
                    + "\t[2] View Categories\n"
                    + "\t[3] View Product Batches\n"
                    + "TRANSACT\n"
                    + "\t[4] Sell Product\n"
                    + "\t[5] View Transaction History\n"
                    + "ACCOUNT\n"
                    + "\t[6] Change Password\n"
                    + "\t[7] View Profile\n"
                    + "\t[0] Logout\n"
                    + border + "\nChoice: ");

            int choice = in.nextInt();
            in.nextLine();

            switch (choice) {
                case 1:
                    displayProducts();
                    pause();
                    break;

                case 2:
                    displayCategories();
                    pause();
                    break;

                case 3:
                    viewProductBatchDetails();
                    pause();
                    break;

                case 4:
                    sellProduct();
                    pause();
                    break;

                case 5:
                    viewTransactionHistory();
                    pause();
                    break;

                case 6:
                    changePassword();
                    pause();
                    break;

                case 7:
                    displayProfile();
                    pause();
                    break;

                case 0:
                    System.out.println("Logging out...");
                    return;

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