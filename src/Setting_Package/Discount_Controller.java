package Setting_Package;

import Setting_Package.Discounts;
import Setting_Package.Single_Discount;
import Users_Package.dataBase;
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
import java.util.List;

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
    private TableView<Single_Discount> tableView;
    @FXML
    private TableColumn<Single_Discount, String> nameCol;
    @FXML
    private TableColumn<Single_Discount, String> locCol;
    @FXML
    private TableColumn<Single_Discount, String> numCol;
    @FXML
    private TableColumn<Single_Discount, String> roomCol;
    @FXML
    private TableColumn<Single_Discount, String> discountCol;
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
        nameCol.setCellValueFactory(new PropertyValueFactory<>("dlsCode"));
        locCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        numCol.setCellValueFactory(new PropertyValueFactory<>("deadline"));

        // Load initial data
        loadDiscounts();
        populateHostelComboBox();
        populateDiscountAmountComboBox();
    }

    private void populateDiscountAmountComboBox() {
        // Add predefined discount values to the Discountamount ComboBox
        Discountamount.getItems().addAll("10%", "20%", "30%", "40%", "50%", "60%", "70%", "80%");
    }

    private void populateHostelComboBox() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            // Fetch hostels
            String hostelQuery = "SELECT id, name, location FROM hostels";
            PreparedStatement hostelStatement = connection.prepareStatement(hostelQuery);
            ResultSet hostelResultSet = hostelStatement.executeQuery();

            while (hostelResultSet.next()) {
                int hostelId = hostelResultSet.getInt("id");
                String hostelName = hostelResultSet.getString("name");
                String hostelLocation = hostelResultSet.getString("location");

                // Add to ComboBoxes
                Hostel_Name.getItems().add(hostelName);
                Location.getItems().add(hostelLocation);

                // Fetch rooms for each hostel
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

        if (hostelName == null || location == null || room == null || code.isEmpty() || amount == null || deadlineDate == null) {
            // Show error message
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter all the fields");
            return;
        }

        // Convert amount and deadline
        float discountAmount = Float.parseFloat(amount.replace("%", "")) / 100; // Convert percentage to a decimal
        Date discountDeadline = Date.valueOf(deadlineDate);

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            // Fetch the hostel_id based on hostel name
            String hostelIdQuery = "SELECT id FROM hostels WHERE name = ?";
            PreparedStatement hostelIdStatement = connection.prepareStatement(hostelIdQuery);
            hostelIdStatement.setString(1, hostelName);
            ResultSet resultSet = hostelIdStatement.executeQuery();
            int hostelId = 0;
            if (resultSet.next()) {
                hostelId = resultSet.getInt("id");
            }

            // Insert the discount into the discounts table
            String insertDiscountQuery = "INSERT INTO discounts (hostel_id, dls_code, amount, deadline) VALUES (?, ?, ?, ?)";
            PreparedStatement insertDiscountStatement = connection.prepareStatement(insertDiscountQuery);
            insertDiscountStatement.setInt(1, hostelId);
            insertDiscountStatement.setString(2, code);
            insertDiscountStatement.setFloat(3, discountAmount);
            insertDiscountStatement.setDate(4, discountDeadline);
            insertDiscountStatement.executeUpdate();

            System.out.println("Discount added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        loadDiscounts();
        clearForm();
    }

    @FXML
    private void UpdateButton() {
        Single_Discount selectedDiscount = tableView.getSelectionModel().getSelectedItem();

        if (selectedDiscount == null) {
            showAlert(Alert.AlertType.ERROR, "Selection Error!", "Please select a discount to update");
            return;
        }

        String hostelName = Hostel_Name.getValue();
        String location = Location.getValue();
        String room = Rooms.getValue();
        String newCode = discountcode.getText();
        String newAmount = Discountamount.getValue();
        LocalDate newDeadlineDate = deadline.getValue();

        if (hostelName == null || location == null || room == null || newCode.isEmpty() || newAmount == null || newDeadlineDate == null) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter all the fields");
            return;
        }

        // Convert new amount and deadline
        float newDiscountAmount = Float.parseFloat(newAmount.replace("%", "")) / 100; // Convert percentage to a decimal
        Date newDiscountDeadline = Date.valueOf(newDeadlineDate);

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            // Fetch the hostel_id based on hostel name
            String hostelIdQuery = "SELECT id FROM hostels WHERE name = ?";
            PreparedStatement hostelIdStatement = connection.prepareStatement(hostelIdQuery);
            hostelIdStatement.setString(1, hostelName);
            ResultSet resultSet = hostelIdStatement.executeQuery();
            int hostelId = 0;
            if (resultSet.next()) {
                hostelId = resultSet.getInt("id");
            }

            // Update the discount in the discounts table
            String updateDiscountQuery = "UPDATE discounts SET dls_code = ?, amount = ?, deadline = ? WHERE hostel_id = ? AND dls_code = ?";
            PreparedStatement updateDiscountStatement = connection.prepareStatement(updateDiscountQuery);
            updateDiscountStatement.setString(1, newCode);
            updateDiscountStatement.setFloat(2, newDiscountAmount);
            updateDiscountStatement.setDate(3, newDiscountDeadline);
            updateDiscountStatement.setInt(4, hostelId);
            updateDiscountStatement.setString(5, selectedDiscount.getDlsCode());
            updateDiscountStatement.executeUpdate();

            System.out.println("Discount updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        loadDiscounts();
        clearForm();
    }

    @FXML
    private void deleteButton() {
        Single_Discount selectedDiscount = tableView.getSelectionModel().getSelectedItem();

        if (selectedDiscount == null) {
            showAlert(Alert.AlertType.ERROR, "Selection Error!", "Please select a discount to delete");
            return;
        }

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            // Fetch the hostel_id based on hostel name
            String hostelIdQuery = "SELECT id FROM hostels WHERE name = ?";
            PreparedStatement hostelIdStatement = connection.prepareStatement(hostelIdQuery);
            hostelIdStatement.setString(1, selectedDiscount.getHostelName());
            ResultSet resultSet = hostelIdStatement.executeQuery();
            int hostelId = 0;
            if (resultSet.next()) {
                hostelId = resultSet.getInt("id");
            }

            // Delete the discount from the discounts table
            String deleteDiscountQuery = "DELETE FROM discounts WHERE hostel_id = ? AND dls_code = ?";
            PreparedStatement deleteDiscountStatement = connection.prepareStatement(deleteDiscountQuery);
            deleteDiscountStatement.setInt(1, hostelId);
            deleteDiscountStatement.setString(2, selectedDiscount.getDlsCode());
            deleteDiscountStatement.executeUpdate();

            System.out.println("Discount deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        loadDiscounts();
    }

    @FXML
    private void undoButton() {
        // Implement undo functionality if needed
    }

    private void loadDiscounts() {
        List<Single_Discount> discountList = discounts.getAllDiscounts();
        tableView.getItems().setAll(discountList);
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