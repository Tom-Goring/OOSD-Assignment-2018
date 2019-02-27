import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        //DatabaseManager.createTables();

        Parent root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
        stage.setTitle("Touis Table Tennis Association Manager");
        stage.getIcons().add(new Image("images/tennis.png"));
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setScene(new Scene(root));

        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}
