package Users_Package;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private static Scene scene2;
    private static Scene scene3,scene_register_hostel;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Landing_Page.fxml"));
        Parent root2 = FXMLLoader.load(getClass().getResource("Login_Page_Hostel_Owner.fxml"));
        Parent root3 = FXMLLoader.load(getClass().getResource("Login_Page_Guest.fxml"));
        Parent root_Registration_hostel= FXMLLoader.load(getClass().getResource("Registration_Hostel.fxml")); 

        Scene scene = new Scene(root, 626, 425);
        scene2 = new Scene(root2,626,425);
        scene3 = new Scene(root3,626,425);
        scene_register_hostel = new Scene(root_Registration_hostel,626,465);
        primaryStage.setScene(scene);


        primaryStage.setTitle("HMS");

        primaryStage.show();
    }
    public static Scene getScene2()
    {
        return scene2;
    }
    public static Scene getScene3()
    {
        return scene3;
    }
    public static Scene getScene_register_hostel()
    {
        return scene_register_hostel;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
