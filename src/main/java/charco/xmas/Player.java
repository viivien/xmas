package charco.xmas;

import charco.xmas.application.FindNextAction;
import charco.xmas.domain.Chemin;
import charco.xmas.domain.Direction;
import charco.xmas.domain.Joueur;
import charco.xmas.domain.Objet;
import charco.xmas.domain.Partie;
import charco.xmas.domain.TourDeJeu;
import charco.xmas.domain.Tuile;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Help the Christmas elves fetch presents in a magical labyrinth!
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        // game loop
        while (true) {
            int turnType = in.nextInt();
            TourDeJeu tourActuel = TourDeJeu.PUSH;
            if (turnType == 1) {
                tourActuel = TourDeJeu.MOVE;
            }

            // Gestion plateau
            List<Tuile> listInputTuiles = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    String tile = in.next();
                    List<Direction> directions = getDirectionsFromInputString(tile);
                    Tuile tuile = new Tuile(i, j, directions);
                    listInputTuiles.add(tuile);
                }
            }


            Joueur monJoueur = new Joueur();
            Joueur joueurEnnemi = new Joueur();

            for (int i = 0; i < 2; i++) {
                int numPlayerCards = in.nextInt(); // the total number of quests for a player (hidden and revealed)
                int playerX = in.nextInt();
                int playerY = in.nextInt();
                String playerTile = in.next();
                List<Direction> directions = getDirectionsFromInputString(playerTile);
                Tuile tuileJoueur = new Tuile(playerX, playerY, directions);
                if (i == 0) {
                    monJoueur = new Joueur(tuileJoueur);
                } else {
                    joueurEnnemi = new Joueur(tuileJoueur);
                }
            }

            int numItems = in.nextInt(); // the total number of items available on board and on player tiles
            for (int i = 0; i < numItems; i++) {
                String itemName = in.next();
                int itemX = in.nextInt();
                int itemY = in.nextInt();
                int itemPlayerId = in.nextInt();

                //TODO extraie daans une methode
//                if (itemX == -1) {
//                    itemX = ;
//                    itemY = ;
//                } else if (itemX == -2) {
//                    itemX = ;
//                    itemY = ;
//                }
                Objet objet = new Objet(itemName, itemX, itemY);

                if (itemPlayerId == 0) {
                    monJoueur.addObjet(objet);
                } else {
                    joueurEnnemi.addObjet(objet);
                }
            }
            int numQuests = in.nextInt(); // the total number of revealed quests for both players
            for (int i = 0; i < numQuests; i++) {
                String questItemName = in.next();
                int questPlayerId = in.nextInt();
            }

            Partie maPartie = new Partie(tourActuel, monJoueur, joueurEnnemi, listInputTuiles);

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            FindNextAction findNextAction = new FindNextAction(maPartie);
            findNextAction.execute();
        }
    }

    private static List<Direction> getDirectionsFromInputString(String tile) {
        List<Direction> directions = new ArrayList<>();
        if (tile.toCharArray()[0] == '1') {
            directions.add(Direction.UP);
        }
        if (tile.toCharArray()[1] == '1') {
            directions.add(Direction.RIGHT);
        }
        if (tile.toCharArray()[2] == '1') {
            directions.add(Direction.DOWN);
        }
        if (tile.toCharArray()[3] == '1') {
            directions.add(Direction.LEFT);
        }
        return directions;
    }
}