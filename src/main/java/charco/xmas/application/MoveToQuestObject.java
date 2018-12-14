package charco.xmas.application;

import charco.xmas.domain.Joueur;
import charco.xmas.domain.Objet;
import charco.xmas.domain.Plateau;
import charco.xmas.infrastructure.PlateauGraph;

public class MoveToQuestObject {
    private Joueur joueur;
    private Plateau plateau;
    private Objet objetDeQuete;

    public MoveToQuestObject(Plateau plateau, Joueur joueur, Objet objetDeQuete) {
        this.joueur = joueur;
        this.plateau = plateau;
        this.objetDeQuete = objetDeQuete;
    }

    public void execute() {
        PlateauGraph plateauGraph = new PlateauGraph(plateau);
        if (joueur.possedeTuileAvecObjetDeMaQuete()) {
            System.err.println("possedeTuileAvecObjetDeMaQuete");
            passe();
        } else if (plateauGraph.isCheminPossibleEntre(joueur.coordonnees(), objetDeQuete.coordonnees())) {
            plateauGraph.printChemin(joueur.coordonnees(), objetDeQuete.coordonnees());
        } else {
            passe();
        }
    }



    private void passe() {
        System.out.println("PASS");
    }
}
