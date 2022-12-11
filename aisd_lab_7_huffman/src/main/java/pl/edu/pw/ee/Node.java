package pl.edu.pw.ee;

public class Node implements Comparable<Node> {

    private char character = '\0';
    private final int frequency;
    private final Node leftChild;
    private final Node rightChild;

    public Node(Node leftChild, Node rightChild) {
        this.frequency = leftChild.frequency + rightChild.frequency;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public Node(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
        this.leftChild = null;
        this.rightChild = null;
    }

    @Override
    public int compareTo(Node node) {
        return this.frequency - node.frequency;
    }

    public boolean isLeaf() {
        return this.leftChild == null && this.rightChild == null;
    }

    public Node getLeftChild() {
        return this.leftChild;
    }

    public Node getRightChild() {
        return this.rightChild;
    }

    public char getCharacter() {
        return this.character;
    }
}
