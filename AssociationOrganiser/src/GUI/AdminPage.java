package GUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;




// TODO: make AdminPage GUI page
public class AdminPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        //new pane to work with
        StackPane pane = new StackPane();

        //create new flowpane to work with
        FlowPane flowPane = new FlowPane();

        //create new button, add to pane's list of children
        flowPane.getChildren().add(new Button("OKAY"));
        flowPane.getChildren().add(new Button("OKAY2"));
        flowPane.getChildren().add(new Button("OKAY3"));






        //make new scene, with the pane
        Scene scene = new Scene(flowPane, 350, 200);
        flowPane.setHgap(10);
        primaryStage.setTitle("Flowplanes");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        Application.launch(args);
    }




}
