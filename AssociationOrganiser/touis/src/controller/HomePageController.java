package controller;

import DB.DatabaseManager;
import model.User;
import view.UserListViewCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    @FXML
    ListView userListView;

    private ObservableList<User> userObservableList;

    public HomePageController() {

        userObservableList = FXCollections.observableArrayList();

        ArrayList<User> userList = DatabaseManager.DB_User.getUserList();
        userObservableList.addAll(userList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userListView.setItems(userObservableList);
        userListView.setCellFactory(userListView -> new UserListViewCell());
    }
}
