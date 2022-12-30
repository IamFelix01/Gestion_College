package com.example.gestion_college;

public class Ligne {
    private int id;
    private String materiel;
    private int idSalle;

    public Ligne(int id, String materiel, int idSalle) {
        this.id = id;
        this.materiel = materiel;
        this.idSalle = idSalle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMateriel() {
        return materiel;
    }

    public void setMateriel(String materiel) {
        this.materiel = materiel;
    }

    public int getIdSalle() {
        return idSalle;
    }

    public void setIdSalle(int idSalle) {
        this.idSalle = idSalle;
    }
}
