package main.java.graphe.algos;

import  main.java.graphe.core.*;

import java.util.*;

public class Dijkstra {
    private static final int INFINI = Integer.MAX_VALUE;

    public static void dijkstra(IGrapheConst g, String source, Map<String, Integer> dist, Map<String, String> prev) {
        for (String g1 : g.getSommets()) {
            dist.put(g1, INFINI);
            prev.put(g1, null);
        }
        dist.put(source, 0);
        List<String> Q = new ArrayList<>(g.getSommets());
        while (!Q.isEmpty()) {
            String u = extraireMin(Q, dist);
            Q.remove(u);
            Integer distU = dist.get(u);
            if (distU != null && distU != INFINI) {
                for (String v : g.getSucc(u)) {
                    int valuation = g.getValuation(u, v);
                    int alt = dist.get(u) + g.getValuation(u, v);
                    Integer distV = dist.get(v);
                    if (distV == null || alt < distV) {
                        dist.put(v, alt);
                        prev.put(v, u);
                    }
                }
            } else {
                dist.remove(u);
            }
        }
    }

    /**
          * Fonction permettant d'extraire le sommet ayant la plus petite distance
          * @param q la liste des sommets
          * @param dist la distance entre le sommet de départ et les autres sommets
          * @return le sommet ayant la plus petite distance
          */

    private static String extraireMin(List<String> q, Map<String, Integer> dist) {
        String min = q.get(0);
        for (String s : q) {
            if (dist.get(s) < dist.get(min)) {
                min = s;
            }
        }
        return min;
    }
}