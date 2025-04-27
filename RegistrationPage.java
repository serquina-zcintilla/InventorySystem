package default1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationPage {
    private static JFrame registrationFrame; // Declare the variable here
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=LMS Test;encrypt=true;trustServerCertificate=true";
    private static final String USER = "chris";
    private static final String PASSWORD = "Christian";

    public static void main(String[] args) {
        registrationFrame = new JFrame("Register Account"); // Initialize it
        registrationFrame.setSize(800, 600);
        registrationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 51, 102), getWidth(), getHeight(), new Color(0, 102, 204));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Register an Account");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Full Name
        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        fullNameLabel.setForeground(Color.WHITE);
        fullNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the label
        JTextField fullNameField = new JTextField();
        fullNameField.setMaximumSize(new Dimension(300, 30));
        fullNameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        fullNameField.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the field

        // ID Number
        JLabel idNumberLabel = new JLabel("ID Number:");
        idNumberLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        idNumberLabel.setForeground(Color.WHITE);
        idNumberLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the label
        JTextField idNumberField = new JTextField();
        idNumberField.setMaximumSize(new Dimension(300, 30));
        idNumberField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        idNumberField.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the field

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the label
        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(300, 30));
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the field

        // Confirm Password
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        confirmPasswordLabel.setForeground(Color.WHITE);
        confirmPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the label
        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setMaximumSize(new Dimension(300, 30));
        confirmPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        confirmPasswordField.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the field

        // Email
        JLabel emailLabel = new JLabel("Email (Optional):");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the label
        JTextField emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(300, 30));
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the field

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        registerButton.setBackground(Color.WHITE);
        registerButton.setForeground(new Color(0, 51, 102));
        registerButton.setFocusPainted(false);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        registerButton.addActionListener((ActionEvent e) -> {
            String fullName = fullNameField.getText();
            String idNumber = idNumberField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String email = emailField.getText();

            // Validation: Check if all required fields are filled out
            if (fullName.isEmpty() || idNumber.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill out all the required fields.");
            } else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(null, "Passwords do not match. Please try again.");
            } else {
                registerAccount(fullName, idNumber, password, email);
            }
        });

        registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerButton.setBackground(new Color(204, 229, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerButton.setBackground(Color.WHITE);
            }
        });

        // Login Redirect Text
        JLabel loginLabel = new JLabel("Already have an account? Log in here");
        loginLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        loginLabel.setForeground(Color.WHITE);
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the label
        loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add click event to open LogInPage
        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registrationFrame.dispose(); // Close Registration window
                LogInPage(null); // Open Login window
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginLabel.setForeground(new Color(204, 204, 255)); // Change color on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginLabel.setForeground(Color.WHITE); // Revert color on mouse exit
            }
        });

        // Add components to the panel
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        panel.add(fullNameLabel);
        panel.add(fullNameField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(idNumberLabel);
        panel.add(idNumberField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(confirmPasswordLabel);
        panel.add(confirmPasswordField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        panel.add(registerButton);
        panel.add(Box.createVerticalGlue());

        // Add the login redirect label at the bottom
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(loginLabel);

        registrationFrame.add(panel);
        registrationFrame.setLocationRelativeTo(null);
        registrationFrame.setVisible(true);
    }

    private static void registerAccount(String fullName, String idNumber, String password, String email) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "INSERT INTO dbo.users (full_name, id_number, password, email) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, fullName);
                stmt.setString(2, idNumber);
                stmt.setString(3, password);
                stmt.setString(4, email.isEmpty() ? null : email);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Account registered successfully!");

                registrationFrame.dispose(); // Close Registration window
                LogInPage(null); // Open Login window
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private static void LogInPage(Object object) {
        default1.LogInPage.main(new String[]{}); // Open the login window
    }
}
