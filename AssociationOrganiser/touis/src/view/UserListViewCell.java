package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import model.User;

import java.io.IOException;

public class UserListViewCell extends ListCell<User> {

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
    protected void updateItem(User user, boolean empty) {

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
                    System.out.println("elevate user: " + user.getUsername());
                }
            });

            btnRemove.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("demote user: " + user.getUsername());
                }
            });

            setText(null);
            setGraphic(Hbox);
        }
    }
}