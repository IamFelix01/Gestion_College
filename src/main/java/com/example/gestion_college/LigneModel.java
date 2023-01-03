package com.example.gestion_college;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LigneModel {
    private static Connection connect=null;
    private static PreparedStatement prepare = null;
    private static Alert alert;
    public static boolean AddLigne(int id,String materiel,int idSalle){
        try {
            connect = Connexion.getConnection();


            if (id==0
                    || materiel==null
                    || idSalle==0
            ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP taper tous les champs");
                alert.showAndWait();
            } else {
//            CHECK IF THE COURSE YOU WANT TO INSERT IS ALREADY EXIST!
                assert connect != null;
                PreparedStatement stmt=connect.prepareStatement("select * from ligne where id=?");
                stmt.setInt(1,id);
                ResultSet result=stmt.executeQuery();

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Ligne: " + id + " est deja ajouté");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement("INSERT INTO ligne VALUES(?,?,?)");
                    prepare.setInt(1, id);
                    prepare.setString(2,materiel);
                    prepare.setInt(3,idSalle);
                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();
                    return true;
                    // TO BECOME UPDATED OUR TABLEVIEW ONCE THE DATA WE GAVE SUCCESSFUL
                    // TO CLEAR THE TEXT FIELDS
                    //availableCourseClear();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }
    public static boolean DeleteLigne(int id ){
        try{
            connect = Connexion.getConnection();
            if (id==0) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP Saisir id");
                alert.showAndWait();
            } else {
                assert connect != null;
                prepare = connect.prepareStatement("delete from ligne where id=?");
                prepare.setInt(1, id);
                int re = prepare.executeUpdate();
                if (re == 1) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();
                    return true;
                }
            }
            return false;
                    // T
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
