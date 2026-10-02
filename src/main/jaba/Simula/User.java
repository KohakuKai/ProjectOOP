package main.jaba.Simula;

public abstract class User {

    private String name;
    private String position; // "Owner", "Pharmacist", or "Staff"

    public User(String name, String position) {
        this.name = name;
        this.position = position;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public abstract void showDashboard();

    @Override
    public String toString() {
        return "Name: " + name + " | Position: " + position;
    }
}