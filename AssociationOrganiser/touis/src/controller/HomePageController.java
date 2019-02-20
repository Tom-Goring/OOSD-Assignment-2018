package controller;

import DB.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import model.User;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    @FXML
    ListView userListView;

    private ArrayList<User> userList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        userList = DatabaseManager.DB_User.getUserList();
    }
}
