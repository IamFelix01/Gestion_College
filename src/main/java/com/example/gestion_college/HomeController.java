package com.example.gestion_college;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController  implements Initializable {
    public Button emploi;
    private Stage stage;
    private Scene scene;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private BorderPane bp;

    public void exit(ActionEvent e ){
        System.exit(0);
    }

    public void minimize(ActionEvent e){
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private double x = 0;
    private double y = 0;
    @FXML
    private Button etudiantBtn;

    public void ToEtu(ActionEvent e) throws IOException {
        //etudiantBtn.getScene().getWindow().hide();

        Parent root = FXMLLoader.load(getClass().getResource("Etudiant.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        root.setOnMousePressed((MouseEvent event) ->{
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) ->{
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
    }

    public void ToEn(ActionEvent e) throws IOException {
        //etudiantBtn.getScene().getWindow().hide();

        Parent root = FXMLLoader.load(getClass().getResource("Enseignant.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        root.setOnMousePressed((MouseEvent event) ->{
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) ->{
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
    }
    public void ToEmploi(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Emploi.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.show();

    }
    public void ToNiv(ActionEvent e) throws IOException {
        //etudiantBtn.getScene().getWindow().hide();

        Parent root = FXMLLoader.load(getClass().getResource("Niveau.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        root.setOnMousePressed((MouseEvent event) ->{
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) ->{
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
    }

}