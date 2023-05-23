package main.java.graphe.implems;

import  java.util.*;
import  main.java.graphe.core.*;
public class GrapheMAdj implements IGraphe {
    private int[][] matrice;
    private Map<String, Integer> indices;

    public GrapheMAdj() {
        this.matrice = new int[0][0];
        this.indices = new HashMap<>();
    }
    public GrapheMAdj(String s) {
        this();
        peupler(s);
    }
    /**
     * Ajoute un sommet au graphe
     * @param noeud le sommet à ajouter
     */
    @Override
    public void ajouterSommet(String noeud) {
        if (!indices.containsKey(noeud)) {
            // On ajoute le nouveau sommet à la map des indices
            indices.put(noeud, indices.size());

            // On agrandit la matrice avec une nouvelle ligne et une nouvelle colonne
            int[][] nouvelleMatrice = new int[matrice.length + 1][matrice.length + 1];
            for (int i = 0; i < matrice.length; i++) {
                for (int j = 0; j < matrice[i].length; j++) {
                    nouvelleMatrice[i][j] = matrice[i][j];
                }
            }
            matrice = nouvelleMatrice;
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
        if(contientArc(source, destination)) {
            int src_index = getIndice(source);
            int dest_index = getIndice(destination);
            int existingValue = matrice[src_index][dest_index];
            if(existingValue != valeur) {
                throw new IllegalArgumentException("L'arc existe déjà dans le graphe avec une autre valuation.");
            }
            return;
        }
        if (valeur < 0) {
            throw new IllegalArgumentException("La valuation d'un arc ne peut pas être négative.");
        }
        if(!contientSommet(source)) {
            ajouterSommet(source);
        }
        if(!contientSommet(destination)) {
            ajouterSommet(destination);
        }
        int scr_index = getIndice(source);
        int dest_index = getIndice(destination);
        matrice[scr_index][dest_index] = valeur;
    }
    /**
     * Supprime un sommet du graphe
     * @param noeud le sommet à supprimer
     */
    @Override
    public void oterSommet(String noeud) {
        if (!indices.containsKey(noeud)) { // si le sommet n'existe pas dans le graphe
            return; // on ne fait rien
        }
        int idx = indices.get(noeud); // indice du sommet à enlever
        int n = matrice.length; // nombre de sommets
        int[][] newMatrice = new int[n-1][n-1]; // nouvelle matrice sans la ligne et colonne du sommet enlevé
        Map<String, Integer> newIndices = new HashMap<>(); // nouvelle map d'indices sans le sommet enlevé
        int row = 0;
        for (int i = 0; i < n; i++) {
            if (i != idx) { // on copie la ligne et colonne seulement si c'est pas celle du sommet enlevé
                newIndices.put(getSommets().get(i), row); // on met à jour la map d'indices
                int col = 0;
                for (int j = 0; j < n; j++) {
                    if (j != idx) { // on copie la valeur seulement si c'est pas dans la colonne du sommet enlevé
                        newMatrice[row][col] = matrice[i][j];
                        col++;
                    }
                }
                row++;
            }
        }
        // mettre à jour la matrice et la map d'indices
        matrice = newMatrice;
        indices = newIndices;
    }

    /**
     * Supprime un arc du graphe
     * @param source le sommet source de l'arc
     * @param destination le sommet destination de l'arc
     */
    @Override
    public void oterArc(String source, String destination) {
        // Vérifier si les sommets source et destination existent dans le graphe
        if (!indices.containsKey(source) || !indices.containsKey(destination)) {
            throw new IllegalArgumentException("Le sommet source ou destination n'existe pas dans le graphe.");
        }

        // Supprimer l'arc correspondant dans la matrice d'adjacence
        matrice[indices.get(source)][indices.get(destination)] = 0;
    }
    /**
     * Vérifie si un sommet est présent dans le graphe
     * @param noeud le sommet à vérifier
     * @return true si le sommet est présent, false sinon
     */
    @Override
    public List<String> getSommets() {
        List<String> sommets = new ArrayList<>();
        for (String sommet : indices.keySet()) {
            sommets.add(sommet);
        }
        return sommets;
    }
    /**
     * Vérifie si un arc est présent dans le graphe
     * @param source le sommet source de l'arc
     * @param destination le sommet destination de l'arc
     * @return true si l'arc est présent, false sinon
     */
    @Override
    public List<String> getSucc(String sommet) {
        List<String> successeurs = new ArrayList<>();
        int indexSommet = indices.get(sommet);
        for (int i = 0; i < matrice.length; i++) {
            if (matrice[indexSommet][i] != 0) {
                successeurs.add(getSommet(i));
            }
        }
        return successeurs;
    }
    private String getSommet(int i) {
        for (String sommet : indices.keySet()) {
            if (indices.get(sommet) == i) {
                return sommet;
            }
        }
        return null;
    }

    @Override
    public int getValuation(String src, String dest) {
        int indexSrc = indices.getOrDefault(src, -1);
        int indexDest = indices.getOrDefault(dest, -1);

        if (indexSrc < 0 || indexDest < 0 || indexSrc >= matrice.length || indexDest >= matrice.length) {
            throw new IllegalArgumentException("Les sommets spécifiés ne sont pas dans le graphe");
        }

        return matrice[indexSrc][indexDest];
    }
    private int getIndice(String sommet) {
        //assert(contientSommet(sommet));
        //return indices.get(sommet);
        return indices.getOrDefault(sommet, -1);
    }
    /**
     * Vérifie si un sommet est présent dans le graphe
     * @param sommet le sommet à vérifier
     * @return true si le sommet est présent, false sinon
     */
    @Override
    public boolean contientSommet(String sommet) {
        for (String s : indices.keySet()) {
            if (s.equals(sommet))
                return true;
        }
        return false;
    }
    /**
     * Vérifie si un arc est présent dans le graphe
     * @param source le sommet source de l'arc
     * @param destination le sommet destination de l'arc
     * @return true si l'arc est présent, false sinon
     */
    @Override
    public boolean contientArc(String src, String dest) {
        // Vérifier que les sommets sont présents dans le graphe
        if (!indices.containsKey(src) || !indices.containsKey(dest)) {
            return false;
        }
        // Récupérer les indices des sommets
        int i = indices.get(src);
        int j = indices.get(dest);
        // Vérifier si l'arc existe dans la matrice d'adjacence
        return matrice[i][j] != 0;
    }
    @Override
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
