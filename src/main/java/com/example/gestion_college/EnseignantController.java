package com.example.gestion_college;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.*;

public class EnseignantController implements Initializable {
    private Stage stage;
    private Scene scene;
    @FXML
    private Button Mbtn;

    @FXML
    private Button Xbtn;

    @FXML
    private TextField addProfs_ID;

    @FXML
    private TextField addProfs_classe;

    @FXML
    private TableColumn<Enseignant, String> addProfs_col_Contact;

    @FXML
    private TableColumn<Enseignant, String> addProfs_col_classe;

    @FXML
    private TableColumn<Enseignant, String> addProfs_col_firstName;

    @FXML
    private TableColumn<Enseignant, String> addProfs_col_gender;

    @FXML
    private TableColumn<Enseignant, String> addProfs_col_lastName;

    @FXML
    private TableColumn<Enseignant, String> addProfs_col_matiere;

    @FXML
    private TableColumn<Enseignant, String> addProfs_col_profNum;

    @FXML
    private TextField addProfs_contact;

    @FXML
    private TextField addProfs_matiere;

    @FXML
    private TextField addProfs_nom;

    @FXML
    private TextField addProfs_prenom;

    @FXML
    private ComboBox<String> addProfs_sexe;

    @FXML
    private TableView<Enseignant> addProfs_tableView;
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    //lister les Enseignant de la bdd
    public ObservableList<Enseignant> addprofListData(){
        ObservableList<Enseignant> listEnseignant = FXCollections.observableArrayList();

//        String sql = "SELECT * FROM Enseignant";


        try {
            Connection connect = Connexion.getConnection();
            Enseignant profD;
            assert connect != null;
            PreparedStatement prepare = connect.prepareStatement("Select * from Enseignant");
            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                profD = new Enseignant(result.getInt("id_prof"),
                        result.getString("nom"),
                        result.getString("prenom"),
                        result.getString("sexe"),
                        result.getString("Contact"),
                        result.getString("code_cours"),
                        result.getString("code_niveau")
                );

                listEnseignant.add(profD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listEnseignant;

    }

    public void addProfsShowListData() {
        ObservableList<Enseignant> addProfsListD = addprofListData();
        addProfs_col_profNum.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProfs_col_firstName.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        addProfs_col_lastName.setCellValueFactory(new PropertyValueFactory<>("nom"));
        addProfs_col_gender.setCellValueFactory(new PropertyValueFactory<>("Sexe"));
        addProfs_col_Contact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        addProfs_col_matiere.setCellValueFactory(new PropertyValueFactory<>("Matiere"));
        addProfs_col_classe.setCellValueFactory(new PropertyValueFactory<>("Classe"));

        addProfs_tableView.setItems(addProfsListD);

    }

    //add les niveaux au list
//    private String[] niveauList = {"1ERE", "2EME", "3EME"};
//
//    public void addProfsYearList() {
//
//        List<String> yearL = new ArrayList<>();
//
//        for (String data : niveauList) {
//            yearL.add(data);
//        }
//
//        ObservableList ObList = FXCollections.observableArrayList(yearL);
//        addProfs_niv.setItems(ObList);
//
//    }



    public void addProfsSexeList() {
        String[] SexeList = {"Homme", "Femme"};
        List<String> SexeL = new ArrayList<>(Arrays.asList(SexeList));

        ObservableList<String> ObList = FXCollections.observableArrayList(SexeL);
        addProfs_sexe.setItems(ObList);

    }


    //ajouter un Enseignant selectionné dans les champs pour faire MAJ
    public void addProfsSelect() {

        Enseignant profD = (Enseignant) addProfs_tableView.getSelectionModel().getSelectedItem();
        int num = addProfs_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        addProfs_ID.setText(String.valueOf(profD.getId()));
        addProfs_nom.setText(profD.getNom());
        addProfs_prenom.setText(profD.getPrenom());
        addProfs_classe.setText(profD.getClasse());
        addProfs_matiere.setText(profD.getMatiere());
        addProfs_sexe.setValue(profD.getSexe());
//        addProfs_n.setSelectionModel(profD.getNiveau());
        addProfs_contact.setText(profD.getContact());


    }

    //   create table Enseignant(id int primary key, nom varchar(30),prenom varchar(15), sexe varchar(10), Contact varchar(15), matiere varchar(15), Classe varchar(15));
    //insert into Enseignant values (1,
    public void ajouterEnseignant() {
        int id_prof = Integer.parseInt(addProfs_ID.getText());
        String nom = addProfs_nom.getText();
        String prenom = addProfs_prenom.getText();
        String sexe = (String) addProfs_sexe.getSelectionModel().getSelectedItem();
        int contact = Integer.parseInt(addProfs_contact.getText());
        String code_cours = addProfs_matiere.getText();
        String code_niveau = addProfs_classe.getText();
        EnseignantModel.AjouterEnseignant(id_prof,nom,prenom,sexe,contact,code_cours,code_niveau);
        addProfsShowListData();
    }

    public void addProfsClear() {
        addProfs_ID.setText("");
        addProfs_sexe.getSelectionModel().clearSelection();
        addProfs_matiere.setText("");
        addProfs_prenom.setText("");
        addProfs_nom.setText("");
        addProfs_classe.setText("");
        addProfs_contact.setText("");
    }

    public void addProfsUpdate() {
        int id_prof = Integer.parseInt(addProfs_ID.getText());
        String nom = addProfs_nom.getText();
        String prenom = addProfs_prenom.getText();
        String sexe = (String) addProfs_sexe.getSelectionModel().getSelectedItem();
        int contact = Integer.parseInt(addProfs_contact.getText());
        String code_cours = addProfs_matiere.getText();
        String code_niveau = addProfs_classe.getText();
        if(EnseignantModel.UpdateEnseignant(id_prof,nom,prenom,sexe,contact,code_cours,code_niveau)) {
            // TO UPDATE THE TABLEVIEW
            addProfsShowListData();
            // TO CLEAR THE FIELDS
            addProfsClear();
        } else{
            System.out.println("couldnt update!!");
        }
    }


//    public static Connection getConnection() throws Exception {
//        try {
//            String driver  = "com.mysql.jdbc.Driver";
//            String url = "jdbc:mysql://127.0.0.1:3306/gestioncollege";
//            String user = "root";
//            String pass = "@saidbenchad@123";
//            Class.forName(driver);
//
//            Connection conn = DriverManager.getConnection(url,user,pass);
//            System.out.println("Connected Now");
//            return conn;
//
//        }catch(Exception e) {
//            System.out.println("Something is gone wrong error: " + e);
//        }
//        return null;
//    }

//DELETE ENSEIGNANT
public void DeleteEnseignant(){
    int id_prof = Integer.parseInt(addProfs_ID.getText());
    String nom = addProfs_nom.getText();
    String prenom = addProfs_prenom.getText();
    if(EnseignantModel.DeleteEnseignant(id_prof,nom,prenom)){
        addProfsShowListData();
        // TO CLEAR THE FIELDS
        addProfsClear();
        addProfsClear();
    } else {
        System.out.println("Failed To Delete ... returns false");
    }
}





    public void exit(ActionEvent e ){
        System.exit(0);
    }

    public void minimize(ActionEvent e){
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProfsShowListData();
        //addProfsYearList();
        addProfsSexeList();
        Xbtn.setStyle("-fx-background-color: null;");
        Mbtn.setStyle("-fx-background-color: null;");
    }
}