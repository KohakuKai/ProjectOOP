package Simula;

import java.util.ArrayList;
import java.util.Scanner;

public class StartUp {

    private ArrayList<User> registeredUsers = new ArrayList<>();
    private Scanner in = new Scanner(System.in);

    public StartUp() { // dito lalagay yung owner
        registeredUsers.add(
                new Owner("ownerName", this, "ownerPassword", "ownerUsername")
        );
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
    public ArrayList<User> getEmployees() {
        return registeredUsers;
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
        User foundUser = findUser(username, empID, password);

        if (foundUser != null) {
            System.out.println("Login successful!");
            // may override each subclass
            foundUser.showDashboard();
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

        User newUser = createUserByChoice(choice, name, username, password);
        if (newUser == null) {
            System.out.println("Invalid position. Employee creation cancelled.");
            return;
        }

        registeredUsers.add(newUser);

        System.out.println("Employee created successfully!");
        System.out.println("Employee ID: " + newUser.getEmpID());
        System.out.println("Position: " + newUser.getPosition());
    }

    // pinagpasahan ng input. taga gawa object
    private User createUserByChoice(int choice, String name,
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
        private User findUser (String username,String empID, String password){
            for (User user : registeredUsers) {
                if (user.getUsername().equals(username)
                        && user.getEmpID().equals(empID)
                        && user.getPassword().equals(password)) {
                    return user;
                }
            }
            return null;
        }
    }

