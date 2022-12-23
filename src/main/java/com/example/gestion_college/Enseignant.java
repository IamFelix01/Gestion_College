package com.example.gestion_college;

import java.util.ArrayList;

public class Enseignant {

    private int id;
    private String nom;
    private String prenom;
    private String Sexe;
    private String Contact;
    private String Matiere;
    private String Classe;

    ArrayList<Enseignant> enseignants = new ArrayList<Enseignant>();

    public Enseignant(int id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Enseignant(int id, String nom, String prenom, String sexe, String contact, String matiere, String classe) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        Sexe = sexe;
        Contact = contact;
        Matiere = matiere;
        Classe = classe;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSexe() {
        return Sexe;
    }

    public void setSexe(String sexe) {
        Sexe = sexe;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getMatiere() {
        return Matiere;
    }

    public void setMatiere(String matiere) {
        Matiere = matiere;
    }

    public String getClasse() {
        return Classe;
    }

    public void setClasse(String classe) {
        Classe = classe;
    }

    public ArrayList<Enseignant> getEnseignants() {
        return enseignants;
    }

    public void setEnseignants(ArrayList<Enseignant> enseignants) {
        this.enseignants = enseignants;
    }

    public void ajouterEnseignant(Enseignant enseignant){
        if(!enseignants.contains(enseignant)){
            enseignants.add(enseignant);
        }
        else{
            System.out.println("deja inseré");
        }
    }

    public void supprimerEnseignant(Enseignant enseignant){
        if(enseignants.contains(enseignant)){
            enseignants.remove(enseignant);
        }
        else{
            System.out.println("deja inseré");
        }
    }

    // a definir
    public void modifierSalle(Enseignant enseignant){
        if(enseignants.contains(enseignant)){
            enseignants.remove(enseignant);
        }
        else{
            System.out.println("deja inseré");
        }
    }
}
