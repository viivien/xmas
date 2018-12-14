package charco.xmas.domain;

import java.util.List;

public class Partie {
    private Joueur moi;

    private Joueur ennemi;

    private Plateau plateau;

    private TourDeJeu tourActuel;

    public Partie(TourDeJeu tourActuel, Joueur monJoueur, Joueur joueurEnnemi, List<Tuile> listInputTuiles) {
        this.tourActuel = tourActuel;
        this.moi = monJoueur;
        this.ennemi = joueurEnnemi;

        plateau = new Plateau(listInputTuiles);
    }

    public TourDeJeu currentTurn() {
        return tourActuel;
    }

    public Joueur moi() {
        return moi;
    }

    public Plateau plateau() {
        return plateau;
    }

}
