package reprensentaion;
import graphe.*;

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

    @Override
    public void ajouterSommet(String noeud) {
        if (!contientSommet(noeud)) {
            hhadj.put(noeud, new HashMap<>());
        }
    }

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

    @Override
    public void oterSommet(String noeud) {
        hhadj.remove(noeud);
        for (String s : hhadj.keySet()) {
            hhadj.get(s).remove(noeud);
        }
    }

    @Override
    public void oterArc(String source, String destination) {
        if (hhadj.get(source) == null || !hhadj.get(source).containsKey(destination)) {
            throw new IllegalArgumentException("Arc non présent");
        }
        hhadj.get(source).remove(destination);
    }


    @Override
    public List<String> getSommets() {
        List<String> sommets = new ArrayList<>();
        for (String s : hhadj.keySet()) {
            sommets.add(s);
        }
        return sommets;
    }

    @Override
    public List<String> getSucc(String sommet) {
        List<String> succ = new ArrayList<>();
        for (String s : hhadj.get(sommet).keySet()) {
            succ.add(s);
        }

        return succ;
    }

    @Override
    public int getValuation(String src, String dest) {
        return hhadj.get(src).get(dest);
    }

    @Override
    public boolean contientSommet(String sommet) {
        return hhadj.containsKey(sommet);
    }

    @Override
    public boolean contientArc(String src, String dest) {
        if (hhadj.get(src) == null) return false;
        if (hhadj.get(src).get(dest) == null) return false;
        return hhadj.get(src).containsKey(dest);
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
