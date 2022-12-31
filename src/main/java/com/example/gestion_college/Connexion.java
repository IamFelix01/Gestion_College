package com.example.gestion_college;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    private static String url="jdbc:mysql://localhost:3306/GestionCollege";
    private static String DBusername = "root";
    private static String DBpwd ="@saidbenchad@123";
    public static Connection getConnection(){
        try{
            return DriverManager.getConnection(url,DBusername,DBpwd);
        }catch (SQLException E ){
            System.out.println("Connection FAILED !");
            E.printStackTrace();
        }
        return null;
    }
}
