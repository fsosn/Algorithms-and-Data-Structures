package pl.edu.pw.ee.graph;

public class Coordinates {

    private final String name;
    private final int x;
    private final int y;

    public Coordinates(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public String getName() {
        return this.name;
    }

    public int getY() {
        return this.y;
    }
}
