package com.example.gestion_college;

import java.util.ArrayList;

public class Niveau {
    private String code_niveau;
    private String nom;
    private ArrayList<Classe> classes=new ArrayList<>();
    public Niveau(String code_niveau){
        this.code_niveau=code_niveau;
    }
    public Niveau(String code_niveau, String nom) {
        this.code_niveau = code_niveau;
        this.nom = nom;
    }
    public void addClasse(Classe classe){
        classes.add(classe);
    }
    public ArrayList<Classe> GetClasses(){
        return classes;
    }
    public String getCode_niveau() {
        return code_niveau;
    }


    public String getNom() {
        return nom;
    }

}