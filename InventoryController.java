package application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InventoryController {
    @FXML private TableView<Tool> toolTable;
    @FXML private TableColumn<Tool, String> toolNameColumn;
    @FXML private TableColumn<Tool, Integer> toolQuantityColumn;
    @FXML private TableColumn<Tool, String> toolStatusColumn;
    @FXML private Button borrowButton;
    @FXML private Button returnButton;
    @FXML private Button addButton;
    @FXML private Button editButton;
    @FXML private Button deleteButton;

    private ObservableList<Tool> toolList = FXCollections.observableArrayList();

    // Initialize method to populate TableView with data
    public void initialize() {
        // Fetch data from the database and populate the toolList
        fetchDataFromDatabase();

        // Set up TableView columns
        toolNameColumn.setCellValueFactory(cellData -> cellData.getValue().toolNameProperty());
        toolQuantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        toolStatusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        // Set the TableView's items to the ObservableList
        toolTable.setItems(toolList);

        // Button actions for borrow, return, add, edit, delete
        borrowButton.setOnAction(event -> borrowTool());
        returnButton.setOnAction(event -> returnTool());
        addButton.setOnAction(event -> addTool());
        editButton.setOnAction(event -> editTool());
        deleteButton.setOnAction(event -> deleteTool());
    }

    // Fetch tool data from the database and populate toolList
    private void fetchDataFromDatabase() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM CleaningTools";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            // Clear existing list
            toolList.clear();

            // Iterate over result set and add tools to toolList
            while (rs.next()) {
                String toolName = rs.getString("tool_name");
                int quantity = rs.getInt("quantity");
                String status = rs.getString("status");

                // Add the tool to the observable list
                toolList.add(new Tool(toolName, quantity, status));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void borrowTool() {
        Tool selectedTool = toolTable.getSelectionModel().getSelectedItem();
        if (selectedTool != null && selectedTool.getQuantity() > 0) {
            selectedTool.setQuantity(selectedTool.getQuantity() - 1);
            selectedTool.setStatus("Borrowed");
            toolTable.refresh();
        }
    }

    private void returnTool() {
        Tool selectedTool = toolTable.getSelectionModel().getSelectedItem();
        if (selectedTool != null) {
            selectedTool.setQuantity(selectedTool.getQuantity() + 1);
            selectedTool.setStatus("Available");
            toolTable.refresh();
        }
    }

    private void addTool() {
        // Implement logic to add new tool
    }

    private void editTool() {
        // Implement logic to edit existing tool details
    }

    private void deleteTool() {
        Tool selectedTool = toolTable.getSelectionModel().getSelectedItem();
        if (selectedTool != null) {
            toolList.remove(selectedTool);
        }
    }
}
