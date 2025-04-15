package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        try {
            primaryStage = stage;
            // Show the Welcome Page first
            showWelcomePage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to show the Welcome Page
    public static void showWelcomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("WelcomePage.fxml"));
            Parent welcomeRoot = loader.load();
            primaryStage.setTitle("Welcome to SWEEP SYNC");
            primaryStage.setScene(new Scene(welcomeRoot, 800, 600));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to switch to Registration Page
    public static void showRegistrationPage() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("RegistrationPage.fxml"));
            Parent registrationRoot = loader.load();
            primaryStage.setTitle("Register - SWEEP SYNC");
            primaryStage.setScene(new Scene(registrationRoot, 800, 600));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to switch to Inventory Page
    public static void showInventoryPage() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("InventoryView.fxml"));
            Parent inventoryRoot = loader.load();
            primaryStage.setTitle("IMPACT: Inventory Management Platform for All Cleaning Tools");
            primaryStage.setScene(new Scene(inventoryRoot, 800, 600));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
