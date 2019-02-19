package java.controller;

import java.DB.DatabaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.model.User;

import java.io.IOException;
import java.io.InputStream;

public class CreateAccountController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private ImageView passwordCheck;

    @FXML
    private Label passwordMatchText;

    public void returnToLogin(ActionEvent actionEvent) throws IOException {

        Parent loginParent = FXMLLoader.load(getClass().getResource("../../resources/view/Login.fxml"));
        Scene loginScene = new Scene(loginParent);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(loginScene);
    }

    public void comparePasswordFields(KeyEvent keyEvent) throws IOException{ ;

        if (passwordField.getText().equals("") && confirmPasswordField.getText().equals("")) {

            passwordCheck.setVisible(false);
            passwordMatchText.setVisible(false);
        }
        else {

            passwordCheck.setVisible(true);
        }

        if (!passwordField.getText().equals(confirmPasswordField.getText())) {

            InputStream inputStream = getClass().getResourceAsStream("/resources/images/cross.png");
            Image image = new Image(inputStream);
            passwordCheck.setImage(image);
            passwordMatchText.setVisible(true);
        }
        else {

            InputStream inputStream = getClass().getResourceAsStream("/resources/images/tick.png");
            Image image = new Image(inputStream);
            passwordCheck.setImage(image);
            passwordMatchText.setVisible(false);
        }
    }

    public void createAccount(ActionEvent actionEvent) throws IOException {

        User user = new User();

        user.setUsername(usernameField.getText());
        user.setPassword(passwordField.getText());

        user.setSalt(DatabaseManager.Security.generateSalt());
        user.setHashedPassword(DatabaseManager.Security.hashPassword(user.getPassword(), user.getSalt()));

        if (user.getHashedPassword() != null) {

            if (!DatabaseManager.User.addPlayerToDatabase(user)) {

                System.out.println("duplicate placeholder");
            }
        }

        Parent loginParent = FXMLLoader.load(getClass().getResource("../../resources/view/Login.fxml"));
        Scene loginScene = new Scene(loginParent);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(loginScene);
    }

    public void checkUserNameAvailability(KeyEvent keyEvent) {


    }
}
