package Classes;

import com.example.gestion_college.Classe;
import com.example.gestion_college.Enseignant;

import java.util.ArrayList;

public class Cours {

    private int idCours;
    Enseignant enseignant;
    Salle salle;
    Classe classe;
    String nom;
    private int heureDebut;
    private int heureFin;
    ArrayList<Cours> courss = new ArrayList<Cours>();

    public Cours(int idCours, Enseignant enseignant, Salle salle, Classe classe, String nom, int heureDebut, int heureFin) {
        this.idCours = idCours;
        this.enseignant = enseignant;
        this.salle = salle;
        this.classe = classe;
        this.nom = nom;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    public int getIdCours() {
        return idCours;
    }

    public void setIdCours(int idCours) {
        this.idCours = idCours;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(int heureDebut) {
        this.heureDebut = heureDebut;
    }

    public int getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(int heureFin) {
        this.heureFin = heureFin;
    }

    public void ajouterCours(Cours cours){
        if(!courss.contains(cours)){
            courss.add(cours);
        }
        else{
            System.out.println("deja inseré");
        }
    }

    public void supprimerCours(Cours cours){
        if(courss.contains(cours)){
            courss.remove(cours);
        }
        else{
            System.out.println("deja inseré");
        }
    }

    // a definir
    public void modifierSalle(Cours cours){
        if(courss.contains(cours)){
            courss.remove(cours);
        }
        else{
            System.out.println("deja inseré");
        }
    }
}
