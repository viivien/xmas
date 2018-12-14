package charco.xmas.infrastructure;

import charco.xmas.domain.Direction;
import charco.xmas.domain.Plateau;
import charco.xmas.domain.Tuile;
import competitive.programming.geometry.Coord;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlateauGraph {
    private List<TuileGraph> tuileGraphList;

    public PlateauGraph(Plateau plateau) {
        tuileGraphList = new ArrayList<>();
        mapTuilesToTuileGraph(plateau.tableauTuiles());
    }

    public boolean isCheminPossibleEntre(Coord coord1, Coord coord2) {
        if (!coord1.estDansLeTableau() || !coord2.estDansLeTableau()) {
            return false;
        }
        return TuileGraph.existeChemin(tuileGraphList, getTuileGraphByCoordonnees(coord1), getTuileGraphByCoordonnees(coord2));
    }

    public void printChemin(Coord coordonnees, Coord coordonnees1) {
        //TuileGraph.plusCourtChemin(tuileGraphList, coordonnees, coordonnees1);
    }

    private void mapTuilesToTuileGraph(Tuile[][] tableauTuiles) {
        for (int x = 0; x < tableauTuiles.length; x++) {
            for (int y = 0; y <tableauTuiles[0].length; y++) {
                Tuile tuile = tableauTuiles[x][y];
                TuileGraph tuileGraph = createIfNotExistAndGetTuileGraph(tuile.coordonnees());

                // UP
                Optional<Tuile> tuileUp = getTuile(tableauTuiles, x, y - 1);
                if (tuileUp.isPresent()) {
                    TuileGraph tuileGraphUp = createIfNotExistAndGetTuileGraph(tuileUp.get().coordonnees());
                    if (tuile.directions().contains(Direction.UP) && tuileUp.get().directions().contains(Direction.DOWN)) {
                        tuileGraph.addAdjacent(tuileGraphUp);
                    }
                }

                // DOWN
                Optional<Tuile> tuileDown = getTuile(tableauTuiles, x, y + 1);
                if (tuileDown.isPresent()) {
                    TuileGraph tuileGraphDown = createIfNotExistAndGetTuileGraph(tuileDown.get().coordonnees());
                    if (tuile.directions().contains(Direction.DOWN) && tuileDown.get().directions().contains(Direction.UP)) {
                        tuileGraph.addAdjacent(tuileGraphDown);
                    }
                }

                // LEFT
                Optional<Tuile> tuileLeft = getTuile(tableauTuiles, x - 1, y);
                if (tuileLeft.isPresent()) {
                    TuileGraph tuileGraphLeft = createIfNotExistAndGetTuileGraph(tuileLeft.get().coordonnees());
                    if (tuile.directions().contains(Direction.LEFT) && tuileLeft.get().directions().contains(Direction.RIGHT)) {
                        tuileGraph.addAdjacent(tuileGraphLeft);
                    }
                }

                //RIGHT
                Optional<Tuile> tuileRight = getTuile(tableauTuiles, x + 1, y);
                if (tuileRight.isPresent()) {
                    TuileGraph tuileGraphRight = createIfNotExistAndGetTuileGraph(tuileRight.get().coordonnees());
                    if (tuile.directions().contains(Direction.RIGHT) && tuileRight.get().directions().contains(Direction.LEFT)) {
                        tuileGraph.addAdjacent(tuileGraphRight);
                    }
                }
            }
        }
    }

    private TuileGraph createIfNotExistAndGetTuileGraph(Coord coordonnees) {
        Optional<TuileGraph> tuileGraphOptional = tuileGraphList.stream().filter(tuileGraph -> tuileGraph.getCoordonnes().equals(coordonnees)).findFirst();
        if (tuileGraphOptional.isPresent()) {
            return tuileGraphOptional.get();
        } else {
            TuileGraph tuileGraph = new TuileGraph(coordonnees);
            tuileGraphList.add(tuileGraph);
            return tuileGraph;
        }
    }

    private Optional<Tuile> getTuile(Tuile[][] tableauTuiles, int x, int y) {
        if (x >= 0 && x < tableauTuiles.length && y >= 0 && y < tableauTuiles[x].length) {
            return Optional.of(tableauTuiles[x][y]);
        }
        return Optional.empty();
    }

    private TuileGraph getTuileGraphByCoordonnees(Coord coord) {
        System.err.println("getTuileGraphByCoordonnees : "  + coord.x + " " + coord.y);
        return tuileGraphList.stream().filter(tuileGraph -> tuileGraph.getCoordonnes().equals(coord)).findFirst().orElseThrow(IllegalStateException::new);
    }
}
