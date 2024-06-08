package Users_Package;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Feedback_Controller {
private static final String URL = "jdbc:mysql://localhost:3306/sda_project_final_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "shabeeb";

    @FXML
    private ComboBox<String> Hostel_Name;
    @FXML
    private ComboBox<String> Location;
    @FXML
    private ComboBox<String> Rating;
    @FXML
    private TextArea description;
    @FXML
    private TableView<ObservableList<String>> tableView;
    @FXML
    private TableColumn<ObservableList<String>, String> nameCol;
    @FXML
    private TableColumn<ObservableList<String>, String> locCol;
    @FXML
    private TableColumn<ObservableList<String>, String> numCol;
    @FXML
    private TableColumn<ObservableList<String>, String> descpCol;
    @FXML
    private TableColumn<ObservableList<String>, String> RatingCol;
    @FXML
    private Button add;
   
    @FXML
    private Button delete;
    @FXML
    private Button Back;
    @FXML
    private TextArea descArea;
    @FXML
    private ListView<String> listView;

    public Feedback_Controller() {
    }

    @FXML
    private void initialize() {
        // Initialize the table columns
        Rating.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5"));
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));
        locCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
        numCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));
        descpCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));
        RatingCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4)));

        // Load initial data
        loadFeedbacks();
        populateHostelComboBox();
    }

    private void populateHostelComboBox() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String hostelQuery = "SELECT name, location FROM hostels";
            PreparedStatement hostelStatement = connection.prepareStatement(hostelQuery);
            ResultSet hostelResultSet = hostelStatement.executeQuery();

            while (hostelResultSet.next()) {
                String hostelName = hostelResultSet.getString("name");
                String hostelLocation = hostelResultSet.getString("location");

                Hostel_Name.getItems().add(hostelName);
                Location.getItems().add(hostelLocation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
private void addButton() {
    String hostelName = Hostel_Name.getValue();
    String location = Location.getValue();
    String desc = description.getText();
    String rating = Rating.getValue();

    // Validate input
    if (hostelName == null || location == null || desc.isEmpty() || rating == null) {
        showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter all the fields");
        return;
    }

    try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
        // Get the hostel_id based on hostel name and location
        String hostelIdQuery = "SELECT id FROM hostels WHERE name = ? AND location = ?";
        try (PreparedStatement hostelIdStatement = connection.prepareStatement(hostelIdQuery)) {
            hostelIdStatement.setString(1, hostelName);
            hostelIdStatement.setString(2, location);
            try (ResultSet resultSet = hostelIdStatement.executeQuery()) {
                if (resultSet.next()) {
                    int hostelId = resultSet.getInt("id");
                    // Insert feedback data into the feedbacks table
                    String insertFeedbackQuery = "INSERT INTO feedbacks (hostel_id, Description, Rating) VALUES (?, ?, ?)";
                    try (PreparedStatement insertFeedbackStatement = connection.prepareStatement(insertFeedbackQuery)) {
                        insertFeedbackStatement.setInt(1, hostelId);
                        insertFeedbackStatement.setString(2, desc);
                        insertFeedbackStatement.setString(3, rating);
                        insertFeedbackStatement.executeUpdate();
                        loadFeedbacks();
                        clearForm();
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Feedback added successfully.");
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Database Error", "Hostel not found.");
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add feedback. Please try again.");
    }
}

    @FXML
    private void deleteButton() {
        ObservableList<String> selectedFeedback = tableView.getSelectionModel().getSelectedItem();
        if (selectedFeedback == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a feedback to delete.");
            return;
        }

        String hostelName = selectedFeedback.get(0);
        String location = selectedFeedback.get(1);

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Deletion");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete the feedback for " + hostelName + " at " + location + "?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                String deleteQuery = 
                "DELETE feedbacks FROM feedbacks " +
                "JOIN hostels ON feedbacks.hostel_id = hostels.id " +
                "WHERE hostels.name = ? AND hostels.location = ?";                try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                    deleteStatement.setString(1, hostelName);
                    deleteStatement.setString(2, location);
                    deleteStatement.executeUpdate();
                    loadFeedbacks();
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Feedback deleted successfully.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to delete feedback. Please try again.");
            }
        }
    }

    @FXML
    public void backButton(ActionEvent event) {
        Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
    
             stage.setScene(App.getHome());
    }

    private void loadFeedbacks() {
        tableView.getItems().clear(); // Clear previous data
                
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT h.name AS hostel_name, h.location, h.contact_number, f.Description, f.rating " +
                           "FROM feedbacks f " +
                           "JOIN hostels h ON f.hostel_id = h.id " ;
        
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String hostelName = resultSet.getString("hostel_name");
                        String location = resultSet.getString("location");
                        String contactNumber = resultSet.getString("contact_number");
                        String description = resultSet.getString("Description");
                        String rating = resultSet.getString("rating");
        
                        ObservableList<String> rowData = FXCollections.observableArrayList(
                            hostelName,
                            location,
                            contactNumber,
                            description,
                            rating
                        );
        
                        tableView.getItems().add(rowData);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load feedbacks from the database: " + e.getMessage());
        }
    }
    
    

    private void clearForm() {
        Hostel_Name.setValue(null);
        Location.setValue(null);
        description.clear();
        Rating.setValue(null);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
