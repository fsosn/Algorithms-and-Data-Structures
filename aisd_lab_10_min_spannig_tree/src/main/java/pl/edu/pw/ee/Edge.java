package pl.edu.pw.ee;

public class Edge implements Comparable<Edge> {

    private final String sourceLabel;
    private final String destinationLabel;
    private final int weight;

    public Edge(String sourceLabel, String destinationLabel, int weight) {
        this.sourceLabel = sourceLabel;
        this.destinationLabel = destinationLabel;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge that) {
        if (that == null) {
            throw new IllegalArgumentException("Edge object cannot be null.");
        }
        return this.weight - that.weight;
    }

    public String getSourceLabel() {
        return this.sourceLabel;
    }

    public String getDestinationLabel() {
        return this.destinationLabel;
    }

    public int getWeight() {
        return this.weight;
    }
}
