package com.example.gestion_college;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class EtudiantModel {
    static Connection connect=null;
    static PreparedStatement prepare=null;
    static ResultSet result=null;
    public static boolean AjouterEtudiant(String cne, String nom, String prenom, String sexe, Date dateNaiss,String niveau,int id_classe,int id_parent){

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
                    || id_classe == 0) {
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
                    prepare = connect.prepareStatement("INSERT INTO etudiant (cne, nom,prenom, sexe, dateNaiss, code_niveau, id_classe,id_parent) VALUES(?,?,?,?,?,?,?,?)");
                    prepare.setString(1, cne);
                    prepare.setString(2, nom);
                    prepare.setString(3, prenom);
                    prepare.setString(4,sexe);
                    prepare.setDate(5, dateNaiss);
                    prepare.setString(6, niveau);
                    prepare.setInt(7, id_classe);
                    prepare.setInt(8, id_parent);

                    if(prepare.executeUpdate()==1){
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Added!");
                        alert.showAndWait();

                        prepare = connect.prepareStatement("Insert into user values(?,?,?)");
                        prepare.setString(1,cne);
                        prepare.setString(2,cne);
                        prepare.setInt(3,0);
                        if(prepare.executeUpdate()==1){
                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Successfully Added as a user");
                            alert.showAndWait();
                            return true;
                        }


                    }





                }
            }
        } catch (Exception e) {
            System.out.println("ECHEC PENDENT L'ADDITION D'UN NEVEAU Etudiant "+e.getMessage());
            return false;
        }
        return false;
    }//AjouterEtudiant

    //UPDATE ETUDIANT

    public static boolean UpdateEtudiant(String cne,String nom,String prenom, String sexe, Date dateNaiss,String niveau,int id_classe){
        try {
            connect = Connexion.getConnection();
            Alert alert;
            if (cne==null
                    || nom==null
                    || prenom == null
                    || niveau == null
                    || id_classe ==0
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
                    prepare = connect.prepareStatement("Update etudiant set nom=?,prenom=?,sexe=?,id_classe=?,code_niveau=?,dateNaiss=? where cne = ?");
                    prepare.setString(1,nom);
                    prepare.setString(2,prenom);
                    prepare.setString(3,sexe);
                    prepare.setInt(4,id_classe);
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


    public static ArrayList<Etudiant> ClasseStudents(int id_classe){
        try{
            connect = Connexion.getConnection();
            assert connect != null;
            PreparedStatement stmt = connect.prepareStatement("Select * from etudiant where id_classe=?");
            stmt.setInt(1,id_classe);
            ArrayList<Etudiant> etudiants=new ArrayList<>();
            ResultSet result = stmt.executeQuery();
            while(result.next()){
                etudiants.add(new Etudiant(result.getString("CNE"),
                        result.getString("nom"),
                        result.getString("prenom"),
                        result.getDate("dateNaiss"),
                        result.getString("sexe"),
                        result.getString("code_niveau"),
                        result.getInt("id_classe"),
                        result.getInt("id_parent")
                        ));
            }
            return etudiants;

        }catch(SQLException e){
            System.out.println("error selection des etudiant de classe"+id_classe+e.getMessage());
        }
        return null;
    }



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

    public static boolean EtudiantSelfUpdate(String cne,String nom,String prenom, String sexe, Date dateNaiss){
        try {
            connect = Connexion.getConnection();
            Alert alert;
            if (cne==null
                    || nom==null
                    || prenom == null
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
                    prepare = connect.prepareStatement("Update etudiant set nom=?,prenom=?,sexe=?,dateNaiss=? where cne = ?");
                    prepare.setString(1,nom);
                    prepare.setString(2,prenom);
                    prepare.setString(3,sexe);
                    prepare.setDate(4,dateNaiss);
                    prepare.setString(5,cne);
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
    }


}//CLASS CLOSE BRACKET
