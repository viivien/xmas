import java.util.List;
import java.util.Scanner;
import java.util.Objects;
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
	    private List<Chemin> chemins;
	    private List<Objet> objets;
	    public Tuile() {
	        objets = new ArrayList<>();
	    }
	    public Tuile(int x, int y, List<Chemin> listChemins) {
	        this();
	        coordonnes = new Coord(x, y);
	        chemins = listChemins;
	    }
	    public Coord coordonnees() {
	        return coordonnes;
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
	}
	private static class Objet {
	    private String nom;
	    private Coord coordonnees;
	    public Objet(String nom, int x, int y) {
	        this.nom = nom;
	        this.coordonnees = coordonnees;
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
                    List<Chemin> listChemins = getCheminsFromInputString(tile);
                    Tuile tuile = new Tuile(i, j, listChemins);
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
                List<Chemin> listChemins = getCheminsFromInputString(playerTile);
                Tuile tuileJoueur = new Tuile(playerX, playerY, listChemins);
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
            if (maPartie.currentTurn().equals(TourDeJeu.PUSH)) {
                System.out.println("PUSH " + joueurEnnemi.coordonnees().x + " LEFT");
            } else {
                System.out.println("PASS");
            }
        }
    }
    private static List<Chemin> getCheminsFromInputString(String tile) {
        List<Chemin> chemins = new ArrayList<>();
        if (tile.toCharArray()[0] == '1') {
            chemins.add(new Chemin(Direction.UP));
        }
        if (tile.toCharArray()[1] == '1') {
            chemins.add(new Chemin(Direction.RIGHT));
        }
        if (tile.toCharArray()[2] == '1') {
            chemins.add(new Chemin(Direction.DOWN));
        }
        if (tile.toCharArray()[3] == '1') {
            chemins.add(new Chemin(Direction.LEFT));
        }
        return chemins;
    }
}
