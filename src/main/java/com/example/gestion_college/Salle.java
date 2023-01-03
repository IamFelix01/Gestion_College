package com.example.gestion_college;

import java.util.ArrayList;

public class Salle {
    private static int i = 0;
    private int id;
    private String type;
    //Materiels
    ArrayList<Salle> salles = new ArrayList<Salle>();

    public Salle(int id){
        this.id=id;
    }

    public Salle(int id, String type) {
        this.id = id;
        this.type = type;
    }


    public static int getI() {
        return i;
    }

    public static void setI(int i) {
        Salle.i = i;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void ajouterSalle(Salle salle){
        if(!salles.contains(salle)){
            salles.add(salle);
        }
        else{
            System.out.println("deja inseré");
        }
    }

    public void supprimerSalle(Salle salle){
        if(salles.contains(salle)){
            salles.remove(salle);
        }
        else{
            System.out.println("deja inseré");
        }
    }

    // a definir
    public void modifierSalle(Salle salle){
        if(salles.contains(salle)){
            salles.remove(salle);
        }
        else{
            System.out.println("deja inseré");
        }
    }

}