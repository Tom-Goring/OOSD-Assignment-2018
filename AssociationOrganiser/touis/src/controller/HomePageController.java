package controller;

import DB.DatabaseManager;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import model.*;
import view.AlphaNumericTextFormatter;
import model.TeamStats;
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
import java.util.Collections;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    // Observable lists
    private ObservableList<User> ol_Users;
    private ObservableList<Team> ol_Teams;
    private ObservableList<Player> ol_HomePlayers;
    private ObservableList<Player> ol_AwayPlayers;
    private ObservableList<Match.Set> ol_MatchSets;
    private ObservableList<TeamStats> ol_TeamStats;

    // TabPane elements
	@FXML private TabPane tp_tabPane;
	@FXML private Tab t_Admin;
	@FXML private Tab t_Viewer;
	@FXML private Tab t_ScoreSheets;

	// Admin Page elements

	@FXML private ListView lv_Users;

    @FXML private TextField tf_EnterNewTeamName;
    @FXML private Button btn_RegTeam;
    @FXML private Label lbl_TeamCreated;

	@FXML private TextField tf_EnterNewPlayerName;
	@FXML private ComboBox<Team> cb_SelectPlayerTeam;
    @FXML private Label lbl_PlayerAdded;

	@FXML private Label lbl_FixturesSuccess;
    @FXML private Label lbl_FixturesFailure;

    // Viewer Page elements

    @FXML private Pane p_MatchViewer;
    @FXML private ComboBox<Team> cb_mvSelectHomeTeam;
    @FXML private ComboBox<Team> cb_mvSelectAwayTeam;
    @FXML private Label lbl_HomeSetsWon;
    @FXML private Label lbl_AwaySetsWon;

    @FXML private TableView<Match.Set> tv_MatchStats;
    @FXML private TableColumn<Match.Set, String> tvc_SetNumber;
    @FXML private TableColumn<Match.Set, String> tvc_HomePlayer;
    @FXML private TableColumn<Match.Set, String> tvc_AwayPlayer;
    @FXML private TableColumn<Match.Set.Game, String> tvc_G1HomeScore;
    @FXML private TableColumn<Match.Set.Game, String> tvc_G2HomeScore;
    @FXML private TableColumn<Match.Set.Game, String> tvc_G3HomeScore;
    @FXML private TableColumn<Match.Set.Game, String> tvc_G1AwayScore;
    @FXML private TableColumn<Match.Set.Game, String> tvc_G2AwayScore;
    @FXML private TableColumn<Match.Set.Game, String> tvc_G3AwayScore;
    @FXML private TableColumn<Match.Set, String> tvc_WinnerName;

    @FXML private Pane p_ShowTeamStats;
    @FXML private TableView<TeamStats> tv_TeamStats;
    @FXML private TableColumn<TeamStats, String> tvc_TeamName;
    @FXML private TableColumn<TeamStats, String> tvc_MatchesPlayed;
    @FXML private TableColumn<TeamStats, String> tvc_MatchesWon;
    @FXML private TableColumn<TeamStats, String> tvc_SetsWon;
    @FXML private TableColumn<TeamStats, String> tvc_GamesWon;

    @FXML private Pane p_Fixtures;
    @FXML private VBox vb_Fixtures;

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
    @FXML private GridPane gp_MatchForm;


	// Page Scope methods

	public HomePageController() { // called before initialize, then FXML are initialized, then initialize is called

		ol_Users = FXCollections.observableArrayList();
		ol_Teams = FXCollections.observableArrayList();
		ol_HomePlayers = FXCollections.observableArrayList();
		ol_AwayPlayers = FXCollections.observableArrayList();
		ol_MatchSets = FXCollections.observableArrayList();
		ol_TeamStats = FXCollections.observableArrayList();

		ArrayList<User> userList = DatabaseManager.DB_User.getUserListFromDatabase();
		ArrayList<Team> teamList = DatabaseManager.DB_Team.getTeamListFromDatabase();
        Collections.reverse(teamList);

		ol_Users.addAll(userList);
		ol_Teams.addAll(teamList);

        for (Team team : ol_Teams) {

            ol_TeamStats.add(DatabaseManager.DB_Team.getTeamStats(team));
        }
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	    StatsThread statsThread = new StatsThread();
	    statsThread.start();

	    // ensure users without privileges cannot use admin functions
		hideAdminPageIfNonAdmin();

		// no listener for users as they cant be made outside of the login screen
		lv_Users.setItems(ol_Users);
		lv_Users.setCellFactory(userListView -> new UserListViewCell());
		cb_SelectPlayerTeam.setItems(ol_Teams);

		// listens for changes to teamlist
		setTeamListListener();

		// initialize combo box lists
        cb_SelectHomeTeam.setItems(ol_Teams);
        cb_SelectAwayTeam.setItems(ol_Teams);
        cb_mvSelectHomeTeam.setItems(ol_Teams);
        cb_mvSelectAwayTeam.setItems(ol_Teams);

        // listeners for changes to TeamList to update combo boxes if new teams are added
        setHomeTeamListListener();
        setAwayTeamListListener();

        setScoreSheetFieldsToIntegerOnly();
        listenForScoreSheetChanges();
        disableSubmitButtonIfEmptyFields();
        disableModifyIfBothTeamsNotSelected();

        initializeTeamStats();

        generateFixtureGrid();
        hideFixturesTableIfNoTeams();
	}

	private void hideAdminPageIfNonAdmin() {

        if (User.currentUser.getClass() != Admin.class) { // hide admin tab from non admin accounts
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

                if (cb_SelectHomeTeam.getValue() != null) {

                    ArrayList<Player> players = cb_SelectHomeTeam.getValue().getPlayerList();
                    ol_HomePlayers.setAll(players);
                    cb_SelectHomePlayer1.setItems(ol_HomePlayers);
                    cb_SelectHomePlayer2.setItems(ol_HomePlayers);
                    disableModifyIfBothTeamsNotSelected();
                }
            }
        });

        cb_mvSelectHomeTeam.valueProperty().addListener(new ChangeListener<Team>() {
            @Override
            public void changed(ObservableValue<? extends Team> observable, Team oldValue, Team newValue) {

                initializeMatchViewer();
            }
        });
    }

    private void setAwayTeamListListener() {

        // listener for changes to TeamList to update combo boxes if new teams are added
        cb_SelectAwayTeam.valueProperty().addListener(new ChangeListener<Team>() {
            @Override
            public void changed(ObservableValue<? extends Team> observable, Team oldValue, Team newValue) {

                if (cb_SelectAwayTeam.getValue() != null) {

                    ArrayList<Player> players = cb_SelectAwayTeam.getValue().getPlayerList();
                    ol_AwayPlayers.setAll(players);
                    cb_SelectAwayPlayer1.setItems(ol_AwayPlayers);
                    cb_SelectAwayPlayer2.setItems(ol_AwayPlayers);
                    disableModifyIfBothTeamsNotSelected();
                }
            }
        });

        cb_mvSelectAwayTeam.valueProperty().addListener(new ChangeListener<Team>() {
            @Override
            public void changed(ObservableValue<? extends Team> observable, Team oldValue, Team newValue) {

                initializeMatchViewer();
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

    private void disableModifyIfBothTeamsNotSelected() {

	    Team homeTeam = cb_SelectHomeTeam.getValue();
	    Team awayTeam = cb_SelectAwayTeam.getValue();

	    btn_ModifySheet.setDisable((homeTeam == null || awayTeam == null));

        cb_SelectHomeTeam.setValue(homeTeam);
        cb_SelectAwayTeam.setValue(awayTeam);
    }

    private void initializeMatchViewer() {

	    ol_MatchSets.clear();

	    Team homeTeam = cb_mvSelectHomeTeam.getValue();
	    Team awayTeam = cb_mvSelectAwayTeam.getValue();

	    if (!homeTeam.equals(awayTeam))
	    if (homeTeam != null && awayTeam != null) {

            lbl_HomeSetsWon.setText(Integer.toString(DatabaseManager.DB_Match.getMatchFromDatabase(homeTeam.getTeamName(), awayTeam.getTeamName()).getHomeTeamSetsWon()));
            lbl_AwaySetsWon.setText(Integer.toString(DatabaseManager.DB_Match.getMatchFromDatabase(homeTeam.getTeamName(), awayTeam.getTeamName()).getAwayTeamSetsWon()));

            tvc_SetNumber.setCellValueFactory(new PropertyValueFactory<Match.Set, String>("setNumber"));
            tvc_HomePlayer.setCellValueFactory(new PropertyValueFactory<Match.Set, String>("HomePlayerString"));
            tvc_AwayPlayer.setCellValueFactory(new PropertyValueFactory<Match.Set, String>("AwayPlayerString"));
            tvc_G1HomeScore.setCellValueFactory(new PropertyValueFactory<Match.Set.Game, String>("G1HomeScore"));
            tvc_G2HomeScore.setCellValueFactory(new PropertyValueFactory<Match.Set.Game, String>("G2HomeScore"));
            tvc_G3HomeScore.setCellValueFactory(new PropertyValueFactory<Match.Set.Game, String>("G3HomeScore"));
            tvc_G1AwayScore.setCellValueFactory(new PropertyValueFactory<Match.Set.Game, String>("G1AwayScore"));
            tvc_G2AwayScore.setCellValueFactory(new PropertyValueFactory<Match.Set.Game, String>("G2AwayScore"));
            tvc_G3AwayScore.setCellValueFactory(new PropertyValueFactory<Match.Set.Game, String>("G3AwayScore"));
            tvc_WinnerName.setCellValueFactory(new PropertyValueFactory<Match.Set, String>("Winner"));

            Match match = DatabaseManager.DB_Match.getMatchFromDatabase(homeTeam.getTeamName(), awayTeam.getTeamName());

            for (int i = 0; i < 5; i++) {

                ol_MatchSets.add(match.getSet(i));
            }

            tv_MatchStats.getItems().setAll(ol_MatchSets);
        }
        else {

            cb_mvSelectHomeTeam.setValue(homeTeam);
            cb_mvSelectAwayTeam.setValue(awayTeam);
        }
    }

    private void initializeTeamStats() {

	    ol_TeamStats.clear();
        for (Team team : ol_Teams) {

            ol_TeamStats.add(DatabaseManager.DB_Team.getTeamStats(team));
        }

	    tvc_TeamName.setCellValueFactory(new PropertyValueFactory<TeamStats, String>("TeamName"));
        tvc_MatchesPlayed.setCellValueFactory(new PropertyValueFactory<TeamStats, String>("MatchesPlayed"));
        tvc_MatchesWon.setCellValueFactory(new PropertyValueFactory<TeamStats, String>("MatchesWon"));
        tvc_SetsWon.setCellValueFactory(new PropertyValueFactory<TeamStats, String>("SetsWon"));
        tvc_GamesWon.setCellValueFactory(new PropertyValueFactory<TeamStats, String>("GamesWon"));

        tv_TeamStats.getItems().setAll(ol_TeamStats);
    }

    private void disableSubmitButtonIfEmptyFields() {

	    boolean fieldEmpty = false;

        if (cb_SelectHomePlayer1.getValue() == null) {

            fieldEmpty = true;
        }
        else if (cb_SelectHomePlayer2.getValue() == null) {

            fieldEmpty = true;
        }
        else if (cb_SelectAwayPlayer1.getValue() == null) {

            fieldEmpty = true;
        }
        else if (cb_SelectAwayPlayer2.getValue() == null) {

            fieldEmpty = true;
        }

        for (int i = 0; i < gp_MatchForm.getChildren().size(); i++) {

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

		DatabaseManager.DB_Player.addPlayerToDatabase(player, team);

        lbl_PlayerAdded.setVisible(true);
        WaitAndHideLabel hidePlayerAdded = new WaitAndHideLabel(5000, lbl_PlayerAdded);
        hidePlayerAdded.start();
	}

    public void registerNewTeam(ActionEvent actionEvent) {

	    Team team = new Team(tf_EnterNewTeamName.getText());
	    DatabaseManager.DB_Team.addNewTeamToDatabase(team);
	    ol_Teams.add(team); // to update combobox without having to retrieve from SQL again
        tf_EnterNewTeamName.clear();

        lbl_TeamCreated.setVisible(true);
        WaitAndHideLabel hideTeamCreated = new WaitAndHideLabel(5000, lbl_TeamCreated);
        hideTeamCreated.start();
    }

    public void generateFixtures(ActionEvent actionEvent) {

        Fixtures fixtures = Fixtures.generateFixtures();

        if (ol_Teams.size() > 3) {

            DatabaseManager.DB_Match.truncMatch();
            DatabaseManager.DB_Fixtures.addFixturesToDatabase(fixtures);
            generateFixtureGrid();
            WaitAndHideLabel hideSuccess = new WaitAndHideLabel(5000, lbl_FixturesSuccess);
            hideSuccess.start();
        }
        else {

            lbl_FixturesFailure.setVisible(true);
            WaitAndHideLabel hideFailure = new WaitAndHideLabel(5000, lbl_FixturesFailure);
            hideFailure.start();
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
        populateContent(gp_Fixtures);

        vb_Fixtures.getChildren().add(gp_Fixtures);

        vb_Fixtures.setVisible(true);
    }

    private void setTeamColumns(GridPane gp_Fixtures) {

        for (int column = 0; column < ol_Teams.size()+1; column++) {

            gp_Fixtures.addColumn(column);
            ColumnConstraints colCon = new ColumnConstraints();
            colCon.setPrefWidth(50);
            colCon.setHalignment(HPos.CENTER);
            gp_Fixtures.getColumnConstraints().add(colCon);
        }
    }

    private void setTeamRows(GridPane gp_Fixtures) {

        for (int row = 0; row < ol_Teams.size()+1; row++) {

            gp_Fixtures.addRow(row);
            RowConstraints rowCon = new RowConstraints();
            rowCon.setPrefHeight(50);
            rowCon.setValignment(VPos.CENTER);
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

	    for (int row = 0; row < ol_Teams.size(); row++) {

            for (int column = 0; column < ol_Teams.size(); column++) {

                if (!ol_Teams.get(row).equals(ol_Teams.get(column))) {

                    String matchScore = "";
                    Match match = DatabaseManager.DB_Match.getMatchFromDatabase(ol_Teams.get(row).getTeamName(), ol_Teams.get(column).getTeamName());

                    if (match.getHomeTeamSetsWon() == 0 && match.getAwayTeamSetsWon() == 0) {

                        gp.add(new Label("np"), column+1, row+1);
                    }
                    else {

                        matchScore += match.getHomeTeamSetsWon() + ":" + match.getAwayTeamSetsWon();
                        gp.add(new Label(matchScore), column+1, row+1);
                    }
                }
                else {

                    gp.add(new Label("np"), column+1, row+1);
                }
            }
        }
    }

    public void showTeamStats(ActionEvent actionEvent) {

        initializeTeamStats();
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

	    cb_SelectHomeTeam.getSelectionModel().clearSelection();
        cb_SelectAwayTeam.getSelectionModel().clearSelection();

        cb_SelectHomePlayer1.getSelectionModel().clearSelection();
        cb_SelectHomePlayer2.getSelectionModel().clearSelection();
        cb_SelectAwayPlayer1.getSelectionModel().clearSelection();
        cb_SelectAwayPlayer2.getSelectionModel().clearSelection();

        for (int i = 0; i < gp_MatchForm.getChildren().size(); i++) {

            if (gp_MatchForm.getChildren().get(i).getClass() == VBox.class) {

                // in order to get the children we need to cast the VBox to a new reference for some reason
                VBox vb = (VBox) gp_MatchForm.getChildren().get(i);

                TextField[] scoreEntries = new TextField[3];
                scoreEntries[0] = (TextField) vb.getChildren().get(0);
                scoreEntries[1] = (TextField) vb.getChildren().get(1);
                scoreEntries[2] = (TextField) vb.getChildren().get(2);

                scoreEntries[0].clear();
                scoreEntries[1].clear();
                scoreEntries[2].clear();
            }
        }
    }

    public void modifyExistingSheet(ActionEvent actionEvent) {

        Match match = DatabaseManager.DB_Match.getMatchFromDatabase(cb_SelectHomeTeam.getValue().getTeamName(), cb_SelectAwayTeam.getValue().getTeamName());

        cb_SelectHomePlayer1.setValue(match.getHomeTeamPlayer1());
        cb_SelectHomePlayer2.setValue(match.getHomeTeamPlayer2());
        cb_SelectAwayPlayer1.setValue(match.getAwayTeamPlayer1());
        cb_SelectAwayPlayer2.setValue(match.getAwayTeamPlayer2());

        for (int i = 0; i < gp_MatchForm.getChildren().size(); i++) {

            if (gp_MatchForm.getChildren().get(i).getClass() == VBox.class) {

                // in order to get the children we need to cast the VBox to a new reference for some reason
                VBox vb = (VBox) gp_MatchForm.getChildren().get(i);
                String ID = vb.getId();
                int setNumber = vb.getId().charAt(2) - '0'; // converts char into int due to the way ASCII works

                if (ID .equals("HDS") || ID.equals("ADS")) {
                    setNumber = 5;
                }

                TextField[] scoreEntries = new TextField[3];
                scoreEntries[0] = (TextField) vb.getChildren().get(0);
                scoreEntries[1] = (TextField) vb.getChildren().get(1);
                scoreEntries[2] = (TextField) vb.getChildren().get(2);

                if (ID.charAt(0) == 'H') {

                    scoreEntries[0].setText(Integer.toString(match.getSet(setNumber-1).getGame(0).getHomeTeamScore()));
                    scoreEntries[1].setText(Integer.toString(match.getSet(setNumber-1).getGame(1).getHomeTeamScore()));
                    scoreEntries[2].setText(Integer.toString(match.getSet(setNumber-1).getGame(2).getHomeTeamScore()));
                }
                else {

                    scoreEntries[0].setText(Integer.toString(match.getSet(setNumber-1).getGame(0).getAwayTeamScore()));
                    scoreEntries[1].setText(Integer.toString(match.getSet(setNumber-1).getGame(1).getAwayTeamScore()));
                    scoreEntries[2].setText(Integer.toString(match.getSet(setNumber-1).getGame(2).getAwayTeamScore()));
                }
            }
        }

        disableSubmitButtonIfEmptyFields();

        cb_SelectHomePlayer1.setValue(match.getHomeTeamPlayer1());
        cb_SelectHomePlayer2.setValue(match.getHomeTeamPlayer2());
        cb_SelectAwayPlayer1.setValue(match.getAwayTeamPlayer1());
        cb_SelectAwayPlayer2.setValue(match.getAwayTeamPlayer2());
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

                    setScoresWithTextFields(match, null, null, 5, scoreEntries, vb.getId());
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
        generateFixtureGrid();
        initializeTeamStats();
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

    public void callInitializeTeamStats(ActionEvent actionEvent) {

        initializeTeamStats();
    }

    private class StatsThread extends Thread {

	    public void run(){

	        while (true) {

                try {

                    Thread.sleep(100000);
                }
                catch (InterruptedException e) {e.printStackTrace(); }

                initializeTeamStats();
            }
        }
    }

    private class WaitAndHideLabel extends Thread {

	    private int duration;
	    private Label labelToDisable;

	    public WaitAndHideLabel(int duration, Label labelToDisable) {

            this.duration = duration;
            this.labelToDisable = labelToDisable;
        }

        public void run() {

            while (true) {

                try {

                    Thread.sleep(duration);
                }
                catch (InterruptedException e) {e.printStackTrace(); }

                labelToDisable.setVisible(false);
            }
        }
    }
}
