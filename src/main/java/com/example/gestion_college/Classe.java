package com.example.gestion_college;

import java.util.ArrayList;

public class Classe {
    private int idClasse;
    private String nomClasse;
    ArrayList<Classe> classes = new ArrayList<Classe>();

    public Classe(int idClasse, String nomClasse) {
        this.idClasse = idClasse;
        this.nomClasse = nomClasse;
    }

    public int getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(int idClasse) {
        this.idClasse = idClasse;
    }

    public String getNomClasse() {
        return nomClasse;
    }

    public void setNomClasse(String nomClasse) {
        this.nomClasse = nomClasse;
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
