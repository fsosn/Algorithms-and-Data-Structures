package pl.edu.pw.ee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class Graph {

    public List<Node> buildAdjacencyListFromFile(String filepath) {
        try ( BufferedReader bf = new BufferedReader(new FileReader(filepath))) {
        } catch () {
        };
    }
}
