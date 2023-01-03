package com.example.gestion_college;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.*;
import java.util.Optional;

public class EnseignantModel {
    private  static PreparedStatement prepare;
    private static Connection connect;
    //AJOUTER ENSEIGNANT
    public static void AjouterEnseignant(int id_prof,String nom,String prenom,String sexe,int contact,int id_cours,int id_classe){

        String insertData = "INSERT INTO Enseignant (id_prof, nom,prenom, sexe, Contact, id_cours, id_classe) VALUES(?,?,?,?,?,?,?)";
        try {
            connect = Connexion.getConnection();
            Alert alert;

            if (id_prof==0
                    || nom==null
                    || prenom==null
                    || id_cours == 0
                    || id_classe == 0
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
                    prepare.setInt(6, id_cours);
                    prepare.setInt(7, id_classe);

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
    public static boolean UpdateEnseignant(int id_prof,String nom,String prenom,String sexe,int contact,int id_cours,int id_classe){
        try {
            connect = Connexion.getConnection();
            Alert alert;
            if (id_prof==0
                    || nom==null
                    || prenom==null
                    || id_cours == 0
                    || id_classe == 0
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
                    prepare = connect.prepareStatement("Update enseignant set nom=?, prenom=? , id_cours=?, id_classe=?, contact=?, sexe=? where id_prof=?");
                    prepare.setString(1,nom);
                    prepare.setString(2,prenom);
                    prepare.setInt(3,id_cours);
                    prepare.setInt(4,id_classe);
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
    //DELETE ENSEIGNANT
    public static boolean DeleteEnseignant(int id_prof,String nom,String prenom){
        try {
            connect = Connexion.getConnection();
            Alert alert;
            if (id_prof==0){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Remplir le champs de ID SVP!!!");
                alert.showAndWait();
            } else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("etes-vous sure pour Suprimer " + nom +" "+prenom+ "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement("Delete from enseignant where id_prof=?");
                    prepare.setInt(1,id_prof);
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
}
