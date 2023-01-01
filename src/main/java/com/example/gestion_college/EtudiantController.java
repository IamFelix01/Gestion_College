package com.example.gestion_college;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class EtudiantController implements Initializable {
    private Stage stage;
    private Scene scene;
    @FXML
    private TableView<Etudiant> addStudents_tableView;

    @FXML
    private TableColumn<Etudiant, String> addStudents_col_studentNum;

    @FXML
    private TableColumn<Etudiant, String> addStudents_col_classe;

    @FXML
    private TableColumn<Etudiant, String> addStudents_col_niv;

    @FXML
    private TableColumn<Etudiant, String> addStudents_col_firstName;

    @FXML
    private TableColumn<Etudiant, String> addStudents_col_lastName;

    @FXML
    private TextField addStudents_search;

    @FXML
    private TableColumn<Etudiant, String> addStudents_col_gender;

    @FXML
    private TableColumn<Etudiant, String> addStudents_col_birth;

    @FXML
    private TableColumn<Etudiant, String> addStudents_col_massar;

    @FXML
    private TextField addStudents_ID;
    @FXML
    private TextField addStudents_nom;
    @FXML
    private TextField addStudents_prenom;
    @FXML
    private TextField addStudents_classe;
    @FXML
    private TextField addStudents_massar;
    @FXML
    private DatePicker addStudents_dateNaiss;

    @FXML
    private ComboBox<String> addStudents_niv;
    @FXML
    private ComboBox<String> addStudents_sexe;


    @FXML
    Button Xbtn;
    @FXML
    Button Mbtn;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    //lister les etudiant de la bdd
    public ObservableList<Etudiant> addStudentListData(){
        ObservableList<Etudiant> listEtudiant = FXCollections.observableArrayList();

        String sql = "SELECT * FROM ETUDIANT";


        try {
            Connection connect = getConnection();
            Etudiant studentD;
            PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                studentD = new Etudiant(result.getInt("id"),
                        result.getString("nom"),
                        result.getString("prenom"),
                        result.getDate("dateNaiss"),
                        result.getString("sexe"),
                        result.getString("niveau"),
                        result.getString("Classe"),
                        result.getString("massar")
                        );

                listEtudiant.add(studentD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //
        return listEtudiant;

    }

    public void Search(){
        String query = addStudents_search.getText().toLowerCase();
        addStudents_tableView.setItems(addStudentsListD.filtered(person -> person.getNom().toLowerCase().contains(query)
                || person.getNiveau().toLowerCase().contains(query)
                || person.getPrenom().toLowerCase().contains(query)
                || person.getSexe().toLowerCase().contains(query)
                || person.getMassar().toLowerCase().contains(query)
                || person.getClasse().toLowerCase().contains(query)
                || String.valueOf(person.getId()).contains(query)
                || person.getDateNaiss().toString().contains(query)
                ));
    }

    private ObservableList<Etudiant> addStudentsListD;

    public void addStudentsShowListData() {
        addStudentsListD = addStudentListData();

        addStudents_col_studentNum.setCellValueFactory(new PropertyValueFactory<>("id"));
        addStudents_col_firstName.setCellValueFactory(new PropertyValueFactory<>("nom"));
        addStudents_col_lastName.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        addStudents_col_gender.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        addStudents_col_birth.setCellValueFactory(new PropertyValueFactory<>("dateNaiss"));
        addStudents_col_niv.setCellValueFactory(new PropertyValueFactory<>("niveau"));
        addStudents_col_classe.setCellValueFactory(new PropertyValueFactory<>("Classe"));
        addStudents_col_massar.setCellValueFactory(new PropertyValueFactory<>("massar"));

        addStudents_tableView.setItems(addStudentsListD);

    }

    //add les niveaux au list
    private String[] niveauList = {"1ERE", "2EME", "3EME"};

    public void addStudentsYearList() {

        List<String> yearL = new ArrayList<>();

        for (String data : niveauList) {
            yearL.add(data);
        }

        ObservableList ObList = FXCollections.observableArrayList(yearL);
        addStudents_niv.setItems(ObList);
        

    }

    private String[] SexeList = {"Mâle", "Femelle"};

    public void addStudentsSexeList() {

        List<String> SexeL = new ArrayList<>();

        for (String data : SexeList) {
            SexeL.add(data);
        }

        ObservableList ObList = FXCollections.observableArrayList(SexeL);
        addStudents_sexe.setItems(ObList);

    }


    //ajouter un etudiant selectionné dans les champs pour faire MAJ
    public void addStudentsSelect() {

        Etudiant studentD = addStudents_tableView.getSelectionModel().getSelectedItem();
        int num = addStudents_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        addStudents_ID.setText(String.valueOf(studentD.getId()));
        addStudents_nom.setText(studentD.getNom());
        addStudents_prenom.setText(studentD.getPrenom());
        addStudents_classe.setText(studentD.getClasse());
        addStudents_massar.setText(studentD.getMassar());
        addStudents_sexe.setValue(studentD.getSexe());
        addStudents_niv.setValue(studentD.getNiveau());
        addStudents_dateNaiss.setValue(LocalDate.parse(String.valueOf(studentD.getDateNaiss())));
        


    }

    public void ajouterEtudiant() {

        String insertData = "INSERT INTO etudiant (id, nom,prenom, sexe, dateNaiss, niveau, Classe,massar) VALUES(?,?,?,?,?,?,?,?)";

        try {
            connect = getConnection();
            Alert alert;

            if (addStudents_ID.getText().isEmpty()
                    || addStudents_nom.getText().isEmpty()
                    || addStudents_prenom.getText().isEmpty()
                    || addStudents_niv.getSelectionModel().getSelectedItem() == null
                    || addStudents_classe.getText().isEmpty()
                    || addStudents_sexe.getSelectionModel().getSelectedItem() == null
                    || addStudents_dateNaiss.getValue() == null
                    || addStudents_massar.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP taper tous les champs");
                alert.showAndWait();
            } else {
//            CHECK IF THE COURSE YOU WANT TO INSERT IS ALREADY EXIST!
                String checkData = "SELECT id FROM etudiant WHERE id = '"
                        + addStudents_ID.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Etudiant: " + addStudents_ID.getText() + " est deja ajouté");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, addStudents_ID.getText());
                    prepare.setString(2, addStudents_nom.getText());
                    prepare.setString(3, addStudents_prenom.getText());
                    prepare.setString(4, (String) addStudents_sexe.getSelectionModel().getSelectedItem());
                    prepare.setString(5, String.valueOf(addStudents_dateNaiss.getValue()));
                    prepare.setString(6, (String) addStudents_niv.getSelectionModel().getSelectedItem());
                    prepare.setString(7, addStudents_classe.getText());
                    prepare.setString(8, addStudents_massar.getText());

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    // TO BECOME UPDATED OUR TABLEVIEW ONCE THE DATA WE GAVE SUCCESSFUL
                    addStudentsShowListData();
                    // TO CLEAR THE TEXT FIELDS
                    //availableCourseClear();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void addStudentsClear() {
        addStudents_ID.setText("");
        addStudents_sexe.getSelectionModel().clearSelection();
        addStudents_niv.getSelectionModel().clearSelection();
        addStudents_prenom.setText("");
        addStudents_nom.setText("");
        addStudents_classe.setText("");
        addStudents_massar.setText("");
        addStudents_dateNaiss.setValue(null);
        
    }

    public void addStudentsUpdate() {

        String updateData = "UPDATE etudiant SET "
                + "id = '" + addStudents_ID.getText()
                + "', nom = '" + addStudents_nom.getText()
                + "', prenom = '" + addStudents_prenom.getText()
                + "', sexe = '" + addStudents_sexe.getSelectionModel().getSelectedItem()
                + "', dateNaiss = '" + addStudents_dateNaiss.getValue()
                + "', niveau = '" + addStudents_niv.getSelectionModel().getSelectedItem()
                + "', classe = '" + addStudents_classe.getText()
                + "', massar = '" + addStudents_massar.getText() + "' WHERE id = '"
                + addStudents_ID.getText() + "'";




        try {
            connect = getConnection();
            Alert alert;
            if (addStudents_ID.getText().isEmpty()
                    || addStudents_nom.getText().isEmpty()
                    || addStudents_prenom.getText().isEmpty()
                    || addStudents_niv.getSelectionModel().getSelectedItem() == null
                    || addStudents_classe.getText().isEmpty()
                    || addStudents_sexe.getSelectionModel().getSelectedItem() == null
                    || addStudents_dateNaiss.getValue() == null
                    || addStudents_massar.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP completer tous les champs");
                alert.showAndWait();
            } else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("etes-vous sure pour modifier " + addStudents_ID.getText() + "?");
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
                    addStudentsShowListData();
                    // TO CLEAR THE FIELDS
                    addStudentsClear();

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
        Stage stage = (Stage) Mbtn.getScene().getWindow();
        stage.close();
    }

    public void minimize(ActionEvent e){
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addStudentsShowListData();
        addStudentsYearList();
        addStudentsSexeList();
        
        Xbtn.setStyle("-fx-background-color: null;");
        Mbtn.setStyle("-fx-background-color: null;");
    }


}
