package Simula;

import java.util.ArrayList;
import java.util.Scanner;

public class StartUp {

    private ArrayList<User> registeredUsers = new ArrayList<>();
    private Scanner in = new Scanner(System.in);

    // login or sign up
    public void start() {
        int choice = 0;
        while ((choice != 1) || (choice !=2)) {
            System.out.println("\n===== PHARMACY SYSTEM =====");
            System.out.println("[1] Log In");
            System.out.println("[2] Sign Up");
            System.out.print("Type here: ");

            choice = in.nextInt();
            in.nextLine(); // nawawala name pag wala to

            switch (choice) {
                case 1:
                    logIn();
                    break;
                case 2:
                    signUp();
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // getter para sa owner
    public ArrayList<User> getEmployees() {
        return registeredUsers;
    }


    // sign up method
    private void signUp() {
        System.out.print("\nEnter your name: ");
        String name = in.nextLine().trim(); // para dito yung nextLine kanina

        System.out.println("Select your position:");
        System.out.println("[1] Owner");
        System.out.println("[2] Pharmacist");
        System.out.println("[3] Staff");
        System.out.print("Choice: ");
        String posChoice = in.nextLine().trim();

        //ipapasa dito yung ininput then pagbalik, rekta store kay newUser
        User newUser = createUserByChoice(posChoice, name);

        if (newUser == null) {
            System.out.println("Invalid position selected. Sign up cancelled.");
            return;
        }

        registeredUsers.add(newUser);
        System.out.println("Sign up successful! You may now log in.");
    }

    // ----- LOG IN -----
    private void logIn() {
        System.out.print("\nEnter your name: ");
        String name = in.nextLine().trim(); // para dito yung nextLine kanina

        System.out.print("Enter your position (Owner/Pharmacist/Staff): ");
        String position = in.nextLine().trim();

        //ipapasa dito yung ininput then pagbalik, rekta store kay foundUser
        User foundUser = findUser(name, position);

        if (foundUser != null) {
            System.out.println("Login successful!");
            // may override each sub class
            foundUser.showDashboard();
        } else {
            System.out.println("No matching account found. Please sign up first.");
        }
    }

    // pinagpasahan ng input. taga gawa object
    private User createUserByChoice(String choice, String name) {
        switch (choice) {
            case "1":
                return new Owner(name, this);
            // this is para maaccess ni Owner yung employee list
            case "2":
                return new Pharmacist(name);
            case "3":
                return new Staff(name);
            default:
                return null;
        }
    }

    // taga hanap if may ganitong tao ba na na sign up sa arraylist
    private User findUser(String name, String position) {
        for (User u : registeredUsers) {
            if (u.getName().equalsIgnoreCase(name)
                    && u.getPosition().equalsIgnoreCase(position)) {
                return u;
            }
        }
        return null;
    }
}
