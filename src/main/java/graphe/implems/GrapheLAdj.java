package main.java.graphe.implems;
import  main.java.graphe.core.*;

import java.util.*;

public class GrapheLAdj implements IGraphe {
    private Map<String, List<Arc>> ladj;

    public GrapheLAdj() {
        this.ladj = new HashMap<>();
    }

    public GrapheLAdj(String s) {
        this();
        peupler(s);
    }
    /**
     * Peuple le graphe à partir d'une chaîne de caractères.
     * @param s la chaîne de caractères
     */

    @Override
    public void ajouterSommet(String noeud) {
        if (!contientSommet(noeud)) {
            ladj.put(noeud, new ArrayList<>());
        }
    }
    /** Ajoute un arc au graphe
     * @param source le sommet source de l'arc
     * @param destination le sommet destination de l'arc
     * @param valeur la valuation de l'arc
     */
    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (contientArc(source, destination)) throw new IllegalArgumentException("arc déjà présent");
        if (valeur < 0) throw new IllegalArgumentException("valuation négative");
        if (ladj.get(source) == null) ladj.put(source, new ArrayList<>());
        if (ladj.get(destination) == null) ladj.put(destination, new ArrayList<>());
        if (!contientArc(source, destination)) {
            ladj.get(source).add(new Arc(source, destination, valeur));
        }
    }
    /** Supprime un sommet du graphe
     * @param noeud le sommet à supprimer
     */
    @Override
    public void oterSommet(String noeud) {
        ladj.remove(noeud);
        for (String s : ladj.keySet()) {
            ladj.get(s).removeIf(arc -> arc.getDestination().equals(noeud));
        }
    }
    /** Supprime un arc du graphe
     * @param source le sommet source de l'arc
     * @param destination le sommet destination de l'arc
     */
    @Override
    public void oterArc(String source, String destination) {
        if (!contientArc(source, destination)) throw new IllegalArgumentException("arc non présent");
        ladj.get(source).removeIf(arc -> arc.getDestination().equals(destination));
    }
    /** Vérifie si le graphe contient un sommet
     * @param sommet le sommet à vérifier
     * @return true si le sommet est présent, false sinon
     */
    @Override
    public List<String> getSommets() {
        List<String> sommets = new ArrayList<>();
        for (String s : ladj.keySet()) {
            sommets.add(s);
        }

        return sommets;
    }
    /** Renvoie la liste des successeurs d'un sommet
     * @param sommet le sommet dont on veut les successeurs
     * @return la liste des successeurs
     */
    @Override
    public List<String> getSucc(String sommet) {
        List<String> succ = new ArrayList<>();
        for (Arc arc : ladj.get(sommet)) {
            succ.add(arc.getDestination());
        }
        return succ;
    }
    /** Renvoie la valuation d'un arc
     * @param src le sommet source de l'arc
     * @param dest le sommet destination de l'arc
     * @return la valuation de l'arc
     */
    @Override
    public int getValuation(String src, String dest) {
        for (Arc arc : ladj.get(src)) {
            if (arc.getDestination().equals(dest)) {
                return arc.getValuation();
            }
        }
        return -1;
    }

    @Override
    public boolean contientSommet(String sommet) {
        for (String s : ladj.keySet()) {
            if (s.equals(sommet)) {
                return true;
            }
        }
        return false;
    }
    /** Vérifie si le graphe contient un arc
     * @param src le sommet source de l'arc
     * @param dest le sommet destination de l'arc
     * @return true si l'arc est présent, false sinon
     */
    @Override
    public boolean contientArc(String src, String dest) {
        if (!contientSommet(src) || !contientSommet(dest)) {
            return false;
        }
        for (Arc arc : ladj.get(src)) {
            if (arc.getDestination().equals(dest)) {
                return true;
            }
        }
        return false;
    }
    public String toString() {
        StringBuilder s = new StringBuilder();
        List<String> sommetsTries = new ArrayList<String>(getSommets());
        Collections.sort(sommetsTries);

        boolean first = true;

        for (String src : sommetsTries) {
            List<String> succTries = new ArrayList<String>(getSucc(src));
            Collections.sort(succTries);

            if (succTries.isEmpty()) {
                if (!first){
                    s.append(", ");
                }
                s.append(src).append(":");
                first = false;
            }

            for (String dest : succTries) {
                if (!first){
                    s.append(", ");
                }
                s.append(src).append("-").append(dest).append("(").append(getValuation(src, dest)).append(")");
                first = false;
            }
        }
        return s.toString();
    }
}