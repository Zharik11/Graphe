package main.java.graphe.algos;

import  main.java.graphe.core.*;

import java.util.*;

public class Dijkstra {
    public static void dijkstra(IGrapheConst g, String source, Map<String, Integer> dist, Map<String, String> prev) {
        // Initialisation des distances avec une valeur par défaut (ici Integer.MAX_VALUE)
        for (String sommet : g.getSommets()) {
            dist.put(sommet, Integer.MAX_VALUE);
        }

        // La distance du sommet source à lui-même est de 0
        dist.put(source, 0);

        // Ensemble des sommets à visiter
        Set<String> sommetsAVisiter = new HashSet<>();
        sommetsAVisiter.add(source);

        while (!sommetsAVisiter.isEmpty()) {
            // Recherche du sommet avec la plus petite distance actuelle
            String sommetCourant = trouverSommetPlusProche(dist, sommetsAVisiter);
            sommetsAVisiter.remove(sommetCourant);

            // Mise à jour des distances des successeurs du sommet courant
            for (String successeur : g.getSucc(sommetCourant)) {
                int poidsArc = g.getValuation(sommetCourant, successeur);
                int distanceTotale = dist.get(sommetCourant) + poidsArc;

                // Vérification de la valeur null avant d'accéder à la distance actuelle
                Integer distanceActuelle = dist.get(successeur);
                if (distanceActuelle == null || distanceTotale < distanceActuelle) {
                    dist.put(successeur, distanceTotale);
                    prev.put(successeur, sommetCourant);
                    sommetsAVisiter.add(successeur);
                }
            }
        }
    }

    private static String trouverSommetPlusProche(Map<String, Integer> dist, Set<String> sommets) {
        String sommetPlusProche = null;
        int distanceMin = Integer.MAX_VALUE;

        for (String sommet : sommets) {
            int distance = dist.get(sommet);
            if (distance < distanceMin) {
                distanceMin = distance;
                sommetPlusProche = sommet;
            }
        }

        return sommetPlusProche;
    }
}
