package Users_Package;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private static Scene scene2;
    private static Scene scene3;
    //sania add this
    private static Scene scene_register_hostel;
    private static Scene scene_discount;
    private static Scene scene_Make_Payment;
    //
    private static Scene scene_hostel_Search, scene_feedback;
    private static Scene scene_hostel_details;
    private static Scene scene_signup_guest, scene_lostfound;
    private static Scene scene_homepage;
    private static Scene scene_notification,scene_profile;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Landing_Page.fxml"));
        Parent root2 = FXMLLoader.load(getClass().getResource("Login_Page_Hostel_Owner.fxml"));
        Parent root3 = FXMLLoader.load(getClass().getResource("Login_Page_Guest.fxml"));
        Parent root_lostfound = FXMLLoader.load(getClass().getResource("LostandFound.fxml"));
        //sania add this
        Parent root_notification = FXMLLoader.load(getClass().getResource("Notification.fxml"));
        Parent root_customuzeprofile = FXMLLoader.load(getClass().getResource("CustomizeProfile.fxml"));
        
        Parent root_homepage = FXMLLoader.load(getClass().getResource("Homepage.fxml"));
        Parent root_Registration_hostel= FXMLLoader.load(getClass().getResource("Registration_Hostel.fxml")); 
        Parent root_Discount=FXMLLoader.load(getClass().getResource("Discount.fxml"));
        Parent root_Make_Payment = FXMLLoader.load(getClass().getResource("Make_Payment.fxml"));
        Parent root_Feedback = FXMLLoader.load(getClass().getResource("feedback.fxml"));
        //
        Parent root_Hostel_Search = FXMLLoader.load(getClass().getResource("Search_Hostel.fxml"));
        Parent root_hostel_details = FXMLLoader.load(getClass().getResource("Hostel_Details.fxml"));
        Parent root_signup_guest = FXMLLoader.load(getClass().getResource("RegisterStudent.fxml"));
        
        Scene scene = new Scene(root, 626, 425);
        scene_lostfound = new Scene(root_lostfound,940,708);
        scene_profile = new Scene(root_customuzeprofile,526,628);
        scene_notification = new Scene(root_notification,499,346);
        scene2 = new Scene(root2,626,425);
        scene_homepage= new Scene(root_homepage,636,425);
        scene3 = new Scene(root3,626,425);
        scene_register_hostel = new Scene(root_Registration_hostel,626,465);
        scene_discount= new Scene(root_Discount,942,708);
        scene_hostel_Search = new Scene(root_Hostel_Search,600,400);
        scene_hostel_details = new Scene(root_hostel_details,600,400);
        scene_Make_Payment=new Scene(root_Make_Payment, 942, 608);
        scene_feedback=new Scene(root_Feedback, 942, 708);
        scene_signup_guest = new Scene(root_signup_guest,636,425);    
        primaryStage.setScene(scene);


        primaryStage.setTitle("HMS");

        primaryStage.show();
    }
    public static Scene getHome()
    {
        return scene_homepage;
    }
    public static Scene getsearch()
    {
        return scene_hostel_Search;
    }
    public static Scene getprofile()
    {
        return scene_profile;
    }
    public static Scene getnotification()
    {
        return scene_notification;
    }
    public static Scene getlostfound()
    {
        return scene_lostfound;
    }
    public static Scene getpayment()
    {
        return scene_Make_Payment;
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
    public static Scene getScene_feedback()
    {
        return scene_feedback;
    }
    public static Scene getScene_Hostel_Details()
    {
        return scene_hostel_details;
    }
    public static Scene getScene_register_student()
    {
        return scene_signup_guest;
    }
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
