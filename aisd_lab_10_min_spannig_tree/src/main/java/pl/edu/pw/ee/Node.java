package pl.edu.pw.ee;

public class Node {

    private final String label;
    private final int weight;

    public Node(String label, int weight) {
        this.label = label;
        this.weight = weight;
    }

    public String getLabel() {
        return this.label;
    }

    public int getWeight() {
        return this.weight;
    }
}
