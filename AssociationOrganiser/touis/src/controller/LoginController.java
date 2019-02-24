// TODO: error message when username/password wrong

package controller;

import DB.DatabaseManager;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    public void openCreateAccount(ActionEvent actionEvent) throws IOException {

        Parent Parent = FXMLLoader.load(getClass().getResource("/view/CreateAccount.fxml"));
        Scene Scene = new Scene(Parent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(Scene);
    }

    private void openMainScene(ActionEvent actionEvent) throws IOException {

        Parent Parent = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
        Scene Scene = new Scene(Parent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(Scene);
    }

    public void attemptLogIn(ActionEvent actionEvent) {

        User desiredUserAccount = DatabaseManager.DB_User.getUserFromDatabase(usernameField.getText());
        String enteredPassword = passwordField.getText();

        assert desiredUserAccount != null;
        if (DatabaseManager.DB_Security.checkPassword(enteredPassword, desiredUserAccount)) {

            try {
                User.currentUser = desiredUserAccount;
                openMainScene(actionEvent);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {

            // failure
            System.out.println("wrong password");
        }
    }
}
