package charco.xmas.application;

import charco.xmas.domain.Joueur;
import charco.xmas.domain.Objet;
import competitive.programming.geometry.Coord;

public class PushTuileWithQuestObjet {

    private Joueur joueur;

    public PushTuileWithQuestObjet(Joueur joueur) {
        this.joueur = joueur;
    }

    public void execute() {
        // On detecte de quel bord la tuile est la plus proche
        Coord coordonneesObjet = joueur.objets().get(0).coordonnees();
        int x = coordonneesObjet.x;
        int y = coordonneesObjet.y;

        int minHorizontal = 6;
        int minVertical = 6;

        String action = "PUSH ";

        String directionHorizontaleAPousser = "RIGHT";
        String directionVerticaleAPousser = "DOWN";

        // Plus pres de la gauche que la droite
        if (x < (6 - x)) {
            minHorizontal = x;
            directionHorizontaleAPousser = "LEFT";
        } else {
            minHorizontal = 6 - x;
        }

        // Plus pres du haut que du bas
        if (y < (6 - y)) {
            minVertical = y;
            directionVerticaleAPousser = "UP";
        } else {
            minVertical = 6 - y;
        }

        if (minHorizontal < minVertical) {
            action += y + " " + directionHorizontaleAPousser;
        } else {
            action += x + " " + directionVerticaleAPousser;
        }

        System.err.println(directionHorizontaleAPousser);
        System.err.println(directionVerticaleAPousser);

        System.out.println(action);
    }
}
