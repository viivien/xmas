package charco.xmas.domain;

import competitive.programming.geometry.Coord;

import java.util.ArrayList;
import java.util.List;

public class Joueur {
    private Tuile tuile;

    private List<Objet> objets;

    public Joueur() {
        objets = new ArrayList<>();
    }

    public Joueur(Tuile tuileJoueur) {
        this();
        tuile = tuileJoueur;
    }

    public Coord coordonnees() {
        return tuile.coordonnees();
    }

    public void addObjet(Objet objet) {
        objets.add(objet);
    }
}
