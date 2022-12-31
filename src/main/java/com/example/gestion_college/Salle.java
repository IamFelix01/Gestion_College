package com.example.gestion_college;

import java.util.ArrayList;

public class Salle {
    private static int i = 0;
    private int idSalle;
    private String typeSalle;
    ArrayList<Salle> salles = new ArrayList<Salle>();

    public Salle(int idSalle, String typeSalle) {
        this.idSalle = idSalle;
        this.typeSalle = typeSalle;
    }


    public static int getI() {
        return i;
    }

    public static void setI(int i) {
        Salle.i = i;
    }

    public int getIdSalle() {
        return idSalle;
    }

    public void setIdSalle(int idSalle) {
        this.idSalle = idSalle;
    }

    public String getTypeSalle() {
        return typeSalle;
    }

    public void setTypeSalle(String typeSalle) {
        this.typeSalle = typeSalle;
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
