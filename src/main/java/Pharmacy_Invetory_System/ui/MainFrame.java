package Pharmacy_Invetory_System.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {

        setTitle("Pharmacy Inventory System");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a panel
        JPanel panel = new JPanel();

        // Set its layout
        panel.setLayout(new GridLayout(2, 3, 10, 10));

        // Add buttons
        panel.add(new JButton("Products"));
        panel.add(new JButton("Categories"));
        panel.add(new JButton("Employees"));
        panel.add(new JButton("Stocks"));
        panel.add(new JButton("Sales"));
        panel.add(new JButton("History"));

        // Add panel to window
        add(panel);
        setVisible(true);

    }
}
