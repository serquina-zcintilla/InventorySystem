package default1;

import javax.swing.*;
import java.awt.*;

public class AdminPage {
    public static void main(String[] args) {
        JFrame adminFrame = new JFrame("Admin Dashboard");
        adminFrame.setSize(800, 600);
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, Admin!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(new Color(51, 0, 102));

        panel.add(welcomeLabel, BorderLayout.CENTER);

        adminFrame.add(panel);
        adminFrame.setLocationRelativeTo(null);
        adminFrame.setVisible(true);
    }
}
