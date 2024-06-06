package Users_Package;
import Hostel_Package.Hostel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

public class Registration_Hostel_Controller {

    @FXML
    private TextField Name_Text_Field;

    @FXML
    private PasswordField Password_Text_Field;

    @FXML
    private TextField Location_Text_Field;

    @FXML
    private TextField Contact_Text_Field;

    @FXML
    private ComboBox<String> one_bed_room;

    @FXML
    private ComboBox<String> two_bed_room;

    @FXML
    private ListView<String> servicesListView;

    @FXML
    private Button Register_Button;

   
   
    @FXML
    private ToggleButton toggleButton;

    @FXML
    private CheckBox laundryCheckBox;

    @FXML
    private CheckBox messCheckBox;

    @FXML
    public void initialize() {
        // Initialize services selection model
        
    
        // Initialize room ComboBoxes
        one_bed_room.getItems().addAll("1", "2", "3", "4", "5");
        two_bed_room.getItems().addAll("1", "2", "3", "4", "5");
    
       
    
        
    }
     @FXML
    void toggle(ActionEvent event) 
{ // Initialize password visibility toggle
    //if(toggleButton != null) {
        toggleButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            Password_Text_Field.setVisible(!newValue);
            Password_Text_Field.setManaged(!newValue);
        });
    }
    @FXML
    private void OnClick(ActionEvent event) {
        try {
            validateInputs();

            // Validate password
            String password = Password_Text_Field.getText();
            if (!isValidPassword(password)) {
                showErrorAlert("Password must contain at least 8 characters, including 1 special character, 1 capital letter, 1 small letter, and 1 number.");
                return;
            }
            StringBuilder selectedServices = new StringBuilder();

            // Check if laundry service is selected
            if (laundryCheckBox.isSelected()) {
                selectedServices.append("Laundry\n");
            }
    
            // Check if mess service is selected
            if (messCheckBox.isSelected()) {
                selectedServices.append("Mess\n");
            }
          

            // Process registration
            registerHostel();
            insertHostelIntoDatabase();
            // Display success message
            showSuccessAlert();
            //viewAllHostelDetails();
            Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        
        
        stage.setScene(App.getScene_Discount());

        } catch (IllegalArgumentException e) {
            showErrorAlert(e.getMessage());
        }
    }
    private void insertHostelIntoDatabase() {
        Hostel hostel = createHostelFromFormData();
        dataBase.insertHostel(hostel);
    }
    
    private Hostel createHostelFromFormData() {
        String name = Name_Text_Field.getText();
        String password = Password_Text_Field.getText();
        String location = Location_Text_Field.getText();
        String contactNumber = Contact_Text_Field.getText();
        int numberOfOneBedRooms = Integer.parseInt(one_bed_room.getValue());
        int numberOfTwoBedRooms = Integer.parseInt(two_bed_room.getValue());
        boolean laundryService = laundryCheckBox.isSelected();
        boolean messService = messCheckBox.isSelected();
    
        return new Hostel(name, password, location, contactNumber, numberOfOneBedRooms, numberOfTwoBedRooms, laundryService, messService);
    }

    private boolean isValidPassword(String password) {
        // Password validation rules
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        return password.matches(passwordRegex);
    }

    private void registerHostel() {
        // Registration logic
        System.out.println("Hostel registration logic goes here...");
      //  Hostel.viewAllHostelDetails();
    }

    private void validateInputs() {
        if (Name_Text_Field.getText().isEmpty()) {
            throw new IllegalArgumentException("Hostel Name is required.");
        }
        if (Password_Text_Field.getText().isEmpty()) {
            throw new IllegalArgumentException("Password is required.");
        }
        if (Location_Text_Field.getText().isEmpty()) {
            throw new IllegalArgumentException("Location is required.");
        }
        if (Contact_Text_Field.getText().isEmpty()) {
            throw new IllegalArgumentException("Contact Number is required.");
        }
        if (!Contact_Text_Field.getText().matches("\\d{11}")) {
            throw new IllegalArgumentException("Contact Number must be 11 numeric digits.");
        }
        if (one_bed_room.getValue() == null) {
            throw new IllegalArgumentException("Please select the number of 1-bed rooms.");
        }
        if (two_bed_room.getValue() == null) {
            throw new IllegalArgumentException("Please select the number of 2-bed rooms.");
        }
       
    }


    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registration Successful");
        alert.setHeaderText(null);
        alert.setContentText("Hostel registered successfully.");
        alert.showAndWait();
    }
}
