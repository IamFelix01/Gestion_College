package com.example.gestion_college;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EmploiController implements Initializable {
    @FXML
    Button Xbtn;
    @FXML
    Button Mbtn;
    @FXML
    private Label C11;

    @FXML
    private Label C12;

    @FXML
    private Label C13;

    @FXML
    private Label C14;

    @FXML
    private Label C15;

    @FXML
    private Label C16;

    @FXML
    private Label C17;
    @FXML
    private Label C18;

    @FXML
    private Label C21;

    @FXML
    private Label C22;

    @FXML
    private Label C23;

    @FXML
    private Label C24;

    @FXML
    private Label C25;

    @FXML
    private Label C26;

    @FXML
    private Label C27;

    @FXML
    private Label C28;

    @FXML
    private Label C31;

    @FXML
    private Label C32;

    @FXML
    private Label C33;

    @FXML
    private Label C34;

    @FXML
    private Label C35;

    @FXML
    private Label C36;

    @FXML
    private Label C37;

    @FXML
    private Label C38;

    @FXML
    private Label C41;

    @FXML
    private Label C42;

    @FXML
    private Label C43;

    @FXML
    private Label C44;

    @FXML
    private Label C45;

    @FXML
    private Label C46;

    @FXML
    private Label C47;

    @FXML
    private Label C48;

    @FXML
    private Label C51;

    @FXML
    private Label C52;

    @FXML
    private Label C53;

    @FXML
    private Label C54;

    @FXML
    private Label C55;

    @FXML
    private Label C56;

    @FXML
    private Label C57;

    @FXML
    private Label C58;

    @FXML
    private Label C61;

    @FXML
    private Label C62;

    @FXML
    private Label C63;

    @FXML
    private Label C64;

    @FXML
    private Label C65;

    @FXML
    private Label C66;

    @FXML
    private Label C67;

    @FXML
    private Label C68;


    private Stage stage;
    private Scene scene;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;


    public void addStudentsShowListData() {
        String sql = "SELECT cours.nom, enseignant.nom FROM cours, enseignant where cours.id_cours = enseignant.id_cours And heurDebut = ? and jour = ? and cours.id_classe = ?";


        try {
            connect = Connexion.getConnection();


            assert connect != null;
            PreparedStatement prepare = connect.prepareStatement(sql);

            String[] temps = {"8","9","10","11","14","15","16","17"};
            Label[][] labels = {{C11, C12,C13,C14, C15, C16, C17,C18},
                    {C21, C22,C23,C24, C25, C26, C27,C28},
                    {C31, C32,C33,C34, C35, C36, C37,C38},
                    {C41, C42,C43,C44, C45, C46, C47,C48},
                    {C51, C52,C53,C54, C55, C56, C57,C58},
                    {C61, C62,C63,C64, C65, C66, C67,C68}
            };

            int i = 0;
            int j=0;
            String[] jours = {"lundi","mardi","mercredi","jeudi","vendredi","samedi"};

            for (String jr: jours){
                j=0;
                for(String s: temps) {
                    prepare.setString(2, jr);
                    prepare.setInt(3, 2);
                    prepare.setString(1, s);
                    ResultSet result = prepare.executeQuery();



                    if(result.next())
                        labels[i][j].setText(result.getString(1) + "\n(" + result.getString(2)+")");
                    else
                        labels[i][j].setText("");
                    j++;
                }
                i++;
            }




        } catch (Exception e) {
            e.printStackTrace();
        }


    }









    public void exit(ActionEvent e ){
        Stage stage = (Stage) Mbtn.getScene().getWindow();
        stage.close();
        addStudentsShowListData();
    }

    public void minimize(ActionEvent e){
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setIconified(true);
        addStudentsShowListData();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addStudentsShowListData();
        Xbtn.setStyle("-fx-background-color: null;");
        Mbtn.setStyle("-fx-background-color: null;");

    }
}