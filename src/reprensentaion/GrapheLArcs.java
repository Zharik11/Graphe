package reprensentaion;

import graphe.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class GrapheLArcs implements IGraphe{
    private List<Arc> arcs;

    public GrapheLArcs(){

        this.arcs = new ArrayList<>();
    }
    public GrapheLArcs(String s){
        this();
        peupler(s);
    }
    /**
     * Peuple le graphe à partir d'une chaîne de caractères.
     * @param s la chaîne de caractères
     */
    @Override
    public void ajouterSommet(String noeud) {
        if(!contientSommet(noeud)){
            arcs.add(new Arc(noeud, "", 0));
        }
    }
    /** Ajoute un arc au graphe
     * @param source le sommet source de l'arc
     * @param destination le sommet destination de l'arc
     * @param valeur la valuation de l'arc
     */
    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if(contientArc(source,destination)) throw  new IllegalArgumentException("arc déjà présent");
        if (valeur < 0) throw new IllegalArgumentException("valuation négative");

        if(DestVide(source) && contientSommet(source)){
            oterArc(source, "");
        }

        if(!contientArc(source, destination)){
            arcs.add(new Arc(source, destination, valeur));
        }

    }
    /** Vérifie si un arc est présent dans le graphe
     * @param source le sommet source de l'arc
     * @param destination le sommet destination de l'arc
     * @return true si l'arc est présent, false sinon
     */
    public boolean DestVide(String sommet){
        //assert(contientSommet(sommet));
        for (Arc arc: arcs) {
            if (arc.getSource().equals(sommet) && Objects.equals(arc.getDestination(), "")) {
                return true;
            }
        }
        return false;
    }
    /** Supprime un sommet du graphe
     * @param noeud le sommet à supprimer
     */
    @Override
    public void oterSommet(String noeud) {
        arcs.removeIf(arc -> arc.getSource().equals(noeud) || arc.getDestination().equals(noeud));
    }
    /** Supprime un arc du graphe
     * @param source le sommet source de l'arc
     * @param destination le sommet destination de l'arc
     */
    @Override
    public void oterArc(String source, String destination) {
        if (!contientArc(source, destination)) throw new IllegalArgumentException("arc non présent");
        arcs.removeIf(arc -> arc.getSource().equals(source) && arc.getDestination().equals(destination));
    }
    /** Vérifie si un sommet est présent dans le graphe
     * @param noeud le sommet à vérifier
     * @return true si le sommet est présent, false sinon
     */
    @Override
    public List<String> getSommets() {
        List<String> sommets = new ArrayList<>();
        for(Arc arc : arcs){
            if(!sommets.contains(arc.getSource())){
                sommets.add(arc.getSource());
            }
        }
        return sommets;
    }

    @Override
    public List<String> getSucc(String sommet) {
        List<String> successeurs = new ArrayList<>();
        for(Arc arc : arcs){
            if(arc.getSource().equals(sommet)){
                successeurs.add(arc.getDestination());
            }
        }
        return successeurs;
    }

    @Override
    public int getValuation(String src, String dest) {
        for(Arc arc : arcs){
            if(arc.getSource().equals(src) && arc.getDestination().equals(dest)){
                return arc.getValuation();
            }
        }
        return -1;
    }

    @Override
    public boolean contientSommet(String sommet) {
        for(Arc arc : arcs){
            if(arc.getSource().equals(sommet) || arc.getDestination().equals(sommet)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contientArc(String src, String dest) {
        for(Arc arc : arcs){
            if(arc.getSource().equals(src) && arc.getDestination().equals(dest)){
                return true;
            }
        }
        return false;
    }

        @Override
        public String toString() {
            StringBuilder s = new StringBuilder();
            Collections.sort(arcs);
            boolean first = true;
            for (Arc arc : this.arcs) {
                if(!first) {
                    s.append(", ");
                }
                s.append(arc.toString());
                first = false;
            }
            return s.toString();
        }
    }
