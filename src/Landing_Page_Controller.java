import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class Landing_Page_Controller {

    @FXML
    private Button Button_Guest;

    @FXML
    private Button Button_Hostel;

    @FXML
    private Text Text_Description_Guest;

    @FXML
    private Text Text_Description_Hostel;

    @FXML
    private Text Text_Discover;

    @FXML
    private Text Text_Space;

    @FXML
    private Text Text_Stay;

    @FXML
    private Text Text_Unlock;

    @FXML
    private Text Text_Your;

    @FXML
    private Text Text_Your_2;
    @FXML
    public void initialize() {
        Button_Hostel.setOnMouseEntered( e-> Button_Hostel.setStyle("-fx-background-color: white; -fx-text-fill: #232323"));
        Button_Hostel.setOnMouseExited(e -> Button_Hostel.setStyle("-fx-background-color: transparent; -fx-text-flll: white;"));
        Button_Guest.setOnMouseEntered(e -> Button_Guest.setStyle("-fx-background-color: #232323; -fx-text-fill: white;"));
        Button_Guest.setOnMouseExited(e -> Button_Guest.setStyle("-fx-background-color: transparent; -fx-text-flll: #232323;"));

    }
}
