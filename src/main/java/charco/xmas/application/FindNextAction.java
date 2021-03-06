package charco.xmas.application;

import charco.xmas.domain.Partie;
import charco.xmas.domain.TourDeJeu;

public class FindNextAction {
    private Partie partie;

    public FindNextAction(Partie partie) {
        this.partie = partie;
    }

    public void execute() {
        if (TourDeJeu.PUSH.equals(partie.currentTurn())) {
            if (partie.moi().possedeTuileAvecObjetDeMaQuete()) {
                System.err.println("possedeTuileAvecObjetDeMaQuete");
                // ou l'insérer pour y avoir accès ?
                System.out.println("PUSH 2 DOWN");
            } else {
                System.err.println("nePossedePasDeTuileAvecObjetDeMaQuete");
                // Trouver quel coté pousser pour récuperer la tuile le plus rapidement possible
                PushTuileWithQuestObjet getTuileWithQuestObjet = new PushTuileWithQuestObjet(partie.moi());
                getTuileWithQuestObjet.execute();
            }
        } else if(TourDeJeu.MOVE.equals(partie.currentTurn())) {
            // Si j'ai accès à un objet, je m'y dirige
            MoveToQuestObject moveToQuestObject = new MoveToQuestObject(partie.plateau(), partie.moi(), partie.moi().objets().get(0));
            moveToQuestObject.execute();
        }
    }
}
