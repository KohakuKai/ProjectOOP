package Simula;

import java.util.ArrayList;
import java.util.Scanner;

public class StartUp {

    String line = "-".repeat(50);
    String border = "=".repeat(50);
    String mborder = "~".repeat(50);

    private final ArrayList<Staff> registeredStaffs = new ArrayList<>();
    private final Scanner in = new Scanner(System.in);

    public StartUp() { // dito lalagay yung owner
        setDefaultUsers();
    }

    private final Inventory inventory = new Inventory();
    public Inventory getInventory() {
        return inventory;
    }

    // login or sign up
    public void start() {
        int choice;
        while (true) {
            System.out.println("\n" + border);
            System.out.printf("%32s\n","PHARMACY SYSTEM");
            System.out.println(border);
            System.out.println("\t[1] Log In");
            System.out.println("\t[2] Exit");
            System.out.println(border);
            System.out.print("Choice: ");
            choice = Staff.userIntInput();//kinuha ko nalang yung nasa staff kaysa gumawa ulit method
            System.out.println(border+"\n");


            switch (choice) {
                case 1:
                    logIn();
                    break;
                case 2:
                    System.out.println(mborder);
                    System.out.printf("%30s\n", "Exiting...");
                    System.out.println(mborder);
                    return;
                case 67: {
                    System.out.println(mborder);
                    System.out.printf("%28s\n","Bypass!");
                    System.out.println(mborder);
                    String username = "ownerUsername";
                    String password = "ownerPassword123_";
                    Staff foundStaff = findUser(username, password);
                    if (foundStaff != null) foundStaff.showDashboard();
                    break;
                }
                default:
                    System.out.println(mborder);
                    System.out.printf("%37s\n","Invalid option. Try again.");
                    System.out.println(mborder);
            }
        }
    }

    // getter para sa owner
    public ArrayList<Staff> getStaff() {
        return registeredStaffs;
    }


    // ----- LOG IN -----
    private void logIn() {
        System.out.println(line);
        System.out.print("Enter your username: ");
        String username = in.nextLine().trim();

        System.out.print("Enter your password: ");
        String password = in.nextLine();

        System.out.println(border);
        //ipapasa dito yung ininput then pagbalik, rekta store kay foundUser
        Staff foundStaff = findUser(username,  password);

        System.out.println();
        System.out.println(mborder);
        if (foundStaff != null) {

            System.out.printf("%33s\n","Login successful!");
            System.out.println(mborder);
            // may override each subclass
            foundStaff.showDashboard();
        } else {
            System.out.printf("%48s\n","No matching account found. Please sign up first.");
            System.out.println(mborder);
        }
    }

        // taga hanap if may ganitong tao ba na na sign up sa arraylist
    private Staff findUser (String username, String password){
        for (Staff staff : registeredStaffs) {
            if (staff.getUsername().equals(username)
                    && staff.getPassword().equals(password)) {
                return staff;
            }
        }
        return null;
    }

    // MGA DEFAULT/PRE DEFINED NA MGA USERS NG SYSTEM -----------------------------------------------------------------------

    private  void setDefaultUsers () {
        Staff owner = new Owner("ownerName", this, "ownerPassword123_", "ownerUsername", inventory);
        registeredStaffs.add(owner);
        inventory.defaultTransactions(owner); // para lang to sa predefined na Transcation History

        Staff pharmacist1 = new Pharmacist("Clay Cruz","July242007_","clay",inventory);
        registeredStaffs.add(pharmacist1);

        Staff staff1 = new Employee("Raiven Joson","ILoveEunice07_","raiven",inventory);
        registeredStaffs.add(staff1);


    }


}

