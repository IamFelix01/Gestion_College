package com.example.gestion_college;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AbsenceModel {
    private static Connection connect = null;
    private static PreparedStatement prepare=null;
    private static ResultSet result=null;
    private static Alert alert;
    public static boolean addAbsence(int id_absence,int id_cours,String cne,int numsemaine,String justification){
        try{
            connect = Connexion.getConnection();
            if (id_absence==0
                    || justification==null
                    || id_cours==0
                    || numsemaine==0
                    || cne==null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP taper tous les champs");
                alert.showAndWait();
            }else{
                assert connect != null;
                prepare = connect.prepareStatement("Insert into absence values(?,?,?,?,?)");
                prepare.setInt(1,id_absence);
                prepare.setString(2,cne);
                prepare.setInt(3,numsemaine);
                prepare.setInt(4,id_cours);
                prepare.setString(5,justification);
                int re = prepare.executeUpdate();
                if(re==1){
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Ajouter Absence");
                    alert.setContentText("L'absence à été ajouté!!");
                    return true;
                }else{return false;}
            }
        }catch (SQLException E){
            E.printStackTrace();
        }
        return false;
    }
    public static boolean updateAbsence(int id_absence,int id_cours,String cne,int numsemaine,String justification){
        try{
            connect = Connexion.getConnection();
            if (id_absence==0
                    || justification==null
                    || id_cours==0
                    || numsemaine==0
                    || cne==null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP taper tous les champs");
                alert.showAndWait();
            }else{
                assert connect != null;
                prepare = connect.prepareStatement("Update absence set id_cours=?,cne=?,estJustifie=?,num_semaine=? where id_absece=?");
                prepare.setInt(1,id_cours);
                prepare.setString(2,cne);
                prepare.setString(3,justification);
                prepare.setInt(4,numsemaine);
                prepare.setInt(5,id_absence);
                int re = prepare.executeUpdate();
                if(re==1){
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Mise à jour des Absences");
                    alert.setContentText("Les modification sont affecté!!");
                    return true;
                }else{return false;}
            }
        }catch (SQLException E){
            E.printStackTrace();
        }
        return false;
    }
    public static boolean deleteAbsence(int id){
        try{
            connect = Connexion.getConnection();
            if (id==0
                  ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP taper tous les champs");
                alert.showAndWait();
            }else{
                assert connect != null;
                prepare = connect.prepareStatement("Delete from absence where id_absece=?");
                prepare.setInt(1,id);
                int re = prepare.executeUpdate();
                if(re==1){
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Suppression des Absences");
                    alert.setContentText("Les modification sont affecté!!");
                    return true;
                }else{return false;}
            }
        }catch (SQLException E){
            E.printStackTrace();
        }
        return false;
    }
}
