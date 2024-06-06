package Users_Package;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Hostel_Package.Hostel;
import Hostel_Package.Room;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Hostel_Details_Controller implements Initializable {

    @FXML
    private Button Book_Hostel_Button;

    @FXML
    private Button Feedback_Button;

    @FXML
    private Button Home_Button;

    @FXML
    private Text Hostel_Name;

    @FXML
    private ScrollPane Image_Area;

    @FXML
    private Button Logout_Button;

    @FXML
    private Button Notifcation_Button;

    @FXML
    private Button Profile_Button;

    @FXML
    private VBox Room_Area;

    @FXML
    private Button Search_Hostel_Button;

    @FXML
    private Pane Side_Bar;

    private Hostel H;
    public void setData(Hostel h)
    {
        H = h;
        Hostel_Name.setText(h.getName());
        View_Data();
    }

    public void View_Data()
    {
        for(int i = 0; i < 4; i++)
        {
                try {
            FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("Rooms_View.fxml"));
        VBox vv = fxmlLoader.load();
Room_View_Controller hi = fxmlLoader.getController();
Room_Area.getChildren().add(vv);
hi.setData(H.getRooms(),(i+1)+"-Seater Room:");

}
catch (Exception e) {
    e.printStackTrace();
            }
    }
}
        
        @Override
    public void initialize(URL location, ResourceBundle resources) {
}
}
