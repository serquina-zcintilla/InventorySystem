package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegistrationController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private Button registerButton;

    @FXML
    private void initialize() {
        registerButton.setOnAction(e -> registerUser());
    }

    private void registerUser() {
        String name = nameField.getText();
        String email = emailField.getText();

        String sql = "INSERT INTO Users (Name, Email) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, email);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("User registered successfully.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
