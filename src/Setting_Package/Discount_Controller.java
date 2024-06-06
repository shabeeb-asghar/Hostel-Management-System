package Setting_Package;

import Setting_Package.Discounts;
import Setting_Package.Single_Discount;
import Users_Package.dataBase;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import Hostel_Package.Hostel;
import Hostel_Package.Room;

public class Discount_Controller {
    private static final String URL = "jdbc:mysql://localhost:3306/sda_project_final_db";
  
   
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234Qwert@";
   
    @FXML
    private ComboBox<String> Hostel_Name;
    @FXML
    private ComboBox<String> Location;
    @FXML
    private ComboBox<String> Rooms;
    @FXML
    private ComboBox<String> Discountamount;
    @FXML
    private TextField discountcode;
    @FXML
    private DatePicker deadline;
    @FXML
    private TableColumn<ObservableList<String>, String> nameCol;
    @FXML
    private TableColumn<ObservableList<String>, String> locCol;
    @FXML
    private TableColumn<ObservableList<String>, String> roomCol;
    @FXML
    private TableColumn<ObservableList<String>, String> DiscodeCol;
    @FXML
    private TableColumn<ObservableList<String>, String> discountCol;
    @FXML
    private TableColumn<ObservableList<String>, String> deadlinecol;

    @FXML
    private TableView<ObservableList<String>> tableView;
    @FXML
    private TextArea descArea;
    @FXML
    private ListView<String> listView;
    @FXML
    private Button add;
    @FXML
    private Button Update;
    @FXML
    private Button delete;
    @FXML
    private Button undo;

    private Discounts discounts;

    public Discount_Controller() {
        this.discounts = new Discounts();
    }

    @FXML
    private void initialize() {
        // Initialize the table columns
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));
        locCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
        roomCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));
        DiscodeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));
        discountCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4)));
        deadlinecol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(5)));

        // Load initial data
        loadDiscounts();
        populateHostelComboBox();
        populateDiscountAmountComboBox();
    }

    private void populateDiscountAmountComboBox() {
        Discountamount.getItems().addAll("10%", "20%", "30%", "40%", "50%", "60%", "70%", "80%");
    }

    private void populateHostelComboBox() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String hostelQuery = "SELECT id, name, location FROM hostels";
            PreparedStatement hostelStatement = connection.prepareStatement(hostelQuery);
            ResultSet hostelResultSet = hostelStatement.executeQuery();

            while (hostelResultSet.next()) {
                int hostelId = hostelResultSet.getInt("id");
                String hostelName = hostelResultSet.getString("name");
                String hostelLocation = hostelResultSet.getString("location");

                Hostel_Name.getItems().add(hostelName);
                Location.getItems().add(hostelLocation);

                String roomQuery = "SELECT name FROM rooms WHERE hostel_id = ?";
                PreparedStatement roomStatement = connection.prepareStatement(roomQuery);
                roomStatement.setInt(1, hostelId);
                ResultSet roomResultSet = roomStatement.executeQuery();

                while (roomResultSet.next()) {
                    String roomName = roomResultSet.getString("name");
                    Rooms.getItems().add(roomName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addButton() {
        String hostelName = Hostel_Name.getValue();
        String location = Location.getValue();
        String room = Rooms.getValue();
        String code = discountcode.getText();
        String amount = Discountamount.getValue();
        LocalDate deadlineDate = deadline.getValue();
    
        // Validate input
        if (hostelName == null || location == null || room == null || code.isEmpty() || amount == null || deadlineDate == null) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter all the fields");
            return;
        }
    
        float discountAmount = Float.parseFloat(amount.replace("%", "")) / 100;
        Date discountDeadline = Date.valueOf(deadlineDate);
    
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String hostelIdQuery = "SELECT id FROM hostels WHERE name = ?";
            try (PreparedStatement hostelIdStatement = connection.prepareStatement(hostelIdQuery)) {
                hostelIdStatement.setString(1, hostelName);
                try (ResultSet resultSet = hostelIdStatement.executeQuery()) {
                    int hostelId = 0;
                    tableView.getItems().clear(); 
                    if (resultSet.next()) {
                        hostelId = resultSet.getInt("id");
                    }
    
                    String insertDiscountQuery = "INSERT INTO discounts (hostel_id, dls_code, amount, deadline) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement insertDiscountStatement = connection.prepareStatement(insertDiscountQuery)) {
                        insertDiscountStatement.setInt(1, hostelId);
                        insertDiscountStatement.setString(2, code);
                        insertDiscountStatement.setFloat(3, discountAmount);
                        insertDiscountStatement.setDate(4, discountDeadline);
                        insertDiscountStatement.executeUpdate();
                        loadDiscounts();
                        // Clear form fields
                        clearForm();
    
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Discount added successfully.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add discount. Please try again.");
        }
    }
    
    @FXML
private void UpdateButton() {
    // Get the selected discount
    ObservableList<String> selectedDiscount = tableView.getSelectionModel().getSelectedItem();
    if (selectedDiscount == null) {
        showAlert(Alert.AlertType.ERROR, "Error", "Please select a discount to update.");
        return;
    }

    // Extract the data from the selected discount
    String hostelName = selectedDiscount.get(0);
    String location = selectedDiscount.get(1);
    String room = selectedDiscount.get(2);
    String code = selectedDiscount.get(3);
    String amount = selectedDiscount.get(4);
    String deadlineDate = selectedDiscount.get(5);

    // Open a dialog to allow the user to modify the discount
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Update Discount");
    dialog.setHeaderText(null);
    dialog.setContentText("Enter new discount code:");

    Optional<String> newCodeResult = dialog.showAndWait();
    if (newCodeResult.isPresent()) {
        String newCode = newCodeResult.get();
        tableView.getItems().clear(); 
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String updateQuery = "UPDATE discounts SET dls_code = ? WHERE dls_code = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setString(1, newCode);
                updateStatement.setString(2, code);
                updateStatement.executeUpdate();
                loadDiscounts(); // Reload discounts after update
                showAlert(Alert.AlertType.INFORMATION, "Success", "Discount updated successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to update discount. Please try again.");
        }
    }
}


@FXML
private void deleteButton() {
    // Get the selected discount
    ObservableList<String> selectedDiscount = tableView.getSelectionModel().getSelectedItem();
    if (selectedDiscount == null) {
        showAlert(Alert.AlertType.ERROR, "Error", "Please select a discount to delete.");
        return;
    }

    // Extract the data from the selected discount
    String hostelName = selectedDiscount.get(0);
    String code = selectedDiscount.get(3);

    // Confirm deletion with the user
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    confirmationAlert.setTitle("Confirm Deletion");
    confirmationAlert.setHeaderText(null);
    confirmationAlert.setContentText("Are you sure you want to delete the discount with code: " + code + "?");
    
    Optional<ButtonType> result = confirmationAlert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
        tableView.getItems().clear(); 
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String deleteQuery = "DELETE FROM discounts WHERE dls_code = ?";
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                deleteStatement.setString(1, code);
                deleteStatement.executeUpdate();
                loadDiscounts(); // Reload discounts after deletion
                showAlert(Alert.AlertType.INFORMATION, "Success", "Discount deleted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to delete discount. Please try again.");
        }
    }
}


    @FXML
    private void undoButton() {
        // Implementation of undoButton method
    }

    private void loadDiscounts() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT d.dls_code, d.amount, d.deadline, h.name AS hostel_name, h.location AS hostel_location, r.name AS room_name " +
               "FROM discounts d " +
               "JOIN hostels h ON d.hostel_id = h.id " +
               "JOIN rooms r ON d.hostel_id = r.id";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String hostelName = resultSet.getString("hostel_name");
                        String hostelLocation = resultSet.getString("hostel_location");
                        String roomName = resultSet.getString("room_name");
                        String discountCode = resultSet.getString("dls_code");
                        float amount = resultSet.getFloat("amount");
                        Date deadline = resultSet.getDate("deadline");

                        // Add the fetched data to a row (ObservableList<String>)
                        ObservableList<String> rowData = FXCollections.observableArrayList(
                            hostelName,
                            hostelLocation,
                            roomName,
                            discountCode,
                            String.valueOf(amount),
                            deadline.toString()
                        );

                        // Add the row data to the TableView
                        tableView.getItems().add(rowData);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load discounts from the database.");
        }
    }

    private void clearForm() {
        Hostel_Name.setValue(null);
        Location.setValue(null);
        Rooms.setValue(null);
        discountcode.clear();
        Discountamount.setValue(null);
        deadline.setValue(null);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}