package com.example.gestion_college;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PipedReader;
import java.sql.*;

public class DbUtils {



    private static Alert alert;

    private static Connection connect = null;
    private static PreparedStatement prepare = null;
    private static PreparedStatement stCheckUsername = null;
    private static ResultSet resultSet = null;
    public static void ChangeScene(ActionEvent event,String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(DbUtils.class.getResource(fxmlFile));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    public static void UserRegister(ActionEvent event,String nom,String prenom,String email,String niveau,int age){
        try{
            connect = Connexion.getConnection();
            assert connect != null;
            stCheckUsername = connect.prepareStatement("SELECT count(*) from etudiant where code_niveau = ? ");
            stCheckUsername.setString(1,niveau);
            resultSet = stCheckUsername.executeQuery();
            resultSet.next();
            if(age>20){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The age must be under 20 years old!");
                alert.show();
            }else{
                    if(resultSet.getInt(1)<45){
                        PreparedStatement stmt = connect.prepareStatement("select count(*) from demandes");
                        ResultSet res = stmt.executeQuery();
                        res.next();
                        int id = res.getInt(1);
                        prepare = connect.prepareStatement("Insert into demandes values(?,?,?,?,?,?)");
                        prepare.setInt(1,id);
                        prepare.setString(2,nom);
                        prepare.setString(3,prenom);
                        prepare.setInt(4,age);
                        prepare.setString(5,niveau);
                        prepare.setString(6,email);
                        int re =prepare.executeUpdate();
                        if(re==1){
                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("INFORMATION");
                            alert.setHeaderText("Bonjour \n "+nom);
                            alert.setContentText("Votre demande expirera dans une semaine.\n" +
                                    "Veuillez donc consulter notre administration et ramener votre dossier le plus rapidement possible pour confirmer votre inscription.");
                            alert.show();
                        }
                    }
            }


        }catch(SQLException EX){
            EX.printStackTrace();
    }
//        finally {
//            if (resultSet != null){
//                try{
//                    resultSet.close();
//                }catch(SQLException EX){
//                    EX.printStackTrace();
//                }
//            }
//            if (stCheckUsername != null){
//                try{
//                    stCheckUsername.close();
//                }catch(SQLException EX){
//                    EX.printStackTrace();
//                }
//            }
//            if (stInsert != null){
//                try{
//                    stInsert.close();
//                }catch(SQLException EX){
//                    EX.printStackTrace();
//                }
//            }
//            if (connection != null){
//                try{
//                    connection.close();
//                }catch(SQLException EX){
//                    EX.printStackTrace();
//                }
//            }
//        }
    }
    public static void UserLogin(ActionEvent event,String username,String password){

        try{
            connect = Connexion.getConnection();
            assert connect != null;
            prepare=connect.prepareStatement("SELECT * from user WHERE username=?");
            prepare.setString(1,username);
            resultSet = prepare.executeQuery();
            if(!resultSet.isBeforeFirst()){
                System.out.println("User not found in Database!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username or password is incorrect!");
                alert.show();
            }else{
                while(resultSet.next()){
                    String retrievedPassword = resultSet.getString("password");
                    int isAdmin = resultSet.getInt("isAdmin");
                    if(retrievedPassword.equals(password)){
                        if(isAdmin==1){
                            System.out.println("Welcome "+username);
                            ChangeScene(event,"Home.fxml");
                        } else{
                            System.out.println("Welcome "+username);
                            ChangeScene(event,"EspaceEtudiant.fxml");
                        }
                        //Change scene

                    }else{
                        System.out.println("Password is incorrect!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Password is incorrect!");
                        alert.show();
                    }

                }

            }
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (resultSet != null){
                try{
                    resultSet.close();
                }catch(SQLException EX){
                    EX.printStackTrace();
                }
            }
            if (prepare != null){
                try{
                    prepare.close();
                }catch(SQLException EX){
                    EX.printStackTrace();
                }
            }
            if (connect != null){
                try{
                    connect.close();
                }catch(SQLException EX){
                    EX.printStackTrace();
                }
            }
        }

    }
    ///////////////Enseignants////////////////////////
}
