package Users_Package;
import Hostel_Package.Hostel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Users_Package.*;

public class RegisterStudentController {


    @FXML
    private TextField CNIC_Text_Field;

    @FXML
    private TextField Contact_Text_Field;

    @FXML
    private TextField Email_TextField;

    @FXML
    private VBox Login_Area;

    @FXML
    private TextField Name_Text_Field;

    @FXML
    private PasswordField Password_Text_Field;

    @FXML
    private Button Register_Button;


    @FXML
private dataBase db;
    public void initialize()
    {
        this.db = new dataBase();
    }
    @FXML
    void OnClick(ActionEvent event) {
        // Validate password
        String password = Password_Text_Field.getText();
        if (!isValidPassword(password)) {
            showErrorAlert("Password must contain at least 8 characters, including 1 special character, 1 capital letter, 1 small letter, and 1 number.");
            return;
        }
        String email = Email_TextField.getText();
        if (isStudentRegistered(email)) {
            showErrorAlert("Student with the provided email is already registered.");
            return;
        }

        registerStudent();
        insertStudentIntoDatabase();
        // Display success message
        showSuccessAlert(event);
    }

    public void registerStudent() {
        // Create a student object from form data
        Student student = createStudentFromFormData();

        // Insert the student into the database
        insertStudentIntoDatabase(student);
    }
    private void insertStudentIntoDatabase(Student student) {
        // Call the database method to insert the student
        db.insertStudent(student);
    }
    
    private boolean isValidPassword(String password) {
        // Password validation rules
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        return password.matches(passwordRegex);
    }
 private void insertStudentIntoDatabase() {
        Student s = createStudentFromFormData();
        dataBase.insertStudent(s);
    }
    
    private boolean isStudentRegistered(String email) {
        return db.isStudentRegistered(email);
    }
    
    private Student createStudentFromFormData() {
       
        String Name = Name_Text_Field.getText();
        String password = Password_Text_Field.getText();
        String CNIC = CNIC_Text_Field.getText();
        String contactNumber = Contact_Text_Field.getText();
       String email=Email_TextField.getText();
       
    
        return new Student(Name,email, password, contactNumber,CNIC);
    }
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registration Successful");
        alert.setHeaderText(null);
        alert.setContentText("Student registered successfully.");
        alert.showAndWait();
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
    
             stage.setScene(App.getScene3());

    }
}