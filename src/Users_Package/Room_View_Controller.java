package Users_Package;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Hostel_Package.Room;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Room_View_Controller implements Initializable {
        @FXML
    private GridPane Room_Grid;

    @FXML
    private Text Room_Type;


    void setData(List<Room> r, String type)
    {
        Room_Type.setText(type);
        for(int i = 0; i < r.size(); i++)
        {
            System.out.println(r.get(i).getRoomNo());
            try
            {
        FXMLLoader fxmlLoader = new FXMLLoader();
                   fxmlLoader.setLocation(getClass().getResource("Room_Item.fxml"));
                   VBox vbox = fxmlLoader.load();
                   Room_Item_Controller hi = fxmlLoader.getController();
                   hi.setData(r.get(i));
                   Room_Grid.add(vbox, i%3, i/3);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


    
}
