package default1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class LogInPage {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=LMS Test;encrypt=true;trustServerCertificate=true";
    private static final String USER = "chris";
    private static final String PASSWORD = "Christian";

    // Admin fixed account
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    public static void main(String[] args) {
        JFrame loginFrame = new JFrame("Log In");
        loginFrame.setSize(800, 600);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(51, 0, 102), getWidth(), getHeight(), new Color(153, 0, 204));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Log In to Your Account");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Admin Username
        JLabel adminLabel = new JLabel("Admin Username:");
        adminLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        adminLabel.setForeground(Color.WHITE);
        adminLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label
        JTextField adminField = new JTextField();
        adminField.setMaximumSize(new Dimension(300, 30));
        adminField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        adminField.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the text field

        // ID Number
        JLabel idNumberLabel = new JLabel("ID Number:");
        idNumberLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        idNumberLabel.setForeground(Color.WHITE);
        idNumberLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label
        JTextField idNumberField = new JTextField();
        idNumberField.setMaximumSize(new Dimension(300, 30));
        idNumberField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        idNumberField.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the text field

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label
        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(300, 30));
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the text field

        // Log In Button
        JButton loginButton = new JButton("Log In");
        loginButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        loginButton.setBackground(Color.WHITE);
        loginButton.setForeground(new Color(51, 0, 102));
        loginButton.setFocusPainted(false);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button

        loginButton.addActionListener((ActionEvent e) -> {
            String adminUsername = adminField.getText();
            String idNumber = idNumberField.getText();
            String password = new String(passwordField.getPassword());

            // Check if any field is empty
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill out all fields.");
            } else {
                // If Admin credentials are entered, validate the admin (skip ID Number for Admin)
                if (validateAdmin(adminUsername, password)) {
                    JOptionPane.showMessageDialog(null, "Welcome Admin!");
                    loginFrame.dispose();
                    AdminPage.main(new String[]{}); // Admin Dashboard
                } else {
                    // For non-admin users, validate ID number and password
                    if (validateCredentials(idNumber, password)) {
                        JOptionPane.showMessageDialog(null, "Welcome User!");
                        loginFrame.dispose();
                        CleaningInventory1.main(new String[]{}); // Normal user login
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid credentials. Please try again.");
                    }
                }
            }
        });

        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(229, 204, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(Color.WHITE);
            }
        });

        // Add components to the panel
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        panel.add(adminLabel);
        panel.add(adminField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(idNumberLabel);
        panel.add(idNumberField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        panel.add(loginButton);
        panel.add(Box.createVerticalGlue());

        loginFrame.add(panel);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }

    private static boolean validateCredentials(String idNumber, String password) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM dbo.users WHERE id_number = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, idNumber);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next(); // Returns true if user credentials are found
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return false;
    }

    private static boolean validateAdmin(String adminUsername, String password) {
        return ADMIN_USERNAME.equals(adminUsername) && ADMIN_PASSWORD.equals(password);
    }
}
