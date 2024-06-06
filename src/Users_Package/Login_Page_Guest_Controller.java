package Users_Package;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Login_Page_Guest_Controller {

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
    private Hyperlink SignUp_Link;

    private dataBase databaseHandler = new dataBase();

    @FXML
    void initialize() {
        Login_Button.setOnAction(event -> handleLogin());
    }

    private void handleLogin() {
        String email = Email_Text_Field.getText();
        String password = Password_TextField.getText();

        boolean isValid = databaseHandler.validateLogintudents(email, password);

        if (isValid) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome!");
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
