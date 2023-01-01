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
    private TableColumn<?, ?> addSalles_col_SalleID;

    @FXML
    private TableColumn<?, ?> addSalles_col_type;
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

        String sql = "SELECT * FROM Cours";


        try {
            Connection connect = getConnection();
            Cours CoursD;
            PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                CoursD = new Cours(result.getInt("id"),
                        result.getString("nom"),
                        result.getInt("heureDebut"),
                        result.getInt("heureFin"),
                        result.getString("enseignant"),
                        result.getString("Classe"),
                        result.getString("salle")
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
        addCours_col_ens.setCellValueFactory(new PropertyValueFactory<>("enseignant"));
        addCours_col_classe.setCellValueFactory(new PropertyValueFactory<>("classe"));
        addCours_col_salle.setCellValueFactory(new PropertyValueFactory<>("salle"));

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
        addCours_ens.setText(CoursD.getEnseignant());
        addCours_classe.setText(CoursD.getClasse());
        addCours_salle.setText(CoursD.getSalle());


    }

    //create table Cours (id int primary key, nom varchar(30),heureDebut varchar(30),heureFin varchar(30), enseignant varchar(30), classe varchar(30), salle varchar(30) )
    public void ajouterCours() {

        String insertData = "INSERT INTO Cours (id, nom,heureDebut, heureFin, enseignant, Classe,salle) VALUES(?,?,?,?,?,?,?)";

        try {
            connect = getConnection();
            Alert alert;

            if (addCours_ID.getText().isEmpty()
                    || addCours_nom.getText().isEmpty()
                    || addCours_hd.getText().isEmpty()
                    || addCours_hf.getText().isEmpty()
                    || addCours_classe.getText().isEmpty()
                    || addCours_ens.getText().isEmpty()
                    || addCours_salle.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP taper tous les champs");
                alert.showAndWait();
            } else {
//            CHECK IF THE COURSE YOU WANT TO INSERT IS ALREADY EXIST!
                String checkData = "SELECT id FROM Cours WHERE id = '"
                        + addCours_ID.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cours: " + addCours_ID.getText() + " est deja ajouté");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, addCours_ID.getText());
                    prepare.setString(2, addCours_nom.getText());
                    prepare.setString(3, addCours_hd.getText());
                    prepare.setString(4, addCours_hf.getText());
                    prepare.setString(5, addCours_ens.getText());
                    prepare.setString(6, addCours_classe.getText());
                    prepare.setString(7, addCours_salle.getText());

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    // TO BECOME UPDATED OUR TABLEVIEW ONCE THE DATA WE GAVE SUCCESSFUL
                    addCoursShowListData();
                    // TO CLEAR THE TEXT FIELDS
                    //availableCourseClear();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addCoursClear() {
        addCours_ID.setText("");
        addCours_hf.setText("");
        addCours_hd.setText("");
        addCours_nom.setText("");
        addCours_classe.setText("");
        addCours_salle.setText("");
        addCours_ens.setText("");
    }

    public void addCoursUpdate() {

        String updateData = "UPDATE Cours SET "
                + "id = '" + addCours_ID.getText()
                + "', nom = '" + addCours_nom.getText()
                + "', heureDebut = '" + addCours_hd.getText()
                + "', heureFin = '" + addCours_hf.getText()
                + "', enseignant = '" + addCours_ens.getText()
                + "', classe = '" + addCours_classe.getText()
                + "', salle = '" + addCours_salle.getText() + "' WHERE id = '"
                + addCours_ID.getText() + "'";




        try {
            connect = getConnection();
            Alert alert;
            if (addCours_ID.getText().isEmpty()
                    || addCours_nom.getText().isEmpty()
                    || addCours_hd.getText().isEmpty()
                    || addCours_hf.getText().isEmpty()
                    || addCours_classe.getText().isEmpty()
                    || addCours_ens.getText().isEmpty()
                    || addCours_salle.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP completer tous les champs");
                alert.showAndWait();
            } else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("etes-vous sure pour modifier " + addCours_ID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(updateData);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    // TO UPDATE THE TABLEVIEW
                    addCoursShowListData();
                    // TO CLEAR THE FIELDS
                    addCoursClear();

                } else {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addCoursSearch(){
        String query = addCours_search.getText().toLowerCase();
        addCours_tableView.setItems(addCoursListD.filtered(person -> person.getNom().toLowerCase().contains(query)
                || person.getSalle().toLowerCase().contains(query)
                || person.getClasse().toLowerCase().contains(query)
                || person.getEnseignant().toLowerCase().contains(query)
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

        String sql = "select id, type from salle where (id,?) not in (select Salle, heureDebut from cours) And type = ? And id in (SELECT idSalle FROM Ligne WHERE materiel IN (";


        String[] Parameter = Materiels.getText().split("-");
        String temp = "";

        for(int i = 0; i < Parameter.length; i++) {
            temp += ",?";
        }

        temp = temp.replaceFirst(",", "");

        temp += ") GROUP BY idSalle HAVING COUNT(*) = ?) ";
        sql = sql + temp;

        try {
            Connection connect = getConnection();
            Salle profD;
            PreparedStatement prepare = connect.prepareStatement(sql);
            prepare.setString(1,Horaire.getText());
            prepare.setString(2,Type.getText());
            int i;
            for (i=3 ; i <Parameter.length + 3; i++)
                prepare.setString(i,Parameter[i-3]);
            prepare.setString(i, String.valueOf(Parameter.length));

            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                profD = new Salle(result.getInt("id"),
                        result.getString("type")
                );

                listSalle.add(profD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSalle;

    }

    private ObservableList<Salle> addProfsListD;

    public void addProfsShowListData() {
        addProfsListD = addprofListData();

        addSalles_col_SalleID.setCellValueFactory(new PropertyValueFactory<>("id"));
        addSalles_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        addProfs_tableView.setItems(addProfsListD);

    }



    public static Connection getConnection() throws Exception {
        try {
            String driver  = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://127.0.0.1:3306/college";
            String user = "root";
            String pass = "root";
            Class.forName(driver);

            Connection conn = DriverManager.getConnection(url,user,pass);
            System.out.println("Connected Now");
            return conn;

        }catch(Exception e) {
            System.out.println("Something is gone wrong error: " + e);
        }
        return null;
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
