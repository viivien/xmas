package charco.xmas.domain;

import competitive.programming.geometry.Coord;

import java.util.Objects;

public class Objet {
    private String nom;

    private Coord coordonnees;

    public Objet(String nom, int x, int y) {
        this.nom = nom;
        this.coordonnees = new Coord(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Objet objet = (Objet) o;
        return Objects.equals(nom, objet.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }

    public Coord coordonnees() {
        return coordonnees;
    }
}
