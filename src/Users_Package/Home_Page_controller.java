package Users_Package;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Home_Page_controller {

    @FXML
    private ResourceBundle resources;
    @FXML
    private Button Discount;
    @FXML
    private URL location;

    @FXML
    private Button Book_Hostel_Button;

    @FXML
    private Button Feedback_Button;

    @FXML
    private Button Home_Button;

    @FXML
    private Button Logout_Button;
    @FXML
    private Button LostFound;

    @FXML
    private Button Notifcation_Button;

    @FXML
    private Button Profile_Button;

    @FXML
    private Button Search_Hostel_Button;

    @FXML
    private Pane Side_Bar;
    @FXML
    void Discount(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

         stage.setScene(App.getScene_Discount());
    }
    @FXML
    void Booking(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

         stage.setScene(App.getsearch());
    }

    @FXML
    void Feedback(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

         stage.setScene(App.getScene_feedback());
    }

    @FXML
    void Notification(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

         stage.setScene(App.getnotification());
    }

    @FXML
    void Profile(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

         stage.setScene(App.getprofile());
    }

    @FXML
    void Searching(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        stage.setScene(App.getsearch());
    }
    @FXML
    void LostFound(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

         stage.setScene(App.getlostfound());
    }
    @FXML
    void initialize() {
        assert Book_Hostel_Button != null : "fx:id=\"Book_Hostel_Button\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert Feedback_Button != null : "fx:id=\"Feedback_Button\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert Home_Button != null : "fx:id=\"Home_Button\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert Logout_Button != null : "fx:id=\"Logout_Button\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert Notifcation_Button != null : "fx:id=\"Notifcation_Button\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert Profile_Button != null : "fx:id=\"Profile_Button\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert Search_Hostel_Button != null : "fx:id=\"Search_Hostel_Button\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert Side_Bar != null : "fx:id=\"Side_Bar\" was not injected: check your FXML file 'Homepage.fxml'.";

    }

}
