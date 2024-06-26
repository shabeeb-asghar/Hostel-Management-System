package Users_Package;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Hostel_Package.Hostel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Search_Hostel_Controller implements Initializable {

    @FXML
    private HBox Whole_Page;

    @FXML
    private Pane Side_Bar;

    @FXML
    private Button Home_Button;

    @FXML
    private Button Book_Hostel_Button;

    @FXML
    private Button Search_Hostel_Button;

    @FXML
    private Button Notifcation_Button;

    @FXML
    private Button Feedback_Button;

    @FXML
    private Button Profile_Button;

    @FXML
    private Button Logout_Button;

    @FXML
    private VBox Hostel_Area;

    @FXML
    private TextField Search_Field;

    @FXML
    private ScrollPane Data_Area;

    @FXML
    private VBox Hostel_List;
    private HBox Hostel_Item = new HBox();

    private List<Hostel> hostels;
    private List<Hostel_Item_Controller> hostelItemControllers;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataBase db = new dataBase();
        hostels = db.fetchHostelData();
        for(Hostel h : hostels)
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Hostel_Item.fxml"));
            try {
            Hostel_Item = fxmlLoader.load();
            Hostel_Item_Controller hi = fxmlLoader.getController();
            hi.setData(h);
            Hostel_List.getChildren().add(Hostel_Item);
            }
            catch (IOException e) {
        e.printStackTrace();
        }
            }
    }
    @FXML
    private void searchHostels(ActionEvent event) {
        String searchText = Search_Field.getText().trim().toLowerCase();

        // Clear the current list of hostels
        Hostel_List.getChildren().clear();

        // Fetch hostels based on search criteria
        dataBase db = new dataBase();
        List<Hostel> filteredHostels = db.fetchHostelSearch(searchText);

        // Add the filtered hostels to the UI
        for (Hostel h : filteredHostels) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Hostel_Item.fxml"));
            try {
                HBox hostelItem = fxmlLoader.load();
                Hostel_Item_Controller hi = fxmlLoader.getController();
                hi.setData(h);
                Hostel_List.getChildren().add(hostelItem);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    void onClickHome(ActionEvent event) {
        
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
             stage.setScene(App.getHome());

    }
}