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

    public List<Objet> objets() {
        return objets;
    }

    public boolean possedeTuileAvecObjetDeMaQuete() {
        for (Objet objet : objets) {
            System.err.println("objet du joueur : " + objet.nom() + " " + objet.coordonnees().x + "-" + objet.coordonnees().y);
        }
        for (Objet objet : tuile.objets()) {
            System.err.println("objet de la tuile du joueur : " + objet.nom() + " " + objet.coordonnees().x + "-" + objet.coordonnees().y);
        }
        return objets.stream().anyMatch(objet -> objet.coordonnees().x == -1);
    }
}
