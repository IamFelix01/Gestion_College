package Classes;

import java.util.Date;

public class Etudiant {
    private  int id;
    private String nom;
    private String prenom;
    private Date dateNaiss;
    private String sexe;
    private String niveau;
    private String Classe;
    private String massar;

    public Etudiant(int id, String nom, String prenom, Date dateNaiss, String sexe, String niveau, String classe, String massar) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaiss = dateNaiss;
        this.sexe = sexe;
        this.niveau = niveau;
        Classe = classe;
        this.massar = massar;
    }

    public int getId() {
        return id;
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

    public String getMassar() {
        return massar;
    }
}
