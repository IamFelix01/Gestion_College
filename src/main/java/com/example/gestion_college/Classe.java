package com.example.gestion_college;

import java.util.ArrayList;

public class Classe {
    private int id;
    private String nom;
    private String code;
    private Niveau niveau;
    private static int capacite;
    public Classe(int id){
        this.id=id;
    }
    public Classe(int id,String nom){
        this.id=id;
        this.nom=nom;
    }
    public Classe(int id,String nom,Niveau niv){
        this.id=id;
        this.nom=nom;
        this.niveau=niv;
        this.code=niveau.getCode_niveau();
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getCode() {
        return code;
    }

    public Niveau getNiveau() {
        return niveau;
    }



}
