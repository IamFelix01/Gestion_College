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
import java.util.*;

public class NiveauController implements Initializable {
    public TableColumn<Classe,String> classe_col_classeName;
    public TableColumn<Classe,String> classe_col_NiveauName;
    public TableView<Classe> Classe_Table;
    public TableColumn<Classe,String> classe_col_classeID;
    public TextField classe_name;
    public TextField classe_id;
    public ComboBox<String> combo_code_niv;
    @FXML
    private TableColumn<Niveau,String> niveau_col_code_niveau;
    @FXML
    private TableColumn<Niveau,String> niveau_col_nom;
    @FXML
    private TableView<Niveau> Niveau_tableView;
    @FXML
    private  TextField code_niveau;
    @FXML
    private  TextField niveau_nom;
    private Stage stage;
    private Scene scene;

    @FXML
    private Button Mbtn;

    @FXML
    private Button Xbtn;






    //lister les Niveau de la bdd
    public ObservableList<Niveau> NiveauListData(){
        ObservableList<Niveau> listNiveau = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Niveau";


        try {
            Connection connect = Connexion.getConnection();
            Niveau NiveauD;
            assert connect != null;
            PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                NiveauD = new Niveau(result.getString("code_niveau"),
                        result.getString("nom")
                );

                listNiveau.add(NiveauD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listNiveau;

    }

    public void ShowNiveauData() {
        ObservableList<Niveau> niveauListD = NiveauListData();
        niveau_col_code_niveau.setCellValueFactory(new PropertyValueFactory<>("code_niveau"));
        niveau_col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        Niveau_tableView.setItems(niveauListD);

    }
    //CLASSE
    public ObservableList<Classe> ClasseListData(){
        ObservableList<Classe> listClasse = FXCollections.observableArrayList();




        try {
            Connection connect = Connexion.getConnection();
            Classe ClasseD;
            assert connect != null;
            PreparedStatement prepare = connect.prepareStatement("select * from classe");
            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                ClasseD = new Classe(result.getInt("id_classe"),
                        result.getString("nom_classe"),
                        new Niveau(result.getString("code_niveau"))
                );
                listClasse.add(ClasseD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listClasse;

    }

    public void ShowClasseData() {
        System.out.println("show classe was called");
        ObservableList<Classe> classeListD = ClasseListData();
        classe_col_classeID.setCellValueFactory(new PropertyValueFactory<>("id"));
        classe_col_classeName.setCellValueFactory(new PropertyValueFactory<>("nom"));
        classe_col_NiveauName.setCellValueFactory(new PropertyValueFactory<>("code"));
        Classe_Table.setItems(classeListD);

    }



    //ajouter un Niveau selectionné dans les champs pour faire MAJ
    public void addProfsSelect() {

        Niveau profD = (Niveau) Niveau_tableView.getSelectionModel().getSelectedItem();
        int num = Niveau_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        code_niveau.setText(profD.getCode_niveau());
        niveau_nom.setText(profD.getNom());


    }
    public void ClasseOnSelect() {

        Classe ClasseD = (Classe) Classe_Table.getSelectionModel().getSelectedItem();
        int num = Classe_Table.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        classe_id.setText(String.valueOf(ClasseD.getId()));
        classe_name.setText(ClasseD.getNom());
        combo_code_niv.setValue(ClasseD.getCode());


    }

    public void ajouterNiveau() {
        if(NiveauModel.AjouterNiveau(code_niveau.getText(),niveau_nom.getText())){
            ShowNiveauData();
            NiveausClear();
            ClasseClear();
        }else {
            System.out.println("FAILED TO ADD NIVEAU !!");
        }
    }

    public void NiveausClear() {
        code_niveau.setText("");
        niveau_nom.setText("");

    }
    public void ClasseClear() {
        combo_code_niv.getSelectionModel().clearSelection();
        classe_id.setText("");
        classe_name.setText("");
    }

    public void UpdateNiveau() {
        if(NiveauModel.UpdateNiveau(code_niveau.getText(),niveau_nom.getText())){
            ShowNiveauData();
            NiveausClear();
        }else {System.out.println("FAILED TO UPDATE NIVEAU");}
    }

    public void DeleteNiveau(){
        if(NiveauModel.DeleteNiveau(code_niveau.getText())){
            ShowNiveauData();
            NiveausClear();
            ClasseClear();
        }else System.out.println("FAILED TO Delete NIVEAU");
    }

    //CLASSE
    public void AjouterClasse(){
        int id = Integer.parseInt(classe_id.getText());
        String code = (String) combo_code_niv.getSelectionModel().getSelectedItem();
        if(NiveauModel.AjouterClasse(id,classe_name.getText(),code)){
            ShowClasseData();
            ClasseClear();
        }else{
            System.out.println("FAILED TO ADD CLASSE");
        }
    }
    public void UpdateClasse(){
        int id = Integer.parseInt(classe_id.getText());
        String code = (String) combo_code_niv.getSelectionModel().getSelectedItem();

        if(NiveauModel.UpdateClasse(id,classe_name.getText(),code)){
            ShowClasseData();
            ClasseClear();
        }else{
            System.out.println("FAILED TO update CLASSE");
        }
    }
    public void DeleteClasse(){
        int id = Integer.parseInt(classe_id.getText());
        if(NiveauModel.DeleteClasse(id)){
            ShowClasseData();
            ClasseClear();
        }else{
            System.out.println("FAILED TO update CLASSE");
        }
    }

    private final String[] niveauList = {"1ERE", "2EME", "3EME"};

    public void NiveauList() {

        List<String> nivList = new ArrayList<>(Arrays.asList(niveauList));

        ObservableList<String> ObList = FXCollections.observableArrayList(nivList);
        combo_code_niv.setItems(ObList);
//        addStudentsSearch();

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
        ShowNiveauData();
        NiveausClear();
        ShowClasseData();
        ClasseClear();
        NiveauList();
        //addProfsYearList();
        //addProfsSexeList();
        Xbtn.setStyle("-fx-background-color: null;");
        Mbtn.setStyle("-fx-background-color: null;");
    }
}