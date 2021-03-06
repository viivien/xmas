package charco.xmas.domain;

import java.util.List;

public class Plateau {
    private Tuile[][] tableauTuiles;

    private List<Tuile> listeTuiles;


    public Plateau(List<Tuile> listInputTuiles) {
        this.tableauTuiles = new Tuile[7][7];
        this.listeTuiles = listInputTuiles;

        listeTuiles.stream().forEach(tuile -> tableauTuiles[tuile.coordonnees().x][tuile.coordonnees().y] = tuile);
    }

    public Tuile[][] tableauTuiles() {
        return tableauTuiles;
    }
}
