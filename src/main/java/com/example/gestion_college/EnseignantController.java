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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

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

        String sql = "SELECT * FROM Enseignant";


        try {
            Connection connect = getConnection();
            Enseignant profD;
            PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                profD = new Enseignant(result.getInt("id"),
                        result.getString("nom"),
                        result.getString("prenom"),
                        result.getString("sexe"),
                        result.getString("Contact"),
                        result.getString("Matiere"),
                        result.getString("Classe")
                );

                listEnseignant.add(profD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listEnseignant;

    }

    private ObservableList<Enseignant> addProfsListD;

    public void addProfsShowListData() {
        addProfsListD = addprofListData();

        addProfs_col_profNum.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProfs_col_firstName.setCellValueFactory(new PropertyValueFactory<>("nom"));
        addProfs_col_lastName.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        addProfs_col_gender.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        addProfs_col_Contact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        addProfs_col_matiere.setCellValueFactory(new PropertyValueFactory<>("matiere"));
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

    private String[] SexeList = {"Homme", "Femme"};

    public void addProfsSexeList() {

        List<String> SexeL = new ArrayList<>();

        for (String data : SexeList) {
            SexeL.add(data);
        }

        ObservableList ObList = FXCollections.observableArrayList(SexeL);
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
        addProfs_contact.setText(profD.getContact());


    }

 //   create table Enseignant(id int primary key, nom varchar(30),prenom varchar(15), sexe varchar(10), Contact varchar(15), matiere varchar(15), Classe varchar(15));
   //insert into Enseignant values (1,
    public void ajouterEnseignant() {

        String insertData = "INSERT INTO Enseignant (id, nom,prenom, sexe, Contact, matiere, Classe) VALUES(?,?,?,?,?,?,?)";

        try {
            connect = getConnection();
            Alert alert;

            if (addProfs_ID.getText().isEmpty()
                    || addProfs_nom.getText().isEmpty()
                    || addProfs_prenom.getText().isEmpty()
                    || addProfs_matiere.getText().isEmpty()
                    || addProfs_classe.getText().isEmpty()
                    || addProfs_sexe.getSelectionModel().getSelectedItem() == null
                    || addProfs_contact.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP taper tous les champs");
                alert.showAndWait();
            } else {
//            CHECK IF THE COURSE YOU WANT TO INSERT IS ALREADY EXIST!
                String checkData = "SELECT id FROM Enseignant WHERE id = '"
                        + addProfs_ID.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Enseignant: " + addProfs_ID.getText() + " est deja ajouté");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, addProfs_ID.getText());
                    prepare.setString(2, addProfs_nom.getText());
                    prepare.setString(3, addProfs_prenom.getText());
                    prepare.setString(4, (String) addProfs_sexe.getSelectionModel().getSelectedItem());
                    prepare.setString(5, addProfs_contact.getText());
                    prepare.setString(6, addProfs_matiere.getText());
                    prepare.setString(7, addProfs_classe.getText());

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    // TO BECOME UPDATED OUR TABLEVIEW ONCE THE DATA WE GAVE SUCCESSFUL
                    addProfsShowListData();
                    // TO CLEAR THE TEXT FIELDS
                    //availableCourseClear();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        String updateData = "UPDATE Enseignant SET "
                + "id = '" + addProfs_ID.getText()
                + "', nom = '" + addProfs_nom.getText()
                + "', prenom = '" + addProfs_prenom.getText()
                + "', sexe = '" + addProfs_sexe.getSelectionModel().getSelectedItem()
                + "', contact = '" + addProfs_contact.getText()
                + "', matiere = '" + addProfs_matiere.getText()
                + "', classe = '" + addProfs_classe.getText() + "' WHERE id = '"
                + addProfs_ID.getText() + "'";




        try {
            connect = getConnection();
            Alert alert;
            if (addProfs_ID.getText().isEmpty()
                    || addProfs_nom.getText().isEmpty()
                    || addProfs_prenom.getText().isEmpty()
                    || addProfs_matiere.getText().isEmpty()
                    || addProfs_classe.getText().isEmpty()
                    || addProfs_sexe.getSelectionModel().getSelectedItem() == null
                    || addProfs_contact.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP completer tous les champs");
                alert.showAndWait();
            } else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("etes-vous sure pour modifier " + addProfs_ID.getText() + "?");
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
                    addProfsShowListData();
                    // TO CLEAR THE FIELDS
                    addProfsClear();

                } else {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
