package default1;

import javax.swing.*;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class CleaningInventory1 {
    private static JTextArea displayArea;

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=LMS Test;encrypt=true;trustServerCertificate=true";
    private static final String USER = "chris";
    private static final String PASSWORD = "Christian";

    public static void main(String[] args) {
        JFrame frame = new JFrame("🧼 Cleaning Inventory Management System");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        displayArea = new JTextArea(20, 50);
        displayArea.setEditable(false);
        frame.add(new JScrollPane(displayArea));

        JButton listButton = new JButton("View Inventory");
        JButton addButton = new JButton("Add Item");
        JButton updateButton = new JButton("Update Item");
        JButton deleteButton = new JButton("Delete Item");
        JButton lowStockButton = new JButton("Low Stock Report");
        JButton exitButton = new JButton("Exit");

        frame.add(listButton);
        frame.add(addButton);
        frame.add(updateButton);
        frame.add(deleteButton);
        frame.add(lowStockButton);
        frame.add(exitButton);

        listButton.addActionListener(e -> listInventory());
        addButton.addActionListener(e -> addItem());
        updateButton.addActionListener(e -> updateItem());
        deleteButton.addActionListener(e -> deleteItem());
        lowStockButton.addActionListener(e -> lowStockReport());
        exitButton.addActionListener(e -> System.exit(0));

        // Center the frame in the middle of the screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void listInventory() {
        StringBuilder list = new StringBuilder("🧼 Cleaning Inventory:\n\n");

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT ItemID, Name, Quantity, Unit, Supplier FROM dbo.inventory";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    list.append("ID: ").append(rs.getString("ItemID"))
                        .append(" | Name: ").append(rs.getString("Name"))
                        .append(" | Qty: ").append(rs.getInt("Quantity"))
                        .append(" ").append(rs.getString("Unit"))
                        .append(" | Supplier: ").append(rs.getString("Supplier"))
                        .append("\n");
                }
            }
        } catch (SQLException e) {
            list.append("Error: ").append(e.getMessage());
        }

        displayArea.setText(list.toString());
    }

    private static void addItem() {
        String name = JOptionPane.showInputDialog("Item Name:");
        String qtyStr = JOptionPane.showInputDialog("Quantity:");
        String unit = JOptionPane.showInputDialog("Unit (e.g., bottles, packs):");
        String supplier = JOptionPane.showInputDialog("Supplier Name:");

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "INSERT INTO dbo.inventory (Name, Quantity, Unit, Supplier) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, name);
                stmt.setInt(2, Integer.parseInt(qtyStr));
                stmt.setString(3, unit);
                stmt.setString(4, supplier);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Item added!");
                listInventory();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private static void updateItem() {
        String id = JOptionPane.showInputDialog("Enter Item ID to update:");
        String newQty = JOptionPane.showInputDialog("New Quantity:");

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "UPDATE dbo.inventory SET Quantity = ? WHERE ItemID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, Integer.parseInt(newQty));
                stmt.setString(2, id);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Item updated!");
                listInventory();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private static void deleteItem() {
        String id = JOptionPane.showInputDialog("Enter Item ID to delete:");

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "DELETE FROM dbo.inventory WHERE ItemID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, id);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Item deleted!");
                listInventory();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private static void lowStockReport() {
        StringBuilder list = new StringBuilder("🧼 Low Stock Items:\n\n");

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT ItemID, Name, Quantity FROM dbo.inventory WHERE Quantity < 5";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    list.append("ID: ").append(rs.getString("ItemID"))
                        .append(" | Name: ").append(rs.getString("Name"))
                        .append(" | Qty: ").append(rs.getInt("Quantity"))
                        .append("\n");
                }
            }
        } catch (SQLException e) {
            list.append("Error: ").append(e.getMessage());
        }

        displayArea.setText(list.toString());
    }
}
