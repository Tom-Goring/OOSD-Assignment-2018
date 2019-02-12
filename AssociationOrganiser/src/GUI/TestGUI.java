package GUI;

import DB.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TestGUI extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Tabs");
        Scene scene = new Scene(new VBox(), 400, 350);
        scene.setFill(Color.OLDLACE);

        // make menus

        MenuBar menubar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("View");

        menubar.getMenus().addAll(menuFile, menuEdit, menuView);

        // make tabs

        TabPane tabpane = new TabPane();

        for (int i = 0; i < 3; i++) {

            Tab tab = new Tab("Tab_" + (i+1));
            Label label = new Label("This is Tab: " + (i+1));
            tab.setContent(label);
            tabpane.getTabs().add(tab);
        }

        ((VBox) scene.getRoot()).getChildren().addAll(menubar, tabpane);

        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
