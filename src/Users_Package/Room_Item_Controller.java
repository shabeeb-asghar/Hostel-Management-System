package Users_Package;

import java.net.URL;
import java.util.ResourceBundle;

import Hostel_Package.Room;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Room_Item_Controller implements Initializable {
    @FXML
    private Text Guest_no;

    @FXML
    private Button Room_Book;

    @FXML
    private Text Room_No;

    @FXML
    private Text Room_Price;

    private Room R;
    private dataBase db = new dataBase();
    @FXML
    void Room_Book_Clicked(ActionEvent event) {
        int roomId = 1; // Replace with the actual room ID you want to book
        boolean availability = false; // Assuming booking means setting availability to false

        db.updateRoomAvailability(roomId, availability);
        
        // Show confirmation dialog
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Booking Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Room booked successfully!");
        alert.showAndWait();
         Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

         stage.setScene(App.getpayment());

    }

    public void setData(Room r)
    {   R = r;
        Room_No.setText("Room No: " + r.getRoomNo());
        Room_Price.setText(Double.toString(r.getRoomPrice()));
        Guest_no.setText(Integer.toString(r.getGuestNo()));
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
