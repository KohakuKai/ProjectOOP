
package Pharmacy_Invetory_System.ui;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

    public Login() {

        setTitle("Pharmacy Inventory System - Login");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Title
        JLabel title = new JLabel("PHARMACY INVENTORY SYSTEM");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setHorizontalAlignment(JLabel.CENTER);

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(15);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(15);

        // Login button
        JButton loginButton = new JButton("LOGIN");

        // Login functionality
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter your username and password.");
            }
            else {
                new MainFrame();
                dispose();
            }
        });

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 15));

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);

        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        formPanel.add(new JLabel());
        formPanel.add(loginButton);

        // Add components to main panel
        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Add main panel to window
        add(mainPanel);
        setVisible(true);
    }
}
