package com.example.gestion_college;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NiveauModel {
    private static Connection connect = null;
    private static PreparedStatement stmt = null;
    private static ResultSet result = null;
    private static Alert alert = null;
    public static boolean AjouterNiveau(String code_niveau,String nom){
        try{
            connect = Connexion.getConnection();

            if (code_niveau==null
                    || nom==null
                    ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP taper tous les champs");
                alert.showAndWait();
            } else {
//            CHECK IF THE COURSE YOU WANT TO INSERT IS ALREADY EXIST!
                assert connect != null;
                stmt = connect.prepareStatement("Select * from Niveau where code_niveau=?");
                stmt.setString(1, code_niveau);
                result = stmt.executeQuery();
                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Niveau: " + code_niveau + " est deja ajouté");
                    alert.showAndWait();
                } else {
                    stmt = connect.prepareStatement("Insert into Niveau Values(?,?)");
                    stmt.setString(1,code_niveau);
                    stmt.setString(2,nom);
                    int result = stmt.executeUpdate();
                    if(result==1){
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Added!");
                        alert.showAndWait();
                        return true;
                    }
                    // TO BECOME UPDATED OUR TABLEVIEW ONCE THE DATA WE GAVE SUCCESSFUL

                    // TO CLEAR THE TEXT FIELDS
                    //availableCourseClear();

                }
            }

        }catch(SQLException e){
            System.out.println("Error pendent l insertion de niveau !"+e.getMessage());
        }
        return false;
    }
    //CLASSE
    public static boolean AjouterClasse(int id_classe,String nom,String code_niveau){
        try{
            connect = Connexion.getConnection();

            if (id_classe==0||code_niveau==null
                    || nom==null
            ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP taper tous les champs");
                alert.showAndWait();
            } else {
//            CHECK IF THE COURSE YOU WANT TO INSERT IS ALREADY EXIST!
                assert connect != null;
                stmt = connect.prepareStatement("Select * from classe where id_classe=?");
                stmt.setInt(1, id_classe);
                result = stmt.executeQuery();
                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Niveau: " + nom + " est deja ajouté");
                    alert.showAndWait();
                } else {
                    stmt = connect.prepareStatement("Insert into classe Values(?,?,?)");
                    stmt.setInt(1,id_classe);
                    stmt.setString(2,nom);
                    stmt.setString(3,code_niveau);
                    int result = stmt.executeUpdate();
                    if(result==1){
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Added!");
                        alert.showAndWait();
                        return true;
                    }
                    // TO BECOME UPDATED OUR TABLEVIEW ONCE THE DATA WE GAVE SUCCESSFUL

                    // TO CLEAR THE TEXT FIELDS
                    //availableCourseClear();

                }
            }

        }catch(SQLException e){
            System.out.println("Error pendent l insertion de CLASSE !"+e.getMessage());
        }
        return false;
    }//AJOUTER CLASSE
    public static boolean UpdateNiveau(String code_niveau,String nom){
        try{
            connect = Connexion.getConnection();
            if (code_niveau==null
                    || nom==null
            ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP taper tous les champs");
                alert.showAndWait();
            } else {
                assert connect != null;
                stmt = connect.prepareStatement("Update niveau set nom=? where code_niveau=? ");
                stmt.setString(1,nom);
                stmt.setString(2,code_niveau);
                int re = stmt.executeUpdate();
                if(re == 1){
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();
                    return true;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public static boolean UpdateClasse(int id_classe,String nom_classe,String code_niveau){
        try{
            connect = Connexion.getConnection();
            if (id_classe==0||code_niveau==null
                    || nom_classe==null
            ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP taper tous les champs");
                alert.showAndWait();
            } else {
                assert connect != null;
                stmt = connect.prepareStatement("Update classe set nom_classe=?, code_niveau=? where id_classe=?");
                stmt.setString(1,nom_classe);
                stmt.setString(2,code_niveau);
                stmt.setInt(3,id_classe);
                int re = stmt.executeUpdate();
                if(re == 1){
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();
                    return true;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public static boolean DeleteNiveau(String code_niveau){
        try{
            connect = Connexion.getConnection();
            if (code_niveau==null
            ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP Remplir le champs Code Niveau");
                alert.showAndWait();
            } else {
                assert connect != null;
                stmt = connect.prepareStatement("Delete from niveau where code_niveau=? ");
                stmt.setString(1,code_niveau);
                int re = stmt.executeUpdate();
                if(re == 1){
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();
                    return true;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public static boolean DeleteClasse(int id_classe){
        try{
            connect = Connexion.getConnection();
            if (id_classe==0
            ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP Remplir le champs Code Niveau");
                alert.showAndWait();
            } else {
                assert connect != null;
                stmt = connect.prepareStatement("Delete from classe where id_classe=? ");
                stmt.setInt(1,id_classe);
                int re = stmt.executeUpdate();
                if(re == 1){
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();
                    return true;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
