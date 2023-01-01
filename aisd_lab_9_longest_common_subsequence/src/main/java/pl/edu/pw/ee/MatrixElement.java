package pl.edu.pw.ee;

public class MatrixElement {

    public final static char DIRECTION_SYMBOL_DEFAULT = '\0';
    public final static char DIRECTION_SYMBOL_LEFT = '<';
    public final static char DIRECTION_SYMBOL_UP = '^';
    public final static char DIRECTION_SYMBOL_ACROSS = '\\';

    private final int value;
    private final char direction;
    private boolean visited;

    public MatrixElement(int value, char direction) {
        this.value = value;
        this.direction = direction;
        this.visited = false;
    }

    public int getValue() {
        return this.value;
    }

    public char getDirection() {
        return this.direction;
    }

    public boolean getVisited() {
        return this.visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
