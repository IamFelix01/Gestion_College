package com.example.gestion_college;

import java.util.ArrayList;

public class Cours {

    private int id;
    private String nom;
    private int heureDebut;
    private int heureFin;
    private String enseignant;
    private String classe;
    private String salle;

    ArrayList<Cours> courss = new ArrayList<Cours>();

    public Cours(int id, String nom, int heureDebut, int heureFin, String enseignant, String classe, String salle) {
        this.id = id;
        this.nom = nom;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.enseignant = enseignant;
        this.classe = classe;
        this.salle = salle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setHeureDebut(int heureDebut) {
        this.heureDebut = heureDebut;
    }

    public int getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(int heureFin) {
        this.heureFin = heureFin;
    }

    public String getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(String enseignant) {
        this.enseignant = enseignant;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
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
