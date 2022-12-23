package com.example.gestion_college;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DbUtils {
    private static String url="jdbc:mysql://localhost:3306/GestionCollege";
    private static String DBusername = "root";
    private static String DBpwd ="@saidbenchad@123";

    private static int id=2;
    public static void ChangeScene(ActionEvent event,String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(DbUtils.class.getResource(fxmlFile));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    public static void UserRegister(ActionEvent event,String username,String password,String email){
        Connection connection = null;
        PreparedStatement stInsert = null;
        PreparedStatement stCheckUsername = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection(url,DBusername,DBpwd);
            stCheckUsername = connection.prepareStatement("SELECT * from user where username = ? ");
            stCheckUsername.setString(1,username);
            resultSet = stCheckUsername.executeQuery();
            if (resultSet.isBeforeFirst()){
                System.out.println("User already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("This Username already exists, Please try another!");
                alert.show();
            } else {
                stInsert = connection.prepareStatement("INSERT INTO user VALUES(?,?,?,?)");
                id++;
                stInsert.setString(1, String.valueOf(id));
                stInsert.setString(2,username);
                stInsert.setString(3,password);
                stInsert.setString(4,email);
                stInsert.executeUpdate();
                ChangeScene(event,"login.fxml");
//                UserRegisterController.ToLogin(event);
            }

        }catch(SQLException EX){
            EX.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if (resultSet != null){
                try{
                    resultSet.close();
                }catch(SQLException EX){
                    EX.printStackTrace();
                }
            }
            if (stCheckUsername != null){
                try{
                    stCheckUsername.close();
                }catch(SQLException EX){
                    EX.printStackTrace();
                }
            }
            if (stInsert != null){
                try{
                    stInsert.close();
                }catch(SQLException EX){
                    EX.printStackTrace();
                }
            }
            if (connection != null){
                try{
                    connection.close();
                }catch(SQLException EX){
                    EX.printStackTrace();
                }
            }
        }
    }
    public static void UserLogin(ActionEvent event,String username,String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection(url,DBusername,DBpwd);
            preparedStatement=connection.prepareStatement("SELECT password from USER WHERE username=?");
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()){
                System.out.println("User not found in Database!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username or password is incorrect!");
                alert.show();
            }else{
                while(resultSet.next()){
                    String retrievedPassword = resultSet.getString("password");
                    if(retrievedPassword.equals(password)){
                        //Change scene
                        System.out.println("Welcome "+username);
                        ChangeScene(event,"Home.fxml");
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
            if (preparedStatement != null){
                try{
                    preparedStatement.close();
                }catch(SQLException EX){
                    EX.printStackTrace();
                }
            }
            if (connection != null){
                try{
                    connection.close();
                }catch(SQLException EX){
                    EX.printStackTrace();
                }
            }
        }

    }
}
