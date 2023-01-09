package pl.edu.pw.ee;

import java.util.ArrayList;
import java.util.List;

public class ParentNode {

    private final String label;
    private final List<Node> adjacencyList;

    public ParentNode(String label) {
        this.label = label;
        this.adjacencyList = new ArrayList<>();
    }

    public String getLabel() {
        return this.label;
    }

    public void addNodeToAdjList(Node node) {
        this.adjacencyList.add(node);
    }

    public int getNumOfLinks() {
        return this.adjacencyList.size();
    }

    public Node getAdjListElement(int index) {
        return this.adjacencyList.get(index);
    }

    public boolean checkIfAdjListContainsLabel(String label) {
        for (Node node : adjacencyList) {
            if (node.getLabel().equals(label)) {
                return true;
            }
        }
        return false;
    }
}
