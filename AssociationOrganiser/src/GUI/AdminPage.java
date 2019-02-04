package GUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;




// TODO: make AdminPage GUI page
public class AdminPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new Button("TESTING BUTTON"), 200, 200);
        primaryStage.setTitle("TESTING STAGE");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        Application.launch(args);
    }




}
