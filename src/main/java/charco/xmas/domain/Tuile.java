package charco.xmas.domain;

import competitive.programming.geometry.Coord;

import java.util.ArrayList;
import java.util.List;

public class Tuile {
    private Coord coordonnes;

    private List<Chemin> chemins;

    private List<Objet> objets;

    public Tuile() {
        objets = new ArrayList<>();
    }

    public Tuile(int x, int y, List<Chemin> listChemins) {
        this();
        coordonnes = new Coord(x, y);
        chemins = listChemins;
    }

    public Coord coordonnees() {
        return coordonnes;
    }
}
