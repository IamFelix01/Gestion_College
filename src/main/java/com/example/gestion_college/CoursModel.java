package com.example.gestion_college;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoursModel {
    private static Connection connect;
    private static PreparedStatement prepare;
    private static Alert alert;
    public static boolean AddCours(int id,String nom,int heurDebut,int heurFin,int id_classe,int id_salle,int jour){


        try {
            connect = Connexion.getConnection();


            if (id==0
                    || nom==null
                    || heurDebut==0
                    || heurFin==0
                    ||id_classe ==0
                    || id_salle==0
            || jour==0) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP taper tous les champs");
                alert.showAndWait();
            } else {
//            CHECK IF THE COURSE YOU WANT TO INSERT IS ALREADY EXIST!
                assert connect != null;
                prepare = connect.prepareStatement("select * from cours where id_cours=?");
                prepare.setInt(1,id);
                ResultSet result = prepare.executeQuery();
                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cours: " + nom + " est deja ajouté");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement("INSERT INTO cours VALUES(?,?,?,?,?,?,?)");
                    prepare.setInt(1, id);
                    prepare.setString(2, nom);
                    prepare.setInt(3, heurDebut);
                    prepare.setInt(4, heurFin);
                    prepare.setInt(5, id_classe);
                    prepare.setInt(6, id_salle);
                    prepare.setInt(7, jour);

                    int re = prepare.executeUpdate();
                    if(re==1){
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean UpdateCours(int id,String nom,int heurDebut,int heurFin,int jour){


        try {
            connect = Connexion.getConnection();
            if (id==0
                    || nom==null
                    || heurDebut==0
                    || heurFin==0
                    ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP taper tous les champs");
                alert.showAndWait();
            }
            assert connect != null;
            prepare = connect.prepareStatement("Update cours set nom=?,heurDebut=?, heurFin=?, jour=? where id_cours = ?");
            prepare.setString(1, nom);
            prepare.setInt(2, heurDebut);
            prepare.setInt(3, heurFin);
            prepare.setInt(4, jour);
            prepare.setInt(5, id);
            int re = prepare.executeUpdate();
            if(re==1){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Updated!");
                alert.showAndWait();
                return true;
                    }
            else {return false;}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean DeleteCours(int id){
        String sql = "Delete from cours where id_cours = ?";
        try{
            connect = Connexion.getConnection();
            if (id==0
            ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("SVP le champs id est vide");
                alert.showAndWait();
            }
            assert connect != null;
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1,id);
            int re = prepare.executeUpdate();
            if(re == 1){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Deleted!");
                alert.showAndWait();
                return true;
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
