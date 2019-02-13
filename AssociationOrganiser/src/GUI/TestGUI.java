package GUI;

import DB.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TestGUI extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Table Tennis Association Manager");
        Scene scene = new Scene(new VBox(), 400, 350);
        scene.setFill(Color.OLDLACE);

        // make menus

        // TODO: add functionality to menu
        MenuBar menubar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("View");

        menubar.getMenus().addAll(menuFile, menuEdit, menuView);

        // make tabs
        TabPane tabpane = new TabPane();
        tabpane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // make first tabs contents
        Tab player_t = new Tab("Player");
        player_t.setContent(getPlayerGrid());
        tabpane.getTabs().add(player_t);

        Tab team_t = new Tab("Team");
        team_t.setContent(getTeamGrid());
        tabpane.getTabs().add(team_t);

        ((VBox) scene.getRoot()).getChildren().addAll(menubar, tabpane);

        Image image = new Image("/images/tennis.png");
        primaryStage.getIcons().add(image);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // TODO: make admin, viewer, and score tabs

    private GridPane getPlayerGrid() {

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Player");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label addPlayer_l = new Label("Player Name:");
        grid.add(addPlayer_l, 0, 1);

        TextField tf_enterName = new TextField();
        grid.add(tf_enterName, 1, 1);

        Label fillTeam_l = new Label("Select Team:");
        grid.add(fillTeam_l, 0, 2);

        ObservableList<Team> teams = FXCollections.observableArrayList(Team.getTeamList());
        ComboBox<Team> cb_selectTeam = new ComboBox<>(teams);

        cb_selectTeam.setOnMouseClicked(event -> {

            cb_selectTeam.getItems().clear();
            cb_selectTeam.getItems().addAll(Team.getTeamList());
        });

        grid.add(cb_selectTeam, 1, 2);

        Button b_addPlayer = new Button("Add Player");

        b_addPlayer.setOnAction(actionEvent-> {

            // TODO: verify player isnt present? make DB do this?
            Player player = new Player(tf_enterName.getText(), cb_selectTeam.getValue().toString());
            player.addPlayerToDatabase();
        });

        grid.add(b_addPlayer, 1, 15);

        grid.setGridLinesVisible(false);

        return grid;
    }

    private GridPane getTeamGrid() {

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Team");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label addPlayer_l = new Label("Team Name:");
        grid.add(addPlayer_l, 0, 1);

        TextField enterName = new TextField();
        grid.add(enterName, 1, 1);

        Button button = new Button("Add Team");
        button.setOnAction(actionEvent -> {

            // TODO: add a check to make sure the same team name isnt already present (maybe make this a database thing?)
            // maybe append a number to the end of non unique names
            Team team = new Team(enterName.getText());
            team.addTeamToDatabase();
        });

        grid.add(button, 2, 1);

        grid.setGridLinesVisible(true);

        return grid;
    }
}
