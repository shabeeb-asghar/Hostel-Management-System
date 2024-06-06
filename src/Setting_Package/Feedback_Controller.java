package Setting_Package;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class Feedback_Controller {
/**
 * Sample Skeleton for 'LostandFound.fxml' Controller Class
 */
     @FXML // ResourceBundle that was given to the FXMLLoader
     private ResourceBundle resources;
 
     @FXML // URL location of the FXML file that was given to the FXMLLoader
     private URL location;
 
     @FXML // fx:id="Hostel_Name"
     private ComboBox<?> Hostel_Name; // Value injected by FXMLLoader
 
     @FXML // fx:id="Location"
     private ComboBox<?> Location; // Value injected by FXMLLoader
 
     @FXML // fx:id="Rating"
     private ComboBox<?> Rating; // Value injected by FXMLLoader
 
     @FXML // fx:id="RatingCol"
     private TableColumn<?, ?> RatingCol; // Value injected by FXMLLoader
 
     @FXML // fx:id="Submit"
     private Button Submit; // Value injected by FXMLLoader
 
     @FXML // fx:id="add"
     private Button add; // Value injected by FXMLLoader
 
     @FXML // fx:id="delete"
     private Button delete; // Value injected by FXMLLoader
 
     @FXML // fx:id="descArea"
     private TextArea descArea; // Value injected by FXMLLoader
 
     @FXML // fx:id="descpCol"
     private TableColumn<?, ?> descpCol; // Value injected by FXMLLoader
 
     @FXML // fx:id="description"
     private TextArea description; // Value injected by FXMLLoader
 
     @FXML // fx:id="listView"
     private ListView<?> listView; // Value injected by FXMLLoader
 
     @FXML // fx:id="locCol"
     private TableColumn<?, ?> locCol; // Value injected by FXMLLoader
 
     @FXML // fx:id="nameCol"
     private TableColumn<?, ?> nameCol; // Value injected by FXMLLoader
 
     @FXML // fx:id="numCol"
     private TableColumn<?, ?> numCol; // Value injected by FXMLLoader
 
     @FXML // fx:id="tableView"
     private TableView<?> tableView; // Value injected by FXMLLoader
 
     @FXML // fx:id="undo"
     private Button undo; // Value injected by FXMLLoader
 
     @FXML
     void addButton(ActionEvent event) {
 
     }
 
     @FXML
     void deleteButton(ActionEvent event) {
 
     }
 
     @FXML
     void submitButton(ActionEvent event) {
 
     }
 
     @FXML
     void undoButton(ActionEvent event) {
 
     }
 
     @FXML // This method is called by the FXMLLoader when initialization is complete
     void initialize() {
         assert Hostel_Name != null : "fx:id=\"Hostel_Name\" was not injected: check your FXML file 'LostandFound.fxml'.";
         assert Location != null : "fx:id=\"Location\" was not injected: check your FXML file 'LostandFound.fxml'.";
         assert Rating != null : "fx:id=\"Rating\" was not injected: check your FXML file 'LostandFound.fxml'.";
         assert RatingCol != null : "fx:id=\"RatingCol\" was not injected: check your FXML file 'LostandFound.fxml'.";
         assert Submit != null : "fx:id=\"Submit\" was not injected: check your FXML file 'LostandFound.fxml'.";
         assert add != null : "fx:id=\"add\" was not injected: check your FXML file 'LostandFound.fxml'.";
         assert delete != null : "fx:id=\"delete\" was not injected: check your FXML file 'LostandFound.fxml'.";
         assert descArea != null : "fx:id=\"descArea\" was not injected: check your FXML file 'LostandFound.fxml'.";
         assert descpCol != null : "fx:id=\"descpCol\" was not injected: check your FXML file 'LostandFound.fxml'.";
         assert description != null : "fx:id=\"description\" was not injected: check your FXML file 'LostandFound.fxml'.";
         assert listView != null : "fx:id=\"listView\" was not injected: check your FXML file 'LostandFound.fxml'.";
         assert locCol != null : "fx:id=\"locCol\" was not injected: check your FXML file 'LostandFound.fxml'.";
         assert nameCol != null : "fx:id=\"nameCol\" was not injected: check your FXML file 'LostandFound.fxml'.";
         assert numCol != null : "fx:id=\"numCol\" was not injected: check your FXML file 'LostandFound.fxml'.";
         assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'LostandFound.fxml'.";
         assert undo != null : "fx:id=\"undo\" was not injected: check your FXML file 'LostandFound.fxml'.";
 
     }
 
  
}
