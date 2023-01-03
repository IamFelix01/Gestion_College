package com.example.gestion_college;

import java.util.ArrayList;

public class Cours {

    private int id;
    private String nom;
    private int heureDebut;
    private int heureFin;
    private int id_classe;
    private int id_salle;
    private int jour;


    ArrayList<Cours> courss = new ArrayList<Cours>();

    public Cours(int id, String nom, int heureDebut, int heureFin, int id_classe, int id_salle,int jour) {
        this.id = id;
        this.nom = nom;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.id_classe = id_classe;
        this.id_salle = id_salle;
        this.jour = jour;
    }

    public int getJour() {
        return jour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_classe() {
        return id_classe;
    }

    public int getId_salle() {
        return id_salle;
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getHeureDebut() {
        return heureDebut;
    }


    public int getHeureFin() {
        return heureFin;
    }



    public ArrayList<Cours> getCourss() {
        return courss;
    }

    public void setCourss(ArrayList<Cours> courss) {
        this.courss = courss;
    }

    public void ajouterCours(Cours cours){
        if(!courss.contains(cours)){
            courss.add(cours);
        }
        else{
            System.out.println("deja inseré");
        }
    }

    public void supprimerCours(Cours cours){
        if(courss.contains(cours)){
            courss.remove(cours);
        }
        else{
            System.out.println("deja inseré");
        }
    }

    // a definir
    public void modifierSalle(Cours cours){
        if(courss.contains(cours)){
            courss.remove(cours);
        }
        else{
            System.out.println("deja inseré");
        }
    }
}