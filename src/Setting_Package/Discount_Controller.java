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

public class Discount_Controller {
/**
 * Sample Skeleton for 'Discount.fxml' Controller Class
 */

 
     @FXML // ResourceBundle that was given to the FXMLLoader
     private ResourceBundle resources;
 
     @FXML // URL location of the FXML file that was given to the FXMLLoader
     private URL location;
 
     @FXML // fx:id="Discount"
     private ComboBox<?> Discount; // Value injected by FXMLLoader
 
     @FXML // fx:id="Hostel_Name"
     private ComboBox<?> Hostel_Name; // Value injected by FXMLLoader
 
     @FXML // fx:id="Location"
     private ComboBox<?> Location; // Value injected by FXMLLoader
 
     @FXML // fx:id="Rooms"
     private ComboBox<?> Rooms; // Value injected by FXMLLoader
 
     @FXML // fx:id="Update"
     private Button Update; // Value injected by FXMLLoader
 
     @FXML // fx:id="add"
     private Button add; // Value injected by FXMLLoader
 
     @FXML // fx:id="delete"
     private Button delete; // Value injected by FXMLLoader
 
     @FXML // fx:id="descArea"
     private TextArea descArea; // Value injected by FXMLLoader
 
     @FXML // fx:id="discountCol"
     private TableColumn<?, ?> discountCol; // Value injected by FXMLLoader
 
     @FXML // fx:id="listView"
     private ListView<?> listView; // Value injected by FXMLLoader
 
     @FXML // fx:id="locCol"
     private TableColumn<?, ?> locCol; // Value injected by FXMLLoader
 
     @FXML // fx:id="nameCol"
     private TableColumn<?, ?> nameCol; // Value injected by FXMLLoader
 
     @FXML // fx:id="numCol"
     private TableColumn<?, ?> numCol; // Value injected by FXMLLoader
 
     @FXML // fx:id="roomCol"
     private TableColumn<?, ?> roomCol; // Value injected by FXMLLoader
 
     @FXML // fx:id="tableView"
     private TableView<?> tableView; // Value injected by FXMLLoader
 
     @FXML // fx:id="undo"
     private Button undo; // Value injected by FXMLLoader
 
     @FXML
     void UpdateButton(ActionEvent event) {
 
     }
 
     @FXML
     void addButton(ActionEvent event) {
 
     }
 
     @FXML
     void deleteButton(ActionEvent event) {
 
     }
 
     @FXML
     void undoButton(ActionEvent event) {
 
     }
 
     @FXML // This method is called by the FXMLLoader when initialization is complete
     void initialize() {
         assert Discount != null : "fx:id=\"Discount\" was not injected: check your FXML file 'Discount.fxml'.";
         assert Hostel_Name != null : "fx:id=\"Hostel_Name\" was not injected: check your FXML file 'Discount.fxml'.";
         assert Location != null : "fx:id=\"Location\" was not injected: check your FXML file 'Discount.fxml'.";
         assert Rooms != null : "fx:id=\"Rooms\" was not injected: check your FXML file 'Discount.fxml'.";
         assert Update != null : "fx:id=\"Update\" was not injected: check your FXML file 'Discount.fxml'.";
         assert add != null : "fx:id=\"add\" was not injected: check your FXML file 'Discount.fxml'.";
         assert delete != null : "fx:id=\"delete\" was not injected: check your FXML file 'Discount.fxml'.";
         assert descArea != null : "fx:id=\"descArea\" was not injected: check your FXML file 'Discount.fxml'.";
         assert discountCol != null : "fx:id=\"discountCol\" was not injected: check your FXML file 'Discount.fxml'.";
         assert listView != null : "fx:id=\"listView\" was not injected: check your FXML file 'Discount.fxml'.";
         assert locCol != null : "fx:id=\"locCol\" was not injected: check your FXML file 'Discount.fxml'.";
         assert nameCol != null : "fx:id=\"nameCol\" was not injected: check your FXML file 'Discount.fxml'.";
         assert numCol != null : "fx:id=\"numCol\" was not injected: check your FXML file 'Discount.fxml'.";
         assert roomCol != null : "fx:id=\"roomCol\" was not injected: check your FXML file 'Discount.fxml'.";
         assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'Discount.fxml'.";
         assert undo != null : "fx:id=\"undo\" was not injected: check your FXML file 'Discount.fxml'.";
 
     }
 
  
}
