package Setting_Package;

import Setting_Package.Discounts;
import Setting_Package.Single_Discount;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

import Hostel_Package.Hostel;
import Hostel_Package.Room;

public class Discount_Controller {

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
    }
    
private void populateHostelComboBox() {
        // Get hostel data from Hostel class
       /* List<Hostel> hostels = Hostel.getAllRegisteredHostels();

        // Populate Hostel_Name combobox
        for (Hostel hostel : hostels) {
            Hostel_Name.getItems().add(hostel.getName());
        }
        for (Hostel hostel : hostels) {
            Location.getItems().add(hostel.getLocation());
            
        }
        for (Hostel hostel : hostels) {
    List<Room> hostelRooms = hostel.getRooms();
    for (Room room : hostelRooms) {
        Rooms.getItems().add(room.getName());
    }*/
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
        float discountAmount = Float.parseFloat(amount);
        Time discountDeadline = Time.valueOf(deadlineDate.atStartOfDay().toLocalTime());

        discounts.setDiscount(code, discountAmount, discountDeadline);
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
        float newDiscountAmount = Float.parseFloat(newAmount);
        Time newDiscountDeadline = Time.valueOf(newDeadlineDate.atStartOfDay().toLocalTime());

        discounts.updateDiscount(
                selectedDiscount.getDlsCode(), selectedDiscount.getAmount(), selectedDiscount.getDeadline(),
                newCode, newDiscountAmount, newDiscountDeadline
        );
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

        discounts.removeDiscount(selectedDiscount.getDlsCode());
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
