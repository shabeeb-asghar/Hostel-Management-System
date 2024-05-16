import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Landing_page.fxml"));

        Scene scene = new Scene(root, 626, 425);
        scene.getStylesheets().add(getClass().getResource("Landing_Page_Style.css").toExternalForm());
        primaryStage.setScene(scene);

        primaryStage.setTitle("HMS");

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
