// TODO: add error display for pre-existing team and player names
// TODO: add error for when less than 2 teams exist and fixtures are generated
// TODO: make submit new team button clear textfield
// TODO: generate team stats method / window
// TODO: make the textfields in the score sheet integer ones

package controller;

import DB.DatabaseManager;
import model.*;
import view.UserListViewCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    // Observable lists
    private ObservableList<User> ol_Users;
    private ObservableList<Team> ol_Teams;
    private ObservableList<Player> ol_HomePlayers;
    private ObservableList<Player> ol_AwayPlayers;

    // TabPane elements
	@FXML private TabPane tp_tabPane;
	@FXML private Tab t_Admin;
	@FXML private Tab t_Viewer;
	@FXML private Tab t_ScoreSheets;

	// Admin Page elements

	@FXML private ListView lv_Users;
    @FXML private TextField btn_EnterNewTeamName;
    @FXML private Button btn_RegTeam;

	@FXML private TextField btn_EnterNewPlayerName;
	@FXML private ComboBox<Team> cb_SelectPlayerTeam;

    // Viewer Page elements

    @FXML private Pane p_MatchViewer;
    @FXML private Pane p_ShowTeamStats;

    // Score Sheet Page elements
    @FXML private Button btn_NewSheet;
    @FXML private Button btn_ModifySheet;
    @FXML private ComboBox<Team> cb_SelectHomeTeam;
    @FXML private ComboBox<Team> cb_SelectAwayTeam;
    @FXML private ComboBox<Player> cb_SelectHomePlayer1;
    @FXML private ComboBox<Player> cb_SelectHomePlayer2;
    @FXML private ComboBox<Player> cb_SelectAwayPlayer1;
    @FXML private ComboBox<Player> cb_SelectAwayPlayer2;

    @FXML private GridPane gp_MatchForm;


	// Page Scope methods

	public HomePageController() { // called before initialize, then FXML are initialized, then initialize is called

		ol_Users = FXCollections.observableArrayList();
		ol_Teams = FXCollections.observableArrayList();
		ol_HomePlayers = FXCollections.observableArrayList();
		ol_AwayPlayers = FXCollections.observableArrayList();

		ArrayList<User> userList = DatabaseManager.DB_User.getUserListFromDatabase();
		ArrayList<Team> teamList = DatabaseManager.DB_Team.getTeamListFromDatabase();

		ol_Users.addAll(userList);
		ol_Teams.addAll(teamList);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		if (User.currentUser.getPrivilegeLevel() != 2) { // hide admin tab from non admin accounts
			closeTab(t_Admin);
		}

		lv_Users.setItems(ol_Users);
		lv_Users.setCellFactory(userListView -> new UserListViewCell());
		cb_SelectPlayerTeam.setItems(ol_Teams);

		ol_Teams.addListener(new ListChangeListener<Team>() {
            @Override
            public void onChanged(Change<? extends Team> c) {
                cb_SelectPlayerTeam.setItems(ol_Teams);
            }
        });

        cb_SelectHomeTeam.setItems(ol_Teams);
        cb_SelectAwayTeam.setItems(ol_Teams);

        cb_SelectHomeTeam.valueProperty().addListener(new ChangeListener<Team>() {
            @Override
            public void changed(ObservableValue<? extends Team> observable, Team oldValue, Team newValue) {

                ArrayList<Player> players = cb_SelectHomeTeam.getValue().getPlayerList();
                ol_HomePlayers.setAll(players);
                cb_SelectHomePlayer1.setItems(ol_HomePlayers);
                cb_SelectHomePlayer2.setItems(ol_HomePlayers);
            }
        });

        cb_SelectAwayTeam.valueProperty().addListener(new ChangeListener<Team>() {
            @Override
            public void changed(ObservableValue<? extends Team> observable, Team oldValue, Team newValue) {

                ArrayList<Player> players = cb_SelectAwayTeam.getValue().getPlayerList();
                ol_AwayPlayers.setAll(players);
                cb_SelectAwayPlayer1.setItems(ol_AwayPlayers);
                cb_SelectAwayPlayer2.setItems(ol_AwayPlayers);
            }
        });
	}

    private void closeTab(Tab tab) {

        EventHandler<Event> handler = tab.getOnClosed();
        if (handler != null) {
            handler.handle(null);
        }
        else {
            tab.getTabPane().getTabs().remove(tab);
        }
    }

	// Admin Page Methods

	public void registerNewPlayer(ActionEvent actionEvent) {

		Player player = new Player(btn_EnterNewPlayerName.getText());
		Team team = cb_SelectPlayerTeam.getValue();

		DatabaseManager.DB_Player.addNewPlayerToDatabase(player, team);
	}

    public void registerNewTeam(ActionEvent actionEvent) {

	    Team team = new Team(btn_EnterNewTeamName.getText());
	    DatabaseManager.DB_Team.addNewTeamToDatabase(team);
	    ol_Teams.add(team); // to update combobox without having to retrieve from SQL again
    }

    public void generateFixtures(ActionEvent actionEvent) {

        Fixtures fixtures = Fixtures.generateFixtures();

        if (fixtures != null) {
            DatabaseManager.DB_Fixtures.addFixturesToDatabase(fixtures);
        }
        else {

            // output error message requiring at least 2 teams to be present
        }
    }

    // Viewer Page Methods

    public void viewFixtures(ActionEvent actionEvent) {

    }

    public void showTeamStats(ActionEvent actionEvent) {

	    p_ShowTeamStats.setVisible(true);
	    p_MatchViewer.setVisible(false);
    }

    public void openMatchViewer(ActionEvent actionEvent) {

        p_ShowTeamStats.setVisible(false);
        p_MatchViewer.setVisible(true);
    }

    // Score Sheet Page Methods

    public void createNewScoreSheet(ActionEvent actionEvent) {


    }

    public void modifyExistingSheet(ActionEvent actionEvent) {


    }

    public void calculateAndSubmitScores(ActionEvent actionEvent) {

        Match match = new Match();

        match.setHomeTeam(cb_SelectHomeTeam.getValue());
        match.setAwayTeam(cb_SelectAwayTeam.getValue());

        Player HP1 = cb_SelectHomePlayer1.getValue();
        Player HP2 = cb_SelectHomePlayer2.getValue();
        Player AP1 = cb_SelectAwayPlayer1.getValue();
        Player AP2 = cb_SelectAwayPlayer2.getValue();

        match.setHomeTeamPlayer1(HP1);
        match.setHomeTeamPlayer2(HP2);
        match.setAwayTeamPlayer1(AP1);
        match.setAwayTeamPlayer2(AP2);

        match.getSet(4).setHomeTeamPlayer(HP1);
        match.getSet(4).setHomeTeamPlayer(HP2);
        match.getSet(4).setHomeTeamPlayer(AP1);
        match.getSet(4).setHomeTeamPlayer(AP2);

        for (int i = 0; i < gp_MatchForm.getChildren().size(); i++) {

            if (gp_MatchForm.getChildren().get(i).getClass() == VBox.class) {

                // im sorry for this monstrosity
                VBox vb = (VBox) gp_MatchForm.getChildren().get(i);
                String ID = vb.getId();

                TextField[] scoreEntries = new TextField[3];
                scoreEntries[0] = (TextField) vb.getChildren().get(0);
                scoreEntries[1] = (TextField) vb.getChildren().get(1);
                scoreEntries[2] = (TextField) vb.getChildren().get(2);

                // if this set is a double
                if (vb.getId().equals("HDS") || vb.getId().equals("ADS")) {

                    // set game scores
                    int gameIndex = 0;
                    for (TextField gameScore : scoreEntries) {

                        // check if this is for home or away
                        if (vb.getId().charAt(0) == 'H') {

                            match.getSet(4).getGame(gameIndex).setHomeTeamScore(Integer.parseInt(gameScore.getText()));
                            gameIndex++;
                        }
                        else if (vb.getId().charAt(0) == 'A') {

                            match.getSet(4).getGame(gameIndex).setAwayTeamScore(Integer.parseInt(gameScore.getText()));
                            gameIndex++;
                        }
                    }
                }
                else {

                    int setNumber = vb.getId().charAt(2) - '0'; // converts char into int due to the way ASCII works
                    int gameIndex = 0;
                    // handle the information of whatever set we're currently looking at
                    switch (setNumber) {

                        case 1:
                            match.getSet(setNumber-1).setHomeTeamPlayer(HP1);
                            match.getSet(setNumber-1).setAwayTeamPlayer(AP1);
                            gameIndex = 0;
                            for (TextField gameScore : scoreEntries) {

                                // check if this is for home or away
                                if (vb.getId().charAt(0) == 'H') {

                                    match.getSet(setNumber-1).getGame(gameIndex).setHomeTeamScore(Integer.parseInt(gameScore.getText()));
                                    gameIndex++;
                                }
                                else if (vb.getId().charAt(0) == 'A') {

                                    match.getSet(setNumber-1).getGame(gameIndex).setAwayTeamScore(Integer.parseInt(gameScore.getText()));
                                    gameIndex++;
                                }
                            }
                            break;
                        case 2:
                            match.getSet(setNumber-1).setHomeTeamPlayer(HP1);
                            match.getSet(setNumber-1).setAwayTeamPlayer(AP2);
                            gameIndex = 0;
                            for (TextField gameScore : scoreEntries) {

                                // check if this is for home or away
                                if (vb.getId().charAt(0) == 'H') {

                                    match.getSet(setNumber-1).getGame(gameIndex).setHomeTeamScore(Integer.parseInt(gameScore.getText()));
                                    gameIndex++;
                                }
                                else if (vb.getId().charAt(0) == 'A') {

                                    match.getSet(setNumber-1).getGame(gameIndex).setAwayTeamScore(Integer.parseInt(gameScore.getText()));
                                    gameIndex++;
                                }
                            }
                            break;
                        case 3:
                            match.getSet(setNumber-1).setHomeTeamPlayer(HP2);
                            match.getSet(setNumber-1).setAwayTeamPlayer(AP2);
                            gameIndex = 0;
                            for (TextField gameScore : scoreEntries) {

                                // check if this is for home or away
                                if (vb.getId().charAt(0) == 'H') {

                                    match.getSet(setNumber-1).getGame(gameIndex).setHomeTeamScore(Integer.parseInt(gameScore.getText()));
                                    gameIndex++;
                                }
                                else if (vb.getId().charAt(0) == 'A') {

                                    match.getSet(setNumber-1).getGame(gameIndex).setAwayTeamScore(Integer.parseInt(gameScore.getText()));
                                    gameIndex++;
                                }
                            }
                            break;
                        case 4:
                            match.getSet(setNumber-1).setHomeTeamPlayer(HP2);
                            match.getSet(setNumber-1).setAwayTeamPlayer(AP2);
                            gameIndex = 0;
                            for (TextField gameScore : scoreEntries) {

                                // check if this is for home or away
                                if (vb.getId().charAt(0) == 'H') {

                                    match.getSet(setNumber-1).getGame(gameIndex).setHomeTeamScore(Integer.parseInt(gameScore.getText()));
                                    gameIndex++;
                                }
                                else if (vb.getId().charAt(0) == 'A') {

                                    match.getSet(setNumber-1).getGame(gameIndex).setAwayTeamScore(Integer.parseInt(gameScore.getText()));
                                    gameIndex++;
                                }
                            }
                            break;

                        default:
                            // nothing due to double set
                    }
                }
            }
        }

        int homeTeamSetsWon = 0;
        int awayTeamSetsWon = 0;

        for (int i = 0; i < 5; i++) {

            int homeTeamGamesWon = 0;
            int awayTeamGamesWon = 0;
            for (int j = 0; j < 3; j++) {

                if (match.getSet(i).getGame(j).getHomeTeamScore() > match.getSet(i).getGame(j).getAwayTeamScore()) {

                    match.getSet(i).getGame(j).setWinningTeam(match.getHomeTeam());
                    homeTeamGamesWon++;
                }
                else {

                    match.getSet(i).getGame(j).setWinningTeam(match.getAwayTeam());
                    awayTeamGamesWon++;
                }
            }

            if (homeTeamGamesWon > awayTeamGamesWon) {
                match.getSet(i).setWinningTeam(match.getHomeTeam());
                homeTeamSetsWon++;
            }
            else {
                match.getSet(i).setWinningTeam(match.getAwayTeam());
                awayTeamSetsWon++;
            }
        }
        if (homeTeamSetsWon > awayTeamSetsWon) {

            match.setWinningTeam(match.getHomeTeam());
        }
        else {

            match.setWinningTeam(match.getAwayTeam());
        }

        DatabaseManager.DB_Match.updateMatchInformation(match);
    }

    // Viewer Page Methods



}
