// TODO: add error display for pre-existing team and player names

package controller;

import DB.DatabaseManager;
import javafx.collections.ListChangeListener;
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

	@FXML private TabPane tabPane;
	@FXML private Tab tabAdmin;
	@FXML private Tab tabViewer;
	@FXML private Tab tabScoreSheets;

	// Observable lists
	private ObservableList<User> userObservableList;
	private ObservableList<Team> teamObservableList;

	// Create Admin Titled Pane
	@FXML ListView userListView;

	// Add Teams TitledPane
    @FXML TextField teamNameField;
    @FXML Button btnRegTeam;

	// Add Players TitledPane
	@FXML private TextField playerNameEntry;
	@FXML private ComboBox<Team> cbSelectTeam;
	@FXML private Button btnRegisterPlayer;

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
		cbSelectTeam.setItems(teamObservableList);

		teamObservableList.addListener(new ListChangeListener<Team>() {
            @Override
            public void onChanged(Change<? extends Team> c) {
                cbSelectTeam.setItems(teamObservableList);
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

		Player player = new Player(playerNameEntry.getText());
		Team team = cbSelectTeam.getValue();

		DatabaseManager.DB_Player.addNewPlayerToDatabase(player, team);
	}

    public void registerNewTeam(ActionEvent actionEvent) {

	    Team team = new Team(teamNameField.getText());
	    DatabaseManager.DB_Team.addNewTeamToDatabase(team);
	    teamObservableList.add(team); // to update combobox without having to retrieve from SQL again
    }
}
