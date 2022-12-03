package Classes;

import java.util.ArrayList;

public class Enseignant {

    private int idProf;
    private String nom;
    private String prenom;
    ArrayList<Enseignant> enseignants = new ArrayList<Enseignant>();

    public Enseignant(int idProf, String nom, String prenom) {
        this.idProf = idProf;
        this.nom = nom;
        this.prenom = prenom;
    }

    public int getIdProf() {
        return idProf;
    }

    public void setIdProf(int idProf) {
        this.idProf = idProf;
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
