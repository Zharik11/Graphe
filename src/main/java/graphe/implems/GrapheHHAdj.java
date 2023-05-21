package main.java.graphe.implems;
import  main.java.graphe.core.*;

import java.util.*;
public class GrapheHHAdj implements IGraphe{
    private Map<String, Map<String, Integer>> hhadj;
    public GrapheHHAdj() {
        this.hhadj = new HashMap<>();
    }
    public GrapheHHAdj(String s) {
        this();
        peupler(s);
    }

    /**
     * Ajoute un sommet au graphe
     * @param noeud le sommet à ajouter
     */
    @Override
    public void ajouterSommet(String noeud) {
        if (!contientSommet(noeud)) {
            hhadj.put(noeud, new HashMap<>());
        }
    }
    /**
     * Ajoute un arc au graphe
     * @param source le sommet source de l'arc
     * @param destination le sommet destination de l'arc
     * @param valeur la valuation de l'arc
     */
    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (contientArc(source, destination)) throw new IllegalArgumentException("arc déjà présent");
        if (valeur < 0) throw new IllegalArgumentException("valuation négative");
        if (hhadj.get(source) == null) hhadj.put(source, new HashMap<>());
        if (hhadj.get(destination) == null) hhadj.put(destination, new HashMap<>());
        if (!contientArc(source, destination)) {
            hhadj.get(source).put(destination, valeur);
        }
    }
    /**
     * Supprime un sommet du graphe
     * @param noeud le sommet à supprimer
     */
    @Override
    public void oterSommet(String noeud) {
        hhadj.remove(noeud);
        for (String s : hhadj.keySet()) {
            hhadj.get(s).remove(noeud);
        }
    }
    /**
     * Supprime un arc du graphe
     * @param source le sommet source de l'arc
     * @param destination le sommet destination de l'arc
     */
    @Override
    public void oterArc(String source, String destination) {
        if (hhadj.get(source) == null || !hhadj.get(source).containsKey(destination)) {
            throw new IllegalArgumentException("Arc non présent");
        }
        hhadj.get(source).remove(destination);
    }

    /**
     * Retourne la liste des sommets du graphe
     * @return la liste des sommets du graphe
     */
    @Override
    public List<String> getSommets() {
        List<String> sommets = new ArrayList<>();
        for (String s : hhadj.keySet()) {
            sommets.add(s);
        }
        return sommets;
    }
    /**
     * Retourne la liste des successeurs d'un sommet
     * @param sommet le sommet dont on veut les successeurs
     * @return la liste des successeurs du sommet
     */
    @Override
    public List<String> getSucc(String sommet) {
        List<String> succ = new ArrayList<>();
        for (String s : hhadj.get(sommet).keySet()) {
            succ.add(s);
        }

        return succ;
    }
    /**
     * Retourne la valuation d'un arc
     * @param src le sommet source de l'arc
     * @param dest le sommet destination de l'arc
     * @return la valuation de l'arc
     */
    @Override
    public int getValuation(String src, String dest) {
        return hhadj.get(src).get(dest);
    }
    /**
     * Retourne la liste des prédécesseurs d'un sommet
     * @param sommet le sommet dont on veut les prédécesseurs
     * @return la liste des prédécesseurs du sommet
     */
    @Override
    public boolean contientSommet(String sommet) {
        return hhadj.containsKey(sommet);
    }
    /**
     * Vérifie si un arc est présent dans le graphe
     * @param src le sommet source de l'arc
     * @param dest le sommet destination de l'arc
     * @return true si l'arc est présent, false sinon
     */
    @Override
    public boolean contientArc(String src, String dest) {
        if (hhadj.get(src) == null) return false;
        if (hhadj.get(src).get(dest) == null) return false;
        return hhadj.get(src).containsKey(dest);
    }
    /**
     * Peuple le graphe à partir d'une chaîne de caractères
     * @param s la chaîne de caractères
     */
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