package com.example.gestion_college;

import java.util.ArrayList;

public class Classe {
    private int id;
    private String nom;
    private String niveau;

    public Classe(int id, String nom, String niveau) {
        this.id = id;
        this.nom = nom;
        this.niveau = niveau;
    }

    ArrayList<Classe> classes = new ArrayList<Classe>();

    public Classe(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
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

    public void ajouterClasse(Classe classe){
        if(!classes.contains(classe)){
            classes.add(classe);
        }
        else{
            System.out.println("deja inseré");
        }
    }

    public void supprimerClasse(Classe classe){
        if(classes.contains(classe)){
            classes.remove(classe);
        }
        else{
            System.out.println("deja inseré");
        }
    }

    // a definir
    public void modifierClasse(Classe classe){
        if(classes.contains(classe)){
            classes.remove(classe);
        }
        else{
            System.out.println("deja inseré");
        }
    }
}
