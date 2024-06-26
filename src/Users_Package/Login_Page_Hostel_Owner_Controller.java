package Users_Package;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login_Page_Hostel_Owner_Controller {

    @FXML
    private StackPane Button_with_G_Icon;

    @FXML
    private TextField Email_Text_Field;

    @FXML
    private Hyperlink Forgot_Password;

    @FXML
    private Button Google_SignIn_Button;

    @FXML
    private VBox Login_Area;

    @FXML
    private Button Login_Button;

    @FXML
    private PasswordField Password_TextField;

    @FXML
    private CheckBox Remeber_Me;

    @FXML
    private Button SignUp_Link;

    @FXML
    private dataBase db = new dataBase();

        void initialize() {
        
    }
    @FXML
        public void onSignUpLinkClick(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

         stage.setScene(App.getScene_register_hostel());
    }
    @FXML
    public void handleLogin(ActionEvent event) {
        String email = Email_Text_Field.getText();
        String password = Password_TextField.getText();

        boolean isValid = db.validateLoginHostel(email, password);

        if (isValid) {
            showAlert(Alert.AlertType.CONFIRMATION, "Login Success", "Login Success");

            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
             stage.setScene(App.getHome());
            
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid email or password.");

        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
