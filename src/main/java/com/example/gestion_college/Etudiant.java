package com.example.gestion_college;

import java.util.Date;

public class Etudiant {
    private String nom;
    private String prenom;
    private Date dateNaiss;
    private String sexe;
    private String code_niveau;
    private int id_classe;
    private String CNE;
    private  int id_parent;

    public Etudiant(String CNE, String nom, String prenom, Date dateNaiss, String sexe, String code_niveau, int id_classe,int id_parent) {
        this.CNE = CNE;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaiss = dateNaiss;
        this.sexe = sexe;
        this.code_niveau = code_niveau;
        this.id_classe = id_classe;
        this.id_parent=id_parent;
    }

    public int getId_parent() {
        return id_parent;
    }

    public String getCNE() {
        return CNE;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Date getDateNaiss() {
        return dateNaiss;
    }

    public String getSexe() {
        return sexe;
    }

    public String getCode_niveau() {
        return code_niveau;
    }

    public int getId_classe() {
        return id_classe;
    }
}
