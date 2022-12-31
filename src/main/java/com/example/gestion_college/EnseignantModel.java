package com.example.gestion_college;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.*;
import java.util.Optional;

public class EnseignantModel {
    private  static PreparedStatement prepare;
    private static Connection connect;
    //AJOUTER ENSEIGNANT
    public static void AjouterEnseignant(int id_prof,String nom,String prenom,String sexe,int contact,String code_cours,String code_niveau){

        String insertData = "INSERT INTO Enseignant (id_prof, nom,prenom, sexe, Contact, code_cours, code_niveau) VALUES(?,?,?,?,?,?,?)";
//        try{
//            conn = Connexion.getConnection();
//
//        }catch(SQLException e ){
//            System.out.println("ECHEC PENDENT L'ADDITION D'UN NEVEAU ENSEIGNANT "+e.getMessage());
//        }
        try {
            connect = Connexion.getConnection();
            Alert alert;

            if (id_prof==0
                    || nom==null
                    || prenom==null
                    || code_cours == null
                    || code_niveau == null
                    || sexe == null
                    || contact == 0) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP taper tous les champs");
                alert.showAndWait();
            } else {
//            CHECK IF THE COURSE YOU WANT TO INSERT IS ALREADY EXIST!
                assert connect != null;
                prepare = connect.prepareStatement("Select * from Enseignant where id_prof=?");
                prepare.setInt(1, id_prof);
                ResultSet result = prepare.executeQuery();
                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Enseignant: " + id_prof + " est deja ajouté");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(insertData);
                    prepare.setInt(1, id_prof);
                    prepare.setString(2, nom);
                    prepare.setString(3, prenom);
                    prepare.setString(4,sexe);
                    prepare.setInt(5, contact);
                    prepare.setString(6, code_cours);
                    prepare.setString(7, code_niveau);

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    // TO BECOME UPDATED OUR TABLEVIEW ONCE THE DATA WE GAVE SUCCESSFUL

                    // TO CLEAR THE TEXT FIELDS
                    //availableCourseClear();

                }
            }
        } catch (Exception e) {
            System.out.println("ECHEC PENDENT L'ADDITION D'UN NEVEAU ENSEIGNANT "+e.getMessage());

        }

    }
    //UPDATE ENSEIGNANT
    public static boolean UpdateEnseignant(int id_prof,String nom,String prenom,String sexe,int contact,String code_cours,String code_niveau){
        try {
            connect = Connexion.getConnection();
            Alert alert;
            if (id_prof==0
                    || nom==null
                    || prenom==null
                    || code_cours == null
                    || code_niveau == null
                    || sexe == null
                    || contact == 0){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP completer tous les champs");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Confirmer les modifications?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.isPresent() && option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement("Update enseignant set nom=?, prenom=? , code_cours=?, code_niveau=?, contact=?, sexe=? where id_prof=?");
                    prepare.setString(1,nom);
                    prepare.setString(2,prenom);
                    prepare.setString(3,code_cours);
                    prepare.setString(4,code_niveau);
                    prepare.setInt( 5,contact);
                    prepare.setString( 6,sexe);
                    prepare.setInt( 7,id_prof);
                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println("Echec pendent le mise a jour d'Enseignant!: "+e.getMessage());
        }
        return false;
    }
}
