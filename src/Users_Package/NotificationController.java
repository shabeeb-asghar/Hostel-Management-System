package Users_Package;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class NotificationController {
    private static final String URL = "jdbc:mysql://localhost:3306/sda_project_final_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "shabeeb";
    
    @FXML
    private Button HomeButton;
    @FXML
    private TextArea statusMessage;
    @FXML
    private ToggleButton busyToggle;
    @FXML
    private ComboBox<String> clearStatus;
    @FXML
    private VBox suggestionsBox;
    @FXML
    private Button setStatusButton;
    @FXML
    private Button clearStatusButton;

    @FXML
    public void initialize() {
        clearStatus.getItems().addAll("Never", "After 1 week", "After 2 weeks", "After a month");
        clearStatus.setValue("Never");
        
        for (Button button : suggestionsBox.getChildren().filtered(node -> node instanceof Button).toArray(Button[]::new)) {
            button.setOnAction(this::handleRoomSelection);
        }

        busyToggle.setOnAction(event -> handleRoomSelection(event));
        setStatusButton.setOnAction(event -> handleSetStatus());
        clearStatusButton.setOnAction(event -> handleClearStatus());
    }
    
    private void handleRoomSelection(ActionEvent event) {
        ToggleButton sourceButton = (ToggleButton) event.getSource();
        statusMessage.setText(sourceButton.getText());
    }
    @FXML
    private void handleSetStatus() {
        String message = statusMessage.getText();
        if (message.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Status message cannot be empty.");
            return;
        }
        
        // Save to database
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "INSERT INTO notifications (message) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, message);
            statement.executeUpdate();

            showAlert(AlertType.INFORMATION, "Notification Set", "Notification has been set!");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Database Error", "Failed to set notification.");
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void handleClearStatus() {
        statusMessage.clear();
        clearStatus.setValue("Never");
    }
    @FXML
    void onClickHome(ActionEvent event) {
                    Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
    
             stage.setScene(App.getHome());
    }
}
