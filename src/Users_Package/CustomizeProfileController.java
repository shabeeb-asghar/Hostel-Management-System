package Users_Package;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CustomizeProfileController {

    @FXML
    private Button HomeButton;
    @FXML
    private TextField nameField;

    @FXML
    private TextField contactNumberField;

    @FXML
    private TextField fatherNameField;

    @FXML
    private TextField cnicField;

    @FXML
    private TextField emailField;

    @FXML
    private ToggleGroup genderGroup;

    @FXML
    private RadioButton maleRadioButton;

    @FXML
    private RadioButton femaleRadioButton;

    @FXML
    private ComboBox<String> courseComboBox;

    @FXML
    private DatePicker datePicker;

    private dataBase db;

    @FXML
    private void initialize() {
        db = new dataBase();
        genderGroup = new ToggleGroup();
        maleRadioButton.setToggleGroup(genderGroup);
        femaleRadioButton.setToggleGroup(genderGroup);

        courseComboBox.getItems().addAll("B.Tech", "BSc", "BCA", "BBA", "MBA", "MCA", "Others");
    }

    @FXML
    private void handleSaveButtonAction() {
        String name = nameField.getText();
        String contactNumber = contactNumberField.getText();
        String fatherName = fatherNameField.getText();
        String cnic = cnicField.getText();
        String email = emailField.getText();
        RadioButton selectedGender = (RadioButton) genderGroup.getSelectedToggle();
        String course = courseComboBox.getValue();
        String dateOfBirth = datePicker.getValue() != null ? datePicker.getValue().toString() : "";

        if (name.isEmpty() || contactNumber.isEmpty() || fatherName.isEmpty() || cnic.isEmpty() || email.isEmpty() ||
                selectedGender == null || course == null || dateOfBirth.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill out all fields.");
            return;
        }

        // Validate inputs...

        String gender = selectedGender.getText();

        // Save profile to database
        db.insertStudentProfile(name,email, "", contactNumber, cnic);

        // Display success message
        showAlert(Alert.AlertType.INFORMATION, "Success", "Profile information has been updated successfully.");
    }

    @FXML
    private void handleCancelButtonAction() {
        // Clear all fields
        nameField.clear();
        contactNumberField.clear();
        fatherNameField.clear();
        cnicField.clear();
        emailField.clear();
        genderGroup.selectToggle(null);
        courseComboBox.getSelectionModel().clearSelection();
        datePicker.setValue(null);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
        @FXML
    void onClickHome(ActionEvent event) {
                    Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
    
             stage.setScene(App.getHome());

    }
}
