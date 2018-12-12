package charco.xmas.infrastructure;

import competitive.programming.geometry.Coord;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class TuileGraph {

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

    /** Les méthodes statiques manipulent plusieurs sommets du graphe
     *    - le graphe est une collection de sommets
     *    - chaque sommet est vu comme le graphe égal à
     * la composante connexe issue de ce sommet.
     **/

    /** Pour remettre en situation initiale chaque sommet **/
    private static void reset(Collection c){
        for (Iterator it = c.iterator(); it.hasNext();){
            TuileGraph g = (TuileGraph)it.next();
            g.reset();
        }
    }

    /** Pour créer un arete non orientée entre deux sommets **/
    public static void arete(TuileGraph g1, TuileGraph g2){
        g1.addAdjacent(g2);
        g2.addAdjacent(g1);
    }

    /** Affichage des sommets dans l'ordre du parcours en profondeur **/
    public static void parcoursProfondeur(Collection c){
        reset(c);
        for (Iterator it = c.iterator(); it.hasNext();){
            TuileGraph g = (TuileGraph)it.next();
            if (!g.estDejaParcouru())
                g.visiterProfondeur();
        }
        System.err.println();
    }

    /** S'il existe, affichage d'un chemin
     *    - Méthode basée sur le parcours en profondeur
     **/
    public static boolean existeChemin(Collection c, TuileGraph s, TuileGraph t){
        reset(c);
        boolean b = s.existeChemin(t);
        if (b)
            t.afficherAncetres();
        else
            System.err.println("pas de chemin");
        return b;
    }

    /** Affichage des sommets dans l'ordre du parcours en largeur **/
    public static void parcoursLargeur(Collection c){
        reset(c);
        for (Iterator it = c.iterator(); it.hasNext();){
            TuileGraph g = (TuileGraph)it.next();
            if (!g.estDejaParcouru())
                g.visiterLargeur();
        }
        System.err.println();
    }

    /** S'il existe, affichage d'un chemin
     *    - Méthode basée sur le parcours en largeur
     **/
    public static int plusCourtChemin(Collection c, TuileGraph s, TuileGraph t){
        reset(c);
        int d = s.plusCourtChemin(t); // parcours largeur depuis s
        if (d>0)
            t.afficherAncetres();
        else
            System.err.println("pas de chemin");
        return t.getDistance();
    }


    /** Les méthodes non statiques propres à chaque sommet **/

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
