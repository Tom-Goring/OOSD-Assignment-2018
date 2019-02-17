package misc;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login extends Application {

    // TODO: add database backend for logins + pw hashing
    private String userName = "Tom";
    private String pw = "password";
    private String checkUser, checkPw;

    @Override
    public void start(Stage primaryStage) {

        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10,50,10,50));

        HBox hb = new HBox();
        hb.setPadding(new Insets(20,20,20,30));
        Text title = new Text("Login");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        hb.getChildren().add(title);

        GridPane gp = new GridPane();
        gp.setPadding(new Insets(20,20,20,30));
        gp.setHgap(10);
        gp.setVgap(10);

        Label l_userName = new Label("Username");
        final TextField txt_userName = new TextField();
        Label l_password = new Label("Password");
        final PasswordField pf_password = new PasswordField();
        Button btn_login = new Button("Login");
        final Label l_message = new Label();

        gp.add(l_userName, 0, 0);
        gp.add(txt_userName, 1, 0);
        gp.add(l_password, 0, 1);
        gp.add(pf_password, 1, 1);
        gp.add(btn_login, 2, 1);
        gp.add(l_message, 1, 2);

        btn_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                checkUser = txt_userName.getText();
                checkPw = pf_password.getText();

                if (checkUser.equals(userName) && checkPw.equals(pw)) {

                    l_message.setText("Login_success_placeholder");
                    l_message.setTextFill(Color.GREEN);
                }
                else {

                    l_message.setText("Login_fail_placeholder");
                    l_message.setTextFill(Color.RED);
                }

                txt_userName.clear();
                pf_password.clear();
            }
        });

        Hyperlink createAccount = new Hyperlink();
        createAccount.setText("Create Account");
        createAccount.setOnAction(event -> primaryStage.setScene(null));

        HBox hb2 = new HBox();
        hb2.setPadding(new Insets(20));
        hb.getChildren().add(createAccount);

        bp.setBottom(createAccount);
        bp.setCenter(gp);
        bp.setTop(hb);

        Scene scene = new Scene(bp);
        scene.setFill(Color.OLDLACE);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Table Tennis Association Manager");
        primaryStage.getIcons().add(new Image("/resources/tennis.png"));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
