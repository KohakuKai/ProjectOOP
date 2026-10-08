package Simula;

public abstract class User {

    private String name;
    private String position; // "Owner", "Pharmacist", or "Staff"
    private String username;
    private String password;
    private static int nextEmpID = 1001;
    private String empID;

    public User(String name, String position, String password, String username) {
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
}