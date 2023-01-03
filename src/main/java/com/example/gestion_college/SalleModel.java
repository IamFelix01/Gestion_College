package com.example.gestion_college;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SalleModel {
    private static Connection connect;
    private static PreparedStatement prepare;
    private static Alert alert;
    public static boolean AddSalle(int id,String type){
        try{
            connect = Connexion.getConnection();
            if(id==0||type==null){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP taper tous les champs");
                alert.showAndWait();
            }else{
                assert connect != null;
                prepare = connect.prepareStatement("insert into salle values(?,?)");
                prepare.setInt(1,id);
                prepare.setString(2,type);
                int re = prepare.executeUpdate();
                if(re==1){
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();
                    return true;
                }
                return false;

            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public static boolean UpdateSalle(int id,String type){
        try{
            connect = Connexion.getConnection();
            if(id==0||type==null){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP taper tous les champs");
                alert.showAndWait();
            }else{
                assert connect != null;
                prepare = connect.prepareStatement("update salle set type=? where id_salle=?");
                prepare.setInt(1,id);
                int re = prepare.executeUpdate();
                if(re==1){
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();
                    return true;
                }
                return false;

            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public static boolean DeleteSalle(int id){
        try{
            connect = Connexion.getConnection();
            if(id==0){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP taper tous les champs");
                alert.showAndWait();
            }else{
                assert connect != null;
                prepare = connect.prepareStatement("Delete from salle where id_salle=?");
                prepare.setInt(1,id);
                int re = prepare.executeUpdate();
                if(re==1){
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();
                    return true;
                }
                return false;

            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
