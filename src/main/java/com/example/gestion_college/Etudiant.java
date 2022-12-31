package com.example.gestion_college;

import java.util.Date;

public class Etudiant {
    private String nom;
    private String prenom;
    private Date dateNaiss;
    private String sexe;
    private String niveau;
    private String Classe;
    private String CNE;

    public Etudiant(String CNE, String nom, String prenom, Date dateNaiss, String sexe, String niveau, String classe) {
        this.CNE = CNE;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaiss = dateNaiss;
        this.sexe = sexe;
        this.niveau = niveau;
        this.Classe = classe;
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

    public String getNiveau() {
        return niveau;
    }

    public String getClasse() {
        return Classe;
    }

}
