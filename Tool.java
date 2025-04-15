package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;

public class Tool {
    private final StringProperty toolName;
    private final IntegerProperty quantity;
    private final StringProperty status;

    public Tool(String toolName, int quantity, String status) {
        this.toolName = new SimpleStringProperty(toolName);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.status = new SimpleStringProperty(status);
    }

    public StringProperty toolNameProperty() {
        return toolName;
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public String getToolName() {
        return toolName.get();
    }

    public void setToolName(String toolName) {
        this.toolName.set(toolName);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
