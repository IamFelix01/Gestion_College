package com.example.gestion_college;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class EtudiantModel {
    static Connection connect=null;
    static PreparedStatement prepare=null;
    static ResultSet result=null;
    public static boolean AjouterEtudiant(String cne, String nom, String prenom, String sexe, Date dateNaiss,String niveau,String classe){

//        try{
//            conn = Connexion.getConnection();
//
//        }catch(SQLException e ){
//            System.out.println("ECHEC PENDENT L'ADDITION D'UN NEVEAU ENSEIGNANT "+e.getMessage());
//        }
        try {
            connect = Connexion.getConnection();
            Alert alert;

            if (cne==null
                    || nom==null
                    || prenom==null
                    || dateNaiss == null
                    || niveau == null
                    || sexe == null
                    || classe == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP taper tous les champs");
                alert.showAndWait();
            } else {
//            CHECK IF THE COURSE YOU WANT TO INSERT IS ALREADY EXIST!
                assert connect != null;
                prepare = connect.prepareStatement("Select * from Etudiant where cne=?");
                prepare.setString(1, cne);
                result = prepare.executeQuery();
                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Etudiant: " + nom +" "+prenom+ " est deja ajouté");
                    alert.showAndWait();
                    return false;
                } else {
                    prepare = connect.prepareStatement("INSERT INTO etudiant (cne, nom,prenom, sexe, dateNaiss, niveau, classe) VALUES(?,?,?,?,?,?,?)");
                    prepare.setString(1, cne);
                    prepare.setString(2, nom);
                    prepare.setString(3, prenom);
                    prepare.setString(4,sexe);
                    prepare.setDate(5, dateNaiss);
                    prepare.setString(6, niveau);
                    prepare.setString(7, classe);
                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();
                    return true;

                }
            }
        } catch (Exception e) {
            System.out.println("ECHEC PENDENT L'ADDITION D'UN NEVEAU Etudiant "+e.getMessage());
            return false;
        }
        return false;
    }//AjouterEtudiant

    //UPDATE ETUDIANT

    public static boolean UpdateEtudiant(String cne,String nom,String prenom, String sexe, Date dateNaiss,String niveau,String classe){
        try {
            connect = Connexion.getConnection();
            Alert alert;
            if (cne==null
                    || nom==null
                    || prenom == null
                    || niveau == null
                    || classe == null
                    || sexe == null
                    || dateNaiss == null){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP completer tous les champs");
                alert.showAndWait();
            } else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("etes-vous sure pour modifier " + nom +" "+prenom+ "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement("Update etudiant set nom=?,prenom=?,sexe=?,classe=?,niveau=?,dateNaiss=? where cne = ?");
                    prepare.setString(1,nom);
                    prepare.setString(2,prenom);
                    prepare.setString(3,sexe);
                    prepare.setString(4,classe);
                    prepare.setString(5,niveau);
                    prepare.setDate(6,dateNaiss);
                    prepare.setString(7,cne);
                    int resultset = prepare.executeUpdate();
                    if(resultset == 1) {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Updated!");
                        alert.showAndWait();
                        return true;
                    }


                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }//Update Etudiant


    //DELETE STUDENT
    public static boolean DeleteEtudiant(String cne,String nom,String prenom){
        try {
            connect = Connexion.getConnection();
            Alert alert;
            if (cne==null){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Remplir le champs de CNE SVP!!!");
                alert.showAndWait();
            } else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("etes-vous sure pour Suprimer " + nom +" "+prenom+ "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement("Delete from etudiant where cne=?");
                    prepare.setString(1,cne);
                    int resultset = prepare.executeUpdate();
                    if(resultset == 1) {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Deleted!");
                        alert.showAndWait();
                        return true;
                    }


                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}//CLASS CLOSE BRACKET
