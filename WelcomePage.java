package default1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class WelcomePage {
    public static void main(String[] args) {
        // Create the frame
        JFrame welcomeFrame = new JFrame("Welcome to I.M.P.A.C.T");
        welcomeFrame.setSize(800, 600);
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a custom gradient panel
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
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Stack elements vertically

        // Welcome message with modern font
        JLabel welcomeLabel = new JLabel("WELCOME TO I.M.P.A.C.T", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 36)); // Sleek modern font
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Spacer between welcome text and button
        panel.add(Box.createVerticalGlue()); // Flexible space above
        panel.add(welcomeLabel);             // Add the welcome label
        panel.add(Box.createRigidArea(new Dimension(0, 50))); // Fixed space between label and button

        // "Get Started" button with hover effect
        JButton getStartedButton = new JButton("Get started →");
        getStartedButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        getStartedButton.setBackground(Color.WHITE);
        getStartedButton.setForeground(new Color(0, 51, 102));
        getStartedButton.setFocusPainted(false); // Remove focus border
        getStartedButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button horizontally
        getStartedButton.addActionListener((ActionEvent e) -> {
            welcomeFrame.dispose(); // Close welcome window
            RegistrationPage.main(null); // Navigate to inventory system
        });

        // Add hover effect to the button
        getStartedButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                getStartedButton.setBackground(new Color(204, 229, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                getStartedButton.setBackground(Color.WHITE);
            }
        });

        panel.add(getStartedButton);        // Add the button
        panel.add(Box.createVerticalGlue()); // Flexible space below

        // Add panel to frame
        welcomeFrame.add(panel);
        welcomeFrame.setLocationRelativeTo(null); // Center the frame on screen
        welcomeFrame.setVisible(true);
    }
}
