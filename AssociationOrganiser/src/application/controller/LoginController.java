package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    public void openCreateAccount(ActionEvent actionEvent) throws IOException {

        Parent createAccountParent = FXMLLoader.load(getClass().getResource("../../view/CreateAccount.fxml"));
        Scene createAccountScene = new Scene(createAccountParent);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(createAccountScene);
    }
}
