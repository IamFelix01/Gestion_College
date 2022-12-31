package com.example.gestion_college;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EtudiantModel {
    static Connection connect=null;
    static PreparedStatement prepare=null;
    static ResultSet result=null;
    public static boolean AjouterEtudiant(String cne, String nom, String prenom, String sexe, Date dateNaiss,String niveau,String classe){
        String insertData = "INSERT INTO etudiant (cne, nom,prenom, sexe, dateNaiss, niveau, classe) VALUES(?,?,?,?,?,?,?)";
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
                    prepare = connect.prepareStatement(insertData);
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

}//CLASS CLOSE BRACKET
