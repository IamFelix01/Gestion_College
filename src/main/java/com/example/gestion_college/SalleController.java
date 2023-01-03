package com.example.gestion_college;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class SalleController implements Initializable {
    private Stage stage;
    private Scene scene;

    @FXML
    private Button Mbtn;

    @FXML
    private Button Xbtn;

    @FXML
    private TextField addLignes_ID;

    @FXML
    private TableColumn<?, ?> addLignes_col_idLigne;

    @FXML
    private TableColumn<?, ?> addLignes_col_idSalle;

    @FXML
    private TableColumn<?, ?> addLignes_col_materiel;

    @FXML
    private TextField addLignes_idSalle;

    @FXML
    private TextField addLignes_materiel;

    @FXML
    private TableView<Salle> addProfs_tableView;

    @FXML
    private TableView<Ligne> addProfs_tableView1;

    @FXML
    private TextField addSalles_ID;

    @FXML
    private TableColumn<?, ?> addSalles_col_SalleID;

    @FXML
    private TableColumn<?, ?> addSalles_col_type;

    @FXML
    private TextField addSalles_type;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    //lister les Ligne de la bdd
    public ObservableList<Ligne> addLigneListData(){
        ObservableList<Ligne> listLigne = FXCollections.observableArrayList();

        String sql = "SELECT * FROM ligne";


        try {
            Connection connect = Connexion.getConnection();
            Ligne profD;
            PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                profD = new Ligne(result.getInt("id"),
                        result.getString("materiel"),
                        result.getInt("idSalle")
                );

                listLigne.add(profD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listLigne;

    }

    private ObservableList<Ligne> addLigneListD;

    public void addProfsShowListData1() {
        addLigneListD = addLigneListData();

        addLignes_col_idLigne.setCellValueFactory(new PropertyValueFactory<>("id"));
        addLignes_col_materiel.setCellValueFactory(new PropertyValueFactory<>("materiel"));
        addLignes_col_idSalle.setCellValueFactory(new PropertyValueFactory<>("idSalle"));
        addProfs_tableView1.setItems(addLigneListD);

    }

    //ajouter un Ligne selectionné dans les champs pour faire MAJ
    public void addProfsSelect1() {

        Ligne profD = (Ligne) addProfs_tableView1.getSelectionModel().getSelectedItem();
        int num = addProfs_tableView1.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        addLignes_ID.setText(String.valueOf(profD.getId()));
        addLignes_materiel.setText(profD.getMateriel());
        addLignes_idSalle.setText(String.valueOf(profD.getIdSalle()));

    }


    //   create table Ligne(id int primary key, type varchar(30),pretype varchar(15), sexe varchar(10), Contact varchar(15), matiere varchar(15), Ligne varchar(15));
    //insert into Ligne values (1,
    public void ajouterLigne() {
        if(LigneModel.AddLigne(Integer.parseInt(addLignes_ID.getText()),addLignes_materiel.getText(),Integer.parseInt(addLignes_idSalle.getText()))) {
            addProfsShowListData1();
            addProfsClear1();
        }
    }
    public void deleteLigne() {
        if(LigneModel.DeleteLigne(Integer.parseInt(addLignes_ID.getText()))){
            addProfsShowListData1();
            addProfsClear1();
        }

    }

    public void addProfsClear1() {
        addLignes_ID.setText("");
        addLignes_materiel.setText("");
        addLignes_idSalle.setText("");
    }



    //lister les Salle de la bdd
    public ObservableList<Salle> addprofListData(){
        ObservableList<Salle> listSalle = FXCollections.observableArrayList();


        try {
            Connection connect = Connexion.getConnection();
            Salle salleD;
            assert connect != null;
            PreparedStatement prepare = connect.prepareStatement("SELECT * FROM salle");
            ResultSet result = prepare.executeQuery();
            while (result.next()) {
                salleD = new Salle(result.getInt("id_salle"),
                        result.getString("type")
                );

                listSalle.add(salleD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSalle;

    }

    public void addProfsShowListData() {
        ObservableList<Salle> salleListData = addprofListData();

        addSalles_col_SalleID.setCellValueFactory(new PropertyValueFactory<>("id"));
        addSalles_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        addProfs_tableView.setItems(salleListData);

    }

    //ajouter un Salle selectionné dans les champs pour faire MAJ
    public void addProfsSelect() {

        Salle profD = (Salle) addProfs_tableView.getSelectionModel().getSelectedItem();
        int num = addProfs_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        addSalles_ID.setText(String.valueOf(profD.getId()));
        addSalles_type.setText(profD.getType());

    }

    //   create table Salle(id int primary key, type varchar(30),pretype varchar(15), sexe varchar(10), Contact varchar(15), matiere varchar(15), Ligne varchar(15));
    //insert into Salle values (1,
    public void ajouterSalle() {
        if(SalleModel.AddSalle(Integer.parseInt(addSalles_ID.getText()),addSalles_type.getText())){
            addProfsShowListData();
            addProfsClear();
        }
    }
    public void updateSalle() {
        if(SalleModel.UpdateSalle(Integer.parseInt(addSalles_ID.getText()),addSalles_type.getText())){
            addProfsShowListData();
            addProfsClear();
        }
    }
    public void deleteSalle() {
        if(SalleModel.DeleteSalle(Integer.parseInt(addSalles_ID.getText()))){
            addProfsShowListData();
            addProfsClear();
        }
    }

    public void addProfsClear() {
        addSalles_ID.setText("");
        addSalles_type.setText("");
    }



    public void exit(ActionEvent e ){
        Stage stage = (Stage) Xbtn.getScene().getWindow();
        stage.close();
    }
    public void minimize(ActionEvent e){
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProfsShowListData();
        addProfsShowListData1();
        //addProfsYearList();
        //addStudentsSexeList();
        Xbtn.setStyle("-fx-background-color: null;");
        Mbtn.setStyle("-fx-background-color: null;");
    }
}