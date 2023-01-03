package com.example.gestion_college;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.Date;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class InfosEtudiantController implements Initializable {
    public TextField addStudents_cne;
    public TextField addStudents_niv;
    public TextField addStudents_ClasseName;
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
    private TextField addStudents_nom;
    @FXML
    private TextField addStudents_prenom;
    //    @FXML
//    private TextField addStudents_classe;
    @FXML
    private TextField addStudents_massar;
    @FXML
    private DatePicker addStudents_dateNaiss;

    //    @FXML
//    private ComboBox<String> addStudents_niv;
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
    private static String classeName;
    //lister les etudiant de la bdd
    public Etudiant addStudentListData(){
        Etudiant studentD = null;

        try {
            Connection connect = Connexion.getConnection();

            assert connect != null;
            String cne = "K184900";
            PreparedStatement prepare = connect.prepareStatement("SELECT * FROM etudiant where cne = ?");
            prepare.setString(1,cne);
            ResultSet result = prepare.executeQuery();


            while (result.next()) {
                PreparedStatement stmt = connect.prepareStatement("Select nom_classe from classe where id_classe=?");
                stmt.setInt(1,result.getInt(6));
                ResultSet res = stmt.executeQuery();
                res.next();
                classeName = res.getString("nom_classe");
                studentD = new Etudiant(cne,
                        result.getString("nom"),
                        result.getString("prenom"),
                        result.getDate("dateNaiss"),
                        result.getString("sexe"),
                        result.getString("code_niveau"),
                        result.getInt("id_classe"),
                        result.getInt("id_parent")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //
        return studentD;

    }


    private ObservableList<Etudiant> addStudentsListD;

    public void addStudentsShowListData() {
        Etudiant studentD = addStudentListData();

        addStudents_cne.setText(studentD.getCNE());
        addStudents_nom.setText(studentD.getNom());
        addStudents_prenom.setText(studentD.getPrenom());
        //addStudents_classe.setText(studentD.getClasse());
        addStudents_ClasseName.setText(classeName);
        addStudents_sexe.setValue(studentD.getSexe());
        addStudents_niv.setText(studentD.getCode_niveau());
        //addStudents_niv.setValue(studentD.getNiveau());
        addStudents_dateNaiss.setValue(LocalDate.parse(String.valueOf(studentD.getDateNaiss())));


    }

    //add les niveaux au list
//    private String[] niveauList = {"1ERE", "2EME", "3EME"};
//
//    public void addStudentsYearList() {
//
//        List<String> yearL = new ArrayList<>();
//
//        for (String data : niveauList) {
//            yearL.add(data);
//        }
//
//        ObservableList ObList = FXCollections.observableArrayList(yearL);
//        addStudents_niv.setItems(ObList);
//
//
//    }


    public void addStudentsSexeList() {
        String[] SexeList = {"Homme", "Femme"};
        List<String> SexeL = new ArrayList<>();

        Collections.addAll(SexeL, SexeList);
        ObservableList<String> ObList = FXCollections.observableArrayList(SexeL);
        addStudents_sexe.setItems(ObList);

    }


    //ajouter un etudiant selectionné dans les champs pour faire MAJ
    public void addStudentsSelect() {




    }


    public void addStudentsClear() {
        addStudents_sexe.getSelectionModel().clearSelection();
        //addStudents_niv.getSelectionModel().clearSelection();
        addStudents_prenom.setText("");
        addStudents_nom.setText("");
        //addStudents_classe.setText("");
        addStudents_dateNaiss.setValue(null);

    }

    public void addStudentsUpdate() {
        Date dateNaiss = Date.valueOf(addStudents_dateNaiss.getValue());
        if(EtudiantModel.EtudiantSelfUpdate(addStudents_cne.getText(),addStudents_nom.getText(),addStudents_prenom.getText(),addStudents_sexe.getSelectionModel().getSelectedItem(),dateNaiss)){
            addStudentsShowListData();
            //addStudentsYearList();
            addStudentsSexeList();
        }
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
        //addStudentsYearList();
        addStudentsSexeList();

        Xbtn.setStyle("-fx-background-color: null;");
        Mbtn.setStyle("-fx-background-color: null;");
    }


}
