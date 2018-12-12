package charco.xmas.domain;

import java.util.List;
import java.util.Objects;

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

//    public void setMyPlayerInfo(Joueur joueur) {
//        moi = Objects.requireNonNull(joueur);
//    }
//
//    public void setEnnemyPlayerInfo(Joueur joueur) {
//        ennemi = Objects.requireNonNull(joueur);
//    }
}
