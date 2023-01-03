package com.example.gestion_college;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AbsenceController implements Initializable {

    public TableView<Absence> absence_tableView;


    public TableColumn<Absence,String> absence_col_justification;
    public Button Xbtn;
    public Button Mbtn;
    public TableColumn<Absence,Integer> Absence_col_id;

    public TableColumn<Absence,String> Absence_col_cne;

    public TableColumn<Absence,Integer> Absence_col_idcours;
    public TextField absence_id;
    public TextField absence_numsemaine;
    public TextField absence_idcours;
    public ComboBox<String> absence_justification;
    public TextField absence_search;
    public TextField absence_cne;
    public TableColumn<Absence,Integer> Absence_col_semaine;
    public TableColumn<String,String> absence_totale;
    private Stage stage;
    private static ObservableList<Absence> absenceListD ;


    public void addAbsence(){
        int id = Integer.parseInt(absence_id.getText());
        int id_cours = Integer.parseInt(absence_idcours.getText());
        int numS = Integer.parseInt(absence_numsemaine.getText());
        String cne = absence_cne.getText();
        String justify = absence_justification.getSelectionModel().getSelectedItem();
        if(AbsenceModel.addAbsence(id,id_cours,cne,numS,justify)){
            AbsenceShowListD();
            clear();
        }
    }

    public void updateAbsence(){
        int id = Integer.parseInt(absence_id.getText());
        int id_cours = Integer.parseInt(absence_idcours.getText());
        int numS = Integer.parseInt(absence_numsemaine.getText());
        String cne = absence_cne.getText();
        String justify = absence_justification.getSelectionModel().getSelectedItem();
        if(AbsenceModel.updateAbsence(id,id_cours,cne,numS,justify)){
            AbsenceShowListD();
            clear();
        }
    }

    public void deleteAbsence(){
        int id = Integer.parseInt(absence_id.getText());

        if(AbsenceModel.deleteAbsence(id)){
            clear();
            AbsenceShowListD();
        }
    }





    public void AbsenceSelect() {

        Absence absenceD = (Absence) absence_tableView.getSelectionModel().getSelectedItem();
        int num = absence_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }
        absence_id.setText(String.valueOf(absenceD.getId_absence()));
        absence_idcours.setText(String.valueOf(absenceD.getId_cours()));
        absence_numsemaine.setText(String.valueOf(absenceD.getNum_semaine()));
        absence_justification.setValue(String.valueOf(absenceD.getEstJustifie()));
        absence_cne.setText(absenceD.getCne_etudiant());
    }

    public void absenceJustification() {
        String[] justify = {"OUI", "NON"};
        List<String> Justification = new ArrayList<>(Arrays.asList(justify));
        ObservableList<String> ObList = FXCollections.observableArrayList(Justification);
        absence_justification.setItems(ObList);

    }
    public ObservableList<Absence> AbsenceListD(){
        ObservableList<Absence> listAbsence = FXCollections.observableArrayList();

//        String sql = "SELECT * FROM ETUDIANT";


        try {
            Connection connect = Connexion.getConnection();
            Absence absenceD;
            assert connect != null;
            PreparedStatement prepare = connect.prepareStatement("select * from absence");
            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                absenceD = new Absence(result.getInt("id_absence"),
                        result.getInt("num_semaine"),
                        result.getString("CNE"),
                        result.getInt("id_cours"),
                        result.getString("estJustifie")
                );

                listAbsence.add(absenceD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //addStudentsSearch();
        return listAbsence;

    }
    public void AbsenceShowListD() {
        absenceListD = AbsenceListD();
        Absence_col_id.setCellValueFactory(new PropertyValueFactory<>("id_absence"));
        Absence_col_cne.setCellValueFactory(new PropertyValueFactory<>("cne_etudiant"));
        Absence_col_idcours.setCellValueFactory(new PropertyValueFactory<>("id_cours"));
        absence_col_justification.setCellValueFactory(new PropertyValueFactory<>("estJustifie"));
        Absence_col_semaine.setCellValueFactory(new PropertyValueFactory<>("num_semaine"));


        absence_tableView.setItems(absenceListD);

    }
    public void clear() {
        absence_id.setText("");
        absence_justification.getSelectionModel().clearSelection();
        absence_numsemaine.setText("");
        absence_idcours.setText("");
        absence_cne.setText("");
    }

    public void Search(){
        String query = absence_search.getText().toLowerCase();
        absence_tableView.setItems(absenceListD.filtered(absence -> absence.getCne_etudiant().toLowerCase().contains(query)
                || absence.getEstJustifie().toLowerCase().contains(query)
        ));
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
    public void initialize(URL location, ResourceBundle resources) {
        absenceJustification();
        AbsenceShowListD();
    }
}
