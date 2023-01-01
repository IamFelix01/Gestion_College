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
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class EtudiantController implements Initializable {
    public  TextField addStudents_CNE;
    public TableColumn<Etudiant, String> addStudents_col_cne;
    private Stage stage;
    private Scene scene;
    @FXML
    private TableView<Etudiant> addStudents_tableView;



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

//    @FXML
//    private TableColumn<Etudiant, String> addStudents_col_massar;

//    @FXML
//    private TextField addStudents_ID;
    @FXML
    private  TextField addStudents_nom;
    @FXML
    private  TextField addStudents_prenom;
    @FXML
    private  TextField addStudents_classe;
    @FXML
    private TextField addStudents_massar;
    @FXML
    private  DatePicker addStudents_dateNaiss;

    @FXML
    private  ComboBox<String> addStudents_niv;
    @FXML
    private  ComboBox<String> addStudents_sexe;


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

//        String sql = "SELECT * FROM ETUDIANT";


        try {
            Connection connect = Connexion.getConnection();
            Etudiant studentD;
            assert connect != null;
            PreparedStatement prepare = connect.prepareStatement("select * from etudiant");
            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                studentD = new Etudiant(result.getString("cne"),
                        result.getString("nom"),
                        result.getString("prenom"),
                        result.getDate("dateNaiss"),
                        result.getString("sexe"),
                        result.getString("niveau"),
                        result.getString("classe")
                );

                listEtudiant.add(studentD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //addStudentsSearch();
        return listEtudiant;

    }

    private static ObservableList<Etudiant> addStudentsListD ;

    public void addStudentsSearch() {
        System.out.println("add student search called");

        FilteredList<Etudiant> filter = new FilteredList<>(addStudentsListD, e -> true);

        addStudents_search.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(predicateStudentData -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (predicateStudentData.getClasse().contains(searchKey)) {
                    return true;
                } else if (predicateStudentData.getPrenom().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateStudentData.getNom().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateStudentData.getCNE().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateStudentData.getSexe().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateStudentData.getDateNaiss().toString().contains(searchKey)) {
                    return true;
                } else return predicateStudentData.getNiveau().toLowerCase().contains(searchKey);
            });
        });

        SortedList<Etudiant> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(addStudents_tableView.comparatorProperty());
        addStudents_tableView.setItems(sortList);

    }


    public void addStudentsShowListData() {
        addStudentsListD = addStudentListData();
        addStudents_col_cne.setCellValueFactory(new PropertyValueFactory<>("CNE"));
        addStudents_col_firstName.setCellValueFactory(new PropertyValueFactory<>("nom"));
        addStudents_col_lastName.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        addStudents_col_gender.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        addStudents_col_birth.setCellValueFactory(new PropertyValueFactory<>("dateNaiss"));
        addStudents_col_niv.setCellValueFactory(new PropertyValueFactory<>("niveau"));
        addStudents_col_classe.setCellValueFactory(new PropertyValueFactory<>("Classe"));

        addStudents_tableView.setItems(addStudentsListD);

    }

    //add les niveaux au list
    private final String[] niveauList = {"1ERE", "2EME", "3EME"};

    public void addStudentsYearList() {

        List<String> yearL = new ArrayList<>(Arrays.asList(niveauList));

        ObservableList<String> ObList = FXCollections.observableArrayList(yearL);
        addStudents_niv.setItems(ObList);
        addStudentsSearch();

    }

    private final String[] SexeList = {"Homme", "Femme"};

    public void addStudentsSexeList() {

        List<String> SexeL = new ArrayList<>(Arrays.asList(SexeList));

        ObservableList<String> ObList = FXCollections.observableArrayList(SexeL);
        addStudents_sexe.setItems(ObList);

    }


    //ajouter un etudiant selectionné dans les champs pour faire MAJ
    public void addStudentsSelect() {

        Etudiant studentD = addStudents_tableView.getSelectionModel().getSelectedItem();
        int num = addStudents_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        addStudents_CNE.setText(studentD.getCNE());
        addStudents_nom.setText(studentD.getNom());
        addStudents_prenom.setText(studentD.getPrenom());
        addStudents_sexe.setValue(studentD.getSexe());
        addStudents_dateNaiss.setValue(LocalDate.parse(String.valueOf(studentD.getDateNaiss())));
        addStudents_niv.setValue(studentD.getNiveau());
        addStudents_classe.setText(studentD.getClasse());


    }

    public void ajouterEtudiant() {
         String cne = addStudents_CNE.getText();
         String nom = addStudents_nom.getText();
         String prenom = addStudents_prenom.getText();
         String sexe = (String) addStudents_sexe.getSelectionModel().getSelectedItem();
         Date dateNaiss = Date.valueOf(addStudents_dateNaiss.getValue());
         String niveau = (String) addStudents_niv.getSelectionModel().getSelectedItem();
         String classe = addStudents_classe.getText();
       if(EtudiantModel.AjouterEtudiant(cne,nom,prenom,sexe,dateNaiss,niveau,classe)) {
            // TO BECOME UPDATED OUR TABLEVIEW ONCE THE DATA WE GAVE SUCCESSFUL
            addStudentsShowListData();
            // TO CLEAR THE TEXT FIELDS
           //availableCourseClear();
           addStudentsSearch();
        }
       else{
           System.out.println("Couldnt add a new student!!!");
       }

    }

    public void addStudentsClear() {
        addStudents_CNE.setText("");
        addStudents_sexe.getSelectionModel().clearSelection();
        addStudents_niv.getSelectionModel().clearSelection();
        addStudents_prenom.setText("");
        addStudents_nom.setText("");
        addStudents_classe.setText("");
        addStudents_massar.setText("");
        addStudents_dateNaiss.setValue(null);
        addStudentsSearch();
    }

    public void addStudentsUpdate() {
        String cne = addStudents_CNE.getText();
        String nom = addStudents_nom.getText();
        String prenom = addStudents_prenom.getText();
        String sexe = (String) addStudents_sexe.getSelectionModel().getSelectedItem();
        Date dateNaiss = Date.valueOf(addStudents_dateNaiss.getValue());
        String niveau = (String) addStudents_niv.getSelectionModel().getSelectedItem();
        String classe = addStudents_classe.getText();
        if(EtudiantModel.UpdateEtudiant(cne,nom,prenom,sexe,dateNaiss,niveau,classe)){
            // TO UPDATE THE TABLEVIEW
            addStudentsShowListData();
            // TO CLEAR THE FIELDS
            addStudentsClear();
            addStudentsSearch();
        } else {
            System.out.println("Failed To Update ... returns false");
        }


    }

    //DELETE STUDENT
    public void DeleteEtudiant(){
        String cne = addStudents_CNE.getText();
        String nom = addStudents_nom.getText();
        String prenom = addStudents_prenom.getText();
        if(EtudiantModel.DeleteEtudiant(cne,nom,prenom)){
            addStudentsShowListData();
            // TO CLEAR THE FIELDS
            addStudentsClear();
            addStudentsSearch();
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
        addStudentsShowListData();
        addStudentsYearList();
        addStudentsSexeList();
        addStudentsSearch();
        Xbtn.setStyle("-fx-background-color: null;");
        Mbtn.setStyle("-fx-background-color: null;");
    }


}