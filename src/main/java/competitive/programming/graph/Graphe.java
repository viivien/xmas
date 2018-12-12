package competitive.programming.graph;

import java.util.*;

public class Graphe{
    private int ident, info;
    private List adjacents;
    private int couleur, distance;
    private Graphe pere;

    public Graphe(int n,int valeur){
        ident = n;
        info = valeur;
        adjacents = new LinkedList();
        pere = null;
        couleur = 0; //blanc
        distance = -1;
    }

    /** Les méthodes statiques manipulent plusieurs sommets du graphe
     *    - le graphe est une collection de sommets
     *    - chaque sommet est vu comme le graphe égal à
     * la composante connexe issue de ce sommet.
     **/

    /** Pour remettre en situation initiale chaque sommet **/
    private static void reset(Collection c){
        for (Iterator it = c.iterator(); it.hasNext();){
            Graphe g = (Graphe)it.next();
            g.reset();
        }
    }

    /** Pour créer un arete non orientée entre deux sommets **/
    public static void arete(Graphe g1, Graphe g2){
        g1.addAdjacent(g2);
        g2.addAdjacent(g1);
    }

    /** Affichage des sommets dans l'ordre du parcours en profondeur **/
    public static void parcoursProfondeur(Collection c){
        reset(c);
        for (Iterator it = c.iterator(); it.hasNext();){
            Graphe g = (Graphe)it.next();
            if (g.getCouleur()==0)
                g.visiterProfondeur();
        }
        System.out.println();
    }

    /** S'il existe, affichage d'un chemin
     *    - Méthode basée sur le parcours en profondeur
     **/
    public static boolean existeChemin(Collection c, Graphe s, Graphe t){
        reset(c);
        boolean b = s.existeChemin(t);
        if (b)
            t.afficherAncetres();
        else
            System.out.println("pas de chemin");
        return b;
    }

    /** Affichage des sommets dans l'ordre du parcours en largeur **/
    public static void parcoursLargeur(Collection c){
        reset(c);
        for (Iterator it = c.iterator(); it.hasNext();){
            Graphe g = (Graphe)it.next();
            if (g.getCouleur()==0)
                g.visiterLargeur();
        }
        System.out.println();
    }

    /** S'il existe, affichage d'un chemin
     *    - Méthode basée sur le parcours en largeur
     **/
    public static int plusCourtChemin(Collection c, Graphe s, Graphe t){
        reset(c);
        int d = s.plusCourtChemin(t); // parcours largeur depuis s
        if (d>0)
            t.afficherAncetres();
        else
            System.out.println("pas de chemin");
        return t.getDistance();
    }


    /** Les méthodes non statiques propres à chaque sommet **/

    public final int    getInfo()      {return info;}
    public final Graphe getPere()      {return pere;}
    public final int    getCouleur()   {return couleur;}
    public final int    getDistance()  {return distance;}
    public final List   getAdjacents() {return adjacents;}

    public final void setCouleur(int c)    {couleur = c;}
    public final void setDistance(int d)   {distance = d;}
    public void addAdjacent(Graphe g)      {adjacents.add(g);}

    private void setPere(Graphe g)         {pere = g;}

    private void reset(){
        setCouleur(0); // blanc;
        pere = null;
        distance = -1;
    }

    public final void visiterProfondeur(){
        System.out.print(this);
        couleur = 1; // gris
        for (Iterator it = adjacents.iterator(); it.hasNext();){
            Graphe g = (Graphe)it.next();
            if (g.getCouleur()==0){
                g.setPere(this);
                g.visiterProfondeur();
            }
        }
        couleur = 2; // noir
    }

    public final boolean existeChemin(Graphe t){
        boolean trouve = false;
        couleur = 1; // gris
        if (equals(t))
            trouve = true;;
        for (Iterator it = adjacents.iterator(); it.hasNext();){
            Graphe g = (Graphe)it.next();
            if (g.getCouleur()==0){
                g.setPere(this);
                trouve = trouve || g.existeChemin(t);
            }
        }
        couleur = 2; // noir
        return trouve;
    }

    public final void visiterLargeur(){
        couleur = 1; // gris
        distance = 0; // sommet de départ
        List file = new LinkedList();
        file.add(this);
        while (!file.isEmpty()){
            Graphe u = (Graphe)file.remove(0);
            System.out.print(u);
            for (Iterator it = u.getAdjacents().iterator(); it.hasNext();){
                Graphe g = (Graphe)it.next();
                if (g.getCouleur()==0){
                    g.setPere(u);
                    g.setCouleur(2);
                    g.setDistance(u.getDistance()+1);
                    file.add(g);
                }
            }
            u.setCouleur(2); // noir
        }
        couleur = 2;
    }

    public final int plusCourtChemin(Graphe t){
        couleur = 1; // gris
        distance = 0; // sommet de départ
        List file = new LinkedList();
        file.add(this);
        while (!file.isEmpty()){
            Graphe u = (Graphe)file.remove(0);
            if (u.equals(t))
                return u.getDistance();
            for (Iterator it = u.getAdjacents().iterator(); it.hasNext();){
                Graphe g = (Graphe)it.next();
                if (g.getCouleur()==0){
                    g.setPere(u);
                    g.setCouleur(2);
                    g.setDistance(u.getDistance()+1);
                    file.add(g);
                }
            }
            u.setCouleur(2); // noir
        }
        couleur = 2;
        return -1;
    }

    public void afficherAncetres(){
        List chemin = new LinkedList();
        Graphe aux = this;
        while (aux.getPere()!=null){
            chemin.add(0,aux); // en debut de liste
            aux = aux.getPere();
        }
        chemin.add(0,aux);
        System.out.println(chemin);
    }

    public String toString(){
        return "["+ident+", "+info+"]";
    }

}
