// TODO: add error display for pre-existing team and player names
// TODO: add error for when less than 2 teams exist and fixtures are generated
// TODO: generate team stats method / window
// TODO: make the textfields in the score sheet integer ones

package controller;

import DB.DatabaseManager;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import model.*;
import view.AlphaNumericTextFormatter;
import view.UserListViewCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
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
    @FXML private TextField tf_EnterNewTeamName;
    @FXML private Button btn_RegTeam;

	@FXML private TextField tf_EnterNewPlayerName;
	@FXML private ComboBox<Team> cb_SelectPlayerTeam;

    // Viewer Page elements

    @FXML private Pane p_MatchViewer;
    @FXML private Pane p_ShowTeamStats;
    @FXML private Pane p_Fixtures;
    @FXML private TilePane tp_Fixtures;

    // Score Sheet Page elements
    @FXML private Button btn_NewSheet;
    @FXML private Button btn_ModifySheet;
    @FXML private ComboBox<Team> cb_SelectHomeTeam;
    @FXML private ComboBox<Team> cb_SelectAwayTeam;
    @FXML private ComboBox<Player> cb_SelectHomePlayer1;
    @FXML private ComboBox<Player> cb_SelectHomePlayer2;
    @FXML private ComboBox<Player> cb_SelectAwayPlayer1;
    @FXML private ComboBox<Player> cb_SelectAwayPlayer2;
    @FXML private Button btn_CalcSubScores;

    @FXML private VBox vb_Fixtures;
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

	    // ensure users without privileges cannot use admin functions
		hideAdminPageIfNonAdmin();

		// no listener for users as they cant be made outside of the login screen
		lv_Users.setItems(ol_Users);
		lv_Users.setCellFactory(userListView -> new UserListViewCell());
		cb_SelectPlayerTeam.setItems(ol_Teams);

		// listens for changes to teamlist
		setTeamListListener();

		// initialize observerlists with arrays
        cb_SelectHomeTeam.setItems(ol_Teams);
        cb_SelectAwayTeam.setItems(ol_Teams);

        // listeners for changes to TeamList to update combo boxes if new teams are added
        setHomeTeamListListener();
        setAwayTeamListListener();

        setScoreSheetFieldsToIntegerOnly();
        listenForScoreSheetChanges();
        disableSubmitButtonIfEmptyFields();
	}

	private void hideAdminPageIfNonAdmin() {

        if (User.currentUser.getPrivilegeLevel() != 2) { // hide admin tab from non admin accounts
            closeTab(t_Admin);
        }
    }

    private void hideFixturesTableIfNoTeams() {

	    if (ol_Teams.size() < 2) {

	        vb_Fixtures.setVisible(false);
        }
    }

	private void setTeamListListener() {

        ol_Teams.addListener(new ListChangeListener<Team>() {
            @Override
            public void onChanged(Change<? extends Team> c) {
                cb_SelectPlayerTeam.setItems(ol_Teams);
            }
        });
    }

    private void setHomeTeamListListener() {

        cb_SelectHomeTeam.valueProperty().addListener(new ChangeListener<Team>() {
            @Override
            public void changed(ObservableValue<? extends Team> observable, Team oldValue, Team newValue) {

                ArrayList<Player> players = cb_SelectHomeTeam.getValue().getPlayerList();
                ol_HomePlayers.setAll(players);
                cb_SelectHomePlayer1.setItems(ol_HomePlayers);
                cb_SelectHomePlayer2.setItems(ol_HomePlayers);
            }
        });
    }

    private void setAwayTeamListListener() {

        // listener for changes to TeamList to update combo boxes if new teams are added
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

	private void setScoreSheetFieldsToIntegerOnly() {

        // loop to set all textfields to be single integer only
        for (int i = 0; i < gp_MatchForm.getChildren().size(); i++) {

            if (gp_MatchForm.getChildren().get(i).getClass() == VBox.class) {

                // have to get children in order to see their children (not sure why)
                VBox vb = (VBox) gp_MatchForm.getChildren().get(i);

                for (int j = 0; j < vb.getChildren().size(); j++) {

                    TextField textField = (TextField) vb.getChildren().get(j);
                    textField.setTextFormatter(new AlphaNumericTextFormatter(2));
                }
            }
        }
    }

    private void disableSubmitButtonIfEmptyFields() {

	    boolean fieldEmpty = false;

        for (int i = 0; i < gp_MatchForm.getChildren().size(); i++) {

            if (cb_SelectHomePlayer1.getSelectionModel().isEmpty()) {

                fieldEmpty = true;
            }

            if (cb_SelectHomePlayer2.getSelectionModel().isEmpty()) {

                fieldEmpty = true;
            }

            if (cb_SelectAwayPlayer1.getSelectionModel().isEmpty()) {

                fieldEmpty = true;
            }

            if (cb_SelectAwayPlayer2.getSelectionModel().isEmpty()) {

                fieldEmpty = true;
            }

            if (gp_MatchForm.getChildren().get(i).getClass() == VBox.class) {

                // have to get children in order to see their children (not sure why)
                VBox vb = (VBox) gp_MatchForm.getChildren().get(i);

                for (int j = 0; j < vb.getChildren().size(); j++) {

                    TextField textField = (TextField) vb.getChildren().get(j);
                    if (textField.getText().isEmpty()) {

                        fieldEmpty = true;
                    }
                }

                btn_CalcSubScores.setDisable(fieldEmpty);
            }
        }
    }

    private void listenForScoreSheetChanges() {

	    cb_SelectHomePlayer1.valueProperty().addListener(new ChangeListener<Player>() {
            @Override
            public void changed(ObservableValue<? extends Player> observable, Player oldValue, Player newValue) {
                disableSubmitButtonIfEmptyFields();
            }
        });

        cb_SelectHomePlayer2.valueProperty().addListener(new ChangeListener<Player>() {
            @Override
            public void changed(ObservableValue<? extends Player> observable, Player oldValue, Player newValue) {
                disableSubmitButtonIfEmptyFields();
            }
        });

        cb_SelectAwayPlayer1.valueProperty().addListener(new ChangeListener<Player>() {
            @Override
            public void changed(ObservableValue<? extends Player> observable, Player oldValue, Player newValue) {
                disableSubmitButtonIfEmptyFields();
            }
        });

        cb_SelectAwayPlayer2.valueProperty().addListener(new ChangeListener<Player>() {
            @Override
            public void changed(ObservableValue<? extends Player> observable, Player oldValue, Player newValue) {
                disableSubmitButtonIfEmptyFields();
            }
        });

        for (int i = 0; i < gp_MatchForm.getChildren().size(); i++) {

            if (gp_MatchForm.getChildren().get(i).getClass() == VBox.class) {

                // have to get children in order to see their children (not sure why)
                VBox vb = (VBox) gp_MatchForm.getChildren().get(i);

                for (int j = 0; j < vb.getChildren().size(); j++) {

                    TextField textField = (TextField) vb.getChildren().get(j);
                    textField.textProperty().addListener(new ChangeListener<String>() {

                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                            disableSubmitButtonIfEmptyFields();
                        }
                    });
                }
            }
        }
    }

	// used to close admin tab if needed
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

		Player player = new Player(tf_EnterNewPlayerName.getText());
		Team team = cb_SelectPlayerTeam.getValue();
		team.addPlayer(player);

		tf_EnterNewTeamName.clear();
		tf_EnterNewPlayerName.clear();

		DatabaseManager.DB_Player.addNewPlayerToDatabase(player, team);
	}

    public void registerNewTeam(ActionEvent actionEvent) {

	    Team team = new Team(tf_EnterNewTeamName.getText());
	    DatabaseManager.DB_Team.addNewTeamToDatabase(team);
	    ol_Teams.add(team); // to update combobox without having to retrieve from SQL again
        tf_EnterNewTeamName.clear();
    }

    public void generateFixtures(ActionEvent actionEvent) {

        Fixtures fixtures = Fixtures.generateFixtures();

        if (fixtures != null) {

            DatabaseManager.DB_Match.truncMatch();
            DatabaseManager.DB_Fixtures.addFixturesToDatabase(fixtures);
            generateFixtureGrid();
        }
        else {

            // output error message requiring at least 2 teams to be present
        }
    }

    // Viewer Page Methods

    public void viewFixtures(ActionEvent actionEvent) {

	    p_Fixtures.setVisible(true);
        p_ShowTeamStats.setVisible(false);
        p_MatchViewer.setVisible(false);
    }

    public void generateFixtureGrid() {

        vb_Fixtures.getChildren().clear();
        GridPane gp_Fixtures = new GridPane();

        VBox.setVgrow(gp_Fixtures, Priority.NEVER);
        gp_Fixtures.setAlignment(Pos.CENTER);

        setTeamColumns(gp_Fixtures);
        setTeamRows(gp_Fixtures);

        gp_Fixtures.setVisible(true);
        gp_Fixtures.setGridLinesVisible(true);

        populateLabels(gp_Fixtures);

        vb_Fixtures.getChildren().add(gp_Fixtures);

        vb_Fixtures.setVisible(true);
    }

    private void setTeamColumns(GridPane gp_Fixtures) {

        for (int column = 0; column < ol_Teams.size()+1; column++) {

            gp_Fixtures.addColumn(column);
            ColumnConstraints colCon = new ColumnConstraints();
            colCon.setPrefWidth(50);
            gp_Fixtures.getColumnConstraints().add(colCon);
        }
    }

    private void setTeamRows(GridPane gp_Fixtures) {

        for (int row = 0; row < ol_Teams.size()+1; row++) {

            gp_Fixtures.addRow(row);
            RowConstraints rowCon = new RowConstraints();
            rowCon.setPrefHeight(50);
            gp_Fixtures.getRowConstraints().add(rowCon);
        }
    }

    private void populateLabels(GridPane gp) {

        for (int column = 0; column < ol_Teams.size(); column++) { // start at 1 to miss the first column, which should be empty

            Label label = new Label(ol_Teams.get(column).getTeamName());
            gp.add(label, column+1, 0);
        }

        for (int row = 0; row < ol_Teams.size(); row++) { // start at 1 to miss the first column, which should be empty

            Label label = new Label(ol_Teams.get(row).getTeamName());
            gp.add(label, 0, row+1);
        }
    }

    private void populateContent(GridPane gp) {

	    for (int row = 1; row < ol_Teams.size(); row++) {

            for (int column = 1; column < ol_Teams.size(); column++) {


            }
        }
    }

    public void showTeamStats(ActionEvent actionEvent) {

        p_Fixtures.setVisible(false);
        p_ShowTeamStats.setVisible(true);
        p_MatchViewer.setVisible(false);
    }

    public void openMatchViewer(ActionEvent actionEvent) {

        p_Fixtures.setVisible(false);
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

        // may as well fill in double set players, even if unnecessary
        match.getSet(4).setHomeTeamPlayer(HP1);
        match.getSet(4).setHomeTeamPlayer(HP2);
        match.getSet(4).setHomeTeamPlayer(AP1);
        match.getSet(4).setHomeTeamPlayer(AP2);

        for (int i = 0; i < gp_MatchForm.getChildren().size(); i++) {

            if (gp_MatchForm.getChildren().get(i).getClass() == VBox.class) {

                // in order to get the children we need to cast the VBox to a new reference for some reason
                VBox vb = (VBox) gp_MatchForm.getChildren().get(i);
                String ID = vb.getId();

                TextField[] scoreEntries = new TextField[3];
                scoreEntries[0] = (TextField) vb.getChildren().get(0);
                scoreEntries[1] = (TextField) vb.getChildren().get(1);
                scoreEntries[2] = (TextField) vb.getChildren().get(2);

                // if this set is a double
                if (vb.getId().equals("HDS") || vb.getId().equals("ADS")) {

                    setScoresWithTextFields(match, HP1, AP1, 4, scoreEntries, vb.getId());
                }
                else { // if its a single

                    int setNumber = vb.getId().charAt(2) - '0'; // converts char into int due to the way ASCII works
                    int gameIndex = 0;

                    // handle the information of whatever set we're currently looking at
                    switch (setNumber) {

                        case 1:
                            setScoresWithTextFields(match, HP1, AP1, setNumber, scoreEntries, vb.getId());
                            break;
                        case 2:
                            setScoresWithTextFields(match, HP1, AP2, setNumber, scoreEntries, vb.getId());
                            break;
                        case 3:
                            setScoresWithTextFields(match, HP2, AP1, setNumber, scoreEntries, vb.getId());
                            break;
                        case 4:
                            setScoresWithTextFields(match, HP2, AP2, setNumber, scoreEntries, vb.getId());
                            break;
                    }
                }
            }
        }

        match.fillInWinnerFields();
        DatabaseManager.DB_Match.updateMatchInformation(match);
    }

    private void setScoresWithTextFields(Match match, Player HP, Player AP, int setNumber, TextField[] scoreEntries, String ID) {

        match.getSet(setNumber - 1).setHomeTeamPlayer(HP);
        match.getSet(setNumber - 1).setAwayTeamPlayer(AP);
        int gameIndex = 0;
        for (TextField gameScore : scoreEntries) {

            // check if this is for home or away
            if (ID.charAt(0) == 'H') {

                match.getSet(setNumber - 1).getGame(gameIndex).setHomeTeamScore(Integer.parseInt(gameScore.getText()));
                gameIndex++;
            } else if (ID.charAt(0) == 'A') {

                match.getSet(setNumber - 1).getGame(gameIndex).setAwayTeamScore(Integer.parseInt(gameScore.getText()));
                gameIndex++;
            }
        }
    }

}
