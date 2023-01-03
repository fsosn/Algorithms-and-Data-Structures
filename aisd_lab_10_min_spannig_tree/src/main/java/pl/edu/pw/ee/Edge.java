package pl.edu.pw.ee;

public class Edge implements Comparable<Edge> {

    private final char firstNodeLabel;
    private final char secondNodeLabel;
    private final int edgeValue;

    public Edge(char firstNodeLabel, char secondNodeLabel, int edgeValue) {
        this.firstNodeLabel = firstNodeLabel;
        this.secondNodeLabel = secondNodeLabel;
        this.edgeValue = edgeValue;
    }

    @Override
    public int compareTo(Edge that) {
        if (that == null) {
            throw new IllegalArgumentException("Edge object cannot be null.");
        }
        return this.edgeValue - that.edgeValue;
    }
}
