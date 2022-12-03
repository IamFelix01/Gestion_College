package Classes;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class Test {

    public static void main(String[] args) {
        int[] temps = {8,10,14,16};
        Salle salle1 = new Salle(1,"science");
        Salle[] salles = {salle1, new Salle(2,"sport"),new Salle(3,"science"),new Salle(4,"science")};

        Enseignant enseignant1 = new Enseignant(1,"hamid","lmath");

        Classe classe1 = new Classe(1,"PC");
        Cours[] Courses = {new Cours(1,enseignant1,salle1,classe1,"math", 8,10),
                new Cours(2,enseignant1,salle1,classe1,"math", 10,12)};


        //ArrayList<String> diponnibilite = new ArrayList<String>();
        // Create a new LinkedHashSet
        Set<String> diponnibilite = new LinkedHashSet<>();
        System.out.println("========= Les Salles disponnibles =========");
        int heureChercher = 10,i=0;
        for(int t: temps)
        for(Salle s:salles){
            for(Cours cours : Courses) {
                if (cours.getSalle() != s) {
                    //System.out.println();
                    diponnibilite.add("Salle numero " + s.getIdSalle() + " à " + t);
                } else {
                    for (Cours c1 : Courses) {
                        if (c1.getHeureDebut() == t) {
                            i = 1;
                        }
                    }
                    if (i == 0) {
                        diponnibilite.add("Salle numero " + s.getIdSalle() + " à " + t);
                    }
                }
            }
        }


        for(String d: diponnibilite){
            System.out.println(d);
        }


    }
}
