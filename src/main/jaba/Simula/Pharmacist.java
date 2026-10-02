package main.jaba.Simula;

public class Pharmacist extends User {

    public Pharmacist(String name) {
        super(name, "Pharmacist");
    }

    @Override
    public void showDashboard() {
        System.out.println("\n=== PHARMACIST DASHBOARD ===");
        System.out.println(this);
    }
}