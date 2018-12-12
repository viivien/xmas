package charco.xmas.application;

import charco.xmas.domain.Joueur;
import charco.xmas.domain.Plateau;

public class MoveToQuestObject {
    private Joueur joueur;
    private Plateau plateau;

    public MoveToQuestObject() {
    }

    public MoveToQuestObject(Joueur joueur, Plateau plateau) {
        this.joueur = joueur;
        this.plateau = plateau;
    }

    public void execute() {
        System.out.println("PASS");
    }
}
