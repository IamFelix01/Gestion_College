package com.example.gestion_college;

import java.util.Date;

public class Absence {
    private int id_absence;
    private int num_semaine;
    private String cne_etudiant ;
    private int id_cours;
    private String estJustifie;
//    private String cne;
//    private Enseignant enseignant;
//    private Classe classe;


    public Absence(int id_absence, int num_semaine, String cne_etudiant, int id_cours, String estJustifie) {
        this.id_absence = id_absence;
        this.num_semaine = num_semaine;
        this.cne_etudiant = cne_etudiant;
        this.id_cours = id_cours;
        this.estJustifie = estJustifie;
    }

    public int getId_absence() {
        return id_absence;
    }

    public int getNum_semaine() {
        return num_semaine;
    }

    public String getCne_etudiant() {
        return cne_etudiant;
    }

    public int getId_cours() {
        return id_cours;
    }

    public String getEstJustifie() {
        return estJustifie;
    }
}
