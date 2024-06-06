package Users_Package;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

import Hostel_Package.Hostel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Hostel_Item_Controller implements Initializable{

    @FXML
    private Text Contact;

    @FXML
    private HBox Hostel_Data;

    @FXML
    private Text Hostel_Name;

    @FXML
    private Text Hostel_Price;

    @FXML
    private Text Hostel_Rooms;

    @FXML
    private ImageView Image;

    @FXML
    private Text Laundry;

    @FXML
    private Text Mess;
    private Hostel hostel;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Hostel_Data.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
    Node source = (Node) event.getSource();
    Stage stage = (Stage) source.getScene().getWindow();
    
    // Get the next scene's controller
    FXMLLoader loader = new FXMLLoader(getClass().getResource("Hostel_Details.fxml"));
    try {
        Scene nextScene = new Scene(loader.load());
        Hostel_Details_Controller nextController = loader.getController();
        
        // Pass data to the next scene's controller
        nextController.setData(hostel);
        
        // Set the scene
        stage.setScene(nextScene);
    } catch (IOException e) {
        e.printStackTrace();
    }
              }
        }
        );
    }

    public void setData(Hostel H)
    {
        hostel = H;
        Hostel_Name.setText(H.getName());
        int o = H.getNumberOfOneBedRooms();
        int t = H.getNumberOfTwoBedRooms();
        Hostel_Rooms.setText(Integer.toString(o+t));
        Hostel_Price.setText(Double.toString(H.getPrice()));
        Contact.setText(H.getContactNumber());
        Mess.setText(H.hasMessService()? "True" : "False");
        Laundry.setText(H.hasLaundryService()? "True" : "False");
        Image image = new Image("file:E:/Univerity Items/Semester 4/SDA/Project/Hostel-Management-System/Assets/nick-kimel-GrLnSHJT1fI-unsplash.jpg"); 
        Image = new ImageView(image);
        


    }

}
