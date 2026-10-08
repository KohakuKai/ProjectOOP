package Simula;

public class Pharmacist extends User {

    public Pharmacist(String name, String password, String username) {
        super(name, "Pharmacist", username, password);
    }

    @Override
    public void showDashboard() {
        System.out.println("\n=== PHARMACIST DASHBOARD ===");
        System.out.println(this);
    }
}