package view;

import DB.DatabaseManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import model.BasicUser;

import java.io.IOException;

public class UserListViewCell extends ListCell<BasicUser> {

    @FXML
    private Label Username;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnRemove;

    @FXML
    private HBox Hbox;

    private FXMLLoader fxmlLoader;

    @Override
    protected void updateItem(BasicUser user, boolean empty) {

        super.updateItem(user, empty);

        if (empty || user == null) {

            setText(null);
            setGraphic(null);
        }
        else {

            if (fxmlLoader == null) {

                fxmlLoader = new FXMLLoader(getClass().getResource("/view/ListViewCell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Username.setText(user.getUsername());

            btnAdd.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    DatabaseManager.DB_User.changeUserPrivilegeLevel(user, 2);
                    btnAdd.setDisable(true);
                    btnRemove.setDisable(false);
                }
            });

            btnRemove.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    DatabaseManager.DB_User.changeUserPrivilegeLevel(user, 1);
                    btnAdd.setDisable(false);
                    btnRemove.setDisable(true);
                }
            });

            if (user.getPrivilegeLevel() == 1) {

                btnAdd.setDisable(false);
                btnRemove.setDisable(true);
            }
            else if (user.getPrivilegeLevel() == 2) {

                btnAdd.setDisable(true);
                btnRemove.setDisable(false);
            }

            if (user.getUsername().equals("admin") || BasicUser.currentUser.getUsername().equals(user.getUsername())) {

                btnAdd.setDisable(true);
                btnRemove.setDisable(true);
            }

            setText(null);
            setGraphic(Hbox);
        }
    }
}