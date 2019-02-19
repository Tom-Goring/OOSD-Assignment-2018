package java.controller;

import java.DB.DatabaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.model.User;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    public void openCreateAccount(ActionEvent actionEvent) throws IOException {

        Parent Parent = FXMLLoader.load(getClass().getResource("../../resources/view/CreateAccount.fxml"));
        Scene Scene = new Scene(Parent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(Scene);
    }

    public void openMainScene(ActionEvent actionEvent, User user) throws IOException {

        Parent Parent = FXMLLoader.load(getClass().getResource("../../resources/view/HomePage.fxml"));
        Scene Scene = new Scene(Parent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(Scene);
        window.setResizable(true);
    }

    public void attemptLogIn(ActionEvent actionEvent) {

        // TODO: account for username non existence
        User desiredUserAccount = DatabaseManager.User.getUserFromDatabase(usernameField.getText());
        String enteredPassword = passwordField.getText();

        if (DatabaseManager.Security.checkPassword(enteredPassword, desiredUserAccount)) {

            try {
                openMainScene(actionEvent, desiredUserAccount);
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
