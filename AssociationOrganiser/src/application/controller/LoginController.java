package application.controller;

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
import java.util.Arrays;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    public void openCreateAccount(ActionEvent actionEvent) throws IOException {

        Parent createAccountParent = FXMLLoader.load(getClass().getResource("../../view/CreateAccount.fxml"));
        Scene createAccountScene = new Scene(createAccountParent);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(createAccountScene);
    }

    public void openMainScene() {

    }

    public void attemptLogIn(ActionEvent actionEvent) {

        // TODO: account for username non existence
        User desiredUserAccount = DatabaseManager.User.loadUserUsingUsername(usernameField.getText());
        String enteredPassword = passwordField.getText();

        // hash entered password with desired user account salt
        byte[] hashAttempt = DatabaseManager.Security.hashPassword(enteredPassword, desiredUserAccount.getSalt());

        // compare hash of entered to pre-existing hash
        if (Arrays.equals(hashAttempt, desiredUserAccount.getHashedPassword())) {

            // login
            System.out.println("success");
        }
        else {

            // failure
            System.out.println("wrong password");
        }
    }
}
