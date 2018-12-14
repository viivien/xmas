import java.util.List;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Scanner;
import java.util.Objects;
import java.util.Iterator;
import java.util.ArrayList;
class Player {
	private static class Vector {
	    private static String doubleToString(double d) {
	        return String.format("%.3f", d);
	    }
	    public final double x;
	    public final double y;
	    public static double COMPARISON_TOLERANCE = 0.0000001;
	    public Vector(Coord coord) {
	        this(coord.x, coord.y);
	    }
	    public Vector(double x, double y) {
	        this.x = x;
	        this.y = y;
	    }
	    public Vector(Vector other) {
	        this(other.x, other.y);
	    }
	    public Vector add(Vector other) {
	        return new Vector(x + other.x, y + other.y);
	    }
	    public Vector negate() {
	        return new Vector(-x, -y);
	    }
	    public Vector rotateInDegree(double degree){
	    	return rotateInRadian(Math.toRadians(degree));
	    }
	    public Vector rotateInRadian(double radians) {
	        final double length = length();
	        double angle = angleInRadian();
	        angle += radians;
	        final Vector result = new Vector(Math.cos(angle), Math.sin(angle));
	        return result.multiply(length);
	    }
	    public double angleInDegree() {
	        return Math.toDegrees(angleInRadian());
	    }
		private double angleInRadian() {
			return Math.atan2(y, x);
		}
	    public double dot(Vector other) {
	        return x * other.x + y * other.y;
	    }
	    @Override
	    public boolean equals(Object obj) {
	        if (this == obj) {
	            return true;
	        }
	        if (obj == null) {
	            return false;
	        }
	        if (getClass() != obj.getClass()) {
	            return false;
	        }
	        final Vector other = (Vector) obj;
	        if (Math.abs(x - other.x) > COMPARISON_TOLERANCE) {
	            return false;
	        }
	        if (Math.abs(y - other.y) > COMPARISON_TOLERANCE) {
	            return false;
	        }
	        return true;
	    }
	    @Override
	    public int hashCode() {
	        final int prime = 31;
	        int result = 1;
	        long temp;
	        temp = Double.doubleToLongBits(x);
	        result = prime * result + (int) (temp ^ (temp >>> 32));
	        temp = Double.doubleToLongBits(y);
	        result = prime * result + (int) (temp ^ (temp >>> 32));
	        return result;
	    }
	    public double length() {
	        return Math.sqrt(x * x + y * y);
	    }
	    public double length2() {
	        return x * x + y * y;
	    }
	    public Vector minus(Vector other) {
	        return new Vector(x - other.x, y - other.y);
	    }
	    public Vector multiply(double factor) {
	        return new Vector(x * factor, y * factor);
	    }
	    public Vector norm() {
	        final double length = length();
	        if (length>0)
	        	return new Vector(x / length, y / length);
	        return new Vector(0,0);
	    }
	    public Vector ortho() {
	        return new Vector(-y, x);
	    }
	    @Override
	    public String toString() {
	        return "[x=" + doubleToString(x) + ", y=" + doubleToString(y) + "]";
	    }
	}
	private static class Coord {
	    public final int x;
	    public final int y;
	    public Coord(int x, int y) {
	        this.x = x;
	        this.y = y;
	    }
	    public Coord(Vector v) {
	        this.x = (int) v.x;
	        this.y = (int) v.y;
	    }
	    public Coord add(Coord coord) {
	        return new Coord(x + coord.x, y + coord.y);
	    }
	    public double distance(Coord coord) {
	        return Math.sqrt(distanceSquare(coord));
	    }
	    public long distanceSquare(Coord coord) {
	        long dx = coord.x - x;
	        long dy = coord.y - y;
	        return dx * dx + dy * dy;
	    }
	    public Coord minus(Coord coord) {
	        return new Coord(x - coord.x, y - coord.y);
	    }
	    @Override
	    public boolean equals(Object obj) {
	        if (this == obj) {
	            return true;
	        }
	        if (obj == null) {
	            return false;
	        }
	        if (getClass() != obj.getClass()) {
	            return false;
	        }
	        final Coord other = (Coord) obj;
	        if (x != other.x) {
	            return false;
	        }
	        if (y != other.y) {
	            return false;
	        }
	        return true;
	    }
	    public boolean estDansLeTableau() {
	        return x >= 0 && y >=0;
	    }
	    @Override
	    public int hashCode() {
	        final int prime = 31;
	        int result = 1;
	        result = prime * result + x;
	        result = prime * result + y;
	        return result;
	    }
	    @Override
	    public String toString() {
	        return "[x=" + x + ", y=" + y + "]";
	    }
	}
	private static class Tuile {
	    private Coord coordonnes;
	    private List<Direction> directions;
	    private List<Objet> objets;
	    public Tuile() {
	        objets = new ArrayList<>();
	    }
	    public Tuile(int x, int y, List<Direction> directions) {
	        this();
	        coordonnes = new Coord(x, y);
	        this.directions = directions;
	    }
	    public Coord coordonnees() {
	        return coordonnes;
	    }
	    public List<Direction> directions() {
	        return directions;
	    }
	    public List<Objet> objets() {
	        return objets;
	    }
	    public void addObjet(Objet objet) {
	        objets.add(objet);
	    }
	}
	private static enum TourDeJeu {
	    PUSH,
	    MOVE
	}
	private static class Plateau {
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
	private static class Objet {
	    private String nom;
	    private Coord coordonnees;
	    public Objet(String nom, int x, int y) {
	        this.nom = nom;
	        this.coordonnees = new Coord(x, y);
	    }
	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        Objet objet = (Objet) o;
	        return Objects.equals(nom, objet.nom);
	    }
	    @Override
	    public int hashCode() {
	        return Objects.hash(nom);
	    }
	    public Coord coordonnees() {
	        return coordonnees;
	    }
	    public String nom() {
	        return nom;
	    }
	}
	private static class Joueur {
	    private Tuile tuile;
	    private List<Objet> objets;
	    public Joueur() {
	        objets = new ArrayList<>();
	    }
	    public Joueur(Tuile tuileJoueur) {
	        this();
	        tuile = tuileJoueur;
	    }
	    public Coord coordonnees() {
	        return tuile.coordonnees();
	    }
	    public void addObjet(Objet objet) {
	        objets.add(objet);
	    }
	    public List<Objet> objets() {
	        return objets;
	    }
	    public boolean possedeTuileAvecObjetDeMaQuete() {
	        for (Objet objet : objets) {
	            System.err.println("objet du joueur : " + objet.nom() + " " + objet.coordonnees().x + "-" + objet.coordonnees().y);
	        }
	        for (Objet objet : tuile.objets()) {
	            System.err.println("objet de la tuile du joueur : " + objet.nom() + " " + objet.coordonnees().x + "-" + objet.coordonnees().y);
	        }
	        return objets.stream().anyMatch(objet -> objet.coordonnees().x == -1);
	    }
	}
	private static enum Direction {
	    UP,
	    DOWN,
	    RIGHT,
	    LEFT
	}
	private static class Chemin {
	    private Direction direction;
	    public Chemin(Direction direction) {
	        this.direction = direction;
	    }
	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        Chemin chemin = (Chemin) o;
	        return direction == chemin.direction;
	    }
	    @Override
	    public int hashCode() {
	        return Objects.hash(direction);
	    }
	}
	private static class Partie {
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
	private static class TuileGraph {
	    private Coord coordonnes;
	    private List adjacents;
	    private int distance;
	    private boolean parcouru;
	    private TuileGraph pere;
	    public TuileGraph(Coord coord){
	        coordonnes = coord;
	        adjacents = new LinkedList();
	        pere = null;
	        distance = -1;
	        parcouru = false;
	    }
	    private static void reset(Collection c){
	        for (Iterator it = c.iterator(); it.hasNext();){
	            TuileGraph g = (TuileGraph)it.next();
	            g.reset();
	        }
	    }
	    public static void arete(TuileGraph g1, TuileGraph g2){
	        g1.addAdjacent(g2);
	        g2.addAdjacent(g1);
	    }
	    public static void parcoursProfondeur(Collection c){
	        reset(c);
	        for (Iterator it = c.iterator(); it.hasNext();){
	            TuileGraph g = (TuileGraph)it.next();
	            if (!g.estDejaParcouru())
	                g.visiterProfondeur();
	        }
	        System.err.println();
	    }
	    public static boolean existeChemin(Collection c, TuileGraph s, TuileGraph t){
	        reset(c);
	        boolean b = s.existeChemin(t);
	        if (b)
	            t.afficherAncetres();
	        else
	            System.err.println("pas de chemin");
	        return b;
	    }
	    public static void parcoursLargeur(Collection c){
	        reset(c);
	        for (Iterator it = c.iterator(); it.hasNext();){
	            TuileGraph g = (TuileGraph)it.next();
	            if (!g.estDejaParcouru())
	                g.visiterLargeur();
	        }
	        System.err.println();
	    }
	    public static int plusCourtChemin(Collection c, TuileGraph s, TuileGraph t){
	        reset(c);
	        int d = s.plusCourtChemin(t); // parcours largeur depuis s
	        if (d>0)
	            t.afficherAncetres();
	        else
	            System.err.println("pas de chemin");
	        return t.getDistance();
	    }
	    public final Coord getCoordonnes()      {return coordonnes;}
	    public final TuileGraph getPere()      {return pere;}
	    public final int    getDistance()  {return distance;}
	    public final List   getAdjacents() {return adjacents;}
	    public final boolean estDejaParcouru() {return parcouru;}
	    public final void setParcouru(boolean b)    {parcouru = b;}
	    public final void setDistance(int d)   {distance = d;}
	    public void addAdjacent(TuileGraph g)      {adjacents.add(g);}
	    private void setPere(TuileGraph g)         {pere = g;}
	    private void reset(){
	        setParcouru(false);
	        pere = null;
	        distance = -1;
	    }
	    public final void visiterProfondeur(){
	        System.err.print(this);
	        parcouru = true;
	        for (Iterator it = adjacents.iterator(); it.hasNext();){
	            TuileGraph g = (TuileGraph)it.next();
	            if (!g.estDejaParcouru()){
	                g.setPere(this);
	                g.visiterProfondeur();
	            }
	        }
	    }
	    public final boolean existeChemin(TuileGraph t){
	        boolean trouve = false;
	        parcouru = true;
	        if (equals(t))
	            trouve = true;;
	        for (Iterator it = adjacents.iterator(); it.hasNext();){
	            TuileGraph g = (TuileGraph)it.next();
	            if (!g.estDejaParcouru()){
	                g.setPere(this);
	                trouve = trouve || g.existeChemin(t);
	            }
	        }
	        return trouve;
	    }
	    public final void visiterLargeur(){
	        parcouru = true;
	        distance = 0; // sommet de départ
	        List file = new LinkedList();
	        file.add(this);
	        while (!file.isEmpty()){
	            TuileGraph u = (TuileGraph)file.remove(0);
	            System.err.print(u);
	            for (Iterator it = u.getAdjacents().iterator(); it.hasNext();){
	                TuileGraph g = (TuileGraph)it.next();
	                if (!g.estDejaParcouru()){
	                    g.setPere(u);
	                    g.setParcouru(true);
	                    g.setDistance(u.getDistance()+1);
	                    file.add(g);
	                }
	            }
	            u.setParcouru(true); // noir
	        }
	    }
	    public final int plusCourtChemin(TuileGraph t){
	        parcouru = true;
	        distance = 0; // sommet de départ
	        List file = new LinkedList();
	        file.add(this);
	        while (!file.isEmpty()){
	            TuileGraph u = (TuileGraph)file.remove(0);
	            if (u.equals(t))
	                return u.getDistance();
	            for (Iterator it = u.getAdjacents().iterator(); it.hasNext();){
	                TuileGraph g = (TuileGraph)it.next();
	                if (!g.estDejaParcouru()){
	                    g.setPere(u);
	                    g.setParcouru(true);
	                    g.setDistance(u.getDistance()+1);
	                    file.add(g);
	                }
	            }
	            u.setParcouru(true);
	        }
	        return -1;
	    }
	    public void afficherAncetres(){
	        List chemin = new LinkedList();
	        TuileGraph aux = this;
	        while (aux.getPere()!=null){
	            chemin.add(0,aux); // en debut de liste
	            aux = aux.getPere();
	        }
	        chemin.add(0,aux);
	        System.err.println(chemin);
	    }
	    public String toString(){
	        return "["+coordonnes.x+", "+coordonnes.y+"]";
	    }
	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        TuileGraph that = (TuileGraph) o;
	        return Objects.equals(coordonnes, that.coordonnes);
	    }
	    @Override
	    public int hashCode() {
	        return Objects.hash(coordonnes);
	    }
	}
	private static class PlateauGraph {
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
	    }
	    private void mapTuilesToTuileGraph(Tuile[][] tableauTuiles) {
	        for (int x = 0; x < tableauTuiles.length; x++) {
	            for (int y = 0; y <tableauTuiles[0].length; y++) {
	                Tuile tuile = tableauTuiles[x][y];
	                TuileGraph tuileGraph = createIfNotExistAndGetTuileGraph(tuile.coordonnees());
	                Optional<Tuile> tuileUp = getTuile(tableauTuiles, x, y - 1);
	                if (tuileUp.isPresent()) {
	                    TuileGraph tuileGraphUp = createIfNotExistAndGetTuileGraph(tuileUp.get().coordonnees());
	                    if (tuile.directions().contains(Direction.UP) && tuileUp.get().directions().contains(Direction.DOWN)) {
	                        tuileGraph.addAdjacent(tuileGraphUp);
	                    }
	                }
	                Optional<Tuile> tuileDown = getTuile(tableauTuiles, x, y + 1);
	                if (tuileDown.isPresent()) {
	                    TuileGraph tuileGraphDown = createIfNotExistAndGetTuileGraph(tuileDown.get().coordonnees());
	                    if (tuile.directions().contains(Direction.DOWN) && tuileDown.get().directions().contains(Direction.UP)) {
	                        tuileGraph.addAdjacent(tuileGraphDown);
	                    }
	                }
	                Optional<Tuile> tuileLeft = getTuile(tableauTuiles, x - 1, y);
	                if (tuileLeft.isPresent()) {
	                    TuileGraph tuileGraphLeft = createIfNotExistAndGetTuileGraph(tuileLeft.get().coordonnees());
	                    if (tuile.directions().contains(Direction.LEFT) && tuileLeft.get().directions().contains(Direction.RIGHT)) {
	                        tuileGraph.addAdjacent(tuileGraphLeft);
	                    }
	                }
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
	private static class PushTuileWithQuestObjet {
	    private Joueur joueur;
	    public PushTuileWithQuestObjet(Joueur joueur) {
	        this.joueur = joueur;
	    }
	    public void execute() {
	        Coord coordonneesObjet = joueur.objets().get(0).coordonnees();
	        int x = coordonneesObjet.x;
	        int y = coordonneesObjet.y;
	        int minHorizontal = 6;
	        int minVertical = 6;
	        String action = "PUSH ";
	        String directionHorizontaleAPousser = "RIGHT";
	        String directionVerticaleAPousser = "DOWN";
	        if (x < (6 - x)) {
	            minHorizontal = x;
	            directionHorizontaleAPousser = "LEFT";
	        } else {
	            minHorizontal = 6 - x;
	        }
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
	private static class MoveToQuestObject {
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
	private static class FindNextAction {
	    private Partie partie;
	    public FindNextAction(Partie partie) {
	        this.partie = partie;
	    }
	    public void execute() {
	        if (TourDeJeu.PUSH.equals(partie.currentTurn())) {
	            if (partie.moi().possedeTuileAvecObjetDeMaQuete()) {
	                System.err.println("possedeTuileAvecObjetDeMaQuete");
	                System.out.println("PUSH 2 DOWN");
	            } else {
	                System.err.println("nePossedePasDeTuileAvecObjetDeMaQuete");
	                PushTuileWithQuestObjet getTuileWithQuestObjet = new PushTuileWithQuestObjet(partie.moi());
	                getTuileWithQuestObjet.execute();
	            }
	        } else if(TourDeJeu.MOVE.equals(partie.currentTurn())) {
	            MoveToQuestObject moveToQuestObject = new MoveToQuestObject(partie.plateau(), partie.moi(), partie.moi().objets().get(0));
	            moveToQuestObject.execute();
	        }
	    }
	}
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        while (true) {
            int turnType = in.nextInt();
            TourDeJeu tourActuel = TourDeJeu.PUSH;
            if (turnType == 1) {
                tourActuel = TourDeJeu.MOVE;
            }
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
                listInputTuiles.add(tuileJoueur);
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
                Objet objet = new Objet(itemName, itemX, itemY);
                if (itemPlayerId == 0 || itemX == -1) {
                    monJoueur.addObjet(objet);
                } else {
                    joueurEnnemi.addObjet(objet);
                }
                listInputTuiles.stream().filter(tuile -> tuile.coordonnees().equals(new Coord(itemX, itemY))).findFirst().ifPresent(tuile -> tuile.addObjet(objet));
            }
            int numQuests = in.nextInt(); // the total number of revealed quests for both players
            for (int i = 0; i < numQuests; i++) {
                String questItemName = in.next();
                int questPlayerId = in.nextInt();
            }
            Partie maPartie = new Partie(tourActuel, monJoueur, joueurEnnemi, listInputTuiles);
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
