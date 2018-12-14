package charco.xmas.domain;

import competitive.programming.geometry.Coord;

import java.util.ArrayList;
import java.util.List;

public class Tuile {
    private Coord coordonnes;

    private List<Direction> directions;

    private List<Objet> objets;

    public Tuile() {
        objets = new ArrayList<>();
    }

    public Tuile(int x, int y, List<Direction> directions) {
        this();
        coordonnes = new Coord(x, y);
        this.directions = directions;
    }

    public Coord coordonnees() {
        return coordonnes;
    }

    public List<Direction> directions() {
        return directions;
    }

    public List<Objet> objets() {
        return objets;
    }

    public void addObjet(Objet objet) {
        objets.add(objet);
    }
}
