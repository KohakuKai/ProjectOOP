package Simula;

public class Staff extends User {

    public Staff(String name, String password, String username) {
        super(name, "Staff", password, username);
    }

    @Override
    public void showDashboard() {
        System.out.println("\n=== STAFF DASHBOARD ===");
        System.out.println(this);
    }
}