package com.example.gestion_college;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class UserRegisterController implements Initializable {
    private static Scene scene;
    private static Stage stage;
    public PasswordField tf_password;
    public TextField tf_email;
    public TextField tf_username;
    public Button button_register;
    public TextField tf_lastname;
    public TextField tf_firstname;
    public ComboBox<String> tf_niveau;
    public TextField tf_age;


    public void ShowNiveau() {
        String[] niveauList = {"3EME", "4EME", "5EME","6EME"};
        List<String> ListNiv = new ArrayList<>(Arrays.asList(niveauList));
        ObservableList<String> ObList = FXCollections.observableArrayList(ListNiv);
        tf_niveau.setItems(ObList);

    }


    public void exit(ActionEvent e){
        System.exit(0);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ShowNiveau();
        button_register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String niveau = (String) tf_niveau.getSelectionModel().getSelectedItem();
                int age = Integer.parseInt(tf_age.getText());
                DbUtils.UserRegister(event,tf_lastname.getText(),tf_firstname.getText(),tf_email.getText(),niveau,age);
            }
        });


    }


}
