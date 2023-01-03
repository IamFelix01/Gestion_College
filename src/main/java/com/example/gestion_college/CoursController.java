package com.example.gestion_college;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class CoursController implements Initializable {
    public TextField addCours_jour;
    public TableColumn<Cours,String> addCours_col_jour;
    private Stage stage;
    private Scene scene;
    @FXML
    private Button Mbtn;

    @FXML
    private Button Xbtn;

    @FXML
    private TextField addCours_ID;

    @FXML
    private TextField addCours_classe;

    @FXML
    private TableColumn<Cours, String > addCours_col_classe;

    @FXML
    private TableColumn<Cours, String > addCours_col_ens;

    @FXML
    private TableColumn<Cours, String > addCours_col_hd;

    @FXML
    private TableColumn<Cours, String > addCours_col_hf;

    @FXML
    private TableColumn<Cours, String > addCours_col_id;

    @FXML
    private TableColumn<Cours, String > addCours_col_nom;

    @FXML
    private TableColumn<Cours, String > addCours_col_salle;

    @FXML
    private TextField addCours_ens;

    @FXML
    private TextField addCours_hd;

    @FXML
    private TextField addCours_hf;

    @FXML
    private TextField addCours_nom;

    @FXML
    private TextField addCours_salle;

    @FXML
    private TableView<Cours> addCours_tableView;

    @FXML
    private TextField addCours_search;

    @FXML
    private TableView<Salle> addProfs_tableView;

    @FXML
    private TableColumn<Salle, Integer> addSalles_col_SalleID;

    @FXML
    private TableColumn<Salle, String> addSalles_col_type;
    @FXML
    private TextField Horaire;

    @FXML
    private TextField Materiels;
    @FXML
    private TextField Type;



    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    //lister les Cours de la bdd
    public ObservableList<Cours> addCoursListData(){
        ObservableList<Cours> listCours = FXCollections.observableArrayList();

        String sql = "SELECT * FROM cours";


        try {
            Connection connect = Connexion.getConnection();
            Cours CoursD;
            assert connect != null;
            PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                CoursD = new Cours(result.getInt("id_cours"),
                        result.getString("nom"),
                        result.getInt("heurDebut"),
                        result.getInt("heurFin"),
                        result.getInt("id_classe"),
                        result.getInt("id_salle"),
                        result.getInt("jour")
                );

                listCours.add(CoursD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCours;

    }

    private ObservableList<Cours> addCoursListD = FXCollections.observableArrayList();

    public void addCoursShowListData() {
        addCoursListD = addCoursListData();

        addCours_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        addCours_col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        addCours_col_hd.setCellValueFactory(new PropertyValueFactory<>("heureDebut"));
        addCours_col_hf.setCellValueFactory(new PropertyValueFactory<>("heureFin"));
        addCours_col_classe.setCellValueFactory(new PropertyValueFactory<>("id_classe"));
        addCours_col_salle.setCellValueFactory(new PropertyValueFactory<>("id_salle"));
        addCours_col_jour.setCellValueFactory(new PropertyValueFactory<>("jour"));
        addCours_tableView.setItems(addCoursListD);

    }

    //ajouter un Cours selectionné dans les champs pour faire MAJ
    public void addCoursSelect() {

        Cours CoursD = addCours_tableView.getSelectionModel().getSelectedItem();
        int num = addCours_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        addCours_ID.setText(String.valueOf(CoursD.getId()));
        addCours_nom.setText(CoursD.getNom());
        addCours_hd.setText(String.valueOf(CoursD.getHeureDebut()));
        addCours_hf.setText(String.valueOf(CoursD.getHeureFin()));
        addCours_classe.setText(String.valueOf(CoursD.getId_classe()));
        addCours_salle.setText(String.valueOf(CoursD.getId_salle()));


    }

    //create table Cours (id int primary key, nom varchar(30),heureDebut varchar(30),heureFin varchar(30), enseignant varchar(30), classe varchar(30), salle varchar(30) )
    public void ajouterCours() {
        int id_cours = Integer.parseInt(addCours_ID.getText());
        String nom = addCours_nom.getText();
        int hd = Integer.parseInt(addCours_hd.getText());
        int hf = Integer.parseInt(addCours_hf.getText());
        int id_classe =Integer.parseInt(addCours_classe.getText());
        int id_salle = Integer.parseInt(addCours_salle.getText());
        int jour = Integer.parseInt(addCours_jour.getText());
        if(CoursModel.AddCours(id_cours,nom,hd,hf,id_classe,id_salle,jour)){
            addCoursShowListData();
            addCoursClear();
        }

    }
    public void updateCours(){
        int id_cours = Integer.parseInt(addCours_ID.getText());
        String nom = addCours_nom.getText();
        int hd = Integer.parseInt(addCours_hd.getText());
        int hf = Integer.parseInt(addCours_hf.getText());
        int jour = Integer.parseInt(addCours_jour.getText());
        if(CoursModel.UpdateCours(id_cours,nom,hd,hf,jour)){
            addCoursShowListData();
            addCoursClear();
        }
    }
    public void deleteCours(){
        int id_cours = Integer.parseInt(addCours_ID.getText());

        if(CoursModel.DeleteCours(id_cours)){
            addCoursShowListData();
            addCoursClear();
        }
    }
    public void addCoursClear() {
        addCours_ID.setText("");
        addCours_hf.setText("");
        addCours_hd.setText("");
        addCours_nom.setText("");
        addCours_classe.setText("");
        addCours_salle.setText("");
        addCours_jour.setText("");
    }



    public void addCoursSearch(){
        String query = addCours_search.getText().toLowerCase();
        addCours_tableView.setItems(addCoursListD.filtered(person -> person.getNom().toLowerCase().contains(query)
                || String.valueOf(person.getHeureFin()).contains(query)
                || String.valueOf(person.getHeureDebut()).contains(query)
                || String.valueOf(person.getId()).contains(query)
        ));
    }

//    select id, type from salle where (id,8) not in (select Salle, heureDebut from cours) And "Counter" in (select materiel from ligne where id=idsalle) and type = "laboratoire";
//SELECT * FROM Ligne WHERE materiel IN ("Rideau noir", "Labo") GROUP BY idSalle HAVING COUNT(*) = 2;
//SELECT salle.id, type from salle,ligne where type = "Amphi" And (salle.id,8) not in (select Salle, heureDebut from cours)  and materiel IN ("Rideau noir", "Projecteur vidéo") GROUP BY idSalle HAVING COUNT(*) = 2;
    //    select id, type from salle where (id,16) not in (select Salle, heureDebut from cours) And id in (SELECT idSalle FROM Ligne WHERE materiel IN ("Rideau noir", "Projecteur vidéo") GROUP BY idSalle HAVING COUNT(*) = 2) and type = "Amphi";

    //lister les Salle de la bdd
    public ObservableList<Salle> addprofListData(){
        ObservableList<Salle> listSalle = FXCollections.observableArrayList();

        String sql = "select id_salle, type from salle where (id_salle,?) not in (select id_salle, heurDebut from cours) And type = ? And id_salle in (SELECT idSalle FROM ligne WHERE materiel IN (";


        String[] Parameter = Materiels.getText().split("-");
        String temp = "";

        for(int i = 0; i < Parameter.length; i++) {
            temp += ",?";
        }

        temp = temp.replaceFirst(",", "");

        temp += ") GROUP BY idSalle HAVING COUNT(*) = ?) ";
        sql = sql + temp;

        try {
            Connection connect = Connexion.getConnection();
            Salle salleD;
            assert connect != null;
            PreparedStatement prepare = connect.prepareStatement(sql);
            prepare.setInt(1,Integer.parseInt(Horaire.getText()));
            prepare.setString(2,Type.getText());
            int i;
            for (i=3 ; i <Parameter.length + 3; i++)
                prepare.setString(i,Parameter[i-3]);
            prepare.setString(i, String.valueOf(Parameter.length));

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
        ObservableList<Salle> addProfsListD = addprofListData();

        addSalles_col_SalleID.setCellValueFactory(new PropertyValueFactory<>("id"));
        addSalles_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        addProfs_tableView.setItems(addProfsListD);

    }










    public void exit(ActionEvent e ){
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();
        //System.exit(0);
    }

    public void minimize(ActionEvent e){
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addCoursShowListData();
        addCoursSearch();
        Xbtn.setStyle("-fx-background-color: null;");
        Mbtn.setStyle("-fx-background-color: null;");
    }


}