// TODO: add error display for pre-existing team and player names
// TODO: add error for when less than 2 teams exist and fixtures are generated
// TODO: make submit new team button clear textfield
// TODO: generate team stats method / window

package controller;

import DB.DatabaseManager;
import javafx.collections.ListChangeListener;
import javafx.scene.layout.Pane;
import model.Fixtures;
import model.Player;
import model.Team;
import model.User;
import view.UserListViewCell;
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
    private ObservableList<User> userObservableList;
    private ObservableList<Team> teamObservableList;

    // TabPane elements
	@FXML private TabPane tabPane;
	@FXML private Tab tabAdmin;
	@FXML private Tab tabViewer;
	@FXML private Tab tabScoreSheets;

	// Admin Page elements

	@FXML private ListView userListView;
    @FXML private TextField enterTeamNameForRegistration;
    @FXML private Button btn_RegTeam;

	@FXML private TextField playerNameForRegistration;
	@FXML private ComboBox<Team> cb_SelectTeam;

    // Viewer Page elements

    @FXML private Pane matchViewer;
    @FXML private Pane showTeamStats;


	// Page Scope methods

	public HomePageController() { // called before initialize, then FXML are initialized, then initialize is called

		userObservableList = FXCollections.observableArrayList();
		teamObservableList = FXCollections.observableArrayList();

		ArrayList<User> userList = DatabaseManager.DB_User.getUserListFromDatabase();
		ArrayList<Team> teamList = DatabaseManager.DB_Team.getTeamListFromDatabase();

		userObservableList.addAll(userList);
		teamObservableList.addAll(teamList);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) { //

		if (User.currentUser.getPrivilegeLevel() != 2) { // hide admin tab from non admin accounts
			closeTab(tabAdmin);
		}

		userListView.setItems(userObservableList);
		userListView.setCellFactory(userListView -> new UserListViewCell());
		cb_SelectTeam.setItems(teamObservableList);

		teamObservableList.addListener(new ListChangeListener<Team>() {
            @Override
            public void onChanged(Change<? extends Team> c) {
                cb_SelectTeam.setItems(teamObservableList);
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

		Player player = new Player(playerNameForRegistration.getText());
		Team team = cb_SelectTeam.getValue();

		DatabaseManager.DB_Player.addNewPlayerToDatabase(player, team);
	}

    public void registerNewTeam(ActionEvent actionEvent) {

	    Team team = new Team(enterTeamNameForRegistration.getText());
	    DatabaseManager.DB_Team.addNewTeamToDatabase(team);
	    teamObservableList.add(team); // to update combobox without having to retrieve from SQL again
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

    public void viewFixtures(ActionEvent actionEvent) {

    }

    public void showTeamStats(ActionEvent actionEvent) {

	    showTeamStats.setVisible(true);
	    matchViewer.setVisible(false);
    }

    public void openMatchViewer(ActionEvent actionEvent) {

        showTeamStats.setVisible(false);
        matchViewer.setVisible(true);
    }

    // Viewer Page Methods



}
