package Simula;

import java.util.Scanner;

public abstract class Employee {

    protected static final Scanner in = new Scanner(System.in); //share share na lahat ng subclass sa isang scanner, para walang duplicate scanners

    private String name;
    private String position; // "Owner", "Pharmacist", or "Staff"
    private String username;
    private String password;
    private static int nextEmpID = 1001;
    private String empID;

    public Employee(String name, String position, String password, String username) {
        this.empID = String.valueOf(nextEmpID++);
        this.name = name;
        this.position = position;
        this.password = password;
        this.username = username;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmpID () {
        return empID;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public abstract void showDashboard();

    @Override
    public String toString() {
        return "ID no.: " + empID + "    |    " + name + "\n";
    }

    public void changePassword() {

        System.out.print("\n=== CHANGE PASSWORD ===\nEnter Current password: ");
        String checkPassword = in.nextLine();

        if(checkPassword.equals(this.getPassword())) {

            System.out.print("Enter new password: ");
            String newPassword = in.nextLine();

            setPassword(newPassword);
            System.out.println("Password changed successfully!");
        }

        else System.out.println("Password does not match!");

    }
}