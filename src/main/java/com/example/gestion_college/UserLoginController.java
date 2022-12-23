package com.example.gestion_college;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserLoginController implements Initializable {

    public Button button_login;
    public TextField tf_username;
    public PasswordField tf_password;
    private static Stage stage;
    private static Scene scene;
    public void exit(ActionEvent e ){
        System.exit(0);
    }
    public void minimize(ActionEvent e){

    }
    public static void ChangeToRegister(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(UserLoginController.class.getResource("Register.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DbUtils.UserLogin(event,tf_username.getText(),tf_password.getText());
            }
        });
    }

    public void ToRegister(ActionEvent actionEvent) throws IOException {
        ChangeToRegister(actionEvent);
    }
}
