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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class UserRegisterController implements Initializable {
    private static Scene scene;
    private static Stage stage;
    public PasswordField tf_password;
    public TextField tf_email;
    public TextField tf_username;
    public Button button_register;

    public void exit(ActionEvent e){
        System.exit(0);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button_register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DbUtils.UserRegister(event,tf_username.getText(),tf_password.getText(),tf_email.getText());
            }
        });


    }

}
