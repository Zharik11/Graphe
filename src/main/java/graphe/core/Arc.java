package main.java.graphe.core;

import java.util.Objects;

public class Arc implements Comparable<Arc> {
    private String source;
    private String destination;
    private int valuation;

    public Arc(String source, String destination, int valuation) {
        this.source = source;
        this.destination = destination;
        this.valuation = valuation;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public int getValuation() {
        return valuation;
    }

    public void setValuation(int valuation) {
        this.valuation = valuation;
    }
    private boolean equals(Arc o) {
        return source.equals(o.getSource()) && destination.equals(o.getDestination()) && valuation == o.getValuation(); //
    }

    @Override
    public String toString() {
        if(destination == "") {
            return source + ":";
        }
        return source + "-" + destination + "(" + valuation + ")";
    }

    @Override
    public int compareTo(Arc o) {
        if (source.equals(o.getSource())) { //si les sommets sources sont égaux, on compare les destinations
            return destination.compareTo(o.destination);
        }
        return source.compareTo(o.getSource());
    }
}
