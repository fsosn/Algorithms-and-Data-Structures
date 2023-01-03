package pl.edu.pw.ee;

public class Node {

    private final char nodeLabel;
    private final int edgeValue;

    public Node(char nodeLabel, int edgeValue) {
        this.nodeLabel = nodeLabel;
        this.edgeValue = edgeValue;
    }

    public char getNodeLabel() {
        return this.nodeLabel;
    }

    public int getEdgeValue() {
        return this.edgeValue;
    }
}
