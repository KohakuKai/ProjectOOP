package Simula;

public class Staff extends User {

    public Staff(String name) {
        super(name, "Staff");
    }

    @Override
    public void showDashboard() {
        System.out.println("\n=== STAFF DASHBOARD ===");
        System.out.println(this);
    }
}