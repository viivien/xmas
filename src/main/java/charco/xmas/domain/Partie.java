package charco.xmas.domain;

public class Partie {
    private Joueur moi;

    private Joueur ennemi;

    private Plateau plateau;

    private TourDeJeu tourActuel;

    public Partie() {

    }

    public TourDeJeu currentTurn() {
        return tourActuel;
    }
}
