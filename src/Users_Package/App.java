package Users_Package;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import UI_Package.*;;

public class App extends Application {
    private static Scene scene2;
    private static Scene scene3;
    //sania add this
    private static Scene scene_register_hostel;
    private static Scene scene_discount;
    private static Scene scene_Make_payment;
    //

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Landing_Page.fxml"));
        Parent root2 = FXMLLoader.load(getClass().getResource("Login_Page_Hostel_Owner.fxml"));
        Parent root3 = FXMLLoader.load(getClass().getResource("Login_Page_Guest.fxml"));
        //sania add this
        Parent root_Registration_hostel= FXMLLoader.load(getClass().getResource("Registration_Hostel.fxml")); 
        Parent root_Discount=FXMLLoader.load(getClass().getResource("Discount.fxml"));
        Parent root_Make_payment= FXMLLoader.load(getClass().getResource("Make_Payment.fxml"));
        //
        Scene scene = new Scene(root, 626, 425);
        scene2 = new Scene(root2,626,425);
        scene3 = new Scene(root3,626,425);
        scene_register_hostel = new Scene(root_Registration_hostel,626,465);
        scene_discount= new Scene(root_Discount,942,708);
        scene_Make_payment= new Scene(root_Make_payment,942,608);
        primaryStage.setScene(scene);


        primaryStage.setTitle("HMS");

        primaryStage.show();
    }
    public static Scene getScene_make_payment()
    {
        return scene_Make_payment;
    }
    public static Scene getScene2()
    {
        return scene2;
    }
    public static Scene getScene3()
    {
        return scene3;
    }
     //sania add this
    public static Scene getScene_register_hostel()
    {
        return scene_register_hostel;
    }
    public static Scene getScene_Discount()
    {
        return scene_discount;
    }
    //
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC driver not found!");
            e.printStackTrace();
            return;
        }
        launch(args);
    }
}
